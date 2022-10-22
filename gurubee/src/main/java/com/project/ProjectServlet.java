package com.project;

import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.login.SessionInfo;
import com.util.MyServlet;

@WebServlet("/project/*")
public class ProjectServlet extends MyServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();
		
		if(uri.indexOf("list.do") != - 1) {
			projectForm(req, resp);
		} else if(uri.indexOf("write.do") != -1) {
			projectWriteForm(req, resp);
		} else if(uri.indexOf("write_ok.do") != - 1) {
			projectSubmit(req, resp);
		} else if(uri.indexOf("article.do") != -1) {
			article(req, resp);
			//프로젝트 1)내용 수정폼 2)진행현황추가(세부사항) 3)수정완료
		} else if (uri.indexOf("update.do") != -1) {
			updateForm(req, resp);
		}else if (uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp);			
		} else if(uri.indexOf("progress.do") != -1) {
			progressUpdate(req, resp);
		} else if(uri.indexOf("progress_ok.do") != -1) {
			progressSubmit(req, resp);
		} else if(uri.indexOf("delete_ok.do") != -1) {
			projectdelete(req, resp);
		}
		
	}
	

	private void projectForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//프로젝트 메인 리스트
		forward(req, resp, "/WEB-INF/views/project/pro_main.jsp");
		
	}

	private void projectWriteForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//프로젝트 작성폼
		ProjectDAO dao = new ProjectDAO();
		
		try {
			
			List<ProjectDTO> list_e = null;
			List<ProjectDTO> list_m = null;
			list_e = dao.listemployee();
			list_m = dao.listMaster();
			
			//프로젝트 처음에 작성
			req.setAttribute("mode", "write");
			req.setAttribute("list_e", list_e);
			req.setAttribute("list_m", list_m);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		forward(req, resp, "/WEB-INF/views/project/pro_write.jsp");
		
	}

	private void projectSubmit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// 프로젝트 등록하기
		
		ProjectDAO dao = new ProjectDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");

		
		String cp = req.getContextPath();
		/*if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/project/list.do");
			return ;
		} */
		
		
		try {
			
			ProjectDTO dto = new ProjectDTO();
			
			//사원 테이블 정보는 session 에 있죠
			dto.setId_p(info.getId());
			
			System.out.println("dd");
			
			dto.setPro_name(req.getParameter("pro_name"));
			dto.setPro_type(req.getParameter("pro_type"));
			dto.setPro_master(req.getParameter("pro_master"));
			dto.setPro_outline(req.getParameter("pro_outline"));
			dto.setPro_content(req.getParameter("pro_content"));
			dto.setPro_sdate(req.getParameter("pro_sdate"));
			dto.setPro_edate(req.getParameter("pro_edate"));
			
			String str[] = req.getParameterValues("pj_id");
				
			dao.insertProject(dto);
			
			for(int i=0; i<str.length; i++) {
				dto.setPj_id(str[i]);
				dao.insertEmployee(dto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/project/list.do");
		
		
	}

	private void article(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		
	}

	private void updateForm(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		
	}

	private void updateSubmit(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		
	}

	private void progressUpdate(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		
	}

	private void progressSubmit(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		
	}

	private void projectdelete(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		
	}
	

}
