<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp"/>

<style type="text/css">


::-webkit-scrollbar { 
background: linear-gradient(#bae0cb);
background-clip: padding-box;
border: 3px solid transparent;
border-radius: 10px; 

::-webkit-scrollbar-track { 
background-color: #fff; 
border-radius: 10px; 
}

::-webkit-scrollbar-thumb{


}
.container {
height: 400px;
width : 500px;
border : 1px solid black;
overflow-y : scroll;
}

.container p {
font-size :20px 

}

 .end__btn__design {
	background: #01d6b7;
	font-weight: 600;
	color: #fff;
	border: none;
	padding: 10px 20px;

}

.listform {
   width: 80%;
    height: 500px;
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
    
   
tbody th:nth-child(2n){
background-color:#bbd3fb;
}

.listcontainer::-webkit-scrollbar {
width : 10px
background-color : #fff;
border-radius : 10px;

}

.search_form {
  font-family: "맑은 고딕", 나눔고딕, 돋움, sans-serif;
  font-size : 9px 
}

</style>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/board2.css" type="text/css">

<script type="text/javascript">

function searchList() {
	const f = document.searchForm;
	f.submit();
}

function selectemployee()


</script>
</head>
<body>

<main>

<!--기본메인 화면 -->
	<div class="container py-4">
		<header class="pb-3 mb-4 border-bottom">
			<jsp:include page="/WEB-INF/views/layout/header.jsp" />
			<jsp:include page="/WEB-INF/views/layout/sidebar.jsp" />
		</header>
   </div>

<!--사이드 메뉴  -->	

	<div class="container mt-5 pt-4 pb-3">
	<div class="body-title">
				<h3>  <i class="bi bi-coin"></i> 급여 정보관리 </h3>
   </div>
		
			
			<div class="body-main">
			        <div class="search_form">
		            <div class="col-auto">&nbsp;</div>

		         </div>  

				 <div class="col-12  text-end ">
		           <button class="btn btn-outline-warning btn-sm shadow-sm" type="button" onclick=""><i class="bi bi-printer"></i>&nbsp;인사정보인쇄</button>
		           <button class="btn btn-outline-warning btn-sm shadow-sm" type="button" onclick="location.href='${pageContext.request.contextPath}/pay/pay_write.do';"> <i class="bi bi-arrow-bar-left"></i>리스트 &nbsp;</button>
		            </div>
		         </div>
		         
	<!-- 사원리스트 -->
		     <div class="col-auto">&nbsp;</div>
			<div class="container">
				<table class="table table-hover board-list">
					<thead class="table-light" style="">
						<tr class= "title">
							<th class="id">사번</th>
							<th class="name">이름</th>
							<th class="dept">부서</th>
							<th class="post">직책</th>
							<th class="pay_date">지급날짜</th>
							<th class="payment">기본급</th>
							<th class="meal_pay">식대</th>
							<th class="benefit">복리후생비</th>
							<th class="bonus">상여금</th>
							<th class="residence_tax">주민세</th>
							<th class="medical_ins">의료보험</th>
							<th class="safety_ins">산재보험</th>
							<th class="longterm_ins">고용보험</th>
							<th class="tot"> 총 지급금액 </th>
						</tr>
					</thead>
					
		            <thead class="table-light">
					<tbody>
						<c:forEach var="dto" items="${plist}" varStatus="status">
							<tr>
								<td>${dto.pay_id}</td>
								<td>${dto.name}</td>
								<td>${dto.pos}</td>
								<td>${dto.dep}</td>
								<td>${dto.pay_date}</td>
								<td>${dto.payment}</td>
								<td>${dto.meal_pay}</td>
								<td>${dto.benefit}</td>
								<td>${dto.bonus}</td>
								<td>${dto.residence_tax}</td>
								<td>${dto.medical_ins}</td>
								<td>${dto.safety_ins}</td>
								<td>${dto.longterm_ins}</td>
								<td>${dto.tot}</td> 
					
	
							</tr>
						</c:forEach>
					  </tbody>
					 
				</table>
				</div>
		    </div>


</main>

<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</footer>

<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>
</body>
</html>