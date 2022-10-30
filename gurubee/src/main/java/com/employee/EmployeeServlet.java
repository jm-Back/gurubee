package com.employee;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.login.SessionInfo;

import com.util.MyServlet;
import com.util.MyUtil;
import com.util.MyUtilBootstrap;


@WebServlet("/employee/*")
public class EmployeeServlet extends MyServlet {
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

		if(uri.indexOf("write.do") != - 1) {
	      joinEmployee(req, resp); // 신입사원 등록 join.jsp [mypage.jsp (tab기능 사용예정)]
			
		} else if(uri.indexOf("write_ok.do") != -1) {
			joinSubmit(req, resp); // 신입사원 등록완료  [mypage_write.jsp (tab1)]
			
		} else if(uri.indexOf("update.do") != -1) {
			updateEmployee(req, resp); // 인사이동 등록폼 
			
		} else if (uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp); // 인사이동 등록완료 [mypage_write.jsp (tab1에서 출력)]
			
		} else if (uri.indexOf("list.do") != -1) {
			employeeList(req, resp); // 사원리스트
			
	    } else if (uri.indexOf("userIdCheck.do") != -1) {
		   userIdCheck(req,resp); //사번중복확인
		
	}
		
	}
	
	
		
	private void joinEmployee(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//신입사원 등록 폼 
		req.setAttribute("title",  "신입사원 등록");
		req.setAttribute("mode", "write");
		
		forward(req, resp, "/WEB-INF/views/employee/join.jsp");
	}
	
	private void joinSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	   // 신입사원 등록확인
	
	EmployeeDAO dao = new EmployeeDAO();

	HttpSession session = req.getSession();
	SessionInfo info = (SessionInfo)session.getAttribute("member");


		String cp = req.getContextPath();
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/employee/list.do");
			return;
		} 
    
		try { 
	
			EmployeeDTO dto = new EmployeeDTO();
			
			dto.setId(req.getParameter("id"));
			dto.setName(req.getParameter("name"));
			dto.setPwd(req.getParameter("pwd"));
			String reg1 = req.getParameter("reg1");
			String reg2 = req.getParameter("reg2");
			dto.setReg(reg1+ "-" +reg2);
			
			String phone1 = req.getParameter("phone1");
			String phone2 = req.getParameter("phone2");
			String phone3 = req.getParameter("phone3");
			dto.setPhone(phone1+"-"+phone2+"-"+phone3);
			
			dto.setTel3(req.getParameter("tel3")); // 내선번호 뒷자리만 받을거
			
			String mail1 = req.getParameter("mail1");
			String mail2 = req.getParameter("mail2");
			dto.setEmail(mail1 +"@"+ mail2);
			dto.setDep_code(req.getParameter("dep_code"));
			dto.setPos_code(req.getParameter("pos_code"));
			dto.setNow_working(req.getParameter("now_working"));
			dto.setType(req.getParameter("type"));
			dto.setStartdate(req.getParameter("startdate"));
			
			
			dao.joinEmployee(dto);
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/employee/list.do");
		
		
	}

	
	private void updateEmployee(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	   //인사이동수정 
		req.setAttribute("title",  "인사기록수정");
		req.setAttribute("mode", "update");
		
		forward(req, resp, "/WEB-INF/views/employee/employeelist.jsp");

	}
	private void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	   //인사이동수정확인 
	}
	
	
	private void employeeList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	   //사원리스트 가지고 오기 
		
		EmployeeDAO dao = new EmployeeDAO();

		
		try {
			
			List<EmployeeDTO> list = dao.employeeList();
			System.out.println(list.size());
            
			req.setAttribute("list",list);
		
			forward(req,resp, "/WEB-INF/views/employee/employeelist.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 에러가 발생하면 에러 코드를 전송
		resp.sendError(400);
	}

	
	private void userIdCheck(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// 아이디 중복 검사
		EmployeeDAO dao = new EmployeeDAO();
		
		String userId = req.getParameter("Id");
		EmployeeDTO dto = dao.readEmployee(userId);
		
		String passed ="false";
		if(dto == null) {
			passed = "true";
			
		}
	
		JSONObject job = new JSONObject();
		job.put("passed", passed);
		
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.print(job.toString());
	}
}