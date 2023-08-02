package com.healpio.mapper;

import java.util.List;

import com.healpio.vo.AttachVO;

public interface AttachMapper {
	
	public int insert(AttachVO attach);
	public List<AttachVO> getList(String class_no);
	public int delete(String class_no);
	
}
