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

	<h3><i class=" bi bi-person-badge-fill"></i> | 근태관리</h3>
	<hr class="container mb-2 pt-3" style="width : 95%">
<div class="row mt-4 mb-2">
	<div class="col text-start">
		&nbsp;
	</div>
	<div class="col fs-5 fw-bold text-center">
		<span class="btn" onclick="changeMonth(${year}, ${month-1});"><i class="bi bi-chevron-left"></i></span>
		<span class="text-dark align-middle">${year}年 ${month}月</span>
		<span class="btn" onclick="changeMonth(${year}, ${month+1});"><i class="bi bi-chevron-right"></i></span>
	</div>
	<div class="col fs-6 text-end">
		<span class="btn btn-sm" onclick="changeMonth(${todayYear}, ${todayMonth});">이번달</span>
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
				<button class="btn" style="background-color: aquamarine; color:white;" ${not empty todayAttendance.att_start ? "disabled='disabled'":""}>출근</button>
				<button class="btn" style="background-color: aquamarine; color:white;" ${empty todayAttendance.att_start or not empty todayAttendance.att_end ? "disabled='disabled'":""}>퇴근</button>
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

<div class="container">
		<div class="body-container">	
			<div class="body-title">
				<h3><i class="bi bi-calendar4"></i> | 월별근태현황</h3>
			</div>
			
			<div class="body-main">
				
				<table class="table table-hover board-list">
					<thead class="table-light">
						<tr>
							<th class="att_id">근태번호</th>
							<th class="date">날짜</th>
							<th class="att_start">출근</th>
							<th class="att_end">퇴근</th>
							<th class="att_ing">비고</th>
						</tr>
					</thead>
					
					<tbody>
						<c:forEach var="dto" items="${list}" varStatus="status">
							<tr>
								<td>${dataCount - (page-1) * size - status.index}</td>
								<td class="left">
									<c:forEach var="n" begin="1" end="${dto.depth }">&nbsp;&nbsp;</c:forEach>
									<c:if test="${dto.depth!=0}">└&nbsp;</c:if>
									<a href="${articleUrl}&boardNum=${dto.att_id}" class="text-reset">${dto.subject}</a>
								</td>
								<td>${dto.userName}</td>
								<td>${dto.reg_date}</td>
								<td>${dto.hitCount}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				
				<div class="page-navigation">
					${dataCount == 0 ? "근태기록이 없습니다." : paging}
				</div>


			</div>
		</div>
	</div>
