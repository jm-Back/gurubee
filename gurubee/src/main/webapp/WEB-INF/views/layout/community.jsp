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

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/board2.css"
	type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/calendar.css"
	type="text/css">	

<style>

.t {
	font-size: 15px;
	text-decoration: none;

}

.card-bg {
	background-color: aquamarine;
	font-weight: 700;
}

</style>

</head>
<body>
		<div class="container pt-3">
			<div class="">
				<div class="card border rounded-2">
					<div class="card-header card-bg " id="boardmenu" style="width: 100%;">
						<ul>
							<li><i class="bi bi-clipboard-check"></i><a class="t" href="#">전사공지</a>
							<li><a class="t" href="#">부서공지</a>
							<li><a class="t" href="#">커뮤니티</a>
							<li style="float: right;"><a class="" href="#"></a><img src="${pageContext.request.contextPath}/resources/images/icon-plus.png">
						</ul>
					</div>
				
				
					<!-- 게시글 최신 6개까지만 출력 -->
					<div class="card-body" id="boardlist" style="height: 250px; overflow: auto;">
						<table class="table table-hover table-light " style="width: 100%;">
							<tr>
								<th style="width: 60%;">[인사]채용_신규 입사자 공지</th>
								<th>김자바</th>
								<th>2022-10-16</th>
							</tr>
							<tr>
								<th>[인사]채용_신규 입사자 공지</th>
								<th>김자바</th>
								<th>2022-10-16</th>
							</tr>
							<tr>
								<th>[인사]채용_신규 입사자 공지</th>
								<th>김자바</th>
								<th>2022-10-16</th>
							</tr>
							<tr>
								<th>[인사]채용_신규 입사자 공지</th>
								<th>김자바</th>
								<th>2022-10-16</th>
							</tr>
							<tr>
								<th>[인사]채용_신규 입사자 공지</th>
								<th>김자바</th>
								<th>2022-10-16</th>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
			

</body>

</html>
