package com.spring.vo;

import lombok.Data;

@Data
public class LogVo {
	
	private String classname;
	private String methodname;
	private String params;
	private String errmsg;
	private String regdate;
	
}
