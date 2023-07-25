package com.spring.mapper;

import java.util.List;

import com.spring.vo.Member;

public interface MemberMapper {

	public Member login(Member member);
	
	public int insert(Member member);
	
	public int idCheck(Member member);
	
	public List<String> getMemberRole(String id);
	
}
