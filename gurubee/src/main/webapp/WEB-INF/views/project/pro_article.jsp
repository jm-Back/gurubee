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

.progress__design {
	margin-top: 20px;
	height: 30px;
	margin-left: 9px;
}

.progress__main {
	height: 50px;
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
	let pd_code = "${pd_code}";
	let pd_writer = $("input[name=pd_writer]").val();
	console.log(pd_writer);
	let query = "pd_code="+pd_code+"&pageNo="+page+"&pd_writer="+pd_writer;
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
			} else {
				alert("삭제가 실패했습니다.")
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
			<div class="p-3 plus__project shadow p-3 rounded" >
				<div class="d-flex justify-content-between">
					<div  class="project__update__icon">Project name : ${dto.pro_name}&nbsp;&nbsp;&nbsp;</div>
					<div class="dropdown">
						<i class="fa-solid fa-ellipsis-vertical " data-bs-toggle="dropdown" aria-expanded="false"></i>
						<input type="hidden" value="${pro_code}" name="pro_code">
						<input type="hidden" value="${pd_code}" name="pd_code">
						<input type="hidden" value="${dto.pd_writer}" name="pd_writer">
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
					<div class="clear__state shadow-sm">${dto.pro_clear}</div>
				</div>
				<div class="d-flex pt-3 pb-2 justify-content-between">
					<div >${dto.pro_outline}</div>
				</div>
				<div class="progress">
					<div class="progress-bar progress-bar-striped progress-bar-animated progress__main" role="progressbar" style="width: ${dto.pd_ing}%" aria-valuenow="${dto.pd_ing}" aria-valuemin="0" aria-valuemax="100"></div>
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





	<!-- 마스터 선택 모달창 -->
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



</main>
	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp" />
	</footer>

<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>

</body>

</html>
