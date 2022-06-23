package org.kosta.myproject.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.kosta.myproject.vo.Authority;
import org.kosta.myproject.vo.MemberVO;
@Mapper
public interface MemberMapper {

	MemberVO findMemberById(String id);

	List<String> getRegionList();

	List<MemberVO> findMemberListByRegion(String region);	

	int getMemberCount();

	void updateMember(MemberVO vo);

	void registerMember(MemberVO vo);

	int idcheck(String id);

	List<Authority> findAuthorityByUsername(String username);

	void registerRole(Authority authority);

	List<MemberVO> findAllWaitingMember();

	void grantAdminMember(String id);

	void deleteWaitingMember(String id);

}