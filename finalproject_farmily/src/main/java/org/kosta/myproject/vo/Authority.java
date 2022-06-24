package org.kosta.myproject.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
// 회원의 권한을 저장하는 클래스 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Authority implements Serializable {
	private static final long serialVersionUID = 5188280555844223102L;
	//private String username;
	private MemberVO memberVO;
	private String authority;

}
