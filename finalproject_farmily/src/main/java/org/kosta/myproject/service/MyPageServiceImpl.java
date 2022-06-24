package org.kosta.myproject.service;

import org.kosta.myproject.mapper.MyPageMapper;
import org.kosta.myproject.vo.MemberVO;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {
	private final MyPageMapper mypageMapper;

	@Override
	public void updateMember(MemberVO memberVO) {
		// TODO Auto-generated method stub
		mypageMapper.updateMember(memberVO);
	}
	
}
