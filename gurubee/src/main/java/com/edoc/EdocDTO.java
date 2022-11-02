package com.edoc;

public class EdocDTO {
	// 결재 문서
	
	private String id_write; // 작성자 사번
	private String name_write;
	private String dep_write;
	private String pos_write;
	private int posCode_write;
	
	private int app_num; // seq
	private String app_doc; // 문서구분
	private String app_date; // 문서작성일자
	private String memo;
	private String doc_form; // 문서폼. CLOB
	private String title; // 제목
	private int temp; // 임시 구분. 0:임시, 1:제출
	
	private String result; // 현재 처리결과. 0:대기, 1:승인, -1:반려
	private String result_name; // 현재 처리 결재자 이름
	
	private int fileNum;
	private String saveFilename;
	private String originalFilename;
	
	private String[] saveFiles;
	private String[] originalFiles;

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
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getResult_name() {
		return result_name;
	}
	public void setResult_name(String aa) {
		this.result_name = aa;
	}
	public String getName_write() {
		return name_write;
	}
	public void setName_write(String name_write) {
		this.name_write = name_write;
	}
	public String getDep_write() {
		return dep_write;
	}
	public void setDep_write(String dep_write) {
		this.dep_write = dep_write;
	}
	public String getPos_write() {
		return pos_write;
	}
	public void setPos_write(String pos_write) {
		this.pos_write = pos_write;
	}
	public int getPosCode_write() {
		return posCode_write;
	}
	public void setPosCode_write(int posCode_write) {
		this.posCode_write = posCode_write;
	}
	public String[] getSaveFiles() {
		return saveFiles;
	}
	public void setSaveFiles(String[] saveFiles) {
		this.saveFiles = saveFiles;
	}
	public String[] getOriginalFiles() {
		return originalFiles;
	}
	public void setOriginalFiles(String[] originalFiles) {
		this.originalFiles = originalFiles;
	}
	public String getSaveFilename() {
		return saveFilename;
	}
	public void setSaveFilename(String saveFilename) {
		this.saveFilename = saveFilename;
	}
	public String getOriginalFilename() {
		return originalFilename;
	}
	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}
	public int getFileNum() {
		return fileNum;
	}
	public void setFileNum(int fileNum) {
		this.fileNum = fileNum;
	}
	
}
