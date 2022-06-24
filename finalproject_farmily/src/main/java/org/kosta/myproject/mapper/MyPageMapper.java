package org.kosta.myproject.mapper;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.kosta.myproject.vo.MemberVO;

@Mapper
public interface MyPageMapper {

	void updateMember(MemberVO memberVO);

	int findMemberByIdAndPassword(HashMap<String, String> map);

	void deleteMember(String memberId);

	void deleteRole(String memberId);

}
