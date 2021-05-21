package com.example.demo.member;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
@Service
public class MemberService {
	
	//valid
	public boolean memberErrors(MemberVO memberVO, Errors errors)throws Exception{
		boolean result=false;
		result = errors.hasErrors();
		
		if(!memberVO.getPassword().equals(memberVO.getPasswordCheck())) {
			result=true;
			errors.rejectValue("passwordCheck", "memberVO.password.NotEqual");
		}
		
		return result;
	}
}
