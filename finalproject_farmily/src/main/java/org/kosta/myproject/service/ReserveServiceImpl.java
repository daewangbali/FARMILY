package org.kosta.myproject.service;

import org.kosta.myproject.mapper.ReserveMapper;
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
	public int getBoardNo(String id) {
		return reserveMapper.getBoardNo(id);
	}
	
	
	
}
