package com.community;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.util.DBConn;

public class CommunityDAO {
	private Connection conn = DBConn.getConnection();
	
	public void insertcompNotice(CommunityDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			
			conn.setAutoCommit(false);
			
			sql = " INSERT INTO community(com_num, com_title, com_contents, views, regdate, id, notice) "
					+ " VALUES(community_SEQ.NEXTVAL, ?,?,0,SYSDATE,?,?) ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getCom_title());
			pstmt.setString(2, dto.getCom_contents());
			pstmt.setString(3, dto.getWriter_id());
			pstmt.setInt(4, dto.getNotice());
			
			
			pstmt.executeUpdate();
			
			pstmt.close();
			pstmt = null;
			
			sql = " INSERT INTO comFile(file_num,save_filename,ori_filename,com_num) "
					+ " VALUES(comFile_SEQ.NEXTVAL,?,?,community_SEQ.CURRVAL) ";
			
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
			
			sql = "SELECT COUNT(*) FROM community ";
			
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
			sql = " SELECT COUNT(*) FROM community c "
					+ " JOIN employee e ON c.id = e.id ";
			
			// 제목+내용
			if(condition.equals("all")) {
				sql += " WHERE INSTR(com_title,?) >= 1 OR INSTR(com_contents,?) >= 1 ";
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
	public List<CommunityDTO> listBoard(int offset, int size) {
		List<CommunityDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			
			sql = " SELECT c.com_num, name, com_title, views, cf.save_filename, "
					+ " regdate, NVL(replyCount, 0) replyCount, com_contents "
					+ " FROM community c "
					+ " JOIN employee e ON c.id = e.id "
					+ " JOIN comFile cf ON c.com_num = cf.com_num "
					+ " LEFT OUTER JOIN ( "
					+ " 	SELECT com_num, COUNT(*) replyCount "
					+ " 	FROM comReply "
					+ " 	WHERE answer = 0 "
					+ " 	GROUP BY com_num "
					+ " ) cr ON c.com_num = cr.com_num "
					+ " ORDER BY com_num DESC "
					+ " OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ";
					
					
					// OFFSET : 건너뛸 데이터 갯수
					// FETCH FIRST : 보여줄 데이터 갯수
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, offset);
			pstmt.setInt(2, size);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				CommunityDTO dto = new CommunityDTO();
				// 리스트에 표기할 것들 DB에서 빼오기
				dto.setNum(rs.getLong("com_num"));
				dto.setWriter_name(rs.getString("name"));
				dto.setCom_title(rs.getString("com_title"));
				dto.setViews(rs.getInt("views"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setSave_filename(rs.getString("save_filename"));
				
				dto.setReplyCount(rs.getInt("replyCount"));
				dto.setCom_contents(rs.getString("com_contents"));
				
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
	
	public List<CommunityDTO> listBoard(int offset, int size, String condition, String keyword) {
		List<CommunityDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			
			sql = " SELECT c.com_num, e.name, c.com_title, c.views, cf.save_filename, "
					+ " regdate,  NVL(replyCount, 0) replyCount, c.com_contents  "
					+ " FROM community c "
					+ " JOIN employee e ON c.id = e.id "
					+ " JOIN comFile cf ON c.com_num = cf.com_num "
					+ " LEFT OUTER JOIN ( "
					+ "      SELECT com_num, COUNT(*) replyCount "
					+ "      FROM comReply "
					+ "      WHERE answer=0 "
					+ "      GROUP BY com_num"
					+ " ) cr ON c.com_num = cr.com_num ";
			if(condition.equals("all")) {
				sql += " WHERE INSTR(com_title,?) >= 1 OR INSTR(com_contents,?) >= 1 ";
			} else if(condition.equals("reg_date")) {
				keyword = keyword.replaceAll("(\\.|\\-|\\/)", "");
				sql += " WHERE TO_CHAR(regdate, 'YYYYMMDD') = ? ";
			} else {
				sql += " WHERE INSTR("+ condition + ",?) >= 1 ";
			}
			sql += " ORDER BY com_num DESC ";
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
				CommunityDTO dto = new CommunityDTO();
				
				dto.setNum(rs.getLong("com_num"));
				dto.setCom_title(rs.getString("com_title"));
				dto.setViews(rs.getInt("views"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setWriter_name(rs.getString("name"));
				dto.setSave_filename(rs.getString("save_filename"));
				
				dto.setReplyCount(rs.getInt("replyCount"));
				dto.setCom_contents(rs.getString("com_contents"));
				
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
	public CommunityDTO readBoard(long com_num) {
		CommunityDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			
			sql = " SELECT c.com_num, com_title, com_contents, "
					+ " cf.save_filename, cf.ori_filename, views, regdate, name, c.id, "
					+ " NVL(boardLikeCount, 0) boardLikeCount "
					+ " FROM community c "
					+ " JOIN comFile cf ON c.com_num = cf.com_num "
					+ " JOIN employee e ON c.id = e.id "
					+ " LEFT OUTER JOIN ("
					+ " 	 SELECT com_num, COUNT(*) boardLikeCount FROM comLike "
					+ " 	 GROUP BY com_num "
					+ " ) bc ON c.com_num = bc.com_num "
					+ " WHERE c.com_num = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, com_num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new CommunityDTO();
				
				dto.setNum(rs.getLong("com_num"));
				dto.setCom_title(rs.getString("com_title"));
				dto.setCom_contents(rs.getString("com_contents"));
				dto.setSave_filename(rs.getString("save_filename"));
				dto.setOri_filename(rs.getString("ori_filename"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setViews(rs.getInt("views"));
				
				dto.setWriter_name(rs.getString("name"));
				dto.setWriter_id(rs.getString("id"));
				
				dto.setBoardLikeCount(rs.getInt("boardLikeCount"));
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
	
	// 조회수 증가하기
	public void updateHitCount(long num) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			
			sql = " UPDATE community SET views=views+1 WHERE com_num = ? ";

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
	public CommunityDTO preReadBoard(long num, String condition, String keyword) {
		CommunityDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			// 검색했을 경우
			if(keyword != null && keyword.length() != 0) {
				// 이름 검색하기 위해 조인 사용
				sb.append(" SELECT com_num, com_title ");
				sb.append(" FROM community c ");
				sb.append(" JOIN employee e ON c.id = e.id ");
				sb.append(" WHERE ( com_num > ? ) ");
				
				if(condition.equals("all")) {
					sb.append(" AND ( INSTR(com_title, ?) >= 1 OR INSTR(com_contents, ?) >= 1 ) ");
				} else if(condition.equals("reg_date")) {
					keyword = keyword.replaceAll("(\\.|\\/|\\-)", "");
					sb.append(" AND ( TO_CHAR(regdate, 'YYYYMMDD') = ? ) ");
				} else {
					sb.append(" AND ( INSTR(" + condition + ", ?) >= 1 ) ");
				}
				
				sb.append(" ORDER BY com_num ASC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");
				
				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setLong(1, num);
				pstmt.setString(2, keyword);
				
				if(condition.equals("all")) {
					pstmt.setString(3, keyword);
				}
			// 검색 안했을 경우	
			} else {
				
				sb.append(" SELECT com_num, com_title ");
				sb.append(" FROM community ");
				sb.append(" WHERE com_num > ? ");
				sb.append(" ORDER BY com_num ASC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");
				
				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setLong(1, num);
				
			}
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				dto = new CommunityDTO();
				
				dto.setNum(rs.getLong("com_num"));
				dto.setCom_title(rs.getString("com_title"));
				
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
	public CommunityDTO nextReadBoard(long num, String condition, String keyword) {
		CommunityDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			// 검색했을 경우
			if(keyword != null && keyword.length() != 0) {
				
				sb.append(" SELECT com_num, com_title "); 
				sb.append(" FROM community c ");
				sb.append(" JOIN employee e ON c.id = e.id ");
				sb.append(" WHERE ( com_num < ? ) ");
				
				if(condition.equals("all")) {
					sb.append("	  AND ( INSTR(com_title, ?) >= 1 OR INSTR(com_contents, ?) >= 1 ) ");
				} else if(condition.equals("reg_date")) {
					keyword = keyword.replaceAll("(\\.|\\-|\\/)", "");
					sb.append("   AND ( TO_CHAR(regdate, 'YYYYMMDD') = ? ) ");
				} else {
					sb.append("   AND ( INSTR(" + condition + ", ?) >= 1 ) ");
				}
				
				sb.append(" ORDER BY com_num DESC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");
			
				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setLong(1, num);
				pstmt.setString(2, keyword);
				
				if(condition.equals("all")) {
					pstmt.setString(3, keyword);
				}
			// 검색 안했을 경우
			} else {
				
				sb.append(" SELECT com_num, com_title ");
				sb.append(" FROM community ");
				sb.append(" WHERE com_num < ? ");
				sb.append(" ORDER BY com_num DESC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");
				
				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setLong(1, num);
				
			}
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new CommunityDTO();
				
				dto.setNum(rs.getLong("com_num"));
				dto.setCom_title(rs.getString("com_title"));
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
	
	public void updateBoard(CommunityDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			
			conn.setAutoCommit(false);
			
			sql = " UPDATE community SET com_title = ?, com_contents = ? "
					+ " WHERE com_num = ? AND id = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getCom_title());
			pstmt.setString(2, dto.getCom_contents());
			pstmt.setLong(3, dto.getNum());
			pstmt.setString(4, dto.getWriter_id());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			pstmt = null;
			
			sql = " UPDATE comFile SET save_filename = ?, ori_filename = ? "
					+ " WHERE com_num = ? ";
			
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
			
			sql = " DELETE FROM comFile "
					+ " WHERE com_num = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, num);
			
			
			pstmt.executeUpdate();
			
			pstmt.close();
			pstmt = null;
			
			sql = " DELETE FROM community "
					+ " WHERE com_num = ? AND id = ? ";
			
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
			
			sql = " INSERT INTO comReply VALUES(comReply_SEQ.NEXTVAL, ?,?,?,SYSDATE,?) ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, dto.getCom_num());
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
			
			sql = " SELECT NVL(COUNT(*), 0) FROM comReply "
					+ " WHERE com_num = ? AND answer = 0 ";
			
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
			sql = " SELECT cr.replyNum, cr.id, name, com_num, content, cr.reg_date, "
					+ " 	NVL(answerCount, 0) answerCount, "
					+ " 	NVL(likeCount, 0) likeCount, "
					+ " 	NVL(disLikeCount, 0) disLikeCount "
					+ " FROM comReply cr "
					+ " JOIN employee e ON cr.id = e.id "
					+ " LEFT OUTER JOIN ( "
					+ " 	SELECT answer, COUNT(*) answerCount "
					+ " 	FROM comReply "
					+ " 	WHERE answer != 0 "
					+ " 	GROUP BY answer "
					+ " ) a ON cr.replyNum = a.answer "
					+ " LEFT OUTER JOIN ( "
					+ "     SELECT replyNum, "
					+ "     	COUNT(DECODE(replyLike, 1, 1)) likeCount, "
					+ "     	COUNT(DECODE(replyLike, 0, 1)) disLikeCount "
					+ " 	FROM replyLike "
					+ " 	GROUP BY replyNum "
					+ " ) b ON cr.replyNum = b.replyNum "
					+ " WHERE com_num = ? AND cr.answer = 0 "
					+ " ORDER BY cr.replyNum DESC "
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
				dto.setCom_num(rs.getLong("com_num"));
				dto.setRep_contents(rs.getString("content"));
				dto.setRep_regdate(rs.getString("reg_date"));
				dto.setAnswerCount(rs.getInt("answerCount"));
				
				dto.setLikeCount(rs.getInt("likeCount"));
				dto.setDisLikeCount(rs.getInt("disLikeCount"));
				
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
				sql = " SELECT replyNum FROM comReply WHERE replyNum = ? AND id = ? ";
				
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
			sql = " DELETE FROM comReply "
					+ " WHERE replyNum IN "
					+ " ( SELECT replyNum FROM comReply START WITH replyNum = ? "
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
			sql = " SELECT replyNum, com_num, cr.id, name, content, reg_date, answer "
					+ " FROM comReply cr "
					+ " JOIN employee e ON cr.id = e.id "
					+ " WHERE answer = ? "
					+ " ORDER BY replyNum ASC ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, answer);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				ReplyDTO dto = new ReplyDTO();
				
				dto.setReplyNum(rs.getLong("replyNum"));
				dto.setCom_num(rs.getLong("com_num"));
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
			
			sql = " SELECT NVL(COUNT(*), 0) FROM comReply "
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
		public List<CommunityDTO> listNotice() {
			List<CommunityDTO> list = new ArrayList<CommunityDTO>();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			StringBuilder sb = new StringBuilder();

			try { 
				sb.append(" SELECT c.com_num, c.id, name, c.com_title, ");
				sb.append("       views, TO_CHAR(regdate, 'YYYY-MM-DD') regdate, ");
				sb.append("        NVL(replyCount, 0) replyCount, cf.save_filename ");
				sb.append(" FROM community c ");
				sb.append(" JOIN employee e ON c.id=e.id ");
				sb.append(" LEFT OUTER JOIN ( ");
				sb.append(" 	SELECT com_num, COUNT(*) replyCount ");
				sb.append(" 	FROM comReply ");
				sb.append(" 	WHERE answer = 0 ");
				sb.append(" 	GROUP BY com_num ");
				sb.append(" ) cr ON c.com_num = cr.com_num ");
				sb.append(" JOIN comFile cf ON c.com_num=cf.com_num ");
				// sb.append(" WHERE notice=1  ");
				sb.append(" ORDER BY c.com_num DESC ");

				pstmt = conn.prepareStatement(sb.toString());

				rs = pstmt.executeQuery();

				while (rs.next()) {
					CommunityDTO dto = new CommunityDTO();

					dto.setNum(rs.getLong("com_num"));
					dto.setWriter_id(rs.getString("id"));
					dto.setWriter_name(rs.getString("name"));
					dto.setCom_title(rs.getString("com_title"));
					dto.setViews(rs.getInt("views"));
					dto.setRegdate(rs.getString("regdate"));
					
					dto.setReplyCount(rs.getInt("replyCount"));
					dto.setSave_filename(rs.getString("save_filename"));
					
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
	
	
		
		// 댓글의 좋아요 / 싫어요 추가
		public void insertReplyLike(ReplyDTO dto) throws SQLException {
			PreparedStatement pstmt = null;
			String sql;
			
			try {
				sql = "INSERT INTO replyLike(replyNum, userId, replyLike) VALUES (?, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setLong(1, dto.getReplyNum());
				pstmt.setString(2, dto.getReply_id());
				pstmt.setInt(3, dto.getReplyLike());
				
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
		
		// 댓글의 좋아요 / 싫어요 개수
		public Map<String, Integer> countReplyLike(long replyNum) {
			Map<String, Integer> map = new HashMap<>();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql;
			
			try {
				sql = " SELECT COUNT(DECODE(replyLike, 1, 1)) likeCount,  "
					+ "     COUNT(DECODE(replyLike, 0, 1)) disLikeCount  "
					+ " FROM replyLike WHERE replyNum = ? ";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setLong(1, replyNum);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					map.put("likeCount", rs.getInt("likeCount"));
					map.put("disLikeCount", rs.getInt("disLikeCount"));
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
			
			return map;
		}
		
		// 게시물의 공감 추가
		public void insertBoardLike(long num, String userId) throws SQLException {
			PreparedStatement pstmt = null;
			String sql;
			
			try {
				sql = "INSERT INTO comLike(com_num, userId) VALUES (?, ?)";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setLong(1, num);
				pstmt.setString(2, userId);
				
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
		
		// 게시글 공감 삭제
		public void deleteBoardLike(long num, String userId) throws SQLException {
			PreparedStatement pstmt = null;
			String sql;
			
			try {
				sql = "DELETE FROM comLike WHERE com_num = ? AND userId = ?";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setLong(1, num);
				pstmt.setString(2, userId);
				
				pstmt.executeUpdate();
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
			}
			
		}
		
		// 게시물의 공감 개수
		public int countBoardLike(long num) {
			int result = 0;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql;
			
			try {
				sql = "SELECT NVL(COUNT(*), 0) FROM comLike WHERE com_num=?";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setLong(1, num);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					result = rs.getInt(1);
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
			
			return result;
		}	
		
		// 모든 게시물의 좋아요 수(각각)
		
		public List<CommunityDTO> allBoardLike() {
			List<CommunityDTO> list = new ArrayList<>();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql;
			
			try {
				
				sql = //" SELECT com_num, NVL(COUNT(*), 0) FROM comLike "
						//+ " GROUP BY com_num ";
				
						 " SELECT c.com_num, COUNT(cl.com_num) "
					+    " FROM community c "
					+    " LEFT OUTER JOIN comLike cl "
					+    " ON c.com_num = cl.com_num "
					+    " GROUP BY c.com_num ";
				
				
				
				
				pstmt = conn.prepareStatement(sql);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					
					CommunityDTO dto = new CommunityDTO();
					
					dto.setNum(rs.getLong("com_num"));
					dto.setBoardLikeCount(rs.getInt(2));
					
					list.add(dto);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			return list;
		}
		
		// 로그인 유저의 게시글 공감 유무
		public boolean isUserBoardLike(long num, String userId) {
			boolean result = false;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql;
			
			try {
				sql = "SELECT com_num, userId FROM comLike WHERE com_num = ? AND userId = ?";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setLong(1, num);
				pstmt.setString(2, userId);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					result = true;
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
		
		// 메인 메뉴 표시
		public List<CommunityDTO> mainList() {
			List<CommunityDTO> list = new ArrayList<>();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql;
			
			try {
				
				sql = " SELECT com_num, com_title, TO_CHAR(regdate, 'YYYY-MM-DD') regdate, name "
						+ " FROM community c "
						+ " JOIN employee e ON c.id = e.id "
						+ " ORDER BY com_num DESC  "
						+ " FETCH FIRST 5 ROWS ONLY ";
				
				pstmt = conn.prepareStatement(sql);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					
					CommunityDTO dto = new CommunityDTO();
					
					dto.setNum(rs.getLong("com_num"));
					dto.setCom_title(rs.getString("com_title"));
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
