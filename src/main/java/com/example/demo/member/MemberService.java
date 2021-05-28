package com.example.demo.member;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	
	//로그인 메서드 개발자가 호출x(get Login)
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MemberVO memberVO = new MemberVO();
		memberVO.setUsername(username);
		memberVO = mapper.getLogin(memberVO);
		return memberVO;
	}

	//검증 메서드
	public boolean memberError(MemberVO memberVO, Errors errors)throws Exception{
		boolean result=false;
		
		//기본 제공 검증 결과
//		if(errors.hasErrors()) {
//			result=true;
//		}
		result = errors.hasErrors();
		
		//검증 코드 만들기
		//password가 일치하는가?
		if(!memberVO.getPassword().equals(memberVO.getPasswordCheck())) {
			errors.rejectValue("passwordCheck", "memberVO.password.notEqual");
							  //form path||field	properties에 적은 키
			result=true;//에러 발생하면 트루
		}
		//userName 중복 여부
		if(mapper.checkUsername(memberVO)>0) {
			errors.rejectValue("userName", "memberVO.userName.has");
		}
		
		//회워가입하러 올때 admin, adminstrator 로 가입하려는 거 막기
		if(memberVO.getUsername().equals("admin")||memberVO.getUsername().equals("adminstrator")) {
			errors.rejectValue("username", "memberVO.userName.nope");
		}
		
		
		return result;
	}
	@Transactional(rollbackFor = Exception.class)
	public Long setJoin(MemberVO memberVO) throws Exception{
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
		
		
		return result;
	}

}
