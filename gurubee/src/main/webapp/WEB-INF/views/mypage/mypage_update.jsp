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
	background-image: url("${pageContext.request.contextPath}/resources/images/add_photo.png");
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
		$(selector).html("<p> 탭-" + tab + " 입니다.<p/>");
    });
});

function sendOk() {
	const f = document.myForm;
	let str;

	 str = f.pwd.value;
	    if( !str ) {
	        alert("패스워드를 입력하세요. ");
	        f.pwd.focus();
	        return;
	    }
	/*
	str = f.pwd.value;
	if( !/^(?=.*[a-z])(?=.*[!@#$%^*+=-]|.*[0-9]).{5,10}$/i.test(str) ) { 
		alert("패스워드를 다시 입력 하세요. ");
		f.pwd.focus();
		return;
	}
	*/
	
	if( str !== f.pwd2.value ) {
        alert("패스워드가 일치하지 않습니다. ");
        f.pwd.focus();
        return;
	}
	

    str = f.reg.value;
    if( !str ) {
        alert("주민등록번호를 입력하세요. ");
        f.reg.focus();
        return;
    }
    
    str = f.phone.value;
    if( !str ) {
        alert("전화번호를 입력하세요. ");
        f.phone.focus();
        return;
    }

    str = f.tel.value;
    if( !str ) {
        alert("내선번호를 가능합니다. ");
        f.tel.focus();
        return;
    }
    
    str = f.email.value.trim();
    if( !str ) {
        alert("이메일을 입력하세요. ");
        f.email.focus();
        return;
    }
    
   	f.action = "${pageContext.request.contextPath}/mypage/mypage_update_ok.do";
    f.submit();
}


