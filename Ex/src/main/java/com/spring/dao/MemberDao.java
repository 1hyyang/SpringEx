package com.spring.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Component;

import com.spring.vo.Member;

@Component
public class MemberDao {
	
	public Member login(Member paramMember) {
		Member member = null;		
		String sql = String.format("SELECT * FROM MEMBER WHERE ID = '%s' AND PW = '%s'"
				, paramMember.getId(), paramMember.getPw());
		try (Connection conn = ConnectionUtil.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);){
			if(rs.next()) {			
				member = new Member();
				member.setId(rs.getString("ID"));
				member.setName(rs.getString("NAME"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return member;
	}
	
}
