package com.edoc;

public class EdocEmpDTO {
	// 결재자
	
	private String id;
	private String name;
	private String reg;
	private String email;
	private String phone;
	private String tel;
	private String pwd;
	private String dept;
	private String pisition;
	
	private String apper_num; // 결재자 번호
	private String app_num; // 결재번호. (E_Approval - app_num)
	private int app_result;
	private int app_level;
	private String app_date;
	private String memo;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getReg() {
		return reg;
	}
	public void setReg(String reg) {
		this.reg = reg;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getPisition() {
		return pisition;
	}
	public void setPisition(String pisition) {
		this.pisition = pisition;
	}
	public String getApper_num() {
		return apper_num;
	}
	public void setApper_num(String apper_num) {
		this.apper_num = apper_num;
	}
	public String getApp_num() {
		return app_num;
	}
	public void setApp_num(String app_num) {
		this.app_num = app_num;
	}
	public int getApp_result() {
		return app_result;
	}
	public void setApp_result(int app_result) {
		this.app_result = app_result;
	}
	public int getApp_level() {
		return app_level;
	}
	public void setApp_level(int app_level) {
		this.app_level = app_level;
	}
	public String getApp_date() {
		return app_date;
	}
	public void setApp_date(String app_date) {
		this.app_date = app_date;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	
}
