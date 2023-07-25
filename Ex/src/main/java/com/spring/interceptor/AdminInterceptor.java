package com.spring.interceptor;

import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.spring.vo.Member;

@Component
public class AdminInterceptor implements HandlerInterceptor {

	/*
	 * preHandle : 컨트롤러 실행 전 실행
	 * return 
	 * 	true : 요청 컨트롤러 실행
	 * 	false : 요청 컨트롤러 실행하지 않음
	 */
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		// 컨트롤러에서 Member 객체를 세션 영역에 저장
		if(session.getAttribute("member")!=null) {
			Member member = (Member)session.getAttribute("member");
			System.out.println("------------------- 2 " + member);
			List<String> roles = member.getRole();
			if(roles.contains("admin_role")) {
				return true;			
			}
		}
		String message = URLEncoder.encode("로그인 후 사용 가능한 메뉴입니다.", "utf-8");
		response.sendRedirect("/login?msg=" + message);
		return false;
	}
	
}
