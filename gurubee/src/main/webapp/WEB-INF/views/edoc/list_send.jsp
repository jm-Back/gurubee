<%@page import="java.util.Calendar"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	Calendar cal = Calendar.getInstance();
	int ty = cal.get(Calendar.YEAR);
	int tm = cal.get(Calendar.MONTH) + 1;
	int td = cal.get(Calendar.DATE);
	
	int year = cal.get(Calendar.YEAR);
	int month = cal.get(Calendar.MONTH) + 1;
	
	String sy = request.getParameter("year");
	String sm = request.getParameter("month");
	if(sy != null) {
		year = Integer.parseInt(sy);
	}
	if(sm != null) {
		month = Integer.parseInt(sm);
	}
	
	cal.set(year, month-1, 1);
	year = cal.get(Calendar.YEAR);
	month = cal.get(Calendar.MONTH) + 1;
	
	int week = cal.get(Calendar.DAY_OF_WEEK); // 1(일) ~ 7(토)
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp" />
<link rel="canonical"href="https://getbootstrap.com/docs/5.2/examples/jumbotron/">

<style>

.conditionSet div{
	float: left;
	align-items: center;
	width: 120px;
	padding-right: 10px;
	padding-bottom: 20px;
}

.table-text tr td{
	font-size: 16px;
}

</style>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/board2.css"
	type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/calendar.css"
	type="text/css">	
</head>

<script type="text/javascript">

function ajaxFun(url, method, query, dataType, fn) {
	$.ajax({
		type:method,
		url:url,
		data:query,
		dataType:dataType,
		success:function(data){
			fn(data);
		},
		beforeSend:function(jqXHR) {
			jqXHR.setRequestHeader("AJAX", true);
		},
		error:function(jqXHR) {
			if(jqXHR.status === 403) {
				login();
				return false;
			} else if(jqXHR.status === 400) {
				alert("요청 처리가 실패 했습니다.");
				return false;
			}
			console.log(jqXHR.responseText);
		}
	});
}

function checkValidDate(value) {
	var result = true;
	try {
	    var date = value.split("-");
	    var y = parseInt(date[0], 10),
	        m = parseInt(date[1], 10),
	        d = parseInt(date[2], 10);
	    
	    var dateRegex = /^(?=\d)(?:(?:31(?!.(?:0?[2469]|11))|(?:30|29)(?!.0?2)|29(?=.0?2.(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))(?:\x20|$))|(?:2[0-8]|1\d|0?[1-9]))([-.\/])(?:1[012]|0?[1-9])\1(?:1[6-9]|[2-9]\d)?\d\d(?:(?=\x20\d)\x20|$))?(((0?[1-9]|1[012])(:[0-5]\d){0,2}(\x20[AP]M))|([01]\d|2[0-3])(:[0-5]\d){1,2})?$/;
	    result = dateRegex.test(d+'-'+m+'-'+y);
	} catch (err) {
		result = false;
	}    
    return result;
}

// btnContidion
function conditionSubmit() {
	const f = document.conditionForm;
	
	alert('실행');
	
	let myDate = $("form input[name=myDate]").val();
	let edoc = $("#edocSelect option:selected").attr("data-edoc");

	console.log(myDate, edoc);
	
	if(myDate!=="") {
		if(! checkValidDate(myDate)){
			alert('올바른 날짜를 입력해주세요.');
			return;
		}
	}
	
	f.submit();
}

$(function() {
	/*
	$("form button[name=btnSearchEdoc]").click(function() {
		let f = document.conditionForm;

		let startDate = $("form input[name=startDate]").val();
		let endDate = $("form input[name=endDate]").val();
		let result =  $("#resultSelect option:selected").attr("data-result");
		let edoc = $("#edocSelect option:selected").attr("data-edoc");
		// ! 로 값 존재 확인	
		startDate = encodeURIComponent(startDate);
		endDate = encodeURIComponent(endDate);
		result = encodeURIComponent(result);
		edoc = encodeURIComponent(edoc);

		let url = "${pageContext.request.contextPath}/edoc/send_condition.do"
		
		let query = "startDate="+startDate;
		let query = $(".conditionForm").serialize()
	});
	*/
})

</script>

