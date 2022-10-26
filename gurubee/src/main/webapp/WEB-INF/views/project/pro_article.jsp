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

.plus__project {
	font-size: 20px;
	font-weight:600;
	cursor: pointer;
	color: white;
	background-color: #01d6b7;
	opacity: 80%;
}

.project__detail__design {
	font-size: 15px;
	border: 2px solid #eee;
}

/*이전 페이지 가기*/
.back_to_page {
	font-size: 15px;
	border: 2px solid #eee;
	cursor: pointer;
}

.back_to_page:hover {
	background: #eee;
}

.project__update__icon {
	justify-content: space-between;

}

/*프로젝트 마스터 프로필입니다.*/
.pro_master_profile {
	width: 100%;
	height: 470px;
	border: 2px solid #eee;

}

.box_photo{
    overflow: visible;
    text-align: center;
   	width: 100%;
    height: 100%;
    padding-bottom: 20px;
    padding-top: 40px;
}

.profile {
	width: 140px;
    height: 140px; 
    object-fit: cover;
    border-radius: 100%;
    border: 2px solid #eee ;
    padding: 4px;
}

/*토글 디자인*/
.delete__red {
	color: red;
	text-decoration: none;
}

/*프로젝트 진행중 태그*/
.clear__state {
	margin-right: 35px;
	padding: 3px 18px;
	display: block;
	border: 3px solid  #ffd980 ;
	outline: none;
	background: #ffd980;
	border-radius: 10px;
	font-size: 17px;
	font-weight: 600;
}

.project_title {
	font-size: 20px;
	font-weight: 600;

}

.textarea__design {
    width: 100%;
    padding: 9px 20px;
    text-align: left;
    border: 0;
    outline: 0;
    border-radius: 6px;
    background-color: #fff;
    font-size: 15px;
    font-weight: 300;
    -webkit-transition: all 0.3s ease;
    transition: all 0.3s ease;
    margin-top: 16px;
}

.emp__list {
	display:inline-block;
    width: 31%;
    height: 70px;
    padding: 3px 7px;
    text-align: left;
    vertical-align:baseline;
    border: 1px solid #eee;
    outline: 0;
    font-weight: 600;
    border-radius: 6px;
    background-color: #fff;
    font-size: 15px;
    font-weight: 300;
    -webkit-transition: all 0.3s ease;
    transition: all 0.3s ease;
    margin-top: 13px;
    margin-left: 8px;
    margin-right: 3px;
    font-weight: 600;
}

.profile__font {
	font-size: 25px;
	font-weight: 600;
	margin-top: 10px;
}

.profile__font2 {
	font-size: 17px;
	color:   #666666;
	
}

.master__call {
	padding: 6px 70px;
	font-size: 15px;
	margin-top: 20px;
}

.profile__small {
	height: 60px;
	width: 60px;
	object-fit: cover;
    border-radius: 100%;
    border: 2px solid #eee ;
    padding: 3px;
}

.fa__location {
	display:inline-block;
	text-align: right;
	color: #01d6b7;
	font-size: 19px;
	vertical-align: middle;
	padding-bottom: 3px;
	cursor: pointer;
	
}

.last__edit {
	color: #81F54F;
}

.last__edit2 {
	color: black;
}

</style>

<script type="text/javascript">

$(function(){
	$(".fa__location").click(function(e){
		const index = $(e.target).parent().parent().index();

		if(confirm("프로젝트 참여자에서 삭제하시겠습니까?")){
			$(".emp__list").eq(index).remove();
			return;
		}
		
	});

}); 


<c:if test="${sessionScope.member.id=='admin'}">
function deleteBoard() {
    if(confirm("게시글을 삭제 하시 겠습니까 ? ")) {
	    let query = "num=${dto.num}&${query}";
	    let url = "${pageContext.request.contextPath}/notice/delete.do?" + query;
    	location.href = url;
    }
}
</c:if>

$(function(){
	$(".delete__red").click(function(){
		if(confirm("프로젝트를 삭제하시겠습니까?")){
			alert("삭제 되었습니다.")
			return;
		}
		
	});
});


