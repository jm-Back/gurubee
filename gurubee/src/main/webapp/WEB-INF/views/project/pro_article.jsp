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
	font-size: 22px;
	font-weight:600;
	cursor: pointer;
	color: white;
	background-color: #01d6b7;
	opacity: 80%;
}

.plus__project__title {
	font-size: 22px;
	font-weight:600;
	color: white;
	background-color: #01d6b7;
	opacity: 80%;
}

.plus__project2:hover {
	opacity: 100%;
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

.clear__state__end {
	margin-right: 35px;
	padding: 3px 18px;
	display: block;
	border: 3px solid  #ccff99 ;
	outline: none;
	background: #ccff99;
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
	font-size: 12px;
}

.last__edit2 {
	color: black;
	font-size: 15px;
}

.add__emp {
	margin-right: 20px;
	background: #01d6b7;
	text-align: center;
	padding: 10px 10px;
	border-radius: 8px;
	color: #fff;
	font-weight: 600;
	font-size: 16px;
	opacity: 80%;
}

.add__emp:hover {
	opacity: 100%;
	cursor: pointer;
}

/*프로젝트 디테일 부분!*/
.profile__project__detail {
	height: 80px;
	width: 80px;
	object-fit: cover;
    border-radius: 100%;
    border: 2px solid #eee ;
    padding: 3px;
    margin-left: 30px;
}


.font__project__detail {
	font-weight: 600;
	font-size: 24px;
	padding-top: 10px;
}

.edit__icon {
	font-size: 30px;
	color: gray;
	float: right;
	padding-right: 30px;
	cursor: pointer;
	opacity: 80%;
}

.edit__icon:hover {
	opacity: 100%;
}

.hr__style {
	color: gray;
}

.detail__title {
	margin-left: 80px;
	margin-top: 10px;
	border-radius: 10px;
	font-size: 18px;
	padding: 5px;
	text-align: left;
	font-weight: 600;
}

.detail__content {
	margin-left: 20px;
	margin-top: 10px;
	border-radius: 10px;
	font-size: 18px;
	padding: 7px;
	padding-left: 30px;
	text-align: left;
	background: #F3F3F3;
	border: none;
}

.detail__content__textarea {
	margin-left: 20px;
	margin-top: 10px;
	border-radius: 10px;
	font-size: 18px;
	padding-top: 20px;
	padding-left: 20px;
	height: 220px;
	background: #F3F3F3;
	border: none;
}


/*article의 프로그래스바*/
.progress__design_main {
	margin-top: 20px;
	height: 30px;
	margin-left: 9px;
	width: 97%;
	border-radius: 30px;
}


/*디테일의 프로그래스바*/
.progress__design {
	margin-top: 20px;
	height: 30px;
	margin-left: 9px;
	width: 103%;
	border-radius: 30px;
}


.progress__color {
	background: linear-gradient(to right ,#01d6b7, #ffe498);
	margin: 7px;
	border-radius: 30px;
	transition: 0.4s linear;  
  	transition-property: width, background-color;  
}


.progress__design .progress__color {
  width: 85%; 
  background-color: #EF476F;  
  animation: progressAnimation 6s;
}

@keyframes progressAnimation {
  0%   { width: 5%; background-color: #F9BCCA;}
  100% { width: 100%; background-color: #EF476F; }
}

/*${project_ing}*/
.progress__design_main .progress__color {
  width: 85%; 
  background-color: #EF476F;  
  animation: progressAnimation2 6s;
}

@keyframes progressAnimation2 {
  0%   { width: 5%; background-color: #F9BCCA;}
  100% { width: ${project_ing}%; background-color: #EF476F; }
}


.progress__main {
	height: 50px;
}

.modal__size {
	width: 100%;
}

.modal__size2 {
	width: 140%;
}

.modal__input__design {
	width: 400px;
}

.click__icon {
	cursor: pointer;
}

.change__color {
	color: #ff6666;
	opacity: 100%

}


/*프로젝트 디테일 버튼!!*/
.clear__detail {
	cursor: pointer;
	font-weight: 600;
}

.delete__detail {
	color: red;
	cursor: pointer;
	font-weight: 600;
}

.update__detail {
	cursor: pointer;
	font-weight: 600;
}



</style>

<script type="text/javascript">

function ajaxFun(url, method, query, dataType, fn) {
	$.ajax({
		type:method,
		url:url,
		data:query,
		dataType:dataType,
		success:function(data){
			fn(data);
		},
		beforeSend:function(jqXHR){
			//ajax 라고 서버한테 한 번 던져주는거다.
			//내가 만든 서버 ajax 란 이름으로 보냄
			//바디보다 헤더가 더 먼저 서버로 가기 때문에, 헤더에 ajax 보내고
			//서블릿에서 보낸것에서 if return 해본다.
			jqXHR.setRequestHeader("AJAX", true);
			
		},
		error:function(jqXHR){
			if(jqXHR.status === 403) {
				login();
				return false;
			}else if(jqXHR.status === 400){
				alert("요청 처리가 실패했습니다.");
				return false;
			}
			console.log(jqXHR.responseText);
		}
	});
}

//페이징 처리
$(function(){
	listPage(1);
});

function listPage(page){
	let url = "${pageContext.request.contextPath}/project/listDetail.do";
	let pro_code = "${pro_code}";
	let pd_writer = $("input[name=pd_writer]").val();

	let query = "pro_code="+pro_code+"&pageNo="+page+"&pd_writer="+pd_writer;
	let selector = "#here_detail_list";
	
	const fn = function(data){
		 $(selector).html(data);
		
	};
	
	ajaxFun(url, "get", query, "html", fn);
	
}


//참여자 삭제하기 (db 에서도 삭제함)
$(function(){
	$(".fa__location").click(function(){
		if(! confirm("참여자를 삭제하시겠습니까?")){
			return false;
		}
		
		let pj_id = $(this).attr("data-pjId");
		let pro_code = $("input[name=pro_code]").val();
		
		let url = "${pageContext.request.contextPath}/project/delete_emp_ok.do";
		let query = "pj_id="+pj_id+"&pro_code="+pro_code;
		
		const fn = function(data){
			if(data.state === "true"){
				window.location.reload();
			} else if(data.state === "false"){
				alert("프로젝트 참여자는 1명 이상 존재해야합니다.")
				window.location.reload();
			}
		};
		
		ajaxFun(url, "post", query, "json", fn);
		
	});
	
});


//프로젝트 챕터 등록 !!!!!!!!!!!!
$(function(){
	$(".plus__project").click(function(){
		$("#detail__add").modal("show");
		
	});
	
	$(".btnClose").click(function(){
		$("#detail__add").attr("data-mode", "");
		$("#detail__add").modal("hide");
	});
	
	$("#add__detail__submit").click(function(){
		//pro_code와 내용 등등 다 넘기기
		let pd_code = $("#m_pd_code").val();
		let pro_code = $("input[name=pro_code]").val();
		let pd_subject = $("#m_pd_subject").val();
		let pd_content = $("#m_pd_content").val();
		let pd_sdate = $("#m_pd_sdate").val();
		let pd_edate = $("#m_pd_edate").val();
		
		if(! check()){
			return false;
		};
		
		
		let mode = $("#detail__add").attr("data-mode");
		$("#detail__add").attr("data-mode", "");
		
		let url = "";
		let query = "";
		if(mode==="update") {
			url = "${pageContext.request.contextPath}/project/listDetailUpdate.do";
			query = "pd_code="+pd_code;
		} else {
			url = "${pageContext.request.contextPath}/project/listDetail_insert.do";
			query = "pro_code="+pro_code;
		}
		query += "&pd_subject="+pd_subject;
		query += "&pd_content="+pd_content;
		query += "&pd_sdate="+pd_sdate;
		query += "&pd_edate="+pd_edate;
		
		alert(url);
		const fn = function(data){
			if(data.state === "true"){
				$("#detail__add").modal("hide");
				window.location.reload();
				listPage(1);
				
			} else {
				alert("프로젝트 챕터 등록이 실패했습니다.")

			}
		};
		
		ajaxFun(url, "post", query, "json", fn);
		
		
	});
	
});


//챕터 수정...
$(function(){
	$("body").on("click", ".update__detail", function(){
		let pd_subject = $("#form-pd_subject").val();
		let pd_content = $(".detail__content__textarea").val();
		let pd_sdate = $("#form-pd_sdate").val();
		let pd_edate = $("#form-pd_edate").val();
		
		let pd_code = $("#form-pd_code").val();
		
		//값 넣기
		$("#m_pd_subject").val(pd_subject);
		$("#m_pd_content").val(pd_content);
		$("#m_pd_sdate").val(pd_sdate);
		$("#m_pd_edate").val(pd_edate);
		$("#m_pd_code").val(pd_code);
		
		//텍스트 변경
		$("#detail__addLabel2").html("챕터 수정하기");
		$("#guide").html("수정할 내용을 입력하세요!");
		
		$("#add__detail__submit").html(" 수정 완료 ");
		$("#add__detail__cancle").html(" 수정 취소 ");
		$("#detail__add").attr("data-mode", "update");
		
		$("#detail__add").modal("show");
	});
	

});


/*
//프로젝트 챕터 수정 -> 해당 프로젝트 정보 (update 로 변경, 모달로 변경)
$(function(){
	$("body").on("click", ".update__detail", function(){
		

		let pd_subject = $("#form-pd_subject").val();
		let pd_content = $(".detail__content__textarea").val();
		let pd_sdate = $("#form-pd_sdate").val();
		let pd_edate = $("#form-pd_edate").val();
		
		let pd_code = $("#form-pd_code").val();
		
		$("input[name=pd_subject]").val(pd_subject);
		$(".pd_content").val(pd_content);
		$("input[name=pd_sdate]").val(pd_sdate);
		$("input[name=pd_edate]").val(pd_edate);
		
		$("#detail__addLabel2").html("챕터 수정하기");
		$("#guide").html("수정할 내용을 입력하세요!");
		
		$("#add__detail__submit").html(" 수정 완료 ");
		$("#add__detail__submit").attr("data-mode", "update");
		$("#add__detail__cancle").html(" 수정 취소 ");
		
		$("#detail__add").modal("show");
	
	});
	
});
*/


function check(){
	if(! $("#m_pd_subject").val() ){
		$("#m_pd_subject").focus();
		return false;
	};
	
	if(! $("#m_pd_content").val() ){
		$("#m_pd_content").focus();
		return false;
	};
	
	if(! $("#m_pd_sdate").val() ){
		$("#m_pd_sdate").focus();
		return false;
	};
	
	
	if(! $("#m_pd_edate").val() ){
		$("#m_pd_edate").focus();
		return false;
	};
	
	if($("#m_pd_edate").val() ){
		let s1 = $("#m_pd_sdate").val().replace("-", "");
		let s2 = $("#m_pd_edate").val().replace("-", "");
		if(s1 > s2){
			alert("종료일이 시작일보다 빠를 수 없습니다.")
			$("#m_pd_edate").focus();
			return false;
		}
	}
	
	return true;

};


//프로젝트 챕터 삭제
$(function(){
	$("body").on("click", "#delete__detail", function(){
		
		if(! confirm('프로젝트 챕터를 삭제하시겠습니까? (삭제 전 백업을 권장합니다.)')){
			return false;
		}
		
		let pd_code = $(this).attr("data-pd_code");
		let pro_code = $(this).attr("data-pro_code");
		
		let url = "${pageContext.request.contextPath}/project/listDetail_delete.do";
		let query = "pd_code="+pd_code+"&pro_code="+pro_code;
		
		const fn = function(data){
			if(data.state==="true"){
				alert("삭제 되었습니다.");
				window.location.reload();
			} else if(data.state==="false"){
				alert("프로젝트 챕터는 1개 이상 존재해야합니다.");
				window.location.reload();
			}
			
		};
		
		ajaxFun(url, "post", query, "json", fn);
		
	});
	
});

//프로젝트 완료 처리
$(function(){
	$("body").on("click", ".clear__detail", function(){
		let pd_code = $(this).attr("data-pd_code");
		
		if(! confirm('해당 프로젝트 챕터를 완료처리할까요? ')){
			return false;
		}
		
		let url = "${pageContext.request.contextPath}/project/listDetail_clear.do";
		let query = "pd_code="+pd_code; 
		
		const fn = function(data){
			if(data.state==="true"){
				alert("고생하셨습니다!");
				window.location.reload();
			}
		};
		
		ajaxFun(url, "post", query, "json", fn);
		
		
	});
	
});


//참여자 등록
$(function(){
	$(".add__emp").click(function(){
		$("#Emp__list__add").modal("show");
		
	});
	
	$(".btnClose").click(function(){
		$("#Emp__list__add").modal("hide");
	});
	
	
	//새로운 참여자 - 등록하기 눌렀을 때
	$("#add__emp__submit").click(function(){
		//사번, pro_code 필요함
		let pj_id = $("#selectAddEmp option:selected").attr("data-empId"); //사번
		let pro_code = "${pro_code}";

		if($("#selectAddEmp option:selected").length < 1 || $("#selectAddEmp option:selected").length > 1){
			alert("추가 참여자 등록은 1명씩 가능합니다.");
			return false;
		}
		
		let url = "${pageContext.request.contextPath}/project/add_employee.do";
		let query = "pj_id="+pj_id+"&pro_code="+pro_code;
		
		//등록
		const fn = function(data){
			if(data.state === "true"){
				window.location.reload();
				
			} else {
				alert("추가 등록이 실패했습니다.")
			}
		};
		
		ajaxFun(url, "post", query, "json", fn);

		
	});
});


$(function(){
	$(".delete__red").click(function(){
		if(confirm("프로젝트를 삭제하시겠습니까?")){
			alert("더 좋은 프로젝트로 만나요 :) ")
			return;
		} else {
			return false;
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
			<div class="p-3 plus__project__title shadow p-3 rounded" >
				<div class="d-flex justify-content-between">
					<div  class="project__update__icon">Project name : ${dto.pro_name}&nbsp;&nbsp;&nbsp;</div>
					<div class="dropdown">
						<i class="fa-solid fa-ellipsis-vertical click__icon " data-bs-toggle="dropdown" aria-expanded="false"></i>
						<input type="hidden" value="${pro_code}" name="pro_code">
						<input type="hidden" value="${dto.pd_writer}" name="pd_writer">
						<ul class="dropdown-menu">
							<li><a class="dropdown-item" href="${pageContext.request.contextPath}/project/update.do?pro_code=${pro_code}">프로젝트 수정</a></li>
						<c:if test="${sessionScope.member.id == dto.id_p}">
							<li><a class="dropdown-item delete__red" href="${pageContext.request.contextPath}/project/delete_ok.do?pro_code=${pro_code}">프로젝트 삭제</a></li>
						</c:if>
						</ul>
					</div>
				</div>
			</div>	
			<div class="p-3 mb-4 project__detail__design shadow p-1 rounded">
				<div class="d-flex justify-content-between">
					<div class="project_title">${dto.pro_type}</div>
					<div class="${project_ing > 97 ? 'clear__state__end' : 'clear__state'}  shadow-sm">${project_ing > 97 ? '완료' : '진행중'}</div>
				</div>
				<div class="d-flex pt-3 pb-2 justify-content-between">
					<div >${dto.pro_outline}</div>
				</div>
				<div class="progress progress__design_main">
					<div class="progress-bar progress__color" role="progressbar" style="width: ${project_ing}%"></div>
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
			<div class="p-3 mb-4 pb-4 project__detail__design shadow p-1 rounded">
				<div class="d-flex justify-content-between">
					<div class="project_title pb-3">프로젝트 참여자 리스트</div>
					<div class="add__emp shadow-sm">참여자 추가하기</div>
				</div>
				<!-- 참여자 삭제 이벤트 존재함 -->
				<form method="post">
					<c:forEach var="dto" items="${list_emp}" varStatus="status">
						<div class="emp__list shadow-sm mt-4 pt-1" ><img class="profile__small" src="${pageContext.request.contextPath}/resources/images/${dto.pro_profile}">&nbsp;${dto.name_p}(${dto.pos_name})/${dto.dep_name} 
							<div class="fa__location" data-pjId='${dto.pj_id}'>
								<i class="fa-solid fa-rectangle-xmark "></i>
								<input type="hidden" value="${dto.pj_id}" name="pj_id">
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
					<div class="last__edit">&nbsp;●&nbsp;&nbsp;<span class="last__edit2">&nbsp;프로젝트 만든이&nbsp;&nbsp;${vo.name_p}&nbsp;&nbsp;</span></div>
					<input type="hidden" name="id_p" value="${vo.id_p}">
				</div>
			</div>
		</div>

	
	</div>
	<div class="row">
		<div class="col-md-12">
			<div class="p-4 mb-2 mt-2 plus__project plus__project2 shadow p-3 mb-3 rounded">
				<div class="d-flex justify-content-between">
					<div ><i class="fa-solid fa-plus"></i>&nbsp;프로젝트 챕터 추가</div>
				</div>
			</div>	
		</div>
	</div>
	
	
	<!--  상세 프로젝트 챕터 추가하기 -->
	<div id="here_detail_list"></div>
</div>


	<!-- 모달-새로운 프로젝트 참여자 추가 -->
<div class="modal fade" id="Emp__list__add" tabindex="-1" 
		data-bs-backdrop="static" data-bs-keyboard="false"
		aria-labelledby="myDialogModalLabel2" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="Emp__list__addLabel2">새로운 프로젝트 인재</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			<div class="modal-body">
        		<p>새로 추가할 프로젝트 참여자를 선택하세요<p>
					<form name="chooseForm_add_emp" method="post">
						<table class="table form-table">
							<tr>
							    <td width="150"><span>사원 목록</span></td>
							</tr>
							<tr>
							    <td class="left">
							        <select id="selectAddEmp" name="itemLeft" multiple="multiple" class="form-select" style="width:100%; height:120px;">
							    		<c:forEach var="ae" items="${list_add_e}" varStatus="status">
							    			<option value="${ae.id_p}" data-name="${ae.name_p}" data-empId="${ae.id_p}">${ae.name_p}(${ae.pos_name})/${ae.dep_name}</option>
							    		</c:forEach>
							    	</select>
							    </td>
							</tr>
						</table>
					</form>	
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary btnClose">닫기</button>
				<button id="add__emp__submit" type="button" class="btn btn-primary">등록하기</button>
			</div>
		</div>
	</div>
</div>


	<!-- 프로젝트 챕터 모달 -->
<form name="add__detail__form" method="post">
<div class="modal fade modal__size" id="detail__add" tabindex="-1" 
		data-bs-backdrop="static" data-bs-keyboard="false"
		aria-labelledby="myDialogModalLabel2" aria-hidden="true">
	<div class="modal-dialog ">
		<div class="modal-content modal__size2 ">
			<div class="modal-header">
				<h5 class="modal-title" id="detail__addLabel2">프로젝트 챕터 추가</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			<div class="modal-body">
        		<p id="guide">새로운 프로젝트 챕터를 추가하세요!<p>
						<table class="table form-table">
							<tr>
							    <td><span>프로젝트 챕터명</span></td>
							    <td><input type="text" name="pd_subject" id="m_pd_subject"><input class="modal__input__design" type="hidden" id="m_pd_code"></td>
							</tr>
							<tr>
							    <td ><span>진행 기간</span></td>
							    <td><input type="date" name="pd_sdate" id="m_pd_sdate"> ~ <input type="date" name="pd_edate" id="m_pd_edate"></td>
							</tr>
							<tr>
							    <td ><span>프로젝트 챕터 내용</span></td>
							    <td><textarea name="pd_content" class="pd_content" id="m_pd_content"></textarea> </td>
							</tr>
						</table>		
			</div>
			<div class="modal-footer">
				<button id="add__detail__cancle" type="button" class="btn btn-secondary btnClose">닫기</button>
				<button id="add__detail__submit" type="button" class="btn btn-primary">등록하기</button>
			</div>
		</div>
	</div>
</div>
</form>





</main>
	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp" />
	</footer>

<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>

</body>

</html>
