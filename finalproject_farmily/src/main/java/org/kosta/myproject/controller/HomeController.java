package org.kosta.myproject.controller;

import org.kosta.myproject.vo.MemberVO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {
	
	@RequestMapping(value = {"/home","/"})
	public String home(Authentication authentication, Model model){//Authentication : Spring Security의 인증객체 
		//Spring Security Authentication 인증객체는 아래처럼 SecurityContext 에 저장되어 있다 
		//log.info("home "+SecurityContextHolder.getContext().getAuthentication().getPrincipal());	
		//Principal(사전적 의미:본인 ) 객체는 인증된 회원 정보 객체를 말한다
		//org.kosta.myproject.config.security.MemberAuthenticationProvider 에서 할당 
		if(authentication!=null) 
		  log.info("Home: 인증받은 사용자 {} ",authentication.getPrincipal());
	
		else
			log.info("Home: 인증받지 않은 사용자");	
		model.addAttribute("message", "SpringBoot Security Thymeleaf");
		return "index";
	}
	/*	
		로그인 하였으나 권한이 없는 요청을 하였을 경우 보여지는 페이지를 지정  
		org.kosta.myproject.config.security.WebSecurityConfig 에서 아래와 같이 설정되어 있음 
		http.exceptionHandling().accessDeniedPage("/accessDeniedView");	
		일반 회원(ROLE_MEMBER)로 로그인한 후 
		관리자 서비스 AdminController에 접근해서 테스트 해본다 
		http://localhost:7777/admin/main
	 */
	@RequestMapping("accessDeniedView")
	public String accessDeniedView() {
		return "auth/accessDeniedView";
	}
}
