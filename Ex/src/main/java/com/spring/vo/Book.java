package com.spring.vo;

import lombok.Data;

@Data
public class Book {

	private int no;		// 도서 일련번호
	private String title;	// 도서명
	private String author;	// 작가
	private String rentyn;	// 도서 대여여부
	
	private String ofile; // 원본 파일명
	private String sfile; // 저장된 파일명
	
	private String rentno; // 대여번호
	private String id; // 대여자 id
	private String startdate; // 대여시작일
	private String enddate; // 반납가능일
	private String returndate; // 반납일
	
}
