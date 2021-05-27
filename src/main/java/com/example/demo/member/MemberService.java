package com.example.demo.member;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;
@Service
public class MemberService implements UserDetailsService {
	
	@Autowired
	private MemberMapper mapper;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MemberVO memberVO = new MemberVO();
		memberVO.setUsername(username);
		memberVO = mapper.getLogin(memberVO);
		return memberVO;
	}
	
	
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
	
	
	@Transactional(rollbackFor = Exception.class)
	public Long setJoin(MemberVO memberVO, MultipartFile file) throws Exception{
		//0. 사전작업
		//a. password 암호화
		memberVO.setPassword(passwordEncoder.encode(memberVO.getPassword()));
		//b. 사용자 계정 활성화
		memberVO.setEnabled(true);
		
		//1.멤버 테이블 저장
		Long result = mapper.setJoin(memberVO);
		
		//1-2 role table에 저장
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", memberVO.getUsername());
		map.put("roleName", "ROLE_MEMBER");
		result = mapper.setMemberRole(map);
		
		//2.HDD에 저장
		/*
		 * if(file.getSize() !=0) { FileManager fileManager = new FileManager(); String
		 * path = "upload/member/"; String fileName = fileManager.save(file, path);
		 * MemberFileVO fileVO = new MemberFileVO();
		 * fileVO.setUserName(memberVO.getUsername()); fileVO.setFileName(fileName);
		 * fileVO.setOriName(file.getOriginalFilename()); mapper.setMemberFile(fileVO);
		 * }
		 */
		return result;
	}

	
}
