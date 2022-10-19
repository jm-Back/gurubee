package com.comp_notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.util.DBConn;

public class compNoticeDAO {
	private Connection conn = DBConn.getConnection();
	
	public void insertcompNotice(compNoticeDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			
			conn.setAutoCommit(false);
			
			sql = " INSERT INTO noticeAll(notice_num, notice_title, notice_content, views, regdate, id) "
					+ " VALUES(NOTICEALL_SEQ.NEXTVAL, ?,?,0,SYSDATE,?) ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getNotice_title());
			pstmt.setString(2, dto.getNotice_content());
			pstmt.setString(3, dto.getId());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			pstmt = null;
			
			sql = " INSERT INTO noticeAllFile(file_num,save_filename,ori_filename,notice_num) "
					+ " VALUES(NOTICEALLFILE_SEQ,?,?,NOTICEALL_SEQ.CURRVAL) ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getSave_filename());
			pstmt.setString(2, dto.getOri_filename());
			
			pstmt.executeUpdate();
			
			conn.commit();
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
					
				}
			}
			conn.setAutoCommit(true);
		}
	}
	
	public int dataCount() {
		int result = 0;
		String sql;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			sql = "SELECT COUNT(*) FROM noticeAll ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			if(rs != null) {
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
		
		return result;
	}
}
