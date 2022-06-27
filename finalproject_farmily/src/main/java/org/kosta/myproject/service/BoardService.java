package org.kosta.myproject.service;

import java.util.List;

import org.kosta.myproject.vo.BoardVO;
import org.springframework.data.domain.Pageable;

public interface BoardService {
	//게시물 작성
	void registerBoard(BoardVO bvo);

	//전체 게시물 리스트
	List<BoardVO> findAllBoardList();
	//Page<BoardVO> findAllBoardList(Pageable pageable);
		
	//지역별 게시물 리스트
	List<BoardVO> findBoardListByRegion(String region);
		
	//게시판 카테고리 리스트(농활 or 장터)
	List<BoardVO> findBoardListByBoardCategori(String boardCategori, Pageable pageable);
		
	//게시물 카테고리 리스트(알바 or 체험 or 중고거래 or 자유)
	List<BoardVO> findBoardListBySelectCategori(String selectCategori);
			
	//게시글 상세페이지
	BoardVO boardView(String boardNo);
	
	//농촌활동 게시물 리스트(+지도)
	List<BoardVO> findBoardFarmingListByBoardCategori(String farmingCategori);
	
	//마이페이지
	List<BoardVO> findMyPostListById(String id);

	//게시글 업데이트
	void updateBoard(BoardVO boardVO);

	//게시글 삭제
	void deletePost(String id);


	

	
}
