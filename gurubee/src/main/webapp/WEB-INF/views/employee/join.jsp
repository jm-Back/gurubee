<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp" />

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
 
 .end__btn__design {
	background: #01d6b7;
	font-weight: 600;
	color: #fff;
	border: none;
	padding: 10px 20px;
	
	
}


.body-container {
    font-family: "맑은 고딕", 나눔고딕, 돋움, sans-serif;
	max-width: 800px;
	font-size : 13px;	
	
}

.registerbox {
  font-size: 10px;
  color: white;
 }

.form-holder {
      font-family: "맑은 고딕", 나눔고딕, 돋움, sans-serif;
      min-height: 700px;
      max-width: 700px;
      flex-direction: column;
      justify-content: flex-start;
      align-items: center;
      text-align: center;
      margin: 0 auto;

}

.form-select {
	border: 1px solid #999; border-radius: 4px; background-color: #fff;
	padding: 4px 5px; 
	font-family: "맑은 고딕", 나눔고딕, 돋움, sans-serif;
	font-size: 12px;
}

.form-content .form-items {
    font-family: "맑은 고딕", 나눔고딕, 돋움, sans-serif;
	background-color: #f7f7f7;
    padding: 40px;
    display: inline-block;
    width: 100%;
    min-width: 540px;
    -webkit-border-radius: 30px;
    -moz-border-radius: 3px;
    border-radius: 10px;
    text-align: left;
    -webkit-transition: all 0.4s ease;
}

.body-title h2 {  <!--신입사원등록--> 
    min-width: 200px;
    margin: 0 0 -5px 0;
    padding-bottom: 5px;
    font-size: 23px;
    font-family:"Malgun Gothic", "맑은 고딕", NanumGothic, 나눔고딕, 돋움, sans-serif;
    font-weight: bold;
}

.join_title { 
	font-size: 18px;
	font-weight:600;
	color: white;
	background-color: #01d6b7;
}


.nav-tabs a:hover {
	color: #98E0AD;
}

</style>

<script type="text/javascript">
	
  function memberOk() {
		const f = document.memberForm;
		let str;

		str = f.id.value;
		if (!/^[a-z][a-z0-9_]{4,9}$/i.test(str)) {
			alert("사원번호를 다시 입력 하세요. ");
			f.id.focus();
			return;
		}

		str = f.userName.value;
		if (!/^[가-힣]{2,5}$/.test(str)) {
			alert("이름을 다시 입력하세요. ");
			f.userName.focus();
			return;
		}

		str = f.phone1.value;
		if (!str) {
			alert("전화번호를 입력하세요. ");
			f.tel1.focus();
			return;
		}

		str = f.phone2.value;
		if (!/^\d{3,4}$/.test(str)) {
			alert("숫자만 가능합니다. ");
			f.tel2.focus();
			return;
		}

		str = f.phone3.value;
		if (!/^\d{4}$/.test(str)) {
			alert("숫자만 가능합니다. ");
			f.tel3.focus();
			return;
		}

		str = f.mail1.value.trim();
		if (!str) {
			alert("이메일을 입력하세요. ");
			f.email1.focus();
			return;
		}

		str = f.mail2.value.trim();
		if (!str) {
			alert("이메일을 입력하세요. ");
			f.email2.focus();
			return;
		}

		f.action = "${pageContext.request.contextPath}/employee/${mode}_ok.do";
		f.submit();
		
		alert("완료되었습니다.")
	}

	function changeEmail() {
		const f = document.memberForm;

		let str = f.selectEmail.value;
		if (str !== "direct") {
			f.mail2.value = str;
			f.mail2.readOnly = true;
			f.mail1.focus();
		} else {
			f.mail2.value = "";
			f.mail2.readOnly = false;
			f.mail1.focus();
		}
	}

	function userIdCheck() {
		// 아이디 중복 검사
	let id = $("#userid").val();

	if(!/^[a-z][a-z0-9_]{4,9}$/i.test(id)) { 
		let str = "사원번호 형식이 올바르지 않습니다.";
		$("#userid").focus();
		$("#userid").parent().find(".help-block").html(str);
		return;
	}
	
	let url = "${pageContext.request.contextPath}/employee/userIdCheck.do";
	let query = "userid=" + id;
	
	$.ajax({
		type:"POST"
		,url:url
		,data:query
		,dataType:"json"
		,success:function(data) {
			let passed = data.passed;
      
			if(passed === "true") {
				let str = "<span style='color:blue; font-weight: bold;'>" + id + "</span> 사용가능 합니다.";
				$(".userId-box").find(".help-block").html(str);
				$("#userIdValid").val("true");
			} else {
				let str = "<span style='color:red; font-weight: bold;'>" + id + "</span> 사용할수 없습니다.";
				$(".userId-box").find(".help-block").html(str);
				$("#userid").val("");
				$("#useridValid").val("false");
				$("#userid").focus();
			}
		}
	});
}
	
