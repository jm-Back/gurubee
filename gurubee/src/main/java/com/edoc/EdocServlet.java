package com.edoc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

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
		} else if(uri.indexOf("modaltest.do") != -1) {
			forward(req, resp, "/WEB-INF/views/edoc/modaltest.jsp");
		}
	}
	
	private void writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글 작성 폼
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		EdocDAO dao = new EdocDAO();
		
		
		
		EdocEmpDTO logindto = dao.loginMemberInfo(info.getUserId());

		System.out.println(logindto.getName()); // 이렇게 하면 안됨 !
		System.out.println(info.getUserName()); // session 에서 가져오기 !!
		
		System.out.println(logindto.getDept());
		System.out.println(logindto.getPisition());
		
		req.setAttribute("logindto", logindto);
		
		String path = "/WEB-INF/views/edoc/write.jsp";
		forward(req, resp, path);
	}

}
