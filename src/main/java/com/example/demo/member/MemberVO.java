package com.example.demo.member;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import lombok.Data;

@Data
public class MemberVO {
	private String userName;
	
	
	//최소 4글자 이상 통과
	@Length(min = 4, max=10)
	private String password;
	
	private String passwordCheck;
	
	
	@NotEmpty(message = "이름을 입력하세요")//단점 영어로 안바뀜 이 메세지 우선순위
	private String name;
	@Email
	@NotEmpty
	private String email;
	private String phone;
	//0이상 200이하
	@Range(min = 0, max= 200)
	private Integer age;
	//@Max(value=200)
	//@Min(value=0)과 같다
}
