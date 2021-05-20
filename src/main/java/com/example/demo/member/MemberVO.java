package com.example.demo.member;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import lombok.Data;

@Data
public class MemberVO {
	private String userName;
	
	
	//최소 4글자 이상 통과
	@Size(min = 4)
	private String password;
	@NotEmpty
	private String name;
	@Email
	private String email;
	private String phone;
	//0이상 200이하
	@Range(min = 0, max= 200)
	private Integer age;
	//@Max(value=200)
	//@Min(value=0)과 같다
}
