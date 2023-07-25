package com.spring.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.spring.vo.Criteria;
import com.spring.vo.Reply;

@Service
public interface ReplyService {

	List<Reply> getList(int bno, Criteria criteria);
	
	int getTotal(int bno);
	
	int insert(Reply reply);

	int update(Reply reply);
	
	int delete(int rno);
	
}
