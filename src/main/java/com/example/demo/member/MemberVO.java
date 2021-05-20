package com.example.demo.member;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class MemberVO {
	private String userName;
	private String password;
	@NotEmpty
	private String name;
	@Email
	private String email;
	private String phone;
	
}
