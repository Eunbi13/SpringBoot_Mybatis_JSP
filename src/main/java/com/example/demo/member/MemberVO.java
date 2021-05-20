package com.example.demo.member;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class MemberVO {
	private String userName;
	
	
	//최소 4글자 이상 통과
	private String password;
	@NotEmpty
	private String name;
	@Email
	private String email;
	private String phone;
	//0이상 200이하
	private Integer age;
	
}
