package com.spring.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.service.ReplyService;
import com.spring.vo.Criteria;
import com.spring.vo.PageVo;
import com.spring.vo.Reply;

/*
 * @RestController
 * 	컨트롤러가 REST 방식을 처리하기 위한 것임을 명시
 * 	(리턴타입에 @ResponseBody를 명시하지 않아도 같은 기능을 수행)
 *
 * @ResponseBody
 *	일반적인 JSP와 같은 뷰로 전달되는 게 아니라 데이터 자체를 전달하기 위한 용도
 *	JSON 형태로 데이터를 반환
 */
@RestController
public class ReplyController extends CommonRestController {

	@Autowired
	ReplyService service;
	
	@GetMapping("test")
	public String test() {
		// jsp 파일로 이동하는 것이 아니라 데이터 자체(문자열)를 그대로 반환
		return "test";
	}
	
	/*
	 * @PathVariable
	 * 	URL 경로에 있는 값을 파라미터로 추출하려고 할 때 사용
	 *  URL 경로의 일부를 변수로 사용
	 */
	@GetMapping("/reply/list/{bno}/{page}")
	public Map<String, Object> getList(@PathVariable("bno") int bno
								, @PathVariable("page") int page){		
		Criteria criteria = new Criteria();
		criteria.setPageno(page);
		int total = service.getTotal(bno);
		PageVo pageVo = new PageVo(criteria, total);
		
		return responseListMap(service.getList(bno, criteria), pageVo);
	}
	
	/*
	 * @RequestBody
	 * 	JSON 데이터를 원하는 타입으로 바인딩 처리
	 * 
	 *	이 메소드는 JSON 데이터를 Reply 객체로 바인딩
	 */
	@PostMapping("/reply/write")
	public Map<String, Object> insert(@RequestBody Reply reply){
		return responseWriteMap(service.insert(reply));
	}
	
	@PostMapping("/reply/edit")
	public Map<String, Object> update(@RequestBody Reply reply){
		return responseEditMap(service.update(reply));
	}	
	
	@GetMapping("/reply/delete/{rno}")
	public Map<String, Object> delete(@PathVariable("rno") int rno){
		return responseDeleteMap(service.delete(rno));
	}
	
}
