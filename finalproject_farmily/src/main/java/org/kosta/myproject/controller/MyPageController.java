package org.kosta.myproject.controller;

import org.kosta.myproject.service.MemberService;
import org.kosta.myproject.service.MyPageService;
import org.kosta.myproject.vo.MemberVO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MyPageController {
	private final MyPageService mypageService;
	private final MemberService memberService;
	private final BCryptPasswordEncoder passwordEncoder;
	
	@RequestMapping("mypage/updateMemberForm")
	public String updateMemberForm(@AuthenticationPrincipal MemberVO memberVO, Model model) {
		model.addAttribute("memberVO", memberVO);
		return "mypage/updateMemberForm";
	}
	
	@PostMapping("mypage/updateMember")
	public String updateMember(@AuthenticationPrincipal MemberVO mvo, String memberPassword, String memberName, int memberTel, String memberRegion) {
		String encodedPwd = passwordEncoder.encode(memberPassword);
		MemberVO memberVO = new MemberVO(mvo.getId(), encodedPwd, memberName, memberTel, memberRegion, 1);
		mypageService.updateMember(memberVO);
		
		return "redirect:/mypage/updateResultView?id="+mvo.getId();
	}
	
	@RequestMapping("mypage/updateResultView")
	public ModelAndView registerResultView(String id) {
		MemberVO memberVO = memberService.findMemberById(id);
		return new ModelAndView("mypage/updateResult", "memberVO", memberVO);
	}
	
	@RequestMapping("mypage/deleteMember")
	public void deleteMember(@AuthenticationPrincipal MemberVO memberVO) {
		System.out.println(memberVO.getId());
	}
}
