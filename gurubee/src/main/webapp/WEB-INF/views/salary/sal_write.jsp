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

.sal_profile {
	padding : 20p;'
}



</style>

<script type="text/javascript">

//연봉 등록 및 유효성 검사
function salaryOk(){
	const f = document.salaryForm;
	
	if(! $("input[name='salary']").val() ) {
		$("input[name='salary']").focus();
		alert("연봉 등록은 필수입니다.")
		return false;
	}
	f.action = "${pageContext.request.contextPath}/pay/sal_update_ok.do";
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
			 <h3> &nbsp; &nbsp;<i class="bi bi-coin"></i> 연봉정보 등록</h3>
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
				                    <label class="col-4 col-form-label" for="email"></label>
				    
				                    <div class="d-flex justify-content-center" >${dto.email} </div>
				    </div>
		        </div>
		  </div>
		  
	 
	<!--  연봉 등록폼  -->

	    <div class ="col-8">
	      <div class="p-3 mb-3 shadow p-1 rounded">
			    <form name="salaryForm" method="post" class="h-100 p-5 bg-light border rounded-3" style="text-align: center">
				   
				      <div class="row mb-3">
							<label class="col-sm-2 col-form-label" for="id"> 사원번호 * </label>
							    <div class="col-sm-3">
								<input type="text" name="sal_id" id="sal_id" class="form-control" value="${dto.id}"maxlength="9">
								</div>
			               <label class="col-sm-2 col-form-label" for="salary"> 연봉금액 * </label>
							    <div class="col-sm-3">
								<input type="text" name="salary" id="salary"
									class="form-control" value="${dto.salary}"maxlength="11">
								</div>
							</div>
						

					     <div class="row mb-3">
							<label class="col-sm-2 col-form-label" for="sal_start"> 연봉시작일 * </label>
							    <div class="col-sm-3">
								<input type="date" name="sal_start" id="sal_date" class="form-control" value="${dto.sal_start}" placeholder="연봉시작일"> <small
									class="form-control-plaintext"></small>
								</div>
								
							<label class="col-sm-2 col-form-label" for="sal_end"> 연봉종료일 * </label>
							    <div class="col-sm-3">
								<input type="date" name="sal_end" id="sal_end" class="form-control" value="${dto.sal_end}" placeholder="연봉종료일"> <small
									class="form-control-plaintext"></small>
								</div>
							</div>
							
						  <div class="row mb-3">
							<label class="col-sm-2 col-form-label" for="sal_memo"> 비고 * </label>
							    <div class="col-sm-8">
								<input type="text" name="sal_memo" id="sal_memo" class="form-control" value="${dto.sal_memo}"maxlength="20">
								</div>
								</div>
							
						<div class="row">
                            <div style='float: right;'>
								<button class="btn btn-outline-warning btn-sm" type="button" onclick="salaryOk();"> 연봉등록 &nbsp; <i class="bi bi-person-plus"></i>
								</button>
								<input type="hidden" name="userIdValid" id="userIdValid"
									value="false">
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
