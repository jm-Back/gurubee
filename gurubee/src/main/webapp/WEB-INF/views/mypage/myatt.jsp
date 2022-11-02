<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script>
function showClock() {
	let now = new Date();
	
	/*
	let y = now.getFullYear();
	let m = now.getMonth() + 1;
	let d = now.getDate();
	if(m < 10) m = "0" + m;
	if(d < 10) d = "0" + d;
	*/
	
	let hr = now.getHours();
	let mn = now.getMinutes();
	let sc = now.getSeconds();
	if(hr < 10) hr = "0" + hr;
	if(mn < 10) mn = "0" + mn;
	if(sc < 10) sc = "0" + sc;
	
	let s = hr + ":" + mn + ":" + sc;
	
	document.querySelector('.currentTime').innerHTML = s;
	
	setTimeout("showClock()", 1000);
}

$(function(){
	showClock();
});

$(function(){
	$(".btn-Attendance").click(function(){
		
	});
});
</script>

<div class="row mt-4 mb-3">
	<div class="col text-start">
		&nbsp;
	</div>
	<div class="col fs-5 fw-bold text-center">
		<span class="btn" onclick="changeMonth(${year}, ${month-1});"><i class="bi bi-chevron-left"></i></span>
		<span class="text-dark align-middle">${year}年 ${month}月</span>
		<span class="btn" onclick="changeMonth(${year}, ${month+1});"><i class="bi bi-chevron-right"></i></span>
	</div>
	<div class="col fs-6 text-end">
		<span class="btn btn-sm" onclick="changeMonth(${todayYear}, ${todayMonth});">오늘</span>
	</div>
</div>

<div class="row border rounded bg-light">
	<div class="col fs-5 p-3">
		${todayYear}년 ${todayMonth}월 ${todayDate}일
		<span class="currentTime"></span>
	</div>
</div>

<div class="row border rounded mt-2 p-3">
	<div class="col">
		<div class="row">
			<div class="col">
				<button class="btn btn-primary btn-Attendance" ${not empty todayAttendance.att_start ? "disabled='disabled'":""}>출근</button>
				<button class="btn btn-primary btn-Attendance" ${empty todayAttendance.att_start or not empty todayAttendance.att_end ? "disabled='disabled'":""}>퇴근</button>
			</div>
		</div>		
	</div>
	<div class="col">
		<div class="row">
			<div class="col-6"></div>		
			<div class="col">
				<label>출근시간 : ${todayAttendance.att_start}</label>
			</div>
		</div>	
		<div class="row mt-1">
			<div class="col-6"></div>
			<div class="col">
				<label>퇴근시간 : ${todayAttendance.att_end}</label>
			</div>
		</div>			
	</div>
</div>

<div>


</div>
