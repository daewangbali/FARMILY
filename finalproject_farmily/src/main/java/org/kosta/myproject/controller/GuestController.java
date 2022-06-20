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
	public String register(MemberVO memberVO) {
		memberService.registerMember(memberVO);// 등록시 service에서 비밀번호를 암호화 한다
		return "redirect:/guest/registerResultView?id=" + memberVO.getId();
	}

	@RequestMapping("guest/registerResultView")
	public ModelAndView registerResultView(String id) {
		MemberVO memberVO = memberService.findMemberById(id);
		return new ModelAndView("guest/register_result", "memberVO", memberVO);
	}

	@RequestMapping("guest/idcheckAjax")
	@ResponseBody
	public String idcheckAjax(String id) {
		return memberService.idcheck(id);
	}
}
