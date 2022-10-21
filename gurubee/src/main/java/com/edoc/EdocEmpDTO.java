package com.edoc;

public class EdocEmpDTO {
	// 결재자
	
	private String id_apper; // 결재자 사번
	private String pos_code; // 직급코드
	
	private String apper_num; // 결재자 번호
	private String app_num; // 결재번호. (E_Approval - app_num)
	private int app_result;
	private int app_level;
	private String app_date;
	private String memo;
	
	public String getId_apper() {
		return id_apper;
	}
	public void setId_apper(String id_apper) {
		this.id_apper = id_apper;
	}
	public String getPos_code() {
		return pos_code;
	}
	public void setPos_code(String pos_code) {
		this.pos_code = pos_code;
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
