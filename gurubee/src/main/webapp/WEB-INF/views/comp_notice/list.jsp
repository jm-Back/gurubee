<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">

<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp"/>

<style type="text/css">

.body-container {
	max-width: 800px;
	min-height: 800px;
	margin-top: 70px;
}

</style>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/board2.css" type="text/css">

<script type="text/javascript">

function searchList() {
	const f = document.searchForm;
	f.submit();
}

</script>
</head>
<body>

<header>
	<jsp:include page="/WEB-INF/views/layout/header.jsp"/>
	<jsp:include page="/WEB-INF/views/layout/sidebar.jsp"/>
</header>

<main>
	<div class="container">
		<div class="body-container">
			<div class="body-title">
				<h3>회사 공지사항</h3>
			</div>
			<div class="body-main">
				<div class="row board-list-header">
					<div class="col-auto me-auto">${dataCount}개(${page}/${total_page} 페이지)</div>
					<div class="col-auto">&nbsp;</div>
				</div>
				
				<table class="table table-hover board-list">
					<thead class="table-light">
						<tr>
							<th class="num">번호</th>
							<th class="subject">제목</th>
							<th class="name">작성자</th>
							<th class="date">작성일</th>
							<th class="hit">번호</th>
						</tr>
					</thead>
					
					<tbody>
						<c:forEach var="dto" items="${list}" varStatus="status">
							<tr>
								<td>${dataCount - (page-1) * size - status.index}</td>
								<td class="left">
									<a href="${articleUrl}&num=${dto.num}" class="text-reset">${dto.subject}</a>
								</td>
								<td>${dto.userName}</td>
								<td>${dto.reg_date}</td>
								<td>${dto.hitCount}</td>
								<td>
									<c:if test="${not empty dto.saveFilename}">
										<a href="${pageContext.request.contextPath}/"></a>
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				
				<div class="page-navigation">
					${dataCount == 0 ? "등록된 공지사항이 없습니다." : paging}
				</div>
				
				<div class="row board-list-footer">
					<div class="col">
						<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/comp_notice/list.do';">새로고침</button>
					</div>
					<div class="col-6 text-center">
						<form class="row" name="searchForm" action="${pageContext.request.contextPath}/comp_notice/list.do" method="post">
							<div class="col-auto p-1">
								<select name="condition" class="form-select">
									<option value="all" ${condition=="all"?"selected='selected'":""}>제목+내용</option>
									<option value="name" ${condition=="name"?"selected='selected'":""}>작성자</option>
									<option value="reg_date" ${condition=="reg_date"?"selected='selected'":""}>등록일</option>
									<option value="notice_title" ${condition=="notice_title"?"selected='selected'":""}>제목</option>
									<option value="notice_content" ${condition=="notice_content"?"selected='selected'":""}>내용</option>
								</select>
							</div>
							<div class="col-auto p-1">
								<input type="text" name="keyword" value="${keyword}" class="form-control">
							</div>
							<div class="col-auto p-1">
								<button type="button" class="btn btn-light" onclick="searchList()">검색</button>
							</div>
						</form>
					</div>
					<div class="col text-end">
						<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/comp_notice/write.do';">글올리기</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</main>

<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</footer>

<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>

</body>
</html>