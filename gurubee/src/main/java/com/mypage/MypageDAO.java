package com.mypage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.login.SessionInfo;
import com.util.DBConn;

public class MypageDAO {
private Connection conn = DBConn.getConnection();
	//---------
	// mypage에 사용자 정보 불러오기
	public SessionInfo selectemployee(String id) {
		PreparedStatement pstmt = null;
		String sql;
		ResultSet rs = null;
		SessionInfo dto = null;
		
		try {
		
			sql = "SELECT his.id, pwd, name, reg, mail, phone, tel, pos_name, dep_name,"
			 		+ "	ori_filename, save_filename "
			 		+ " FROM (SELECT his_no, date_iss, reason, id, pos_code, dep_code, division, "
			 		+ "    now_working, type, startdate, enddate, "
			 		+ "    ROW_NUMBER() OVER(PARTITION BY id ORDER BY pos_code DESC) as now "
					+ "    FROM employee_history)his LEFT OUTER JOIN employee e ON his.id = e.id "
					+ " LEFT OUTER JOIN department d ON his.dep_code = d.dep_code "
					+ " LEFT OUTER JOIN position p ON p.pos_code = his.pos_code "
					+ " where his.id=? and now=1 and now_working='재직' "
					+ " order by enddate DESC ";
			 
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			
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

	//mypage_write 작성
	public void mypageWriteForm(SessionInfo dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
		
			sql = "UPDATE employee pwd=?, reg=?, email=?, phone=?, tel=?, "
					+ " save_filename=?  WHERE id=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getPwd());
			pstmt.setString(2, dto.getReg());
			pstmt.setString(3, dto.getEmail());
			pstmt.setString(4, dto.getPhone());
			pstmt.setString(5, dto.getTel());
			pstmt.setString(7, dto.getSave_filename());
			pstmt.setString(8, dto.getId());
			 
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

