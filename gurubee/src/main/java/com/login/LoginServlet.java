package com.login;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.util.MyServlet;

@WebServlet("/member/*")
public class LoginServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		
		String uri = req.getRequestURI();

		if (uri.indexOf("login.do") != -1) {
			loginForm(req, resp);
		} else if (uri.indexOf("login_ok.do") != -1) {
			loginSubmit(req, resp);
		} else if (uri.indexOf("logout.do") != -1) {
			logout(req, resp);
		} else if (uri.indexOf("sitemap.do") != -1) {
			String path = "/WEB-INF/views/member/sitemap.jsp";
			forward(req, resp, path);
			return;
		}
	}

	private void loginForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 로그인 폼
		String path = "/WEB-INF/views/member/login.jsp";
		forward(req, resp, path);
	}

	private void loginSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 로그인 처리
		// 세션객체. 세션 정보는 서버에 저장(로그인 정보, 권한등을 저장)
		HttpSession session = req.getSession();
		
		LoginDAO dao = new LoginDAO();
		String cp = req.getContextPath();

		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/");
			return;
		}

		String userId = req.getParameter("userId");
		String userPwd = req.getParameter("userPwd");

		SessionInfo info = dao.loginMember(userId, userPwd);
		
		if (info == null) { // 사원번호, 패스워드 일치X. 
			// 사번이나 아이디가 일치하지 않는 경우 (다시 로그인 폼으로)
			String msg = "사원번호 또는 패스워드가 일치하지 않습니다.";
			req.setAttribute("message", msg);

			// 로그인 화면으로 포워딩
			// resp.sendRedirect(cp + "/index.jsp");
			String path = "/WEB-INF/views/member/login.jsp";
			forward(req, resp, path);
			// loginForm(req, resp);
			return;

		} else { // 로그인 정보가 있으면
			// info = dao.loginMember(info.getId(), info.getPwd());
			
			String birth = info.getReg().substring(0, 6);
			if (info.getPwd().equals(birth)) { // 초기 패스워드
				// dao.updateMember(userId, userPwd);
				// resp.sendRedirect(cp + "/member/updatePwdForm.do");
				String path = "/WEB-INF/views/member/updatePwdForm.jsp";
				forward(req, resp, path);
				return;
			}

			
			// 유지시간: 120분
	         session.setMaxInactiveInterval(120 * 60);

	         // 세션에 저장할 내용
	         // SessionInfo info = new SessionInfo(); 

	         // 세션에 member이라는 이름으로 저장
	         session.setAttribute("member", info);

	         req.setAttribute("dto", info);

	         forward(req, resp, "/WEB-INF/views/main/main.jsp");
	         return;

		}

	}

	private void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 로그아웃
		HttpSession session = req.getSession();
		String cp = req.getContextPath();

		// 세션에 저장된 정보를 지운다.
		session.removeAttribute("member");

		// 세션에 저장된 모든 정보를 지우고 세션을 초기화 한다.
		session.invalidate();

		// 루트로 리다이렉트
		resp.sendRedirect(cp + "/");
	}
	/*
	 * private void passwordForm(HttpServletRequest req, HttpServletResponse resp)
	 * throws ServletException, IOException { String cp = req.getContextPath();
	 * 
	 * LoginDAO dao = new LoginDAO();
	 * 
	 * // dao.updateMember(id, pwd);
	 * 
	 * forward(req, resp, "/WEB-INF/views/main/main.jsp"); }
	 */

}
