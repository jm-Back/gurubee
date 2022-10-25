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
			pstmt.setString(3, dto.getWriter_id());
			
			
			pstmt.executeUpdate();
			
			pstmt.close();
			pstmt = null;
			
			sql = " INSERT INTO noticeAllFile(file_num,save_filename,ori_filename,notice_num) "
					+ " VALUES(NOTICEALLFILE_SEQ.NEXTVAL,?,?,NOTICEALL_SEQ.CURRVAL) ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getSave_filename());
			pstmt.setString(2, dto.getOri_filename());
			
			pstmt.executeUpdate();
			
			conn.commit();
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
					
				}
			}
			try {
				conn.setAutoCommit(true);
			} catch (Exception e2) {
				
			}
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
			
			sql = " SELECT n.notice_num, name, notice_title, views, nf.save_filename, "
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
				dto.setWriter_name(rs.getString("name"));
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
				dto.setWriter_name(rs.getString("name"));
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
	public CompNoticeDTO readBoard(long notice_num) {
		CompNoticeDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			
			sql = " SELECT notice_num, notice_title, notice_content, "
					+ " save_filename, ori_filename, views,regdate "
					+ " FROM noticeAll n "
					+ " JOIN noticeAllFile nf ON n.notice_num = nf.notice_num "
					+ " WHERE notice_num = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, notice_num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new CompNoticeDTO();
				
				dto.setNum(rs.getLong("notice_num"));
				dto.setNotice_title(rs.getString("notice_title"));
				dto.setNotice_content(rs.getString("notice_content"));
				dto.setSave_filename(rs.getString("save_filename"));
				dto.setOri_filename(rs.getString("ori_filename"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setViews(rs.getInt("views"));
				
			}
			
		} catch (Exception e) {
			
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
		
		return dto;
	}
	
	// 조회수 증가하기
	public void updateHitCount(long num) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			
			sql = " UPDATE noticeAll SET views=views+1 WHERE num = ? ";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, num);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
					
				}
			}
		}
	}
	
	// 이전 글
	public CompNoticeDTO preReadBoard(long num, String condition, String keyword) {
		CompNoticeDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			// 검색했을 경우
			if(keyword != null && keyword.length() != 0) {
				// 이름 검색하기 위해 조인 사용
				sb.append(" SELECT notice_num, notice_title ");
				sb.append(" FROM noticeAll n ");
				sb.append(" JOIN employee e ON n.id = e.id ");
				sb.append(" WHERE ( notice_num > ? ) ");
				
				if(condition.equals("all")) {
					sb.append(" AND ( INSTR(notice_title, ?) >= 1 OR INSTR(notice_content, ?) >= 1 ) ");
				} else if(condition.equals("reg_date")) {
					keyword = keyword.replaceAll("(\\.|\\/|\\-)", "");
					sb.append(" AND ( TO_CHAR(regdate, 'YYYYMMDD') = ? ) ");
				} else {
					sb.append(" AND ( INSTR(" + condition + ", ?) >= 1 ) ");
				}
				
				sb.append(" ORDER BY notice_num ASC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");
				
				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setLong(1, num);
				pstmt.setString(2, keyword);
				
				if(condition.equals("all")) {
					pstmt.setString(3, keyword);
				}
			// 검색 안했을 경우	
			} else {
				
				sb.append(" SELECT notice_num, notice_title ");
				sb.append(" FROM noticeAll ");
				sb.append(" WHERE notice_num > ? ");
				sb.append(" ORDER BY notice_num ASC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");
				
				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setLong(1, num);
				
			}
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				dto = new CompNoticeDTO();
				
				dto.setNum(rs.getLong("notice_num"));
				dto.setNotice_title(rs.getString("notice_title"));
				
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
		
		
		return dto;
	}
	
	// 다음 글
	public CompNoticeDTO nextReadBoard(long num, String condition, String keyword) {
		CompNoticeDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			// 검색했을 경우
			if(keyword != null && keyword.length() != 0) {
				
				sql = " SELECT notice_num, notice_title "
						+ " FROM noticeAll n "
						+ " JOIN employee e ON n.id = e.id "
						+ " WHERE ( notice_num < ? ) ";
				
				if(keyword.equals("all")) {
					sql += " AND ( INSTR(notice_title, ?) >= 1 OR INSTR(notice_content, ?) >= 1 ) ";
				} else if(keyword.equals("reg_date")) {
					keyword = keyword.replaceAll("(\\.|\\-|\\/)", "");
					sql += " AND ( TO_CHAR(regdate, 'YYYYMMDD') = ? ) ";
				} else {
					sql += " AND ( INSTR(" + condition + ", ? ) >= 1 ) ";
				}
				
				sql += " ORDER BY notice_num DESC ";
				sql += " FETCH FIRST 1 ROWS ONLY ";
			
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setLong(1, num);
				pstmt.setString(2, keyword);
				
				if(keyword.equals("all")) {
					pstmt.setString(3, keyword);
				}
			// 검색 안했을 경우
			} else {
				
				sql = " SELECT notice_num, notice_title "
						+ " FROM noticeAll "
						+ " WHERE notice_num < ? "
						+ " ORDER BY notice_num DESC "
						+ " FETCH FIRST 1 ROWS ONLY ";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setLong(1, num);
				
			}
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new CompNoticeDTO();
				
				dto.setNum(rs.getLong("notice_num"));
				dto.setNotice_title(rs.getString("notice_title"));
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
		
		return dto;
	}
	
	public void updateBoard(CompNoticeDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			
			conn.setAutoCommit(false);
			
			sql = " UPDATE noticeAll SET notice_title = ?, notice_content = ? "
					+ " WHERE notice_num = ? AND id = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getNotice_title());
			pstmt.setString(2, dto.getNotice_content());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			pstmt = null;
			
			sql = " UPDATE noticeAllFile SET save_filename = ?, ori_filename = ? "
					+ " WHERE notice_num = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getSave_filename());
			pstmt.setString(2, dto.getOri_filename());
			pstmt.setLong(3, dto.getNum());
			
			pstmt.executeUpdate();
			
			conn.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
					
				}
			}
			
			try {
				conn.setAutoCommit(true);
			} catch (Exception e2) {
				
			}
		}
	}
	
	public void deleteBoard(long num, String writer_id) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			
			conn.setAutoCommit(false);
			
			sql = " DELETE FROM noticeAll "
					+ " WHERE notice_num = ? AND id = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, num);
			pstmt.setString(2, writer_id);
			
			pstmt.executeUpdate();
			
			pstmt.close();
			pstmt = null;
			
			sql = " DELETE FROM noticeAllFile "
					+ " WHERE notice_num = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, num);
			
			pstmt.executeUpdate();
			
			conn.commit();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
					
				}
			}
			
			try {
				conn.setAutoCommit(true);
			} catch (Exception e2) {
				
			}
		}
	}
	
	public void insertReply(ReplyDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			
			sql = " INSERT INTO noticeAllReply VALUES(noticeAllReply_SEQ.NEXTVAL, ?,?,?,SYSDATE,?) ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, dto.getNotice_num());
			pstmt.setString(2, dto.getReply_id());
			pstmt.setString(3, dto.getRep_contents());
			pstmt.setLong(4, dto.getAnswer());
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
					
				}
			}
		}
	}
	
	// 댓글 갯수 찾기(대댓글은 제외)
	public int dataCountReply(long num) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		String sql;
		
		
		try {
			
			sql = " SELECT COUNT(*) FROM noticeAllReply "
					+ " WHERE num = ? AND answer = 0 ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
					
				}
			}
			
			if(rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
					
				}
			}
		}
		
		return result;
	}
	
	
	
	
	
}
