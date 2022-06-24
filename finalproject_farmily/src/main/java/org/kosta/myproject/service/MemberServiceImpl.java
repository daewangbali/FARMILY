package org.kosta.myproject.service;

import java.util.List;

import org.kosta.myproject.mapper.MemberMapper;
import org.kosta.myproject.vo.Authority;
import org.kosta.myproject.vo.MemberVO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	private final MemberMapper memberMapper;
	/*
	 * 비밀번호 암호화처리를 위한 객체를 주입받는다 
	 * org.kosta.config.security.WebSecurityConfig 에 @Bean 설정 되어 있음 
	 */
	private final BCryptPasswordEncoder passwordEncoder;

	
	// 회원가입시 반드시 권한까지 부여되도록 트랜잭션 처리한다
	@Transactional
	@Override
	public void registerMember(MemberVO memberVO) {
		// 비밀번호를 bcrypt 알고리즘으로 암호화하여 DB에 저장한다
		String encodedPwd = passwordEncoder.encode(memberVO.getPassword());
		memberVO.setPassword(encodedPwd);
		memberMapper.registerMember(memberVO);
		// 회원 가입시 반드시 권한이 등록되도록 트랜잭션처리를 한다
		Authority authority = new Authority(memberVO, "ROLE_MEMBER");
		memberMapper.registerRole(authority);
	}
	
	@Transactional
	@Override
	public void registerAdminMember(MemberVO memberVO) {
		// 비밀번호를 bcrypt 알고리즘으로 암호화하여 DB에 저장한다
		String encodedPwd = passwordEncoder.encode(memberVO.getPassword());
		memberVO.setPassword(encodedPwd);
		memberMapper.registerMember(memberVO);
		// 회원 가입시 반드시 권한이 등록되도록 트랜잭션처리를 한다
		Authority authority = new Authority(memberVO, "ROLE_WAIT");
		memberMapper.registerRole(authority);
	}

	@Override
	public void updateMember(MemberVO memberVO) {
		// 변경할 비밀번호를 암호화한다
		String encodePassword = passwordEncoder.encode(memberVO.getPassword());
		memberVO.setPassword(encodePassword);
		memberMapper.updateMember(memberVO);
	}

	@Override
	public MemberVO findMemberById(String id) {
		return memberMapper.findMemberById(id);
	}

	@Override
	public List<String> getRegionList() {
		return memberMapper.getRegionList();
	}

	@Override
	public List<MemberVO> findMemberListByRegion(String region) {
		return memberMapper.findMemberListByRegion(region);
	}

	@Override
	public int getMemberCount() {
		return memberMapper.getMemberCount();
	}

	@Override
	public String idcheck(String id) {
		int count = memberMapper.idcheck(id);
		return (count == 0) ? "ok" : "fail";
	}

	@Override
	public List<Authority> findAuthorityByUsername(String username) {
		return memberMapper.findAuthorityByUsername(username);
	}

	@Override
	public List<MemberVO> findAllWaitingMember() {
		List<MemberVO> list = memberMapper.findAllWaitingMember();
		return list;
	}

	@Override
	public void grantAdminMember(String id) {
		memberMapper.grantAdminMember(id);
	}

	@Override
	public void deleteWaitingMember(String id) {
		// TODO Auto-generated method stub
		memberMapper.deleteWaitingMember(id);
	}

}
