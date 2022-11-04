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
				<h3>  <i class="bi bi-coin"></i> 연봉정보관리 </h3>
   </div>
		
			
			<div class="body-main">
			        <div class="search_form">
		              <div class="row board-list-header">
		            <div class="col-auto me-auto"></div>
		            </div>
		         </div> 
		         </div>
		         
	<!-- 전체연봉 리스트 -->
		     <div class="col-auto">&nbsp;</div>
			<div class="container">
				<table class="table table-hover board-list">
					<thead class="table-light" style="">
						<tr class= "title">
							<th class="id">사번</th>
							<th class="name">이름</th>
							<th class="dept_name">부서</th>
							<th class="pos_name">직책</th>
							<th class="salary">연봉금액</th>
							<th class="sal_start">연봉시작일</th>
							<th class="sal_end">연봉종료일</th>
							<th class="sal_memo">비고</th>
						</tr>
					</thead>
					
		            <thead class="table-light">
					<tbody>
						<c:forEach var="dto" items="${list}" varStatus="status">
							<tr>
								<td>${dto.sal_id}</td>
								<td>${dto.sal_name}</td>
								<td>${dto.dep}</td>
								<td>${dto.pos}</td>
								<td>${dto.salary}</td>
								<td>${dto.sal_start}</td>
								<td>${dto.sall_end}</td>
								<td>${dto.sal_memo}</td>
							</tr>
						</c:forEach>
					  </tbody>
					 
				</table>
				</div>


</main>

<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</footer>

<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>
</body>
</html>