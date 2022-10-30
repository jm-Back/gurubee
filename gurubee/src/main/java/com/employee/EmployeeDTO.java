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
