package com.spring.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.spring.vo.Board;
import com.spring.vo.Criteria;

@Service
public interface BoardService {
	
	public void getList(Criteria criteria, Model model);
	
	public int insert(Board board, List<MultipartFile> files) throws Exception;
	
	public Board read(int bno);
	
	public int delete(int bno, Criteria criteria);
	
	public int update(Board board, List<MultipartFile> files) throws Exception;
	
	public int updateReplyCnt(int bno, int amount);
	
}
