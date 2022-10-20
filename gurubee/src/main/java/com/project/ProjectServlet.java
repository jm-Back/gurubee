package com.project;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		
		
		forward(req, resp, "/WEB-INF/views/project/pro_main.jsp");
		
	}

	private void projectWriteForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

	private void projectSubmit(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		
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
