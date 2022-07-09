package org.kosta.myproject.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.kosta.myproject.vo.BoardVO;
import org.kosta.myproject.vo.FileVO;
import org.kosta.myproject.vo.JjimVO;
import org.kosta.myproject.vo.Pagination;

@Mapper
public interface BoardMapper {
	//게시물 작성
	void registerBoardBoard(BoardVO bvo);
	void registerBoardFile(FileVO fileVO); 
	
	//전체 게시물 리스트
	List<BoardVO> findAllBoardList(Pagination pagination);
	
	int getTotalPostCount();
	
	//지역별 게시물 리스트
	List<BoardVO> findBoardListByRegion(String region);
	
	//게시판 카테고리 리스트(농활 or 장터)
	List<BoardVO> findBoardListByBoardCategori(String boardCategori);
	
	//게시물 카테고리 리스트(알바 or 체험 or 중고거래 or 자유)
	List<BoardVO> findBoardListBySelectCategori(String selectCategori);
	
	//게시글 상세페이지
	BoardVO boardView(String boardNo);

	//게시물 농활 리스트
	List<BoardVO> findBoardFarmingListByBoardCategori(String farmingCategori);

	// 내가 쓴 게시물 조회
	List<BoardVO> findMyPostListById(String id);
	
	// 내가 찜한 게시물 조회
	List<BoardVO> findMyJjimListById(String id);
	
	
	//게시물 찜 여부
	String findJjim(JjimVO jvo);
	
	//게시물 업데이트
	void updateBoardBoard(BoardVO boardVO);

	//게시글 삭제
	void deletePost(String id);

	// 게시물 찜하기
	void registerJjim(JjimVO jvo);
	//게시물 찜 취소하기
	void deleteJjim(JjimVO jvo);
	
	// 조회수 업데이트하기
		void updateCount(String boardNo);
}