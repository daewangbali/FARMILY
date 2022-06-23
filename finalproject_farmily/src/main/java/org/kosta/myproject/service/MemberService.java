package org.kosta.myproject.service;

import java.util.List;

import org.kosta.myproject.vo.Authority;
import org.kosta.myproject.vo.MemberVO;

public interface MemberService {
	
	MemberVO findMemberById(String id);

	List<String> getRegionList();

	List<MemberVO> findMemberListByRegion(String region);

	int getMemberCount();

	void updateMember(MemberVO vo);

	void registerMember(MemberVO vo);

	String idcheck(String id);
	
	List<Authority> findAuthorityByUsername(String username);

	void registerAdminMember(MemberVO memberVO);

	List<MemberVO> findAllWaitingMember();

	void grantAdminMember(String id);

	void deleteWaitingMember(String id);
}