$(function() {
	let img = "${dto.ori_filename}";
	if( img ) { // 수정인 경우
		img = "${pageContext.request.contextPath}/uploads/profile/" + img;
		$("form .img-viewer").empty();
		$("form .img-viewer").css("background-image", "url("+img+")");
	}
	
	$("form .img-viewer").click(function(){
		$("form[name=myForm] input[name=selectFile]").trigger("click"); 
	});
	
	$("form[name=myForm] input[name=selectFile]").change(function(){
		let file=this.files[0];
		if(! file) {
			$("form .img-viewer").empty();
			if( img ) {
				img = "${pageContext.request.contextPath}/uploads/profile/" + img;
				$("form .img-viewer").css("background-image", "url("+img+")");
			} else {
				img = "${pageContext.request.contextPath}/resources/images/add_photo.png";
				$("form .img-viewer").css("background-image", "url("+img+")");
			}
			return false;
		}
		
		if(! file.type.match("image.*")) {
			this.focus();
			return false;
		}
		
		let reader = new FileReader();
		reader.onload = function(e) {
			$("form .img-viewer").empty();
			$("form .img-viewer").css("background-image", "url("+e.target.result+")");
		}
		reader.readAsDataURL(file);
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
	
			<div class="tab-content pt-5" id="nav-tabContent">
				<div class="tab-pane fade show active" id="nav-1" role="tabpanel" aria-labelledby="nav-tab-1">
				<h3><i class="bi bi-person-check-fill"></i> | 개인정보 수정</h3>
				<hr class="container mb-2 pt-3" style="width : 95%">
				  <form name="myForm" method="post" class="w-75 pt-4 mx-auto border rounded-3" style="text-align: center" enctype="multipart/form-data">
				   <div class="input-form col-md-11 mx-auto">
				   		<div class="row mb-3 pt-3" >
				   			<label class="col-sm-5 fs-6 fw-semibold col-form-label">&nbsp;&nbsp;프로필&nbsp;사진</label>
				   			<div class="img-viewer mx-auto"></div>
							<input type="file" name="selectFile" accept="image/*" class="form-control" style="display: none;">
							<input type="hidden" name="ori_filename" value="${dto.ori_filename}">
				   		</div>
				   	</div>
  						<div style="margin-bottom: 0px;">
								<div class="row mb-3" >
									<label class="col-sm-6 fs-6 fw-semibold col-form-label" for="name">이&nbsp;름</label>
									<div class="col-sm-2">
									<div class="form-control-plaintext">${dto.name}</div>
				        			</div>
								</div>
								
								<div class="row mb-3 pt-3">
									<label class="col-sm-6 fs-6 fw-semibold col-form-label" for="id">사&nbsp;번</label>
									<div class="col-sm-2">
				            		<div class="form-control-plaintext" >${dto.id}</div>
									</div>
								</div>
								
								<div class="row mb-3 pt-3">
									<label class="col-sm-6 fs-6 fw-semibold col-form-label" for="dep_name">부&nbsp;서</label>
									<div class="col-sm-2">
				            		<div class="form-control-plaintext" >${dto.dep_name}</div>
									</div>
								</div>
								
								<div class="row mb-3 pt-3">
									<label class="col-sm-6 fs-6 fw-semibold col-form-label" for="pos_name">직&nbsp;급</label>
									<div class="col-sm-2">
				            		<div class="form-control-plaintext" >${dto.pos_name}</div>
									</div>
								</div>
						</div>
					
						<div class="row mb-3 pt-3" >
							<label class="col-sm-6 fs-6 fw-semibold col-form-label" for="pwd">패스워드</label>
							<div class="col-sm-2">
				            	<input type="password"  style="width: 200%;" name="pwd" id="pwd" class="form-control" autocomplete="off" placeholder="5~10자/하나 이상의 숫자, 특수문자">
				        	</div>
				    	</div>
				    
				   	 	<div class="row mb-3 pt-3">
				        	<label class="col-sm-6 fs-6 fw-semibold col-form-label" for="pwd2">패스워드 확인</label>
				       		<div class="col-sm-2">
				            	<input type="password"  style="width: 200%;" name="pwd2" id="pwd2" class="form-control" autocomplete="off" placeholder="패스워드를 한번 더 입력해주세요.">
				        	</div>
				  	 	 </div>
				  	 	 
				  	 	<div class="row justify-content-around">
				        	<label class="col-sm-5 fs-6 pt-4 fw-semibold col-form-label" for="reg">주민등록번호</label>
				        	<div class="col-sm-1 pt-3 input-group" style="width: 45%">
				            	<input type="text" name="reg" id="reg" class="form-control" value="${dto.reg}" placeholder="주민등록번호 앞자리" >
				             		<div class="input-group-text">-</div>
				            		<input type="password" name="reg2" id="reg2" class="form-control" value="${dto.reg2}" placeholder="주민등록번호 뒷자리">
				           	 		<small class="form-control-plaintext"></small>
				        	</div>
				   		 </div>
				  	 	 
				  	 	 <div class="row mb-3 pt-3">
				  			<label class="col-sm-6 fs-6 fw-semibold col-form-label" for="email">이메일 </label>
								<div class="col-sm-2">
									<input type="text"  style="width: 200%;" name="email" id="email" class="form-control" autocomplete="off" placeholder="기존 이메일" value="${dto.email}">
				            		<small class="form-control-plaintext"></small>
				            	</div>
				  	 	 </div>
				  	 	 
				  	 	<div class="row mb-3 pt-3">
				        <label class="col-sm-6 fs-6 fw-semibold col-form-label" for="phone">전화번호</label>
				        	<div class="col-sm-2">
								<input type="text"  style="width: 200%;" name="phone" id="phone" class="form-control" autocomplete="off" placeholder="기존 전화번호" value="${dto.phone}">
				            	<small class="form-control-plaintext"></small>
				        </div>
				    </div>
				  	 	 
				  	 	 <div class="row pt-3 mx-center">
				        <label class="col-sm-6 fs-6 fw-semibold col-form-label" for="tel">내선번호 </label>
				       		<div class="col-sm-2">
				            	<input type="text"  style="width: 200%;" name="tel" id="tel" class="form-control" autocomplete="off" placeholder="기존 내선번호" value="${dto.tel}">
				            	<small class="form-control-plaintext"></small>
				        	</div>
				  	 	 </div>
				  	 	 
				  	 	 <div class="row pt-5 pb-4 mx-auto">
				        <div class="text-center">
				            <button type="button" name="sendButton" class="btn" style="background-color: aquamarine; color:white;" onclick="sendOk();"> 수정완료 <i class="bi bi-check2"></i></button>
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