package com.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.spring.vo.Book;
import com.spring.vo.Criteria;

@Service
public interface BookService {

	public void getList(Criteria criteria, Model model);

	public Book read(int no, Model model);
	
}
