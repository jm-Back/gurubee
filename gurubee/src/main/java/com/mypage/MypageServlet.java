package com.mypage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
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

@MultipartConfig
@WebServlet("/mypage/*")
public class MypageServlet extends MyUploadServlet{
	public static final long serialVersionUID = 1L;

	private String pathname;
	
	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String cp = req.getContextPath();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		if(info == null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		// 이미지를 저장할 경로(pathname)
		String root = session.getServletContext().getRealPath("/");
		pathname = root + "uploads" + File.separator + "profile";
		
		String uri = req.getRequestURI();
		if(uri.indexOf("mypage.do") != - 1) {
			selectemployee(req, resp); // mypage main(사용자정보 불러오기) [mypage.jsp (tab기능 사용예정)]
			
		} else if(uri.indexOf("mypage_update.do") != -1) {
			mypageWriteForm(req, resp); // mypage 개인정보수정 폼 [mypage_write.jsp (tab1에서 출력)]
			
		} else if(uri.indexOf("mypage_update_ok.do") != -1) {
			mypageWriteOkSubmit(req, resp); // // mypage 개인정보수정처리
						
		} else if(uri.indexOf("myatt.do") != -1) {
			myattForm(req, resp); // mypage 근태관리 [myatt.jsp (tab2)]
			
		
		} else if(uri.indexOf("myatt_write.do") != -1) {
			myattinsertForm(req, resp); // mypage 출근등록 [myatt_write.jsp (tab2에서 출력)]
			
		} else if(uri.indexOf("myatt_write.do") != -1) {
			myattupdateForm(req, resp); // mypage 퇴근등록 [myatt_write.jsp (tab2에서 출력)]
			
		} else if(uri.indexOf("myatt_ok.do") != -1) {
			myattupdateOkSubmit(req, resp); // mypage 출퇴근등록 완료
			
		}
	}
	
	private void selectemployee(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 개인정보 보기
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		MypageDAO dao = new MypageDAO();
		
		UserDTO dto = dao.selectemployee(info.getId());
		
		req.setAttribute("dto", dto);
		
		
		forward(req, resp, "/WEB-INF/views/mypage/mypage.jsp");
		
	}
	
	private void mypageWriteForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//mypage에서 개인정보 수정 폼
		MypageDAO dao = new MypageDAO();
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		UserDTO dto = dao.selectemployee(info.getId());
		
		req.setAttribute("dto", dto);
		forward(req, resp, "/WEB-INF/views/mypage/mypage_update.jsp");
	}

	private void mypageWriteOkSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// mypage 개인정보수정 완료
		MypageDAO dao = new MypageDAO();
		
		String cp = req.getContextPath();
		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/member/login_ok.do");
			return;
		}
		
		try {
			HttpSession session = req.getSession();
			SessionInfo info = (SessionInfo) session.getAttribute("member");
			
			UserDTO dto = new UserDTO();
			
			String imageFilename = req.getParameter("ori_filename");
			dto.setOri_filename(imageFilename);
			
			dto.setId(info.getId());
			dto.setPwd(req.getParameter("pwd"));

			String reg1 = req.getParameter("reg");
			String reg2 = req.getParameter("reg2");
			
			dto.setReg(reg1+"-"+reg2);

			dto.setEmail(req.getParameter("email"));
			dto.setPhone(req.getParameter("phone"));
			dto.setTel(req.getParameter("tel"));
			
			Part p = req.getPart("selectFile");
			Map<String, String> map = doFileUpload(p, pathname);
			if (map != null) { // 이미지 파일을 업로드 한 경우
				String filename = map.get("saveFilename");
				// 기존 이미지 파일 지우기
				FileManager.doFiledelete(pathname, imageFilename);
				dto.setOri_filename(filename);
			}
			
			
			dao.mypageWriteForm(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/main.do");
	}


	private void myattForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 근태관리 홈
		MypageDAO dao = new MypageDAO();
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		
		int todayYear = year;
		int todayMonth = month;
		int todayDate = cal.get(Calendar.DATE);
		
		try {
			String y = req.getParameter("year");
			String m = req.getParameter("month");

			if (y != null) {
				year = Integer.parseInt(y);
			}
			if (m != null) {
				month = Integer.parseInt(m);
			}

			// year년 month월 1일의 요일
			cal.set(year, month - 1, 1);
			year = cal.get(Calendar.YEAR);
			month = cal.get(Calendar.MONTH) + 1;
			
			String s;
			
			// 오늘 근태현황
			s = String.format("%4d%02d%02d", todayYear, todayMonth, todayDate);
			MypageDTO todayAttendance = dao.readAttendance(s, info.getId());
			
			// 해당월 근태현황
			s = String.format("%4d%02d", year, month);
			List<MypageDTO> list = dao.listAttendance(s, info.getId());
			
			req.setAttribute("year", year);
			req.setAttribute("month", month);
			
			req.setAttribute("todayYear", todayYear);
			req.setAttribute("todayMonth", todayMonth);
			req.setAttribute("todayDate", todayDate);
			
			req.setAttribute("todayAttendance", todayAttendance);
			req.setAttribute("list", list);
			
		}catch (Exception e) {
			e.printStackTrace();
		}

		
		
		forward(req, resp, "/WEB-INF/views/mypage/myatt.jsp");
	}



	private void myattinsertForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// 출근기록
		MypageDAO dao = new MypageDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String state = "false";
		
		try {
			MypageDTO mdto = new MypageDTO();
			
			mdto.setId(info.getId());
			mdto.setAtt_id(mdto.getAtt_id());
			mdto.setAtt_start(req.getParameter("att_start"));
			mdto.setAtt_end(req.getParameter("att_end"));
			mdto.setAtt_ing(req.getParameter("att_ing"));
			
			dao.attendanceInsert(mdto);
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
	
	
	private void myattupdateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		MypageDAO dao = new MypageDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		UserDTO dto = dao.selectemployee(info.getId());
		
		String state = "false";
		try {
			
			MypageDTO mdto = new MypageDTO();
			
			dto.setId(req.getParameter("id"));
			mdto.setAtt_id(req.getParameter("att_id"));
			mdto.setAtt_start(req.getParameter("att_start").replaceAll("-", ""));
			mdto.setAtt_end(req.getParameter("att_and").replaceAll("-", ""));
			mdto.setAtt_ing(req.getParameter("att_ing"));
			
			System.out.println(mdto.getAtt_end());
			
			if(req.getParameter("allDay")!=null) {
				mdto.setAtt_start("");
				mdto.setAtt_end("");
			}
			
			if(mdto.getAtt_start().length()==0 && mdto.getAtt_end().length()==0 && mdto.getAtt_start().equals(mdto.getAtt_end())) {
				mdto.setAtt_end("");
			}
			
			dao.attendanceupdate(mdto);
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
	

	private void myattupdateOkSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		MypageDAO dao = new MypageDAO();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/mypage/Myatt.do");
			return;
		}
		
		String page = req.getParameter("page");
		
		try {
			MypageDTO dto = new MypageDTO();
			
			dto.setAtt_id(req.getParameter("att_id"));
			dto.setId(info.getId());

			dao.attendanceupdate(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/mypage/myatt.do?page=" + page);
	}
	
	private void myattarticleForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

	}
	
	
}
