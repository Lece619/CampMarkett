package com.izo.camp.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.izo.camp.infomation.CampInfoService;
import com.izo.camp.infomation.WeatherService;
import com.izo.camp.member.MemberService;
import com.izo.camp.vo.CampInfoVO;

@Controller
public class CampingAreaController {
	
	@Autowired
	WeatherService weatherService;
	
	@Autowired
	CampInfoService campInfoService;
	
	@Autowired
	MemberService memberService;

	
	//캠핑장 상세보기
	@RequestMapping("/campDetail")
	public String goDetailPage(Integer idx,Model model) {
		
		CampInfoVO campInfoVO = campInfoService.campInfoByIndex(idx);
		
		
		model.addAttribute("campInfo",campInfoVO);
		
		return "campingArea/detail";
	}
	
	
	
	//나의 위치 선택하기
	@RequestMapping("/makeLocation")
	public String goMakeLocation(HttpServletRequest request,Model model) {
		HttpSession session = request.getSession();
		
		Map<String,Double> getXY = new HashMap<String, Double>();
		getXY.put("lat", (Double) session.getAttribute("sessionLat"));
		getXY.put("lon", (Double) session.getAttribute("sessionLon"));
		
		List<CampInfoVO> list  = 
				campInfoService.getNearCampingArea(getXY);
		list = list.subList(0, 10);
		model.addAttribute("camplist", list);
		return "campingArea/popupLocation";
	}
	
	
	
	//==============================================임시  ==> 메인
	@RequestMapping("campingAreaMain_Temp")
	public String goAreaMaingo(@RequestParam(required=false, defaultValue="0")Double lat,
			@RequestParam(required=false, defaultValue="0")Double lon,
			@RequestParam(required=false, defaultValue="1")int page,
			Model model,HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		//현재 위치 xy 좌표
		Map<String,Double> getXY = new HashMap<String, Double>();
		
		/////////////////////////////////////////////////////////
		//처음 접속 or 위치 선택을 안했을때는
		//주어진 정보가 있다면 
		
		if(lat!=0) {
			getXY.put("lat", lat);
			getXY.put("lon", lon);
		}
		//주어진 정보가 없고 세션도 null이면  자신의주소
		else if(session.getAttribute("sessionLat") == null) {
			//자신의 주소로 위치 확인 
			String address;
			if(session.getAttribute("loginId")==null) {
				address = "인천 부평구 시장로 7, 학원빌딩";//(임시주소)
			}else {
				address = memberService.getAddress((String)session.getAttribute("loginId"));
			}
			getXY = campInfoService.getKakaoApiFromAddress(address);
			
		}//주어진 정보가 없고 세션이 null이 아니면
		else {
			getXY.put("lat", (Double) session.getAttribute("sessionLat"));
			getXY.put("lon", (Double) session.getAttribute("sessionLon"));
		}
		
		//세션에 위치 바인딩
		session.setAttribute("sessionLat", getXY.get("lat"));
		session.setAttribute("sessionLon", getXY.get("lon"));
		/////////////////////////////////////////////////////////
		//가까운 캠핑장 목록
		List<CampInfoVO> list  = 
				campInfoService.getNearCampingArea(getXY);
		//현재 위치 바인딩
		model.addAttribute("lat", getXY.get("lat"));
		model.addAttribute("lon", getXY.get("lon"));
		
		
		
		//페이징 처리 -- > 우선 데이터가 한번 필터링 되어있기 때문에 걸러진 데이터를 전부 가져온 상태에서 페이징할 숫자를 가져온것. 
		// 쿼리를 조금더 효과적으로 사용하게 된다면 SQL쿼리를 날릴때 페이징될 순서로 가져오는 것이 좋을 수 있다. 지금은 데이터가 적기 때문에 가능한 일
		
		
		int last = (page * 10) > list.size() ? list.size() : (page * 10);
		int maxPage = (list.size() + 9) / 10; 
		
	    list = list.subList(10 * (page - 1), last);
	    
	    Collections.sort(list,new Comparator<CampInfoVO>() {

			@Override
			public int compare(CampInfoVO o1, CampInfoVO o2) {
					
				return o1.getDistance()-o2.getDistance() > 0 ? 1 : -1;
			}
			
		});
		for(CampInfoVO camp : list) {
			System.out.println(camp.getDistance());
		}
	    
	    
	    model.addAttribute("camplist", list);
	    model.addAttribute("maxPage", maxPage);
	    model.addAttribute("nowPage", page);
		
		return "campingArea/areaMain";
	}
	
	
	//가까운 캠핑장 10개 불러오기
	@RequestMapping("movePosition")
	@ResponseBody
	public List<CampInfoVO> movePosition(Double lat,Double lon){
			Map<String,Double> getXY = new HashMap<String, Double>();
			
			getXY.put("lat", lat);
			getXY.put("lon", lon);
			List<CampInfoVO> list  = 
					campInfoService.getNearCampingArea(getXY);
			list = list.subList(0, 10);
		return list;
	}

	
	@RequestMapping("/moveTest")
	public String moveTest(Model model,HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		Map<String,Double> getXY = new HashMap<String, Double>();
		getXY.put("lat", (Double) session.getAttribute("sessionLat"));
		getXY.put("lon", (Double) session.getAttribute("sessionLon"));
		
		List<CampInfoVO> list  = 
				campInfoService.getNearCampingArea(getXY);
		list = list.subList(0, 10);
		
		
		return "campingArea/moveLocation";
	}
	
}
