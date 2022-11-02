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
			
			sql = " INSERT INTO noticeAll(notice_num, notice_title, notice_content, views, regdate, id, notice) "
					+ " VALUES(NOTICEALL_SEQ.NEXTVAL, ?,?,0,SYSDATE,?,?) ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getNotice_title());
			pstmt.setString(2, dto.getNotice_content());
			pstmt.setString(3, dto.getWriter_id());
			pstmt.setInt(4, dto.getNotice());
			
			
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
					+ " regdate "
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
			
			sql = " SELECT n.notice_num, e.name, n.notice_title, n.views, nf.save_filename, "
					+ " regdate "
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
			
			sql = " SELECT n.notice_num, notice_title, notice_content, "
					+ " nf.save_filename, nf.ori_filename, views, regdate, name, n.id "
					+ " FROM noticeAll n "
					+ " JOIN noticeAllFile nf ON n.notice_num = nf.notice_num "
					+ " JOIN employee e ON n.id = e.id "
					+ " WHERE n.notice_num = ? ";
			
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
				
				dto.setWriter_name(rs.getString("name"));
				dto.setWriter_id(rs.getString("id"));
				
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
			
			sql = " UPDATE noticeAll SET views=views+1 WHERE notice_num = ? ";

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
		StringBuilder sb = new StringBuilder();
		
		try {
			// 검색했을 경우
			if(keyword != null && keyword.length() != 0) {
				
				sb.append(" SELECT notice_num, notice_title "); 
				sb.append(" FROM noticeAll n ");
				sb.append(" JOIN employee e ON n.id = e.id ");
				sb.append(" WHERE ( notice_num < ? ) ");
				
				if(condition.equals("all")) {
					sb.append("	  AND ( INSTR(notice_title, ?) >= 1 OR INSTR(notice_content, ?) >= 1 ) ");
				} else if(condition.equals("reg_date")) {
					keyword = keyword.replaceAll("(\\.|\\-|\\/)", "");
					sb.append("   AND ( TO_CHAR(regdate, 'YYYYMMDD') = ? ) ");
				} else {
					sb.append("   AND ( INSTR(" + condition + ", ?) >= 1 ) ");
				}
				
				sb.append(" ORDER BY notice_num DESC ");
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
				sb.append(" WHERE notice_num < ? ");
				sb.append(" ORDER BY notice_num DESC ");
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
			pstmt.setLong(3, dto.getNum());
			pstmt.setString(4, dto.getWriter_id());
			
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
			
			sql = " DELETE FROM noticeAllFile "
					+ " WHERE notice_num = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, num);
			
			
			pstmt.executeUpdate();
			
			pstmt.close();
			pstmt = null;
			
			sql = " DELETE FROM noticeAll "
					+ " WHERE notice_num = ? AND id = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, num);
			pstmt.setString(2, writer_id);
			
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
			
			sql = " SELECT NVL(COUNT(*), 0) FROM noticeAllReply "
					+ " WHERE notice_num = ? AND answer = 0 ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, num);
			
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
	
	// 댓글 목록 
	public List<ReplyDTO> listReply(long num, int offset, int size) {
		List<ReplyDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			
			/*
			  	가져올 것들 : 댓글 번호, 아이디, 이름, 공지사항 번호, 댓글 내용, 댓글 등록일자, 대댓글 갯수
				이름 가져오기 위해 employee(e) 테이블과 조인, 댓글과 대댓글 구분하기 위해 서브쿼리(a) 조인
				(댓글에 대댓글은 있을 수도 있고 없을 수도 있다, 그러므로 nr 테이블 기준으로 LEFT OUTER JOIN을 한다.)
			*/
			sql = " SELECT nr.replyNum, nr.id, name, notice_num, content, nr.reg_date, "
					+ " NVL(answerCount, 0) answerCount "
					+ " FROM noticeAllReply nr "
					+ " JOIN employee e ON nr.id = e.id "
					+ " LEFT OUTER JOIN ( "
					+ " 	SELECT answer, COUNT(*) answerCount "
					+ " 	FROM noticeAllReply nr "
					+ " 	WHERE answer != 0 "
					+ " 	GROUP BY answer "
					+ " ) a ON nr.replyNum = a.answer "
					+ " WHERE notice_num = ? AND nr.answer = 0 "
					+ " ORDER BY nr.replyNum DESC "
					+ " OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, num);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, size);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				ReplyDTO dto = new ReplyDTO();
				
				dto.setReplyNum(rs.getLong("replyNum"));
				dto.setReply_id(rs.getString("id"));
				dto.setReply_name(rs.getString("name"));
				dto.setNotice_num(rs.getLong("notice_num"));
				dto.setRep_contents(rs.getString("content"));
				dto.setRep_regdate(rs.getString("reg_date"));
				dto.setAnswerCount(rs.getInt("answerCount"));
				
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
	
	public void deleteReply(long replyNum, String id) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		
		try {
			
			// 관리자일 경우
			if(id.equals("admin")) {
				
				// 관리자가 쓴 댓글인지 확인
				sql = " SELECT replyNum FROM noticeAllReply WHERE replyNum = ? AND id = ? ";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setLong(1, replyNum);
				pstmt.setString(2, id);
				
				rs = pstmt.executeQuery();
				
				boolean b = false;
				
				// 관리자가 쓴 댓글 맞을 경우
				if(rs.next()) {
					b = true;
				}
				
				rs.close();
				pstmt.close();
				
				// 관리자가 쓴 댓글이 아닐 경우
				if(! b) {
					return;
				}
				
			}
			
			/*
			 		계층형 쿼리 : 부모, 자식 간의 수직관계를 트리 구조 형태로 보여주는 쿼리
					START WITH : 트리 구조의 최상위 행을 지정
					CONNECT BY : 부모, 자식의 관계를 지정
					PRIOR : CONNECT BY 절에 사용되며 PRIOR에 지정된 컬럼이 맞은편 컬럼을 찾아간다.
					CONNECT BY PRIOR 자식 컬럼 = 부모 컬럼 : 부모 → 자식 순방향 전개
					CONNECT BY PRIOR 부모 컬럼 = 자식 컬럼 : 자식 → 부모 역방향 전개
			 */
			sql = " DELETE FROM noticeAllReply "
					+ " WHERE replyNum IN "
					+ " ( SELECT replyNum FROM noticeAllReply START WITH replyNum = ? "
					+ " 	CONNECT BY PRIOR replyNum = answer ) ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, replyNum);
			
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
	
	// 댓글의 답글 리스트
	public List<ReplyDTO> listReplyAnswer(long answer) {
		List<ReplyDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			// answer가 0일 경우 : 댓글
			//	    0이 아닐 경우 : 대댓글
			sql = " SELECT replyNum, notice_num, nr.id, name, content, reg_date, answer "
					+ " FROM noticeAllReply nr "
					+ " JOIN employee e ON nr.id = e.id "
					+ " WHERE answer = ? "
					+ " ORDER BY replyNum ASC ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, answer);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				ReplyDTO dto = new ReplyDTO();
				
				dto.setReplyNum(rs.getLong("replyNum"));
				dto.setNotice_num(rs.getLong("notice_num"));
				dto.setReply_id(rs.getString("id"));
				dto.setReply_name(rs.getString("name"));
				dto.setRep_contents(rs.getString("content"));
				dto.setRep_regdate(rs.getString("reg_date"));
				dto.setAnswer(rs.getLong("answer"));
				
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
	
	// 댓글의 답글 갯수
	public int dataCountReplyAnswer(long answer) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			
			sql = " SELECT NVL(COUNT(*), 0) FROM noticeAllReply "
					+ " WHERE answer = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, answer);
			
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
	
	// 공지글
		public List<CompNoticeDTO> listNotice() {
			List<CompNoticeDTO> list = new ArrayList<CompNoticeDTO>();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			StringBuilder sb = new StringBuilder();

			try { 
				sb.append(" SELECT notice_num, n.id, name, notice_title, ");
				sb.append("       views, TO_CHAR(regdate, 'YYYY-MM-DD') regdate ");
				sb.append(" FROM noticeAll n ");
				sb.append(" JOIN employee e ON n.id=e.id ");
				sb.append(" WHERE notice=1  ");
				sb.append(" ORDER BY notice_num DESC ");

				pstmt = conn.prepareStatement(sb.toString());

				rs = pstmt.executeQuery();

				while (rs.next()) {
					CompNoticeDTO dto = new CompNoticeDTO();

					dto.setNum(rs.getLong("notice_num"));
					dto.setWriter_id(rs.getString("id"));
					dto.setWriter_name(rs.getString("name"));
					dto.setNotice_title(rs.getString("notice_title"));
					dto.setViews(rs.getInt("views"));
					dto.setRegdate(rs.getString("regdate"));

					list.add(dto);
				}
			} catch (SQLException e) {
				e.printStackTrace();
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

			return list;
		}
	
		
		public List<CompNoticeDTO> mainList() {
			List<CompNoticeDTO> list = new ArrayList<>();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql;
			
			try {
				
				sql = " SELECT notice_title, TO_CHAR(regdate, 'YYYY-MM-DD') regdate, name "
						+ " FROM noticeAll n "
						+ " JOIN employee e ON n.id = e.id "
						+ " ORDER BY notice_num DESC  "
						+ " FETCH FIRST 6 ROWS ONLY ";
				
				pstmt = conn.prepareStatement(sql);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					
					CompNoticeDTO dto = new CompNoticeDTO();
					
					dto.setNotice_title(rs.getString("notice_title"));
					dto.setRegdate(rs.getString("regdate"));
					dto.setWriter_name(rs.getString("name"));
					
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
	
}
