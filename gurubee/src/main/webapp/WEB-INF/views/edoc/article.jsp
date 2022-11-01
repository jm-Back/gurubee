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
<link rel="canonical"
	href="https://getbootstrap.com/docs/5.2/examples/jumbotron/">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/board2.css"
	type="text/css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/calendar.css"
	type="text/css">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/se2/js/service/HuskyEZCreator.js"
	charset="utf-8"></script>

<style type="text/css">
#empList a:hover {
	background: #98E0AD;
	color: #fff;
}
</style>

<script type="text/javascript">

function ajaxFun(url, method, query, dataType, fn) {
	$.ajax({
		type:method,
		url:url,
		data:query,
		dataType:dataType,
		success:function(data) {
			fn(data);
		},
		beforeSend:function(jqXHR){
			jqXHR.setRequestHeader("AJAX", true);
		},
		error:function(jqXHR) {
			if(jqXHR.status === 403) {
				login();
				return false;
			} else if(jqXHR.status === 400) {
				alert("요청 처리가 실패했습니다.");
				return false;
			}
			
			console.log(jqXHR.responseText);
		}
	});
}

// 문서 제출
function update() {
  	const f = document.writeForm;
  	
	let edoc = f.edocSelect.value.trim(); // 문서구분
	let content = oEditors.getById["ir1"].getIR(); // 문서폼
	let title = f.title.value.trim(); // 문서제목
	let id_array = [f.empId1.value, f.empId2.value, f.empId3.value, f.empId4.value];
	
	// 결재자 사번 빈 값 없애기 
	const id_apper_array = id_array.filter(
		(element) => true		
	);
	
	console.log(id_array);
  	
  	if(! edoc.trim()) {
      	alert("문서구분을 선택하세요. ");
  	    return false;
 	}
  	
  	if(! title.trim()) {
      	alert("제목을 선택하세요. ");
  	    return false;
 	}
  
 	if(!content || content==="<p><br></p>") {
    	alert("상세내용을 입력하세요. ");
    	$("#content").focus();
    	return false;
  	}
  
 	if(id_apper_array.length < 1) {
 		alert("수신자는 1명 이상 선택하세요. ");
 		return false;
 	}
 	
 	f.action = "${pageContext.request.contextPath}/edoc/update_ok.do";
  
  	f.submit();
}

// 문서 임시저장
function saveOk() {
	alert('임시작성');
	
	const f = document.writeForm;
	
	let edoc = f.edocSelect.value.trim(); // 문서구분
	let content = oEditors.getById["ir1"].getIR(); // 문서폼
	let title = f.title.value.trim(); // 문서제목
	let id_array = [f.empId1.value, f.empId2.value, f.empId3.value, f.empId4.value];
	
	// 결재자 사번 빈 값 없애기 
	const id_apper_array = id_array.filter(
		(element) => true		
	);
	
	console.log(id_array);
  	
  	if(! edoc.trim()) {
      	alert("문서구분을 선택하세요. ");
  	    return false;
 	}
  	
  	if(! title.trim()) {
      	alert("제목을 선택하세요. ");
  	    return false;
 	}
  
 	if(!content || content==="<p><br></p>") {
    	alert("상세내용을 입력하세요. ");
    	$("#content").focus();
    	return false;
  	}
  
 	if(id_apper_array.length < 1) {
 		alert("수신자는 1명 이상 선택하세요. ");
 		return false;
 	}
 	
 	f.action = "${pageContext.request.contextPath}/edoc/write_save.do";
  
  	f.submit();
}

$(function(){

	// 결재하기 모달 클릭
	$("form button[name=btnApproval]").click(function() {
		$("#approvalModal").modal("show");
	});
	
	// 결재하기 모달 - 결재 선택
	$("body").on("click", "#btnEmpSelect", function() {
    	let $ele = $(this).closest(".modal-content");
    	
    	let app_result = $ele.find("input:radio[name=resultRadio]:checked").val();
		let app_num = "${dto.app_num}";
		
		let url = "${pageContext.request.contextPath}/edoc/result_ok.do";
		let query = "app_num=" + app_num + "&app_result=" + app_result;
		
		const fn = function(data) {
			if(data.state) {
				alert(data.msg);
				location.href = "${pageContext.request.contextPath}/edoc/list_receive.do";
			}
		}
		
		ajaxFun(url, "post", query, "json", fn);
	});
    
});


</script>

