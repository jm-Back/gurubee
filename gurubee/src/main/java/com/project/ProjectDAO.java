package com.project;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;


public class ProjectDAO {
	private Connection conn = DBConn.getConnection();
	
	//---------------
	//프로젝트 참여자 모두 가져오기
	public List<ProjectDTO> listemployee() {
		List<ProjectDTO> list = new ArrayList<ProjectDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			
			sql = " SELECT name, id, dep_name, MAX(date_iss) date_iss, MIN(pos_name) pos_name "
				+ " FROM ( "
				+ " SELECT A.name, A.id, date_iss date_iss ,C.dep_name, D.pos_name "
				+ " FROM Employee A "
				+ " JOIN Employee_History B ON A.id = B.id "
				+ " JOIN Department C ON B.dep_code = C.dep_code "
				+ " JOIN Position D ON B.pos_code = D.pos_code "
				+ " WHERE B.now_working = '재직' ) "
				+ " GROUP BY name, id, dep_name "
				+ " ORDER BY dep_name, pos_name ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ProjectDTO dto = new ProjectDTO();
				
				dto.setName_p(rs.getString("name"));
				dto.setId_p(rs.getString("id"));
				dto.setDate_iss(rs.getString("date_iss"));
				dto.setDep_name(rs.getString("dep_name"));
				dto.setPos_name(rs.getString("pos_name"));
				
				list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		
		return list;
	}
	
	
	//프로젝트 총괄자 (대리급 이상)
		public List<ProjectDTO> listMaster() {
			List<ProjectDTO> list = new ArrayList<ProjectDTO>();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql;
			
			try {
				
				sql = " SELECT name, id, dep_name, MAX(date_iss) date_iss, MIN(pos_name) pos_name "
					+ " FROM ( "
					+ " SELECT A.name, A.id, date_iss date_iss ,C.dep_name, D.pos_name "
					+ " FROM Employee A "
					+ " JOIN Employee_History B ON A.id = B.id "
					+ " JOIN Department C ON B.dep_code = C.dep_code "
					+ " JOIN Position D ON B.pos_code = D.pos_code "
					+ " WHERE B.now_working = '재직' AND B.pos_code >= 3 ) "
					+ " GROUP BY name, id, dep_name "
					+ " ORDER BY dep_name, pos_name ";
				
				pstmt = conn.prepareStatement(sql);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					ProjectDTO dto = new ProjectDTO();
					
					dto.setName_p(rs.getString("name"));
					dto.setId_p(rs.getString("id"));
					dto.setDate_iss(rs.getString("date_iss"));
					dto.setDep_name(rs.getString("dep_name"));
					dto.setPos_name(rs.getString("pos_name"));
					
					list.add(dto);
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(rs!=null) {
					try {
						rs.close();
					} catch (Exception e2) {
					}
				}
				
				if(pstmt!=null) {
					try {
						pstmt.close();
					} catch (Exception e2) {
					}
				}
			}
			
			
			return list;
		}
		
		// 프로젝트 등록하기
		public void insertProject(ProjectDTO dto)  {
			PreparedStatement pstmt = null;
			String sql;
			
			try {
				
				conn.setAutoCommit(false);
				
				//프로젝트 등록
				sql = "INSERT INTO Project(pro_code, id, pro_name, pro_clear, pro_type, pro_master, pro_outline, "
						+ " pro_content, pro_sdate, pro_edate ) "
						+ " VALUES(pro_seq.NEXTVAL, ?, ?, '진행중', ?, ?, ?, ?, ?, ? ) ";
			
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, dto.getId_p()); //작성자 사번
				pstmt.setString(2, dto.getPro_name()); //프로젝트 이름
				pstmt.setString(3, dto.getPro_type());
				pstmt.setString(4, dto.getPro_master()); //총괄자
				pstmt.setString(5, dto.getPro_outline());
				pstmt.setString(6, dto.getPro_content());
				pstmt.setString(7, dto.getPro_sdate());
				pstmt.setString(8, dto.getPro_edate());
				
				pstmt.executeUpdate();
				
				pstmt.close();
				pstmt = null;
				
				//디테일 테이블 등록
				sql = " INSERT INTO Project_detail(pd_code, pro_code, pd_rank, pd_subject, pd_content, "
						+ " pd_part, pd_ing, pd_sdate, pd_edate ) "
						+ " VALUES (pd_seq.NEXTVAL, pro_seq.CURRVAL, 1, '챕터1', '내용', 100, 0, ?, ? ) ";
				
				pstmt = conn.prepareStatement(sql);
				
				//파트 - 비중 : 100 / 진척율 : 0
				pstmt.setString(1, dto.getPro_sdate()); //첫 시작일은 프로젝트 시작일
				pstmt.setString(2, dto.getPro_edate()); //마지막도 마찬가지
				
				pstmt.executeUpdate();
				
				pstmt.close();
				pstmt = null;
				
				conn.commit();
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			
			} finally {
				if(pstmt !=null) {
					try {
						pstmt.close();
					} catch (Exception e) {
					}
				}
				
				try {
					conn.setAutoCommit(true);
				} catch (Exception e2) {
				}
			}
			
			
		}
		
		//참여자 테이블 등록 
		public void insertEmployee(ProjectDTO dto)  {
			PreparedStatement pstmt = null;
			String sql;
			
			try {
				//참여자 테이블 등록 (참여자 목록만큼 돌려줌)

					sql = "INSERT INTO Project_join(pj_code, id, pj_role ) "
							+ " 	VALUES(pd_seq.CURRVAL, ?, '참여자') ";
					
					pstmt =conn.prepareStatement(sql);
					pstmt.setString(1, dto.getPj_id());
					
					pstmt.executeUpdate();
					
					pstmt.close();
					pstmt = null;


				conn.commit();
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			
			} finally {
				if(pstmt !=null) {
					try {
						pstmt.close();
					} catch (Exception e) {
					}
				}
				
				try {
					conn.setAutoCommit(true);
				} catch (Exception e2) {
				}
			}
			
			
		}
	
	
	
	
	
	
	
}
