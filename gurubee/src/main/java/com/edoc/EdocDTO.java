package com.edoc;

public class EdocDTO {
	// 결재 문서
	
	private String id;
	private String name;
	private String reg;
	private String email;
	private String phone;
	private String tel;
	private String pwd;
	private String dept;
	private String pisition;
	
	private int app_num; // seq
	private String app_doc; // 문서구분
	private String app_date; // 문서작성일자
	private String memo;
	private String doc_form; // 문서폼. CLOB
	private String doc_num; // 문서번호. 재무-20221014_4
	private int temp; // 임시 구분. 0:임시, 1:제출
	
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
	public int getApp_num() {
		return app_num;
	}
	public void setApp_num(int app_num) {
		this.app_num = app_num;
	}
	public String getApp_doc() {
		return app_doc;
	}
	public void setApp_doc(String app_doc) {
		this.app_doc = app_doc;
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
	public String getDoc_form() {
		return doc_form;
	}
	public void setDoc_form(String doc_form) {
		this.doc_form = doc_form;
	}
	public String getDoc_num() {
		return doc_num;
	}
	public void setDoc_num(String doc_num) {
		this.doc_num = doc_num;
	}
	public int getTemp() {
		return temp;
	}
	public void setTemp(int temp) {
		this.temp = temp;
	}

	
	
}
