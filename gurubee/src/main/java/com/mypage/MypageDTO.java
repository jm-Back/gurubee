package com.mypage;

public class MypageDTO {

	//마이페이지 근태관리
	private String att_id; // 근태번호
	private String id;
	private String att_start; // 출근시간
	private String att_end; // 퇴근시간
	private String att_ing; // 출근 퇴근 외출
	
	public String getAtt_id() {
		return att_id;
	}
	public void setAtt_id(String att_id) {
		this.att_id = att_id;
	}
	public String getAtt_start() {
		return att_start;
	}
	public void setAtt_start(String att_start) {
		this.att_start = att_start;
	}
	public String getAtt_end() {
		return att_end;
	}
	public void setAtt_end(String att_end) {
		this.att_end = att_end;
	}
	public String getAtt_ing() {
		return att_ing;
	}
	public void setAtt_ing(String att_ing) {
		this.att_ing = att_ing;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
