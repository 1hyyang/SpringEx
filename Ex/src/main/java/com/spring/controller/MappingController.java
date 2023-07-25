package com.spring.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.vo.Member;
import com.spring.vo.MemberList;

/* 
 * 스프링 MVC에서 제공하고 있는 어노테이션을 이용하여 Controller를 작성하기
 * 
 * 	톰켓 서버를 실행하면 web.xml 파일의 설정을 읽어 서버를 시작한다.
 * 	web.xml 파일에 기술되어 있는 servlet-context.xml 파일의 
 * 	component-scan에 등록된 패키지를 탐색하며 클래스를 조사하고 
 * 	객체 설정에 사용되는 어노테이션들을 가진 클래스를 객체로 생성하고 관리한다.
 * 
 * 	@Controller
 * 		해당 클래스의 인스턴스를 스프링의 빈으로 등록하고 컨트롤러로 사용
 * 
 * 	스프링 MVC Controller의 장점
 * 		1. 파라미터를 자동 수집
 *		2. URL 매핑을 메소드 단위로 처리
 * 		3. 화면에 전달할 데이터는 Model에 담기만 하면 됨
 * 		4. 간단한 페이지 전환(forward, redirect)
 * 		5. 상속/인터페이스 방식 대신 어노테이션만으로도 필요한 설정 가능
 */

@Controller
@RequestMapping("/mapping/*")
public class MappingController {

	/*
	 * RequestMapping
	 * 	클래스의 상단에 적용시 현재 클래스의 모든 메소드들의 기본 URL 경로를 지정
	 * 	메소드의 상단에 적용시 메소드의 URL 경로를 지정
	 * 
	 * 	get 방식과 post 방식을 모두 처리하고 싶은 경우 배열로 받을 수 있다.
	 * 
	 *	/mapping/ URI를 GET 메소드로 호출하면 해당 메소드가 실행된다.
	 */
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String requestMapping() {
		return "mapping";
	}
	
	/*
	 * /mapping/requestMapping URI를 GET 메소드로 호출하면 해당 메소드가 실행된다.
	 */
	@RequestMapping(value="/requestMapping", method= {RequestMethod.GET, RequestMethod.POST})
	public String requestMapping2() {
		return "mapping";
	}
	
	/*
	 * 스프링 4.3 이후에는 GetMapping, PostMapping 등으로 간단히 표현 가능
	 * 어노테이션 사용이 불가능한 경우 스프링의  버전을 확인한다.
	 * 
	 * 파라미터의 자동 수집
	 * 	- RequestParam 어노테이션을 이용하면 기본 타입의 데이터를 지정한 타입으로 받을 수 있다.
	 *	    단, 타입이 불일치하는 경우 400 오류가 발생할 수 있다. 
	 *	- 화면에 값을 전달하고 싶은 경우 Model 객체를 매개 변수로 받아 속성을 추가한다.
	 *	  model.addAttribute("이름", 값)
	 */
	@GetMapping("getMapping")
	public String getMapping(@RequestParam("name") String name
							, @RequestParam("age") int age
							, Model model) {
		model.addAttribute("name", name);
		model.addAttribute("age", age);
		return "mapping";
	}
	
	/*	
	 * 	- VO, DTO 객체를 타입으로 지정하여 파라미터를 받을 경우 
	 *	    객체 생성 후 파라미터의 name 속성과 일치하는 필드에 세팅한다.
	 *  - 이 경우 별도의 저장 없이 화면까지 전달된다.
	 */
	@GetMapping("getMappingVo")
	public String getMappingVo(Member member, Model model) {
		return "mapping";
	}
	
	@GetMapping("getMappingArray")
	public String getMappingArray(@RequestParam("names") String[] names) {
		for(String name:names) {
			System.out.println("name: " + name);
		}
		return "mapping";
	}
	
	@GetMapping("getMappingList")
	public String getMappingList(@RequestParam("names") List<String> names) {
		/*
		 * forEach : 익명의 함수를 이용한 컬렉션의 반복 처리
		 * collection.forEach(변수->{반복처리(변수)})
		 */
		names.forEach(name->{
			System.out.println("name: " + name);
		});
		return "mapping";
	}
	
	@GetMapping("getMappingMemberList")
	public String getMappingMemberList(MemberList list) {
		System.out.println("list: " + list);
		return "mapping";
	}
	
}
