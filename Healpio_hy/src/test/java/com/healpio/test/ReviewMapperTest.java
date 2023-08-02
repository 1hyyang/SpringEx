package com.healpio.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.healpio.mapper.ReviewMapper;
import com.healpio.vo.ReviewVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class ReviewMapperTest {

	@Autowired
	ReviewMapper reviewMapper;
	
	@Test
	public void insert() {
		ReviewVO reviewVO = new ReviewVO();
		reviewVO.setMember_no("M000002");
		reviewVO.setClass_no("C000072");
		reviewVO.setReview_content("선생님 최고입니다.");
		reviewVO.setReview_star(5);
		reviewMapper.insert(reviewVO);
	}

}
