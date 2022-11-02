package com.main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.login.SessionInfo;
import com.util.MyServlet;

@WebServlet("/main.do")
public class MainServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri=req.getRequestURI();
		
		if(uri.indexOf("main.do") != -1) {
			
			HttpSession session = req.getSession();
			SessionInfo info = (SessionInfo)session.getAttribute("member");
			
			String cp = req.getContextPath();
			
			if(info == null) {
				resp.sendRedirect(cp + "/");
				return;
			}
			
			forward(req, resp, "/WEB-INF/views/main/main.jsp");
			
		} else if(uri.indexOf("testmain.do") != -1) {
			forward(req, resp, "/WEB-INF/views/main/testmain.jsp");
		}
		
	}
}
