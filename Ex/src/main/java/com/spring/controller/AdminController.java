package com.spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class AdminController {

	// 컨트롤러를 실행하기 전에 AdminInterceptor의 preHandle 메소드가 실행된다.
	@GetMapping("admin")
	public String admin() {
		return "/admin/main";
	}
	
}
