package com.schedule;

public class ScheduleDTO {
	
	private String sch_num;
	private String sch_id; //작성자 
	private String sc_code;
	private String sch_name;
	private String sch_content;
	private String sch_sdate;
	private String sch_edate;
	private String sch_stime;
	private String sch_etime;
	private int sch_repeat;
	private int sch_repeat_c;		//반복 주기
	private String sch_reg_date; //등록일
	
	public String getSch_num() {
		return sch_num;
	}
	public void setSch_num(String sch_num) {
		this.sch_num = sch_num;
	}
	public String getSch_id() {
		return sch_id;
	}
	public void setSch_id(String sch_id) {
		this.sch_id = sch_id;
	}
	public String getSc_code() {
		return sc_code;
	}
	public void setSc_code(String sc_code) {
		this.sc_code = sc_code;
	}
	public String getSch_name() {
		return sch_name;
	}
	public void setSch_name(String sch_name) {
		this.sch_name = sch_name;
	}
	public String getSch_content() {
		return sch_content;
	}
	public void setSch_content(String sch_content) {
		this.sch_content = sch_content;
	}
	public String getSch_sdate() {
		return sch_sdate;
	}
	public void setSch_sdate(String sch_sdate) {
		this.sch_sdate = sch_sdate;
	}
	public String getSch_edate() {
		return sch_edate;
	}
	public void setSch_edate(String sch_edate) {
		this.sch_edate = sch_edate;
	}
	public String getSch_stime() {
		return sch_stime;
	}
	public void setSch_stime(String sch_stime) {
		this.sch_stime = sch_stime;
	}
	public String getSch_etime() {
		return sch_etime;
	}
	public void setSch_etime(String sch_etime) {
		this.sch_etime = sch_etime;
	}
	public int getSch_repeat() {
		return sch_repeat;
	}
	public void setSch_repeat(int sch_repeat) {
		this.sch_repeat = sch_repeat;
	}
	public int getSch_repeat_c() {
		return sch_repeat_c;
	}
	public void setSch_repeat_c(int sch_repeat_c) {
		this.sch_repeat_c = sch_repeat_c;
	}
	public String getSch_reg_date() {
		return sch_reg_date;
	}
	public void setSch_reg_date(String sch_reg_date) {
		this.sch_reg_date = sch_reg_date;
	}
	

}
