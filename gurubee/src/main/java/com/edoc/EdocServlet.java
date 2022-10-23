package com.edoc;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.login.SessionInfo;
import com.util.MyServlet;

@WebServlet("/edoc/*")
public class EdocServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri=req.getRequestURI();
		
		if(uri.indexOf("main.do") != -1) {
			forward(req, resp, "/WEB-INF/views/edoc/main.jsp");
		} else if(uri.indexOf("write.do") != -1) {
			writeForm(req, resp);
		} else if(uri.indexOf("write_searchEmp.do") != -1) {
			listEmp(req, resp);
		} else if(uri.indexOf("write_edocForm.do") != -1) {
			getEdocForm(req, resp);
		}
	}
	
	protected void writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글 작성 폼
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		EdocDAO dao = new EdocDAO();

		
		
		
		String path = "/WEB-INF/views/edoc/write.jsp";
		forward(req, resp, path);
	}
	
	protected void listEmp(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 수신자 사원 리스트 - AJAX : Text
		EdocDAO dao = new EdocDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		try {
			
			String dep_name = info.getDep_name();
			String pos_code = info.getPos_name();
			// 결재자 리스트 가져오기
			List<EdocEmpDTO> list = dao.deptEmpList(dep_name, pos_code);
			System.out.println(list +"  "+list.size());
			
			req.setAttribute("list", list);
			
			forward(req, resp, "/WEB-INF/views/edoc/write_searchEmp.jsp");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	protected void getEdocForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
	}

}