</script>
</head>
<body>
 <main>
	<!-- 기본메인 화면 -->
	<div class="container py-4">
		<header class="pb-3 mb-4 border-bottom">
			<jsp:include page="/WEB-INF/views/layout/header.jsp" />
			<jsp:include page="/WEB-INF/views/layout/sidebar.jsp" />
		</header>
	</div>
	
	
<div class="container">
     <div class="form-holder mb-5">
      <div class=form-content>
	      <div class = "form-items shadow rounded"> 
	      <div class ="body-title">
	       <div class="row">
		     <div class="col-md-20">
		        <div class="p-2 join_title p-2 rounded" >
				 <h2> <i class="bi bi-person-check-fill"></i> &nbsp; ${mode=="write"?"신입사원 등록":"인사정보 수정"} </h2>
				 </div>
			  </div>
		  </div>
	   </div>
	    <form name="memberForm" method="post">
			<div class="body-main">
				    <div class="row mb-3">
							<label class="col-sm-2 col-form-label" for="Id">* 사원번호</label>
							<div class="col-sm-10 userId-box">
								<div class="row">
									<div class="col-5 pe-1">
										<input type="text" name="id" id="userid"
											class="form-control" value="${dto.id}" ${mode=="update" ? "readonly='readonly' ":""}
											placeholder="사원번호" maxlength="10">
									</div>
									<div class="col-3 ps-1">
										<c:if test="${mode=='write'}">
									 	 <button class="btn btn-primary btn-sm end__btn__design shadow-sm" type="button" onclick="userIdCheck();">중복검사</button>
										</c:if>
									</div>
								</div>
								<c:if test="${mode=='write'}">
									<small class="form-control-plaintext help-block"></small>
								</c:if>
							</div>
						</div>
					 	<div class="row mb-3">
							<label class="col-sm-2 col-form-label" for="userpwd">* 패스워드</label>
							<div class="col-sm-10">
							    <div class="col-5 pe-1">
								<input type="text" name="pwd" id="userpwd"
									class="form-control" value="${dto.pwd}" ${mode=="update" ? "readonly='readonly' ":""} maxlength="6">
								</div>
							</div>
						</div>

						<div class="row mb-3">
							<label class="col-sm-2 col-form-label" for="userName">* 이름</label>
							<div class="col-sm-10">
								<input type="text" name="name" id="userName"
									class="form-control" value="${dto.name}" ${mode=="update" ? "readonly='readonly' ":""} placeholder="이름">
							</div>
						</div>
		

						<div class="row mb-3">
							<label class="col-sm-2 col-form-label" for="tel1">* 주민등록번호</label>
							<div class="col-sm-10 row">

								<div class="col-sm-1 px-1" style="width: 2%;"></div>
								<div class="col-sm-3 px-1">
									<input type="text" name="reg1" id="reg1" class="form-control" value="${dto.reg1}" ${mode=="update" ? "readonly='readonly' ":""} maxlength="6">
								</div>
								<div class="col-sm-1 px-1" style="width: 2%;">
									<p class="form-control-plaintext text-center">-</p>
								</div>
								<div class="col-sm-3 ps-1">
									<input type="text" name="reg2" id="reg2" class="form-control" value="${dto.reg2}" ${mode=="update" ? "readonly='readonly' ":""} maxlength="7">
								</div>
							</div>
						</div>


						<div class="row mb-3">
							<label class="col-sm-2 col-form-label" for="selectEmail">* 이메일</label>
							<div class="col-sm-10 row">
								<div class="col-3 pe-0">
									<select name="selectEmail" class="form-select" onchange="changeEmail();">
										<option value="">선 택</option>
										<option value="naver.com"
											${dto.email2=="naver.com" ? "selected='selected'" : ""}>네이버 메일</option>
										<option value="gmail.com"
											${dto.email2=="gmail.com" ? "selected='selected'" : ""}>지메일</option>
										<option value="hanmail.net"
											${dto.email2=="hanmail.net" ? "selected='selected'" : ""}>한메일</option>
										<option value="hotmail.com"
											${dto.email2=="hotmail.com" ? "selected='selected'" : ""}>핫메일</option>
										<option value="direct">직접입력</option>
									</select>
								</div>

								<div class="col input-group">
									<input type="text" name="mail1" class="form-control" maxlength="30" value="${dto.email1}" ${mode=="update" ? "readonly='readonly' ":""}> <span class="input-group-text p-1" style="border: none; background: none;">@</span> 
									<input type="text" name="mail2" class="form-control" maxlength="30" value="${dto.email2}" readonly="readonly">
								</div>

							</div>
						</div>
                        
						<div class="row mb-3">
							<label class="col-sm-2 col-form-label" for="tel1">* 전화번호</label>
							<div class="col-sm-10 row">
								<div class="col-sm-3 pe-2">
									<input type="text" name="phone1" id="phone1" class="form-control"
										value="${dto.phone1}" ${mode=="update" ? "readonly='readonly' ":""} maxlength="3">
								</div>
								<div class="col-sm-1 px-1" style="width: 2%;">
									<p class="form-control-plaintext text-center">-</p>
								</div>
								<div class="col-sm-3 px-1">
									<input type="text" name="phone2" id="phone2" class="form-control"
										value="${dto.phone2}" ${mode=="update" ? "readonly='readonly' ":""} maxlength="4">
								</div>
								<div class="col-sm-1 px-1" style="width: 2%;">
									<p class="form-control-plaintext text-center">-</p>
								</div>
								<div class="col-sm-3 ps-1">
									<input type="text" name="phone3" id="phone3" class="form-control"
										value="${dto.phone3}"${mode=="update" ? "readonly='readonly' ":""} maxlength="4">
								</div>
							</div>
						</div>

						<div class="row mb-3">
							<label class="col-sm-2 col-form-label" for="tel1">* 내선번호</label>
							<div class="col-sm-10 row">
								<div class="col-sm-3 pe-2">
									<input type="text" name="tel1" id="tel1" class="form-control"
										value="02" maxlength="3" readonly="readonly">
								</div>
								<div class="col-sm-1 px-1" style="width: 2%;">
									<p class="form-control-plaintext text-center">-</p>
								</div>
								<div class="col-sm-3 px-1">
									<input type="text" name="tel2" id="tel2" class="form-control"
										value="383" maxlength="3" readonly="readonly">
								</div>
								<div class="col-sm-1 px-1" style="width: 2%;">
									<p class="form-control-plaintext text-center">-</p>
								</div>
								<div class="col-sm-3 ps-1">
									<input type="text" name="tel3" id="tel3" class="form-control"
										value="${dto.tel3}" maxlength="4">
								</div>
							</div>
						</div>

						<div class="row mb-3">
							<label class="col-sm-2 col-form-label" for="depselect">* 해당부서</label>
							<div class="col-sm-10 row">
								<div class="col-sm-3 pe-2">
									<select class="form-select" aria-label="Default select example"
										name="dep_code" id="dep_code" style="width: 100%;">
										<option selected>부서 선택</option>
										<option value="100"
											${dto.dep_code=="100" ? "selected='selected'" : ""}>인사팀</option>
										<option value="200"
											${dto.dep_code=="200" ? "selected='selected'" : ""}>총무팀</option>
										<option value="300"
											${dto.dep_code=="300" ? "selected='selected'" : ""}>재무팀</option>
										<option value="400"
											${dto.dep_code=="400" ? "selected='selected'" : ""}>영업팀</option>
										<option value="500"
											${dto.dep_code=="500" ? "selected='selected'" : ""}>생산팀</option>
										<option value="600"
											${dto.dep_code=="600" ? "selected='selected'" : ""}>개발팀</option>
										<option value="700"
											${dto.dep_code=="700" ? "selected='selected'" : ""}>마케팅팀</option>
									</select>
									
									
								</div>
							</div>
						</div>

						<div class="row mb-3">
							<label class="col-sm-2 col-form-label" for="posselect">* 해당직급</label>
							<div class="col-sm-10 row">
								<div class="col-sm-3 pe-2">
									<select class="form-select" aria-label="Default select example"
										name="pos_code" id="pos_code" style="width: 100%">
										<option selected>직급 선택</option>
										<option value="1"
											${dto.pos_code=="1" ? "selected='selected'" : ""}>인턴</option>
										<option value="2"
											${dto.pos_code=="2" ? "selected='selected'" : ""}>사원</option>
										<option value="3"
											${dto.pos_code=="3" ? "selected='selected'" : ""}>대리</option>
										<option value="4"
											${dto.pos_code=="4" ? "selected='selected'" : ""}>과장</option>
										<option value="5"
											${dto.pos_code=="5" ? "selected='selected'" : ""}>차장</option>
										<option value="6"
											${dto.pos_code=="6" ? "selected='selected'" : ""}>부장</option>
										<option value="7"
											${dto.pos_code=="7" ? "selected='selected'" : ""}>본부장</option>
										<option value="8"
											${dto.pos_code=="8" ? "selected='selected'" : ""}>대표이사</option>
									</select>
								</div>
							</div>
						</div>
						<div class="row mb-3">
							<label class="col-sm-2 col-form-label" for="type">* 직원구분</label>
							<div class="col-sm-10 row">
								<div class="col-sm-3 pe-2">
									<select class="form-select" aria-label="Default select example"
										name="type" id="type" style="width: 100%;">
										<option selected>선택</option>
										<option value="정규직"
											${dto.type=="정규직" ? "selected='selected'" : ""}>정규직</option>
										<option value="계약직"
											${dto.type=="계약직" ? "selected='selected'" : ""}>계약직</option>
									</select>
								</div>
							</div>
						</div> 
                      
						<div class="row mb-3">
							<label class="col-sm-2 col-form-label" for="startdate">* 입사일자</label>
							<div class="col-sm-10">
								<input type="date" name="startdate" id="startdate"
									class="form-control" value="${dto.startdate}" ${mode=="update" ? "readonly='readonly' ":""} placeholder="입사일자"> <small
									class="form-control-plaintext">.</small>
							</div>
						</div>
						

						<div class="row mb-3">
							<div class="text-center">
								<button class="btn btn-primary end__btn__design shadow-sm" type="button" onclick="memberOk();"> ${mode=="write"?"사원등록":"수정완료"} &nbsp; <i class="bi bi-person-plus"></i>
								</button>
								<button class="btn btn-primary end__btn__design shadow-sm" type="button" onclick="location.href='${pageContext.request.contextPath}/';">
									${mode=="write"?"가입취소":"수정취소"}  &nbsp; <i class="bi bi-person-x"></i>
								</button>
								<input type="hidden" name="userIdValid" id="userIdValid"
									value="false">
							</div>
						</div>
						<div class="row">
							<p class="form-control-plaintext text-center">${message}</p>
					</div>
				 </div>
				 </form>
			 </div>
		 </div>
      </div>
   </div>
 

 



</main>

	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp" />
	</footer>

	<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp" />
</body>
</html>