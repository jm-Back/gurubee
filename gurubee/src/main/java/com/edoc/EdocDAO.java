package com.edoc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.util.DBConn;

public class EdocDAO {
	private Connection conn = DBConn.getConnection();
	
	// 전자결재문서 
	public void insertEApproval(EdocDTO edocdto) {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "INSERT INTO E_APPROVAL (app_num, app_doc, id, app_date, doc_form, title, temp, result) "
					+ " VALUES (APPVAL_SEQ.NEXTVAL, ?, ?, SYSDATE, ?, ?, ?, 0) ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, edocdto.getApp_doc());
			pstmt.setString(2, edocdto.getId_write());
			pstmt.setString(3, edocdto.getDoc_form());
			pstmt.setString(4, edocdto.getTitle());
			pstmt.setInt(5, edocdto.getTemp());
			
			pstmt.executeQuery();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		
	}
	
	// 전자결재자 
	public void insertEApprover(EdocEmpDTO empdto) {
		PreparedStatement pstmt = null;
		String sql;
		int app_num=0;
		ResultSet rs = null;
		
		try {
			sql = "SELECT LAST_NUMBER as app_num "
					+ " FROM USER_SEQUENCES "
					+ " WHERE SEQUENCE_NAME='APPVAL_SEQ' ";
				
			pstmt = conn.prepareStatement(sql);
				
			rs = pstmt.executeQuery();
				
			if(rs.next()) {
				app_num = rs.getInt(1)-1;
			}
			
			pstmt.close();
			
			if(app_num==0) {
				return;
			}
			
			sql = "INSERT INTO E_APPROVER(apper_num, app_num, id, app_result, memo, app_level, app_date) "
				+ " VALUES(APPVER_SEQ.NEXTVAL, ?, ?, ?, ?, ?, SYSDATE)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, app_num);
			pstmt.setString(2, empdto.getId_apper());
			pstmt.setInt(3, 0); // 대기
			pstmt.setString(4, empdto.getMemo());
			pstmt.setInt(5, empdto.getApp_level());
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}
	
	public int edocCount(String writeId) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT COUNT(*) FROM E_APPROVAL "
				+ " WHERE id IN ? AND temp NOT IN 0 ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, writeId);
			
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
	
	
	public int edocCount(String writeId, String edoc, String myDate) {
		int r = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			
			sql = "SELECT COUNT(*) FROM E_APPROVAL "
					+ " WHERE id IN ? AND temp NOT IN 0 ";

			if(myDate==null && edoc!=null) {
				sql += " AND app_doc = ? ";
			} else if(myDate!=null && edoc==null) {
				sql += " AND TO_CHAR(app_date,'YYYY-MM-DD') = ? ";
			} else if (myDate!=null && edoc!=null) {
				sql += " AND app_doc=? AND TO_CHAR(app_date,'YYYY-MM-DD') = ?";
			}
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, writeId);
			
			if(myDate==null && edoc!=null) {
				pstmt.setString(2, edoc);
			} else if(myDate==null && edoc==null) {
				pstmt.setString(2, myDate);
			} else if (myDate!=null && edoc!=null) {
				pstmt.setString(2, edoc);
				pstmt.setString(3, myDate);
			}
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				r = rs.getInt(1);
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
		
		return r;
	} 
	
