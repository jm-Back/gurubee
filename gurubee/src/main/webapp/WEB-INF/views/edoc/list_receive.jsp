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


</style>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/board2.css"
	type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/calendar.css"
	type="text/css">	


</head>
<body>

	<main>
		<!-- 메인 화면 -->
		<div class="container py-4">
			<header class="pb-3 mb-4 border-bottom">
				<jsp:include page="/WEB-INF/views/layout/header.jsp" />
				<jsp:include page="/WEB-INF/views/layout/sidebar.jsp" />
			</header>
		
		<div class="body-container">		
			<div class="conditionSet">
				<form name="conditionForm" method="post">
						<div>
							<input type="text" class="form-control" id="startDate" name="date" placeholder="날짜">
						</div>
						<div>
							<input type="text" class="form-control" id="endDate" name="date" placeholder="날짜">
						</div>
						<div>
							<select id="appResultSelect" class="form-select">
								<option value="0">대기</option>
								<option value="1">승인</option>
								<option value="-1">반려</option>
							</select>
						</div>
						<div style="width: 200px;">
							<select id="edocSelect" class="form-select">
								<option value="휴가신청서">휴가신청서</option>
								<option value="접근권한신청서">DB접근권한신청서</option>
								<option value="구매요청의뢰서">구매요청의뢰서</option>
								<option value="재택근무신청서">재택근무신청서</option>
								<option value="법인카드지출의뢰서">법인카드지출의뢰서</option>
								<option value="출장신청서">출장신청서</option>
							</select>	
						</div>
						<div>
							<select id="totalResultSelect" class="form-select">
								<option value="휴가신청서">최종승인</option>
								<option value="접근권한신청서">반려</option>
							</select>
						</div> 
						<div>
							<input type="text" class="form-control" id="empSearchId" placeholder="이름 검색">	
						</div>
						<div>
							<button type="button" id="btnSearchEdoc" class="btn btn-secondary" style="height: 35px;">조회</button>	
						</div>
						<input type="hidden" id="page" value="${page}">		
				</form>
			</div>	
		
				<div class="body-main" style="display: block;">	
					<div style="display: block;">
						<div class="col-auto me-auto">${dataCount}개(${page}/${total_page} 페이지)</div>
						<div class="col-auto">&nbsp;</div>
					</div>
					
					<form name="listForm" method="post">	
						<table class="table table-bordered table-hover">
							<thead>
								<tr>
									<th>No</th>
									<th>문서구분</th>
									<th>결재상태</th>
									<th>제목</th>
									<th>작성일</th>
									<th>결재자</th>
									<th>결재일</th>
									<th>처리결과</th>
								</tr>
							</thead>
							
							<tbody>
								<tr style="width: 100%">
									<td style="width: 5%">No</td>
									<td style="width: 10%">문서구분</td>
									<td style="width: 7%">결재상태</td>
									<td style="width: 30%">제목</td>
									<td style="width: 5%">작성일</td>
									<td style="width: 5%">결재자</td>
									<td style="width: 10%">결재일</td>
									<td style="width: 10%">처리결과</td>
								</tr>
								<tr>
									<td>No</td>
									<td>문서구분</td>
									<td>결재상태</td>
									<td>제목</td>
									<td>작성일</td>
									<td>결재자</td>
									<td>결재일</td>
									<td>처리결과</td>
								</tr>
								<tr>
									<td>No</td>
									<td>문서구분</td>
									<td>결재상태</td>
									<td>제목</td>
									<td>작성일</td>
									<td>결재자</td>
									<td>결재일</td>
									<td>처리결과</td>
								</tr>
							</tbody>
							
						</table>
					</form>
				</div>
			</div>
		</div>
	</main>

	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp" />
	</footer>

</body>

</html>
