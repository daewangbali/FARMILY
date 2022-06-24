package org.kosta.myproject.service;

import java.util.List;

import org.kosta.myproject.vo.BoardVO;
import org.kosta.myproject.vo.FileVO;
import org.springframework.web.multipart.MultipartFile;

public interface BoardService {
	//게시물 작성
	void registerBoard(BoardVO bvo, FileVO fileVO, MultipartFile file) throws Exception;
		
	//전체 게시물 리스트
	List<BoardVO> findAllBoardList();
		
	//지역별 게시물 리스트
	List<BoardVO> findBoardListByRegion(String region);
		
	//게시판 카테고리 리스트(농활 or 장터)
	List<BoardVO> findBoardListByBoardCategori(String boardCategori);
		
	//게시물 카테고리 리스트(알바 or 체험 or 중고거래 or 자유)
	List<BoardVO> findBoardListBySelectCategori(String selectCategori);
	
	//내가 쓴 게시물 조회
	List<BoardVO> findMyPostListById(String id);
			
	
}
