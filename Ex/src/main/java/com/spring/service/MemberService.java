package com.spring.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.spring.mapper.MemberMapper;
import com.spring.vo.Member;

@Service
public interface MemberService {
	
	public Member login(Member member);
	
	public int insert(Member member);
	
	public int idCheck(Member member);

	public void naverLogin(HttpServletRequest request, Model model);
	
}
