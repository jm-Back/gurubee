<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script>
$(function(){
	showClock();
});
</script>

	<h3><i class=" bi bi-person-badge-fill"></i> 근태관리</h3>
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
				<button class="btn btn-Attendance btn-in" data-att_id="${todayAttendance.att_id}" data-year="${year}" data-month="${month}" style="background-color: aquamarine; color:white;" ${not empty todayAttendance.att_start ? "disabled='disabled'":""}>출근</button>
				<button class="btn btn-Attendance btn-out" data-att_id="${todayAttendance.att_id}" data-year="${year}" data-month="${month}" style="background-color: aquamarine; color:white;" ${empty todayAttendance.att_start or not empty todayAttendance.att_end ? "disabled='disabled'":""}>퇴근</button>
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
				<h3> ${month}월 근태현황</h3>
			</div>
			
			<div class="body-main att-list">
				<table class="table table-hover board-list">
					<thead class="table-light">
						<tr>
							<th class="att_id">번호</th>
							<th class="att_date">날짜</th>
							<th class="att_start">출근시간</th>
							<th class="att_end">퇴근시간</th>
							<th class="att_ing">구분</th>
							<th class="att_ing">비고</th>
						</tr>
					</thead>
					
					<tbody>
						<c:forEach var="dto" items="${list}" varStatus="status">
							<tr>
								<td>${status.count}</td>
								<td>${dto.att_date}</td>
								<td>${dto.att_start}</td>
								<td>${dto.att_end}</td>
								<td>${dto.att_ing}</td>
								<td>&nbsp;</td>
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
