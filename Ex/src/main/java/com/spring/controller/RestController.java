package com.spring.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.vo.Member;

/*
 * JSON 데이터를 반환하는 방법
 *  
 * 	1. 라이브러리 추가
 *		jackson-databind 라이브러리를 메이븐 리파지토리에서 검색 후 pom.xml 파일에 추가한다. 
 * 
 * 	2. 리턴타입에 어노테이션 추가
 *		메소드 선언부의 리턴타입에 ResponseBody 어노테이션을 추가한다.
 *		리턴타입에 맞게 데이터를 자동으로 변환해준다.
 *
 *		(개발자 도구 > 네트워크 > 응답 헤더 > Content-Type에서 확인 가능
 *		(text/html -> application/json))
 *
 * 	3. 메서드의 리턴타입
 *      VO, DTO
 *  	JSON 타입의 데이터를 만들어서 반환하는 용도로 사용
 */

@Controller
public class RestController {
	
	@GetMapping("rest")
	public @ResponseBody Member rest(Member member) {
		return member;
	}
	
	/*
	 * ResponseEntity
	 * 	헤더 정보를 가공하기 위한 용도로 사용
	 * 
	 * 	Request, Response 객체를 직접 다루지 않고
	 * 	Spring MVC에서 제공하는 어노테이션 또는 객체를 이용한다.
	 */
	@GetMapping("restResponseEntity")
	public ResponseEntity<String> rest(){
		HttpHeaders header = new HttpHeaders();
		header.add("content-type", "application/json;charset=utf-8");
		String message = "{\"음,,\"}";
		ResponseEntity<String> responseE = new ResponseEntity<String>(message, header, HttpStatus.OK);
		return responseE;
	}
	
}
