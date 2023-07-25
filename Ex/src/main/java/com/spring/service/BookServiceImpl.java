package com.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.spring.mapper.BookMapper;
import com.spring.vo.Book;
import com.spring.vo.Criteria;
import com.spring.vo.PageVo;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	BookMapper bookMapper;
	
	@Override
	public void getList(Criteria criteria, Model model) {
		/*
		 * 1. 목록 조회 - 검색, 페이지당 게시물 수(startnum~endnum)
		 * 2. 총 게시물 수 조회
		 * 3. pageVo 객체 생성 - 페이지 블록(startno~endno)
		 */		
		List<Book> list = bookMapper.getList(criteria);
		int total = bookMapper.getTotal(criteria);
		PageVo pageVo = new PageVo(criteria, total);
		
		model.addAttribute("list", list);
		model.addAttribute("pageVo", pageVo);
	}

	@Override
	public Book read(int no, Model model) {
		Book book = bookMapper.read(no);
		
		model.addAttribute(book);
		return book;
	}
	
}
