package com.edoc;

import java.io.IOException;
import java.io.PrintWriter;
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
			writeSubmit(req, resp);
		}  else if(uri.indexOf("write_searchEmp.do") != -1) {
			listEmp(req, resp);
		} else if(uri.indexOf("write_edocForm.do") != -1) {
			getEdocForm(req, resp);
		}
	}
	
	protected void writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글 작성 폼
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		// EdocDAO dao = new EdocDAO();

		String path = "/WEB-INF/views/edoc/write.jsp";
		forward(req, resp, path);
	}
	
	protected void writeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글 작성 폼
		EdocDAO dao = new EdocDAO();
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/board/list.do");
			return;
		}
		
		try {
			EdocDTO edocdto = new EdocDTO();
			
			edocdto.setId_write(info.getId());
			edocdto.setApp_doc(req.getParameter("edoc"));
			edocdto.setApp_doc(req.getParameter("content"));
			edocdto.setTemp(Integer.parseInt(req.getParameter("temp")));
			
			
			EdocEmpDTO empdto = new EdocEmpDTO();
			// 넘어온 수신자 id 배열에 담고 dto 넣기
			// empdto.setId_apper(req.getParameter("id_apper"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
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
			/* 엔터를 <br>로
			 *  for(ReplyDTO dto : listReply) {
				dto.setContent(dto.getContent().replaceAll("\n", "<br>"));
				}
			 */
			req.setAttribute("formdto", formdto);
			
			forward(req, resp, "/WEB-INF/views/edoc/write_edocForm.jsp");
			
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 에러가 발생하면 에러 코드를 전송
		resp.sendError(400);
	}

}
