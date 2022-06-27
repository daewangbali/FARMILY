package org.kosta.myproject.service;

import org.kosta.myproject.vo.ReservationVO;

public interface ReserveService {
	//예약가능날짜 insert
	void availableReservation(ReservationVO reservationVO);

	//등록한 게시물 번호 가져오기
	int getBoardNo(String id);
}
