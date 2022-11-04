<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>spring</title>
<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp"/>

<style type="text/css">
tab-content pt-2{
	width: 55px;
	height: 55px auto;
}

form .img-viewer {
	cursor: pointer;
	border: 1px solid #ccc;
	width: 55px;
	height: 55px;
	border-radius: 55px;
	background-image: url("${pageContext.request.contextPath}${empty dto.ori_filename ? '/resources/images/add_photo.png':'/uploads/profile/'+=dto.ori_filename}");
	position: relative;
	z-index: 9999;
	background-repeat : no-repeat;
	background-size : cover;
}
</style>

<script type="text/javascript">
$(function(){
    // 탭을 클릭할 때 마다
    $("button[role='tab']").on("click", function(e){
		var tab = $(this).attr("aria-controls");
        var selector = "#nav-"+tab;
        
        if(tab==="1") { // 개인정보
        	return false; 
        } else if(tab==="2") { // 근태
        	url = "${pageContext.request.contextPath}/mypage/myatt.do";
        	$(selector).load(url);
        } 
        
		// alert(selector);
		// $(selector).load("서버주소");
    });
});

function changeMonth(year, month) {
	url = "${pageContext.request.contextPath}/mypage/myatt.do";
	let query = "year="+year+"&month="+month;
	
	
	$("#nav-2").load(url+"?"+query);
}

function showClock() {
	let now = new Date();
	
	/*
	let y = now.getFullYear();
	let m = now.getMonth() + 1;
	let d = now.getDate();
	if(m < 10) m = "0" + m;
	if(d < 10) d = "0" + d;
	*/
	
	let hr = now.getHours();
	let mn = now.getMinutes();
	let sc = now.getSeconds();
	if(hr < 10) hr = "0" + hr;
	if(mn < 10) mn = "0" + mn;
	if(sc < 10) sc = "0" + sc;
	
	let s = hr + ":" + mn + ":" + sc;
	
	document.querySelector('.currentTime').innerHTML = s;
	
	setTimeout("showClock()", 1000);
}

function login() {
	location.href="${pageContext.request.contextPath}/member/login.do";
}

function ajaxFun(url, method, query, dataType, fn) {
	$.ajax({
		type:method,
		url:url,
		data:query,
		dataType:dataType,
		success:function(data) {
			fn(data);
		},
		beforeSend:function(jqXHR) {
			jqXHR.setRequestHeader("AJAX", true);
		},
		error:function(jqXHR) {
			if(jqXHR.status === 403) {
				login();
				return false;
			} else if(jqXHR.status === 400) {
				alert("요청 처리가 실패 했습니다.");
				return false;
			}
	    	
			console.log(jqXHR.responseText);
		}
	});
}

