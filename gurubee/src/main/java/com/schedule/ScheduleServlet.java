package com.schedule;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.login.SessionInfo;
import com.util.MyServlet;

@WebServlet("/schedule/*")
public class ScheduleServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		if(info == null) {
			String cp = req.getContextPath();
			resp.sendRedirect(cp+"/member/login.do");;
			return;
		}
		
		String uri = req.getRequestURI();
		if(uri.indexOf("main.do")!= -1) {
			scheduleMain(req, resp);
		}
		
		
	}

	
	
	private void scheduleMain(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 캘린더 메인
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1; // 0 ~ 11
		int date = cal.get(Calendar.DATE);
		
		String today = String.format("%04d%02d%02d", year, month, date);
		
		//오늘 날짜 넘기기
		req.setAttribute("today", today);
		forward(req, resp, "/WEB-INF/views/schedule/sch_main.jsp");
		
	}
	
	

}
