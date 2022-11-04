package com.pay;

public class PayDTO {
	
private String pay_id; //급여받는 사람 사번 
private String name; //급여받는사람 이름 
private String pos; //직급 
private String dep; //부서 
private String pay_no; //급여번호
private String pay_date;  //지급날짜 

private long payment; //기본급 
private long meal_pay; //식대
private long benefit; //복리후생비 
private long etc_pay; //기타지급 
private long bonus; //상여금
private long  residence_tax; //주민세 
private long  medical_ins; //의료보험
private long employee_ins; //고용보험 
private long  safety_ins ; //산재보험 
private long  longterm_ins ; //고용보험
private long  tot;
public String getPay_id() {
	return pay_id;
}
public void setPay_id(String pay_id) {
	this.pay_id = pay_id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getPos() {
	return pos;
}
public void setPos(String pos) {
	this.pos = pos;
}
public String getDep() {
	return dep;
}
public void setDep(String dep) {
	this.dep = dep;
}
public String getPay_no() {
	return pay_no;
}
public void setPay_no(String pay_no) {
	this.pay_no = pay_no;
}
public String getPay_date() {
	return pay_date;
}
public void setPay_date(String pay_date) {
	this.pay_date = pay_date;
}
public long getPayment() {
	return payment;
}
public void setPayment(long payment) {
	this.payment = payment;
}
public long getMeal_pay() {
	return meal_pay;
}
public void setMeal_pay(long meal_pay) {
	this.meal_pay = meal_pay;
}
public long getBenefit() {
	return benefit;
}
public void setBenefit(long benefit) {
	this.benefit = benefit;
}
public long getEtc_pay() {
	return etc_pay;
}
public void setEtc_pay(long etc_pay) {
	this.etc_pay = etc_pay;
}
public long getBonus() {
	return bonus;
}
public void setBonus(long bonus) {
	this.bonus = bonus;
}
public long getResidence_tax() {
	return residence_tax;
}
public void setResidence_tax(long residence_tax) {
	this.residence_tax = residence_tax;
}
public long getMedical_ins() {
	return medical_ins;
}
public void setMedical_ins(long medical_ins) {
	this.medical_ins = medical_ins;
}
public long getEmployee_ins() {
	return employee_ins;
}
public void setEmployee_ins(long employee_ins) {
	this.employee_ins = employee_ins;
}
public long getSafety_ins() {
	return safety_ins;
}
public void setSafety_ins(long safety_ins) {
	this.safety_ins = safety_ins;
}
public long getLongterm_ins() {
	return longterm_ins;
}
public void setLongterm_ins(long longterm_ins) {
	this.longterm_ins = longterm_ins;
}
public long getTot() {
	return tot;
}
public void setTot(long tot) {
	this.tot = tot;
} //총액 

}
