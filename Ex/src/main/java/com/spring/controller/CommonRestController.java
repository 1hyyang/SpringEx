package com.spring.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.spring.vo.PageVo;
import com.spring.vo.Reply;

public class CommonRestController {

	private final String REST_GETLIST = "조회";
	private final String REST_WRITE = "등록";
	private final String REST_EDIT = "수정";
	private final String REST_DELETE = "삭제";
	protected final String REST_SUCCESS = "success";
	protected final String REST_FAIL = "fail";
	
	/*
	 * 입력, 수정, 삭제의 경우 int 값을 반환
	 * 결과를 받아서 Map을 생성 후 반환
	 */
	public Map<String, Object> responseMap(int res, String msg){
		Map<String, Object> map = new HashMap<String, Object>();
		if(res>0) {
			map.put("result", REST_SUCCESS);
			map.put("message", msg + "되었습니다.");
		} else {
			map.put("result", REST_FAIL);
			map.put("message", msg + " 중 오류가 발생하였습니다.");
		}
		return map;
	}
	
	// 아이디 중복 체크
	public Map<String, Object> responseMap(String res, String msg){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", res);
		map.put("message", msg);
		return map;
	}
	
	// 리스트로 받는 객체를 ? 처리함으로써 하나의 메소드로 다양한 타입의 객체를 받아 처리할 수 있다.
	public Map<String, Object> responseListMap(List<?> list, PageVo pageVo){
		int res = list!=null?1:0;
		Map<String, Object> map = responseMap(res, REST_GETLIST);
		map.put("list", list);
		map.put("pageVo", pageVo);
		return map;
	}
	
	public Map<String, Object> responseWriteMap(int res){
		return responseMap(res, REST_WRITE);
	}
	
	public Map<String, Object> responseEditMap(int res){
		return responseMap(res, REST_EDIT);
	}
	
	public Map<String, Object> responseDeleteMap(int res){
		return responseMap(res, REST_DELETE);
	}
}
