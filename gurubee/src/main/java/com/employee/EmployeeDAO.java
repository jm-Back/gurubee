package com.employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class EmployeeDAO {
	private Connection conn = DBConn.getConnection();
	//신입사원 등록 
	public void joinEmployee(EmployeeDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			conn.setAutoCommit(false);
			sql = "INSERT INTO employee(id,pwd,name,reg,phone,tel,mail) VALUES (?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getPwd());
			pstmt.setString(3, dto.getName());
			pstmt.setString(4, dto.getReg());

			pstmt.setString(5, dto.getPhone());
			
			pstmt.setString(6, dto.getTel3());
			
			pstmt.setString(7, dto.getEmail());
			
			
			pstmt.executeUpdate();
			
			pstmt.close();
			pstmt = null;
			
			sql = "INSERT INTO employee_history(his_No, id, pos_code, dep_code, division, now_working, type, startdate) VALUES (Employee_history_SEQ.NEXTVAL,?, ?, ?,'입사','재직',?,?)";
			pstmt=conn.prepareStatement(sql);
		
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getPos_code());
			pstmt.setString(3, dto.getDep_code());
			pstmt.setString(4, dto.getType());
			pstmt.setString(5, dto.getStartdate());
			
			pstmt.executeUpdate();
			

			conn.commit();

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e2) {
			}
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
			
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e2) {
			}
		}
		
	}
	 //모든 사원리스트 
	
    public List<EmployeeDTO> employeeList() { 
			List<EmployeeDTO> list = new ArrayList<EmployeeDTO>();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql;
			
			try {

					 sql = "SELECT * From ( "
					 	+ " SELECT e.id, name, mail, phone, tel, dep_name, pos_name,type, now_working, division,TO_CHAR(startdate, 'YYYY-MM-DD') startdate ,TO_CHAR(date_iss, 'YYYY-MM-DD') date_iss, "
					 	+ " rank() over (partition by e.id order by startdate desc) rank "
					 	+ " FROM ( SELECT his_no, date_iss, reason, id, pos_code, dep_code, division, now_working, type, startdate, enddate, "
					 	+ " ROW_NUMBER() OVER (PARTITION by id ORDER BY pos_code desc) as now "
					 	+ " FROM employee_history )his LEFT OUTER JOIN employee e ON his.id = e.id"
					 	+ " LEFT OUTER JOIN department d ON his.dep_code = d.dep_code"
					 	+ " LEFT OUTER JOIN position p ON p.pos_code = his.pos_code "
					 	+ " ) where rank = 1 ";
	
				 pstmt = conn.prepareStatement(sql);
			 	
				 rs = pstmt.executeQuery();
				
				while(rs.next()) {
					EmployeeDTO dto = new EmployeeDTO();
					
					dto.setName(rs.getString("name"));
					dto.setId(rs.getString("id"));
					dto.setPhone(rs.getString("phone"));
					dto.setTel(rs.getString("tel"));
					dto.setEmail(rs.getString("mail"));
					dto.setDate_iss(rs.getString("date_iss"));
					dto.setDept_name(rs.getString("dep_name"));
					dto.setPos_name(rs.getString("pos_name"));
					dto.setType(rs.getString("type"));
					dto.setNow_working(rs.getString("now_working"));
					dto.setDivision(rs.getString("division"));
					dto.setStartdate(rs.getString("startdate"));
					
					
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
		
     //아이디로 정보불러오기 
     
		public EmployeeDTO readEmployee(String Id) {
			EmployeeDTO dto = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql;
			
			try {  sql ="SELECT * From ( "
				 	+ " SELECT e.id, name, mail, phone,reg,tel, his.dep_code,his.pos_code,type, now_working, division,TO_CHAR(startdate, 'YYYY-MM-DD') startdate ,TO_CHAR(date_iss, 'YYYY-MM-DD') date_iss, ori_filename, "
				 	+ " rank() over (partition by e.id order by startdate desc) rank "
				 	+ " FROM ( SELECT his_no, date_iss, reason, id, pos_code, dep_code, division, now_working, type, startdate, enddate, "
				 	+ " ROW_NUMBER() OVER (PARTITION by id ORDER BY pos_code desc) as now "
				 	+ " FROM employee_history )his LEFT OUTER JOIN employee e ON his.id = e.id"
				 	+ " LEFT OUTER JOIN department d ON his.dep_code = d.dep_code"
				 	+ " LEFT OUTER JOIN position p ON p.pos_code = his.pos_code "
				 	+ " ) where rank = 1  and id=? ";
			
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1,Id);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					dto = new EmployeeDTO();
					dto.setName(rs.getString("name"));
					dto.setId(rs.getString("id"));
					dto.setPhone(rs.getString("phone"));
					String s[] = rs.getString("phone").split("-");
					if(s.length==3) {
						dto.setPhone1(s[0]);			
						dto.setPhone2(s[1]);
						dto.setPhone3(s[2]);
					}	
					dto.setTel3(rs.getString("tel"));
					
					dto.setReg(rs.getString("reg"));
					String ss[] = rs.getString("reg").split("-");
					if(ss.length==2) {
						dto.setReg1(ss[0]);
						dto.setReg2(ss[1]);
					}
					
					dto.setEmail(rs.getString("mail"));
					String sss[] = rs.getString("mail").split("@");
					if(sss.length==2) {
						dto.setEmail1(sss[0]);
						dto.setEmail2(sss[1]);
					}
					
					dto.setDate_iss(rs.getString("date_iss"));
					dto.setDep_code(rs.getString("dep_code"));
					dto.setPos_code(rs.getString("pos_code"));
					dto.setType(rs.getString("type"));
					dto.setNow_working(rs.getString("now_working"));
					dto.setDivision(rs.getString("division"));
					dto.setStartdate(rs.getString("startdate"));
					dto.setOri_filename(rs.getString("ori_filename"));
					
				
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if(rs != null) {
					try {
						rs.close();
					} catch (SQLException e) {
					}
				}
					
				if(pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
					}
				}
			}
			
			return dto;
		}	
		
	// 사원정보 수정하기 
	public void updateEmployee(EmployeeDTO dto) throws SQLException { 
		PreparedStatement pstmt =null;
		String sql;
		
	    try {
			sql = "UPDATE employee SET tel=? WHERE Id=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1,dto.getTel3());
			pstmt.setString(2, dto.getId());

			pstmt.executeUpdate();
			
			pstmt.close();
			pstmt = null;
			
			sql = "UPDATE employee_history SET division='승진', now_working='재직' , type=? WHERE id = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getType());
			pstmt.setString(2, dto.getId());
			
			pstmt.executeUpdate();

	    } catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}

	}
	
}