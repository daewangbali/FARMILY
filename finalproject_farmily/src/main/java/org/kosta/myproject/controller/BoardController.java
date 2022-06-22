package org.kosta.myproject.controller;

import java.util.List;

import org.kosta.myproject.service.BoardService;
import org.kosta.myproject.vo.BoardVO;
import org.kosta.myproject.vo.MemberVO;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BoardController {
	private final BoardService boardService;
		
	@RequestMapping("guest/boardListByBoardCategori")
	public String findBoardListByBoardCategori(String boardCategori,Model model) {
		List<BoardVO> list = boardService.findBoardListByBoardCategori(boardCategori);
		model.addAttribute("boardList",list);
		return "board/board-categori-list";
	}
	@RequestMapping("guest/boardListBySelectCategori")
	public String boardListBySelectCategori(String selectCategori,Model model) {
		List<BoardVO> list = boardService.findBoardListBySelectCategori(selectCategori);
		model.addAttribute("boardList",list);
		return "board/board-categori-list";
	}
	@RequestMapping("guest/boardListByRegion")
	public String findBoardListByRegion(String region,Model model) {
		List<BoardVO> list = boardService.findBoardListByRegion(region);
		model.addAttribute("boardList", list);
		return "board/board-categori-list";
	}
	
	@GetMapping("registerPostForm")
	public String registerPostForm(String boardCategori) {
		return "board/registerPostForm";
	}
	
	@PostMapping("registerPost")
	public String registerPost(@AuthenticationPrincipal MemberVO membervo,BoardVO boardVO,String boardCategori) {
		boardVO.setId(membervo.getId());
		boardService.registerBoard(boardVO);
		return "redirect:/guest/boardListByBoardCategori?boardCategori=" + boardCategori;
	}

	
	
}
