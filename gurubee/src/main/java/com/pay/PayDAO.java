package com.pay;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.edoc.EdocEmpDTO;
import com.employee.EmployeeDTO;
import com.schedule.ScheduleDTO;
import com.util.DBConn;

public class PayDAO {
	
	private Connection conn = DBConn.getConnection();
	
	//급여 지급
	
	public void writePay(PayDTO dto) throws SQLException {
	PreparedStatement pstmt =null;
	String sql;
	
	try {

		conn.setAutoCommit(false);
		sql = "INSERT INTO PAY (pay_no, id, payment, meal_pay, benefit, etc_pay, bonus,residence_tax,medical_ins,employee_ins,safety_ins,longterm_ins,pay_date) values (Pay_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		pstmt = conn.prepareStatement(sql);
		
		
		pstmt.setString(1, dto.getPay_id());
		pstmt.setLong(2, dto.getPayment());
		pstmt.setLong(3, dto.getMeal_pay());
		pstmt.setLong(4, dto.getBenefit());
		pstmt.setLong(5, dto.getEtc_pay());
		pstmt.setLong(6, dto.getBonus());
		pstmt.setLong(7, dto.getResidence_tax());
		pstmt.setLong(8, dto.getMedical_ins());
		pstmt.setLong(9, dto.getEmployee_ins());
		pstmt.setLong(10,dto.getSafety_ins());
		pstmt.setLong(11,dto.getLongterm_ins());
		pstmt.setString(12, dto.getPay_date());
		
		pstmt.executeUpdate();
		
		conn.commit();
		
		
	} catch (SQLException e) {
		try {
			conn.rollback();
		} catch (SQLException e2) {
		}
		e.printStackTrace();
		throw e;
	} finally {
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
			}
		}
		
			
	}
	
	}
	
	//급여 수정 
	
	public void updatePay(PayDTO dto) throws SQLException { 
		PreparedStatement pstmt =null;
		String sql;
		
	    try {
			sql = "UPDATE PAY SET payment=?, meal_pay=?,benefit=?,etc_pay=?, bonus=?, residence_tax=?, medical_ins=?,employee_ins=?,safety_ins=?,longterm_ins=?,pay_date =? WHERE Id=?";
			pstmt = conn.prepareStatement(sql);
					
			pstmt.setLong(1, dto.getPayment());
			pstmt.setLong(2, dto.getMeal_pay());
			pstmt.setLong(3, dto.getBenefit());
			pstmt.setLong(4, dto.getEtc_pay());
			pstmt.setLong(5, dto.getBonus());
			pstmt.setLong(6, dto.getResidence_tax());
			pstmt.setLong(7, dto.getMedical_ins());
			pstmt.setLong(8, dto.getEmployee_ins());
			pstmt.setLong(9, dto.getSafety_ins());
			pstmt.setLong(10, dto.getLongterm_ins());
			pstmt.setString(11,dto.getPay_date());
			pstmt.setString(12, dto.getPay_id());
	
			pstmt.executeUpdate();

	    } catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}

	}
	
	//전체급여리스트 
	
	public List<PayDTO> payListemp(String id) {
		List<PayDTO> findList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		long tot;
		
		try {

			     sql = " SELECT e.id, e.name, d.dep_name, p.pos_name, pay_Date,"
							+ " NVL(payment,0)payment, NVL(meal_pay,0)meal_pay, NVL(benefit,0)benefit,NVL(etc_pay,0)etc_pay, "
							+ " NVL(bonus,0)bonus,NVL(medical_ins,0)medical_ins, NVL(employee_ins,0)employee_ins,"
							+ " NVL(safety_ins ,0) safety_ins , NVL(longterm_ins,0)longterm_ins, NVL(residence_tax,0)residence_tax"
							+ " FROM employee_history his JOIN Employee e ON e.id = his.id JOIN department d ON d.dep_code = his.dep_code"
							+ " JOIN position p ON p.pos_code = his.pos_code RIGHT OUTER JOIN Pay pay ON pay.id = his.id"
							+ " JOIN department d ON d.depcode = his.depcode"
							+ " now_working ='재직'";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				PayDTO paydto = new PayDTO();
				paydto.setPay_id(rs.getString("p_id"));
				paydto.setName(rs.getString("name"));
				paydto.setDep(rs.getString("dep"));
				paydto.setPos(rs.getString("pos"));
			
				paydto.setPayment(rs.getLong("payment"));
				paydto.setMeal_pay(rs.getLong("meal_pay"));
			    paydto.setBenefit(rs.getLong("benefit"));
				paydto.setEtc_pay(rs.getLong("etc_pay"));
				paydto.setBonus(rs.getLong("bonus"));
				paydto.setResidence_tax(rs.getLong("residence_tax"));
				paydto.setMedical_ins(rs.getLong("medical_ins"));
				paydto.setEmployee_ins(rs.getLong("employee_ins"));
				paydto.setSafety_ins(rs.getLong("safety_ins"));
				paydto.setLongterm_ins(rs.getLong("longterm_ins"));
				
				paydto.setPay_date(rs.getString("pay_date"));
				
				// 총 지급금액 = 
				// + 기본급,식대,복리후생비,기타지급,상여금, (5)
				// - 주민세,의료보험,고용보험,산재보혐,고용보험 (5)
				tot =  paydto.getPayment()+paydto.getMeal_pay()+paydto.getBenefit()+paydto.getEtc_pay()+paydto.getBonus()
				     - paydto.getResidence_tax() - paydto.getMedical_ins() - paydto.getEmployee_ins() - paydto.getSafety_ins() + paydto.getLongterm_ins() ;
				paydto.setTot(tot);
				
				findList.add(paydto);
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}

		}
		
		return findList;
	}
	
