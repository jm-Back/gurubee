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
			sb.append(" SELECT sch_num, sc_code, sch_name, sch_content, sch_sdate, ");
			sb.append(" 	sch_edate, sch_stime, sch_etime, sch_repeat, sch_repeat_c, sch_reg_date ");
			sb.append(" FROM schedule ");
			sb.append(" WHERE id = ? AND ");
			sb.append("	  ( ");
			sb.append("      ( ");
			sb.append("			( TO_DATE(sch_sdate, 'YYYYMMDD') >= TO_DATE(?, 'YYYYMMDD') ");
			sb.append("				AND TO_DATE(sch_sdate, 'YYYYMMDD') <= TO_DATE(?, 'YYYYMMDD') ");
			sb.append("			) OR  (TO_DATE(sch_edate, 'YYYYMMDD') <= TO_DATE(?, 'YYYYMMDD')  ");
			sb.append("			  AND TO_DATE(sch_edate, 'YYYYMMDD') <= TO_DATE(?, 'YYYYMMDD')   ");
			sb.append("			) ");
			sb.append("		   ) OR ("); //일정 반복 부분 출력
			sb.append(" 		sch_repeat = 1 AND sch_repeat_cycle != 0 ");
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
				
				dto.setSch_num(rs.getString("num"));
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
					+ ") VALUES(sch_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE ) ";
			
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
	
	
	
	
	
	
	
	
	
	
	
}
