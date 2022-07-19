package com.izo.camp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.izo.camp.addmoney.AddmoneyService;
import com.izo.camp.market.BasketService;
import com.izo.camp.market.MarketService;
import com.izo.camp.market.OrderService;
import com.izo.camp.member.MemberService;

import com.izo.camp.review.ReviewService;
import com.izo.camp.vo.AddmoneyVO;
import com.izo.camp.vo.BasketVO;
import com.izo.camp.vo.OrderVO;
import com.izo.camp.vo.PDetailVO;
import com.izo.camp.vo.ProductVO;
import com.izo.camp.vo.ReviewVO;

@Controller
public class MarketController {

	@Autowired
	MarketService marketService;
	@Autowired
	BasketService basketService;
	@Autowired
	AddmoneyService addmoneyService;
	@Autowired 
	OrderService orderService;
	@Autowired
	ReviewService reviewService;

	@Autowired
	MemberService memberService;	
	
	@Autowired
	HttpSession session;
	
	
	//마켓메인이동
	@RequestMapping("/marketMain_Temp")
	public String goMarketMain(Model model) {
		
		model.addAttribute("productList", marketService.getList());
		
		return "market/marketMain";
	}
	
	
	
	@RequestMapping("/marketDetail")
	public String goDetail(int idx, Model model) {
		
		ProductVO product = marketService.getProductByIdx(idx);
		PDetailVO pDetail = marketService.getPDetail(product.getProductId());
		
		List<ReviewVO> reviewList = reviewService.getListById(product.getProductId());
		
		model.addAttribute("product", product);
		model.addAttribute("pDetail", pDetail);
		
		model.addAttribute("reviewList", reviewList);
		
		return "market/detail";
	}
	
	//장바구니 페이지 이동
	@RequestMapping("/goBasket")
	public String goBasket(Model model) {
		String loginId = (String)session.getAttribute("loginId");
		
		List<BasketVO> basketList = basketService.getBasket(loginId);
		
		int totolPrice = 0;
		for(BasketVO vo : basketList) {
			totolPrice += vo.getTotalPrice();
		}
		model.addAttribute("basketProduct", basketList);
		model.addAttribute("totalPrice", totolPrice);
		
		return "market/basket";
	}
	
	//장바구니 추가
	@ResponseBody
	@RequestMapping("/addBasket")
	public String addBasket(BasketVO basketVO) {
		
		basketService.addBasket(basketVO);
		
		return basketVO.getMemberId();
	}
	
	//plusMinusPcs
	//plusMinusPage 플러스 마이너스 쿼리
	@ResponseBody
	@RequestMapping(value="/plusMinusPcs",method=RequestMethod.POST )
	public int plusMinus(BasketVO basketVO) {
	
		//chagePcs = 변한 토탈 가격을 반환한다.
		int totalPrice = basketService.changePcs(basketVO);
		
		return totalPrice;
	}
	
	//deleteBasket 장바구니 항목 지우기
	@ResponseBody
	@RequestMapping("/deleteBasket")
	public int deleteBasket(BasketVO basketVO) {
		basketService.deleteProduct(basketVO);
		List<BasketVO> basketList = basketService.getBasket(basketVO.getMemberId());
		int totalPrice = 0;
		for(BasketVO vo : basketList) {
			totalPrice += vo.getTotalPrice();
		}
		return totalPrice;
	}
	
	@ResponseBody
	@RequestMapping(value="/findTotalPrice",method=RequestMethod.POST )
	public int findTotalPrice(String memberId) {
		
		List<BasketVO> basketList = basketService.getBasket(memberId);
		int totalPrice = 0;
		for(BasketVO vo : basketList) {
			totalPrice += vo.getTotalPrice();
		}
		
		return totalPrice;
	}
	
	//오더페이지 
	@RequestMapping("/goOrderPage")
	public String goOrderPage(Model model) {
		String loginId = (String)session.getAttribute("loginId");
		
		List<BasketVO> basketList = basketService.getBasket(loginId);
		int totalPrice = 0;
		for(BasketVO vo : basketList) {
			totalPrice += vo.getTotalPrice();
		}
		
		AddmoneyVO addmoneyVO = addmoneyService.getMoneyInfo(loginId);
		//getMoneyInfo
		model.addAttribute("basketProduct", basketList);
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("charge",addmoneyVO.getTotalmoney());
		model.addAttribute("simplePwd",addmoneyVO.getSimplepwd());
		
		
		return "market/order";
	}
	
	@RequestMapping("checkHasCard")
	@ResponseBody
	public boolean checkHasCard(String loginId) {
		return addmoneyService.getMoneyInfo(loginId) != null;
	}
	
	//주문완료 ! 
	//주문완료 페이지를 Redirection 으로 막았어야한다.
	//이후 주문완료페이지에서 새로고침시 요청이 다시 들어간다.
	//지금 작동으로는 문제가 되지않음 => 장바구니 DB에서 직접 조회를 해서 꺼내는 작업을 진행했음
	//하지만 addMoneyService.minusPoint(addMoneyVO) 쿼리가 계속해서 반복되서 실행될 것임.
	//물론 addMoneyVO에 TotalMoney는 변경이 없음 그렇지만 내역이 계속해서 변함없고 똑같은 형태로 DB에 저장 될 것.
	//다음에 짤때에는 redirection으로 페이지를 이동시키는게 제일 합리적임.
	@RequestMapping("orderOk")
	public String orderOk(Model model) {
		//구매 관련 DB 수정 구현
		//session에 장바구니에 baskeVO 들을 가져옴 
		String loginId = (String)session.getAttribute("loginId");
		
		List<BasketVO> basketList = basketService.getBasket(loginId);
		
		//난수 발생 -주문번호 추후 수정필요
		//주문번호를 생성후에
		//order에 정보 삽입.
		Integer orderNum = new Random().nextInt(100000);
		int totalPrice = 0;
		List<OrderVO> orderList = new ArrayList<OrderVO>();
		for(BasketVO basketVO: basketList) {
			OrderVO orderVO = new OrderVO();
			orderVO.setMemberId(basketVO.getMemberId());
			orderVO.setProductId(basketVO.getProductId());
			orderVO.setOrderNum(orderNum);
			orderVO.setPcs(basketVO.getPcs());
			orderVO.setTotalPrice(basketVO.getTotalPrice());
			totalPrice = basketVO.getTotalPrice();
			orderList.add(orderVO);
		}
		orderService.orderAdd(orderList);
	
		
		
		//카드에서 돈빼기
		AddmoneyVO addmoneyVO = new AddmoneyVO();
		addmoneyVO.setId(loginId);
		addmoneyVO.setTotalmoney(totalPrice);
		
		String address = memberService.getAddress(loginId);
		String resultMessage = "";
		if(addmoneyService.minusPoint(addmoneyVO)) {
			resultMessage ="구매가 완료 되었습니다.";
			basketService.clearBasket(loginId);
			
		}else {
			resultMessage ="구매에 실패 했습니다.";
		}
		
		model.addAttribute("resultMessage", resultMessage);
		model.addAttribute("address",address);

		model.addAttribute("orderList",basketList);
		
		return "market/orderComplete";
	}
	
}
