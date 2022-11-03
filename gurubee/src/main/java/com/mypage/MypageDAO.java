package com.mypage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class MypageDAO {
private Connection conn = DBConn.getConnection();
	//---------
	// mypage에 사용자 정보 불러오기
	public UserDTO selectemployee(String id) {
		PreparedStatement pstmt = null;
		String sql;
		ResultSet rs = null;
		UserDTO dto = null;
		
		try {
		
			sql = "SELECT his.id, pwd, name, reg, mail, phone, tel, pos_name, dep_name,"
			 		+ "	ori_filename "
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
				dto = new UserDTO();
				
				dto.setId(rs.getString("id"));
				dto.setPwd(rs.getString("pwd"));
				dto.setName(rs.getString("name"));
				String reg = rs.getString("reg");
				if(reg != null) {
					String [] rr = reg.split("-");
					if(rr.length==2) {
						dto.setReg(rr[0]);
						dto.setReg2(rr[1]);
					}
				}
				
				
				dto.setEmail(rs.getString("mail"));
				dto.setPhone(rs.getString("phone"));
				dto.setTel(rs.getString("tel"));
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
	public void mypageWriteForm(UserDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
		
			sql = "UPDATE employee SET pwd=?, reg=?, mail=?, phone=?, tel=?, "
					+ " ori_filename=?  WHERE id=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getPwd());
			pstmt.setString(2, dto.getReg());
			pstmt.setString(3, dto.getEmail());
			pstmt.setString(4, dto.getPhone());
			pstmt.setString(5, dto.getTel());
			pstmt.setString(6, dto.getOri_filename());
			pstmt.setString(7, dto.getId());
			 
			pstmt.executeUpdate();
			
		
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e2) {
				}
			}
		}
	}
	
	// 게시물 리스트
		public List<MypageDTO> myattlist(int offset, int size) {
			List<MypageDTO> list = new ArrayList<MypageDTO>();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			StringBuilder sb = new StringBuilder();

			try {
				sb.append(" SELECT att_id, att_start, att_end, att_ing, a.id");
				sb.append("       TO_CHAR(reg_date, 'YYYY-MM-DD') reg_date ");
				sb.append(" FROM attendance ");
				sb.append(" JOIN employee a ON a.id = id ");
				sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setInt(1, offset);
				pstmt.setInt(2, size);

				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					MypageDTO dto = new MypageDTO();

					dto.setAtt_id(rs.getString("att_id"));
					dto.setAtt_start(rs.getString("att_start"));
					dto.setAtt_end(rs.getString("att_end"));
					dto.setAtt_ing(rs.getString("att_ing"));
					dto.setId(rs.getString("Id"));
					
					list.add(dto);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException e2) {
					}
				}
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e2) {
					}
				}
			}

			return list;
		}
		
		public List<MypageDTO> listAttendance(String date, String id) {
			List<MypageDTO> list = new ArrayList<MypageDTO>();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			StringBuilder sb = new StringBuilder();

			try {
				sb.append(" SELECT att_id, TO_CHAR(att_start, 'HH24:MI:SS') att_start, TO_CHAR(att_end, 'HH24:MI:SS') att_end, att_ing, a.id");
				sb.append(" FROM attendance a");
				sb.append(" JOIN employee e ON a.id = e.id ");
				sb.append(" WHERE a.id = ? AND TO_CHAR(att_start, 'YYYYMM') = ? ");
				sb.append(" ORDER BY att_start ASC ");
				
				
				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setString(1, id);
				pstmt.setString(2, date);

				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					MypageDTO dto = new MypageDTO();

					dto.setAtt_id(rs.getString("att_id"));
					dto.setAtt_start(rs.getString("att_start"));
					dto.setAtt_end(rs.getString("att_end"));
					dto.setAtt_ing(rs.getString("att_ing"));
					dto.setId(rs.getString("id"));
					
					list.add(dto);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException e2) {
					}
				}
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e2) {
					}
				}
			}

			return list;
	}
		
	public MypageDTO readAttendance(String date, String id) {
		MypageDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append(" SELECT att_id, TO_CHAR(att_start, 'HH24:MI:SS') att_start, TO_CHAR(att_end, 'HH24:MI:SS') att_end, att_ing, a.id");
			sb.append(" FROM attendance a ");
			sb.append(" JOIN employee e ON a.id = e.id ");
			sb.append(" WHERE a.id = ? AND TO_CHAR(att_start, 'YYYYMMDD') = ? ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, id);
			pstmt.setString(2, date);

			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				dto = new MypageDTO();

				dto.setAtt_id(rs.getString("att_id"));
				dto.setAtt_start(rs.getString("att_start"));
				dto.setAtt_end(rs.getString("att_end"));
				dto.setAtt_ing(rs.getString("att_ing"));
				dto.setId(rs.getString("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e2) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e2) {
				}
			}
		}

		return dto;
	}
		
		
	// 근태등록
	public void attendanceInsert(MypageDTO mdto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = " INSERT INTO attendance (att_id, att_start, att_end, att_ing, id) "
					+ " VALUES (attendance_seq.NEXTVAL, SYSDATE, NULL, ?, ?) ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, mdto.getAtt_ing());
			pstmt.setString(2, mdto.getId());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}

	}

	public void attendanceupdate(MypageDTO mdto) throws SQLException {
		PreparedStatement pstmt = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append("UPDATE attendance SET ");
			sb.append("   att_end=SYSDATE, att_ing = att_ing || '/' || ? ");
			sb.append("   WHERE att_id=? AND Id=?");
			
			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setString(1, mdto.getAtt_ing());
			pstmt.setString(2, mdto.getId());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}

	}
}
