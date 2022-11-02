package com.mypage;

public class MypageDTO {

	//마이페이지 근태관리
	private String att_id; // 근태번호
	private String id;
	private String att_start; // 출근시간
	private String att_end; // 퇴근시간
	private String att_ing; // 출근 퇴근 외출
	
	// 마이페이지 연차관리
	// 연차 사용 현황
	private String division; // 발생일
	private int div_off; // 발생연차
	private int off_use; // 사용연차
	private int rem_off; // 잔여연차
	
	// 연차 신청 내역
	private int use_num; // 연차 사용 번호
	
	// 연차/휴가 신청
	private int off_num; // 연차번호
	private String off_type; // 연차구분(연차 반차
	private int off_start; // 연차시작일
	private int off_end; // 연차 종료일
	private String off_reason; // 연차사유
	private String memo; // 메모
	
	// 마이페이지 급여 명세서출력
	// 급여 명세서 조회
	private int sal_no; // 연봉번호

	// 월별급여 명세서 출력
	private String pay_no; // 급여번호
	private int pay_date; // 지급일
	private int payment; // 기본급
	private int meal_pay; // 식대
	private int benefit; // 복리후생비
	private int etc_pay; // 기타지급
	private int bonus; // 상여금
	private int residence_tax; // 주민세
	private int medical_ins; // 의료 보험
	private int employee_ins; // 고용보험
	private int safety_ins; // 산재보험
	private int longterm_ins; // 장기요양보험
	
	// 재직증명서 출력
	private int his_no; // 직급변경번호
	private int date_iss; // 발령일자
	private String reason; // 변경사유
	private String now_working; // 근무상태(재직, 퇴사)
	private int type; // 정규직, 계약직
	private int startdate; // 입사일
	private int enddate; // 퇴사일
	
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
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public int getDiv_off() {
		return div_off;
	}
	public void setDiv_off(int div_off) {
		this.div_off = div_off;
	}
	public int getOff_use() {
		return off_use;
	}
	public void setOff_use(int off_use) {
		this.off_use = off_use;
	}
	public int getRem_off() {
		return rem_off;
	}
	public void setRem_off(int rem_off) {
		this.rem_off = rem_off;
	}
	public int getUse_num() {
		return use_num;
	}
	public void setUse_num(int use_num) {
		this.use_num = use_num;
	}
	public int getOff_num() {
		return off_num;
	}
	public void setOff_num(int off_num) {
		this.off_num = off_num;
	}
	public String getOff_type() {
		return off_type;
	}
	public void setOff_type(String off_type) {
		this.off_type = off_type;
	}
	public int getOff_start() {
		return off_start;
	}
	public void setOff_start(int off_start) {
		this.off_start = off_start;
	}
	public int getOff_end() {
		return off_end;
	}
	public void setOff_end(int off_end) {
		this.off_end = off_end;
	}
	public String getOff_reason() {
		return off_reason;
	}
	public void setOff_reason(String off_reason) {
		this.off_reason = off_reason;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public int getSal_no() {
		return sal_no;
	}
	public void setSal_no(int sal_no) {
		this.sal_no = sal_no;
	}
	public String getPay_no() {
		return pay_no;
	}
	public void setPay_no(String pay_no) {
		this.pay_no = pay_no;
	}
	public int getPay_date() {
		return pay_date;
	}
	public void setPay_date(int pay_date) {
		this.pay_date = pay_date;
	}
	public int getPayment() {
		return payment;
	}
	public void setPayment(int payment) {
		this.payment = payment;
	}
	public int getMeal_pay() {
		return meal_pay;
	}
	public void setMeal_pay(int meal_pay) {
		this.meal_pay = meal_pay;
	}
	public int getBenefit() {
		return benefit;
	}
	public void setBenefit(int benefit) {
		this.benefit = benefit;
	}
	public int getEtc_pay() {
		return etc_pay;
	}
	public void setEtc_pay(int etc_pay) {
		this.etc_pay = etc_pay;
	}
	public int getBonus() {
		return bonus;
	}
	public void setBonus(int bonus) {
		this.bonus = bonus;
	}
	public int getResidence_tax() {
		return residence_tax;
	}
	public void setResidence_tax(int residence_tax) {
		this.residence_tax = residence_tax;
	}
	public int getMedical_ins() {
		return medical_ins;
	}
	public void setMedical_ins(int medical_ins) {
		this.medical_ins = medical_ins;
	}
	public int getEmployee_ins() {
		return employee_ins;
	}
	public void setEmployee_ins(int employee_ins) {
		this.employee_ins = employee_ins;
	}
	public int getSafety_ins() {
		return safety_ins;
	}
	public void setSafety_ins(int safety_ins) {
		this.safety_ins = safety_ins;
	}
	public int getLongterm_ins() {
		return longterm_ins;
	}
	public void setLongterm_ins(int longterm_ins) {
		this.longterm_ins = longterm_ins;
	}
	public int getHis_no() {
		return his_no;
	}
	public void setHis_no(int his_no) {
		this.his_no = his_no;
	}
	public int getDate_iss() {
		return date_iss;
	}
	public void setDate_iss(int date_iss) {
		this.date_iss = date_iss;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getNow_working() {
		return now_working;
	}
	public void setNow_working(String now_working) {
		this.now_working = now_working;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getStartdate() {
		return startdate;
	}
	public void setStartdate(int startdate) {
		this.startdate = startdate;
	}
	public int getEnddate() {
		return enddate;
	}
	public void setEnddate(int enddate) {
		this.enddate = enddate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	

}
