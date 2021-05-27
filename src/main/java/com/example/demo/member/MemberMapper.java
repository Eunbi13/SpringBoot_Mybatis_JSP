package com.example.demo.member;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
	public Long setJoin(MemberVO memberVO)throws Exception;
	public Long setMemberRole(Map<String, String> map)throws Exception;
	public MemberVO getLogin(MemberVO memberVO);
	
	public Long checkUsername(MemberVO memberVO)throws Exception;
}
