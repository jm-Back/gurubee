package com.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.util.DBConn;

public class LoginDAO {
	private Connection conn = DBConn.getConnection();
	
	public LoginDTO loginMember(String id, String pwd) {
		LoginDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// StringBuilder sb = new StringBuilder();
		String sql;
		
		try {
			sql = "SELECT id, name, pwd, reg, mail, phone, tel"
					+ " FROM employee "
					+ " WHERE id=? AND pwd=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new LoginDTO();
				
				dto.setId(rs.getString("id"));
				dto.setPwd(rs.getString("pwd"));
				dto.setName(rs.getString("name"));
				dto.setReg(rs.getString("reg"));
				dto.setEmail(rs.getString("mail"));
				dto.setPhone(rs.getString("phone"));
				dto.setTel(rs.getString("tel"));
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if( rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}

		return dto;
	}
	
	public void updateMember(String id, String pwd) {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE employee pwd=? WHERE id=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, pwd);
			pstmt.setString(2, id);
			
			pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
	}
	
}
