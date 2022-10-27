<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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

function appResult() {
	alert('appResult 실행');
	let url = "${pageContext.request.contextPath}/edoc/appResult.do";
	let query = "app_num=" + app_num;
	
	const fn = function(data) {
		console.log(data);
	};
	ajaxFun(url, "get", query, "json", fn);
}


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
							<form class="row" name=conditionForm method="post" enctype="multipart/form-data">
								<div class="col-1 p-1">
									<input type="text" class="form-control" id="startDate" name="date" placeholder="날짜">
								</div>
								<div class="col-1 p-1">
									<input type="text" class="form-control" id="endDate" name="date" placeholder="날짜">
								</div>
								<div class="col-1 p-1">
									<select id="appResultSelect" class="form-select">
										<option selected>처리결과</option>
										<option value="0">대기</option>
										<option value="1">승인</option>
										<option value="-1">반려</option>
									</select>
								</div>
								<div class="col-2 p-1" style="width: 200px;">
									<select id="edocSelect" class="form-select">
										<option selected>문서구분</option>
										<option value="휴가신청서">휴가신청서</option>
										<option value="접근권한신청서">DB접근권한신청서</option>
										<option value="구매요청의뢰서">구매요청의뢰서</option>
										<option value="재택근무신청서">재택근무신청서</option>
										<option value="법인카드지출의뢰서">법인카드지출의뢰서</option>
										<option value="출장신청서">출장신청서</option>
									</select>	
								</div>
								<div class="col-1 p-1">
									<button type="button" id="btnSearchEdoc" class="btn btn-success" style="height: 35px;"><i class="bi bi-search"></i></button>	
								</div>
								<input type="hidden" id="page" value=" ">		
							</form>
						</div>
						
						<div class="row">3/2 페이지</div>
					</div>
					
					<form name="listForm" method="post" enctype="multipart/form-data">
						<table class="table table-hover board-list table-bordered table-text">
							<thead class="table-light">
								<tr style="width: 100%">
									<td style="width: 5%">No</td>
									<td style="width: 10%">문서구분</td>
									<td style="width: 10%">결재상태</td>
									<td style="width: 30%">제목</td>
									<td style="width: 10%">작성일</td>
									<td style="width: 10%">처리결과</td>
								</tr>
							</thead>
						
							<tbody>
							
								<c:forEach var="dto" items="${list}" varStatus="status">
									<tr>
										<td>${dto.app_num}</td>
										<td>${dto.app_doc}</td>
										<td>
											<div id="currentResult"></div>
											<c:choose>
												<c:when test="${dto.result == 0}">
													<button class="btn btn-success" disabled style="border-radius: 20px;">진행중</button>
												</c:when>
												<c:when test="${dto.result == -1}">
													<button class="btn btn-success" disabled style="border-radius: 20px;">반려</button>
												</c:when>
												<c:otherwise>
													<button class="btn btn-success" disabled style="border-radius: 20px;">승인</button>
												</c:otherwise>
											</c:choose>
										</td>
										<td>
											<c:if test="${dto.temp == -1}">
												<button class="btn btn-outline-success" disabled style="border-radius: 20px; width: 85px;">수정 이력</button>		
											</c:if>
											<a href="#">${dto.title}</a>
										</td>
										<td>${dto.app_date}</td>
										<td>
											<c:choose>
												<c:when test="${dto.result == 0}">
													<button class="btn btn-success" disabled style="border-radius: 20px;">진행중</button>
												</c:when>
												<c:when test="${dto.result == -1}">
													<button class="btn btn-success" disabled style="border-radius: 20px;">반려</button>
												</c:when>
												<c:otherwise>
													<button class="btn btn-success" disabled style="border-radius: 20px;">승인</button>
												</c:otherwise>
											</c:choose>
										</td>
									</tr>
									<input type="hidden" name="app_num" value="${dto.app_num}">
								</c:forEach>
								
							</tbody>
						</table>
						
						<div style="float: right;">
							<button type="button" id="btnWriteEdoc" class="btn btn-success" style="height: 35px;">문서작성</button>
							<button type="button" id="btnTempEdoc" class="btn btn-success" style="height: 35px;">임시보관함</button>
						</div>
						
					</form>
					<div class="page-navigation">
						
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
