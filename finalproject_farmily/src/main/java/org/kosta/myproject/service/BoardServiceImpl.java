package org.kosta.myproject.service;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.kosta.myproject.mapper.BoardMapper;
import org.kosta.myproject.vo.BoardVO;
import org.kosta.myproject.vo.FileVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
	@Autowired
	private final BoardMapper boardMapper;
	//게시물 작성
	@Override
	public void registerBoard(BoardVO bvo, FileVO fileVO, MultipartFile file) throws Exception {
		String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";
		UUID uuid = UUID.randomUUID();
		String fileName = uuid + "_" + file.getOriginalFilename();
				
		File saveFile = new File(projectPath, fileName);
		file.transferTo(saveFile);
		
		fileVO.setFilename(fileName);
		fileVO.setFilepath("/files/" + fileName);
		
		boardMapper.registerBoard(bvo);
	}
	//전체 게시물 리스트
	@Override
	public List<BoardVO> findAllBoardList() {
		return boardMapper.findAllBoardList();
	}
	//지역별 게시물 리스트
	@Override
	public List<BoardVO> findBoardListByRegion(String region) {
		return boardMapper.findBoardListByRegion(region);
	}
	//게시판 카테고리 리스트(농활 or 장터)
	@Override
	public List<BoardVO> findBoardListByBoardCategori(String boardCategori) {
		return boardMapper.findBoardListByBoardCategori(boardCategori);
	}
	//게시물 카테고리 리스트(알바 or 체험 or 중고거래 or 자유)
	@Override
	public List<BoardVO> findBoardListBySelectCategori(String selectCategori) {
		return boardMapper.findBoardListBySelectCategori(selectCategori);
	}

}
