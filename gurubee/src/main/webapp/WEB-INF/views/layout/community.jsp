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
	background-color: #00d1b3;
	font-weight: 700;
	opacity: 80%;
}

.card_body {
	text-overflow:ellipsis; 
	overflow:hidden; 
	white-space:nowrap;
}

</style>

</head>
<body>
		<div class="container pt-3">
			<div class="">
				<div class="card border rounded-2">
					<div class="card-header card-bg " id="boardmenu" style="width: 100%;">
						<ul>
							<li><i class="bi bi-clipboard-check"></i><a class="t" href="#">익명 커뮤니티</a>
							<li style="float: right;"><a href="${pageContext.request.contextPath}/community/list.do"><img src="${pageContext.request.contextPath}/resources/images/icon-plus.png"></a>
						</ul>
					</div>
				
				
					<!-- 게시글 최신 6개까지만 출력 -->
					<div class="card-body" id="boardlist" style="height: 250px; overflow: auto;">
						<table class="table table-hover table-light " style="width: 100%;">
							<c:forEach var="dto" items="${list}">
								<tr>
									<th id="title"><a href="${articleUrl}?num=${dto.num}" class="text-reset" style="font-weight: bold">${dto.com_title}</a></th>
									<th>${dto.regdate}</th>
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>
			</div>
		</div>
			

</body>

</html>
