package com.project;

public class ProjectDTO {
	
	//프로젝트
	private String pro_code;
	private String id_p;
	private String name_p;
	
	private String date_iss;
	public String getDate_iss() {
		return date_iss;
	}
	public void setDate_iss(String date_iss) {
		this.date_iss = date_iss;
	}

	private String dep_name;
	private String pos_name;
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
	
	private String pro_name;
	private String pro_clear;
	private String pro_type;
	private String pro_master;
	private String pro_outline;
	private String pro_content;
	private String pro_sdate;
	private String pro_edate;
	
	//프로젝트 명단 메일, 폰, 부서번호
	private String pro_mail;
	private String pro_phone;
	private String pro_tel;
	private String pd_num;
	private String pd_writer;
	
	public String getPd_writer() {
		return pd_writer;
	}
	public void setPd_writer(String pd_writer) {
		this.pd_writer = pd_writer;
	}
	public String getPd_num() {
		return pd_num;
	}
	public void setPd_num(String pd_num) {
		this.pd_num = pd_num;
	}
	public String getPro_mail() {
		return pro_mail;
	}
	public void setPro_mail(String pro_mail) {
		this.pro_mail = pro_mail;
	}
	public String getPro_phone() {
		return pro_phone;
	}
	public void setPro_phone(String pro_phone) {
		this.pro_phone = pro_phone;
	}
	public String getPro_tel() {
		return pro_tel;
	}
	public void setPro_tel(String pro_tel) {
		this.pro_tel = pro_tel;
	}

	//프로젝트 상세
	private String pd_code;
	private int pd_rank;
	private String pd_subject;
	private String pd_content;
	private int pd_part;
	private int pd_ing;
	private String pd_sdate;
	private String pd_edate;
	
	//프로젝트 작성자
	private String pro_writer;
	
	public String getPro_writer() {
		return pro_writer;
	}
	public void setPro_writer(String pro_writer) {
		this.pro_writer = pro_writer;
	}
	public String getPj_name() {
		return pj_name;
	}
	public void setPj_name(String pj_name) {
		this.pj_name = pj_name;
	}

	//프로젝트 참여자
	private String pj_code;
	private String pj_id;
	private String pj_name;
	
	public String getPj_id() {
		return pj_id;
	}
	public void setPj_id(String pj_id) {
		this.pj_id = pj_id;
	}

	private String pj_role;
	
	//사진! 프로젝트 프로필
	private String pro_profile;
	
	
	
	public String getPro_profile() {
		return pro_profile;
	}
	public void setPro_profile(String pro_profile) {
		this.pro_profile = pro_profile;
	}
	public String getPro_code() {
		return pro_code;
	}
	public void setPro_code(String pro_code) {
		this.pro_code = pro_code;
	}

	public String getId_p() {
		return id_p;
	}
	public void setId_p(String id_p) {
		this.id_p = id_p;
	}
	public String getName_p() {
		return name_p;
	}
	public void setName_p(String name_p) {
		this.name_p = name_p;
	}
	public String getPro_name() {
		return pro_name;
	}
	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}
	public String getPro_clear() {
		return pro_clear;
	}
	public void setPro_clear(String pro_clear) {
		this.pro_clear = pro_clear;
	}
	public String getPro_type() {
		return pro_type;
	}
	public void setPro_type(String pro_type) {
		this.pro_type = pro_type;
	}
	public String getPro_master() {
		return pro_master;
	}
	public void setPro_master(String pro_master) {
		this.pro_master = pro_master;
	}
	public String getPro_outline() {
		return pro_outline;
	}
	public void setPro_outline(String pro_outline) {
		this.pro_outline = pro_outline;
	}
	public String getPro_content() {
		return pro_content;
	}
	public void setPro_content(String pro_content) {
		this.pro_content = pro_content;
	}
	public String getPro_sdate() {
		return pro_sdate;
	}
	public void setPro_sdate(String pro_sdate) {
		this.pro_sdate = pro_sdate;
	}
	public String getPro_edate() {
		return pro_edate;
	}
	public void setPro_edate(String pro_edate) {
		this.pro_edate = pro_edate;
	}
	public String getPd_code() {
		return pd_code;
	}
	public void setPd_code(String pd_code) {
		this.pd_code = pd_code;
	}
	public int getPd_rank() {
		return pd_rank;
	}
	public void setPd_rank(int pd_rank) {
		this.pd_rank = pd_rank;
	}
	public String getPd_subject() {
		return pd_subject;
	}
	public void setPd_subject(String pd_subject) {
		this.pd_subject = pd_subject;
	}
	public String getPd_content() {
		return pd_content;
	}
	public void setPd_content(String pd_content) {
		this.pd_content = pd_content;
	}
	public int getPd_part() {
		return pd_part;
	}
	public void setPd_part(int pd_part) {
		this.pd_part = pd_part;
	}
	public int getPd_ing() {
		return pd_ing;
	}
	public void setPd_ing(int pd_ing) {
		this.pd_ing = pd_ing;
	}
	public String getPd_sdate() {
		return pd_sdate;
	}
	public void setPd_sdate(String pd_sdate) {
		this.pd_sdate = pd_sdate;
	}
	public String getPd_edate() {
		return pd_edate;
	}
	public void setPd_edate(String pd_edate) {
		this.pd_edate = pd_edate;
	}
	public String getPj_code() {
		return pj_code;
	}
	public void setPj_code(String pj_code) {
		this.pj_code = pj_code;
	}
	public String getPj_role() {
		return pj_role;
	}
	public void setPj_role(String pj_role) {
		this.pj_role = pj_role;
	}
	
	
	
	

}
