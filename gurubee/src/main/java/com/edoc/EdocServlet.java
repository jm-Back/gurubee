package com.edoc;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.login.SessionInfo;
import com.util.MyServlet;

@MultipartConfig
@WebServlet("/edoc/*")
public class EdocServlet extends MyServlet {
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
		
		String uri=req.getRequestURI();
		
		if(uri.indexOf("main.do") != -1) {
			forward(req, resp, "/WEB-INF/views/edoc/main.jsp");
		} else if(uri.indexOf("write.do") != -1) {
			writeForm(req, resp);
		} else if(uri.indexOf("write_ok.do") != -1) {
			writeSubmit(req, resp, 1);
		} else if(uri.indexOf("write_save.do") != -1) {
			writeSubmit(req, resp, 0);
		} else if(uri.indexOf("write_searchEmp.do") != -1) {
			listEmp(req, resp);
		} else if(uri.indexOf("write_edocForm.do") != -1) {
			getEdocForm(req, resp);
		} else if(uri.indexOf("list_send.do") != -1) {
			listSend(req, resp);
		} else if(uri.indexOf("list_receive.do") != -1) {
			listReceive(req, resp);
		} else if(uri.indexOf("appResult.do") != -1) {
			currentAppResult(req, resp);
		} 
		
	}
	
	protected void writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = "/WEB-INF/views/edoc/write.jsp";
		forward(req, resp, path);
	}
	
	// 결재문서 등록 폼
	protected void writeSubmit(HttpServletRequest req, HttpServletResponse resp, int temp) throws ServletException, IOException {
		// temp:임시구분
		EdocDAO dao = new EdocDAO();
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/board/list.do");
			return;
		}
		
		try {
			// 전자결재문서 등록
			EdocDTO edocdto = new EdocDTO();
			
			edocdto.setId_write(info.getId());
			edocdto.setApp_doc(req.getParameter("edocSelect"));
			edocdto.setDoc_form(req.getParameter("content"));
			edocdto.setTitle(req.getParameter("title"));
			edocdto.setTemp(temp);
			
			System.out.println(edocdto.getDoc_form());
			System.out.println(edocdto.getId_write());
			
			dao.insertEApproval(edocdto);
			
			String app_id[] = req.getParameterValues("empId"); // 수신자 사번
			
			// 전자결재문서 결재자 등록 - 수신자 아이디 갯수만큼 반복
			for (int i = 0; i < app_id.length; i++) {
				if (! (app_id[i] == null || app_id[i].length() == 0)) {
					System.out.println(app_id[i]);
					EdocEmpDTO empdto = new EdocEmpDTO();
					empdto.setId_apper(app_id[i]);
					empdto.setApp_level(i + 1);
					empdto.setMemo("memo");

					dao.insertEApprover(empdto);
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp + "/edoc/list_send.do");
	}
	
	protected void listEmp(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 수신자 사원 리스트 - AJAX:text
		EdocDAO dao = new EdocDAO();
		
		try {
			int pos_code = Integer.parseInt(req.getParameter("pos_code"));
			// 특정 직급 사원 리스트 가져오기
			List<EdocEmpDTO> list = dao.posEmpList(pos_code);
			
			req.setAttribute("list", list);
			req.setAttribute("pos_code", pos_code);
			forward(req, resp, "/WEB-INF/views/edoc/write_searchEmp.jsp");
			
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 에러가 발생하면 에러 코드를 전송
		resp.sendError(400);
	}
	
	
	protected void getEdocForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 문서폼 가져오기 - AJAX:text 
		EdocDAO dao = new EdocDAO();
		EdocFormDTO formdto = null;
		
		try {
			String form = req.getParameter("edoc");
			
			formdto = dao.findByForm(form);
			req.setAttribute("formdto", formdto);
			
			forward(req, resp, "/WEB-INF/views/edoc/write_edocForm.jsp");
			
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendError(400);
	}

	// 결재문서 발신함 리스트 
	protected void listSend(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		EdocDAO dao = new EdocDAO();
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		try {
			// 결재문서 리스트 가져오기
			String id = info.getId();
			List<EdocDTO> myEdocList = dao.listEApproval(id);

			req.setAttribute("list", myEdocList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		forward(req, resp, "/WEB-INF/views/edoc/list_send.jsp");
	}
	
	// 문서의 처리결과 가져오기
	protected void currentAppResult(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 문서의 처ꈰ결과 가져오기. AJAX:JSON
		EdocDAO dao = new EdocDAO();
		
		try {
			/*
			int app_num = Integer.parseInt(req.getParameter("app_num"));
			
			String[] num_array = req.getParameterValues("app_num");
					
			for(int i=0; i<num_array.length; i++){
				String result = dao.resultApprover(Integer.parseInt(num_array[i]));
				
				System.out.println(num_array[i] + "번 문서 처리결과:" +result);
			}			
			
			
			JSONObject job = new JSONObject();
			job.put("result", result);
			
			resp.setContentType("text/html; charset=utf-8");
			PrintWriter out = resp.getWriter();
			out.print(job.toString());
			
			return;
			*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendError(400);
	}
	
	// 결재문서 수신함 리스트 
	protected void listReceive(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = "/WEB-INF/views/edoc/list_receive.jsp";
		forward(req, resp, path);
	}
}
