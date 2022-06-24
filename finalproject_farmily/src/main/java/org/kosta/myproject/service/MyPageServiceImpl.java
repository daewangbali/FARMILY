package org.kosta.myproject.service;

import org.kosta.myproject.mapper.MyPageMapper;
import org.kosta.myproject.vo.MemberVO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

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

	@Override
	@ResponseBody
	public boolean deleteCheck(@AuthenticationPrincipal MemberVO memberVO, String memberId, String deleteCheck) {
		if(memberId.equals(memberVO.getId()) && deleteCheck.equals("회원탈퇴확인")) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public void deleteMember(String memberId) {
		mypageMapper.deleteMember(memberId);
	}

	@Override
	public void deleteRole(String memberId) {
		mypageMapper.deleteRole(memberId);
	}
	
}
