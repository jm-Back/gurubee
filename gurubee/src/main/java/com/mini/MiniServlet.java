package com.mini;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.login.SessionInfo;
import com.schedule.ScheduleDTO;
import com.util.MyServlet;

@WebServlet("/mini/*")
public class MiniServlet extends MyServlet {

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
			scheduleMini(req, resp);
		} else if(uri.indexOf("month_list.do")!= -1) {
			monthMini(req, resp);
		}
		
	}

	private void scheduleMini(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//메인
		
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1; // 0 ~ 11
		int date = cal.get(Calendar.DATE);
		
		String today = String.format("%04d%02d%02d", year, month, date);
		
		//오늘 날짜 넘기기
		req.setAttribute("today", today);
		forward(req, resp, "/WEB-INF/views/mini/minisch.jsp");
		
	}


	private void monthMini(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 미니 달력
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		//오늘 날짜 기준으로 구할거니까 캘린더 구하기
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1; // 0 ~ 11
		int todayYear = year;
		int todayMonth = month;
		int todayDate = cal.get(Calendar.DATE);
		
		try {
			
			String y = req.getParameter("year");
			String m = req.getParameter("month");
			
			if( y!=null) {
				year = Integer.parseInt(y);
			}
			
			if( m!=null) {
				month = Integer.parseInt(m);
			}
			
			//1일 요일 구하기 (이전 day 구하기 위해 필요하다)
			cal.set(year, month - 1, 1);
			year = cal.get(Calendar.YEAR);
			month = cal.get(Calendar.MONTH) + 1;
			int week = cal.get(Calendar.DAY_OF_WEEK); // 1~7
			
			//첫 주 이전 날짜 (이전달)
			Calendar scal = (Calendar) cal.clone();
			scal.add(Calendar.DATE, -(week - 1));
			int syear = scal.get(Calendar.YEAR);
			int smonth = scal.get(Calendar.MONTH) + 1;
			int sdate = scal.get(Calendar.DATE);
			
			//마지막 주 말일의 토요일
			Calendar ecal = (Calendar)cal.clone();
			
			//말일
			ecal.add(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
			
			//말일 토요일
			ecal.add(Calendar.DATE, 7 - ecal.get(Calendar.DAY_OF_WEEK));
			int eyear = ecal.get(Calendar.YEAR);
			int emonth = ecal.get(Calendar.MONTH) + 1;
			int edate = ecal.get(Calendar.DATE);
			
			String s;
			String[][] days = new String[cal.getActualMaximum(Calendar.WEEK_OF_MONTH)][7];
			
			//1일 앞 전달 날짜 
			int cnt;
			for(int i=1; i<week; i++) {
				s = String.format("%04d%02d%02d", syear, smonth, sdate);
				days[0][i - 1] = "<span class='textDate preMonthDate' data-date='" + s + "' >" + sdate + "</span>";
				
				sdate++;
			}
			
			//해당년, 해당월 날짜 및 일정
			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		forward(req, resp, "/WEB-INF/views/mini/minisch.jsp");
		
		
		
		
	}
	
	
	
	
}
