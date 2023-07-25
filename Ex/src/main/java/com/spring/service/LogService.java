package com.spring.service;

import org.springframework.stereotype.Service;

import com.spring.vo.LogVo;

@Service
public interface LogService {

	public int insert(LogVo logVo);
	
}
