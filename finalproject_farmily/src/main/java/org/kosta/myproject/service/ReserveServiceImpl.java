package org.kosta.myproject.service;

import java.util.List;

import org.kosta.myproject.mapper.ReserveMapper;
import org.kosta.myproject.vo.BoardVO;
import org.kosta.myproject.vo.ReservationVO;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReserveServiceImpl implements ReserveService{
	private final ReserveMapper reserveMapper;

	@Override
	public void availableReservation(ReservationVO reservationVO) {
		reserveMapper.availableReservation(reservationVO);
	}
	@Override
	public String getBoardNo(String id) {
		return reserveMapper.getBoardNo(id);
	}

	@Override
	public List<ReservationVO> findReservateDate(String boardNo) {
		return reserveMapper.findReservateDate(boardNo);
	}

	@Override
	public void registerReservation(ReservationVO rvo) {
		reserveMapper.registerReservation(rvo);
	}
	@Override
	public List<BoardVO> findReservationListById(String id) {
		return reserveMapper.findReservationListById(id);
	}
	@Override
	public List<BoardVO> findReservationBoardNoDistinct(String id) {
		return reserveMapper.findReservationBoardNoDistinct(id);
	}
	
	
	
}
