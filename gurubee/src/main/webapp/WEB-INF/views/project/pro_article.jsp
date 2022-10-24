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
	
<style type="text/css">
.plus__project {
	font-size: 20px;
	font-weight:600;
	cursor: pointer;
	color: white;
	background-color: #01d6b7;"
}

.project__update__icon {
	justify-content: space-between;

}

.pro_master_profile {
	width: 100%;
	height: 300px;

}

.box_photo{

    overflow: visible;
    text-align: center;
   	width: 100%;
    height: 100%;
    padding-bottom: 20px;
}

.profile {
	width: 120px;
    height: 120px; 
    object-fit: cover;
    border-radius: 100%;
    border: 5px solid aquamarine ;
    padding: 4px;
}

</style>

</head>
<body>

<main>
	<!-- 디폴트 메인 화면 -->
	<div class="container py-4">
		<header class="pb-3 mb-4 border-bottom">
			<jsp:include page="/WEB-INF/views/layout/header.jsp" />
			<jsp:include page="/WEB-INF/views/layout/sidebar.jsp" />
		</header>
	</div>
	
	
<div class="container mt-5 mb-3">
	<div class="row">
		<div class="col-md-8">
			<div class="card p-3 plus__project" >
				<div class="d-flex justify-content-between">
					<span  class="project__update__icon">${dto.pro_name}&nbsp;&nbsp;&nbsp;<i class="fa-solid fa-ellipsis-vertical"></i></span>
				</div>
			</div>	
			<div class="p-3 mb-4" style="background-color: #eee;">
				<div class="d-flex justify-content-between">
					<div >프로젝트 참여자 목록</div>
				</div>
			</div>	
		</div>
		<div class="col-md-4">
			<div class="pro_master_profile mb-5" style="background: #eee">
				<div class="box_photo">
					<img class="profile" src="${pageContext.request.contextPath}/resources/images/profile.jpg" ></div>
					<span class="fw-bold fs-6"> ${dto.pro_master}님</span>
			</div>
		</div>
	</div>
	
	<div class="row">
		<div class="col-md-12">
			<div class="card p-3 plus__project" >
				<div class="d-flex justify-content-between">
					<div >프로젝트 참여자 목록</div>
				</div>
			</div>	
			<div class="p-3 mb-4" style="background-color: #eee;">
				<div class="d-flex justify-content-between">
					<div >프로젝트 참여자 목록</div>
				</div>
			</div>	
		</div>
	</div>
	
	<div class="row">
		<div class="col-md-12">
			<div class="p-4 mb-4 mt-1 plus__project">
				<div class="d-flex justify-content-between">
					<div ><i class="fa-solid fa-plus"></i>&nbsp;프로젝트 챕터 추가</div>
				</div>
			</div>	
		</div>
	</div>
</div>


	
	
</main>
	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp" />
	</footer>

<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>

</body>

</html>
