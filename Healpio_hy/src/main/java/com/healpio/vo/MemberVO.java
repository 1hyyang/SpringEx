package com.healpio.vo;

import java.util.List;

import lombok.Data;

@Data
public class MemberVO {

	private String member_no;
	private String member_id;
	private String member_pw;
	private String member_name;
	private String email;
	private String nickname;
	private String teacheryn;
	private String adminyn;
	private String phonenumber;
	
	// 사용자 권한
	private List<String> role;
	
}
