package com.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.service.BookService;
import com.spring.vo.Book;
import com.spring.vo.Criteria;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/book/*")
@Log4j
public class BookController {
	
	@Autowired
	BookService bookService;

	@GetMapping("list")
	public void getList(Criteria criteria, Model model) {
		bookService.getList(criteria, model);
	}
	
	@GetMapping("read")
	public void read(Book book, Model model) {
		bookService.read(book.getNo(), model);
	}
	
	@GetMapping("insert")
	public void insert() {
		
	}
	
}
