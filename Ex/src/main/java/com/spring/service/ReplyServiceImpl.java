package com.spring.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.mapper.BoardMapper;
import com.spring.mapper.ReplyMapper;
import com.spring.vo.Criteria;
import com.spring.vo.Reply;

@Service
public class ReplyServiceImpl implements ReplyService{

	@Autowired
	ReplyMapper replyMapper;
	
	@Autowired
	BoardMapper boardMapper;
	
	@Override
	public List<Reply> getList(int bno, Criteria criteria) {
		return replyMapper.getList(bno, criteria);
	}

	@Override
	public int getTotal(int bno) {
		return replyMapper.getTotal(bno);
	}
	
	/*
	 * Transactional
	 * 	서비스 로직에 대한 트랜잭션 처리를 지원
	 * 	모두 성공하면 commit, 오류 발생시 rollback
	 */
	@Transactional
	@Override
	public int insert(Reply reply) {
		// 답글 입력시 Board 테이블의 답글 수를 1 증가
		boardMapper.updateReplyCnt(reply.getBno(), 1);
		return replyMapper.insert(reply);
	}
	
	@Override
	public int update(Reply reply) {
		return replyMapper.update(reply);
	}

	@Transactional
	@Override
	public int delete(int rno) {
		// bno를 얻기 위해 rno를 통해 Reply 객체를 가져 온다.
		Reply reply = replyMapper.selectOne(rno);
		boardMapper.updateReplyCnt(reply.getBno(), -1);
		return replyMapper.delete(rno);
	}

}
