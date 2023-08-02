package com.healpio.service;

import org.springframework.stereotype.Service;

import com.healpio.vo.ReviewVO;

@Service
public interface ReviewService {

	public int insert(ReviewVO reviewVO);
	
}
