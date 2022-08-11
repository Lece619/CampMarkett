package com.izo.camp.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.izo.camp.infomation.CampInfoService;
import com.izo.camp.mapper.DeptMapper;
import com.izo.camp.member.MemberService;
import com.izo.camp.vo.DeptVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	CampInfoService campInfoService;
	@Autowired
	DeptMapper deptMapper;
	
	//Session을 사용할때 필드주입이 가능한가 ? (프로젝트시 의문 )
	/* 의문점 해결
	Session을 자동주입 받는다고 싱글톤으로 session이 생성되거나 하지 않는다. 
	스프링 컨테이너가 동적 생성 프록시로 Session 인스턴스를 생성하고 사용하는데 이때 스코프가 Session이기 때문에 웹요청시마다 생성된다.
	*/
	@Autowired
	private HttpSession session;

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
				
		model.addAttribute("serverTime", formattedDate );

		campInfoService.hasCamping();
		
		return "home_real";

	}
	
	
	@Autowired
	MemberService memberService;
	@RequestMapping(value = "/join_view.do")
	public String join_view(Model model) {
		model.addAttribute("list", memberService.list());
		return "join/join_view";
	}
	//테스트	
	
	//테스트2
	@RequestMapping(value = "/home2", method = RequestMethod.GET)
	public String home2go() {
		session.setAttribute("sessionLat", 37.49051);
		session.setAttribute("sessionLon", 126.72414);
		return "home2";
	}
	
	//테스트 Home 페이지로 이동
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String homeGo() {
		campInfoService.hasCamping();
		return "home_real";
	}
	
	@RequestMapping("/makeSampleId")
	public String makeSampleLogin() {
		
		session.setAttribute("loginId", "SampleID");
		session.setAttribute("loginIdx", 1);
		System.out.println("샘플 로그인이 완료되었습니다. ");
		return "home_real";
	}
}
