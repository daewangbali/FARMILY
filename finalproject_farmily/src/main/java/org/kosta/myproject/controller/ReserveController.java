package org.kosta.myproject.controller;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.kosta.myproject.service.BoardService;
import org.kosta.myproject.service.ReserveService;
import org.kosta.myproject.vo.BoardVO;
import org.kosta.myproject.vo.MemberVO;
import org.kosta.myproject.vo.ReservationVO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReserveController {
	private final BoardService boardService;
	private final ReserveService reserveService;
	
	@PostMapping("registerFarmPost")
	public String registerFarmPost(@AuthenticationPrincipal MemberVO membervo,BoardVO boardVO,String boardCategori,String[] dateArray, 
			MultipartFile file,RedirectAttributes redirect ) throws Exception {
		boardVO.setId(membervo.getId());
		
		if (file.isEmpty()) {
			boardVO.setFilename("");
			boardVO.setFilepath("");

		} else {
			String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";
			UUID uuid = UUID.randomUUID();
			String fileName = uuid + "_" + file.getOriginalFilename();

			File saveFile = new File(projectPath, fileName);

			file.transferTo(saveFile);

			boardVO.setFilename(fileName);
			boardVO.setFilepath("/files/" + fileName);
		}
		boardService.registerBoard(boardVO);
		
		for(int i=0;i<dateArray.length;i++) {
			if(dateArray[i].length()>1) {
				
				String boardNo = reserveService.getBoardNo(membervo.getId());
				ReservationVO reservationVO = new ReservationVO();
				reservationVO.setReservationDate(dateArray[i]);
				reservationVO.setBoardNo(boardNo);
				reserveService.availableReservation(reservationVO);	
			}else {
				continue;
			}
		}
		redirect.addAttribute("boardCategori", boardCategori);
		return "redirect:/guest/boardListByBoardCategori";
	}
	
	@GetMapping("reservationForm")
	public String reservationForm(String boardNo, Model model) {
		List<ReservationVO> rdateList = reserveService.findReservateDate(boardNo);
		BoardVO boardVO = boardService.boardView(boardNo);
		model.addAttribute("rdateList",rdateList);
		model.addAttribute("boardVO",boardVO);
		return "reserve/reservationForm";
	}
	
	@PostMapping("registerReservation")
	public String registerReservation(@AuthenticationPrincipal MemberVO membervo,String[] myCheckList,String boardNo, Model model) {
		BoardVO boardVO = boardService.boardView(boardNo);
		ReservationVO rvo = new ReservationVO();
		rvo.setBoardNo(boardNo);
		rvo.setId(membervo.getId());
		for(int i=0;i<myCheckList.length;i++) {
			rvo.setReservationDate(myCheckList[i]);
			reserveService.registerReservation(rvo);
		}
		model.addAttribute("myCheckList",myCheckList);
		model.addAttribute("boardVO",boardVO);
		model.addAttribute("memberVO",membervo);
		return "reserve/reservation-ok";
	}
}