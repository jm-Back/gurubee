package com.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.util.DBConn;

public class LoginDAO {
	private Connection conn = DBConn.getConnection();
	
	public SessionInfo searchMember(String id, String pwd) {
		SessionInfo dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// StringBuilder sb = new StringBuilder();
		String sql;
		
		try {
			sql = "SELECT id, pwd, reg "
					+ " FROM employee"
					+ " WHERE id = ? AND pwd = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new SessionInfo();
				
				dto.setId(rs.getString("id"));
				dto.setPwd(rs.getString("pwd"));
				dto.setReg(rs.getString("reg"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return dto;
	}
	
	public SessionInfo loginMember(String id, String pwd) {
		SessionInfo dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// StringBuilder sb = new StringBuilder();
		String sql;
		
		try {
			/*
			  sql = " SELECT name, id, pwd, dep_name, MAX(date_iss) date_iss, MIN(pos_name) pos_name, reg "
			            + " FROM ( "
			            + " SELECT A.name, A.id, B.date_iss ,C.dep_name, D.pos_name, A.pwd, A.reg "
			            + " FROM Employee A "
			            + " JOIN Employee_History B ON A.id = B.id "
			            + " JOIN Department C ON B.dep_code = C.dep_code "
			            + " JOIN Position D ON B.pos_code = D.pos_code "
			            + " WHERE B.now_working = '재직' and A.id=? and A.pwd=? ) "
			            + " GROUP BY name, id, pwd, dep_name, reg "
			            + " ORDER BY dep_name, pos_name ";
			  
			  */
			
			 sql = "SELECT his.id, pwd, name, reg, mail, phone, tel, pos_name, dep_name, dep_code,"
			 		+ "	ori_filename, save_filename "
			 		+ " FROM (SELECT his_no, date_iss, reason, id, pos_code, dep_code, division, "
			 		+ "    now_working, type, startdate, enddate, "
			 		+ "    ROW_NUMBER() OVER(PARTITION BY id ORDER BY pos_code DESC) as now "
					+ "    FROM employee_history)his LEFT OUTER JOIN employee e ON his.id = e.id "
					+ " LEFT OUTER JOIN department d ON his.dep_code = d.dep_code "
					+ " LEFT OUTER JOIN position p ON p.pos_code = his.pos_code "
					+ " where his.id=? and pwd=? and now=1 and now_working='재직' "
					+ " order by enddate DESC ";
			 
			
			  
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new SessionInfo();
				
				dto.setId(rs.getString("id"));
				dto.setPwd(rs.getString("pwd"));
				dto.setName(rs.getString("name"));
				dto.setReg(rs.getString("reg"));
				dto.setEmail(rs.getString("mail"));
				dto.setPhone(rs.getString("phone"));
				dto.setTel(rs.getString("tel"));
				dto.setSave_filename(rs.getString("save_filename"));
				dto.setOri_filename(rs.getString("ori_filename"));
				
				dto.setPos_name(rs.getString("pos_name"));
				dto.setDep_name(rs.getString("dep_name"));
				dto.setDep_code(rs.getString("dep_code"));
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
	
	public void updateMember(String id, String pwd) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE employee SET pwd=? WHERE id=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, pwd);
			pstmt.setString(2, id);
			
			pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
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
