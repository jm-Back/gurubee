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

.pt__style {
	padding-top: 12px;
}



</style>

</head>
<body>
		<div class="container pt__style">
			<div class="">
				<div class="card border rounded-2">
					<div class="card-header card-bg " id="boardmenu" style="width: 100%;">
						<ul>
							<li><i class="bi bi-envelope-paper"></i>&nbsp;문서 발신함
							<li style="float: right;"><a href="${pageContext.request.contextPath}/edoc/list_send.do"><img src="${pageContext.request.contextPath}/resources/images/icon-plus.png"></a>
						</ul>
					</div>
				
				
					<div class="card-body" id="boardlist" style="height: 250px; overflow: auto;">
						<table class="table table-hover table-light " style="width: 100%;">
							<c:forEach var="dto" items="${list}">
								<tr>
									<th>[${dto.app_doc}]</th>
									<th id="title"><a href="${articleUrl}?app_num=${dto.app_num}&page=1" class="text-reset" style="font-weight: bold">${dto.title}</a></th>
									<th>${dto.result_name}</th>
									<th>${dto.result}</th>
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>
			</div>
		</div>
			

</body>

</html>
