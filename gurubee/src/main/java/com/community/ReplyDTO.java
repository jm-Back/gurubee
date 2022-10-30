package com.community;

public class ReplyDTO {
	
	private long replyNum;  	 // 댓글 번호
	private long com_num; 	 	 // 커뮤니티 번호
	private String reply_id; 	 // 댓글 작성자 아이디
	private String reply_name;   // 댓글 작성자 이름
	private String rep_contents; // 댓글 내용
	private String rep_regdate;  // 댓글 작성 날짜
	private long answer; 		 // 대댓글 번호
	private int answerCount; 	 // 대댓글 갯수
	
	private int replyLike;
	private int likeCount;		 // 댓글 좋아요 갯수
	private int disLikeCount;	 // 댓글 싫어요 갯수
	
	
	
	public int getReplyLike() {
		return replyLike;
	}
	public void setReplyLike(int replyLike) {
		this.replyLike = replyLike;
	}
	public int getLikeCount() {
		return likeCount;
	}
	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}
	public int getDisLikeCount() {
		return disLikeCount;
	}
	public void setDisLikeCount(int disLikeCount) {
		this.disLikeCount = disLikeCount;
	}
	public long getReplyNum() {
		return replyNum;
	}
	public void setReplyNum(long replyNum) {
		this.replyNum = replyNum;
	}
	public long getCom_num() {
		return com_num;
	}
	public void setCom_num(long com_num) {
		this.com_num = com_num;
	}
	public String getReply_id() {
		return reply_id;
	}
	public void setReply_id(String reply_id) {
		this.reply_id = reply_id;
	}
	public String getReply_name() {
		return reply_name;
	}
	public void setReply_name(String reply_name) {
		this.reply_name = reply_name;
	}
	public String getRep_contents() {
		return rep_contents;
	}
	public void setRep_contents(String rep_contents) {
		this.rep_contents = rep_contents;
	}
	public String getRep_regdate() {
		return rep_regdate;
	}
	public void setRep_regdate(String rep_regdate) {
		this.rep_regdate = rep_regdate;
	}
	public long getAnswer() {
		return answer;
	}
	public void setAnswer(long answer) {
		this.answer = answer;
	}
	public int getAnswerCount() {
		return answerCount;
	}
	public void setAnswerCount(int answerCount) {
		this.answerCount = answerCount;
	}
	
	
	
	
	
}
