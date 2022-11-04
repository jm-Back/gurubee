package com.pay;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.employee.EmployeeDAO;
import com.employee.EmployeeDTO;
import com.login.SessionInfo;
import com.util.MyServlet;

@WebServlet("/pay/*")
public class PayServlet extends MyServlet {
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
        System.out.println(uri);
        
		if(uri.indexOf("pay_write.do") != - 1) {
	      writePay(req, resp); // 급여 정보 등록 
			
		} else if(uri.indexOf("pay_write_ok.do") != -1) {
		  writePayok(req, resp); // 급여 등록완료  
		  
		} else if(uri.indexOf("pay_update.do") != -1) {
		   updatePay(req, resp); // 급여 등록완료
			
		} else if(uri.indexOf("pay_update_ok.do") != -1) {
	       updatePayOk(req, resp); // 급여 등록완료
			  
		} else if (uri.indexOf("pay_list.do") != -1) {
		   payList(req, resp); // 급여리스트
			
		} else if(uri.indexOf("sal_update.do") != -1) {
		  writeSal(req, resp); // 연봉 등록 
			
		} else if(uri.indexOf("sal_update_ok.do") != -1) {
	      writeSalOk(req, resp); // 연봉 등록 완료   
			  
		} else if (uri.indexOf("sal_list.do") != -1) {
		   salList(req, resp); // 연봉리스트 
		   
		} else if (uri.indexOf("sal_pay_main.do") != 1) {
			   main(req, resp); //(연봉,급여 등록 메인)
		
	}
	
	}
	
	private void main(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {
		
				req.setAttribute("title", "급여정보등록");
				req.setAttribute("mode", "write");
				
				EmployeeDAO dao = new EmployeeDAO();		
		        List<EmployeeDTO> list = dao.employeeList();
		        System.out.println(list.size());
		        
				req.setAttribute("list",list);
			
				forward(req, resp, "/WEB-INF/views/pay/pay_writelist.jsp");
				return;
			}
		
	
	private void writePay(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//연봉정보 
		req.setAttribute("title", "급여정보등록");
		req.setAttribute("mode", "write");
		
		EmployeeDAO dao = new EmployeeDAO();		
        List<EmployeeDTO> list = dao.employeeList();
        System.out.println(list.size());
        
		req.setAttribute("list",list);
	
		forward(req, resp, "/WEB-INF/views/pay/pay_writelist.jsp");
		return;
	}
	
	private void writePayok(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PayDAO dao = new PayDAO();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");


			String cp = req.getContextPath();
			if(req.getMethod().equalsIgnoreCase("GET")) {
				resp.sendRedirect(cp + "/pay/pay_write.do");
				return;
			} 
	    
			try { 
		
				PayDTO dto = new PayDTO();
				
				dto.setPay_id(req.getParameter("p_id"));
				dto.setPayment(Long.parseLong(req.getParameter("payment")));
				dto.setMeal_pay(Long.parseLong(req.getParameter("meal_pay")));
				dto.setBenefit(Long.parseLong(req.getParameter("benefit")));
				dto.setEtc_pay(Long.parseLong(req.getParameter("etc_pay")));
				dto.setBonus(Long.parseLong(req.getParameter("bonus")));
				dto.setResidence_tax(Long.parseLong(req.getParameter("residence_tax")));
				dto.setMedical_ins(Long.parseLong(req.getParameter("medical_ins")));
				dto.setSafety_ins(Long.parseLong(req.getParameter("safety_ins")));
				dto.setEmployee_ins(Long.parseLong(req.getParameter("employee_ins")));
				dto.setLongterm_ins(Long.parseLong(req.getParameter("longterm_ins")));
				dto.setPay_date(req.getParameter("pay_date"));
				
			
				
			    
				
				dao.writePay(dto);
		
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			resp.sendRedirect(cp + "/pay/pay_write.do");
	}

	private void updatePay(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		

	  EmployeeDAO dao = new EmployeeDAO();
      String id = req.getParameter("id");

      EmployeeDTO dto = dao.readEmployee(id);
	   
		req.setAttribute("dto", dto);
		req.setAttribute("title",  "급여등록수정");
		req.setAttribute("mode", "update");
		
    forward(req, resp, "/WEB-INF/views/pay/pay_write.jsp");
}

	
	private void updatePayOk (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		PayDAO dao = new PayDAO();
		String cp = req.getContextPath();
		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/pay/pay_write.do");
			return;
		}

		try {
		
            PayDTO dto = new PayDTO();
			
            dto.setPay_id(req.getParameter("p_id"));
			dto.setPayment(Long.parseLong(req.getParameter("payment")));
			dto.setMeal_pay(Long.parseLong(req.getParameter("meal_pay")));
			dto.setBenefit(Long.parseLong(req.getParameter("benefit")));
			dto.setEtc_pay(Long.parseLong(req.getParameter("etc_pay")));
			dto.setBonus(Long.parseLong(req.getParameter("bonus")));
			dto.setResidence_tax(Long.parseLong(req.getParameter("residence_tax")));
			dto.setMedical_ins(Long.parseLong(req.getParameter("medical_ins")));
			dto.setSafety_ins(Long.parseLong(req.getParameter("safety_ins")));
			dto.setEmployee_ins(Long.parseLong(req.getParameter("employee_ins")));
			dto.setLongterm_ins(Long.parseLong(req.getParameter("longterm_ins")));
			dto.setPay_date(req.getParameter("pay_date"));

			dao.updatePay(dto);
		
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/pay/pay_write.do");
	}



	private void payList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		PayDAO dao = new PayDAO();

			
			try {
				
				// 월별 사원리스트 
				List<PayDTO> list =dao.monthpaylist("pay_date");
				
				req.setAttribute("list", list);
				req.setAttribute("pay_date", dao);
				 
				forward(req,resp, "/WEB-INF/views/pay/pay_list.jsp");
				return;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			// 에러가 발생하면 에러 코드를 전송
			resp.sendError(400);
		
		
		
		}
		
	
	private void writeSal(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		   //연봉정보수정
	    	EmployeeDAO dao = new EmployeeDAO();
	        String id = req.getParameter("id");
	   
            EmployeeDTO dto = dao.readEmployee(id);
			   
				req.setAttribute("dto", dto);
				req.setAttribute("title",  "연봉등록수정");
				req.setAttribute("mode", "update");
				
		forward(req, resp, "/WEB-INF/views/salary/sal_write.jsp");
	}
	
	private void writeSalOk(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		PayDAO dao = new PayDAO();
	    HttpSession session = req.getSession();

		String cp = req.getContextPath();
		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/pay/pay_write.do");
			return;
		}

		try {
		
		    SalaryDTO dto = new SalaryDTO();

			dto.setSal_id(req.getParameter("sal_id"));
			dto.setSalary(req.getParameter("salary"));
			dto.setSal_start(req.getParameter("sal_start"));
			dto.setSall_end(req.getParameter("sal_end"));
			dto.setSal_memo(req.getParameter("sal_memo"));
			

			dao.writeSal(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/pay/pay_write.do");
	}
	

	
	private void salList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
     //연봉리스트 가지고 오기 
		  PayDAO dao = new PayDAO();

		
		try {
	
			List<SalaryDTO> slist = dao.SalaryList();
			
		    System.out.println(slist.size());
            
			req.setAttribute("list",slist);
			
		
			forward(req,resp, "/WEB-INF/views/salary/sal_list.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 에러가 발생하면 에러 코드를 전송
		resp.sendError(400);
	

	}
	}
	
	

