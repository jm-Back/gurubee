package com.employee;

public class EmployeeDTO {
	private String Id; //사번 //
	private String pwd; //패스워드
	private String Name;
	private String reg,reg1,reg2;
	private String phone,phone1,phone2,phone3;// 휴대폰번호
	private String email, email1, email2; 
	private String tel,tel1,tel2,tel3;//내선번호
	private String date_iss; //발령일자
	private String pos_code; //직급코드
	private String pos_name; //직급이름 
	private String dept_name; //부서이름
	private String dep_code; //부서코드
	public String getPos_name() {
		return pos_name;
	}
	public void setPos_name(String pos_name) {
		this.pos_name = pos_name;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	private String now_working; //근무상태 
	private String type; // 고용형태
	private String startdate; //입사일자
	private String enddate; 
	private String division; //구분 입사/퇴사/승진 


	
	//급여정보
	private String pay_date;  //지급날짜 

	private long payment; //기본급 
	private long meal_pay; //식대
	private long benefit; //복리후생비 
	private long etc_pay; //기타지급 
	private long bonus; //상여금
	private long  residence_tax; //주민세 
	private long  medical_ins; //의료보험
	private long employee_ins; //고용보험 
	private long  safety_ins ; //산재보험 
	private long  longterm_ins ; //고용보험
	private long  tot;
	
	//연봉정보 
	
	private String sal_id;
	public String getDep() {
		return dep;
	}
	public void setDep(String dep) {
		this.dep = dep;
	}
	public String getPos() {
		return pos;
	}
	public void setPos(String pos) {
		this.pos = pos;
	}
	public String getSal_no() {
		return sal_no;
	}
	public void setSal_no(String sal_no) {
		this.sal_no = sal_no;
	}
	public String getSal_end() {
		return sal_end;
	}
	public void setSal_end(String sal_end) {
		this.sal_end = sal_end;
	}
	private String dep;
	private String pos;
	private String sal_no; //연봉번호 
	private String salary; //연봉 
	private String sal_start; //연봉시작일 
	private String sal_end; //연봉종료일 
	private String sal_memo; 
	public String getTel2() {
		return tel2;
	}
	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}
	public String getPay_date() {
		return pay_date;
	}
	public void setPay_date(String pay_date) {
		this.pay_date = pay_date;
	}
	public long getPayment() {
		return payment;
	}
	public void setPayment(long payment) {
		this.payment = payment;
	}
	public long getMeal_pay() {
		return meal_pay;
	}
	public void setMeal_pay(long meal_pay) {
		this.meal_pay = meal_pay;
	}
	public long getBenefit() {
		return benefit;
	}
	public void setBenefit(long benefit) {
		this.benefit = benefit;
	}
	public long getEtc_pay() {
		return etc_pay;
	}
	public void setEtc_pay(long etc_pay) {
		this.etc_pay = etc_pay;
	}
	public long getBonus() {
		return bonus;
	}
	public void setBonus(long bonus) {
		this.bonus = bonus;
	}
	public long getResidence_tax() {
		return residence_tax;
	}
	public void setResidence_tax(long residence_tax) {
		this.residence_tax = residence_tax;
	}
	public long getMedical_ins() {
		return medical_ins;
	}
	public void setMedical_ins(long medical_ins) {
		this.medical_ins = medical_ins;
	}
	public long getEmployee_ins() {
		return employee_ins;
	}
	public void setEmployee_ins(long employee_ins) {
		this.employee_ins = employee_ins;
	}
	public long getSafety_ins() {
		return safety_ins;
	}
	public void setSafety_ins(long safety_ins) {
		this.safety_ins = safety_ins;
	}
	public long getLongterm_ins() {
		return longterm_ins;
	}
	public void setLongterm_ins(long longterm_ins) {
		this.longterm_ins = longterm_ins;
	}
	public long getTot() {
		return tot;
	}
	public void setTot(long tot) {
		this.tot = tot;
	}
	public String getSal_id() {
		return sal_id;
	}
	public void setSal_id(String sal_id) {
		this.sal_id = sal_id;
	}
	
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public String getSal_start() {
		return sal_start;
	}
	public void setSal_start(String sal_start) {
		this.sal_start = sal_start;
	}
	
	public String getSal_memo() {
		return sal_memo;
	}
	public void setSal_memo(String sal_memo) {
		this.sal_memo = sal_memo;
	}
	
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getPhone1() {
		return phone1;
	}
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	public String getPhone2() {
		return phone2;
	}
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	public String getPhone3() {
		return phone3;
	}
	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}
	public String getId() {
		return Id;
	}
	public String getDate_iss() {
		return date_iss;
	}
	public void setDate_iss(String date_iss) {
		this.date_iss = date_iss;
	}
	public String getNow_working() {
		return now_working;
	}
	public void setNow_working(String now_working) {
		this.now_working = now_working;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getReg() {
		return reg;
	}
	public void setReg(String reg) {
		this.reg = reg;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail1() {
		return email1;
	}
	public void setEmail1(String email1) {
		this.email1 = email1;
	}
	public String getEmail2() {
		return email2;
	}
	public void setEmail2(String email2) {
		this.email2 = email2;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}

	
	public String getPos_code() {
		return pos_code;
	}
	public void setPos_code(String pos_code) {
		this.pos_code = pos_code;
	}
	public String getDep_code() {
		return dep_code;
	}
	public void setDep_code(String dep_code) {
		this.dep_code = dep_code;
	}
	public String getReg1() {
		return reg1;
	}
	public void setReg1(String reg1) {
		this.reg1 = reg1;
	}
	public String getReg2() {
		return reg2;
	}
	public void setReg2(String reg2) {
		this.reg2 = reg2;
	}
	public String getTel3() {
		return tel3;
	}
	public void setTel3(String tel3) {
		this.tel3 = tel3;
	}
	public String getTel1() {
		return tel1;
	}
	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}
	public String getTe2() {
		return tel2;
	}
	public void setTe2(String te2) {
		this.tel2 = te2;
	}


	
	

}
