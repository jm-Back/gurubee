package com.comp_notice;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.MyServlet;
import com.util.MyUtil;
import com.util.MyUtilBootstrap;

@WebServlet("/comp_notice/*")
public class CompNoticeServlet extends MyServlet {
	// 전체 공지사항 게시판 서블릿
	private static final long serialVersionUID = 1L;

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri = req.getRequestURI();
		
		if(uri.indexOf("list.do") != -1) {
			list(req,resp);
		} else if(uri.indexOf("write.do") != -1) {
			writeForm(req,resp);
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
				
			}
			
		} catch (Exception e) {
			
		}
		
		String path = "/WEB-INF/views/comp_notice/list.jsp";
		forward(req, resp, path);
	}
	
	protected void writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = "/WEB-INF/views/comp_notice/write.jsp";
		forward(req, resp, path);
	}

}
