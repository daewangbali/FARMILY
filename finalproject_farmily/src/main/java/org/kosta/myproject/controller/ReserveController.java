package org.kosta.myproject.controller;

import java.io.File;
import java.util.UUID;

import org.kosta.myproject.service.BoardService;
import org.kosta.myproject.service.ReserveService;
import org.kosta.myproject.vo.BoardVO;
import org.kosta.myproject.vo.MemberVO;
import org.kosta.myproject.vo.ReservationVO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReserveController {
	private final BoardService boardService;
	private final ReserveService reserveService;
	
	@PostMapping("registerFarmPost")
	public String registerFarmPost(@AuthenticationPrincipal MemberVO membervo,BoardVO boardVO,String boardCategori,String[] dateArray,  MultipartFile file ) throws Exception {
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
				
				int boardNo = reserveService.getBoardNo(membervo.getId());
				ReservationVO reservationVO = new ReservationVO();
				reservationVO.setReservationDate(dateArray[i]);
				reservationVO.setBoardNo(boardNo);
				reserveService.availableReservation(reservationVO);	
			}else {
				continue;
			}
		}
		return "redirect:/guest/boardListByBoardCategori?boardCategori=" + boardCategori;
	}
}