package com.example.demo.member;

import java.util.Enumeration;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/member/**")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("join")
	public String setJoin(Model model)throws Exception{
		model.addAttribute("memberVO", new MemberVO());
		return "member/memberJoin";
	}
	
	@PostMapping("join")
	public String join(@Valid MemberVO memberVO, Errors bindingResult)throws Exception{
		//memberVO 안에 있는 annotiation하고 검증해보는 것 
		//검증을 통과 하지 못할 경우 bindingResult에 담는다
		//꼭 Valid 다음에 BindingResult가 와야한다
//		if(bindingResult.hasErrors()){//에러가 있으면 true 없으면 false
//			return "member/memberJoin";
//		}
		
		if(memberService.memberErrors(memberVO, bindingResult)) {
			return "member/memberJoin";
		}
		return "redirect:../";
	}
	@GetMapping("login")
	public String getLogin()throws Exception{
		return "member/memberLogin";
	}
	@GetMapping("memberLoginResult")
	public String memberLoginResult(HttpSession session)throws Exception{
		//로그인 성공했을 경우 시큐리티에서 이 주소로 보내준다 그리고 그걸 컨트롤러에서 받아서 홈으로 돌아감
		//로그인 성공했을 때 세선에 넣어주기는 하는데 이름을 몰라 찾아내야함
		//session의 속성명들 꺼내오기
		Enumeration<String> en=session.getAttributeNames();
		while(en.hasMoreElements()) {
			System.out.println("이름: "+en.nextElement());
		}
		
		//로그인 시 session의 속성 명: SPRING_SECURITY_CONTEXT object
		Object obj = session.getAttribute("SPRING_SECURITY_CONTEXT");
		System.out.println(obj);
		//SecurityContextImpl [Authentication~~~[Principal=MemberVO ~~~, Granted Authorities=[ROLE_MEMBER]]]
		//클래스명 				//인증			//시큐리티 접근주체(유저정보)   //role정보
		SecurityContextImpl sc = (SecurityContextImpl)obj;
		
		Authentication auth= sc.getAuthentication();
		System.out.println("===========auth===========");
		System.out.println("name: "+auth.getName());//username
		System.out.println("detaile: "+auth.getDetails());// WebAuthenticationDetails [RemoteIpAddress=0:0:0:0:0:0:0:1, SessionId=null]
		System.out.println("Principal: "+auth.getPrincipal());//memberVO 유저 정보
		System.out.println("auth: "+auth.getAuthorities());//role_member 권한 정보
	
		System.out.println("로그인 성공");
		return "redirect:/";
	}
	
	@GetMapping("loginFail")
	public String loginFail() throws Exception{
		System.out.println("fail!");
		return "redirect:/member/login";
	}
	
	
}