	// 특정 직급의 모든 부서 사원 리스트 가져오기
	public List<EdocEmpDTO> posEmpList(int pos_code) {
		List<EdocEmpDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT his.id, name, dep_name, pos_name, p.pos_code "
					+ " FROM (SELECT his_no, date_iss, reason, id, pos_code, dep_code, division, "
					+ "    now_working, type, startdate, enddate, "
					+ "    ROW_NUMBER() OVER(PARTITION BY id ORDER BY pos_code DESC) as now "
					+ "    FROM employee_history "
					+ "    WHERE now_working='재직')his LEFT OUTER JOIN employee e ON his.id = e.id "
					+ " LEFT OUTER JOIN department d ON his.dep_code = d.dep_code "
					+ " LEFT OUTER JOIN position p ON p.pos_code = his.pos_code "
					+ " where now=1 and now_working='재직' and p.pos_code = ? " // 직급코드
					+ " order by enddate DESC ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, pos_code);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				EdocEmpDTO empdto = new EdocEmpDTO();
				
				empdto.setId_apper(rs.getString("id"));
				empdto.setName_apper(rs.getString("name"));
				empdto.setDep_name(rs.getString("dep_name"));
				empdto.setPos_code(rs.getInt("pos_code"));
				empdto.setPos_name(rs.getString("pos_name"));
				
				list.add(empdto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}  finally {
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

	// 문서구분에 따른 문서폼 가져오기
	public EdocFormDTO findByForm(String app_doc) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		EdocFormDTO formdto = null;
		
		try {
			sql = "SELECT app_doc, doc_form, form_num "
				+ " FROM E_DOCFORM"
				+ "	WHERE app_doc = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, app_doc);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				formdto = new EdocFormDTO();
				
				formdto.setApp_doc(rs.getString("app_doc"));
				formdto.setDoc_form(rs.getString("doc_form"));
				formdto.setForm_num(rs.getInt("form_num"));
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
		return formdto;
	}
	
	// 결재문서 발신함 리스트 (조건X)
	public List<EdocDTO> listEApproval(String writeId, int offset, int size) {
		List<EdocDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			/*
			sql = "SELECT app_num, app_doc, result, title, TO_CHAR(app_date,'YYYY-MM-DD')app_date, temp"
				+ " FROM E_APPROVAL "
				+ " WHERE (temp=1 OR temp=-1) AND (id IN ?) "
				+ " ORDER BY app_date DESC ";
			*/
			sql = "select al.app_num, al.app_doc, TO_CHAR(al.app_date,'YYYY-MM-DD')app_date, al.id, "
					+ "	al.title, resultList, apperList, al.temp "
					+ " FROM E_APPROVAL al "
					+ " LEFT OUTER JOIN "
					+ "    (SELECT app_num, LISTAGG(app_result, ',') WITHIN GROUP(ORDER BY app_level) "
					+ "        AS resultList, "
					+ "        LISTAGG(e.name, ',') WITHIN GROUP(ORDER BY app_level) "
					+ "        AS apperList "
					+ "     FROM E_APPROVER er "
					+ "     LEFT OUTER JOIN EMPLOYEE e ON er.id=e.id "
					+ "     GROUP BY app_num "
					+ "    ) er "
					+ "    ON er.app_num = al.app_num "
					+ " WHERE (temp=1 OR temp=-1) AND al.id = ? "
					+ " ORDER BY al.app_num DESC "
					+ " OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, writeId);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, size);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				EdocDTO edocdto = new EdocDTO();
				String[] rr, aa;
				
				edocdto.setApp_num(rs.getInt("app_num"));
				edocdto.setApp_doc(rs.getString("app_doc"));
				
				rr = rs.getString("resultList").split(",");
				aa = rs.getString("apperList").split(",");
				
				for(int i=0; i<rr.length; i++) {
					if(rs.getString("resultList").contains("-1")) {
						edocdto.setResult((Arrays.asList(rr).indexOf("-1")+1)+"차반려");
						edocdto.setResult_name(aa[Arrays.asList(rr).indexOf("-1")]);
					} else {
						if(! rs.getString("resultList").contains("0")) {
							edocdto.setResult("승인");
							edocdto.setResult_name(aa[rr.length-1]); 
						}
						else {
							edocdto.setResult((Arrays.asList(rr).indexOf("0")+1)+"차대기");
							edocdto.setResult_name(aa[Arrays.asList(rr).indexOf("0")]);
						}
					}
				}
				
				// System.out.println(edocdto.getApp_num() + ": " + rs.getString("id") +edocdto.getResult() + ", " + edocdto.getResult_name());
				
				edocdto.setTitle(rs.getString("title"));
				edocdto.setApp_date(rs.getString("app_date"));
				edocdto.setTemp(rs.getInt("temp"));
				
				list.add(edocdto);
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

		return list;
	}
	
	// 결재문서 발신함 리스트 (조건O)
		public List<EdocDTO> listEApproval(String writeId, int offset, int size, String edoc, String myDate) {
			List<EdocDTO> list = new ArrayList<>();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql;
			
			try {
				
				sql = "select al.app_num, al.app_doc, TO_CHAR(al.app_date,'YYYY-MM-DD')app_date, al.id, "
						+ "	al.title, resultList, apperList, al.temp "
						+ " FROM E_APPROVAL al "
						+ " LEFT OUTER JOIN "
						+ "    (SELECT app_num, LISTAGG(app_result, ',') WITHIN GROUP(ORDER BY app_level) "
						+ "        AS resultList, "
						+ "        LISTAGG(e.name, ',') WITHIN GROUP(ORDER BY app_level) "
						+ "        AS apperList "
						+ "     FROM E_APPROVER er "
						+ "     LEFT OUTER JOIN EMPLOYEE e ON er.id=e.id "
						+ "     GROUP BY app_num "
						+ "    ) er "
						+ "    ON er.app_num = al.app_num "
						+ " WHERE (temp=1 OR temp=-1) AND al.id = ? ";
						
				if(myDate==null && edoc!=null) {
					sql += " AND app_doc = ? ";
				} else if(myDate!=null && edoc==null) {
					sql += " AND TO_CHAR(app_date,'YYYY-MM-DD') = ? ";
				} else if (myDate!=null && edoc!=null) {
					sql += " AND app_doc=? AND TO_CHAR(app_date,'YYYY-MM-DD')=? ";
				}

				sql += " ORDER BY al.app_num DESC "
						+ " OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, writeId);

				if(myDate==null && edoc!=null) {
					pstmt.setString(2, edoc);
					pstmt.setInt(3, offset);
					pstmt.setInt(4, size);
				} else if(myDate!=null && edoc==null) {
					pstmt.setString(2, myDate);
					pstmt.setInt(3, offset);
					pstmt.setInt(4, size);
				} else if (myDate!=null && edoc!=null) {
					pstmt.setString(2, edoc);
					pstmt.setString(3, myDate);
					pstmt.setInt(4, offset);
					pstmt.setInt(5, size);
				} else {
					pstmt.setInt(2, offset);
					pstmt.setInt(3, size);
				}
				
				
				System.out.println("DAO: "+edoc +", "+ myDate);
		
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					EdocDTO edocdto = new EdocDTO();
					String[] rr, aa;
					
					edocdto.setApp_num(rs.getInt("app_num"));
					edocdto.setApp_doc(rs.getString("app_doc"));
					
					rr = rs.getString("resultList").split(",");
					aa = rs.getString("apperList").split(",");
					
					for(int i=0; i<rr.length; i++) {
						if(rs.getString("resultList").contains("-1")) {
							edocdto.setResult((Arrays.asList(rr).indexOf("-1")+1)+"차반려");
							edocdto.setResult_name(aa[Arrays.asList(rr).indexOf("-1")]);
						} else {
							if(! rs.getString("resultList").contains("0")) {
								edocdto.setResult("승인");
								edocdto.setResult_name(aa[rr.length-1]); 
							}
							else {
								edocdto.setResult((Arrays.asList(rr).indexOf("0")+1)+"차대기");
								edocdto.setResult_name(aa[Arrays.asList(rr).indexOf("0")]);
							}
						}
					}
					
					// System.out.println(edocdto.getApp_num() + ": " +edocdto.getResult() + ", " + edocdto.getResult_name());
					
					edocdto.setTitle(rs.getString("title"));
					edocdto.setApp_date(rs.getString("app_date"));
					edocdto.setTemp(rs.getInt("temp"));
					
					list.add(edocdto);
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

			return list;
		}
	
	
	// 수신자 문서 리스트 
	public List<EdocDTO> listEApproverReceiver(String appID, int offset, int size, String edoc, String myDate) {
		List<EdocDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "select al.app_num, al.app_doc, TO_CHAR(al.app_date,'YYYY-MM-DD')app_date, al.id, "
					+ "    name, al.title, resultList, apperList, idList, al.temp "
					+ " FROM E_APPROVAL al "
					+ " LEFT OUTER JOIN "
					+ "    (SELECT app_num, LISTAGG(app_result, ',') WITHIN GROUP(ORDER BY app_level) "
					+ "        AS resultList, "
					+ "        LISTAGG(e.name, ',') WITHIN GROUP(ORDER BY app_level)  "
					+ "        AS apperList, "
					+ "        LISTAGG(er.id, ',') WITHIN GROUP(ORDER BY app_level)  "
					+ "        AS idList "
					+ "     FROM E_APPROVER er "
					+ "     LEFT OUTER JOIN EMPLOYEE e ON er.id=e.id "
					+ "     GROUP BY app_num "
					+ "    ) er "
					+ "    ON er.app_num = al.app_num "
					+ " LEFT OUTER JOIN EMPLOYEE e ON e.id=al.id "
					+ " WHERE (temp=1 OR temp=-1) AND INSTR(idList, ?) > 0 ";
			
			if(myDate==null && edoc!=null) {
				sql += " AND app_doc = ? ";
			} else if(myDate!=null && edoc==null) {
				sql += " AND TO_CHAR(app_date,'YYYY-MM-DD') = ? ";
			} else if (myDate!=null && edoc!=null) {
				sql += " AND app_doc=? AND TO_CHAR(app_date,'YYYY-MM-DD')=? ";
			}

			sql += " ORDER BY al.app_num DESC "
					+ " OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, appID);

			if(myDate==null && edoc!=null) {
				pstmt.setString(2, edoc);
				pstmt.setInt(3, offset);
				pstmt.setInt(4, size);
			} else if(myDate!=null && edoc==null) {
				pstmt.setString(2, myDate);
				pstmt.setInt(3, offset);
				pstmt.setInt(4, size);
			} else if (myDate!=null && edoc!=null) {
				pstmt.setString(2, edoc);
				pstmt.setString(3, myDate);
				pstmt.setInt(4, offset);
				pstmt.setInt(5, size);
			} else {
				pstmt.setInt(2, offset);
				pstmt.setInt(3, size);
			}
	
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				EdocDTO edocdto = new EdocDTO();
				String[] rr, aa;
				
				edocdto.setApp_num(rs.getInt("app_num"));
				edocdto.setApp_doc(rs.getString("app_doc"));
				
				rr = rs.getString("resultList").split(",");
				aa = rs.getString("apperList").split(",");
				
				for(int i=0; i<rr.length; i++) {
					if(rs.getString("resultList").contains("-1")) {
						edocdto.setResult((Arrays.asList(rr).indexOf("-1")+1)+"차반려");
						edocdto.setResult_name(aa[Arrays.asList(rr).indexOf("-1")]);
					} else {
						if(! rs.getString("resultList").contains("0")) {
							edocdto.setResult("승인");
							edocdto.setResult_name(aa[rr.length-1]); 
						}
						else {
							edocdto.setResult((Arrays.asList(rr).indexOf("0")+1)+"차대기");
							edocdto.setResult_name(aa[Arrays.asList(rr).indexOf("0")]);
						}
					}
				}
				
				edocdto.setId_write(rs.getString("id"));
				edocdto.setName_write(rs.getString("name"));
				edocdto.setTitle(rs.getString("title"));
				edocdto.setApp_date(rs.getString("app_date"));
				edocdto.setTemp(rs.getInt("temp"));
				
				list.add(edocdto);
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

		return list;
	}
	
	
	// 수신자 문서 개수 카운트
	public int edocCountReceiver(String apperId, String edoc, String myDate) {
		int r = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			
			sql = "SELECT COUNT(*) "
					+ " FROM E_APPROVAL al "
					+ " LEFT OUTER JOIN "
					+ " (SELECT app_num, er.id, app_result, app_level "
					+ " FROM E_APPROVER er "
					+ " WHERE er.id = ?) er "
					+ " ON er.app_num = al.app_num"
					+ " WHERE al.temp NOT IN 0 ";
			System.out.println("DAO: "+myDate+", "+edoc);
			if(myDate==null && edoc!=null) {
				sql += " AND app_doc = ? ";
			} else if(myDate!=null && edoc==null) {
				sql += " AND TO_CHAR(app_date,'YYYY-MM-DD') = ? ";
			} else if (myDate!=null && edoc!=null) {
				sql += " AND app_doc=? AND TO_CHAR(app_date,'YYYY-MM-DD') = ?";
			}
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, apperId);
			
			if(myDate==null && edoc!=null) {
				pstmt.setString(2, edoc);
			} else if(myDate!=null && edoc==null) {
				pstmt.setString(2, myDate);
			} else if (myDate!=null && edoc!=null) {
				pstmt.setString(2, edoc);
				pstmt.setString(3, myDate);
			}
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				r = rs.getInt(1);
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
		
		return r;
	} 
	
	// 결재문서 정보 가져오기
	public EdocDTO readEdoc(int app_num) {
		EdocDTO edocdto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			// 결재문서 정보
			sql = "SELECT app_num, app_doc, al.id, name, app_date, doc_form, title, temp "
				+ "    , dep_name, pos_name, p.pos_code "
				+ " FROM E_APPROVAL al "
				+ " LEFT OUTER JOIN EMPLOYEE e ON e.id = al.id "
				+ " LEFT OUTER JOIN (SELECT his_no, date_iss, reason, id, pos_code, dep_code, division, "
				+ "    now_working, type, startdate, enddate, "
				+ "    ROW_NUMBER() OVER(PARTITION BY id ORDER BY pos_code DESC) as now "
				+ "    FROM employee_history)his ON his.id = e.id "
				+ " LEFT OUTER JOIN department d ON his.dep_code = d.dep_code "
				+ " LEFT OUTER JOIN position p ON p.pos_code = his.pos_code "
				+ " WHERE now=1 and now_working='재직' AND app_num=? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, app_num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				edocdto = new EdocDTO();
				
				edocdto.setApp_num(rs.getInt("app_num"));
				edocdto.setApp_doc(rs.getString("app_doc"));
				edocdto.setId_write(rs.getString("id"));
				edocdto.setName_write(rs.getString("name"));	
				edocdto.setDep_write(rs.getString("dep_name"));
				edocdto.setPos_write(rs.getString("pos_name"));
				edocdto.setPosCode_write(rs.getInt("pos_code"));
				edocdto.setApp_date(rs.getString("app_date"));
				edocdto.setDoc_form(rs.getString("doc_form"));
				edocdto.setTitle(rs.getString("title"));
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
		return edocdto;
	}
	
	
		// 문서의 결재자 리스트 가져오기
	public List<EdocEmpDTO> readEdocApper(int app_num) {
		List<EdocEmpDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
			
		try {
				// 결재문서에 대한 결재자 리스트
			sql = "SELECT app_num, er.id, name, app_result, app_date, app_level, dep_name, pos_name, p.pos_code "
				+ " FROM E_APPROVER er "
				+ " LEFT OUTER JOIN EMPLOYEE e ON e.id = er.id "
				+ " LEFT OUTER JOIN (SELECT his_no, date_iss, reason, id, pos_code, dep_code, division, "
				+ "    now_working, type, startdate, enddate, "
				+ "    ROW_NUMBER() OVER(PARTITION BY id ORDER BY pos_code DESC) as now "
				+ "    FROM employee_history)his ON his.id = e.id "
				+ " LEFT OUTER JOIN department d ON his.dep_code = d.dep_code "
				+ " LEFT OUTER JOIN position p ON p.pos_code = his.pos_code "
				+ " WHERE now=1 and now_working='재직' AND app_num = ? "
				+ " ORDER BY app_level ";
				
			pstmt = conn.prepareStatement(sql);
				
			pstmt.setInt(1, app_num);
				
			rs = pstmt.executeQuery();
				
			while(rs.next()) {
				EdocEmpDTO empdto = new EdocEmpDTO();

				empdto.setApp_num(rs.getInt("app_num"));
				empdto.setId_apper(rs.getString("id"));
				empdto.setName_apper(rs.getString("name"));
				empdto.setDep_name(rs.getString("dep_name"));
				empdto.setPos_code(rs.getInt("pos_code"));
				empdto.setPos_name(rs.getString("pos_name"));
				empdto.setApp_result(rs.getInt("app_result"));
				empdto.setApp_level(rs.getInt("app_level"));
				empdto.setApp_date(rs.getString("app_date"));

				list.add(empdto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}

		return list;
	}
		
	// 전자문서 결재하기 (app_level 1)
	public String insertEdocMyResult1(int app_num, String id_apper, int app_result) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		String beforeResult = null;

		try {
			sql = "SELECT app_level, app_result, id "
				+ " FROM E_APPROVER "
				+ " WHERE app_num=? ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, app_num);

			rs = pstmt.executeQuery();

			// r = rs.getInt("app_result");
			if(rs.next()) {
				if (rs.getInt("app_result") != 0) {
					beforeResult = "이미 결재가 완료된 문서입니다.";
					return beforeResult;
				}
			}
			
			rs.close();
			pstmt.close();
			rs = null;
			pstmt = null;
				
			sql = "UPDATE E_APPROVER SET app_result=? "
				+ " WHERE app_num=? AND id = ?";
			
			pstmt = conn.prepareStatement(sql);
				
			pstmt.setInt(1, app_result);
			pstmt.setInt(2, app_num);
			pstmt.setString(3, id_apper);
			
			pstmt.executeUpdate();

			beforeResult = "결재 완료";
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}

		return beforeResult;

	}

	public String insertEdocMyResultOver1(int app_num, String id_apper, int app_result, int app_level) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		String beforeResult = null;

		try {
				// 전 결재자의 결과 확인
			sql = "SELECT app_level, app_result, id "
				+ " FROM E_APPROVER "
				+ " WHERE app_num=? AND app_level = ?";
				
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, app_num);
			pstmt.setInt(2, app_level - 1);

			rs = pstmt.executeQuery();
			if(rs.next()) {
				if (rs.getInt("app_result") == 0) {
					beforeResult = "전 결재자의 결재가 완료되지 않았습니다.";
					return beforeResult;
				} else if (rs.getInt("app_result") == -1) {
					beforeResult = "반려된 문서입니다.";
					return beforeResult;
				}
			}
			rs.close();
			pstmt.close();
			rs = null;
			pstmt = null;
				
				// 나의 이전 결과 이력이 있는지
			sql = "SELECT app_level, app_result, id "
				+ " FROM E_APPROVER "
				+ " WHERE app_num=? ";
				
			pstmt = conn.prepareStatement(sql);
				
			pstmt.setInt(1, app_num);
				
			rs = pstmt.executeQuery();
			if(rs.next()) {	
				if(rs.getInt("app_result")!=0) {
					beforeResult = "이미 결재가 완료된 문서입니다.";
					return beforeResult;
				}
			}
			
			pstmt.executeQuery();
			
			rs.close();
			pstmt.close();
			rs = null;
			pstmt = null;
				
			sql = "UPDATE SET E_APPROVER app_result=? "
					+ "WHERE app_num=? AND id = ?";
				
			pstmt.setInt(1, app_result);
			pstmt.setInt(2, app_num);
			pstmt.setString(3, id_apper);
			
			rs = pstmt.executeQuery();
			
			beforeResult = "결재 완료";
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}

		return beforeResult;
	}

	
	public int readAppLevel(String apper_id, int app_num) {
		int level = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT app_level FROM E_APPROVER WHERE app_num=? AND id=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, app_num);
			pstmt.setString(2, apper_id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				level = rs.getInt("app_level");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return level;
	}
}
