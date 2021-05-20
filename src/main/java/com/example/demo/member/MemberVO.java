package com.example.demo.member;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class MemberVO {
	private String userName;
	private String password;
	@NotNull
	private String name;
	@Email
	private String email;
	private String phone;
	
}
