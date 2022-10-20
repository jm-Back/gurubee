package com.mypage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.MyServlet;

@WebServlet("/mypage/*")
public class MypageServlet extends MyServlet{
	public static final long serialVersionUID = 1L;

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();
		
		if(uri.indexOf("mypage.do") != - 1) {
			mypageForm(req, resp); // mypage main [mypage.jsp (tab기능 사용예정)]
			
		} else if(uri.indexOf("mypage_write.do") != -1) {
			mypageWriteForm(req, resp); // mypage 개인정보등록(사진) [mypage_write.jsp (tab1)]
			
		} else if(uri.indexOf("mypage_ok.do") != -1) {
			mypageWriteOkSubmit(req, resp); // mypage 개인정보등록완료 [mypage_ok.jsp (tab1에서 출력)]
			
		} else if (uri.indexOf("update.do") != -1) {
			mypageupdateForm(req, resp); // mypage 개인정보수정 [mypage_write.jsp (tab1에서 출력)]
			
		}else if (uri.indexOf("update_ok.do") != -1) {
			mypageupdateOkSubmit(req, resp);	// mypage 개인정보수정완료
			
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
	
	private void mypageForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		forward(req, resp, "/WEB-INF/views/mypage/mypage.jsp");
		
	}
	
	private void mypageWriteForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		//프로젝트 처음에 작성
		req.setAttribute("mode", "write");
		forward(req, resp, "/WEB-INF/views/project/mypage_write.jsp");
		
	}

	private void mypageWriteOkSubmit(HttpServletRequest req, HttpServletResponse resp) {

	}

	private void mypageupdateForm(HttpServletRequest req, HttpServletResponse resp) {

	}

	private void mypageupdateOkSubmit(HttpServletRequest req, HttpServletResponse resp) {

	}

	private void myattForm(HttpServletRequest req, HttpServletResponse resp) {

	}

	private void myattListForm(HttpServletRequest req, HttpServletResponse resp) {

	}

	private void myattupdateForm(HttpServletRequest req, HttpServletResponse resp) {

	}

	private void myattupdateOkSubmit(HttpServletRequest req, HttpServletResponse resp) {

	}
	
	private void myattarticleForm(HttpServletRequest req, HttpServletResponse resp) {

	}
	
	private void myoffForm(HttpServletRequest req, HttpServletResponse resp) {

	}
	
	private void myoffuseForm(HttpServletRequest req, HttpServletResponse resp) {

	}
	
	private void myofflistForm(HttpServletRequest req, HttpServletResponse resp) {

	}
	
	private void myoffwriteForm(HttpServletRequest req, HttpServletResponse resp) {

	}
	
	private void mypayForm(HttpServletRequest req, HttpServletResponse resp) {

	}
	
	private void mypayarticleForm(HttpServletRequest req, HttpServletResponse resp) {

	}
	
	private void mypaylistSubmit(HttpServletRequest req, HttpServletResponse resp) {

	}
	
	private void myworkSubmit(HttpServletRequest req, HttpServletResponse resp) {

	}
	
	
}
