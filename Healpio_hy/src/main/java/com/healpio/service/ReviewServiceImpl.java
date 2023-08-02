package com.healpio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.healpio.mapper.ReviewMapper;
import com.healpio.vo.ReviewVO;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	ReviewMapper reviewMapper;
	
	@Override
	public int insert(ReviewVO reviewVO) {
		return reviewMapper.insert(reviewVO);
	}

}
