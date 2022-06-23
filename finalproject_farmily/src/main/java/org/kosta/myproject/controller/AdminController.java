package org.kosta.myproject.controller;

import java.util.List;

import org.kosta.myproject.service.MemberService;
import org.kosta.myproject.vo.MemberVO;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AdminController {
	private final MemberService memberService;
	/* 
	 * @Secure 어노테이션: 인가 처리하는 어노테이션 - 권한이 부여된 사람들만 서비스를 제공받도록 설정한다 
	 * @Secured("ROLE_ADMIN") -> ROLE_ADMIN 권한이 있는 접속자만 서비스를 받을 수 있다 
										 인가가 되지 않은 경우에는 WebSecurityConfig에서 설정한 대로 
										 accessDeniedPage("/accessDeniedView") 로 이동한다 
		
		로그인 하였으나 권한이 없는 요청을 하였을 경우 보여지는 페이지를 지정  
		org.kosta.myproject.config.security.WebSecurityConfig 에서 아래와 같이 설정되어 있음 
		http.exceptionHandling().accessDeniedPage("/accessDeniedView");	
		일반 회원(ROLE_MEMBER)로 로그인한 후 
		관리자 서비스 AdminController에 접근해서 테스트 해본다 
		http://localhost:7777/admin/main			
		
		accessDeniedView 화면처리 컨트롤러 메서드는 HomeController에 있음 
		
		king id로 회원 가입 후 sql 상에서 ROLE_ADMIN 으로 권한을 추가한 후 
		로그인해서 아래 관리자 서비스를 확인한다 					 
	 */
	@Secured("ROLE_MASTER")
	@RequestMapping("admin/grantAdmin")
	public ModelAndView adminMain() {
		// 구현내용 
		List<MemberVO> waitingMemberVOList = memberService.findAllWaitingMember();
		return new ModelAndView("admin/grantAdmin","waitingMemberVOList", waitingMemberVOList);
	}
	
	@Secured("ROLE_MASTER")
	@PostMapping("admin/grantAdminMember")
	@Transactional
	@ResponseBody
	public String grantAdminMember(String id) {
		memberService.grantAdminMember(id);
		memberService.deleteWaitingMember(id);
		return "redirect:admin/grantAdmin";
	}
}
