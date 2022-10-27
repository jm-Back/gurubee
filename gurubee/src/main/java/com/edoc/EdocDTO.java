package com.edoc;

public class EdocDTO {
	// 결재 문서
	
	private String id_write; // 작성자 사번
	private int app_num; // seq
	private String app_doc; // 문서구분
	private String app_date; // 문서작성일자
	private String memo;
	private String doc_form; // 문서폼. CLOB
	private String title; // 제목
	private int temp; // 임시 구분. 0:임시, 1:제출
	private int result; // 최종 처리결과. 0:진행중, 1:승인, -1:반려

	public String getId_write() {
		return id_write;
	}
	public void setId_write(String id_write) {
		this.id_write = id_write;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getTemp() {
		return temp;
	}
	public void setTemp(int temp) {
		this.temp = temp;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	
	
	
}
