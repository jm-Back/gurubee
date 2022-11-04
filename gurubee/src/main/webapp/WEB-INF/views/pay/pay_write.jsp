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
 
 .body__title {
   font-size: 15px;
   font-weight: 600;
   padding-bottom: 5px;
   font-family:"Malgun Gothic", "맑은 고딕", NanumGothic, 나눔고딕, 돋움, sans-serif;
   
 }

.header__bar {
	font-size: 22px;
	font-weight:600;
	cursor: pointer;
	color: white;
	background-color: #01d6b7;
	opacity: 80%;
}

.form_box {
    flat : right;
	font-size: 15px;
	border: 2px solid #eee;	
}

/*급여등록사원 프로필*/

.profile_form_box {
    flat : right;
	font-size: 15px;
	border: 2px solid #eee;
	background: #b7d2d0;
	
}

.profile__font {
	font-size: 25px;
	font-weight: 600;
	margin-top: 10px;
}

.box_photo {
	overflow: visible;
	text-align: center;
	width: 100%;
	height: 200px;
	padding-bottom: 10px;
	padding-top: 30px;
}


.profile {
	width: 160px;
	height: 160px;
	object-fit: cover;
	border-radius: 100%;
	border: 7px solid #01d6b7;
	padding: 4px;
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

//급여 등록 및 유효성 검사
function payOk(){
	const f = document.payForm;
	let str;
	
	str = f.id.value;
	
	if (!/^[a-z][a-z0-9_]{4,9}$/i.test(str)) {
		alert("사원번호를 다시 입력 하세요. ");
		f.id.focus();
		return;
	}
	
	if(! $("input[name='payment']").val() ) {
		$("input[name='payment']").focus();
		alert("기본급은 필수사항입니다.");
		return false;
	}
	
	f.action = "${pageContext.request.contextPath}/pay/pay_write_ok.do";
    f.submit();
    
    alert("완료되었습니다.")
	
}

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
	  <div class="body-title">
			 <h3> &nbsp; &nbsp;<i class="bi bi-coin"></i> 급여정보 등록</h3>
	  </div>
	       
		   <div  class = "row">
		      <div class ="col-3">
	     		  <div class="p-1 header__bar p-1 rounded">
	     		   </div>	  
			   <div class="p-3 mb-4 profile_form_box p-1 rounded">
			    <div class= "row">
			     <div class="col-mid-3">

			       <div class = "d-flex justify-content-center">
		            <div class="box-photo">
					<img class="profile border rounded-3 " src="${pageContext.request.contextPath}/uploads/profile/${dto.ori_filename}">
				     </div>
				 
  				  </div>
			     </div>
			   </div>
		          <div class="row mb-2" >
									<label class="col-sm-4 col-form-label" for="name"></label>
									<div class="d-flex justify-content-center" >${dto.name} 님 (${dto.id}) 
								</div>
							</div>		
								<div class="row mb-2">
									<label class="col-4 col-form-label" for="dept_name">  </label>
									<div class="col-sm-8">
				            		<div class="form-control-plaintext" ></div>
									</div>
								</div>
								
								<div class="row mb-2">
								    <label class="col-4 col-form-label" for="tel3">  </label>
				            		<div class="d-flex justify-content-center" ><i class="bi bi-telephone-fill"></i> 031-383-${dto.tel3}</div>
								</div>
							  <div class="row mb-2">
				                    <label class="col-sm-6 col-form-label" for="tel3"></label>
				                    <div class="d-flex justify-content-center" >${dto.email} </div>
						</div>
				    </div>
		        </div>
  <!-- 급여 정보등록 폼  -->  
             <div class ="col-8">
			   <div class="p-3 mb-4 form_box  shadow p-1 rounded">
			     <form name="payForm" method="post" class="h-100 p-5 bg-light border rounded-3" style="text-align: center">
			             <div class ="deflex  justify-content-center">
					     <div class="row mb-3">
							<label class="col-sm-2 col-form-label" for="userpwd"> 사원번호* </label>
							    <div class="col-sm-3">
								<input type="text" name="p_id" id="p_id"
									class="form-control" value="${dto.id}"maxlength="9">
								</div>
							</div>
						</div>
					 	<div class="row mb-3">
							<label class="col-sm-2 col-form-label" for="payment"> 기본급 * </label>
							    <div class="col-sm-3">
								<input type="text" name="payment" id="payment"
									class="form-control" value="${dto.payment}"maxlength="9">
								</div>
			               <label class="col-sm-3 col-form-label" for="medical_ins"> 의료보험*</label>
							    <div class="col-sm-3">
								<input type="text" name="medical_ins" id="medical_ins"
									class="form-control" value="${dto.medical_ins}"maxlength="9">
								</div>
							</div>	
						<div class="row mb-3">
							<label class="col-sm-2 col-form-label" for="meal_pay"> 식대*</label>
							    <div class="col-sm-3">
								<input type="text" name="meal_pay" id="meal_pay"
									class="form-control" value="${dto.meal_pay}"maxlength="9">
								</div>
			               <label class="col-sm-3 col-form-label" for="bonus">상여금</label>
							    <div class="col-sm-3">
								<input type="text" name="bonus" id="bonus"
									class="form-control" value="${dto.bonus}"maxlength="9">
								</div>
							</div>
					
						  
						<div class="row mb-3">
							<label class="col-sm-2 col-form-label" for="benefit">복리후생비*  </label>
							    <div class="col-sm-3">
								  <input type="text" name="benefit" id="benefit" class="form-control" value="${dto.benefit}"maxlength="9">
								</div>
			               <label class="col-sm-3 col-form-label" for="residence_tax"> 주민세*</label>
							    <div class="col-sm-3">
								<input type="text" name="residence_tax" id="residence_tax"
									class="form-control" value="${dto.residence_tax}"maxlength="9">
								</div>
							</div>
				
						
						<div class="row mb-3">
							<label class="col-sm-2 col-form-label" for="etc_pay"> 기타지급* </label>
							    <div class="col-sm-3">
								<input type="text" name="etc_pay" id="etc_pay"
									class="form-control" value="${dto.etc_pay}"maxlength="9">
								</div>
			               <label class="col-sm-3 col-form-label" for="employee_ins"> 고용보험* </label>
							    <div class="col-sm-3">
								<input type="text" name="employee_ins" id="employee_ins"
									class="form-control" value="${dto.employee_ins}"maxlength="9">
								</div>
							</div>

						
						<div class="row mb-3">
							<label class="col-sm-2 col-form-label" for="safety_ins"> 산재보험* </label>
							    <div class="col-sm-3">
								<input type="text" name="safety_ins" id="safety_ins" class="form-control" value="${dto.safety_ins}"maxlength="9">
								</div>
			               <label class="col-sm-3 col-form-label" for="longterm_ins"> 장기요양보험* </label>
							    <div class="col-sm-3">
								<input type="text" name="longterm_ins" id="longterm_ins"
									class="form-control" value="${dto.longterm_ins}"maxlength="9">
								</div>
							</div>
						
						
						  
					     <div class="row mb-3">
							<label class="col-sm-2 col-form-label" for="pay_date"> 지급일 * </label>
							    <div class="col-sm-3">
								<input type="date" name="pay_date" id="pay_date" class="form-control" value="${dto.pay_date}" placeholder="지급일자"> <small
									class="form-control-plaintext">.</small>
								</div>
					
						<div class="row">
                            <div style='float: right;'>
								<button class="btn btn-outline-warning btn-sm" type="button" onclick="payOk();"> 급여등록 &nbsp; <i class="bi bi-person-plus"></i>
								</button>
								<input type="hidden" name="userIdValid" id="userIdValid"
									value="false">
							   </div>
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

<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>

</body>

</html>
