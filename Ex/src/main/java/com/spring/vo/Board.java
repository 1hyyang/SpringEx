package com.spring.vo;

import java.util.List;

import lombok.Data;

@Data
public class Board {
	
	private int bno;
	private String title;
	private String content;
	private String writer;
	private String regdate;
	private String updatedate;
	
	private List<Attach> attachList;
	
}
