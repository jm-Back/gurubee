package com.comp_notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class CompNoticeDAO {
	private Connection conn = DBConn.getConnection();
	
	public void insertcompNotice(CompNoticeDTO dto) throws SQLException {
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
	
	// 검색할 때 조회 데이터 갯수
	public int dataCount(String condition, String keyword) {
		int result = 0;
		String sql;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// 기본. 사원 테이블에서 이름 가져오기 위해 조인
			sql = " SELECT COUNT(*) FROM noticeAll n "
					+ " JOIN employee e ON n.id = e.id ";
			
			// 제목+내용
			if(condition.equals("all")) {
				sql += " WHERE INSTR(notice_title,?) >= 1 OR INSTR(notice_content,?) >= 1 ";
			// 등록일
			} else if(condition.equals("reg_date")) {
				keyword = keyword.replaceAll("(\\-|\\.|\\/)", "");
				sql += " WHERE TO_CHAR(regdate, 'YYYYMMDD') = ? ";
			// 작성자, 제목, 내용
			} else {
				sql += " WHERE INSTR(" + condition + ", ?) >= 1 ";
			}
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, keyword);
			
			if(condition.equals("all")) {
				pstmt.setString(2, keyword);
			}
			
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
	
	// 리스트 출력
	public List<CompNoticeDTO> listBoard(int offset, int size) {
		List<CompNoticeDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			
			sql = " SELECT notice_num, name, notice_title, views, save_filename "
					+ " TO_CHAR(regdate, 'YYYY-MM-DD') regdate "
					+ " FROM noticeAll n "
					+ " JOIN employee e ON n.id = e.id "
					+ " JOIN noticeAllFile nf ON n.notice_num = nf.notice_num "
					+ " ORDER BY notice_num DESC "
					+ " OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ";
					// OFFSET : 건너뛸 데이터 갯수
					// FETCH FIRST : 보여줄 데이터 갯수
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, offset);
			pstmt.setInt(2, size);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				CompNoticeDTO dto = new CompNoticeDTO();
				// 리스트에 표기할 것들 DB에서 빼오기
				dto.setNum(rs.getLong("notice_num"));
				dto.setName(rs.getString("name"));
				dto.setNotice_title(rs.getString("notice_title"));
				dto.setViews(rs.getInt("views"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setSave_filename(rs.getString("save_filename"));
				
				list.add(dto);
				
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
		
		return list;
	}
	
	public List<CompNoticeDTO> listBoard(int offset, int size, String condition, String keyword) {
		List<CompNoticeDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			
			sql = " SELECT notice_num, name, notice_title, views, save_filename "
					+ " TO_CHAR(regdate, 'YYYY-MM-DD') regdate "
					+ " FROM noticeAll n "
					+ " JOIN employee e ON n.id = e.id "
					+ " JOIN noticeAllFile nf ON n.notice_num = nf.notice_num ";
			if(condition.equals("all")) {
				sql += " WHERE INSTR(notice_title,?) >= 1 OR INSTR(notice_content,?) >= 1 ";
			} else if(condition.equals("reg_date")) {
				keyword = keyword.replaceAll("(\\.|\\-|\\/)", "");
				sql += " WHERE TO_CHAR(regdate, 'YYYYMMDD') = ? ";
			} else {
				sql += " WHERE INSTR("+ condition + ",?) >= 1 ";
			}
			sql += " ORDER BY notice_num DESC ";
			sql += " OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ";
			
			pstmt = conn.prepareStatement(sql);
			
			if(condition.equals("all")) {
				pstmt.setString(1, keyword);
				pstmt.setString(2, keyword);
				pstmt.setInt(3, offset);
				pstmt.setInt(4, size);
			} else {
				pstmt.setString(1, keyword);
				pstmt.setInt(2, offset);
				pstmt.setInt(3, size);
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				CompNoticeDTO dto = new CompNoticeDTO();
				
				dto.setNum(rs.getLong("notice_num"));
				dto.setNotice_title(rs.getString("notice_title"));
				dto.setViews(rs.getInt("views"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setName(rs.getString("name"));
				dto.setSave_filename(rs.getString("save_filename"));
				
				list.add(dto);
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
		
		return list;
	}
	
	// 공지사항 클릭시 내용 출력
	public CompNoticeDTO readBoard(Long notice_num) {
		CompNoticeDTO dto = null;
		
		
		return dto;
	}
	
}
