package com.spring.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import com.spring.service.LogService;
import com.spring.vo.LogVo;

import lombok.extern.log4j.Log4j;

/*
 * AOP(Aspect-Oriented Programming)
 * 	관점 지향 프로그래밍
 * 	핵심 비즈니스 로직과 부가적인 관심사를 분리하여 개발하는 방법론
 * 	코드의 중복을 줄이고 유지보수성을 향상시킬 수 있다.
 * 
 * 	부가적인 관심사
 * 		로깅, 보안, 트랜잭션 관리 등 애플리케이션에서 공통적으로 처리해야 하는 기능
 * 
 * 	Aspect
 * 		부가적인 관심사를 모듈화한 단위 (Advice를 그룹화) 
 * 		주 업무 로직 이외의 부가적인 기능을 의미
 * 
 * 	Advice
 * 		부가 기능(횡단 관심사)을 구현한 객체
 * 		(Cross Concern : 횡단 관심사)
 * 
 * 	Pointcut
 * 		부가 기능이 적용되는 지점
 * 
 * 	Target
 * 		핵심 기능(핵심 관심사)을 구현한 객체
 * 		(Core Concern : 핵심 관심사)
 * 
 * 	Proxy
 * 		Target + Advice
 */

@Aspect
@Component
@Log4j
public class LogAdvice {
	
	/*
	 * Pointcut
	 * 	언제 어디에 적용할지를 기술
	 * 	* : 메소드 리턴타입
	 * 	com.spring.service : 패키지 경로
	 * 	Board* : 클래스명(Board로 시작하는 모든 클래스)
	 * 	* : 메소드명
	 * 	(..) : 매개 변수
	 * 
	 * Before
	 * 	타겟 객체의 메소드가 실행되기 전에 호출되는 어드바이스
	 * 	JoinPoint를 통해 파라미터 정보 참조 가능
	 */
	@Before("execution(* com.spring.service.Board*.*(..))")
	public void logBefore() {
		log.info("---------------------------");		
	}
	
	/*
	 * JoinPoint
	 * 	타겟에 대한 정보와 상태를 담고 있는 객체로 매개 변수로 받아서 사용
	 */
	@Before("execution(* com.spring.service.Reply*.*(..))")
	public void logBeforeParams(JoinPoint joinpoint) {
		log.info("---------- Before ---------");
		log.info("Params: " + Arrays.toString(joinpoint.getArgs()));
		log.info("Target: " + joinpoint.getTarget());
		log.info("Method: " + joinpoint.getSignature().getName());
	}
	
	/*
	 * Around
	 * 	타겟의 메소드가 호출되기 이전 시점과 이후 시점에 모두 처리해야 할 필요가 있는 부가 기능 정의
	 * 	주 업무 로직을 실행하기 위해 JoinPoint의 하위 클래스인 
	 * 	ProceedingJoinPoint 타입의 파라미터를 필수적으로 선언해야 한다.
	 * 	(타겟 메소드의 실행 결과를 반환하기 위해서)
	 */
	/*
	@Around("execution(* com.spring.service.Board*.*(..))")
	public Object logTime(ProceedingJoinPoint pJoinpoint) {
		StopWatch stopwatch = new StopWatch();
		stopwatch.start();
		
		Object res = "";
		try {
			// 주 업무 로직을 실행하고 결과를 반환
			res = pJoinpoint.proceed();
		} catch(Throwable e) {
			e.printStackTrace();
		}
		
		stopwatch.stop();
		log.info("---------- Around ---------");
		log.info(pJoinpoint.getTarget().getClass().getName()
				+ "." + pJoinpoint.getSignature().getName());
		
		log.info("수행시간: " + stopwatch.getTotalTimeMillis() + "(ms)초");
		
		return res;
	}
	*/
	
	@Autowired
	LogService service;
	
	/*
	 * AfterThrowing
	 * 	타겟 메소드 실행 중 예외가 발생한 후에 실행할 부가 기능
	 * 	예외 발생 내역을 테이블에 등록
	 */
	@AfterThrowing(pointcut="execution(* com.spring.service.*.*(..))"
					, throwing="exception")
	public void logException(JoinPoint joinpoint, Exception exception) {
		// 예외 발생시 예외 내용을 테이블에 저장
		try {
			LogVo logVo = new LogVo();
			logVo.setClassname(joinpoint.getTarget().getClass().getName());
			logVo.setMethodname(joinpoint.getSignature().getName());
			logVo.setParams(Arrays.toString(joinpoint.getArgs()));
			logVo.setErrmsg(exception.getMessage());
			log.info("로그 테이블에 예외 발생 내역 저장");
			service.insert(logVo);
		} catch (Exception e) {
			log.info("로그 테이블에 예외 발생 내역 저장 중 오류 발생");
			log.info(e.getMessage());
			e.printStackTrace();
		}
	}
	
}
