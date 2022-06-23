package org.kosta.myproject.controller;

import org.kosta.myproject.service.MemberService;
import org.kosta.myproject.vo.MemberVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class GuestController {
	private final MemberService memberService;
	
	//register member
	@RequestMapping("guest/registerForm")
	public String registerForm() {
		return "guest/registerForm";
	}

	@PostMapping("guest/registerMember")
	public String register(String memberRole, String memberId, String memberPassword, String memberName, int memberTel, String memberRegion) {
		MemberVO memberVO = new MemberVO(memberId, memberPassword, memberName, memberTel, memberRegion, 1);
		// 등록시 service에서 비밀번호를 암호화 한다
		if(memberRole.equals("ROLE_ADMIN")) {
			memberService.registerAdminMember(memberVO);
		}else {
			memberService.registerMember(memberVO);
		}
		return "redirect:/guest/registerResultView?id=" + memberVO.getId();
	}

	@RequestMapping("guest/registerResultView")
	public ModelAndView registerResultView(String id) {
		MemberVO memberVO = memberService.findMemberById(id);
		return new ModelAndView("guest/registerResult", "memberVO", memberVO);
	}

	@RequestMapping("guest/idcheckAjax")
	@ResponseBody
	public String idcheckAjax(String id) {
		return memberService.idcheck(id);
	}
	
	@RequestMapping("guest/loginForm")
	public String loginForm() {
		return "guest/loginForm";
	}
	
	//WebSecurityConfig에 등록되어 있음 ( failureUrl("/login_fail") )
	@RequestMapping("guest/loginFail")
	public String loginFail() {
		return "guest/loginFail";
	}
}
