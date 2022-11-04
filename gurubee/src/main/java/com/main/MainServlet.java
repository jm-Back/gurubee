package com.main;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.login.SessionInfo;
import com.mypage.MypageDAO;
import com.mypage.MypageDTO;
import com.util.MyServlet;

@WebServlet("/main.do")
public class MainServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri=req.getRequestURI();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		if(info == null) {
			resp.sendRedirect(cp + "/member/login.do");
			return;
		}
		
		if(uri.indexOf("main.do") != -1) {
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH)+ 1;
			int day = cal.get(Calendar.DATE);
			
			MypageDAO mdao = new MypageDAO();
			String s = String.format("%4d%02d%02d", year, month, day);
			MypageDTO todayAttendance = mdao.readAttendance(s, info.getId());

			req.setAttribute("year", year);
			req.setAttribute("month", month);
			req.setAttribute("day", day);
			req.setAttribute("todayAttendance", todayAttendance);
			
			forward(req, resp, "/WEB-INF/views/main/main.jsp");
			
		} else if(uri.indexOf("testmain.do") != -1) {
			forward(req, resp, "/WEB-INF/views/main/testmain.jsp");
		}
		
	}
}
