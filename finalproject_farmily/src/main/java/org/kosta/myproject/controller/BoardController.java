package org.kosta.myproject.controller;

import java.util.List;

import org.kosta.myproject.service.BoardService;
import org.kosta.myproject.vo.BoardVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	
	
}
