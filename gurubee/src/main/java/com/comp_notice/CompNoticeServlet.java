package com.comp_notice;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.login.SessionInfo;
import com.util.FileManager;
import com.util.MyUploadServlet;
import com.util.MyUtil;
import com.util.MyUtilBootstrap;


@MultipartConfig
@WebServlet("/comp_notice/*")
public class CompNoticeServlet extends MyUploadServlet {
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
		pathname = root + "uploads" + File.separator + "comp_notice";
		
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
		}
		
	}
	
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 전체 공지사항 리스트
		// 공지사항 메뉴 클릭 or 페이지 번호 클릭시 마다 실행
		CompNoticeDAO dao = new CompNoticeDAO();
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
			
			List<CompNoticeDTO> list = null;
			
			// 검색 안 할시
			if(keyword.length() == 0) {
				list = dao.listBoard(offset, size);
			} else { // 검색시
				list = dao.listBoard(offset, size, condition, keyword);
			}
			
			String query = "";
			if(keyword.length() != 0) {
				query = "condition="+condition+"&keyword"+URLEncoder.encode(keyword,"utf-8");
			}
			
			// 페이징
			String listUrl = cp + "/comp_notice/list.do";
			String articleUrl = cp + "/comp_notice/article.do?page="+current_page;
			
			// 검색했을 때
			if(query.length() != 0) {
				listUrl += "?" + query;
				articleUrl += "&" + query;
			}
			String paging = util.paging(current_page, total_page, listUrl);
			
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
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String path = "/WEB-INF/views/comp_notice/list.jsp";
		forward(req, resp, path);
	}
	
	protected void writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setAttribute("mode", "write");
		String path = "/WEB-INF/views/comp_notice/write.jsp";
		forward(req, resp, path);
	}
	
	protected void writeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 공지사항 등록
		CompNoticeDAO dao = new CompNoticeDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/comp_notice/list.do");
			return;
		}
		
		try {
		
			CompNoticeDTO dto = new CompNoticeDTO();
			
			dto.setWriter_id(info.getId());
			
			dto.setNotice_title(req.getParameter("notice_title"));
			dto.setNotice_content(req.getParameter("notice_content"));
			
			

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
		
		resp.sendRedirect(cp + "/comp_notice/list.do");
	}
	
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 공지사항 보기
		CompNoticeDAO dao = new CompNoticeDAO();
		
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
			CompNoticeDTO dto = dao.readBoard(num);
			
			if(dto == null) {
				resp.sendRedirect(cp + "/comp_notice/list.do?" + query);
				return;
			}
			
			// 이전글, 다음글
			CompNoticeDTO preReadDto = dao.preReadBoard(dto.getNum(), condition, keyword);
			CompNoticeDTO nextReadDto = dao.nextReadBoard(dto.getNum(), condition, keyword);
			
			// 뷰로 전달할 속성
			
			// 클릭한 공지사항에 대한 정보
			req.setAttribute("dto", dto);
			// 다시 리스트로 돌아갈 때 기존 페이지로 이동
			req.setAttribute("page", page);
			// [현재 페이지, (조건, 키워드)] 검색시 리스트로 다시 돌아갈 때 필요
			req.setAttribute("query", query);
			req.setAttribute("preReadDto", preReadDto);
			req.setAttribute("nextReadDto", nextReadDto);
			
			forward(req, resp, "/WEB-INF/views/comp_notice/article.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 resp.sendRedirect(cp + "/comp_notice/list.do?" + query);
	}
	
	// 글 수정폼
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		CompNoticeDAO dao = new CompNoticeDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		String page = req.getParameter("page");
		
		try {
			
			// 수정할 게시물의 DB 접근하기 위해 공지사항 번호 필요
			long num = Long.parseLong(req.getParameter("num"));
			
			CompNoticeDTO dto = dao.readBoard(num);
			
			// 수정을 누르기전 해당 게시물이 삭제 되었을 경우( ex) 관리자에 의한 삭제 )
			if(dto == null) {
				resp.sendRedirect(cp + "/comp_notice/list.do");
				return;
			}
			
			// 게시물 작성자와 로그인한 사용자 아이디가 다를 경우
			if(! dto.getWriter_id().equals(info.getId())) {
				resp.sendRedirect(cp + "/comp_notice/list.do");
				return;
			}
			
			req.setAttribute("page", page);
			req.setAttribute("dto", dto);
			req.setAttribute("mode", "update");
			
			forward(req, resp, "/WEB-INF/views/comp_notice/write.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 예외발생시 리스트로 리다이렉트
		resp.sendRedirect(cp + "/comp_notice/list.do");
	}
	
	// 수정 완료
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		CompNoticeDAO dao = new CompNoticeDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		// POST 방식이 아니라 GET 방식으로 쏠 경우 (보안 위험)
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/comp_notice/list.do");
			return;
		}
		
		String page = req.getParameter("page");
		
		try {
			
			CompNoticeDTO dto = new CompNoticeDTO();
			
			dto.setNum(Long.parseLong(req.getParameter("num")));
			dto.setNotice_title(req.getParameter("notice_title"));
			dto.setNotice_content(req.getParameter("notice_content"));
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
		
		resp.sendRedirect(cp + "/comp_notice/list.do?page=" + page);
		
	}
	
	// 게시물 수정시 파일 삭제
	protected void deleteFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		CompNoticeDAO dao = new CompNoticeDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		String page = req.getParameter("page");
		
		try {
			
			long num = Long.parseLong(req.getParameter("num"));
			
			CompNoticeDTO dto = dao.readBoard(num);
			
			// 게시물이 존재하지 않을 경우
			if(dto == null) {
				resp.sendRedirect(cp + "/comp_notice/list.do?page=" + page);
				return;
			}
			
			// 작성자가 아닐 경우
			if(! info.getId().equals(dto.getWriter_id())) {
				resp.sendRedirect(cp + "/comp_notice/list.do?page=" + page);
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
			
			forward(req, resp, "/WEB-INF/views/comp_notice/write.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/comp_notice/list.do");
	}
	
	// 게시글 삭제
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		
	}

}
