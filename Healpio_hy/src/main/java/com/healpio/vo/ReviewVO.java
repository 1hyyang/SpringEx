package com.healpio.vo;

import lombok.Data;

@Data
public class ReviewVO {

	private String review_no;
	private String member_no;
	private String class_no;
	private String review_regdate;
	private String review_content;
	private double review_star;
	
}
