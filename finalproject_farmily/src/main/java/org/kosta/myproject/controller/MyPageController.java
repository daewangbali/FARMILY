package org.kosta.myproject.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.kosta.myproject.service.MemberService;
import org.kosta.myproject.service.MyPageService;
import org.kosta.myproject.vo.MemberVO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MyPageController {
	private final MyPageService mypageService;
	private final MemberService memberService;
	private final BCryptPasswordEncoder passwordEncoder;
	
	@RequestMapping("mypage")
	public String mypage(@AuthenticationPrincipal MemberVO memberVO, Model model) {
		model.addAttribute("memberVO", memberVO);
		return "mypage/mypageindex";
	}
	
	@RequestMapping("mypage/updateMemberForm")
	public String updateMemberForm(@AuthenticationPrincipal MemberVO memberVO, Model model) {
		model.addAttribute("memberVO", memberVO);
		return "mypage/updateMemberForm";
	}
	
	@PostMapping("mypage/updateMember")
	public String updateMember(@AuthenticationPrincipal MemberVO mvo, String memberPassword, String memberName, int memberTel, String memberRegion, String filename, String filepath, Model model, MultipartFile file) {
		String encodedPwd = passwordEncoder.encode(memberPassword);
		MemberVO memberVO = new MemberVO(mvo.getId(), encodedPwd, memberName, memberTel, memberRegion, 1, filename, filepath);
		mypageService.updateMember(memberVO);
		
		return "redirect:/mypage/updateResultView?id="+mvo.getId();
	}
	
	@RequestMapping("mypage/updateResultView")
	public ModelAndView registerResultView(String id) {
		MemberVO memberVO = memberService.findMemberById(id);
		return new ModelAndView("mypage/updateResult", "memberVO", memberVO);
	}
	
	@RequestMapping("mypage/deleteMemberForm")
	public String deleteMemberForm(@AuthenticationPrincipal MemberVO memberVO) {
		return "mypage/deleteMemberForm";
	}
	
	@PostMapping("mypage/deleteCheck")
	@ResponseBody
	public String deleteMemberForm(@AuthenticationPrincipal MemberVO memberVO, String memberId, String deleteCheck) {
		boolean isCheched = mypageService.deleteCheck(memberVO, memberId, deleteCheck);
		if(isCheched)
			return "ok";
		else
			return "fail";
	}
	
	@PostMapping("mypage/deleteMember")
	@Transactional
	public String deleteMember(String memberId, HttpServletRequest request) {
		mypageService.deleteMember(memberId);
		mypageService.deleteRole(memberId);
		
		HttpSession session = request.getSession(false);
		session.invalidate();
		SecurityContextHolder.getContext().setAuthentication(null);
		
		return "redirect:/";
	}
}
