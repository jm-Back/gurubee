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

//수정하기
function update(id) {
	location.href="${pageContext.request.contextPath}/employee/update.do?id="+id;
}




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
				<h3>사원통합관리 </h3>
   </div>
		
			
			<div class="body-main">
			        <div class="search_form">
		            <div class="col-auto">&nbsp;</div>
		              <div class="row board-list-header">
		            <div class="col-auto me-auto"></div>
		            </div>
		         </div>  
					<div class="col-6 text-center">
						<form class="row" name="searchForm" action="" method="post">
							<div class="col-auto p-1">
								<select name="condition" class="form-select">
									<option value="dep_code" ${condition=="dep_code"?"selected='selected'":""}>부서</option>
									<option value="post_code" ${condition=="pos_code"?"selected='selected'":""}>직급</option>
									<option value="name" ${condition=="name"?"selected='selected'":""}>이름</option>
								</select>
							</div>
							<div class="col-auto p-1">
								<input type="text" name="keyword" value="${keyword}" class="form-control">
								</div>
							<div class="col-auto p-1">
								<button type="button" class="btn btn-light" onclick="searchList()">검색</button>
							</div>
						</form>
						   </div>
						   </div>
				 <div class="col-12  text-end ">
		           <button class="btn btn-primary btn-sm end__btn__design shadow-sm" type="button" onclick=""><i class="bi bi-printer"></i>&nbsp;인사정보인쇄</button>
		           <button class="btn btn-primary btn-sm end__btn__design shadow-sm" type="button"  type="button" onclick="location.href='${pageContext.request.contextPath}/employee/write.do';"> 신입사원등록 &nbsp; <i class="bi bi-person-plus"></i></button>
		            </div>
		         </div>
		         
	<!-- 사원리스트 -->
		     <div class="col-auto">&nbsp;</div>
			<div class="container">
				<table class="table table-hover board-list">
					<thead class="table-light" style="">
						<tr class= "title">
							<th class="num">선택</th>
							<th class="type">직원구분</th>
							<th class="id">사번</th>
							<th class="dept">부서</th>
							<th class="name">사원명</th>
							<th class="pos">직책</th>
							<th class="startdate">입사일자</th>
							<th class="phone">휴대전화</th>
							<th class="mail">이메일</th>
							<th class="tel">내선번호</th>
							<th class="date_iss">최종발령일</th>
							<th class="update">수정</th>
						</tr>
					</thead>
					
		            <thead class="table-light">
					<tbody>
						<c:forEach var="dto" items="${list}" varStatus="status">
							<tr>
								<td><input type='checkbox' name='mycheck' value='selectemployee'></td>
								<td>${dto.type}</td>
								<td>${dto.id}</td>
								<td>${dto.dept_name}</td>
								<td>${dto.name}</td>
								<td>${dto.pos_name}</td>
								<td>${dto.startdate}</td>
								<td>${dto.phone}</td>
								<td>${dto.email}</td>
								<td>${dto.tel}</td>
								<td>${dto.date_iss}</td>
								<td><button class="btn btn-primary btn-sm end__btn__design shadow-sm" type="button" onclick="update('${dto.id}')">수정</button></td>
			
				
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