$(function(){
	$("body").on("click", ".btn-in", function(){
		if(! confirm("출근 처리를 진행하시겠습니까 ? ")) {
			return false;
		}
		
		let url = "${pageContext.request.contextPath}/mypage/myatt_write.do";
		let query = null;
		// let year = $(this).attr("data-year");
		// let month = $(this).attr("data-month");
		
		const fn = function(data){
			url = "${pageContext.request.contextPath}/mypage/myatt.do?tmp="+new Date().getTime();
        	$("#nav-2").load(url);
			
			$(".btn-in").prop("disabled", true);
			$(".btn-out").prop("disabled", false);
		};
		ajaxFun(url, "post", query, "json", fn);
		
	});
	
	$("body").on("click", ".btn-out", function(){
		if(! confirm("퇴근 처리를 진행하시겠습니까 ? ")) {
			return false;
		}
		
		let att_id = $(this).attr("data-att_id");
		let url = "${pageContext.request.contextPath}/mypage/myatt_update.do";
		let query = "att_id="+att_id;
		// let year = $(this).attr("data-year");
		// let month = $(this).attr("data-month");
		
		const fn = function(data){
			url = "${pageContext.request.contextPath}/mypage/myatt.do?tmp="+new Date().getTime();
        	$("#nav-2").load(url);
			
			$(".btn-out").prop("disabled", true);			
		};
		
		ajaxFun(url, "post", query, "json", fn);
		
	});	
});
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
			
		<h2 class="container mb-2 pt-3"><i class="bi bi-person"></i> | My Page</h2>
		
		<div class="container mb-2 pt-4">
	
			<ul class="nav nav-tabs" id="myTab" role="tablist">
				<li class="nav-item" role="presentation">
					<button class="nav-link active" id="tab-1" data-bs-toggle="tab" data-bs-target="#nav-1" type="button" role="tab" aria-controls="1" aria-selected="true">개인정보</button>
				</li>
				<li class="nav-item" role="presentation">
					<button class="nav-link" id="tab-2" data-bs-toggle="tab" data-bs-target="#nav-2" type="button" role="tab" aria-controls="2" aria-selected="true">근태관리</button>
				</li>
			</ul>
	
			<div class="tab-content pt-5 " id="nav-tabContent">
			<!-- 개인정보관리 -->
			<div class="tab-pane fade show active " id="nav-1" role="tabpanel" aria-labelledby="nav-tab-1">
				<h3 ><i class=" mb-2 bi bi-person-check"></i> | 개인정보</h3>
				<hr class="container mb-2 pt-3" style="width : 95%">
				  <form name="myForm" class="w-75 pt-4 mx-auto border rounded-3" style="text-align: center" enctype="multipart/form-data">
				   <div class="input-form col-md-11 mx-auto">
				   		<div class="row mb-3 pt-3" >
				   			<label class="col-sm-7 fs-6 fw-semibold col-form-label" >프로필&nbsp;사진</label>
				   			<div class="img-viewer mx-5"></div>
				   		</div>
				   	</div>
				   	
  						<div style="margin-bottom: 0px;">
								<div class="row mb-3 pt-3 " >
									<label class="col-sm-7 fs-6 fw-semibold col-form-label" for="name">이&nbsp;름</label>
									<div class="col-sm-2 ">
									<div class="form-control-plaintext" >${dto.name}</div>
				        			</div>
								</div>
								
								<div class="row mb-3 pt-3 ">
									<label class="col-sm-7 fs-6 fw-semibold col-form-label" for="id">사&nbsp;번</label>
									<div class="col-sm-2">
				            		<div class="form-control-plaintext" >${dto.id}</div>
									</div>
								</div>
								
								<div class="row mb-3 pt-3 ">
									<label class="col-sm-7 fs-6 fw-semibold col-form-label" for="dep_name">부&nbsp;서</label>
									<div class="col-sm-2">
				            		<div class="form-control-plaintext" >${dto.dep_name}</div>
									</div>
								</div>
								
								<div class="row mb-3 pt-3 ">
									<label class="col-sm-7 fs-6 fw-semibold col-form-label" for="pos_name">직&nbsp;급</label>
									<div class="col-sm-2">
				            		<div class="form-control-plaintext" >${dto.pos_name}</div>
									</div>
								</div>
						</div>
						
				  	 	<div class="row mb-3 pt-3 ">
				        	<label class="col-sm-7 fs-6 fw-semibold col-form-label" for="reg">주민등록번호</label>
				        	<div class="col-sm-2">
							<div class="form-control-plaintext" >${dto.reg}</div>
				        	</div>
				   		 </div>
				  	 	 
				  	 	 <div class="row mb-3 pt-3 ">
				  			<label class="col-sm-7 fs-6 fw-semibold col-form-label" for="email">이메일 </label>
				  			<div class="col-sm-2">
							<div class="form-control-plaintext" >${dto.email}</div>
							</div>
				  	 	 </div>
				  	 	 
				  	 	<div class="row mb-3 pt-3 ">
				        <label class="col-sm-7 fs-6 fw-semibold col-form-label" for="phone">전화번호</label>
				        <div class="col-sm-2">
				        <div class=" form-control-plaintext" >${dto.phone}</div>
						</div>
				    </div>
				  	 	 
				  	 	 <div class="row pt-3 mx-center">
				        <label class="col-sm-7 fs-6 fw-semibold col-form-label" for="tel">내선번호 </label>
				       		<div class="col-sm-2 ">
				            <div class="form-control-plaintext" >${dto.tel}</div>
				        	</div>
				  	 	 </div>
				  	 	 
				  	 	 <div class="row pt-5 pb-4 mx-auto">
				        <div class="text-center">
				            <button type="button"  name="sendButton" class="btn" style="background-color: aquamarine; color:white;" onclick="location.href='${pageContext.request.contextPath}/mypage/mypage_update.do';"> 수정하기 <i class="bi bi-check2"></i></button>
							<input type="hidden" name="userIdValid" id="userIdValid" value="false">
				        </div>
				    </div>
				  	 	 

				</form>	
				</div>
					<div class="tab-pane fade show active" id="nav-2" role="tabpanel" aria-labelledby="nav-tab-2"></div>
			</div>
		</div>

	</main>

<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</footer>

<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>
</body>
</html>