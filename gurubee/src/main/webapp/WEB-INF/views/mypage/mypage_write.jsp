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
        	//url = "${pageContext.request.contextPath}/mypage/myatt.do";
        	//$(selector).load(url);
        } else if(tab==="3") { // 연차
        	//url = "${pageContext.request.contextPath}/mypage/myoff.do";
        	//$(selector).load(url);
        } else if(tab==="4") {	// 급여
        	//url = "${pageContext.request.contextPath}/mypage/mypay.do";
        	//$(selector).load(url);
        }
        
		// alert(selector);
		// $(selector).load("서버주소");
		$(selector).html("<p> 탭-" + tab + " 입니다.<p/>");
    });
});

function memberOk() {
	const f = document.memberForm;
	let str;
	
	str = f.userPwd.value;
	if( !/^(?=.*[a-z])(?=.*[!@#$%^*+=-]|.*[0-9]).{5,10}$/i.test(str) ) { 
		alert("패스워드를 다시 입력 하세요. ");
		f.userPwd.focus();
		return;
	}

	if( str !== f.userPwd2.value ) {
        alert("패스워드가 일치하지 않습니다. ");
        f.userPwd.focus();
        return;
	}
	

    str = f.birth.value;
    if( !str ) {
        alert("주민등록번호를 입력하세요. ");
        f.birth.focus();
        return;
    }
    
    str = f.tel1.value;
    if( !str ) {
        alert("전화번호를 입력하세요. ");
        f.tel1.focus();
        return;
    }

    str = f.tel2.value;
    if( !/^\d{3,4}$/.test(str) ) {
        alert("숫자만 가능합니다. ");
        f.tel2.focus();
        return;
    }

    str = f.tel3.value;
    if( !/^\d{4}$/.test(str) ) {
    	alert("숫자만 가능합니다. ");
        f.tel3.focus();
        return;
    }
    
    str = f.email1.value.trim();
    if( !str ) {
        alert("이메일을 입력하세요. ");
        f.email1.focus();
        return;
    }

    str = f.email2.value.trim();
    if( !str ) {
        alert("이메일을 입력하세요. ");
        f.email2.focus();
        return;
    }

   	f.action = "${pageContext.request.contextPath}/member/${mode}_ok.do";
    f.submit();
}


$(function(){
	// 이메일 
	$("form select[name=selectEmail]").change(function(){
		let s = $(this).val();
		if(s === "") {
			$("form input[name=email2]").val("");
			$("form input[name=email2]").prop("readonly",true);
		} else if(s === "direct") {
			$("form input[name=email2]").val("");
			$("form input[name=email2]").prop("readonly",false);
		} else {
			$("form input[name=email2]").val(s);
			$("form input[name=email2]").prop("readonly",true);
		}
		$("form input[name=email1]").focus();
	});
});

$(function() {
	let img = "${dto.save_filename}";
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
			
		<h2 class="container mb-2 pt-3">My page</h2>
		<hr class="container mb-2 pt-3">
		
		<div class="container mb-2 pt-3">
	
			<ul class="nav nav-tabs" id="myTab" role="tablist">
				<li class="nav-item" role="presentation">
					<button class="nav-link active" id="tab-1" data-bs-toggle="tab" data-bs-target="#nav-1" type="button" role="tab" aria-controls="1" aria-selected="true">개인정보</button>
				</li>
				<li class="nav-item" role="presentation">
					<button class="nav-link" id="tab-2" data-bs-toggle="tab" data-bs-target="#nav-2" type="button" role="tab" aria-controls="2" aria-selected="true">근태관리</button>
				</li>
				<li class="nav-item" role="presentation">
					<button class="nav-link" id="tab-3" data-bs-toggle="tab" data-bs-target="#nav-3" type="button" role="tab" aria-controls="3" aria-selected="true">연차관리</button>
				</li>
				<li class="nav-item" role="presentation">
					<button class="nav-link" id="tab-4" data-bs-toggle="tab" data-bs-target="#nav-4" type="button" role="tab" aria-controls="4" aria-selected="true">급여명세</button>
				</li>				
			</ul>
	
			<div class="tab-content pt-3" id="nav-tabContent">
				<div class="tab-pane fade show active" id="nav-1" role="tabpanel" aria-labelledby="nav-tab-1">
				  <form name="myForm" class="h-100 p-5 bg-light border rounded-3" style="text-align: center" enctype="multipart/form-data">
				   <div class="input-form col-md-12 mx-auto">
				   		<div class="row mb-3" >
				   			<label class="col-sm-2 col-form-label" >프로필사진</label>
				   			<div class="img-viewer"></div>
							<input type="file" name="selectFile" accept="image/*" class="form-control" style="display: none;">
				   		</div>
				   	</div>
  						<div style="margin-bottom: 0px;">
								<div class="row mb-3" >
									<label class="col-sm-2 col-form-label" for="name">이름</label>
									<div class="col-sm-10">
									<div class="form-control-plaintext">${sessionScope.member.name}</div>
				        			</div>
								</div>
								
								<div class="row mb-3">
									<label class="col-sm-2 col-form-label" for="id">사번</label>
									<div class="col-sm-10">
				            		<div class="form-control-plaintext" >${sessionScope.member.id}</div>
									</div>
								</div>
								
								<div class="row mb-3">
									<label class="col-sm-2 col-form-label" for="dep_name">부서</label>
									<div class="col-sm-10">
				            		<div class="form-control-plaintext" >${sessionScope.member.dep_name}</div>
									</div>
								</div>
								
								<div class="row mb-3">
									<label class="col-sm-2 col-form-label" for="pos_name">직급</label>
									<div class="col-sm-10">
				            		<div class="form-control-plaintext" >${sessionScope.member.pos_name}</div>
									</div>
								</div>
					</div>
					
						<div class="row mb-3" >
							<label class="col-sm-2 col-form-label" for="userPwd">패스워드</label>
							<div class="col-sm-10">
				            	<input type="password"  style="width: 50%;" name="userPwd" id="userPwd" class="form-control" autocomplete="off" placeholder="패스워드">
				            	<small class="form-control-plaintext">패스워드는 5~10자이며 하나 이상의 숫자나 특수문자가 포함되어야 합니다.</small>
				        	</div>
				    	</div>
				    
				   	 	<div class="row mb-3">
				        	<label class="col-sm-2 col-form-label" for="userPwd2">패스워드 확인</label>
				       		<div class="col-sm-10">
				            	<input type="password"  style="width: 50%;" name="userPwd2" id="userPwd2" class="form-control" autocomplete="off" placeholder="패스워드 확인">
				            	<small class="form-control-plaintext">패스워드를 한번 더 입력해주세요.</small>
				        	</div>
				  	 	 </div>
				  	 	 
				  	 	<div class="row mb-3">
				        	<label class="col-sm-2 col-form-label" for="reg">주민등록번호</label>
				        	<div class="col-sm-10">
				            	<input type="text" style="width: 50%;" name="reg1" id="reg" class="form-control" value="${dto.reg1}" placeholder="주민등록번호 앞자리"> -
				            	<input type="password" style="width: 50%;" name="reg2" id="reg2" class="form-control" value="${dto.reg2}" placeholder="주민등록번호 뒷자리">
				           	 	<small class="form-control-plaintext"></small>
				        	</div>
				   		 </div>
				  	 	 
				  	 	 <div class="row mb-3">
				  			<label class="col-sm-2 col-form-label" for="email">이메일 </label>
								<input type="text" name="email1" maxlength="30" class="form-control" style="width: 25%;"> @ 
								<input type="text" name="email2" maxlength="30" class="form-control" style="width: 25%;" readonly="readonly">
				        		<select name="selectEmail" class="form-select" style="width: 20%;">
					       			<option value="">선 택</option>
									<option value="naver.com">네이버</option>
									<option value="hanmail.net">한메일</option>
									<option value="gmail.com">구글</option>
									<option value="hotmail.com">핫 메일</option>
									<option value="kakao.com">카카오다음</option>
									<option value="direct">직접입력</option>
								</select>
				  	 	 </div>
				  	 	 
				  	 	<div class="row mb-3">
				        <label class="col-sm-2 col-form-label" for="phone1">전화번호</label>
				        <div class="col-sm-10 row">
							<div class="col-sm-3 pe-2">
								<input type="text" name="phone1" id="phone1" class="form-control" placeholder="phone1" maxlength="3">
							</div>
							<div class="col-sm-1 px-1" style="width: 2%;">
								<p class="form-control-plaintext text-center">-</p>
							</div>
							<div class="col-sm-3 px-1">
								<input type="text" name="phone2" id="phone2" class="form-control" placeholder="phone2" maxlength="4">
							</div>
							<div class="col-sm-1 px-1" style="width: 2%;">
								<p class="form-control-plaintext text-center">-</p>
							</div>
							<div class="col-sm-3 ps-1">
								<input type="text" name="phone3" id="phone3" class="form-control" placeholder="phone3" maxlength="4">
							</div>
				        </div>
				    </div>
				  	 	 
				  	 	 <div class="row mb-3">
				        <label class="col-sm-2 col-form-label" for="tel">내선번호 </label>
				       		<div class="col-sm-10">
				            	<input type="text"  style="width: 50%;" name="tel" id="tel" class="form-control" autocomplete="off" placeholder="tel">
				            	<small class="form-control-plaintext"></small>
				        	</div>
				  	 	 </div>
				  	 	 
				  	 	 <div class="row mb-3">
				        <div class="text-center">
				            <button type="button" name="sendButton" class="btn btn-primary" onclick="sendOk();"> ${"수정완료"} <i class="bi bi-check2"></i></button>
							<input type="hidden" name="userIdValid" id="userIdValid" value="false">
				        </div>
				    </div>
				  	 	 

				</form>	
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