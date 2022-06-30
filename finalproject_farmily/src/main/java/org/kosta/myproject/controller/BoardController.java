package org.kosta.myproject.controller;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.kosta.myproject.service.BoardService;
import org.kosta.myproject.vo.BoardVO;
import org.kosta.myproject.vo.JjimVO;
import org.kosta.myproject.vo.MemberVO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BoardController {
	private final BoardService boardService;

	@RequestMapping("guest/boardListByBoardCategori")
	public String findBoardListByBoardCategori(String boardCategori, Model model) {
		List<BoardVO> list = boardService.findBoardListByBoardCategori(boardCategori);
		model.addAttribute("boardList", list);
		return "board/board-categori-list";
	}

	@RequestMapping("guest/boardListBySelectCategori")
	public String boardListBySelectCategori(String selectCategori, Model model) {
		List<BoardVO> list = boardService.findBoardListBySelectCategori(selectCategori);
		model.addAttribute("boardList", list);
		return "board/board-categori-list";
	}

	@RequestMapping("guest/boardListByRegion")
	public String findBoardListByRegion(String region, Model model) {
		List<BoardVO> list = boardService.findBoardListByRegion(region);
		model.addAttribute("boardList", list);
		return "board/board-categori-list";
	}

	@GetMapping("registerPostForm")
	public String registerPostForm(String boardCategori) {
		return "board/registerPostForm";
	}

	@PostMapping("registerPost")
	public String registerPost(@AuthenticationPrincipal MemberVO membervo, BoardVO bvo, String boardCategori,
			Model model, MultipartFile file) throws Exception {
		bvo.setId(membervo.getId());

		if (file.isEmpty()) {
			bvo.setFilename("");
			bvo.setFilepath("");

		} else {
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
	
	@GetMapping("deletePost")
	public String deletePost(String boardNo, Model model) {
		boardService.deletePost(boardNo);
		model.addAttribute("message", "게시글이 삭제되었습니다.");
		model.addAttribute("searchUrl", "http://localhost:7777/#");
		return "delete-message";

	}
	@RequestMapping("boardView")
	public String boardView(@AuthenticationPrincipal MemberVO membervo,String boardNo, Model model) throws Exception {
		BoardVO boardVO = boardService.boardView(boardNo);
		JjimVO jjimVO = new JjimVO();
		jjimVO.setBoardNo(boardVO.getBoardNo());
		jjimVO.setId(membervo.getId());
		String jjimCheck = boardService.findJjim(jjimVO);
		model.addAttribute("jjimCheck",jjimCheck);
		model.addAttribute("boardVO", boardVO);
		return "board/boardView";
	}

	@RequestMapping("mypage")
	public String mypage() {
		return "mypage/index";
	}
	
	@RequestMapping("guest/boardFarmingListByBoardCategori")
	public String findBoardFarmingListByBoardCategori(String farmingCategori, Model model) throws Exception {
		List<BoardVO> list = boardService.findBoardFarmingListByBoardCategori(farmingCategori);
		model.addAttribute("boardFarmingList",list);
		return "board/boardFarmingList";
	}
	
	@RequestMapping("mypage/findMyPostListById")
	public String findMyPostById(@AuthenticationPrincipal MemberVO membervo, Model model) {
		List<BoardVO> list = boardService.findMyPostListById(membervo.getId());
		model.addAttribute("boardList", list);
		return "mypage/findMyPostListById";
	}
	
	@GetMapping("updatePostForm")
	public String updatePostForm( String boardNo, Model model) {
		BoardVO boardVO = boardService.boardView(boardNo);
		model.addAttribute("boardVO", boardVO);
		return "board/updatePostForm";
	}
	
	@PostMapping("updatePost")
	public String updatePost(@AuthenticationPrincipal MemberVO membervo, BoardVO boardVO, String boardCategori,
			Model model, MultipartFile file, int boardNo) throws Exception {
		boardVO.setBoardNo(boardNo);
		boardService.updateBoard(boardVO);//이 boardVO안에는 title, content, boardNo만 들어있음
		return "redirect:/guest/boardListByBoardCategori?boardCategori=" + boardCategori;
	}
	@PostMapping("registerJjim")
	public String registerJjim(@AuthenticationPrincipal MemberVO membervo, BoardVO bvo, String boardCategori,
			Model model) {
		bvo.setId(membervo.getId());
		bvo.setBoardNo(bvo.getBoardNo());
		JjimVO jjimVO=new JjimVO();
		jjimVO.setBoardNo(bvo.getBoardNo());
		jjimVO.setId(membervo.getId());
		
		return boardCategori;
	}	
	@GetMapping("changeJjim")
	@ResponseBody
	public String changeJjim(@AuthenticationPrincipal MemberVO membervo,String jjimCheck,int boardNo,Model model) {
		JjimVO jjimVO = new JjimVO();
		jjimVO.setBoardNo(boardNo);
		jjimVO.setId(membervo.getId());
		if(jjimCheck.equals("0")) {
			boardService.registerJjim(jjimVO);
		}else {
			boardService.deleteJjim(jjimVO);
		}
		jjimCheck = boardService.findJjim(jjimVO);
		model.addAttribute("jjimCheck",jjimCheck);
		return jjimCheck;
	}

}