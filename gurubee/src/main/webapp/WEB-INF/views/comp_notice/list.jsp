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

.notice {
	--bs-bg-opacity: 1;
    background-color: #00d1b3;
    --bs-badge-padding-x: 0.65em;
    --bs-badge-padding-y: 0.35em;
    --bs-badge-font-size: 0.75em;
    --bs-badge-font-weight: 700;
    --bs-badge-color: #fff;
    --bs-badge-border-radius: 0.375rem;
    display: inline-block;
    padding: var(--bs-badge-padding-y) var(--bs-badge-padding-x);
    font-size: var(--bs-badge-font-size);
    font-weight: var(--bs-badge-font-weight);
    line-height: 1;
    color: var(--bs-badge-color);
    text-align: center;
    white-space: nowrap;
    vertical-align: baseline;
    border-radius: var(--bs-badge-border-radius);

}

#new {
	width: 18px;
}

.body-title h3 {
	border-bottom: 3px solid #00d1b3;
	color : #00d1b3;
}

.btn {
	background-color: #00d1b3;
	color: white;
}

.btn:hover {
	background-color: #00d1b3;
	color: white;
	font-weight: bold;
}

.table-light {
	background-color: #00d1b3;
	color: white;
}

.form-select {
	background-color: #00d1b3;
	color: white;
}

.active>.page-link, .page-link.active {
	z-index: 3;
    color: var(--bs-pagination-active-color);
    background-color: #00d1b3;
    border-color: #00d1b3;
    height: 40px;
}

.page-link {
	position: relative;
    display: block;
    padding: var(--bs-pagination-padding-y) var(--bs-pagination-padding-x);
    font-size: var(--bs-pagination-font-size);
    border: var(--bs-pagination-border-width) solid var(--bs-pagination-border-color);
    transition: color .15s ease-in-out,background-color .15s ease-in-out,border-color .15s ease-in-out,box-shadow .15s ease-in-out;
    color: #00d1b3;
    height: 40px;
}

.bm {
	margin-top: 4px;
}

.float{
	position:fixed;
	width:60px;
	height:60px;
	bottom:40px;
	right:40px;
	background-color:#00d1b3;
	color:#FFF;
	border-radius:50px;
	text-align:center;
	box-shadow: 2px 2px 3px #999;
}

.float:hover {
	font-weight: bold;
}

.my-float{
	margin-top:22px;
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
							<th class="hit">조회수</th>
							<th class="file">파일</th>
						</tr>
					</thead>
					
					<tbody>
						<c:forEach var="dto" items="${listNotice}">
								<tr>	
									<td><span class="notice">공지</span></td>
									<td class="left">
										<a href="${articleUrl}&num=${dto.num}" class="text-reset" style="font-weight: bold">${dto.notice_title}</a>
									</td>
									<td>${dto.writer_name}</td>
									<td>${dto.regdate}</td>
									<td>${dto.views}</td>
									<td>
										<c:if test="${not empty dto.save_filename}"><!-- bi bi-file-arrow-down -->
											<a href="${pageContext.request.contextPath}/comp_notice/download.do?num=${dto.num}" class="text-reset"><i class="bi bi-box2-heart"></i></a>
										</c:if>
									</td>
								</tr>
							</c:forEach>
					
						<c:forEach var="dto" items="${list}" varStatus="status">
							<tr>
								<td>${dataCount - (page-1) * size - status.index}</td>
								<td class="left">
									<a href="${articleUrl}&num=${dto.num}" class="text-reset">${dto.notice_title}</a>
									<c:if test="${dto.gap<3}"><img id="new" src="${pageContext.request.contextPath}/resources/images/new1.gif"></c:if>
								</td>
								<td>${dto.writer_name}</td>
								<td>${dto.regdate}</td>
								<td>${dto.views}</td>
								<td>
									<c:if test="${not empty dto.save_filename}">
										<a href="${pageContext.request.contextPath}/comp_notice/download.do?num=${dto.num}" class="text-reset"><i class="bi bi-box2-heart"></i></a>
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
						<button type="button" class="btn btn-light bm" onclick="location.href='${pageContext.request.contextPath}/comp_notice/list.do';">새로고침</button>
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
						<button type="button" class="btn btn-light bm" onclick="location.href='${pageContext.request.contextPath}/comp_notice/write.do';">글올리기</button>
					</div>
				</div>
			</div>
			<a href='${pageContext.request.contextPath}/comp_notice/write.do' class="float"><i class="fa fa-plus my-float"></i></a>
		</div>
	</div>
</main>

<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</footer>

<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>

</body>
</html>