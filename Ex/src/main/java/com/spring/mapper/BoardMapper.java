package com.spring.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.spring.vo.Board;
import com.spring.vo.Criteria;

public interface BoardMapper {

	@Select("SELECT * FROM TBL_BOARD")
	public List<Board> getListByAnnotation();

	public List<Board> getList(Criteria criteria);
	
	public int insert(Board board);
	
	public Board read(int bno);
	
	public int delete(int bno);
	
	public int update(Board board);
	
	public int getTotal(Criteria criteria);
	
	// 파라미터가 2개 이상인 경우 Param 어노테이션 필수
	public int updateReplyCnt(@Param("bno") int bno, @Param("amount") int amount);

} 
