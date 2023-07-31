package com.healpio.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.healpio.mapper.ClassMapper;
import com.healpio.vo.ClassVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class ClassMapperTest {

	@Autowired
	ClassMapper classMapper;
	
//	@Test
//	public void getExerciseList() {
//		classMapper.getExerciseList();
//	}
//	
//	@Test
//	public void insert() {
//		ClassVO classVO = new ClassVO();
//		classVO.setLocation_no(1);
//		classVO.setMember_no("M000002");
//		classVO.setExercise_no("E000002");
//		classVO.setClass_title("플로우 요가");
//		classVO.setClass_content("플로우 요가는 순환적인 동작으로 몸과 마음을 조화롭게 이어주는 요가입니다. 이 강의는 호흡과 운동의 조화로운 흐름을 경험하면서 스트레스 해소와 유연성 향상을 목표로 합니다. 강의 시작부터 마지막까지 자연스럽게 이어지는 움직임으로 심신의 안정과 피로 회복을 도모합니다. 천천히 흐르는 움직임들은 몸의 균형과 민첩성을 증진시키며, 정신적인 안정과 집중력을 향상시키는 데 도움이 됩니다. 이 강의는 기초 자세부터 고급 동작까지 다양한 레벨의 수강생을 대상으로 합니다. 특별한 준비나 경험이 없어도 참여 가능하며, 강사 이지영은 참석자들의 개인적인 요구에 맞게 세심한 지도를 제공합니다. 각자의 한계를 뛰어넘을 수 있도록 도와주는 이 강의는 체력과 유연성의 향상과 함께 긍정적인 마음가짐을 기르는 데 큰 도움이 될 것입니다.");
//		classVO.setClass_introduce("플로우 요가는 순환적인 동작으로 몸과 마음을 조화롭게 이어주는 요가입니다. 호흡과 운동의 조화로운 흐름을 경험하세요.");
//		classVO.setTeacher_content("서울대학교 체육학과 졸업 / 휘트니스 요가 스튜디오에서 5년간 주요 강사로서 활동");
//		classVO.setClass_maxcount(6);
//		classVO.setClass_price("22,000원/1회");		
//		classMapper.insert(classVO);
//	}
//	
//	@Test
//	public void getOne() {
//		classMapper.getOne("C000012");
//	}
//	
//	@Test
//	public void update() {
//		ClassVO classVO = new ClassVO();
//		classVO.setClass_no("C000012");
//		classVO.setLocation_no(1);
//		classVO.setExercise_no("E000002");
//		classVO.setClass_title("플로우 요가");
//		classVO.setClass_content("플로우 요가는 순환적인 동작으로 몸과 마음을 조화롭게 이어주는 요가입니다. 이 강의는 호흡과 운동의 조화로운 흐름을 경험하면서 스트레스 해소와 유연성 향상을 목표로 합니다. 강의 시작부터 마지막까지 자연스럽게 이어지는 움직임으로 심신의 안정과 피로 회복을 도모합니다. 천천히 흐르는 움직임들은 몸의 균형과 민첩성을 증진시키며, 정신적인 안정과 집중력을 향상시키는 데 도움이 됩니다. 이 강의는 기초 자세부터 고급 동작까지 다양한 레벨의 수강생을 대상으로 합니다. 특별한 준비나 경험이 없어도 참여 가능하며, 강사 이지영은 참석자들의 개인적인 요구에 맞게 세심한 지도를 제공합니다. 각자의 한계를 뛰어넘을 수 있도록 도와주는 이 강의는 체력과 유연성의 향상과 함께 긍정적인 마음가짐을 기르는 데 큰 도움이 될 것입니다.");
//		classVO.setClass_introduce("플로우 요가는 순환적인 동작으로 몸과 마음을 조화롭게 이어주는 요가입니다. 호흡과 운동의 조화로운 흐름을 경험하세요.");
//		classVO.setTeacher_content("서울대학교 체육학과 졸업 / 휘트니스 요가 스튜디오에서 5년간 주요 강사로서 활동");
//		classVO.setClass_maxcount(6);
//		classVO.setClass_price("22,000원/1회");		
//		classMapper.update(classVO);
//	}
//	
//	@Test
//	public void delete() {
//		classMapper.delete("C000015");
//	}
//	
//	@Test
//	public void scrap() {
//		classMapper.scrap("C000009", "M000005");
//	}
//	
//	@Test
//	public void cancelScrap() {
//		classMapper.cancelScrap("C000009", "M000005");
//	}
	
	@Test
	public void scrapYN() {
		classMapper.scrapYN("C000009", "M000005");
	}
	
}