///특정사원 급여출력 
	
	public PayDTO readpayList(String Id) {
		PayDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		long tot;
		
		try {   sql = " SELECT e.id, e.name, d.dep_name, p.pos_name, pay_Date,"
				+ " NVL(payment,0)payment, NVL(meal_pay,0)meal_pay, NVL(benefit,0)benefit,NVL(etc_pay,0)etc_pay, "
				+ " NVL(bonus,0)bonus,NVL(medical_ins,0)medical_ins, NVL(employee_ins,0)employee_ins,"
				+ " NVL(safety_ins ,0) safety_ins , NVL(longterm_ins,0)longterm_ins, NVL(residence_tax,0)residence_tax"
				+ " FROM employee_history his JOIN Employee e ON e.id = his.id JOIN department d ON d.dep_code = his.dep_code"
				+ " JOIN position p ON p.pos_code = his.pos_code RIGHT OUTER JOIN Pay ON pay.id = his.id"
				+ " JOIN department d ON d.dep_code = his.dep_code"
				+ " where now_working ='재직' and id = ?  ";
		
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1,Id);
			
			rs = pstmt.executeQuery();

			
			while(rs.next()) {
				PayDTO paydto = new PayDTO();
				paydto.setPay_id(rs.getString("id"));
				paydto.setName(rs.getString("name"));
				paydto.setDep(rs.getString("dep"));
				paydto.setPos(rs.getString("pos"));
			
				paydto.setPayment(rs.getLong("payment"));
				paydto.setMeal_pay(rs.getLong("meal_pay"));
			    paydto.setBenefit(rs.getLong("benefit"));
				paydto.setEtc_pay(rs.getLong("etc_pay"));
				paydto.setBonus(rs.getLong("bonus"));
				paydto.setResidence_tax(rs.getLong("residence_tax"));
				paydto.setMedical_ins(rs.getLong("medical_ins"));
				paydto.setEmployee_ins(rs.getLong("employee_ins"));
				paydto.setSafety_ins(rs.getLong("safety_ins"));
				paydto.setLongterm_ins(rs.getLong("longterm_ins"));
				
				paydto.setPay_date(rs.getString("pay_date"));
				
				// 총 지급금액 = 
				// + 기본급,식대,복리후생비,기타지급,상여금, (5)
				// - 주민세,의료보험,고용보험,산재보혐,고용보험 (5)
				tot =  paydto.getPayment()+paydto.getMeal_pay()+paydto.getBenefit()+paydto.getEtc_pay()+paydto.getBonus()
				     - paydto.getResidence_tax() - paydto.getMedical_ins() - paydto.getEmployee_ins() - paydto.getSafety_ins() + paydto.getLongterm_ins() ;
				paydto.setTot(tot);
				
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
				
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return dto;
	}	

	
 
	//월별 전체 급여 리스트 
	
	public List<PayDTO> monthpaylist(String month) { 
		List<PayDTO> list = new ArrayList<PayDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		long tot;
		
		try {
			
			sql = " SELECT e.id, e.name, d.dep_name, p.pos_name, pay_Date,"
					+ " NVL(payment,0)payment, NVL(meal_pay,0)meal_pay, NVL(benefit,0)benefit,NVL(etc_pay,0)etc_pay,"
					+ " NVL(bonus,0)bonus,NVL(medical_ins,0)medical_ins, NVL(employee_ins,0)employee_ins,"
					+ " NVL(safety_ins ,0) safety_ins , NVL(longterm_ins,0)longterm_ins, NVL(residence_tax,0)residence_tax"
					+ " FROM employee_history his JOIN Employee e ON e.id = his.id JOIN department d ON d.dep_code = his.dep_code"
					+ " JOIN position p ON p.pos_code = his.pos_code RIGHT OUTER JOIN Pay ON pay.id = his.id"
					+ " JOIN department d ON d.dep_code = his.dep_code"
					+ " WHERE TO_CHAR(pay_Date,'YYYY-MM') =? and now_working ='재직' ";
			
			
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, month);
			rs = pstmt.executeQuery();
	
			
			while(rs.next()) {
				PayDTO paydto = new PayDTO();
				
				paydto.setPay_id(rs.getString("p_id"));
				paydto.setName(rs.getString("p_name"));
				paydto.setDep(rs.getString("dep"));
				paydto.setPos(rs.getString("pos"));
				paydto.setPayment(rs.getLong("payment"));
				paydto.setMeal_pay(rs.getLong("meal_pay"));
			    paydto.setBenefit(rs.getLong("benefit"));
				paydto.setEtc_pay(rs.getLong("etc_pay"));
				paydto.setBonus(rs.getLong("bonus"));
				paydto.setResidence_tax(rs.getLong("residence_tax"));
				paydto.setMedical_ins(rs.getLong("medical_ins"));
				paydto.setEmployee_ins(rs.getLong("employee_ins"));
				paydto.setSafety_ins(rs.getLong("safety_ins"));
				paydto.setLongterm_ins(rs.getLong("longterm_ins"));
				
				paydto.setPay_date(rs.getString("pay_date"));
				
				// 총 지급금액 = 
				// + 기본급,식대,복리후생비,기타지급,상여금, (5)
				// - 주민세,의료보험,고용보험,산재보혐,고용보험 (5)
				tot =  paydto.getPayment()+paydto.getMeal_pay()+paydto.getBenefit()+paydto.getEtc_pay()+paydto.getBonus()
				     - paydto.getResidence_tax() - paydto.getMedical_ins() - paydto.getEmployee_ins() - paydto.getSafety_ins() + paydto.getLongterm_ins() ;
				paydto.setTot(tot);
				
				list.add(paydto);
		
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}

		}
		
		return list;
	}
	
	//연봉협상 
	
	public void writeSal(SalaryDTO sdto) throws SQLException {
	    
		PreparedStatement pstmt = null;
		int rs = 0;
		String sql = null;
		
		
		try {	
			// 자동 커밋되지 않도록
			conn.setAutoCommit(false);
			
			// 업데이트 전 마지막 연봉 정보 가져와서 연봉종료일을 업데이트 연봉시작일로.
			sql = "UPDATE salary SET sal_end=? "
					+ "WHERE sal_no = (SELECT MAX(sal_No) FROM salary WHERE id=?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, sdto.getSall_end());
			pstmt.setString(2, sdto.getSal_id());
			
			rs = pstmt.executeUpdate();
			pstmt.close();
			
			
			// 새로운 연봉 정보 추가
			sql = "INSERT INTO Salary(sal_No,id,salary,sal_start,sal_end,sal_memo) VALUES(sal_seq.NEXTVAL,?,?,?,?,?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, sdto.getSal_id());
			pstmt.setString(2, sdto.getSalary());
			pstmt.setString(3, sdto.getSal_start());
			pstmt.setString(4, sdto.getSall_end());
			pstmt.setString(5, sdto.getSal_memo());
	
			
			rs = pstmt.executeUpdate();
			pstmt.close();
			
			// 커밋 
			conn.commit();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
			
			try {
				conn.setAutoCommit(true);
			} catch (Exception e2) {
			}
		}
		
	}

	
	// 전체연봉리스트 
	
	public List<SalaryDTO> SalaryList() {
		List<SalaryDTO> list = new ArrayList<>();
		SalaryDTO dto = new SalaryDTO();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			// 현재 연봉과 현재 직급,부서 가져오기 
			sql = " SELECT name, s.id, pos_name, dep_name, salary, TO_CHAR(sal_start,'YYYY-MM-DD')sal_start,TO_CHAR(sal_end,'YYYY-MM-DD')sal_end,sal_memo "
					+ " FROM(" 
					+ "  SELECT id, ROW_NUMBER() OVER(PARTITION BY id ORDER BY sal_start DESC) as st," 
					+ "  sal_No, salary,sal_start, sal_end, NVL(sal_memo, '-')sal_memo"
					+ " FROM Salary"
					+" ) s "
					+ " LEFT OUTER JOIN (SELECT id, date_iss, dep_code, pos_code," 
					+ " ROW_NUMBER() OVER(PARTITION BY id ORDER BY date_iss DESC) as pd "
					+ " FROM Employee_history"
					+ " )emp_his ON emp_his.id = s.id"
					+ " LEFT OUTER JOIN Employee e ON e.id=s.id "
					+ " LEFT OUTER JOIN position p ON p.pos_code = emp_his.pos_code "
					+ " LEFT OUTER JOIN department d ON d.dep_code = emp_his.dep_code"
					+ " WHERE pd=1 AND st=1 ";
			
					


			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				dto = new SalaryDTO();
				
				dto.setSal_name(rs.getString("name"));
				dto.setSal_id(rs.getString("id"));
				dto.setPos(rs.getString("pos_name"));
				dto.setDep(rs.getString("dep_name"));
				dto.setSalary(rs.getString("salary"));
				dto.setSal_start(rs.getString("sal_start"));
				dto.setSall_end(rs.getString("sal_end"));
				dto.setSal_memo(rs.getString("sal_memo"));
				
			    list.add(dto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
			
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
		}
		return list;
	}

	
	
}
	
	
	



