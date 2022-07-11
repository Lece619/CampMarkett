package com.izo.camp.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.izo.camp.addmoney.AddmoneyService;
import com.izo.camp.member.MemberService;
import com.izo.camp.vo.AddmoneyVO;
import com.izo.camp.vo.MemberVO;

@Controller
public class MemberController {

	@Autowired
	MemberService memberService;
	
	@Autowired
	AddmoneyService addmoneyService;
	
	@Autowired
	HttpSession session;
	
	//가입여부 확인 페이지 ->약관동의 페이지
	@RequestMapping("/term.do")
	public String term(MemberVO vo) {			
		String name = vo.getName();
		String email = vo.getEmail();
		session.setAttribute("wannaName", name);
		session.setAttribute("wannaEmail", email);
		//로그인 필요
		return "join/term";
	}
	
	//가입확인 페이지로 이동
	@RequestMapping("/memberCheck.do")
	public String memberCheck() {
		return "join/memberCheck";
	}
		
	//약관 페이지 -> 회원가입 페이지
	@RequestMapping("/joinView.do")
	public String joinView(MemberVO vo, Model model) {
		model.addAttribute("vo", vo);
		return "join/joinView";
	}	
	
	//주소찾기 이동
	@RequestMapping("/jusoPopup.do")
	public String jusoPopup() {
		return "join/jusoPopup";
	}
	
	//아이디중복확인
	@RequestMapping("checkID.do")
	@ResponseBody
	public String checkId(@ModelAttribute("id") String id) {		
		int idx = memberService.idIdx(id);		
		System.out.println(idx);		
		String param = "n";		
		if(idx > 0) {
			param = "y";
		}		
		String result = String.format("[{'param':'%s'}]", param);				
		return result;
	}
	
	//회원가입하기
	@ResponseBody
	@RequestMapping("/join.do")
	public String join(MemberVO vo) {
		int cnt = memberService.insertInfo(vo);
		System.out.println(cnt);
		String param = "n";
		if(cnt > 0) {
			param = "y";
		}
		String result = String.format("[{'param':'%s'}]", param);
		return result;
	}
	
	//회원가입 유무 확인
	@ResponseBody
	@RequestMapping("/memberOrNot.do")
	public String search(MemberVO vo) {		
		int idx = memberService.getMemberIdx(vo);		
		String param = "n";		
		if(idx > 0) {
			param = "y";
		}		
		String result = String.format("[{'param':'%s'}]", param);		
		return result;
	}
	
	
	//로그인 메인 이동
	@RequestMapping(value="/login_Temp")
	public String loginView() {
		return "login/loginView";
	}
		
	//로그인하기
	@ResponseBody
	//ajax로 post형식 데이터 주고 받을 때 produces = "application/text; charset=UTF-8", method=RequestMethod.POST 붙이기!
	@RequestMapping(value="/goLogin.do", produces = "application/text; charset=UTF-8", method=RequestMethod.POST)
	public String goLogin(MemberVO vo) {		
		int idx = memberService.getIdxFromId(vo);		
		int param = 0;	
		String name = "none";
		String id = "none";
		if(idx > 0) {
			param = idx;
			MemberVO vo1 = memberService.userInfo(idx);
			name = vo1.getName();
			id = vo1.getId();
			//로그인 성공하면 세션에 바로 묶어서 결과 보내주고 사용할 페이지에서 ${sessionScope.key값} 으로 사용하기
			//바로 사용하는 게 아니라 컨트롤러에서 활용할 용도라면 session.getAttribute()
			session.setAttribute("loginId", id);
			session.setAttribute("loginIdx", idx);
		}
		System.out.println("로그인버튼 클릭시 접속자 이름: " +name);
		String result = String.format("[{'param':'%d'}, {'name':'%s'}, {'id':'%s'}]", param, name, id);		
		return result;
	}
	
	//아이디찾기 페이지로 이동
	@RequestMapping("/findMyID.do")
	public String findMyID() {
		return "login/findMyID";
	}
	
