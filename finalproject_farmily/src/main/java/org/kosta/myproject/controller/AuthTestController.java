package org.kosta.myproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
//인증 및 인가 테스트를 위한 컨트롤러
@Controller
public class AuthTestController {
	//인증받지 않은 사람도 접근해서 테스트 해야 하므로 guest 경로로 mapping 한다 
	// org.kosta.myproject.config.security.WebSecurityConfig 에 설정되어 있음 
	@RequestMapping("guest/testAuth")
	public String testAuth() {
		return "auth/test-auth";
	}
}
