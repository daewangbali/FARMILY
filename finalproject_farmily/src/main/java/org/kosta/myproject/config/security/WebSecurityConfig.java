package org.kosta.myproject.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
//스프링 시큐리티 설정 클래스 
@Configuration
@EnableWebSecurity  
//@EnableGlobalMethodSecurity 어노테이션은 @Secured @PreAuthorize, @PostAuthorize 애노테이션을 사용하여 인증,인가 처리를 하고 싶을때 사용하는 설정
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig  {

	//비밀번호 암호화를 위한 bean 생성 -> MemberService 에서 비번 암호화를 위해 사용 , 
	//MemberAuthenticationProvider 에서 비번 일치여부를 위해 사용 
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	
	/*
	 	인증(authentication:로그인 여부)을 위한 설정은 아래의 메서드에서 처리한다 (로그인 기능과 로그인 여부에 따른 서비스 접근에 대한 설정 ) 
	 	인가(authorization: 권한 여부)에 대한 설정은 각 컨트롤러 메서드에서 @Secured("ROLE_ADMIN") 와 같이 설정한다  
	 	( 인증받은 회원이 부여받은 권한에 의거해 접근할 수 있는 서비스에 대한 설정 ) 	 	
	 	AdminController 와 AuthTestController 에서 확인한다 
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {		
		/*	
		 	인증 처리 : 로그인 여부에 따라 접근 가능한 서비스를 설정 
		 	/  , home : welcome file 		
			
			정적인 파일 ( css , javascript , image ) 은 static 디렉토리 이하에 위치시킨다 
			/myweb/** 이하 하위 디렉토리(css, images, js) 및 파일에 접근 허용  
			
			/guest/ 로그인 없어도 서비스할 url 
			위의 지정한 url 에는 permitAll() 로그인 인증없이 서비스 되고 
			그 외의 요청에는 anyRequest().authenticated()  로그인 인증된 사용자만 접근할 수 있다 
		 */
		http.authorizeRequests().antMatchers("/","/home", "/assets/**", "/css/**","/images/**","/js/**", "/guest/**").permitAll()
		.anyRequest().authenticated();
		
		// 인증(authentication): 로그인을 위한 설정
		http.formLogin().loginPage("/guest/loginForm") // 로그인 폼이 있는 url
				.loginProcessingUrl("/guest/login")// login form 의 action 경로 ==> templates/fragments/left.html의 로그인 폼에서 확인
				.failureUrl("/guest/loginFail") // 로그인 실패시 메세지 보여줄 url ==> MemberController에서 메서드 정의
				.defaultSuccessUrl("/home",true) // 로그인 성공 후 이동할 url, 두번째 인자값 true는 로그인 성공 후 결과페이지 경로를 고정하기 위해서
				.usernameParameter("id") // 로그인 폼 아이디 name 
				.passwordParameter("password")//로그인 폼에서 전달할 패스워드 name 
				.and() 
				.formLogin().permitAll();//로그인 폼은 인증없이 접근하도록 설정 
		// 로그아웃을 위한 설정, 로그아웃 처리 후 로그인 폼이 있는 home으로 이동 , session invalidate (무효화한다) 
		http.logout().permitAll().logoutUrl("/logout").logoutSuccessUrl("/home").invalidateHttpSession(true);
		
			 
		
		//Spring Security에서 인증이 필요한 서비스를 비인증 상태에서 요청하면 AccessDeniedException 발생된다
		//이 때 CustomAuthenticationEntryPoint 객체가 실행되도록 설정
		// ajax 요청일 경우 http status 403 Forbidden code 로 응답하게 하고 
		//ajax 요청이 아닌 경우 로그인 폼이 있는 home 으로 redirect 하도록 처리한다 => CustomAuthenticationEntryPoint 에서 구현 내용을 확인 
		http.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint("/home"));
		
		//서비스 접근 권한이 없을 때 accessDeniedView 로 이동된다 
		//HomeController의 accessDeniedView 메서드에 정의되어 있음
		http.exceptionHandling().accessDeniedPage("/accessDeniedView");	
		
		return http.build();
	}

	

}