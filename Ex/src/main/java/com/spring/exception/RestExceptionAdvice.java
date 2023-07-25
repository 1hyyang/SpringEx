package com.spring.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.log4j.Log4j;

/*
 * ControllerAdvice
 * 	컨트롤러에 대한 예외를 처리하는 객체임을 명시
 * 	컨트롤러 실행 중 발생하는 오류이므로 500번 오류가 발생하는 경우 실행된다.
 * 
 * ExceptionHandler
 * 	Controller, restController가 적용된 Bean 내에서 발생하는 예외를 하나의 메소드에서 처리하는 기능
 */

@RestControllerAdvice
@Log4j
public class RestExceptionAdvice {
	
	@ExceptionHandler(Exception.class)
	public Map<String, Object> handleException(Exception exception) {
		System.out.println("Exception..." + exception.getMessage());
		exception.printStackTrace();
		log.info("Exception...info level");
		log.debug("Exception...debug level");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "fail");
		map.put("message", exception.getMessage());
		return map;
	}
	
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handle404(NoHandlerFoundException ex) {
		return "error/error400";
	}
}
