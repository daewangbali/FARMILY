package org.kosta.myproject.config.security;

import java.util.ArrayList;
import java.util.List;

import org.kosta.myproject.service.MemberService;
import org.kosta.myproject.vo.Authority;
import org.kosta.myproject.vo.MemberVO;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/*
 * Spring Security를 이용한 인증로직을 정의한 클래스 
 * Spring Security 를 담당하는 FliterChainProxy 객체가 인증 처리를 위해  MemberAuthenticationProvider 를 사용한다 
 * 
 * (src/test/java/1-1-spring-security-filter-chain 이미지 참조)
 * (https://docs.spring.io/spring-security/reference/servlet/architecture.html 참조)
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class MemberAuthenticationProvider implements AuthenticationProvider{	
	// 로그인 메서드가 정의된 MemberService를 주입받는다 
	private final MemberService memberService;
	//비밀번호 암호화처리(로그인시 사용자가 입력한 비밀번호와 db에 저장된 ..?)를 위한 BCryptPasswordEncoder 객체를 주입받는다 	
	//org.kosta.myproject.config.security.WebSecurityConfig 에서 @Bean 으로 생성되어 스프링 컨테이너에서 저장되어 있음 
    private final BCryptPasswordEncoder passwordEncoder;
    

	@Override
	/*Authentication authenticate(Authentication authentication) throws AuthenticationException
	 * -실제 인증 처리
     *    - 규칙
     *     1. 파라미터로 전달받은 Authentication 객체에 대해 id(username) , password 방식의 인증처리를 지원하지 않으면 null을 리턴한다.
     *     2. Authentication 객체를 이용한 인증에 실패하면 AuthenticationException 발생시킨다.
     *     3. 인증에 성공하면, 인증 정보를 담은 Authentication 객체를 만들어 return 한다.
	 * 
	 *   사용자가 화면에서 로그인을 하면 아래의 메서드가 실행된다. 
	 *   매개변수 : 인증시 필요한 정보 - Authentication ( 사용자가 입력한 ID , PASSWORD가 저장되어 있음 ) 
	 *   리턴값 : 인증한 정보를 가진 Authentication를 	  FliterChainProxy 로 반환한다 
	 *   
	 *   간단히 정리하면 authenticate 메서드는 
	 *   매개변수에 전달된 Authentication객체를 받아 인증처리를 한 뒤 
	 *   1. 아이디 패스워드가 일치하면 인증 정보(회원 및 권한 정보)를 Authentication에 저장해 리턴
	 *   2. 일치하지 않으면 UsernameNotFoundException or BadCredentialsException or InsufficientAuthenticationException
	 *   	 throws 한다 
	 */
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {		
		//1.파라미터로 전달받은 Authentication 객체에 대해 지정한 id(username) , password 방식의 인증처리를 지원하지 않으면 null을 리턴한다.
		//  supports 메서드는 아래에 오버라이딩 되어있음 
		if(!supports(authentication.getClass())){
			return null;
		}
		//2.사용자 정보 DB로부터 조회
		String id = authentication.getName();//사용자가 로그인시 입력한 ID 반환 		
		MemberVO member = memberService.findMemberById(id);
		if(member == null){
			throw new UsernameNotFoundException("회원 아이디가 존재하지 않습니다");
		}
		String password=(String)authentication.getCredentials();//사용자가 입력한 패스워드 반환 
		//3.패스워드 비교
		/*if(!password.equals(member.getPassword())){//패스워드가 틀리면
			throw new BadCredentialsException("패스워드가 틀립니다.");
		}*/
		/* 
		   비밀번호 암호화를 이용할 경우 
		   이용자가 로그인 폼에서 입력한 비밀번호와 DB로부터 가져온 암호화된 비밀번호를 비교한다
		   Spring Security에서 사용하는 암호화는
		   보안 운영체제의  대표적인 OpenBSD에서 사용하는 암호 인증 메커니즘인
           단방향 암호화 해싱함수의 bcrypt 암호화 기법을 이용하므로 복호화는 불가능하고  
	       비교만 가능함 ( 비밀번호 찾기는 불가능하고 교체만 가능 )  
	    */
        if (!passwordEncoder.matches(password, member.getPassword()))//! 비밀번호가 일치하지 않으면  
                throw new BadCredentialsException("비밀번호가 일치하지 않습니다");
		//4.사용자 권한 조회
		List<Authority> list = memberService.findAuthorityByUsername(id);
		// 권한이 하나 이상 없으면 자격 증명이 불충분한 것으로 판단한다
		// ( 회원가입시 ROLE_MEMBER 로 권한이 자동 등록되도록 MemberService 에 구현되어 있음 ) 
		if(list.size() == 0){ 
			throw new InsufficientAuthenticationException("권한이 없습니다.");
		}
		// 회원과 권한이 1 대 다 이므로 권한을 리스트로 저장해서 반환할 Authentication 에 할당한다 
		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();		
		for(Authority au : list){ // ROLE_ 형식의 db 정보가 아니라면 이 시점에 ROLE_ 를 접두어로 추가한다
			authorities.add(new SimpleGrantedAuthority(au.getAuthority()));
		}
				
		Authentication auth = new UsernamePasswordAuthenticationToken(member, password, authorities);
		log.debug("MemberAuthenticationProvider 인증처리완료:{}",auth);
		return auth;		
	}

	@Override
	public boolean supports(Class<?> authentication) {
		//현재 MemberAuthenticationProvider가  지정된 Authentication 객체가 id(username),password 인증방식이면 true를 반환한다 
		//위의 authenticate 메서드 인증로직 첫 시점에 비교한다 
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}	
}
