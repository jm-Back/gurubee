<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="container">
<div class="row mt-4">
	<div class="col pb-3">
		<div class="row size__">
			<div class="col fw-bold shadow pb-2 pt-2 title">
				<div ><i class="bi bi-calendar2-date"></i> ${year}년 ${month}월 ${day}일 일정</div>
			</div>
		</div>
		
	</div>
</div>
<div class="row container__2 shadow">
	<div class=" container__list" >
		<span class="col-1 list__title">일정분류</span>
		<span class="col-md-2 list__title" >제목</span>
		<span class="col-md-4 list__title" >내용</span>
		<span class="col-md-2 list__title" >기간</span>
		<span class="col-md-1 list__title" >반복</span>
		<span class="col-md-1 list__title" >수정&nbsp;|&nbsp;삭제&nbsp;</span>
	</div>
	<div class="container__list" >
	<input type="hidden" value="${dto.sch_num}" name="sch_num">
	<input type="hidden" value="${dto.sc_code}" name="sc_code">
	<input type="hidden" value="${dto.sch_sdate}" name="sch_sdate">
	<input type="hidden" value="${dto.sch_edate}" name="sch_edate">
	<input type="hidden" value="${dto.sch_stime}" name="sch_stime">
	<input type="hidden" value="${dto.sch_etime}" name="sch_etime">
	<input type="hidden" value="${dto.sch_repeat_c}" name="sch_repeat_c">
	<input type="hidden" name="allDay" value="${empty dto.sch_stime?'1':'0'}">
	
		<input class="col-1 ml-1 list__item1 center_item" type="text" readonly="readonly" value="${dto.sc_type}" name="sch_type">
		<input class="col-md-2 list__item2 center_item" type="text" readonly="readonly" value="${dto.sch_name}" name="sch_name">
		<input class="col-md-4 list__item3 center_item" readonly="readonly" value="${dto.sch_content}" name="sch_content">
		<input class="col-md-2 list__item4 center_item" type="text" readonly="readonly" value="${dto.period}" name="period">
		<input class="col-md-1 list__item5 center_item" type="text" readonly="readonly" value="${dto.sch_repeat > 0 ? 'O' : 'X'}" name="sch_repeat">
		<span class="col-md-1 list__item6" >&nbsp;&nbsp;&nbsp;<i class="fa-solid fa-pencil" data-date="${date}" data-num="${dto.sch_num}" ></i>&nbsp;&nbsp;&nbsp;<i class="fa-solid fa-trash" data-date="${date}" data-num="${dto.sch_num}"></i></span>
	</div>
	
	
</div>
</div>
