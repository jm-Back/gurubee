package com.edoc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class EdocDAO {
	private Connection conn = DBConn.getConnection();
	/*
	public EdocEmpDTO loginMemberInfo(String id) {
		EdocEmpDTO empdto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			
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
				
		return empdto;
	}
	*/
	
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
	
	public void insertEAppro(String app_doc) {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			
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
