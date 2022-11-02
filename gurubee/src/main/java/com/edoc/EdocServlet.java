package com.edoc;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.login.SessionInfo;
import com.util.FileManager;
import com.util.MyUploadServlet;
import com.util.MyUtil;
import com.util.MyUtilBootstrap;

@MultipartConfig
@WebServlet("/edoc/*")
public class EdocServlet extends MyUploadServlet {
	private static final long serialVersionUID = 1L;

	private String pathname;
	
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
		
		// 파일 저장 경로
		String root = session.getServletContext().getRealPath("/");
		pathname = root + "uploads" + File.separator + "edoc";
				
		String uri=req.getRequestURI();
		
		if(uri.indexOf("main.do") != -1) {
			forward(req, resp, "/WEB-INF/views/edoc/main.jsp");
		} else if(uri.indexOf("write.do") != -1) {
			writeForm(req, resp);
		} else if(uri.indexOf("write_ok.do") != -1) {
			writeSubmit(req, resp, 1);
		} else if(uri.indexOf("write_save.do") != -1) {
			writeSubmit(req, resp, 0);
		} else if(uri.indexOf("list_temp.do") != -1) {
			listTemp(req, resp);
		} else if(uri.indexOf("temp.do") != -1) {
			tempForm(req, resp);
		} else if(uri.indexOf("temp_ok.do") != -1) {
			tempSubmit(req, resp);
		} else if(uri.indexOf("write_searchEmp.do") != -1) {
			listEmp(req, resp);
		} else if(uri.indexOf("write_edocForm.do") != -1) {
			getEdocForm(req, resp);
		} else if(uri.indexOf("list_send.do") != -1) {
			listSend(req, resp);
		} else if(uri.indexOf("list_receive.do") != -1) {
			listReceive(req, resp);
		} else if(uri.indexOf("article.do") != -1) {
			article(req, resp);
		} else if(uri.indexOf("update.do") != -1) {
			updateForm(req, resp);
		} else if(uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp);
		} else if(uri.indexOf("result_ok.do") != -1) {
			insertResult(req, resp);
		} else if(uri.indexOf("download.do") != -1) {
			download(req, resp);
		} else if(uri.indexOf("deleteFile.do") != -1) {
			deleteFile(req, resp);
		}
		
	}
	
	protected void writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = "/WEB-INF/views/edoc/write.jsp";
		String size = req.getParameter("size");
		
		req.setAttribute("mode", "write");
		req.setAttribute("size", size);
		forward(req, resp, path);
	}
	
	// 결재문서 등록 폼
	protected void writeSubmit(HttpServletRequest req, HttpServletResponse resp, int temp) throws ServletException, IOException {
		EdocDAO dao = new EdocDAO();
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		String cp = req.getContextPath();
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/edoc/list_send.do");
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
			
			// Part p = req.getPart("selectFile");
			Map<String, String[]> map = doFileUpload(req.getParts(), pathname);
			if(map != null) {
				String[] saveFiles = map.get("saveFilenames");
				String[] originalFiles = map.get("originalFilenames");
				edocdto.setSaveFiles(saveFiles);
				edocdto.setOriginalFiles(originalFiles);
			}
			
			dao.insertEApproval(edocdto);
			
			
			String app_id[] = req.getParameterValues("empId"); // 수신자 사번

			// 전자결재문서 결재자 등록 - 수신자 아이디 갯수만큼 반복
			for (int i = 0; i < app_id.length; i++) {
				if (!(app_id[i] == null || app_id[i].length() == 0)) {
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
		MyUtil util = new MyUtilBootstrap();
		String cp = req.getContextPath();
		String page = req.getParameter("page");
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		try {
			
			int current_page = 1;
			if(page != null) {
				current_page = Integer.parseInt(page);
			}
			
			// 날짜 myDate, 문서구분 edocSelect
			String myDate = req.getParameter("myDate");
			String edoc = req.getParameter("edocSelect");
			
			// 전체 데이터 갯수
			int dataCount=0;
	
			// 조건 없을 때
			if(myDate==null && edoc==null) {
				dataCount = dao.edocCount(info.getId());
			} else {
				if(edoc!=null) {
					edoc = URLDecoder.decode(edoc, "utf-8");
				} else {
					edoc = "";
				}
				if(myDate!=null) {
					myDate = URLDecoder.decode(myDate, "utf-8");
				} else {
					myDate = "";
				}
				dataCount = dao.edocCount(info.getId(), edoc, myDate);
			}
			
			// 전체 페이지 수
			int size = 5;
			int total_page = util.pageCount(dataCount, size);
			if(current_page > total_page) {
				current_page = total_page;
			}
			
			// 게시물 가져오기
			int offset = (current_page - 1) * size;
			if(offset < 0) offset = 0;

			// 결재문서 리스트 가져오기
			List<EdocDTO> myEdocList= null;
			
			if(myDate==null && edoc==null) {
				myEdocList = dao.listEApproval(info.getId(), offset, size);
			} else {
				myEdocList = dao.listEApproval(info.getId(), offset, size, edoc, myDate);
			}
			
			// 페이징 처리
			String listUrl = cp + "/edoc/list_send.do";
			String articleUrl = cp + "/edoc/article.do?page=" + current_page;
			
			String paging = util.paging(current_page, total_page, listUrl);
	
			req.setAttribute("list", myEdocList);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("size", size);
			req.setAttribute("articleUrl", articleUrl);
			req.setAttribute("paging", paging);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		forward(req, resp, "/WEB-INF/views/edoc/list_send.jsp");
	}
	
	
	// 결재문서 수신함 리스트 
	protected void listReceive(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		EdocDAO dao = new EdocDAO();
		MyUtil util = new MyUtilBootstrap();
		String cp = req.getContextPath();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		try {
			String page = req.getParameter("page");
			
			int current_page = 1;
			if(page != null) {
				current_page = Integer.parseInt(page);
			}
			
			// 날짜 myDate, 문서구분 edocSelect
			String myDate = req.getParameter("myDate");
			String edoc = req.getParameter("edocSelect");
			
			// 전체 데이터 갯수
			int dataCount=0;
			if(edoc!=null) {
				edoc = URLDecoder.decode(edoc, "utf-8");
			} else {
				edoc = "";
			}
			
			if(myDate!=null) {
				myDate = URLDecoder.decode(myDate, "utf-8");
			} else {
				myDate = "";
			}
			
			dataCount = dao.edocCountReceiver(info.getId(), edoc, myDate);
			
			// 전체 페이지 수
			int size = 5;
			int total_page = util.pageCount(dataCount, size);
			if(current_page > total_page) {
				current_page = total_page;
			}
			
			// 게시물 가져오기
			int offset = (current_page - 1) * size;
			if(offset < 0) offset = 0;

			// 결재문서 리스트 가져오기
			List<EdocDTO> myEdocList= null;
			myEdocList = dao.listEApproverReceiver(info.getId(), offset, size, edoc, myDate);
			
			// 페이징 처리
			String listUrl = cp + "/edoc/list_receive.do";
			String articleUrl = cp + "/edoc/article.do?page=" + current_page;
			
			String paging = util.paging(current_page, total_page, listUrl);
	
			req.setAttribute("list", myEdocList);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("size", size);
			req.setAttribute("articleUrl", articleUrl);
			req.setAttribute("paging", paging);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String path = "/WEB-INF/views/edoc/list_receive.jsp";
		forward(req, resp, path);
	}
	
	
	// 글보기
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		EdocDAO dao = new EdocDAO();
		
		String cp = req.getContentType();
		
		String page = req.getParameter("page");

		try {
			int app_num = Integer.parseInt(req.getParameter("app_num"));
			List<EdocEmpDTO> empdto = new ArrayList<>();
			List<EdocDTO> filedto = new ArrayList<>();

			// 문서, 결재자 리스트 가져오기. readEdoc, readEdocEmp
			EdocDTO dto = dao.readEdoc(app_num);
			empdto = dao.readEdocApper(app_num);
			
			// 문서 파일 리스트 가져오기
			filedto = dao.listEdocFile(app_num);
			
			if(dto == null) {
				resp.sendRedirect(cp+"/edoc/list_send");
				return;
			}
			
			req.setAttribute("dto", dto);
			req.setAttribute("listFile", filedto);
			req.setAttribute("empdto", empdto);
			req.setAttribute("page", page);
			
			forward(req, resp, "/WEB-INF/views/edoc/article.jsp");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		// resp.sendRedirect(cp+"/edoc/list_send");
		
	}	

	// 결재하기 - AJAX:JSON
	protected void insertResult(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		EdocDAO dao = new EdocDAO();
		
		String resultMessage = null;
		String state = "false";

		try {
			HttpSession session = req.getSession();
			SessionInfo info = (SessionInfo) session.getAttribute("member");
			
			
			int app_num = Integer.parseInt(req.getParameter("app_num"));
			int app_result = Integer.parseInt(req.getParameter("app_result"));
			int app_level = 0;
			
			// 나의 결재단계 가져오기
			app_level = dao.readAppLevel(info.getId(), app_num);
			System.out.println("내 결재 레벨"+app_level);
			// 1vL 결재자
			if(app_level == 1) {
				resultMessage = dao.insertEdocMyResult1(app_num, info.getId(), app_result);
			} else {
				resultMessage = dao.insertEdocMyResultOver1(app_num, info.getId(), app_result, app_level);
			}
			
			state = "true";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONObject job = new JSONObject();
		job.put("state", state);
		job.put("msg", resultMessage);
		
		resp.setContentType("text/html; charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.print(job.toString());

	}
	
	// 임시보관함 글 리스트 출력
	protected void listTemp(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		try {
			
			EdocDAO dao = new EdocDAO();
			MyUtil util = new MyUtilBootstrap();
			String cp = req.getContextPath();
			
			HttpSession session = req.getSession();
			SessionInfo info = (SessionInfo) session.getAttribute("member");
			
			String page = req.getParameter("page");
			int current_page = 1;
			if(page != null) {
				current_page = Integer.parseInt(page);
			}
			
			// 날짜 myDate, 문서구분 edocSelect
			String myDate = req.getParameter("myDate");
			String edoc = req.getParameter("edocSelect");
			
			// 전체 데이터 갯수
			int dataCount=0;
	
			// 조건 없을 때
			if(myDate==null && edoc==null) {
				dataCount = dao.edocCountTemp(info.getId());
			} else {
				if(edoc!=null) {
					edoc = URLDecoder.decode(edoc, "utf-8");
				} else {
					edoc = "";
				}
				if(myDate!=null) {
					myDate = URLDecoder.decode(myDate, "utf-8");
				} else {
					myDate = "";
				}
				dataCount = dao.edocCountTemp(info.getId(), edoc, myDate);
			}
			
			// 전체 페이지 수
			int size = 5;
			int total_page = util.pageCount(dataCount, size);
			if(current_page > total_page) {
				current_page = total_page;
			}
			
			// 게시물 가져오기
			int offset = (current_page - 1) * size;
			if(offset < 0) offset = 0;

			// 결재문서 리스트 가져오기
			List<EdocDTO> myEdocList= null;
			
			if(myDate==null && edoc==null) {
				myEdocList = dao.listEApprovalTemp(info.getId(), offset, size);
			} else {
				myEdocList = dao.listEApprovalTemp(info.getId(), offset, size, edoc, myDate);
			}
			
			if(myEdocList.size()==0) {
				System.out.println("임시보관 문서 X");
			}
			
			// 페이징 처리
			String listUrl = cp + "/edoc/list_temp.do";
			String articleUrl = cp + "/edoc/article.do?page=" + current_page;
			
			String paging = util.paging(current_page, total_page, listUrl);
			
			req.setAttribute("list", myEdocList);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("size", size);
			req.setAttribute("articleUrl", articleUrl);
			req.setAttribute("paging", paging);
			
			forward(req, resp, "/WEB-INF/views/edoc/list_temp.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// 문서 수정 폼
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		EdocDAO dao = new EdocDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		String page = req.getParameter("page");
		
		try {
			int app_num = Integer.parseInt(req.getParameter("app_num"));
			EdocDTO edocdto = dao.readEdoc(app_num);
			List<EdocDTO> filedto = new ArrayList<>();
			
			boolean b1= false, b2 = false;
			
			// 문서 작성자 확인
			b1 = dao.readEdocWriteId(info.getId(), app_num);
			// 모든 결재결과 0:대기 인지 확인
			b2 = dao.readEdocResult(app_num);
			
			if(b2==false || b1==false) {
				resp.sendRedirect(cp+"/edoc/list_send.do?page="+page);
				return;
			}	
			
			filedto = dao.listEdocFile(app_num);
			

			req.setAttribute("dto", edocdto);
			req.setAttribute("listFile", filedto);
			req.setAttribute("page", page);
			req.setAttribute("mode", "update");
			
			forward(req, resp, "/WEB-INF/views/edoc/write.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/edoc/list_send.do?page="+page);
	}
	
	// 문서 수정
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		EdocDAO dao = new EdocDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		String page = req.getParameter("page");
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp+"/edoc/list_send.do");
			return;
		}
		
		try {
			EdocDTO edocdto = new EdocDTO();
			
			edocdto.setId_write(info.getId());
			edocdto.setApp_num(Integer.parseInt(req.getParameter("app_num")));
			edocdto.setApp_doc(req.getParameter("edocSelect"));
			edocdto.setDoc_form(req.getParameter("content"));
			edocdto.setTitle(req.getParameter("title"));
			
			// 새로운 파일 올리기
			Map<String, String[]> map = doFileUpload(req.getParts(), pathname);
			if (map != null) {
				
				String[] saveFiles = map.get("saveFilenames");
				String[] originalFiles = map.get("originalFilenames");
				edocdto.setSaveFiles(saveFiles);
				edocdto.setOriginalFiles(originalFiles);
			}
			
			dao.updateEdoc(edocdto);
			
			req.setAttribute("page", page);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/edoc/list_send.do?page="+page);
	}
	

	protected void tempForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		EdocDAO dao = new EdocDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		String page = req.getParameter("page");
		
		try {
			int app_num = Integer.parseInt(req.getParameter("app_num"));
			EdocDTO edocdto = dao.readEdoc(app_num);
			List<EdocDTO> filedto = new ArrayList<>();
			
			boolean b1= false;
			
			// 문서 작성자 확인
			b1 = dao.readEdocWriteId(info.getId(), app_num);
			
			if(b1==false) {
				resp.sendRedirect(cp+"/edoc/list_temp.do?page="+page);
				return;
			}	
			
			filedto = dao.listEdocFile(app_num);
			
			req.setAttribute("dto", edocdto);
			req.setAttribute("listFile", filedto);
			req.setAttribute("app_num", app_num);
			req.setAttribute("page", page);
			req.setAttribute("mode", "temp");
			
			forward(req, resp, "/WEB-INF/views/edoc/write_temp.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// resp.sendRedirect(cp+"/edoc/list_temp.do?page="+page);
		
	}
	
	protected void tempSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		EdocDAO dao = new EdocDAO();
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		try {
			int app_num = Integer.parseInt(req.getParameter("app_num"));

			EdocDTO edocdto = new EdocDTO();
			
			edocdto.setId_write(info.getId());
			edocdto.setApp_num(app_num);
			edocdto.setApp_doc(req.getParameter("edocSelect"));
			edocdto.setDoc_form(req.getParameter("content"));
			edocdto.setTitle(req.getParameter("title"));
			edocdto.setTemp(1);
		
			// 파일 여부 확인
			Map<String, String[]> map = doFileUpload(req.getParts(), pathname);
			if (map != null) {
				//FileManager.doFiledelete(pathname, edocdto.getSaveFilename());
				//dao.deleteFile(edocdto.getApp_num());
				
				String[] saveFiles = map.get("saveFilenames");
				String[] originalFiles = map.get("originalFilenames");
				edocdto.setSaveFiles(saveFiles);
				edocdto.setOriginalFiles(originalFiles);
			}
			
			// 이미 저장된 결재자가 있는지 확인
			dao.deleteTempApper(app_num);
			
			dao.updateTempEdoc(edocdto);
			
			String app_id[] = req.getParameterValues("empId"); // 수신자 사번
			
			// 전자결재문서 결재자 등록 - 수신자 아이디 갯수만큼 반복
			for (int i = 0; i < app_id.length; i++) {
				if (! (app_id[i] == null || app_id[i].length() == 0)) {
					EdocEmpDTO empdto = new EdocEmpDTO();
					empdto.setApp_num(app_num);
					empdto.setId_apper(app_id[i]);
					empdto.setApp_level(i + 1);
					empdto.setMemo("memo");

					dao.insertTempEdocApper(empdto);
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp + "/edoc/list_send.do");
	}

	// 파일 다운로드
	protected void download(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		EdocDAO dao = new EdocDAO();
		
		boolean b = false;
		
		try {
			
			int fileNum = Integer.parseInt(req.getParameter("fileNum"));
			
			EdocDTO dto = dao.edocFile(fileNum);
			
			
			if(dto != null) {
				b = FileManager.doFiledownload(dto.getSaveFilename(),
				dto.getOriginalFilename(), pathname, resp);
			}
			
			if(! b) {
				// 브라우저에게 utf-8을 사용할거라는 메시지를 전달
				resp.setContentType("text/html; charset=utf-8");
				// PrintWriter : byte를 문자열 형태로 변환
				// .getWriter  : 
				PrintWriter out = resp.getWriter();
				out.print("<script>alert('파일다운로드가 실패했습니다. 다시 시도해 주세요!'); history.back();</script>");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
	// 수정 시 파일 삭제
	protected void deleteFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		EdocDAO dao = new EdocDAO();
		String page = req.getParameter("page");
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		String cp = req.getContextPath();

		try {

			int fileNum = Integer.parseInt(req.getParameter("fileNum"));
			int app_num = Integer.parseInt(req.getParameter("app_num"));
			EdocDTO dto = dao.edocFile(fileNum);
			// 작성자 일치 여부 확인
			boolean b1= false;
			
			// 문서 작성자 확인
			b1 = dao.readEdocWriteId(info.getId(), app_num);
			
			if(b1==false) {
				resp.sendRedirect(cp+"/edoc/list_send.do?page="+page);
				return;
			}	
			
			// 삭제
			FileManager.doFiledelete(pathname, dto.getSaveFilename());
			
			dao.deleteFile(app_num);
			
			req.setAttribute("page", page);
			req.setAttribute("dto", dto);
			req.setAttribute("mode", "update");
			
			resp.sendRedirect(cp+"/edoc/list_send.do");

			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// resp.sendRedirect(cp+"/edoc/list_send.do");

	}
	
	
}
