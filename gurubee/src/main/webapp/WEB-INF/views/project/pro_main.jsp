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

.btn_projectAdd {
	border:  1px solid gray;
	border-radius: 60px;
};

.card {
	border: none;
	border-radius: 10px;
};

.p-details span {
	font-weight: 300;
	font-size: 13px;
};

.p_photo {
    width: 50px;
    height: 50px;
    background-color: #eee;
    border-radius: 15px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 39px
};

.p_state span {
    background-color: #fffbec;
    width: 60px;
    height: 25px;
    padding-bottom: 3px;
    padding-right: 5px;
    border-radius: 5px;
    display: flex;
    color: #fed85d;
    justify-content: center;
    align-items: center;
};

.progress {
    height: 10px;
    border-radius: 10px
}

.progress div {
    background-color: red
}

.text1 {
    font-size: 14px;
    font-weight: 600
}

.text2 {
    color: #a5aec0
}


</style>

<script type="text/javascript">


</script>


</head>
<body>

	<main>
		<!-- 메인 화면 -->
		<div class="container py-4">
			<header class="pb-3 mb-4 border-bottom">
				<jsp:include page="/WEB-INF/views/layout/header.jsp" />
				<jsp:include page="/WEB-INF/views/layout/sidebar.jsp" />
			</header>
		</div>
			
			
		<!-- 프로젝트 메인 등록, 내용 -->
<div class="container" > 프로젝트 메인 	
	<form name="projectAdd">
		<div>
			<button type="button" class="btn_projectAdd" onclick="location.href='${pageContext.request.contextPath}/project/write.do'"><i class="fa-solid fa-plus"></i> 새 프로젝트</button>
		</div>
	</form>
</div>
	
<form action="${pageContext.request.contextPath}/project/list.do" name="projectList" method="post">
	<div class="container mt-5 mb-3 pt-3 pb-3" style="background-color: #eee;">
		<div class="row">
		<c:forEach var="dto" items="${list}" varStatus="status">
			<div class="col-md-4">
				<div class="card p-3 mb-4">
					<div class="d-flex justify-content-between">
						<div class="d-flex flex-row align-items-center">
							<div class="p_photo"><i class="fa-solid fa-heart"></i></div>
							<div class="ms-2 p-details">
								<h6 class="mb-0">${dto.pro_master}</h6> <span>${dto.pro_sdate} ~ ${dto.pro_edate}</span>
							</div>
						</div>
						<div class="p_state"><span>${dto.pro_clear}</span></div>
					</div>
					<div class="mt-5">
						<h3 class="heading">${dto.pro_name}</h3>
						<div class="mt-5">
							<div class="progress">
								<div class="progress-bar" role="progressbar" style="width: ${dto.pd_ing}%" aria-valuenow="${dto.pd_ing}" aria-valuemin="0" aria-valuemax="100"></div>
							</div>
							<div class="mt-3"><span class="text1">${dto.pro_type}</span>  <span class="text2">[참여자목록]</span></div>
						</div>
					</div>
				</div>
			</div>
		</c:forEach>
		
		</div>
	</div>	
</form>

	</main>
	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp" />
	</footer>

</body>

</html>