	//아이디 찾기 팝업창으로 이동
	@RequestMapping("/searchIDView.do")
	public String searchIDView() {
		return "login/findMyID";
	}
	
	//아이디찾기
	@ResponseBody
	@RequestMapping("/searchID.do")
	public String searchID(MemberVO vo) {
		String result = String.format("[{'id':'%s'}]", memberService.searchID(vo)); 
		return result;		
	}
	
	//비밀번호 찾기 팝업창으로 이동
	@RequestMapping("/searchPwdView.do")
	public String searchPwdView() {
		return "login/findMyPWD";
	}
	
	//비밀번호 찾기
	@ResponseBody
	@RequestMapping("/searchPwd.do")
	public String searchPwd(MemberVO vo) {
		int idx1 = memberService.idIdx(vo.getId());
		int idx2 = memberService.getMemberIdx(vo);
		System.out.println("idx1 : "+idx1+"/ idx2 : "+idx2);
		int idx = 0;
		if(idx1 == idx2) {
			if(idx1!=0 ||idx2!=0) {
				idx = idx1;
			}else {
				idx = 0;
			}
		}else {
			idx = 0;
		}
		System.out.println(idx);
		String result = String.format("[{'idx':'%d'}]", idx);
		return result;
	}
	
	//비밀번호 변경하기
	@ResponseBody
	@RequestMapping("/changePwd.do")
	public String changePwd(MemberVO vo) {
		int cnt = memberService.changePwd(vo);
		System.out.println("컨트롤러 결과 : "+cnt);
		String param = "n";
		if(cnt > 0) {
			param = "y";
		}
		String result = String.format("[{'param':'%s'}]", param);
		return result;
	}
	
	//test
	@RequestMapping("/test")
	public String test() {
		return "login/popup";
	}
	
	//로그아웃
	@RequestMapping(value="/logout", method = {RequestMethod.POST, RequestMethod.GET})
	public String logout() {
		session.invalidate();
		System.out.println("세션 종료");
		return "home_real";
	}
	
	//내정보보기 페이지로 이동
	@RequestMapping(value="/myInfo.do", method={RequestMethod.POST, RequestMethod.GET})
	public String myInfo(Model model) {
		int idx = (Integer)session.getAttribute("loginIdx");
		String id = (String)session.getAttribute("loginId"); 

		MemberVO vo = memberService.userInfo(idx);
		
		int totalmoney = 0;
		
		//정보 조회에 필요한 카드정보
		//등록된 카드가 없으면 현재잔액에 0으로 세팅하기
		int cardCnt = addmoneyService.getNumber(id);//아이디 갯수 반환
		if(cardCnt>=1) {
			//조회 결과 중 가장 최근 정보 가져오기
			AddmoneyVO vo1 = addmoneyService.getMoneyInfo(id);
			totalmoney = vo1.getTotalmoney();
		}else {
			totalmoney = addmoneyService.searchId(id);
		}
		
		model.addAttribute("vo", vo);
		model.addAttribute("totalmoney", totalmoney);
		return "mypage/myInfo";
	}
	
	//이메일 변경하기
	@ResponseBody
	@RequestMapping(value="/changeEmail.do", method={RequestMethod.POST, RequestMethod.GET})
	public String changeEmail(MemberVO vo) {
		int res = memberService.changeEmail(vo);
		String param = "n";
		if(res>0) {
			param = "y";
		}
		
		String result = String.format("[{'param':'%s'}]", param);
		return result;
	}
	
	//휴대전화번호 변경하기
	@ResponseBody
	@RequestMapping(value="/changeMobileTel.do", method= {RequestMethod.POST, RequestMethod.GET})
	public String changeMobileTel(MemberVO vo) {
		int res = memberService.changeMobileTel(vo);
		String param = "n";
		if(res>0) {
			param = "y";
		}
		String result = String.format("[{'param':'%s'}]", param);
		return result;
	}
}
