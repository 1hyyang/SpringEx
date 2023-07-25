package com.spring.mapper;

import java.util.List;

import com.spring.vo.Book;
import com.spring.vo.Criteria;

public interface BookMapper {

	public List<Book> getList(Criteria criteria);

	public int getTotal(Criteria criteria);
	
	public Book read(int no);
	
}
