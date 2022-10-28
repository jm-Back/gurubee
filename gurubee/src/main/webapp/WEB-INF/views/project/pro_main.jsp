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
::-webkit-scrollbar { 
	width: 15px;
} 

::-webkit-scrollbar-thumb { 
background: linear-gradient(#01d6b7, #ffe498);
background-clip: padding-box;
border: 3px solid transparent;
border-radius: 10px; /*스크롤바 라운드*/}

::-webkit-scrollbar-track { 
background-color: #fff; /*스크롤바 트랙 색상*/ 
border-radius: 10px; /*스크롤바 트랙 라운드*/ 
 /*스크롤바 트랙 안쪽 그림자*/}


.btn_projectAdd {
	border-radius: 60px;
	font-size: 23px;
	font-weight:600;
	cursor: pointer;
	color: white;
	background-color: #01d6b7;
	opacity: 80%;
	width: 300px;
	height: 100px;
	border: none;

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

.p_state {
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

.progress__design {
	margin-top: 20px;
	height: 30px;
	width: 100%;
	border-radius: 30px;
	margin-right: 30px;
	
}

.progress__color {
	background: linear-gradient(to right ,#01d6b7, #ffe498);
	margin: 7px;
	border-radius: 30px;
}

.text1 {
    font-size: 14px;
    font-weight: 600
}

.text2 {
    color: #a5aec0
}

.pointer {
 	cursor: pointer;
}

.clear__state {
	padding: 2px 14px;
	display: inline-block;
	border: 3px solid  #ffd980 ;
	outline: none;
	border-radius: 10px;
	font-size: 19px;
	font-weight: 600;
	background: #ffd980;
	margin-left: 20px;
}

.profile__small {
	height: 60px;
	width: 60px;
	object-fit: cover;
    border-radius: 100%;
    border: 2px solid #eee ;
    padding: 3px;
}

h6 {
	font-weight: 600;
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
<div class="container" >	
	<form name="projectAdd" method="post">
		<div class="">
			<button type="button" class="btn_projectAdd shadow p-1 rounded " onclick="location.href='${pageContext.request.contextPath}/project/write.do'"><i class="fa-solid fa-plus"></i> 새 프로젝트</button>
		</div>
	</form>
</div>
	
<form action="${pageContext.request.contextPath}/project/list.do" name="projectList" method="post">
	<div class="container mt-5 mb-3 pt-3 pb-3" style="background-color: #f7f7f7;">
		<div class="row">
		<c:forEach var="dto" items="${list}" varStatus="status">
			<div class="col-md-4 ">
				<div onclick="location.href='${pageContext.request.contextPath}/project/article.do?pro_code=${dto.pro_code}&pd_code=${dto.pd_code}&id_p=${dto.id_p}'" class="card p-3 mb-4 pointer shadow p-1 rounded">
					<div class="d-flex justify-content-between">
						<div class="d-flex flex-row align-items-center">
							<div class="p_photo"></div>
								<div><img class="profile__small" src="${pageContext.request.contextPath}/resources/images/${dto.pro_profile}"></div>
							<div class="side__place" style="justify-content: ;">
								<div class="ms-2 p-details">
									<input type="hidden" value="${dto.pro_code}"> 
									<input type="hidden" value="${dto.pd_code}">
									<input type="hidden" value="${dto.id_p}" >
									<h6 class="mb-0 font__bold">${dto.name_p}</h6> <span>${dto.pro_sdate} ~ ${dto.pro_edate}</span>
									<div class="clear__state">${dto.pro_clear}</div>
								</div>
								
							</div>
						</div>
						
					</div>
					<div class="mt-5">
						<h3 class="heading">${dto.pro_name}</h3>
						<div class="mt-3">
							<p style="color: #404040;">${dto.pro_outline}</p>
							<div class="progress progress__design">
								<div class="progress-bar progress__color " role="progressbar" style="width: ${project_ing}%" ></div>
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
