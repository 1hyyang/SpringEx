package com.spring.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.vo.Attach;

public interface AttachMapper {
	
	public int insert(Attach attach);
	public List<Attach> getList(int bno);
	public int delete(@Param("uuid") String uuid, @Param("bno") int bno);
	public Attach selectOne(@Param("uuid") String uuid, @Param("bno") int bno);
	
}
