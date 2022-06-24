package org.kosta.myproject.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.kosta.myproject.vo.MemberVO;

@Mapper
public interface MyPageMapper {

	void updateMember(MemberVO memberVO);

}
