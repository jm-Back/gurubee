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

.list {
	border: 1px solid #eaecee;
    border-radius: 10px;
    margin-bottom: 20px;
    padding: 25px;
    padding-bottom: 35px;
}

.title {
	color: rgb(4, 5, 5);
    font-weight: bold;
    font-size: 18px;
    line-height: 25px;
    font-family: "Pretendard-Regular", sans-serif;
}

.right {
	float: right;
}

.left {
	float: left;
}

@font-face {
    font-family: 'Pretendard-Regular';
    src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Regular.woff') format('woff');
    font-weight: 400;
    font-style: normal;
}

.file {
	float: left;
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
				<h3>익명 커뮤니티</h3>
			</div>
			<div class="body-main">
				
				
				
					
					
					<div class="board">
						<c:forEach var="dto" items="${listNotice}">
							<div class="list" onclick="location.href='${articleUrl}&num=${dto.num}'" style="cursor: pointer;">	
								<p><span class="notice">공지</span></p>
								<p class="left">
									<a href="${articleUrl}&num=${dto.num}" class="text-reset" style="font-weight: bold">${dto.com_title}</a>
									<c:if test="${dto.replyCount!=0}">(${dto.replyCount})</c:if>
								</p>
								<p>${dto.writer_name}</p>
								<p>${dto.regdate}</p>
								<p><i class="fa-solid fa-eye"></i> ${dto.views}</p>
								<p>
									<c:if test="${not empty dto.save_filename}"><!-- bi bi-file-arrow-down -->
										<a href="${pageContext.request.contextPath}/community/download.do?num=${dto.num}" class="text-reset"><i class="bi bi-box2-heart"></i></a>
									</c:if>
								</p>
							</div>
						</c:forEach>
					</div>
					<div class="board">
						<c:forEach var="dto" items="${list}" varStatus="status">
							<div class="list" onclick="location.href='${articleUrl}&num=${dto.num}'" style="cursor: pointer;">
								
								<p class="right">익명 ${dataCount - (page-1) * size - status.index}</p> 
								<p class="title"><c:if test="${dto.gap<3}"><img id="new" src="${pageContext.request.contextPath}/resources/images/new1.gif"></c:if>&nbsp;&nbsp;${dto.com_title}</p>
									
									
								<p>${dto.com_contents}</p>
								<!-- <p>${dto.writer_name}</p> -->
								<br>
								<div class="foot">
									<div class="left view"><i class="fa-solid fa-eye"></i> ${dto.views}&nbsp;&nbsp;</div> 
									<div class="left"><i class="fa-regular fa-comment-dots"></i> ${dto.replyCount}&nbsp;&nbsp;</div>
									<div class="left"><i class="fa-regular fa-heart"></i>&nbsp;</div>
									<c:forEach var="dtoLike" items="${listLike}" varStatus="status">
										<div class="left"><c:if test="${dtoLike.num == dto.num}">${dtoLike.boardLikeCount}</c:if></div>
									</c:forEach>
									<div class="right">${dto.regdate}</div>
									<div class="file">
										<c:if test="${not empty dto.save_filename}">
											&nbsp;&nbsp;<a href="${pageContext.request.contextPath}/community/download.do?num=${dto.num}" class="text-reset"><i class="fa-regular fa-file"></i></a>
										</c:if>	
									</div>
								</div>
									
							</div>
						</c:forEach>
					</div>
					
				<div class="row board-list-header" style="float: right">
					<div class="col-auto me-auto">${dataCount}개(${page}/${total_page}페이지)</div>
					<div class="col-auto">&nbsp;</div>
				</div>
				<div class="page-navigation">
					${dataCount == 0 ? "등록된 공지사항이 없습니다." : paging}
				</div>
				
				<div class="row board-list-footer">
					<div class="col">
						<button type="button" class="btn btn-light bm" onclick="location.href='${pageContext.request.contextPath}/community/list.do';">새로고침</button>
					</div>
					<div class="col-6 text-center">
						<form class="row" name="searchForm" action="${pageContext.request.contextPath}/community/list.do" method="post">
							<div class="col-auto p-1">
								<select name="condition" class="form-select">
									<option value="all" ${condition=="all"?"selected='selected'":""}>제목+내용</option>
									<option value="name" ${condition=="name"?"selected='selected'":""}>작성부서</option>
									<option value="reg_date" ${condition=="reg_date"?"selected='selected'":""}>등록일</option>
									<option value="com_title" ${condition=="com_title"?"selected='selected'":""}>제목</option>
									<option value="com_content" ${condition=="com_content"?"selected='selected'":""}>내용</option>
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
						<button type="button" class="btn btn-light bm" onclick="location.href='${pageContext.request.contextPath}/community/write.do';">글올리기</button>
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