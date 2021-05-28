package com.example.demo.security;

import java.io.IOException;

import javax.security.auth.login.AccountExpiredException;
import javax.security.auth.login.CredentialExpiredException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

//로그인에 실패했을 떄 실행 하는 클래스 객체
public class LoginFail implements AuthenticationFailureHandler{

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		// TODO Auto-generated method stub
		System.out.println("로그인 실패");
		System.out.println(exception.getClass());

		String errorClass = exception.getClass().toString().substring(exception.getClass().toString().lastIndexOf(".")+1);
		System.out.println(errorClass);
		//아이디가 없을 경우 = InternalAuthenticationServiceException
		//비번이 다를 경우 = BadCredentialsException
		
		
		
		String message="";
		switch (errorClass) {
		case "InternalAuthenticationServiceException": 
			message="잘못된 아이디 입니다.";
			break;
		case "BadCredentialsException":
			message="비밀번호를 잘못 입력하셨습니다.";			
			break;
		case "AuthenticationCredentialsNotFoundException":
			message="인증에 실패하셨습니다.";
			break;
		case "LockedException":
			message="계정이 잠겼습니다.";//memberVO에 isAccountNonLocked()가 false면 뜨는거(이거 신고에 쓰면 될듯) 
			break;
		case "DisabledException":
			message="휴면계정입니다.";
			break;
		case "AccountExpiredException":
			message="계정의 유효기간이 만료되었습니다.";
			break;
		case "CredentialExpiredException":
			message="비번의 유효기간이 만료되었습니다.(강제로 무조건 바꾸게 하는거)";
			break;
		default :
			message="다시 시도해주세요";
			break;
		}
		
		System.out.println(message);
		
		request.setAttribute("message", message);
		request.getRequestDispatcher("/member/memberLogin").forward(request, response);;
	}
	
	
}
