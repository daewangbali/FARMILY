package org.kosta.myproject.service;

import java.util.List;

import org.kosta.myproject.mapper.BoardMapper;
import org.kosta.myproject.vo.BoardVO;
import org.kosta.myproject.vo.JjimVO;
import org.kosta.myproject.vo.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
	@Autowired
	private final BoardMapper boardMapper;
	//게시물 작성
	@Override
	public void registerBoard(BoardVO bvo){
		boardMapper.registerBoardBoard(bvo);
	}
	//전체 게시물 리스트
	@Override
	public List<BoardVO> findAllBoardList(Pagination pagination) {
		return boardMapper.findAllBoardList(pagination);
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
	//게시글 상세보기
	@Override
	public BoardVO boardView(String boardNo){
		return boardMapper.boardView(boardNo);
	}
	//게시물 농활 리스트
	@Override
	public List<BoardVO> findBoardFarmingListByBoardCategori(String farmingCategori) {
		return boardMapper.findBoardFarmingListByBoardCategori(farmingCategori);
	}
	
	//내가 쓴 게시물 조회
	@Override
	public List<BoardVO> findMyPostListById(String id) {
		return boardMapper.findMyPostListById(id);
	}
	//내가 찜한 게시물 조회
	@Override
	public List<BoardVO> findMyJjimListById(String id) {
		return boardMapper.findMyJjimListById(id);
	}
	@Override
	public void registerJjim(JjimVO jvo) {
		boardMapper.registerJjim(jvo);	
	}
	@Override
	public String findJjim(JjimVO jvo) {
		return boardMapper.findJjim(jvo);
	}
	@Override
	public void deleteJjim(JjimVO jvo) {
		boardMapper.deleteJjim(jvo);
		
	}
	//게시물 수정
	@Override
	public void updateBoard(BoardVO boardVO){
		boardMapper.updateBoardBoard(boardVO);
	}

	//게시물 삭제
	@Override
	public void deletePost(String boardNo){
		boardMapper.deletePost(boardNo);
	}

	//조회수 업데이트
	@Override
	public void updateCount(String boardNo) {
		boardMapper.updateCount(boardNo);
	}
}