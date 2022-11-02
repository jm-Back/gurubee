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
	//프로젝트 참여자 모두 가져오기 (사원, 대리 급 아래만)
	public List<ProjectDTO> listemployee() {
		List<ProjectDTO> list = new ArrayList<ProjectDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			
			sql = " SELECT name, id, date_iss, dep_name, pos_name, pos_code FROM ( "
					+ "    SELECT name, id, dep_name, MAX(date_iss) date_iss, MIN(pos_name) pos_name, MAX(pos_code) pos_code "
					+ "    FROM ( "
					+ "    SELECT A.name, A.id, date_iss date_iss ,C.dep_name, D.pos_name ,D.pos_code "
					+ "    FROM Employee A "
					+ "    JOIN Employee_History B ON A.id = B.id "
					+ "    JOIN Department C ON B.dep_code = C.dep_code "
					+ "    JOIN Position D ON B.pos_code = D.pos_code "
					+ "    WHERE B.now_working = '재직' ) "
					+ "    GROUP BY name, id, dep_name "
					+ "    ORDER BY dep_name, pos_name ) "
					+ "WHERE pos_code < 4  ";
			
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
	
	
	//프로젝트 담당자 (과장급 이상)
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
					+ " WHERE B.now_working = '재직' AND B.pos_code >= 4 ) "
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
		
		
		//프로젝트 마스터 이름
		public String listProject_master(ProjectDTO dto) {
			String result = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql;
			
			try {
				
				sql = " SELECT name FROM Employee "
						+ " WHERE id = (SELECT pro_master FROM Project WHERE pro_code = ?)"; 
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, dto.getPro_code());
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					result = rs.getString(1); 
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(rs !=null) {
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
		
		
		// 프로젝트 등록하기
		public void insertProject(ProjectDTO dto) throws SQLException  {
			PreparedStatement pstmt = null;
			String sql;
			
			try {
				
				conn.setAutoCommit(false);
				
				//프로젝트 등록
				sql = "INSERT INTO Project(pro_code, id, pro_writer, pro_name, pro_clear, pro_type, pro_master, pro_outline, "
						+ " pro_content, pro_sdate, pro_edate ) "
						+ " VALUES(pro_seq.NEXTVAL, ?, ?, ?, '진행중', ?, ?, ?, ?, ?, ? ) ";
			
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, dto.getId_p()); //작성자 사번
				pstmt.setString(2, dto.getId_p()); //작성자 사번
				pstmt.setString(3, dto.getPro_name()); //프로젝트 이름
				pstmt.setString(4, dto.getPro_type());
				pstmt.setString(5, dto.getPro_master()); //총괄자
				pstmt.setString(6, dto.getPro_outline());
				pstmt.setString(7, dto.getPro_content());
				pstmt.setString(8, dto.getPro_sdate());
				pstmt.setString(9, dto.getPro_edate());
				
				pstmt.executeUpdate();
				
				pstmt.close();
				pstmt = null;
				
				//디테일 테이블 등록
				sql = " INSERT INTO Project_detail(pd_code, pro_code, pd_rank, pd_subject, pd_content, "
						+ " pd_part, pd_ing, pd_sdate, pd_edate, pd_writer ) "
						+ " VALUES (pd_seq.NEXTVAL, pro_seq.CURRVAL, 1, '챕터1', '내용', 100, 0, ?, ?, ? ) ";
				
				pstmt = conn.prepareStatement(sql);
				
				//파트 - 비중 : 100 / 진척율 : 0
				pstmt.setString(1, dto.getPro_sdate()); //첫 시작일은 프로젝트 시작일
				pstmt.setString(2, dto.getPro_edate()); //마지막도 마찬가지
				pstmt.setString(3, dto.getPro_master()); //담당자가 상세정보 1순위 가져감
				
				pstmt.executeUpdate();
				
				pstmt.close();
				pstmt = null;
				
				conn.commit();
				
				
			} catch (SQLException e) {
				try {
					conn.rollback();
				} catch (Exception e2) {
				}
				
				e.printStackTrace();
				throw e;
			
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

					sql = "INSERT INTO Project_join(pj_code, pro_code, id, pj_role ) "
							+ " 	VALUES(pj_seq.NEXTVAL , pro_seq.CURRVAL, ?, '참여자') ";
					
					pstmt =conn.prepareStatement(sql);
					pstmt.setString(1, dto.getPj_id());
					
					pstmt.executeUpdate();
					
				
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
		
		
		//1. 내 프로젝트 갯수 모두 가져오기! *list
		public int dataCount(ProjectDTO dto) {
			int result = 0;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql;
			
			try {
				
				//내 사번으로 참여자 중인 프로젝트 갯수
				sql = "SELECT COUNT(pro_code) pro_code FROM "
						+ "(SELECT DISTINCT A.pro_code "
						+ "		FROM Employee E "
						+ "		JOIN project A ON A.pro_master = E.id "
						+ "		JOIN project_detail B ON A.pro_code = B.pro_code "
						+ "		JOIN project_join C ON A.pro_code = C.pro_code "
						+ "		WHERE C.id = ? OR A.pro_master = ? "
						+ "	ORDER BY A.pro_sdate ASC )  ";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, dto.getPj_id());
				pstmt.setString(2, dto.getPj_id());
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					result = rs.getInt(1);
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(rs !=null) {
					try {
						rs.close();
					} catch (Exception e2) {
					}
				}
				
				if(pstmt !=null) {
					try {
						pstmt.close();
					} catch (Exception e2) {
					}
				}
			}
			
			return result;
		}
	
	
		// ** 내가 참여자 or 마스터인 프로젝트 리스트
		public List<ProjectDTO> listProject(ProjectDTO dto, int offset, int size){
			List<ProjectDTO> list = new ArrayList<>();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql;
			
			try {
				
				//내 사번으로 연관된 프로젝트 list
				sql = " SELECT DISTINCT E.ori_filename, E.name, A.pro_code, A.id id_p, A.pro_name, A.pro_clear, A.pro_type, A.pro_master, A.pro_outline, A.pro_content, A.pro_sdate, A.pro_edate "
						+ " , count(C.id) pj_count, NVL(pp, 0) pp, NVL(cc,0) cc "
						+ " FROM Employee E  "
						+ " JOIN project A ON A.pro_master = E.id  "
						+ " JOIN project_join C ON A.pro_code = C.pro_code  "
						+ " left join (select sum(pd_part) as pp,count(*) as cc , pro_code from project_detail where pd_ing > 0 group by pro_code) B on A.pro_code = B.pro_code "
						+ " WHERE (C.id = ?  OR A.pro_master = ?  OR A.id = ? ) "
						+ " group by E.ori_filename, E.name, A.pro_code, A.id, A.pro_name, A.pro_clear, A.pro_type, A.pro_master, A.pro_outline, A.pro_content, A.pro_sdate, A.pro_edate, pp, cc "
						+ " ORDER BY A.pro_sdate ASC  "
						+ " OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, dto.getPj_id());
				pstmt.setString(2, dto.getPj_id());
				pstmt.setString(3, dto.getPj_id());
				pstmt.setInt(4, offset);
				pstmt.setInt(5, size);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					ProjectDTO dto2 = new ProjectDTO();
					
					dto2.setPro_profile(rs.getString("ori_filename"));
					dto2.setName_p(rs.getString("name"));
					dto2.setPro_code(rs.getString("pro_code"));
					dto2.setId_p(rs.getString("id_p")); //작성한사람의 사번
					dto2.setPro_name(rs.getString("pro_name"));
					dto2.setPro_clear(rs.getString("pro_clear"));
					dto2.setPro_type(rs.getString("pro_type"));
					dto2.setPro_master(rs.getString("pro_master"));
					dto2.setPro_outline(rs.getString("pro_outline"));
					dto2.setPro_content(rs.getString("pro_content"));
					dto2.setPro_sdate(rs.getDate("pro_sdate").toString());
					dto2.setPro_edate(rs.getDate("pro_edate").toString());
					dto2.setPj_id(rs.getString("pj_count"));
					dto2.setPd_part(rs.getInt("pp"));
					dto2.setPd_ing(rs.getInt("cc"));

					list.add(dto2);
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
				
				if(pstmt !=null) {
					try {
						pstmt.close();
					} catch (Exception e2) {
					}
				}
			}

			return list;
		}
		
		//프로젝트 article 주요 정보 출력~~~~~~~~~
		public ProjectDTO readProject(String pro_code, String me_id) {
			ProjectDTO dto = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql;
			
			try {
				
				String pro_master_id = null;
				
				//1.해당 프로젝트 내용 읽기 (내 사번, 프로젝트 코드 필요)
				sql = " SELECT E.name, E.mail, E.phone, E.tel, E.id id_p, B.pd_ing, "
						+ " A.pro_code, A.pro_writer pro_writer, B.pd_writer pd_writer, A.pro_name, A.pro_clear, A.pro_type, B.pd_code, A.pro_master, A.pro_outline, A.pro_content, A.pro_sdate, A.pro_edate "
						+ " FROM Employee E "
						+ " JOIN project A ON A.pro_master = E.id "
						+ " JOIN project_detail B ON A.pro_code = B.pro_code "
						+ " JOIN project_join C ON A.pro_code = C.pro_code "
						+ " WHERE (C.id = ? OR A.pro_master = ? OR A.id = ? ) AND A.pro_code = ? "
						+ " ORDER BY A.pro_sdate DESC ";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, me_id);
				pstmt.setString(2, me_id);
				pstmt.setString(3, me_id);
				pstmt.setString(4, pro_code);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					dto = new ProjectDTO();
					
					dto.setPro_writer(rs.getString("pro_writer")); //작성자,편집자 사번
					dto.setPro_name(rs.getString("pro_name")); //프로젝트명
					dto.setPro_clear(rs.getString("pro_clear")); //진행중
					dto.setPro_type(rs.getString("pro_type")); //타입
					dto.setPd_code(rs.getString("pd_code")); //pd code
					dto.setPd_ing(rs.getInt("pd_ing")); //진행률
					dto.setPro_master(rs.getString("name")); //담당자 이름
					dto.setPro_outline(rs.getString("pro_outline"));
					dto.setPro_content(rs.getString("pro_content"));
					dto.setPro_sdate(rs.getDate("pro_sdate").toString());
					dto.setPro_edate(rs.getDate("pro_edate").toString());
					dto.setPro_mail(rs.getString("mail"));
					dto.setPro_phone(rs.getString("phone"));
					dto.setPro_tel(rs.getString("tel"));
					dto.setPd_writer(rs.getString("pd_writer"));
					//마스터 사번
					dto.setId_p(rs.getString("id_p"));
					
					pro_master_id = dto.getId_p();
					
				}
				
				rs.close();
				rs = null;
				pstmt.close();
				pstmt = null;
				
				//프로젝트 담당자 부서,급
				sql = "SELECT name, dep_name, MIN(pos_name) pos_name, ori_filename "
						+ "FROM ( "
						+ "SELECT A.name, A.id, date_iss date_iss ,C.dep_name, D.pos_name, A.ori_filename "
						+ "FROM Employee A "
						+ "JOIN Employee_History B ON A.id = B.id "
						+ "JOIN Department C ON B.dep_code = C.dep_code "
						+ "JOIN Position D ON B.pos_code = D.pos_code "
						+ "WHERE A.id = ? ) "
						+ "GROUP BY name, id, dep_name, ori_filename "
						+ "ORDER BY dep_name, pos_name ";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, pro_master_id);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					dto.setDep_name(rs.getString("dep_name"));
					dto.setPos_name(rs.getString("pos_name"));
					dto.setPro_profile(rs.getString("ori_filename"));
					
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

			return dto;
		}
		
		
		//프로젝트 수정
		public void updateProject(ProjectDTO dto) throws SQLException {
			PreparedStatement pstmt = null;
			String sql;
			
			try {
				
				sql = " UPDATE Project SET pro_writer=?, pro_type= ? , pro_name = ?, pro_outline=?, pro_content=?, pro_sdate=?, pro_edate=?  WHERE pro_code = ? ";
				
				pstmt = conn.prepareStatement(sql);
				
				//id_p = 마지막 수정자 사번으로 변경하기!
				pstmt.setString(1, dto.getId_p());
				pstmt.setString(2, dto.getPro_type());
				pstmt.setString(3, dto.getPro_name());
				pstmt.setString(4, dto.getPro_outline());
				pstmt.setString(5, dto.getPro_content());
				pstmt.setString(6, dto.getPro_sdate());
				pstmt.setString(7, dto.getPro_edate());
				
				pstmt.setString(8, dto.getPro_code());
				
				pstmt.executeUpdate();
				
				
			} catch (SQLException e) {		
				e.printStackTrace();
				throw e;
				
			} finally {
				if(pstmt !=null) {
					try {
						pstmt.close();
					} catch (SQLException e2) {
					}
				}
			}
			
			
		}
		
		//프로젝트 수정폼 :  등록된 내용 출력
		public ProjectDTO readUpdateProject(String pro_code) {
			ProjectDTO dto = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql;
			
			try {
				
				//프로젝트 코드로 읽기
				sql = " SELECT pro_code, pro_type, id, pro_name, pro_type, pro_master, pro_outline, pro_content "
						+ ", pro_sdate, pro_edate "
						+ "FROM project "
						+ "WHERE pro_code = ? ";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, pro_code);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					dto = new ProjectDTO();
					
					dto.setPro_code(rs.getString("pro_code"));
					dto.setId_p(rs.getString("id"));
					dto.setPro_name(rs.getString("pro_name"));
					dto.setPro_type(rs.getString("pro_type"));
					dto.setPro_master(rs.getString("pro_master"));
					dto.setPro_outline(rs.getString("pro_outline"));
					dto.setPro_content(rs.getString("pro_content"));
					dto.setPro_sdate(rs.getDate("pro_sdate").toString());
					dto.setPro_edate(rs.getDate("pro_edate").toString());
				}
				
				rs.close();
				pstmt.close();
				rs = null;
				pstmt = null;
				
				//프로젝트 담당자 성함,부서
				sql = "SELECT name, dep_name, MIN(pos_name) pos_name "
						+ "FROM ( "
						+ "SELECT A.name, A.id, date_iss date_iss ,C.dep_name, D.pos_name "
						+ "FROM Employee A "
						+ "JOIN Employee_History B ON A.id = B.id "
						+ "JOIN Department C ON B.dep_code = C.dep_code "
						+ "JOIN Position D ON B.pos_code = D.pos_code "
						+ "WHERE A.id = ? ) "
						+ "GROUP BY name, id, dep_name "
						+ "ORDER BY dep_name, pos_name ";
				
				
				pstmt = conn.prepareStatement(sql);
				//마스터 사번으로 검색
				pstmt.setString(1, dto.getPro_master());
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					dto.setName_p(rs.getString("name"));
					
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

			return dto;
		}
		
		//프로젝트 article : 참여자 정보 가져오기
		//프로젝트 코드 필요함!
		public List<ProjectDTO> listProjectEmployee(String pro_code) throws SQLException {
			List<ProjectDTO> list = new ArrayList<>();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql;
			
			try {
				
				//*프로젝트 디테일 코드 구하기
				/*sql = " SELECT pd_code FROM project_detail WHERE pro_code = ? ";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, pro_code);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					pd_code = rs.getString(1);
				}
				
				rs.close();
				pstmt.close();
				rs = null;
				pstmt = null; */
				
				//list로 받기
				sql = " SELECT name, dep_name, MIN(pos_name) pos_name, id, ori_filename "
						+ " FROM ( "
						+ " SELECT A.name, A.id, date_iss date_iss ,C.dep_name, D.pos_name, A.ori_filename "
						+ " FROM Employee A "
						+ " JOIN Employee_History B ON A.id = B.id "
						+ " JOIN Department C ON B.dep_code = C.dep_code "
						+ " JOIN Position D ON B.pos_code = D.pos_code "
						+ " WHERE A.id in  (SELECT id FROM Employee WHERE id in ( "
						+ " SELECT id FROM project_join "
						+ " WHERE pro_code = ? ) )) "
						+ " GROUP BY name, id, dep_name, ori_filename "
						+ " ORDER BY dep_name, pos_name  ";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, pro_code);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					ProjectDTO dto = new ProjectDTO();
					
					dto.setName_p(rs.getString("name"));
					dto.setDep_name(rs.getString("dep_name"));
					dto.setPos_name(rs.getString("pos_name"));
					dto.setPj_id(rs.getString("id"));;
					dto.setPro_profile(rs.getString("ori_filename"));
					
					list.add(dto);
				}

			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
				
			} finally {
				if(rs!=null) {
					try {
						rs.close();
					} catch (Exception e2) {
					}
				}
				
				if(pstmt !=null) {
					try {
						pstmt.close();
					} catch (Exception e2) {
					}
				}
			}
			
			return list;
		}
		
		//프로젝트 삭제
		public void deleteProject(String pro_code) throws SQLException {
			PreparedStatement pstmt = null;
			String sql;
			
			//순서 : 참여자 - 디테일 - 프로젝트순
			
			try {
				//참여자 테이블 삭제
				conn.setAutoCommit(false);
				
				sql = " DELETE FROM project_join WHERE pro_code = ? ";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, pro_code);
				
				pstmt.executeUpdate();
				
				pstmt.close();
				pstmt = null;
				
				sql = " DELETE FROM project_detail WHERE pro_code = ? ";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, pro_code);
				
				pstmt.executeUpdate();
				
				pstmt.close();
				pstmt = null;
				
				sql = " DELETE FROM project WHERE pro_code = ? ";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, pro_code);
				
				pstmt.executeUpdate();
				
				pstmt.close();
				pstmt = null;
				
				conn.commit();
				

			} catch (SQLException e) {
				try {
					conn.rollback();
				} catch (Exception e2) {
				}
				e.printStackTrace();
				throw e;
				
			} finally {
				if(pstmt!=null) {
					try {
						
					} catch (Exception e2) {
					}
				}
				
				try {
					conn.setAutoCommit(true);
				} catch (Exception e2) {
				}
			}
			
			
			
		}
		
		//사번 읽어서 정보 가져오기
		public ProjectDTO readId(String id_p) throws SQLException {
			PreparedStatement pstmt = null;
			ProjectDTO dto = new ProjectDTO();
			ResultSet rs = null;
			String sql = null;
			
			try {
				
				sql = "SELECT id, name, dep_name, MIN(pos_name) pos_name, ori_filename "
						+ "FROM ( "
						+ "SELECT A.name, A.id, date_iss date_iss ,C.dep_name, D.pos_name, A.ori_filename "
						+ "FROM Employee A "
						+ "JOIN Employee_History B ON A.id = B.id "
						+ "JOIN Department C ON B.dep_code = C.dep_code "
						+ "JOIN Position D ON B.pos_code = D.pos_code "
						+ "WHERE A.id = ? ) "
						+ "GROUP BY name, id, dep_name, ori_filename "
						+ "ORDER BY dep_name, pos_name ";
				
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, id_p);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					
					dto.setId_p(rs.getString("id"));
					dto.setName_p(rs.getString("name"));
					dto.setDep_name(rs.getString("dep_name"));
					dto.setPos_name(rs.getString("pos_name"));
					dto.setPro_profile(rs.getString("ori_filename"));
					
				}
				

			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
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
			
			return dto;
		}
		
		//프로젝트 참여자 삭제하기 (ajax 로 만들기!!)
		public void deleteEmployeeList(String pj_id, String pro_code) throws SQLException {
			PreparedStatement pstmt = null;
			String sql;
			
			try {
				sql = " DELETE FROM project_join "
						+ " WHERE id = ? AND pro_code = ? ";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, pj_id);
				pstmt.setString(2, pro_code);
				
				pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			} finally {
				if(pstmt !=null) {
					try {
						pstmt.close();
					} catch (Exception e) {
					}
				}
			}
			

		}
		
		//프로젝트 참여자 추가 
		public void addEmployee(String pro_code, String pj_id) throws SQLException {
			PreparedStatement pstmt = null;
			String sql;
			
			try {
				
				sql = " INSERT INTO project_join(pj_code, pro_code, id, pj_role) "
						+ " VALUES(pj_seq.NEXTVAL, ?, ?, '참여자') ";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, pro_code);
				pstmt.setString(2, pj_id);
				pstmt.executeUpdate();
				
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			} finally {
				if(pstmt !=null) {
					try {
						pstmt.close();
					} catch (Exception e2) {
					}
				}
			}
		}
		
		public int CountEmployee(String pro_code) throws SQLException {
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql;
			int result = 0;
			
			try {
				
				sql = " SELECT COUNT(*) FROM project_join "
						+ " WHERE pro_code = ? ";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, pro_code);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					result = rs.getInt(1);
				}
				
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
				
			} finally {
				if(pstmt!=null) {
					try {
						pstmt.close();
					} catch (Exception e2) {
					}
				}
				
				if(rs!=null) {
					try {
						rs.close();
					} catch (Exception e2) {
					}
				}
			}
			
			return result;
		}
		
		
		
		
		//프로젝트 참여자 중복 검사
		public int checkEmployee(String pro_code, String pj_id) throws SQLException {
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			int result = 0;
			String sql;
			
			try {
				
				sql = " SELECT COUNT(*) FROM project_join WHERE pro_code = ? AND id = ? ";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, pro_code);
				pstmt.setString(2, pj_id);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					result = rs.getInt(1);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			} finally {
				if(rs!=null) {
					try {
						rs.close();
					} catch (Exception e2) {
					}
				}
				if(pstmt !=null) {
					try {
						pstmt.close();
					} catch (Exception e2) {
					}
				}
			}

			return result;
			
		}
		
		
		//세부 프로젝트 디테일 - list 정보 가져오기
		public List<ProjectDTO> detailProjectlist(String pro_code, int offset, int size) throws SQLException{
			List<ProjectDTO> list = new ArrayList<>();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql;
			
			try {
				
				sql = " SELECT pd_code, pro_code, pd_rank, pd_subject, pd_content, pd_part, pd_ing, pd_sdate, pd_edate, ori_filename, ee.name name FROM ( "
						+ "SELECT pd_code, pro_code, pd_rank, pd_subject, pd_content, pd_part, pd_ing, "
						+ " pd_sdate, pd_edate, pd_writer "
						+ " FROM project_detail "
						+ " WHERE pro_code = ? "
						+ " ORDER BY pd_sdate ASC ) pp left outer join employee ee on pp.pd_writer = ee.id "
						+ " ORDER BY pd_sdate ASC "
						+ " OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, pro_code);
				pstmt.setInt(2, offset);
				pstmt.setInt(3, size);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					ProjectDTO dto = new ProjectDTO();
					
					dto.setPd_code(rs.getString("pd_code"));
					dto.setPro_code(rs.getString("pro_code"));
					dto.setPd_rank(rs.getInt("pd_rank"));
					dto.setPd_subject(rs.getString("pd_subject"));
					dto.setPd_content(rs.getString("pd_content"));
					dto.setPd_part(rs.getInt("pd_part"));
					dto.setPd_ing(rs.getInt("pd_ing"));
					dto.setPd_sdate(rs.getDate("pd_sdate").toString());
					dto.setPd_edate(rs.getDate("pd_edate").toString());
					dto.setPd_writer(rs.getString("ori_filename"));
					dto.setName_p(rs.getString("name"));
					
					list.add(dto);
					
				}

			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			} finally {
				if(rs!=null) {
					try {
						rs.close();
					} catch (Exception e2) {
					}
				}
				
				if(pstmt !=null) {
					try {
						pstmt.close();
					} catch (Exception e2) {
					}
				}
			}

			return list;
		}
		
		
		//세부 프로젝트 사항 갯수
		public int dataCountDetail(String pro_code) {
			int result = 0;
			PreparedStatement pstmt = null;
			ResultSet rs= null;
			String sql;
			
			try {
				
				sql = " SELECT COUNT(*) FROM project_detail "
						+ " WHERE pro_code = ? ";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, pro_code);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					result = rs.getInt(1);
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
			
			return result;
		}
		
		
		//세부 프로젝트 추가 = insert (기존 프로젝트들의 max part 를 변경해야한다...ㅎㅎ..)
		public void insertProjectDetail(ProjectDTO dto, String pro_code) throws SQLException {
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql;
			
			int pd_rank = 0;
			
			
			try {
				
				conn.setAutoCommit(false);
				
				//1. 기존 프로젝트 내용들에서 pd_rank 값 참고해야함... ㅎ 
				//pd, pro 코드는 있음
				sql = " select MAX(pd_rank) pd_rank from project_detail where pro_code = ? GROUP BY pd_code";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, pro_code);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					pd_rank = rs.getInt(1);
				}
				
				pstmt.close();
				pstmt = null;
				
				
				//2. 세부 프로젝트 내용 추가 (ing = 0 / part 는 기존 프로젝트들에서 나누기 / pd_rank 도 기존것에서 +1
				sql = " INSERT INTO project_detail(pd_code, pro_code, pd_rank, pd_subject, pd_content, "
						+ " pd_part, pd_ing, pd_sdate, pd_edate, pd_writer ) "
						+ " VALUES(pd_seq.NEXTVAL, ?, ?, ?, ?, 100, 0, ?, ?, ? ) ";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, pro_code);
				pstmt.setInt(2, pd_rank+1);
				pstmt.setString(3, dto.getPd_subject());
				pstmt.setString(4, dto.getPd_content());
				pstmt.setString(5, dto.getPd_sdate().toString());
				pstmt.setString(6, dto.getPd_edate().toString());
				pstmt.setString(7, dto.getPd_writer());
			
				pstmt.executeQuery();
				
				conn.commit();
				
				
			} catch (SQLException e) {
				try {
					conn.rollback();
				} catch (Exception e2) {
				}
				
				e.printStackTrace();
				throw e;
			} finally {
				if(pstmt!=null) {
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
		
		//2- 프로젝트 챕터 갯수 구하기
		public int detailCount(String pro_code) throws SQLException {
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql;
			int result = 0;
			
			try {
				
				sql = " SELECT COUNT(*) FROM project_detail WHERE pro_code = ? ";
				
				pstmt = conn.prepareStatement(sql);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					result = rs.getInt(1);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
				
			} finally {
				if(pstmt!=null) {
					try {
						pstmt.close();
					} catch (Exception e2) {
					}
				}
				
				if(rs!=null) {
					try {
						rs.close();
					} catch (Exception e2) {
					}
				}
			}
			
			
			return result;
			
		}
		
		//지분 변경 *
		//프로젝트 갯수로 part 지분 나누기 !!
		public void updatePart(int partCount, String pro_code) throws SQLException {
			PreparedStatement pstmt = null;
			String sql;
			
			try {
				
				sql = " UPDATE project_detail SET pd_part = 100 / ? WHERE pro_code = ? ";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, partCount);
				pstmt.setString(2, pro_code);
				
				pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			} finally {
				if(pstmt!=null) {
					try {
						pstmt.close();
					} catch (Exception e2) {
					}
				}
			}
		}
		
		
		//프로젝트 지분 총합! pro_code 의 pd_part 를 모두 더한 것 (main이랑 article 에서 볼 것)
		public int partAll(String pro_code) throws SQLException {
			PreparedStatement pstmt = null;
			ResultSet rs= null;
			int result = 0;
			String sql;
			
			try {
				
				sql = " SELECT SUM(pd_part) FROM project_detail "
						+ " WHERE pro_code = ? AND pd_ing > 0 ";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, pro_code);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					result = rs.getInt(1);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
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

			return result;
		}
		
		//프로젝트별 지분...
		
		
		
		
		//프로젝트 챕터 삭제하기 / 삭제하면 pd_code 로 
		public void deleteProjectDetail(String pd_code) throws SQLException {
			PreparedStatement pstmt = null;
			String sql;
			
			try {
				
				sql = " DELETE FROM project_detail WHERE pd_code = ? ";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, pd_code);
				
				pstmt.executeUpdate();
	
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			} finally {
				if(pstmt!=null) {
					try {
						pstmt.close();
					} catch (Exception e2) {
					}
				}
				
			}
			
			
		}
		
		
		//프로젝트 챕터 내용 수정하는 기능
		public void updateProjectDetail(ProjectDTO dto, String pd_code) throws SQLException {
			PreparedStatement pstmt = null;
			String sql;
			
			try {
				
				sql = " UPDATE project_detail SET pd_subject = ? , pd_content = ? , pd_sdate = ? , pd_edate = ? "
						+ " WHERE pd_code = ? ";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, dto.getPd_subject());
				pstmt.setString(2, dto.getPd_content());
				pstmt.setString(3, dto.getPd_sdate());
				pstmt.setString(4, dto.getPd_edate());
				
				pstmt.setString(5, pd_code);
				
				pstmt.executeUpdate();
				
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			} finally {
				if(pstmt!=null) {
					try {
						pstmt.close();
					} catch (Exception e2) {
					}
				}
			}
			
		}
		
		
		//프로젝트 상태 변경.... 완료처리..! 
		public void clearProjectDetail(String pd_code) throws SQLException {
			PreparedStatement pstmt = null;
			String sql;
			
			try {
				
				sql = " UPDATE project_detail SET pd_ing = pd_part WHERE pd_code = ? ";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, pd_code);
				
				pstmt.executeUpdate();
				
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			} finally {
				if(pstmt!=null) {
					try {
						pstmt.close();
					} catch (Exception e2) {
					}
				}
			}
			
		}
		
		//filterProject
		// ** 내가 참여자 or 마스터인 프로젝트 리스트 + 필터
		public List<ProjectDTO> filterProject(ProjectDTO dto, String pro_type){
			List<ProjectDTO> list = new ArrayList<>();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql;
			
			try {
				
				//내 사번으로 연관된 프로젝트 list
				sql = " SELECT DISTINCT E.ori_filename, E.name, A.pro_code, A.id id_p, A.pro_name, A.pro_clear, A.pro_type, A.pro_master, A.pro_outline, A.pro_content, A.pro_sdate, A.pro_edate "
						+ " , count(C.id) pj_count, NVL(pp, 0) pp, NVL(cc,0) cc "
						+ "FROM Employee E "
						+ "JOIN project A ON A.pro_master = E.id  "
						+ "JOIN project_join C ON A.pro_code = C.pro_code  "
						+ "left join (select sum(pd_part) as pp,count(*) as cc , pro_code from project_detail where pd_ing > 0 group by pro_code) B on A.pro_code = B.pro_code "
						+ "WHERE (C.id = ?  OR A.pro_master = ? OR A.id = ?) AND A.pro_type = ? "
						+ "group by E.ori_filename, E.name, A.pro_code, A.id, A.pro_name, A.pro_clear, A.pro_type, A.pro_master, A.pro_outline, A.pro_content, A.pro_sdate, A.pro_edate, pp, cc "
						+ "ORDER BY A.pro_sdate ASC ";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, dto.getPj_id());
				pstmt.setString(2, dto.getPj_id());
				pstmt.setString(3, dto.getPj_id());
				
				pstmt.setString(4, pro_type);

				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					ProjectDTO dto2 = new ProjectDTO();
					
					dto2.setPro_profile(rs.getString("ori_filename"));
					dto2.setName_p(rs.getString("name"));
					dto2.setPro_code(rs.getString("pro_code"));
					dto2.setId_p(rs.getString("id_p")); //작성한사람의 사번
					dto2.setPro_name(rs.getString("pro_name"));
					dto2.setPro_clear(rs.getString("pro_clear"));
					dto2.setPro_type(rs.getString("pro_type"));
					dto2.setPro_master(rs.getString("pro_master"));
					dto2.setPro_outline(rs.getString("pro_outline"));
					dto2.setPro_content(rs.getString("pro_content"));
					dto2.setPro_sdate(rs.getDate("pro_sdate").toString());
					dto2.setPro_edate(rs.getDate("pro_edate").toString());
					dto2.setPj_id(rs.getString("pj_count"));
					dto2.setPd_part(rs.getInt("pp"));
					dto2.setPd_ing(rs.getInt("cc"));


					list.add(dto2);
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
				
				if(pstmt !=null) {
					try {
						pstmt.close();
					} catch (Exception e2) {
					}
				}
			}

			return list;
		}
	
		
		
	
	
}
