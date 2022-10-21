package com.edoc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class EdocDAO {
	private Connection conn = DBConn.getConnection();
	
	public EdocEmpDTO loginMemberInfo(String id) {
		EdocEmpDTO edocEmp = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			/*
			LoginDAO 로 옮김
			sql = "SELECT e.id, name, mail, phone, tel, pos_name, dep_name "
					+ " FROM (SELECT his_no, date_iss, reason, id, pos_code, dep_code, division, "
					+ "    now_working, type, startdate, enddate, "
					+ "    ROW_NUMBER() OVER(PARTITION BY id ORDER BY pos_code DESC) as now "
					+ "    FROM employee_history)his LEFT OUTER JOIN employee e ON his.id = e.id "
					+ " LEFT OUTER JOIN department d ON his.dep_code = d.dep_code "
					+ " LEFT OUTER JOIN position p ON p.pos_code = his.pos_code "
					+ " where e.id=? and now=1 and now_working='재직' "
					+ " order by enddate DESC;";
			*/
			sql = "";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				// edocemp = new EdocEmpDTO();
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
				
		return null;
	}
	
	
	public List<EdocEmpDTO> deptEmpList(String id) {
		List<EdocEmpDTO> list = new ArrayList<>();
		
		return list;
	} 
	
	
}
