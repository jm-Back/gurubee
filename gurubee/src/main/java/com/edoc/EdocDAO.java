package com.edoc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class EdocDAO {
	private Connection conn = DBConn.getConnection();
	
	// 전자결재문서 
	public int insertEApproval(EdocDTO edocdto) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int app_num = 0; // 전자결재번호. 
		String sql;
		
		try {
			sql = "INSERT INTO E_APPROVAL (app_num, app_doc, id, app_date, doc_form, doc_num, temp) "
					+ " VALUES (APPVAL_SEQ.NEXTVAL, ?, ?, SYSDATE, ?, ?, ?) ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, edocdto.getApp_doc());
			pstmt.setString(2, edocdto.getId_write());
			pstmt.setString(3, edocdto.getDoc_form());
			pstmt.setString(4, edocdto.getDoc_num());
			pstmt.setInt(5, edocdto.getTemp());
			
			rs = pstmt.executeQuery();
			
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
		
		return app_num;
	}
	
	// 전자결재자 
	public void insertEApprover(EdocEmpDTO empdto) {
		PreparedStatement pstmt = null;
		String sql;
		int app_num=0;
		ResultSet rs = null;
		
		try {
			sql = "SELECT LAST_NUMBER as app_num "
					+ " FROM USER_SEQUENCES "
					+ " WHERE SEQUENCE_NAME='APPVAL_SEQ' ";
				
			pstmt = conn.prepareStatement(sql);
				
			rs = pstmt.executeQuery();
				
			if(rs.next()) {
				app_num = rs.getInt(1)-1;
			}
			
			pstmt.close();
			
			if(app_num==0) {
				return;
			}
			
			sql = "INSERT INTO E_APPROVER(apper_num, app_num, id, app_result, memo, app_level, app_date) "
				+ " VALUES(APPVER_SEQ.NEXTVAL, ?, ?, ?, ?, ?, SYSDATE)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, app_num);
			pstmt.setString(2, empdto.getId_apper());
			pstmt.setInt(3, 0); // 대기
			pstmt.setString(4, empdto.getMemo());
			pstmt.setInt(5, empdto.getApp_level());
			
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
	
	// 특정 직급의 모든 부서 사원 리스트 가져오기
	public List<EdocEmpDTO> posEmpList(int pos_code) {
		List<EdocEmpDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT his.id, name, dep_name, pos_name, p.pos_code "
					+ " FROM (SELECT his_no, date_iss, reason, id, pos_code, dep_code, division, "
					+ "    now_working, type, startdate, enddate, "
					+ "    ROW_NUMBER() OVER(PARTITION BY id ORDER BY pos_code DESC) as now "
					+ "    FROM employee_history "
					+ "    WHERE now_working='재직')his LEFT OUTER JOIN employee e ON his.id = e.id "
					+ " LEFT OUTER JOIN department d ON his.dep_code = d.dep_code "
					+ " LEFT OUTER JOIN position p ON p.pos_code = his.pos_code "
					+ " where now=1 and now_working='재직' and p.pos_code = ? " // 직급코드
					+ " order by enddate DESC ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, pos_code);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				EdocEmpDTO empdto = new EdocEmpDTO();
				
				empdto.setId_apper(rs.getString("id"));
				empdto.setName_apper(rs.getString("name"));
				empdto.setDep_name(rs.getString("dep_name"));
				empdto.setPos_code(rs.getInt("pos_code"));
				empdto.setPos_name(rs.getString("pos_name"));
				
				list.add(empdto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}  finally {
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
		
		return list;
	} 

	// 문서구분에 따른 문서폼 가져오기
	public EdocFormDTO findByForm(String app_doc) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		EdocFormDTO formdto = null;
		
		try {
			sql = "SELECT app_doc, doc_form, form_num "
				+ " FROM E_DOCFORM"
				+ "	WHERE app_doc = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, app_doc);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				formdto = new EdocFormDTO();
				
				formdto.setApp_doc(rs.getString("app_doc"));
				formdto.setDoc_form(rs.getString("doc_form"));
				formdto.setForm_num(rs.getInt("form_num"));
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
		
		return formdto;
	}
	
		
	
	
}
