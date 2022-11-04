package com.login;

public class SessionInfo {
	private String id; // 사번
	private String name; // 이름
	private String reg;
	private String email;
	private String phone;
	private String tel;
	private String pwd;
	private String ori_filename; // 프로필 원본파일이름(사진)
	private String save_filename; // 프로필 저장파일이름(사진)
	
	private String dep_name; // 부서명
	private String pos_name; // 직급명
	private String dep_code;
	
	
	public String getDep_code() {
		return dep_code;
	}
	public void setDep_code(String dep_code) {
		this.dep_code = dep_code;
	}
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
	public String getOri_filename() {
		return ori_filename;
	}
	public void setOri_filename(String ori_filename) {
		this.ori_filename = ori_filename;
	}
	public String getSave_filename() {
		return save_filename;
	}
	public void setSave_filename(String save_filename) {
		this.save_filename = save_filename;
	}
	public String getDep_name() {
		return dep_name;
	}
	public void setDep_name(String dep_name) {
		this.dep_name = dep_name;
	}
	public String getPos_name() {
		return pos_name;
	}
	public void setPos_name(String pos_name) {
		this.pos_name = pos_name;
	}
	
	
	
	
	
}
