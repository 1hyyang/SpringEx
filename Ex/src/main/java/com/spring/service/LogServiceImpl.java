package com.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.mapper.LogMapper;
import com.spring.vo.LogVo;

@Service
public class LogServiceImpl implements LogService {

	@Autowired
	LogMapper mapper;
	
	@Override
	public int insert(LogVo logVo) {
		return mapper.insert(logVo);		
	}

}
