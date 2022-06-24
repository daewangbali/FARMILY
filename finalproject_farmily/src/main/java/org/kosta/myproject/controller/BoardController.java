package org.kosta.myproject.controller;

import java.util.List;

import org.kosta.myproject.service.BoardService;
import org.kosta.myproject.vo.BoardVO;
import org.kosta.myproject.vo.FileVO;
import org.kosta.myproject.vo.MemberVO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

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
	public String registerPost(@AuthenticationPrincipal MemberVO membervo,BoardVO boardVO, FileVO fileVO, String boardCategori, Model model, MultipartFile file) throws Exception{
		boardVO.setId(membervo.getId());
		boardService.registerBoard(boardVO, fileVO, file);
		return "redirect:/guest/boardListByBoardCategori?boardCategori=" + boardCategori;
	}
	
	@RequestMapping("boardView")
	public String boardView(@AuthenticationPrincipal MemberVO membervo,BoardVO boardVO, FileVO fileVO, String boardCategori, Model model, MultipartFile file) throws Exception {
		return "board/boardView";
	}
	@RequestMapping("mypage")
	public String mypage() {
		return "member/mypage";
	}
	
	@RequestMapping("findMyPostListById")
	public String findMyPostById(@AuthenticationPrincipal MemberVO membervo,Model model) {
		List<BoardVO> list = boardService.findMyPostListById(membervo.getId());
		model.addAttribute("boardList", list);
		return "member/findMyPostListById";
	}
	
	
}