</script>

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
	
	
<div class="container mt-3 mb-3">
	<div class="row">
		<div class="col-md-8">
			<div class="p-3 plus__project shadow p-3 rounded" >
				<div class="d-flex justify-content-between">
					<div  class="project__update__icon">Project name : ${dto.pro_name}&nbsp;&nbsp;&nbsp;</div>
					<div class="dropdown">
						<i class="fa-solid fa-ellipsis-vertical " data-bs-toggle="dropdown" aria-expanded="false"></i>
						<input type="hidden" value="${pro_code}" name="pro_code">
						<input type="hidden" value="${pd_code}" name="pd_code">
						<ul class="dropdown-menu">
							<li><a class="dropdown-item" href="${pageContext.request.contextPath}/project/update.do?pro_code=${pro_code}">프로젝트 수정</a></li>
						<c:if test="${sessionScope.member.id == dto.id_p}">
							<li><a class="dropdown-item delete__red" href="${pageContext.request.contextPath}/project/delete_ok.do?pro_code=${pro_code}&pd_code=${pd_code}">프로젝트 삭제</a></li>
						</c:if>
						</ul>
					</div>
				</div>
			</div>	
			<div class="p-3 mb-4 project__detail__design shadow p-1 rounded">
				<div class="d-flex justify-content-between">
					<div class="project_title">${dto.pro_type}</div>
					<div class="clear__state">${dto.pro_clear}</div>
				</div>
				<div class="d-flex pt-3 pb-2 justify-content-between">
					<div >${dto.pro_outline}</div>
				</div>
				<div class="progress">
					<div class="progress-bar progress-bar-striped progress-bar-animated" role="progressbar" style="width: ${dto.pd_ing}%" aria-valuenow="${dto.pd_ing}" aria-valuemin="0" aria-valuemax="100"></div>
				</div>
				<div class="d-flex pt-4">
					<div >${dto.pro_sdate}</div> &nbsp; ~ &nbsp; <div >${dto.pro_edate}</div>
				</div>
			</div>	
			
			<!-- 프로젝트 개요/내용  -->
			<div class="p-3 mb-4 project__detail__design shadow p-1 rounded">
				<div class="d-flex justify-content-between">
					<div class="project_title">프로젝트 내용</div>
				</div>
				<div class="d-flex justify-content-between">
					<textarea class="textarea__design" readonly="readonly">${dto.pro_content}</textarea>
				</div>
			</div>	
			
			
			<!-- 프로젝트 참여자 목록 -->
			<div class="p-3 mb-4 project__detail__design shadow p-1 rounded">
				<div class="d-flex justify-content-between">
					<div class="project_title">프로젝트 참여자 리스트</div>
				</div>
				<!-- 참여자 삭제 이벤트 존재함 -->
				<form method="post">
					<c:forEach var="dto" items="${list_emp}" varStatus="status">
						<div class="emp__list shadow-sm" ><img class="profile__small" src="${pageContext.request.contextPath}/resources/images/${dto.pro_profile}">&nbsp;${dto.name_p}(${dto.pos_name})/${dto.dep_name} 
							<div class="fa__location">
								<i class="fa-solid fa-rectangle-xmark "></i>
								<input type="hidden" value="${dto.pj_id}" >
							</div>
						</div>
							
					</c:forEach>
				</form>
			</div>
			
		</div>
		<div class="col-md-4">
			<div class="p-3 mb-4 back_to_page shadow p-3 rounded" >
				<div class="d-flex justify-content-between">
					<div onclick="location.href='${pageContext.request.contextPath}/project/list.do'">&nbsp;<i class="fa-solid fa-chevron-left"></i>&nbsp;리스트로 돌아가기</div>
				</div>
			</div>	
			<div class="pro_master_profile mb-4 mt-2 shadow p-1 rounded" >
				<div class="box_photo">
					<img class="profile" src="${pageContext.request.contextPath}/resources/images/${dto.pro_profile}" >
				<div class="pt-5 mt-6">
					<div class="profile__font"> ${dto.pro_master}</div>
					<div class="profile__font2"><i class="fa-solid fa-circle-user"></i>&nbsp;${dto.pos_name} / ${dto.dep_name}</div>
					<div class="profile__font2"><i class="fa-solid fa-phone"></i>&nbsp;${dto.pro_tel} (${dto.pro_phone})</div>
					<div><button class="btn btn-primary master__call" type="button">쪽지 보내기</button></div>
				</div>
				</div>
			</div>
			<div class="p-3 mb-3  shadow p-3 rounded" >
				<div class="d-flex justify-content-between">
					<div class="last__edit">&nbsp;●&nbsp;&nbsp;<span class="last__edit2">&nbsp;프로젝트 작성자&nbsp;&nbsp;${vo.name_p}&nbsp;&nbsp;</span></div>
				</div>
			</div>
		</div>

	
	</div>
	<div class="row">
		<div class="col-md-12">
			<div class="p-4 mb-4 mt-2 plus__project shadow p-3 mb-5 rounded">
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
