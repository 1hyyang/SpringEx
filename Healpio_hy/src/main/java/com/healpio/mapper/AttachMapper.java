package com.healpio.mapper;

import com.healpio.vo.AttachVO;

public interface AttachMapper {
	
	public int insert(AttachVO attach);
	public int delete(String class_no);
	public AttachVO getOne(String class_no);
	
}