</head>
<body>

	<header class="pb-3 mb-4 border-bottom">
		<jsp:include page="/WEB-INF/views/layout/header.jsp" />
		<jsp:include page="/WEB-INF/views/layout/sidebar.jsp" />
	</header>

	<main>
		<div class="container py-4">
			<div class="body-title">
				<h3>${dto.title }</h3>
			</div>
			<div class="body-main">

				<div>
					<form method="post" name="articleForm"
						enctype="multipart/form-data">
						<table class="table table-border table-form">

							<tr>
								<th class="fs-6">작성자</th>
								<td class="fs-6">
									${dto.dep_write}&nbsp;${dto.name_write}&nbsp;${dto.pos_write}</td>
							</tr>

							<tr>
								<th class="fs-6">작성일자</th>
								<td>${dto.app_date}</td>
							</tr>

							<tr>
								<th class="fs-6">수신자</th>
								<td><c:forEach var="empdto" items="${empdto}"
										varStatus="status">
										<div>${empdto.dep_name}&nbsp;${empdto.name_apper}&nbsp;${empdto.pos_name}</div>
									</c:forEach></td>
							</tr>

							<tr>
								<th class="fs-6">상세내용</th>
								<td>${dto.doc_form}</td>
							</tr>

							<tr>
								<th class="fs-6">메모</th>
								<td>${dto.memo }</td>
							</tr>

							<c:forEach var="vo" items="${listFile}">
								<tr>
									<td colspan="2">파일 : <a
										href="${pageContext.request.contextPath}/edoc/download.do?fileNum=${vo.fileNum}">${vo.originalFilename}</a>
									</td>
								</tr>
							</c:forEach>
						</table>
						<div style="text-align: right;">
							<c:choose>
								<c:when test="${dto.id_write == sessionScope.member.id}">
									<c:choose>
										<c:when test="${dto.temp == 0}">
											<button type="button" class="btn btn-success"
												style="font-size: 20px;"
												onclick="location.href='${pageContext.request.contextPath}/edoc/temp.do?app_num=${dto.app_num}';">임시문서등록</button>
										</c:when>
										<c:otherwise>
											<button type="button" class="btn btn-success"
												style="font-size: 20px;"
												onclick="location.href='${pageContext.request.contextPath}/edoc/update.do?app_num=${dto.app_num}&page=${page}';">문서수정</button>
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<button type="button" class="btn btn-light" disabled="disabled">수정하기</button>
								</c:otherwise>

							</c:choose>

						</div>

						<div style="text-align: center;">
							<c:choose>
								<c:when test="${dto.id_write == sessionScope.member.id}">
									<button type="button" id="btnApproval" name="btnApproval"
										class="btn btn-secondary" disabled="disabled"
										style="font-size: 25px;">결제하기</button>
								</c:when>
								<c:otherwise>
									<button type="button" id="btnApproval" name="btnApproval"
										class="btn btn-success" style="font-size: 23px;">결제하기</button>
								</c:otherwise>
							</c:choose>

						</div>
						<input type="hidden" name="app_num" value="${dto.app_num }">
						<input type="hidden" name="page" value="${page}"> <input
							type="hidden" name="temp" value="${dto.temp}">
					</form>
				</div>
			</div>
		</div>

		<!-- approvalModal: 결재하기 모달 -->
		<div class="modal" id="approvalModal" tabindex="-1">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title">문서 결재</h5>
						<button type="button" class="btn-close" data-bs-dismiss="modal"
							aria-label="Close"></button>
					</div>
					<div class="modal-body">
						<p>결재하기</p>
						<div>
							<div class="form-check">
								<input class="form-check-input" type="radio" name="resultRadio"
									id="resultRadio_Y" value="1" checked> <label
									class="form-check-label" for="flexRadioDefault1" id="result_1">
									승인 </label>
							</div>
							<div class="form-check">
								<input class="form-check-input" type="radio" name="resultRadio"
									id="resultRadio_N" value="-1"> <label
									class="form-check-label" for="flexRadioDefault2" id="result_2">
									반려 </label>
							</div>

						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" id="btnEmpSelect"
							data-bs-dismiss="modal">선택하기</button>
						<button type="button" class="btn btn-primary" id="btnEmpDelete"
							data-bs-dismiss="modal">취소하기</button>
					</div>
				</div>
			</div>
		</div>

		<!-- 결재하기 모달 end -->

	</main>

	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp" />
	</footer>

	<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp" />
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/se2/js/service/HuskyEZCreator.js"
		charset="utf-8"></script>

</body>

</html>