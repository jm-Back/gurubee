package com.pay;

public class SalaryDTO {
    
	
	public String getSal_id() {
		return sal_id;
	}
	public void setSal_id(String sal_id) {
		this.sal_id = sal_id;
	}
	public String getSal_name() {
		return sal_name;
	}
	public void setSal_name(String sal_name) {
		this.sal_name = sal_name;
	}
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
	private String sal_id;
	private String sal_name;
	private String dep;
	private String pos;
	private String sal_no; //연봉번호 
	private String salary; //연봉 
	private String sal_start; //연봉시작일 
	private String sal_end; //연봉종료일 
	private String sal_memo; 
	public String getSal_no() {
		return sal_no;
	}
	public void setSal_no(String sal_no) {
		this.sal_no = sal_no;
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
	public String getSall_end() {
		return sal_end;
	}
	public void setSall_end(String sall_end) {
		this.sal_end = sall_end;
	}
	public String getSal_memo() {
		return sal_memo;
	}
	public void setSal_memo(String sal_memo) {
		this.sal_memo = sal_memo;
	}
}

