package com.example.demo.member;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	
	
}
