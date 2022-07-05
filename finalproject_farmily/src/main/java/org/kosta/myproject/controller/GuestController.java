package org.kosta.myproject.controller;

import java.io.File;
import java.util.UUID;

import org.kosta.myproject.service.MemberService;
import org.kosta.myproject.vo.MemberVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class GuestController {
	private final MemberService memberService;
	
	//register member
	@RequestMapping("guest/registerForm")
	public String registerForm() {
		return "guest/registerForm";
	}

	@PostMapping("guest/registerMember")
	public String register(MemberVO memberVO, String memberRole, Model model, MultipartFile file)throws Exception {
		
		// 등록시 service에서 비밀번호를 암호화 한다
		if(memberRole.equals("ROLE_ADMIN")) {
			memberService.registerAdminMember(memberVO);
		}else {
			if (file.isEmpty()) {
				memberVO.setFilename("farmer1");
				memberVO.setFilepath("/assets/img/farmer1.png");

			} else {
				String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\profilephoto";
				UUID uuid = UUID.randomUUID();
				String fileName = uuid + "_" + file.getOriginalFilename();
				File saveFile = new File(projectPath, fileName);
				file.transferTo(saveFile);
				memberVO.setFilename(fileName);
				memberVO.setFilepath("/profilephoto/" + fileName);
			
			}
			
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
