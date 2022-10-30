package com.schedule;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

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
		} else if(uri.indexOf("month_list.do")!= -1) {
			month(req, resp);
		} else if(uri.indexOf("insert.do")!= -1) {
			insert(req, resp);
		} else if(uri.indexOf("detail.do")!= -1) {
			detail(req, resp);
		} else if(uri.indexOf("delete.do")!= -1) {
			delete(req, resp);
		} else if(uri.indexOf("update.do")!= -1) {
			update(req, resp);
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
	
	private void month(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 월 달력 jsp
		ScheduleDAO dao = new ScheduleDAO();
		
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
			
			//1일 요일 구하기 (이전 day 구하기 위해 필요)
			cal.set(year, month - 1, 1);
			year = cal.get(Calendar.YEAR);
			month = cal.get(Calendar.MONTH) + 1;
			int week = cal.get(Calendar.DAY_OF_WEEK); // 1~7
			
			//첫 주 이전 날짜
			Calendar scal = (Calendar) cal.clone();
			scal.add(Calendar.DATE, -(week - 1));
			int syear = scal.get(Calendar.YEAR);
			int smonth = scal.get(Calendar.MONTH) + 1;
			int sdate = scal.get(Calendar.DATE);
			
			//마지막 주 말일의 토요일
			Calendar ecal = (Calendar)cal.clone();
			//말일
			ecal.add(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
			//말일의 토요일
			ecal.add(Calendar.DATE, 7 - ecal.get(Calendar.DAY_OF_WEEK));
			int eyear = ecal.get(Calendar.YEAR);
			int emonth = ecal.get(Calendar.MONTH) + 1;
			int edate = ecal.get(Calendar.DATE);
			
			//스케쥴
			String startDay = String.format("%04d%02d%02d", syear, smonth, sdate);
			String endDay = String.format("%04d%02d%02d", eyear, emonth, edate);
			List<ScheduleDTO> list = dao.listMonth(startDay, endDay, info.getId());
			
			
			String s;
			String[][] days = new String[cal.getActualMaximum(Calendar.WEEK_OF_MONTH)][7];

			
			//1일 앞 전달 날짜 및 일정 출력
			int cnt;
			for(int i=1; i<week; i++) {
				s = String.format("%04d%02d%02d", syear, smonth, sdate);
				days[0][i - 1] = "<span class='textDate preMonthDate' data-date='" + s + "' >" + sdate + "</span>";
				
				cnt = 0;
				for(ScheduleDTO dto : list) {
					int sd8 = Integer.parseInt(dto.getSch_sdate());
					int sd4 = Integer.parseInt(dto.getSch_sdate().substring(4));
					int ed8 = -1;
					if(dto.getSch_edate() != null) {
						ed8 = Integer.parseInt(dto.getSch_edate());
					}
					int cn8 = Integer.parseInt(s);
					int cn4 = Integer.parseInt(s.substring(4));
					
					if(cnt ==4) {
						days[0][i - 1] += "<span class='scheduleMore' data-date='" + s + "' >" + "more..." + "</span>";
						break;
					}
					
					if((dto.getSch_repeat()==0 && (sd8 == cn8 || sd8 <= cn8 && ed8 >= cn8 ))
							|| (dto.getSch_repeat()==1 && sd4 == cn4)) {
						days[0][i - 1] += "<span class='scheduleSubject' style='background-color: "+dto.getSc_color() + ";' data-date='" + s + "' data-num='" + dto.getSch_num()
								+ "' >" + dto.getSch_name() + "</span>";
						cnt++;
					} else if((dto.getSch_repeat()==0 && (sd8 > cn8 && ed8 < cn8)) || (dto.getSch_repeat()==1 && sd4 > cn4 )) {
						break;
					}
				}
				
				sdate++;
			}
			
			//해당년 해당월 날짜 및 일정 출력
			int row = 0;
			int n = 0;
			
			jump: for(row = 0; row < days.length; row ++) {
				for(int i= week - 1; i<7; i++) {
					n++;
					s = String.format("%04d%02d%02d", year, month, n);
					
					if (i == 0) {
						days[row][i] = "<span class='textDate sundayDate' data-date='" + s + "' >" + n + "</span>";
					} else if (i == 6) {
						days[row][i] = "<span class='textDate saturdayDate' data-date='" + s + "' >" + n + "</span>";
					} else {
						days[row][i] = "<span class='textDate nowDate' data-date='" + s + "' >" + n + "</span>";
					}
					
					cnt = 0;
					for(ScheduleDTO dto : list) {
						int sd8 = Integer.parseInt(dto.getSch_sdate());
						int sd4 = Integer.parseInt(dto.getSch_sdate().substring(4));
						int ed8 = -1;
						if (dto.getSch_edate() != null) {
							ed8 = Integer.parseInt(dto.getSch_edate());
						}
						
						int cn8 = Integer.parseInt(s);
						int cn4 = Integer.parseInt(s.substring(4));
						
						if(cnt==4) {
							days[row][i] += "<span class='scheduleMore' data-date='" + s + "' >" + "more..." + "</span>";
							break;
						}
						
						if ((dto.getSch_repeat() == 0 && (sd8 == cn8 || sd8 <= cn8 && ed8 >= cn8))
								|| (dto.getSch_repeat() == 1 && sd4 == cn4)) {
							days[row][i] += "<span class='scheduleSubject' style='background-color: "+dto.getSc_color() + ";' data-date='" + s + "' data-num='" + dto.getSch_num()
									+ "' >" + dto.getSch_name() + "</span>";
							cnt++;
						} else if ((dto.getSch_repeat() == 0 && (sd8 > cn8 && ed8 < cn8))
								|| (dto.getSch_repeat() == 1 && sd4 > cn4)) {
							break;
						}
						
					}
					
					if(n== cal.getActualMaximum(Calendar.DATE)) {
						week = i + 1;
						break jump;
					}
				}
				
				week = 1;
				
			}
			
			if(week != 7) {
				n = 0;
				for(int i = week; i<7; i++) {
					n++;
					s = String.format("%04d%02d%02d", eyear, emonth, n);
					days[row][i] = "<span class='textDate nextMonthDate' data-date='" + s + "' >" + n + "</span>";
					
					cnt = 0;
					for(ScheduleDTO dto : list) {
						int sd8 = Integer.parseInt(dto.getSch_sdate());
						int sd4 = Integer.parseInt(dto.getSch_sdate().substring(4));
						int ed8 = -1;
						if (dto.getSch_edate() != null) {
							ed8 = Integer.parseInt(dto.getSch_edate());
						}
						
						int cn8 = Integer.parseInt(s);
						int cn4 = Integer.parseInt(s.substring(4));
						
						if(cnt==4) {
							days[row][i] += "<span class='scheduleMore' data-date='" + s + "' >" + "more..." + "</span>";
							break;
						}
						
						if ((dto.getSch_repeat() == 0 && (sd8 == cn8 || sd8 <= cn8 && ed8 >= cn8))
								|| (dto.getSch_repeat() == 1 && sd4 == cn4)) {
							days[row][i] += "<span class='scheduleSubject' style='background-color: "+dto.getSc_color() + ";' data-date='" + s + "' data-num='" + dto.getSch_num()
									+ "' >" + dto.getSch_name() + "</span>";
							cnt++;
						} else if ((dto.getSch_repeat() == 0 && (sd8 > cn8 && ed8 < cn8))
								|| (dto.getSch_repeat() == 1 && sd4 > cn4)) {
							break;
						}
					}

				}
			}
			
			String today = String.format("%04d%02d%02d", todayYear, todayMonth, todayDate);
			
			req.setAttribute("year", year);
			req.setAttribute("month", month);
			req.setAttribute("todayYear", todayYear);
			req.setAttribute("todayMonth", todayMonth);
			req.setAttribute("todayDate", todayDate);
			req.setAttribute("today", today);
			req.setAttribute("days", days);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		forward(req, resp, "/WEB-INF/views/schedule/month.jsp");

	}
	
	
	private void insert(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// 스케쥴 삽입 
		ScheduleDAO dao = new ScheduleDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String state = "false";
		
		try {
			ScheduleDTO dto = new ScheduleDTO();
			
			
			dto.setSch_id(info.getId());
			dto.setSc_code(req.getParameter("sc_code"));
			dto.setSch_name(req.getParameter("sch_name"));
			dto.setSch_content(req.getParameter("sch_content"));
			dto.setSch_sdate(req.getParameter("sch_sdate").replaceAll("-", ""));
			dto.setSch_edate(req.getParameter("sch_edate").replaceAll("-", ""));
			dto.setSch_stime(req.getParameter("sch_stime").replaceAll(":", ""));
			dto.setSch_etime(req.getParameter("sch_etime").replaceAll(":", ""));
			
			
			if(req.getParameter("allDay")!= null) {
				dto.setSch_stime("");
				dto.setSch_etime("");
			}
			
			if(dto.getSch_stime().length() ==0 && dto.getSch_etime().length() ==0 && dto.getSch_sdate().equals(dto.getSch_edate())) {
				dto.setSch_edate("");
			}
			
			dto.setSch_repeat(Integer.parseInt(req.getParameter("sch_repeat")));
			if(req.getParameter("sch_repeat_c").length()!=0) {
				dto.setSch_repeat_c(Integer.parseInt(req.getParameter("sch_repeat_c")));
				
				dto.setSch_edate("");
				dto.setSch_stime("");
				dto.setSch_etime("");
			}
			dto.setSch_content(req.getParameter("sch_content"));
			
			dao.insertSchedule(dto);
			state = "true";
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONObject job = new JSONObject();
		job.put("state", state);
		
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.print(job.toString());
		
		
	}
	
	
	private void detail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 일정 상세 보기
		ScheduleDAO dao = new ScheduleDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		try {
			
			String date = req.getParameter("date");
			String snum = req.getParameter("num");
			
			Calendar cal = Calendar.getInstance();
			
			// 오늘
			String today = String.format("%04d%02d%02d", 
					cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE));
			if (date == null || ! Pattern.matches("^\\d{8}$", date)) {
				date = today;
			}
			
			//일정 출력할 년,월,일
			int year = Integer.parseInt(date.substring(0, 4));
			int month = Integer.parseInt(date.substring(4, 6));
			int day = Integer.parseInt(date.substring(6));
			
			cal.set(year, month-1, day);
			year = cal.get(Calendar.YEAR);
			month = cal.get(Calendar.MONTH) +1;
			day = cal.get(Calendar.DATE);
			
			cal.set(year, month-1, 1);
			
			//당일 전체일정 리스트 가져오기
			date = String.format("%04d%02d%02d", year, month, day);
			List<ScheduleDTO> list = dao.listDay(date, info.getId());
			
			
			String num = null;
			ScheduleDTO dto = null;
			if(snum!=null) {
				num = snum;
				dto = dao.read(num);
			}
			

			if(dto ==null && list.size() > 0) {
				dto = dao.read(list.get(0).getSch_num());
			}
			
			
			req.setAttribute("year", year);
			req.setAttribute("month", month);
			req.setAttribute("day", day);
			req.setAttribute("date", date);
			
			req.setAttribute("today", today);
			req.setAttribute("dto", dto);
			req.setAttribute("list", list);
			
			forward(req, resp, "/WEB-INF/views/schedule/day.jsp");
			
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendError(400);
		
	}
	
	private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// 삭제하기
		ScheduleDAO dao = new ScheduleDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String state = "false";
		
		try {
			String sch_num = req.getParameter("num");
			
			dao.delete(sch_num, info.getId());
			state = "true";

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONObject job = new JSONObject();
		job.put("state", state);
		
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.print(job.toString());
	
		
	}
	
	private void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// 업데이트
		
		ScheduleDAO dao = new ScheduleDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String state = "false";
		try {
			
			ScheduleDTO dto = new ScheduleDTO();
			
			dto.setSch_id(info.getId());
			dto.setSch_num(req.getParameter("sch_num"));
			dto.setSch_name(req.getParameter("sch_name"));
			dto.setSc_code(req.getParameter("sc_code"));
			dto.setSch_content(req.getParameter("sch_content"));
			dto.setSch_sdate(req.getParameter("sch_sdate").replaceAll("-", ""));
			dto.setSch_edate(req.getParameter("sch_edate").replaceAll("-", ""));
			dto.setSch_stime(req.getParameter("sch_stime").replaceAll(":", ""));
			dto.setSch_etime(req.getParameter("sch_etime").replaceAll(":", ""));
			
			if(req.getParameter("allDay")!=null) {
				dto.setSch_stime("");
				dto.setSch_etime("");
			}
			
			if(dto.getSch_stime().length()==0 && dto.getSch_etime().length()==0 && dto.getSch_sdate().equals(dto.getSch_edate())) {
				dto.setSch_edate("");
			}
			
			dto.setSch_repeat(Integer.parseInt(req.getParameter("sch_repeat")));
			if(req.getParameter("sch_repeat_c").length()!=0) {
				dto.setSch_repeat_c(Integer.parseInt(req.getParameter("sch_repeat_c")));
				
				dto.setSch_edate("");
				dto.setSch_stime("");
				dto.setSch_etime("");
			}	
			dao.update(dto);
			state = "true";
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONObject job = new JSONObject();
		job.put("state", state);

		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.print(job.toString());
		

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
