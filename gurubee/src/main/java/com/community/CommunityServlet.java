package com.community;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.json.JSONObject;

import com.login.SessionInfo;
import com.util.FileManager;
import com.util.MyUploadServlet;
import com.util.MyUtil;
import com.util.MyUtilBootstrap;


@MultipartConfig
@WebServlet("/community/*")
public class CommunityServlet extends MyUploadServlet {
	// 전체 공지사항 게시판 서블릿
	private static final long serialVersionUID = 1L;

	private String pathname;
	
	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();
		
		// 세션 정보
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		if(info == null) {//  로그인이 안 되어 있을 경우, 로그인 화면으로
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}
		
		/*
		파일을 저장할 경로
		getRealPath : webapp 경로까지를 의미
		File.separator : 프로그램이 실행 중인 OS에 해당하는 구분자를 리턴
						 ex) windows : /, Linux : \
		*/
		String root = session.getServletContext().getRealPath("/");
		pathname = root + "uploads" + File.separator + "community";
		
		if(uri.indexOf("list.do") != -1) {
			list(req,resp);
		} else if(uri.indexOf("write.do") != -1) {
			writeForm(req,resp);
		} else if(uri.indexOf("write_ok.do") != -1) {
			writeSubmit(req, resp);
		} else if(uri.indexOf("article.do") != -1) {
			article(req, resp);
		} else if(uri.indexOf("update.do") != -1) {
			updateForm(req, resp);
		} else if(uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp);
		} else if(uri.indexOf("deleteFile.do") != -1) {
			deleteFile(req, resp);
		} else if(uri.indexOf("delete.do") != -1) {
			delete(req, resp);
		} else if(uri.indexOf("download.do") != -1) {
			download(req, resp);
		} else if(uri.indexOf("insertReply.do") != -1) {
			insertReply(req, resp);
		} else if(uri.indexOf("listReply.do") != -1) {
			listReply(req, resp);
		} else if(uri.indexOf("deleteReply.do") != -1) {
			deleteReply(req, resp);
		} else if(uri.indexOf("insertReplyAnswer.do") != -1) {
			insertReplyAnswer(req, resp);
		} else if(uri.indexOf("listReplyAnswer.do") != -1) {
			listReplyAnswer(req, resp);
		} else if(uri.indexOf("deleteReplyAnswer.do") != -1) {
			deleteReplyAnswer(req, resp);
		} else if(uri.indexOf("countReplyAnswer.do") != -1) {
			countReplyAnswer(req, resp);
		} else if (uri.indexOf("insertReplyLike.do") != -1) {
			// 댓글 좋아요/싫어요 추가
			insertReplyLike(req, resp);
		} else if (uri.indexOf("countReplyLike.do") != -1) {
			// 댓글 좋아요/싫어요 개수
			countReplyLike(req, resp);
		} else if (uri.indexOf("insertBoardLike.do") != -1) {
			// 게시물 공감 저장
			insertBoardLike(req, resp);
		} else if(uri.indexOf("mainList.do") != -1) {
			mainList(req, resp);
		} 
		
	}
	
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 전체 공지사항 리스트
		// 공지사항 메뉴 클릭 or 페이지 번호 클릭시 마다 실행
		CommunityDAO dao = new CommunityDAO();
		MyUtil util = new MyUtilBootstrap();
		
		String cp = req.getContextPath();
		
		try {
			// 페이지 번호 누를때 get방식으로 파라미터를 받는다.
			String page = req.getParameter("page");
			
			// 초기 페이지
			int current_page = 1;
			
			// 페이지 번호를 누를 경우, 해당 번호로 현재 페이지를 갱신
			if(page != null) {
				current_page = Integer.parseInt(page);
			}
			
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			
			// 검색을 안했을 경우, 초기 설정
			if(condition == null) {
				condition = "all";
				keyword = "";
			}
			
			// 요청 방식이 GET방식이면 검색어 디코딩
			if(req.getMethod().equalsIgnoreCase("GET")) {
				keyword = URLDecoder.decode(keyword, "utf-8");
			}
			
			// 공지사항 갯수
			int dataCount;
			if(keyword.length() == 0) {
				dataCount = dao.dataCount();
			} else {
				dataCount = dao.dataCount(condition, keyword);
			}
			
			// 전체 페이지수
			int size = 10;
			int total_page = util.pageCount(dataCount, size);
			if(current_page > total_page) {
				current_page = total_page;
			}
			
			// 게시글 가져오기
			int offset = (current_page - 1) * size;
			if(offset < 0) offset = 0;
			
			List<CommunityDTO> list = null;
			
			// 검색 안 할시
			if(keyword.length() == 0) {
				list = dao.listBoard(offset, size);
			} else { // 검색시
				list = dao.listBoard(offset, size, condition, keyword);
			}
			
			// 공지글
			List<CommunityDTO> listNotice = null;
			listNotice = dao.listNotice();
			for (CommunityDTO dto : listNotice) {
				dto.setRegdate(dto.getRegdate().substring(0, 10));
			}

			long gap;
			Date curDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 

			for (CommunityDTO dto : list) { 
				Date date = sdf.parse(dto.getRegdate());
				// gap = (curDate.getTime() - date.getTime()) / (1000*60*60*24); // 일자
				gap = (curDate.getTime() - date.getTime()) / (1000 * 60 * 60); // 시간
				dto.setGap(gap);

				dto.setRegdate(dto.getRegdate().substring(0, 10));
			}
			
			String query = "";
			
			if(keyword.length() != 0) {
				query = "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword,"utf-8");
			}
			
			// 페이징
			String listUrl = cp + "/community/list.do";
			String articleUrl = cp + "/community/article.do?page="+current_page;
			
			// 검색했을 때
			if(query.length() != 0) {
				listUrl += "?" + query;
				articleUrl += "&" + query;
			}
			String paging = util.paging(current_page, total_page, listUrl);
			
			List<CommunityDTO> listLike = dao.allBoardLike();
			
			// 포워딩할 JSP에 넘길 속성
			req.setAttribute("list", list); // 공지시항 리스트
			req.setAttribute("page", current_page); // 현재 페이지
			req.setAttribute("total_page", total_page); // 전체 페이지
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("size", size); // 한 페이지에 표시할 공지사항 갯수
			req.setAttribute("articleUrl", articleUrl); // 글쓰기폼 URL
			req.setAttribute("paging", paging);
			req.setAttribute("condition", condition);
			req.setAttribute("keyword", keyword);
			
			req.setAttribute("listNotice", listNotice); // [공지사항]
			req.setAttribute("listLike", listLike);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String path = "/WEB-INF/views/community/list.jsp";
		forward(req, resp, path);
	}
	
	protected void writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setAttribute("mode", "write");
		String path = "/WEB-INF/views/community/write.jsp";
		forward(req, resp, path);
	}
	
	protected void writeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 공지사항 등록
		CommunityDAO dao = new CommunityDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/community/list.do");
			return;
		}
		
		try {
		
			CommunityDTO dto = new CommunityDTO();
			
			dto.setWriter_id(info.getId());
			if (req.getParameter("notice") != null) {
				dto.setNotice(Integer.parseInt(req.getParameter("notice")));
			}
			
			dto.setCom_title(req.getParameter("com_title"));
			dto.setCom_contents(req.getParameter("com_contents"));
			
			

			// 파일정보
			Part p = req.getPart("selectFile");
			Map<String, String> map = doFileUpload(p, pathname);
			if(map != null) {
				String saveFilename = map.get("saveFilename");
				String originalFilename = map.get("originalFilename");
				
				dto.setSave_filename(saveFilename);
				dto.setOri_filename(originalFilename);
				
			}
			
			dao.insertcompNotice(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/community/list.do");
	}
	
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 공지사항 보기
		CommunityDAO dao = new CommunityDAO();
		
		String cp = req.getContextPath();
		
		String page = req.getParameter("page");
		String query = "page=" + page;
		
		try {
			
			long num = Long.parseLong(req.getParameter("num"));
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			
			
			
			// 초기 검색창 셋팅
			if(condition == null) {
				condition = "all";
				keyword = "";
			}
			
			// 뷰에서 인코딩되어 전송된 것을 디코딩
			keyword = URLDecoder.decode(keyword, "utf-8");
			
			// 검색시
			if(keyword.length() != 0) {
				query += "&condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
			}
			
			// 조회수 증가
			dao.updateHitCount(num);
			
			// 게시물 가져오기
			CommunityDTO dto = dao.readBoard(num);
			
			if(dto == null) {
				resp.sendRedirect(cp + "/community/list.do?" + query);
				return;
			}
			
			// 이전글, 다음글
			CommunityDTO preReadDto = dao.preReadBoard(dto.getNum(), condition, keyword);
			CommunityDTO nextReadDto = dao.nextReadBoard(dto.getNum(), condition, keyword);
			
			// 뷰로 전달할 속성
			
			// 클릭한 공지사항에 대한 정보
			req.setAttribute("dto", dto);
			// 다시 리스트로 돌아갈 때 기존 페이지로 이동
			req.setAttribute("page", page);
			// [현재 페이지, (조건, 키워드)] 검색시 리스트로 다시 돌아갈 때 필요
			req.setAttribute("query", query);
			req.setAttribute("preReadDto", preReadDto);
			req.setAttribute("nextReadDto", nextReadDto);
			
			forward(req, resp, "/WEB-INF/views/community/article.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 resp.sendRedirect(cp + "/community/list.do?" + query);
	}
	
	// 글 수정폼
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		CommunityDAO dao = new CommunityDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		String page = req.getParameter("page");
		
		try {
			
			// 수정할 게시물의 DB 접근하기 위해 공지사항 번호 필요
			long num = Long.parseLong(req.getParameter("num"));
			
			CommunityDTO dto = dao.readBoard(num);
			
			// 수정을 누르기전 해당 게시물이 삭제 되었을 경우( ex) 관리자에 의한 삭제 )
			if(dto == null) {
				resp.sendRedirect(cp + "/community/list.do");
				return;
			}
			
			// 게시물 작성자와 로그인한 사용자 아이디가 다를 경우
			if(! dto.getWriter_id().equals(info.getId())) {
				resp.sendRedirect(cp + "/community/list.do");
				return;
			}
			
			req.setAttribute("page", page);
			req.setAttribute("dto", dto);
			req.setAttribute("mode", "update");
			
			forward(req, resp, "/WEB-INF/views/community/write.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 예외발생시 리스트로 리다이렉트
		resp.sendRedirect(cp + "/community/list.do");
	}
	
	// 수정 완료
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		CommunityDAO dao = new CommunityDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		// POST 방식이 아니라 GET 방식으로 쏠 경우 (보안 위험)
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/community/list.do");
			return;
		}
		
		String page = req.getParameter("page");
		
		try {
			
			CommunityDTO dto = new CommunityDTO();
			
			dto.setNum(Long.parseLong(req.getParameter("num")));
			dto.setCom_title(req.getParameter("com_title"));
			dto.setCom_contents(req.getParameter("com_contents"));
			dto.setOri_filename(req.getParameter("originalFilename"));
			dto.setSave_filename(req.getParameter("saveFilename"));
			
			dto.setWriter_id(info.getId());
			
			Part p = req.getPart("selectFile");
			Map<String, String> map = doFileUpload(p, pathname);
			if(map != null) { // 파일이 있을 때
				if(req.getParameter("saveFilename").length() != 0) {
					
					// 기존 파일 삭제
					FileManager.doFiledelete(pathname, req.getParameter("saveFilename"));
				}
				
				// 새로운 파일
				String saveFilename = map.get("saveFilename");
				String originalFilename = map.get("originalFilename");
				
				dto.setSave_filename(saveFilename);
				dto.setOri_filename(originalFilename);
			}
			
			dao.updateBoard(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/community/list.do?page=" + page);
		
	}
	
	// 게시물 수정시 파일 삭제
	protected void deleteFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		CommunityDAO dao = new CommunityDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		String page = req.getParameter("page");
		
		try {
			
			long num = Long.parseLong(req.getParameter("num"));
			
			CommunityDTO dto = dao.readBoard(num);
			
			// 게시물이 존재하지 않을 경우
			if(dto == null) {
				resp.sendRedirect(cp + "/community/list.do?page=" + page);
				return;
			}
			
			// 작성자가 아닐 경우
			if(! info.getId().equals(dto.getWriter_id())) {
				resp.sendRedirect(cp + "/community/list.do?page=" + page);
				return;
			}
			
			// 파일 삭제
			FileManager.doFiledelete(pathname, dto.getSave_filename());
			
			// 파일명과 파일크기 변경
			dto.setOri_filename("");
			dto.setSave_filename("");
			
			dao.updateBoard(dto);
			
			req.setAttribute("page", page);
			req.setAttribute("dto", dto);
			req.setAttribute("mode", "update");
			
			forward(req, resp, "/WEB-INF/views/community/write.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/community/list.do");
	}
	
	// 게시글 삭제
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		CommunityDAO dao = new CommunityDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		String page = req.getParameter("page");
		String query = "page=" + page;
		
		try {
			
			long num = Long.parseLong(req.getParameter("num"));
			
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			
			// 검색을 통한 게시물 접근이 아닐때
			if(condition == null) {
				condition = "all";
				keyword = "";
			}
			
			// 검색을 통해 게시물 접근시
			keyword = URLDecoder.decode(keyword, "utf-8");
			
			if(keyword.length() != 0) {
				query += "condition=" + condition + "keyword=" + keyword;
			}
			
			CommunityDTO dto = dao.readBoard(num);
			
			if(dto == null) {
				resp.sendRedirect(cp + "/community/list.do?" + query);
				return;
			}
			
			if(! info.getId().equals(dto.getWriter_id()) && ! info.getId().equals("admin")) {
				resp.sendRedirect(cp + "/community/list.do?" + query);
				return;
			}
			
			// 로컬에 저장된 파일 삭제
			if(dto.getSave_filename() != null && dto.getSave_filename().length() != 0) {
				FileManager.doFiledelete(pathname, dto.getSave_filename());
			}
			
			dao.deleteBoard(num, info.getId());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/community/list.do" + query);
		
	}
	
	// 파일 다운로드
	protected void download(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		CommunityDAO dao = new CommunityDAO();
		
		boolean b = false;
		
		try {
			
			long num = Long.parseLong(req.getParameter("num"));
			
			CommunityDTO dto = dao.readBoard(num);
			
			if(dto != null) {
				
				b = FileManager.doFiledownload(dto.getSave_filename(),
						dto.getOri_filename(), pathname, resp);
				
			}
			
			if(! b) {
				// 브라우저에게 utf-8을 사용할거라는 메시지를 전달
				resp.setContentType("text/html; charset=utf-8");
				// PrintWriter : byte를 문자열 형태로 변환
				// .getWriter  : 
				PrintWriter out = resp.getWriter();
				out.print("<script>alert('파일다운로드가 실패했습니다. 다시 시도해 주세요!'); history.back();</script>");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// 댓글 등록 : AJAX - JSON
	protected void insertReply(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		CommunityDAO dao = new CommunityDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String state = "false";
		
		try {
			
			ReplyDTO dto = new ReplyDTO();
			
			dto.setCom_num(Long.parseLong(req.getParameter("num")));
			dto.setReply_id(info.getId());
			dto.setRep_contents(req.getParameter("content"));
			dto.setAnswer(Long.parseLong(req.getParameter("answer")));
			
			dao.insertReply(dto);
			
			state = "true";
			
			// INSERT, UPDATE, DELETE는 리다이렉트 필수
			// forward 사용시 req에 데이터 저장되어 있어 중복 입력 발생
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// JsonObject는 객체(주로 String)을 
		// Json객체로 바꿔주거나 Json객체를 새로 만드는 역할
		JSONObject job = new JSONObject();
		job.put("state", state);
		
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.print(job.toString());
		
		
	}
	
	// 댓글 리스트 - AJAX : Text
	protected void listReply(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		CommunityDAO dao = new CommunityDAO();
		MyUtil util = new MyUtilBootstrap();
		
		try {
			
			long num = Long.parseLong(req.getParameter("num"));
			
			String pageNo = req.getParameter("pageNo");
			
			// 초기 페이지 1
			int current_page = 1;
			
			//  페이지 번호가 있을때
			if(pageNo != null) {
				current_page = Integer.parseInt(pageNo);
			}
			
			// 한 화면에 보일 게시물 수 
			int size = 5;
			int total_page = 0;
			int replyCount = 0 ;
			
			replyCount = dao.dataCountReply(num);
			total_page = util.pageCount(replyCount, size);
			
			if(current_page > total_page) {
				current_page = total_page;
			}
			
			int offset = (current_page-1) * size;
			if(offset < 0) offset = 0;
			
			List<ReplyDTO> listReply = dao.listReply(num, offset, size);
			
			// 엔터를 <br>로 
			for(ReplyDTO dto : listReply) { // replaceAll : 정규식 사용 가능
				dto.setRep_contents(dto.getRep_contents().replaceAll("\n", "<br>"));
			}
			
			// 페이징 : javascript 함수 호출
			// listPage : 페이지번호를 클릭할 때 호출 할 자바스크립트 함수명
			String paging = util.pagingMethod(current_page, total_page, "listPage");
 			
			req.setAttribute("listReply", listReply);   // 댓글 목록
			req.setAttribute("pageNo", current_page);   // 현재 댓글 페이지
			req.setAttribute("replyCount", replyCount); // 댓글 갯수
			req.setAttribute("total_page", total_page); // 댓글 페이지 갯수
			req.setAttribute("paging", paging);
			
			forward(req, resp, "/WEB-INF/views/community/listReply.jsp");
			return;
			
		} catch (Exception e) {
			
		}
		
		// 에러가 발생하면 에러 코드를 전송
		resp.sendError(400);
		
		
	}
	
	// 댓글 삭제 - AJAX : JSON
	protected void deleteReply(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		CommunityDAO dao = new CommunityDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String state = "false";
		
		try {
			
			long replyNum = Long.parseLong(req.getParameter("replyNum"));
			
			dao.deleteReply(replyNum, info.getId());
			
			state = "true";
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONObject job = new JSONObject();
		job.put("state", state);
		
		// charset : 해당 HTML 문서의 문자 인코딩 방식을 명시
		resp.setContentType("text/html;charset=utf-8");
		
		PrintWriter out = resp.getWriter();
		out.print(job.toString());
		
	}
	
	// 댓글의 답글 등록 - AJAX : JSON
	protected void insertReplyAnswer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		insertReply(req, resp);
	}
	
	// 댓글의 답글 리스트 - AJAX : Text
	protected void listReplyAnswer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		CommunityDAO dao = new CommunityDAO();
		
		try {
			
			long answer = Long.parseLong(req.getParameter("answer"));
			
			List<ReplyDTO> listAnswer = dao.listReplyAnswer(answer);
			
			for(ReplyDTO dto : listAnswer) {
				dto.setRep_contents(dto.getRep_contents().replaceAll("\n", "<br>"));
			}
			
			req.setAttribute("listAnswer", listAnswer);
			
			forward(req, resp, "/WEB-INF/views/community/listReplyAnswer.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendError(400);
		
	}
	
	// 댓글의 답글 삭제 - AJAX : JSON
	protected void deleteReplyAnswer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		deleteReply(req, resp);
	}
	
	// 댓글의 답글 개수 - AJAX : JSON
	protected void countReplyAnswer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		CommunityDAO dao = new CommunityDAO();
		
		int count = 0;
		
		try {
			
			long answer = Long.parseLong(req.getParameter("answer"));
			
			count = dao.dataCountReplyAnswer(answer);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONObject job = new JSONObject();
		job.put("count", count);
		
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.print(job.toString());
		
	}
	
	// 댓글 좋아요 / 싫어요 저장 - AJAX:JSON
		protected void insertReplyLike(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			
			CommunityDAO dao = new CommunityDAO();

			HttpSession session = req.getSession();
			SessionInfo info = (SessionInfo) session.getAttribute("member");
			
			String state = "false";
			int likeCount = 0;
			int disLikeCount = 0;

			try {
				long replyNum = Long.parseLong(req.getParameter("replyNum"));
				int replyLike = Integer.parseInt(req.getParameter("replyLike"));

				ReplyDTO dto = new ReplyDTO();

				dto.setReplyNum(replyNum);
				dto.setReply_id(info.getId());
				dto.setReplyLike(replyLike);

				dao.insertReplyLike(dto);

				Map<String, Integer> map = dao.countReplyLike(replyNum);

				if (map.containsKey("likeCount")) {
					likeCount = map.get("likeCount");
				}

				if (map.containsKey("disLikeCount")) {
					disLikeCount = map.get("disLikeCount");
				}

				state = "true";
			} catch (SQLException e) {
				if(e.getErrorCode() == 1) {
					state = "liked";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			JSONObject job = new JSONObject();
			job.put("state", state);
			job.put("likeCount", likeCount);
			job.put("disLikeCount", disLikeCount);

			resp.setContentType("text/html;charset=utf-8");
			PrintWriter out = resp.getWriter();
			out.print(job.toString());
		}

		// 댓글 좋아요 / 싫어요 개수 - AJAX:JSON
		protected void countReplyLike(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			
			CommunityDAO dao = new CommunityDAO();

			int likeCount = 0;
			int disLikeCount = 0;

			try {
				long replyNum = Long.parseLong(req.getParameter("replyNum"));
				Map<String, Integer> map = dao.countReplyLike(replyNum);

				if (map.containsKey("likeCount")) {
					likeCount = map.get("likeCount");
				}

				if (map.containsKey("disLikeCount")) {
					disLikeCount = map.get("disLikeCount");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			JSONObject job = new JSONObject();
			job.put("likeCount", likeCount);
			job.put("disLikeCount", disLikeCount);

			resp.setContentType("text/html;charset=utf-8");
			PrintWriter out = resp.getWriter();
			out.print(job.toString());
		}
		
		// 게시물 공감 저장 - AJAX:JSON
		protected void insertBoardLike(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			CommunityDAO dao = new CommunityDAO();

			HttpSession session = req.getSession();
			SessionInfo info = (SessionInfo) session.getAttribute("member");

			String state = "false";
			int boardLikeCount = 0;

			try {
				long num = Long.parseLong(req.getParameter("num"));
				String isNoLike = req.getParameter("isNoLike");
				
				if(isNoLike.equals("true")) {
					dao.insertBoardLike(num, info.getId()); // 공감
				} else {
					dao.deleteBoardLike(num, info.getId()); // 공감 취소
				}
				
				boardLikeCount = dao.countBoardLike(num);
				
				state = "true";
			} catch (SQLException e) {
				state = "liked";
			} catch (Exception e) {
				e.printStackTrace();
			}

			JSONObject job = new JSONObject();
			job.put("state", state);
			job.put("boardLikeCount", boardLikeCount);

			resp.setContentType("text/html;charset=utf-8");
			PrintWriter out = resp.getWriter();
			out.print(job.toString());
		}
	
	
		protected void mainList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			
			try {
				
				CommunityDAO dao = new CommunityDAO();
				
				List<CommunityDTO> list = dao.mainList();
				
				req.setAttribute("list", list);
				
				forward(req, resp, "/WEB-INF/views/layout/community.jsp");
				return;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			resp.sendError(400);
		}
}
