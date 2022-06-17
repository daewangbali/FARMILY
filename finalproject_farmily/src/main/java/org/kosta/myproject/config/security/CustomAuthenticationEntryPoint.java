package org.kosta.myproject.config.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

public class CustomAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {
     public CustomAuthenticationEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }
     
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
    	//Ajax 요청시에는 request header 안의 X-Requested-With 속성에  XMLHttpRequest 로 전달된다
    	//이를 이용해 Ajax 요청인지 식별한다 
    	String ajaxHeader = ((HttpServletRequest) request).getHeader("X-Requested-With");    	
        boolean isAjax = "XMLHttpRequest".equals(ajaxHeader);             
        if (isAjax) {      
        		// ajax 요청이면 login form이 있는 url(결론적으로 page) 대신 403 error로 응답
        		// 인증이 필요한 서비스에 비인증상태에서 ajax 요청하면 Http status 403 error를 전달 
              response.sendError(HttpServletResponse.SC_FORBIDDEN, "사용자 인증이 필요합니다");
        } else { // ajax 요청이 아니면        	
        	//생성자에서 할당된 loginForm url 로 redirect 이동된다 
            super.commence(request, response, authException);
        }
    }
}