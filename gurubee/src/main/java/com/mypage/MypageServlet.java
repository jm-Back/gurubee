package com.mypage;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.login.SessionInfo;
import com.util.MyServlet;

@WebServlet("/mypage/*")
public class MypageServlet extends MyServlet{
	public static final long serialVersionUID = 1L;

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
		
		String uri = req.getRequestURI();
		if(uri.indexOf("mypage.do") != - 1) {
			selectemployee(req, resp); // mypage main(사용자정보 불러오기) [mypage.jsp (tab기능 사용예정)]
			
		} else if(uri.indexOf("mypage_update.do") != -1) {
			mypageWriteForm(req, resp); // mypage 개인정보수정 폼 [mypage_write.jsp (tab1에서 출력)]
			
		} else if(uri.indexOf("mypage_update_ok.do") != -1) {
			mypageWriteOkSubmit(req, resp); // // mypage 개인정보수정처리
						
		} else if(uri.indexOf("myatt.do") != -1) {
			myattForm(req, resp); // mypage 근태관리 [myatt.jsp (tab2)]
			
		} else if(uri.indexOf("myatt_list.do") != -1) {
			myattListForm(req, resp); // mypage 월별근태관리조회 [myatt_list.jsp (tab2에서 출력)]
			
		} else if(uri.indexOf("myatt_write.do") != -1) {
			myattupdateForm(req, resp); // mypage 출퇴근등록 [myatt_write.jsp (tab2에서 출력)]
			
		} else if(uri.indexOf("myatt_ok.do") != -1) {
			myattupdateOkSubmit(req, resp); // mypage 출퇴근등록 완료
			
		} else if(uri.indexOf("myatt_article.do") != -1) {
			myattarticleForm(req, resp); // mypage 총근무시간 출력 [myatt_article.jsp (tab2에서 출력)]
			
		} else if(uri.indexOf("myoff.do") != -1) {
			myoffForm(req, resp); // mypage 연차관리 출력 [myoff.jsp (tab3)]
				
		} else if(uri.indexOf("myoff_use.do") != -1) {
			myoffuseForm(req, resp); // mypage 연차사용현황 출력 [myoff_use.jsp (tab3에서 출력)]
				
		} else if(uri.indexOf("myoff_list.do") != -1) {
			myofflistForm(req, resp); // mypage 연차신청내역 출력 [myoff_list.jsp (tab3에서 출력)]
		
		} else if(uri.indexOf("myoff_write.do") != -1) {
			myoffwriteForm(req, resp); // mypage 연차/휴가신청 출력 [myoff_write.jsp (tab3에서 출력)]
		
		} else if(uri.indexOf("mypay.do") != -1) {
			mypayForm(req, resp); // mypage 급여명세서 출력 [mypay.jsp (tab4)]
		
		} else if(uri.indexOf("mypay_article.do") != -1) {
			mypayarticleForm(req, resp); // mypage  월별급여명세서조회 [mypay_article.jsp (tab4에서 출력)]
		
		} else if(uri.indexOf("mypay_list.do") != -1) {
			mypaylistSubmit(req, resp); // mypage 급여명세서출력[mypay_article.jsp (tab4에서 출력)]
		
		} else if(uri.indexOf("mywork.do") != -1) {
			myworkSubmit(req, resp); // mypage 재직증명서출력 [입사일 받아오기 (tab4에서 출력)]
		
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
			resp.sendRedirect(cp + "/");
			return;
		}
		
		try {
			HttpSession session = req.getSession();
			SessionInfo info = (SessionInfo) session.getAttribute("member");
			
			UserDTO dto = new UserDTO();
			
			dto.setOri_filename(req.getParameter("ori_filename"));
			
			dto.setId(info.getId());
			dto.setPwd(req.getParameter("pwd"));

			String reg1 = req.getParameter("reg");
			String reg2 = req.getParameter("reg2");
			
			dto.setReg(reg1+"-"+reg2);

			dto.setEmail(req.getParameter("email"));
			dto.setPhone(req.getParameter("phone"));
			dto.setTel(req.getParameter("tel"));
			
			dao.mypageWriteForm(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/");
	}


	private void myattForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 근태관리 홈
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		MypageDAO dao = new MypageDAO();
		
		UserDTO dto = dao.selectemployee(info.getId());
		List<MypageDTO> listMyAtt = dao.myattForm(info.getId());
		
		req.setAttribute("dto", dto);
		req.setAttribute("listMyAtt", listMyAtt);
		
		forward(req, resp, "/WEB-INF/views/mypage/myatt.jsp");
	}

	private void myattListForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	private void myattupdateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

	}

	private void myattupdateOkSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

	}
	
	private void myattarticleForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

	}
	
	private void myoffForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// 연차관리
	}
	
	private void myoffuseForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

	}
	
	private void myofflistForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

	}
	
	private void myoffwriteForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

	}
	
	private void mypayForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// 급여명세서
	}
	
	private void mypayarticleForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

	}
	
	private void mypaylistSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

	}
	
	private void myworkSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

	}
	
	
}
