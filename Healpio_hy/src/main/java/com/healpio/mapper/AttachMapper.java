package com.healpio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.healpio.vo.AttachVO;

public interface AttachMapper {
	
	public int insert(AttachVO attach);
//	public List<AttachVO> getList(int bno);
//	public int delete(@Param("uuid") String uuid, @Param("bno") int bno);
//	public AttachVO selectOne(@Param("uuid") String uuid, @Param("bno") int bno);
	
}