<body>

	<main>
		<div class="container py-4">
			<header class="pb-3 mb-4 border-bottom">
				<jsp:include page="/WEB-INF/views/layout/header.jsp" />
				<jsp:include page="/WEB-INF/views/layout/sidebar.jsp" />
			</header>
	
			<div class="body-container">
				<div class="body-main">
					<div class="row board-list-header">
						<div class="col-12 text-center">
							<form class="row" id="conditionForm" name="conditionForm" method="post" enctype="multipart/form-data">
								<div class="col-1 p-1">
									<input type="text" class="form-control" id="myDate" name="myDate" placeholder="날짜">
								</div>
								<div class="col-2 p-1" style="width: 200px;">
									<select id="edocSelect" name="edocSelect" class="form-select">
										<option selected>문서구분</option>
										<option value="휴가신청서" data-edoc="휴가신청서">휴가신청서</option>
										<option value="접근권한신청서" data-edoc="접근권한신청서">DB접근권한신청서</option>
										<option value="구매요청의뢰서" data-edoc="구매요청의뢰서">구매요청의뢰서</option>
										<option value="재택근무신청서" data-edoc="재택근무신청서">재택근무신청서</option>
										<option value="법인카드지출의뢰서" data-edoc="법인카드지출의뢰서">법인카드지출의뢰서</option>
										<option value="출장신청서">출장신청서</option>
									</select>	
								</div>
								<div class="col-1 p-1">
									<button type="button" id="btnContidion" onclick="conditionSubmit();" class="btn btn-success" style="height: 35px;"><i class="bi bi-search"></i></button>	
								</div>
								<input type="hidden" id="page" value=" ">		
							</form>
						</div>
						
						<div class="row">${dataCount}개(${page}/${total_page})</div>
					</div>
					
					<form name="listForm" method="post" enctype="multipart/form-data">
						<table class="table table-hover board-list table-bordered table-text">
							<thead class="table-light">
								<tr style="width: 100%">
									<td style="width: 5%">No</td>
									<td style="width: 10%">문서구분</td>
									<td style="width: 10%">결재상태</td>
									<td style="width: 10%">마지막결재자</td>
									<td style="width: 30%">제목</td>
									<td style="width: 10%">작성일</td>
									<td style="width: 10%">처리결과</td>
								</tr>
							</thead>
						
							<tbody>
							
								<c:forEach var="dto" items="${list}" varStatus="status">
									<tr><td>${dto.app_num}</td>
										<td>${dto.app_doc}</td>
										<td>
											<c:if test="${fn:contains(dto.result, '반려')}">
												<button class="btn btn-warning" disabled style="border-radius: 20px;">${dto.result}</button>
											</c:if>
											<c:if test="${fn:contains(dto.result, '승인')}">
												<button class="btn btn-success" disabled style="border-radius: 20px;">${dto.result}</button>
											</c:if>
											<c:if test="${fn:contains(dto.result, '대기')}">
												<button class="btn btn-success" disabled style="border-radius: 20px;">${dto.result}</button>
											</c:if>
										</td>
										<td>${dto.result_name}</td>
										<td>
											<c:if test="${dto.temp == -1}">
												<button class="btn btn-outline-success" disabled style="border-radius: 20px; width: 85px;">수정 이력</button>		
											</c:if>
											<a href="${articleUrl}&app_num=${dto.app_num}">${dto.title}</a>
										</td>
										<td>${dto.app_date}</td>
										<td>
											<c:if test="${fn:contains(dto.result, '반려')}">
												<button class="btn btn-warning" disabled style="border-radius: 20px;">반려</button>
											</c:if>
											<c:if test="${fn:contains(dto.result, '승인')}">
												<button class="btn btn-success" disabled style="border-radius: 20px;">승인</button>
											</c:if>
											<c:if test="${fn:contains(dto.result, '대기')}">
												<button class="btn btn-success" disabled style="border-radius: 20px;">진행중</button>
											</c:if>
										</td>
									</tr>
									<input type="hidden" name="app_num" value="${dto.app_num}">
								</c:forEach>
								
							</tbody>
						</table>
						
						
						<div style="float: right;">
							<a href="${pageContext.request.contextPath}/edoc/write.do">
							<button type="button" id="btnWriteEdoc" class="btn btn-success" style="height: 35px;">문서작성</button></a>
							<button type="button" id="btnTempEdoc" class="btn btn-success" style="height: 35px;">임시보관함</button>
						</div>
						
					</form>
					
						<div class="page-navigation">
							${dataCount == 0 ? "등록된 게시물이 없습니다." : paging}
						</div>
					
					
					<div class="row board-list-footer">
						
					</div>
				</div>
			</div>
		</div>
	</main>

	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp" />
	</footer>

</body>

</html>
