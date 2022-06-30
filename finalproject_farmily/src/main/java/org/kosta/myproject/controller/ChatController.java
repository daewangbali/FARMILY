package org.kosta.myproject.controller;

import org.kosta.myproject.vo.MemberVO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ChatController {
	/*
	 * ChatController를 통해 chat index(채팅방)에 접속
	 */
	@RequestMapping("chat")
	public String chat(@AuthenticationPrincipal MemberVO memberVO, Model model) {
		model.addAttribute("name", memberVO.getName());
		return "chat/chatroom";
	}
}
