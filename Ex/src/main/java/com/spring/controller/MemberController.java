package com.spring.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.service.MemberService;
import com.spring.vo.Member;

import jdk.internal.org.jline.utils.Log;
import lombok.extern.log4j.Log4j;

@Log4j
@Controller
public class MemberController extends CommonRestController {
	
	@Autowired
	MemberService service;
	
	@GetMapping("/login/naver")
	public void naverLogin() {
		
	}
	
	@GetMapping("/login/naver_callback")
	public String naverLogin_callback(HttpServletRequest request
										, Model model) {
		service.naverLogin(request, model);
		return "/login/naver";
	}
	
	/*
	 * 로그인 페이지로 이동
	 */
	@GetMapping("login")
	public String login() {
		return "login";
	}
	
	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "login";
	}
	
	/*
	 * @ResponseBody
	 *	일반적인 JSP와 같은 뷰로 전달되는 게 아니라 데이터 자체를 전달하기 위한 용도
	 *	JSON 형태로 데이터를 반환
	 *
	 * @RequestBody
	 * 	JSON 데이터를 원하는 타입으로 바인딩 처리
	 * 
	 *	이 메소드는 JSON 데이터를 Member 객체로 바인딩
	 */
	@PostMapping("loginAction")
	public @ResponseBody Map<String, Object> loginAction(@RequestBody Member paramMember
														, HttpSession session) {
		Member member = service.login(paramMember);
		if(member!=null) {
			Map<String, Object> map = responseMap(REST_SUCCESS, "로그인되었습니다.");
			
			session.setAttribute("member", member);
			session.setAttribute("userid", member.getId());
			
			if(member.getRole()!=null && member.getRole().contains("admin_role")){
				map.put("url", "/admin");				
			} else {
				map.put("url", "/board/list");
			}
			return map;
		} else {
			return responseMap(REST_FAIL, "아이디와 비밀번호를 확인하세요.");
		}
	}
	
	@PostMapping("idCheck")
	public @ResponseBody Map<String, Object> idCheck(@RequestBody Member member){
		if(service.idCheck(member)==0) {
			return responseMap(REST_SUCCESS, "사용 가능한 아이디입니다.");					
		} else {
			return responseMap(REST_FAIL, "이미 사용 중인 아이디입니다.");	
		}
	}
	
	@PostMapping("register")
	public @ResponseBody Map<String, Object> register(@RequestBody Member member){
		try {			
			return responseWriteMap(service.insert(member));
		} catch (Exception e) {
			e.printStackTrace();
			return responseMap(REST_FAIL, "등록 중 오류가 발생하였습니다.");
		}
	}
	
}
