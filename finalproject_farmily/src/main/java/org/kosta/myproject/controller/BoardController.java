package org.kosta.myproject.controller;

import java.io.File;
import java.util.List;
import java.util.UUID;

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
	public String registerPost(@AuthenticationPrincipal MemberVO membervo,BoardVO bvo, String boardCategori, Model model, MultipartFile file) throws Exception{
		bvo.setId(membervo.getId());
		
		if(file.isEmpty()) {
			bvo.setFilename("");
			bvo.setFilepath("");
		
		}else {
			String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";
			UUID uuid = UUID.randomUUID();
			String fileName = uuid + "_" + file.getOriginalFilename();
					
			File saveFile = new File(projectPath, fileName);
			
			file.transferTo(saveFile);
		
			bvo.setFilename(fileName);
			bvo.setFilepath("/files/" + fileName);
		}
		boardService.registerBoard(bvo);
		return "redirect:/guest/boardListByBoardCategori?boardCategori=" + boardCategori;
	}
	
	@RequestMapping("boardView")
	public String boardView(String boardNo, Model model) throws Exception {
		BoardVO boardVO =  boardService.boardView(boardNo);
		model.addAttribute("boardVO",boardVO);
		return "board/boardView";
	}
	
	

	
	
}
