package com.edoc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.util.DBConn;

public class EdocDAO {
	private Connection conn = DBConn.getConnection();
	
	public EdocEmpDTO loginMemberInfo(String id) {
		EdocEmpDTO logindto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT e.id, name, mail, phone, tel, pos_name, dep_name "
					+ " FROM (SELECT his_no, date_iss, reason, id, pos_code, dep_code, division, "
					+ "    now_working, type, startdate, enddate, "
					+ "    ROW_NUMBER() OVER(PARTITION BY id ORDER BY pos_code DESC) as now "
					+ "    FROM employee_history)his LEFT OUTER JOIN employee e ON his.id = e.id "
					+ " LEFT OUTER JOIN department d ON his.dep_code = d.dep_code "
					+ " LEFT OUTER JOIN position p ON p.pos_code = his.pos_code "
					+ " where e.id=? and now=1 and now_working='재직' "
					+ " order by enddate DESC;";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				logindto = new EdocEmpDTO();
				logindto.setId(rs.getString("is"));
				logindto.setName(rs.getString("name"));
				logindto.setEmail("mail");
				logindto.setPhone("phone");
				logindto.setTel(rs.getString("tel"));
				logindto.setDept(rs.getString("dep_name"));
				logindto.setPisition(rs.getString("pos_name"));
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
				
		return logindto;
	}
	
	
}
