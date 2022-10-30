package com.schedule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class ScheduleDAO {
	private Connection conn = DBConn.getConnection();
	
	public List<ScheduleDTO> listMonth(String startDay, String endDay, String id){
		List<ScheduleDTO> list =new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb =  new StringBuilder();
		
		try {
			sb.append(" SELECT sch_num, S.sc_code, sch_name, sch_content, sch_sdate, ");
			sb.append(" 	sch_edate, sch_stime, sch_etime, sch_repeat, sch_repeat_c, sch_reg_date ");
			sb.append(" 	, C.sc_color ");
			sb.append(" FROM schedule S ");
			sb.append(" JOIN schedule_color C ON S.sc_code = C.sc_code ");
			sb.append(" WHERE id = ? AND ");
			sb.append("	  ( ");
			sb.append("      ( ");
			sb.append("			( TO_DATE(sch_sdate, 'YYYYMMDD') >= TO_DATE(?, 'YYYYMMDD') ");
			sb.append("				AND TO_DATE(sch_sdate, 'YYYYMMDD') <= TO_DATE(?, 'YYYYMMDD') ");
			sb.append("			) OR  (TO_DATE(sch_edate, 'YYYYMMDD') <= TO_DATE(?, 'YYYYMMDD')  ");
			sb.append("			  AND TO_DATE(sch_edate, 'YYYYMMDD') <= TO_DATE(?, 'YYYYMMDD')   ");
			sb.append("			) ");
			sb.append("		   ) OR ("); //일정 반복 부분 출력
			sb.append(" 		sch_repeat = 1 AND sch_repeat_c != 0 ");
			sb.append("				AND TO_CHAR(ADD_MONTHS(sch_sdate, 12 * sch_repeat_c * TRUNC(((SUBSTR(? , 1 , 4) - SUBSTR(sch_sdate , 1 , 4)) / sch_repeat_c ))), 'YYYYMMDD') >= ? ");
			sb.append("				AND TO_CHAR(ADD_MONTHS(sch_sdate, 12 * sch_repeat_c * TRUNC(((SUBSTR(? , 1 , 4) - SUBSTR(sch_sdate , 1 , 4)) / sch_repeat_c ))), 'YYYYMMDD') <= ? ");
			sb.append("			)");
			sb.append("	 )   ");
			sb.append(" ORDER BY sch_sdate ASC, sch_num DESC ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, id);
			pstmt.setString(2, startDay);
			pstmt.setString(3, endDay);
			pstmt.setString(4, startDay);
			pstmt.setString(5, endDay);

			pstmt.setString(6, startDay);
			pstmt.setString(7, startDay);
			pstmt.setString(8, startDay);
			pstmt.setString(9, endDay);

			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ScheduleDTO dto = new ScheduleDTO();
				
				dto.setSch_num(rs.getString("sch_num"));
				dto.setSc_code(rs.getString("sc_code"));
				dto.setSch_name(rs.getString("sch_name"));
				dto.setSch_content(rs.getString("sch_content"));
				dto.setSch_sdate(rs.getString("sch_sdate"));
				dto.setSch_edate(rs.getString("sch_edate"));
				dto.setSch_stime(rs.getString("sch_stime"));
				dto.setSch_etime(rs.getString("sch_etime"));
				dto.setSch_repeat(rs.getInt("sch_repeat"));
				dto.setSch_repeat_c(rs.getInt("sch_repeat_c"));
				dto.setSch_reg_date(rs.getString("sch_reg_date"));
				dto.setSc_color(rs.getString("sc_color"));
				
				
				list.add(dto);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs !=null) {
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
	
	public void insertSchedule(ScheduleDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			
			sql = " INSERT INTO schedule(sch_num, id, sc_code, sch_name, sch_content, sch_sdate, sch_edate, sch_stime, sch_etime, sch_repeat, sch_repeat_c, sch_reg_date "
					+ ") VALUES(schnum_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE ) ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getSch_id());
			pstmt.setString(2, dto.getSc_code());
			pstmt.setString(3, dto.getSch_name());
			pstmt.setString(4, dto.getSch_content());
			pstmt.setString(5, dto.getSch_sdate());
			pstmt.setString(6, dto.getSch_edate());
			pstmt.setString(7, dto.getSch_stime());
			pstmt.setString(8, dto.getSch_etime());
			pstmt.setInt(9, dto.getSch_repeat());
			pstmt.setInt(10, dto.getSch_repeat_c());
			
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
	
	//삭제
	public void delete(String sch_num, String id) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			
			sql = " DELETE FROM schedule WHERE sch_num = ? AND id = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, sch_num);
			pstmt.setString(2, id);
			
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
	
	
	
	
	public List<ScheduleDTO> listDay(String date, String id) throws SQLException{
		List<ScheduleDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			
			sb.append(" SELECT sch_num, S.sc_code, sch_name, sch_sdate, ");
			sb.append(" 	sch_edate, TO_CHAR(sch_reg_date, 'YYYYMMDD') sch_reg_date ");
			sb.append(" 	, C.sc_color ");
			sb.append(" FROM schedule S ");
			sb.append(" JOIN schedule_color C ON S.sc_code = C.sc_code ");
			sb.append(" WHERE id = ? AND ");
			sb.append("	  ( ");
			sb.append("      ( ");
			sb.append("			 TO_DATE(sch_sdate, 'YYYYMMDD') = TO_DATE(?, 'YYYYMMDD') ");
			sb.append("			 OR (sch_edate IS NOT NULL AND TO_DATE(sch_sdate, 'YYYYMMDD') <= TO_DATE(?, 'YYYYMMDD') AND TO_DATE(sch_edate, 'YYYYMMDD') >= TO_DATE(?, 'YYYYMMDD'))  ");
			sb.append("		  ) OR ( "); //반복일정
			sb.append("			  sch_repeat=1 AND MOD(MONTHS_BETWEEN(TO_DATE(sch_sdate, 'YYYYMMDD'), TO_DATE(?, 'YYYYMMDD'))/12, sch_repeat_c) = 0  ");
			sb.append("		  ) ");
			sb.append("	 ) ");
			sb.append(" ORDER BY sch_num DESC ");
			
			pstmt =conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, id);
			pstmt.setString(2, date);
			pstmt.setString(3, date);
			pstmt.setString(4, date);
			
			pstmt.setString(5, date);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ScheduleDTO dto = new ScheduleDTO();
				
				dto.setSch_num(rs.getString("sch_num"));
				dto.setSc_code(rs.getString("sc_code"));
				dto.setSch_name(rs.getString("sch_name"));

				dto.setSch_sdate(rs.getString("sch_sdate"));
				dto.setSch_edate(rs.getString("sch_edate"));
				dto.setSch_reg_date(rs.getString("sch_reg_date"));
				dto.setSc_color(rs.getString("sc_color"));

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
			
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
			
		}
		
		return list;
		
	}
	
	public ScheduleDTO read(String num) throws SQLException {
		ScheduleDTO dto = null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		String sql;
		String period, s;
		
		try {
			
			sql = " SELECT  sch_num, S.sc_code, sch_name, sch_content, sch_sdate, "
					+ " sch_edate, sch_stime, sch_etime, sch_repeat, sch_repeat_c, sch_reg_date "
					+ " , C.sc_color, C.sc_type "
					+ " FROM schedule S "
					+ " JOIN schedule_color C ON S.sc_code = C.sc_code "
					+ " WHERE sch_num = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new ScheduleDTO();
				
				dto.setSch_num(rs.getString("sch_num"));
				dto.setSc_code(rs.getString("sc_code"));
				dto.setSch_name(rs.getString("sch_name"));
				dto.setSch_content(rs.getString("sch_content"));
				
				dto.setSch_sdate(rs.getString("sch_sdate"));
				s = dto.getSch_sdate().substring(0,4) + "-" + dto.getSch_sdate().substring(4,6)+ "-"
						+ dto.getSch_sdate().substring(6);
				dto.setSch_sdate(s);
				
				dto.setSch_edate(rs.getString("sch_edate"));
				if(dto.getSch_edate()!=null && dto.getSch_edate().length()==8) {
					s = dto.getSch_edate().substring(0,4)+"-"+dto.getSch_edate().substring(4,6)+"-"
							+ dto.getSch_edate().substring(6);
					dto.setSch_edate(s);
				}
				dto.setSch_stime(rs.getString("sch_stime"));
				if(dto.getSch_stime()!=null && dto.getSch_stime().length()==4) {
					s = dto.getSch_stime().substring(0,2)+":"+dto.getSch_stime().substring(2);
					dto.setSch_stime(s);
				}
				
				dto.setSch_etime(rs.getString("sch_etime"));
				if(dto.getSch_etime()!=null && dto.getSch_etime().length()==4) {
					s = dto.getSch_etime().substring(0,2)+":"+dto.getSch_etime().substring(2);
					dto.setSch_etime(s);
				}
				
				period = dto.getSch_sdate();
				if(dto.getSch_stime()!=null && dto.getSch_stime().length() != 0) {
					period += " " + dto.getSch_stime();
				}
				
				if(dto.getSch_edate()!=null && dto.getSch_edate().length() != 0) {
					period += " ~ " + dto.getSch_edate();
				}
				if(dto.getSch_etime()!=null && dto.getSch_etime().length() != 0) {
					period += " " + dto.getSch_etime();
				}
				
				dto.setPeriod(period);
				
				dto.setSch_reg_date(rs.getString("sch_reg_date"));
				dto.setSch_repeat(rs.getInt("sch_repeat"));
				dto.setSch_repeat_c(rs.getInt("sch_repeat_c"));
				dto.setSc_color(rs.getString("sc_color"));
				dto.setSc_type(rs.getString("sc_type"));
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return dto;
		
	}
	
	
	public void update(ScheduleDTO dto) throws SQLException {
		PreparedStatement pstmt =null;
		StringBuilder sb = new StringBuilder();
		
		try {
			
			sb.append(" UPDATE schedule SET ");
			sb.append("  sch_name = ? , sc_code = ? , sch_sdate = ? , sch_edate = ? , sch_stime = ? , sch_etime = ? ");
			sb.append("		, sch_repeat = ? , sch_repeat_c = ? , sch_content = ? ");
			sb.append("		WHERE sch_num = ? AND id = ? ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, dto.getSch_name());
			pstmt.setString(2, dto.getSc_code());
			pstmt.setString(3, dto.getSch_sdate());
			pstmt.setString(4, dto.getSch_edate());
			pstmt.setString(5, dto.getSch_stime());
			pstmt.setString(6, dto.getSch_etime());
			pstmt.setInt(7, dto.getSch_repeat());
			pstmt.setInt(8, dto.getSch_repeat_c());
			pstmt.setString(9, dto.getSch_content());
			pstmt.setString(10, dto.getSch_num());
			pstmt.setString(11, dto.getSch_id());
			
			
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
	
	
	
	
	
	
	
	
	
	
	
	
}
