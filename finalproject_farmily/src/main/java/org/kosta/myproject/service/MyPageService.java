package org.kosta.myproject.service;

import java.util.HashMap;

import org.kosta.myproject.vo.MemberVO;

public interface MyPageService {

	void updateMember(MemberVO memberVO);

	boolean deleteCheck(MemberVO memberVO, String memberId, String deleteCheck);

	void deleteMember(String memberId);

	void deleteRole(String memberId);

}