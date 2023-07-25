package com.spring.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.vo.Criteria;
import com.spring.vo.Reply;

public interface ReplyMapper {

	/*
	 * 매개 변수가 두 개 이상 전달되는 경우 @Param 사용
	 */
	public List<Reply> getList(@Param(value="bno") int bno, @Param(value="criteria") Criteria criteria);
	
	public int getTotal(int bno);
	
	public int insert(Reply reply);
	
	public Reply read(int rno);
	
	public int update(Reply reply);
	
	public int delete(int rno);	
	
	public Reply selectOne(int rno);
	
}
