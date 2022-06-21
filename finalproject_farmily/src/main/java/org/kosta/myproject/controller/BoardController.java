package org.kosta.myproject.controller;

import org.kosta.myproject.service.BoardService;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BoardController {
	private final BoardService boardService;
	
	
}
