package com.project;

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


@WebServlet("/project/*")
public class ProjectServlet extends MyServlet{
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
		} else if(uri.indexOf("progress.do") != -1) { //진행현황 수정
			progressUpdate(req, resp);
		} else if(uri.indexOf("progress_ok.do") != -1) {
			progressSubmit(req, resp);
		} else if(uri.indexOf("delete_ok.do") != -1) { //프로젝트 삭제(담당자전용)
			projectdelete(req, resp);
		} else if(uri.indexOf("delete_emp_ok.do") != -1) { //프로젝트 참여자 삭제
			projectempdelete(req, resp);
		} else if(uri.indexOf("add_employee.do") != -1) { //프로젝트 참여자 추가등록
			addProjectemp(req, resp);
		} else if(uri.indexOf("listDetail.do") != -1) { //디테일 시작------!(리스트)
			detailList(req, resp);
		} 
		 
	}


	private void projectForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//프로젝트 메인 리스트
		ProjectDAO dao = new ProjectDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		try {
			
			ProjectDTO dto = new ProjectDTO();
			
			//내 사번
			dto.setPj_id(info.getId());
			//데이터 개수
			int dataCount = dao.dataCount(dto);
			
			//프로젝트 가져오기
			List<ProjectDTO> list = null;
			list = dao.listProject(dto);
			
			//포워딩할 JSP 에 넘길 속성
			req.setAttribute("list", list);
			req.setAttribute("dataCount", dataCount);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/project/list.do");
			return;
		} 

		try {
			
			ProjectDTO dto = new ProjectDTO();
			
			//작성자 (write.jsp 에서는 sessionScope)
			dto.setId_p(info.getId());
	
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

	private void article(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// 프로젝트 상세 보기
		ProjectDAO dao = new ProjectDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		//페이지 처리 x
		try {
			
			String me_id = info.getId();
			String pro_code = req.getParameter("pro_code");
			String pd_code = req.getParameter("pd_code");
			String id_p = req.getParameter("id_p");

			// 1. 프로젝트 메인 정보 가져오기!
			ProjectDTO dto = dao.readProject(pro_code, me_id);
			if (dto == null) { // 프로젝트가 없으면 다시 리스트로
				resp.sendRedirect(cp + "/project/list.do");
				return;
			}

			// 2. 참여자 상세 정보 가져오기 (이름,부서,직책)
			List<ProjectDTO> list_emp = null;
			list_emp = dao.listProjectEmployee(pro_code);
			
			// 3. 작성자 정보 가져오기 readId
			ProjectDTO vo = dao.readId(id_p);
			
			// 4. 추가 참여자 모달 정보 가져오기
			List<ProjectDTO> list_add_e = null;
			list_add_e = dao.listemployee();
			

			//JSP 로 전달할 속성
			req.setAttribute("dto", dto);
			req.setAttribute("pro_code", pro_code);
			req.setAttribute("list_emp", list_emp);
			req.setAttribute("pd_code", pd_code);
			req.setAttribute("vo", vo);
			req.setAttribute("list_add_e", list_add_e);
	

			forward(req, resp, "/WEB-INF/views/project/pro_article.jsp");
			return;
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/project/list.do");
		
	}

	private void updateForm(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// 수정 폼!
		ProjectDAO dao = new ProjectDAO();
		
		String cp = req.getContextPath();
		String pro_code = req.getParameter("pro_code");
		
		try {
			
			ProjectDTO dto= dao.readUpdateProject(pro_code);

			if(dto ==null) {
				resp.sendRedirect(cp+ "/project/list.do");
				return;
			}
			
			req.setAttribute("dto", dto);
			req.setAttribute("mode", "update");
			
			forward(req, resp, "/WEB-INF/views/project/pro_write.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "project/article.do?pro_code=" + pro_code);
		
	}

	private void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// 수정 완료
		ProjectDAO dao = new ProjectDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/project/list.do");
			return;
		}
		
		try {
			ProjectDTO dto = new ProjectDTO();

			dto.setPro_code(req.getParameter("pro_code"));
			dto.setId_p(info.getId());
			dto.setPro_type(req.getParameter("pro_type"));
			dto.setPro_name(req.getParameter("pro_name"));
			dto.setPro_outline(req.getParameter("pro_outline"));
			dto.setPro_content(req.getParameter("pro_content"));
			dto.setPro_sdate(req.getParameter("pro_sdate"));
			dto.setPro_edate(req.getParameter("pro_edate"));
			
			dao.updateProject(dto);

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/project/list.do");
		
	}

	private void progressUpdate(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		
	}

	private void progressSubmit(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		
	}

	
	private void projectdelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// 프로젝트 삭제 (담당자만 가능!)
		ProjectDAO dao = new ProjectDAO();
	
		String cp = req.getContextPath();

		try {
			String pro_code = req.getParameter("pro_code");
			String pd_code = req.getParameter("pd_code");
			
			dao.deleteProject(pro_code, pd_code);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/project/list.do");
		
	}
	
	//프로젝트 참여자 삭제 버튼  (ajax - json)
	private void projectempdelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// 프로젝트 참여자 삭제 버튼
		ProjectDAO dao = new ProjectDAO();
		
		String state = "false";
		
		try {
			String pj_id = req.getParameter("pj_id");
			String pro_code = req.getParameter("pro_code");
			
			dao.deleteEmployeeList(pj_id, pro_code);
			
			//삭제 되면 true
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
	

	private void addProjectemp(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// 프로젝트 참여자 추가등록하기! json 
		ProjectDAO dao = new ProjectDAO();
		
		String state = "false";
		
		try {
			
			//등록할 사원의 id 사번, 해당 프로젝트 pd_code 필요
			String pj_id = req.getParameter("pj_id");
			String pro_code = req.getParameter("pro_code");
			
			//중복 검사
			int result = dao.checkEmployee(pro_code, pj_id);
			if(result > 0) {
				state = "false";
				return;
				
			} else {
				dao.addEmployee(pro_code, pj_id);
				state = "true";
			}

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONObject job = new JSONObject();
		job.put("state", state);
		
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.print(job.toString());
		
		
	}
	

	private void detailList(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// 디테일 리스트 (출력하기) json
		ProjectDAO dao = new ProjectDAO();
		MyUtil util = new MyUtilBootstrap();
		
		try {
			
			String pd_code = req.getParameter("pd_code");
			String page = req.getParameter("pageNo");
			String id_p = req.getParameter("pd_writer");
			
			int current_page = 1;
			if(page !=null) {
				current_page = Integer.parseInt(page);
			}
			
			int dataCount = dao.dataCountDetail(pd_code);
			int size = 1;
			int total_page = util.pageCount(dataCount, size);
			
			if(total_page < current_page) {
				current_page = total_page;
			}
			
			int offset = (current_page - 1) * size;
			if(offset < 0) offset = 0;
			
			//세부 사항 출력!
			List<ProjectDTO> list_detail = dao.detailProjectlist(pd_code, offset, size);
			ProjectDTO me = dao.readId(id_p);
			
			for(ProjectDTO dto : list_detail) {
				dto.setPd_content(util.htmlSymbols(dto.getPd_content()));
			}
			
			req.setAttribute("list_detail", list_detail);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("pageNo", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("me", me);
			
			forward(req, resp, "/WEB-INF/views/project/pro_detail.jsp");
			
			return;
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendError(400);
		
		
	}

	
	
	

}
