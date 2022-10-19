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


<script type="text/javascript">
$(function(){
	$(".attend_btn").click(function(){
		$("#attend_Modal").modal("show");
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
			
			
	'<div class="container">
	
		<div>프로젝트 등록</div>
		<table> 
			<tr>
				<td>프로젝트명</td>
				<td>
					<input type="text" name="pro_name">
					<input type="hidden" name="id" value="">
					<input type="hidden" name="pro_clear" value="진행중">
				</td>
			</tr>
			<tr>
				<td>프로젝트 종류</td>
				<td>
					<input type="checkbox" value="DEPT"> DEPT
					<input type="checkbox" value="TEAM"> TEAM
					<input type="checkbox" value="TFT"> TFT
				</td>
			</tr>
			<tr>
				<td>프로젝트 개요</td>
				<td>
					<input type="text" name="pro_outline">
				</td>
			</tr>
			<tr>
				<td>프로젝트 설명</td>
				<td>
					<input type="text" name="pro_content">
				</td>
			</tr>
			<tr>
				<td>프로젝트 총괄자</td>
				<td>
					<input type="text" name="pro_master">
				</td>
			</tr>
			<tr>
				<td>프로젝트 시작일</td>
				<td>
					<input type="date" name="pro_sdate">
				</td>
			</tr>
			<tr>
				<td>프로젝트 종료일</td>
				<td>
					<input type="date" name="pro_edate">
				</td>
			</tr>
		</table>
		<br>
		
		<div>참여자 목록</div>
		<table>
			<tr>
				<td>참여자 등록</td>
				<td>
					<input type="text">
							<button type="button" class="attend_btn">선택하기</button>
				</td>
			</tr>
			<tr>
				<td>프로젝트 종류</td>
				<td>
					<input type="checkbox" value="DEPT"> DEPT
					<input type="checkbox" value="TEAM"> TEAM
					<input type="checkbox" value="TFT"> TFT
				</td>
			</tr>
		</table>
		
		
		
	</div>	
	
	
	<!-- 참여자 선택 모달창 -->
<div class="modal fade" id="attend_Modal" tabindex="-1" 
		data-bs-backdrop="static" data-bs-keyboard="false"
		aria-labelledby="myDialogModalLabel2" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="attend_ModalLabel2">프로젝트 참여자 목록</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			<div class="modal-body">
        		<h4>프로젝트 참여자를 선택하세요.</h4>
        		<p>대화상자 밖을 클릭해도 대화상자는 사라지지 않는다.</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary btnClose">닫기</button>
				<button type="button" class="btn btn-primary">등록하기</button>
			</div>
		</div>
	</div>
</div>
	
	
	
	
</main>
	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp" />
	</footer>

</body>

</html>
