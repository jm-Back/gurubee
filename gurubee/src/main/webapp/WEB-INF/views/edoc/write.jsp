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
function sendOk() {
  	const f = document.writeForm;
  	
  	let edoc = f.edocSelect.value.trim(); // 문서구분
	oEditors.getById["ir1"].exec("UPDATE_CONTENTS_FIELD", []);
	let content = oEditors.getById["ir1"].getIR(); // 문서폼
	let title = f.title.value.trim(); // 문서제목
	let id_array = [f.empId1.value, f.empId2.value, f.empId3.value, f.empId4.value];
	
	// 결재자 사번 빈 값 없애기 
	id_array = id_array.filter(function(id) {
		return id !== null && id!='';
	});
	
  	
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
 	
 	if(id_array.length===0) {
 		alert("수신자는 1명 이상 선택하세요. ");
 		return false;
 	}
 	
 	if(id_array.indexOf('${sessionScope.member.id}')!=-1) {
 		alert("본인이 수신자가 될 수 없습니다.");
 		return false;
 	}
 	
 	f.action = "${pageContext.request.contextPath}/edoc/${mode}_ok.do";
 
    f.submit();
}

// 문서 임시저장
function saveOk() {
	const f = document.writeForm;
	
	let edoc = f.edocSelect.value.trim(); // 문서구분
	oEditors.getById["ir1"].exec("UPDATE_CONTENTS_FIELD", []);
	let content = oEditors.getById["ir1"].getIR(); // 문서폼
	let title = f.title.value.trim(); // 문서제목
	let id_array = [f.empId1.value, f.empId2.value, f.empId3.value, f.empId4.value];
	
	// 결재자 사번 빈 값 없애기 
	id_array = id_array.filter(function(id) {
		return id !== null && id!='';
	});
	
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
  
 	if(id_array.length < 1) {
 		alert("수신자는 1명 이상 선택하세요. ");
 		return false;
 	}
 	
 	if(id_array.indexOf('${sessionScope.member.id}')!=-1) {
 		alert("본인이 수신자가 될 수 없습니다.");
 		return false;
 	}
 	
 	f.action = "${pageContext.request.contextPath}/edoc/write_save.do";
  
  	f.submit();
  	
  	alert('임시작성');
}

$(function(){
	// 작성일자
	$('input[name=date]').attr('value', year+"-"+month+"-"+day);
	
	$('select[name=edocSelect]').val('${dto.app_doc}').prop("selected",true);
	
	// 사원검색 모달 
    $(".empSearch").on("click", function() {
    	let pos_code = $(this).attr("data-posCode");
    	
    	let url = "${pageContext.request.contextPath}/edoc/write_searchEmp.do";
    	let query = "pos_code="+encodeURIComponent(pos_code);
    	
    	const fn = function(empList) {
    		$("#empList").html(empList);
			$("#empListModal").modal("show");
    	}
    	
        ajaxFun(url, "post", query, "text", fn);
    	
	});
	
	
	// 사원 검색 클릭
	$("form input[name=empSearch]").on("click", function() {
		clickEmpSearch($(this));
	});
	
	
    // 문서구분 select - 값에 따른 문서폼 가져오기
    $("select[name=edocSelect]").change(function() {
    	let edoc = $(this).val();
    	edoc = encodeURIComponent(edoc);

    	let url = "${pageContext.request.contextPath}/edoc/write_edocForm.do";
        let query = "edoc="+edoc;
        
        const fn = function(doc_form) {
    		// 문서구분이 선택되면 문서폼 가져와서 에디터 content 채우기
    		oEditors.getById["ir1"].exec("SET_IR", ['']);
    		oEditors.getById["ir1"].exec("PASTE_HTML", [doc_form]);
    		oEditors.getById["ir1"].exec("UPDATE_CONTENTS_FIELD", []);
    	}
        
        ajaxFun(url, "post", query, "text", fn);
	});
    
    
    // 수신자 검색 - select 된 사원 데이터 가져오기
    $("body").on("click", "#btnEmpSelect", function() {
    	let $ele = $(this).closest(".modal-content").find("select");
    	
		let id_apper = $ele.val();
    	let name_apper = $ele.find("option:selected").attr("data-name"); 
    	let pos_code = $ele.attr("data-posCode");
    	// let pos_name = $ele.attr("data-posName");
    	
    	// alert(id_apper+": "+name_apper+": "+pos_code);
    	if(pos_code === "3") {
    		$("#empId1").val(id_apper);
			$("#empSearch1").val(name_apper+" 대리");
    	} else if (pos_code === "4") {
    		$("#empId2").val(id_apper);
			$("#empSearch2").val(name_apper+" 과장");
    	} else if (pos_code === "5") {
    		$("#empId3").val(id_apper);
			$("#empSearch3").val(name_apper+" 차장");
    	} else if (pos_code === "6") {
    		$("#empId4").val(id_apper);
			$("#empSearch4").val(name_apper+" 부장");
    	}
	});
   
    
 	// 수신자 검색 - select 된 사원 삭제
    $("body").on("click", "#btnEmpDelete", function() {
    	let $ele = $(this).closest(".modal-content").find("select");
    	let pos_code = $ele.attr("data-posCode");
    	
    	if(pos_code === "3") {
    		$("#empId1").val("");
			$("#empSearch1").val("");
    	} else if (pos_code === "4") {
    		$("#empId2").val("");
			$("#empSearch2").val("");
    	} else if (pos_code === "5") {
    		$("#empId3").val("");
			$("#empSearch3").val("");
    	} else if (pos_code === "6") {
    		$("#empId4").val("");
			$("#empSearch4").val("");
    	}
	});
   

    
});

// 수신자 사원 검색 ajax 
function clickEmpSearch(object) {
	let pos_code = object.attr("data-posCode");
	// alert(pos_code);
	
	let url = "${pageContext.request.contextPath}/edoc/write_searchEmp.do"
	let query = "pos_code="+encodeURIComponent(pos_code);
	
	const fn = function(empList) {
		$("#empList").html(empList);
		$("#empListModal").modal("show");
	}
	
    ajaxFun(url, "post", query, "text", fn);
}

<c:if test="${mode=='update'}">
function deleteFile(fileNum, app_num) {
	if(! confirm("파일을 삭제 하시겠습니까 ?")) {
		return;
	}
	
	let query = "fileNum="+fileNum+"&app_num="+app_num+"&page=${page}";
	let url = "${pageContext.request.contextPath}/edoc/deleteFile.do?" + query;
	location.href = url;
}
</c:if>

</script>

</head>

<header class="pb-3 mb-4 border-bottom">
	<jsp:include page="/WEB-INF/views/layout/header.jsp" />
	<jsp:include page="/WEB-INF/views/layout/sidebar.jsp" />
</header>
<body>

	<main>
		<div class="container py-4">
			<div class="body-title">
				<h3>
					<i class="bi bi-envelope-paper"></i>&nbsp;문서 작성
				</h3>
			</div>
			<div>

				<form action="" method="post" name="writeForm"
					enctype="multipart/form-data">
					<table class="table table-border table-form">
						<tr>
							<th class="fs-6">문서구분</th>
							<td><select class="form-select"
								aria-label="Default select example" name="edocSelect"
								id="edocSelect" style="width: 50%;">
									<option selected>문서구분 선택</option>
									<option value="휴가신청서">휴가신청서 - 각종 휴가 신청서 사용 양식</option>
									<option value="DB접근권한신청서">DB접근권한신청서 - DB 계정 신청시 사용 양식</option>
									<option value="구매요청의뢰서">구매요청의뢰서 - 회사 비품 구매 신청서 사용 양식</option>
									<option value="재택근무신청서">재택근무신청서 - 재택근무 신청 양식</option>
									<option value="법인카드지출결의서">법인카드지출결의서 - 법인카드 지출결의를 위한 양식</option>
									<option value="출장신청서">출장신청서 - 출장 전 품의 결재시 사용 양식</option>
							</select></td>
						</tr>

						<tr>
							<th class="fs-6">제목</th>
							<td>
								<div class="mb-3">
									<input class="form-control" type="text" id="title" name="title"
										multiple value="${dto.title }"
										style="width: 50%; height: 80px;">
								</div>
							</td>
						</tr>

						<tr>
							<th class="fs-6">작성자</th>
							<td class="fs-6"><input type="text" name="name"
								value="${sessionScope.member.name}" class="form-control"
								readonly="readonly" style="width: 27%;"></td>
						</tr>

						<tr>
							<th class="fs-6">작성일자</th>
							<td><input type="text" name="date" value=" "
								class="form-control" readonly="readonly" style="width: 27%;">
							</td>
						</tr>
						
						<tr>
							<th class="fs-6">수신자</th>
							<td>
								<div>
									<div>
										<p>
											<input type="text" id="empSearch1" name="empSearch"
												class="form-control" data-posCode="3" style="width: 27%;"
												placeholder="대리 - 사원 검색" readonly="readonly"> <input
												type="hidden" id="empId1" name="empId" value="">
										</p>

										<p>
											<input type="text" id="empSearch2" name="empSearch"
												class="form-control" data-posCode="4" style="width: 27%;"
												placeholder="과장 - 사원 검색" readonly="readonly"> <input
												type="hidden" id="empId2" name="empId" value="">
										</p>

										<p>
											<input type="text" id="empSearch3" name=empSearch
												class="form-control" data-posCode="5" style="width: 27%;"
												placeholder="차장 - 사원 검색" readonly="readonly"> <input
												type="hidden" id="empId3" name="empId" value="">
										</p>

										<p>
											<input type="text" id="empSearch4" name="empSearch"
												class="form-control" data-posCode="6" style="width: 27%;"
												placeholder="부장 - 사원 검색" readonly="readonly"> <input
												type="hidden" id="empId4" name="empId" value="">
									</div>
								</div>
							</td>
						</tr>

						<tr>
							<th class="fs-6">상세내용</th>
							<td><textarea name="content" id="ir1" class="form-control"
									style="width: 90%; height: 500px;">${dto.doc_form}</textarea></td>
						</tr>

						<tr>
							<th class="fs-6">첨부파일</th>
							<td>
								<div class="mb-3">
									<input class="form-control" type="file" name="selectFile"
										multiple="multiple" style="width: 50%;">
								</div>
							</td>
						</tr>
						<c:if test="${mode=='update'}">
							<c:forEach var="vo" items="${listFile}">
								<tr>
									<td class="table-light col-sm-2" scope="row">첨부된파일</td>
									<td>
										<p class="form-control-plaintext">
											<a
												href="javascript:deleteFile('${vo.fileNum}','${dto.app_num}');"><i
												class="bi bi-trash"></i></a> ${vo.originalFilename}
										</p>
									</td>
								</tr>
							</c:forEach>
						</c:if>

						<tr>
							<th class="fs-6">메모</th>
							<td>
								<div class="mb-3">
									<input class="form-control" type="text" id="memo" name="memo"
										multiple style="width: 50%; height: 80px;">
								</div>
							</td>
						</tr>
					</table>
					<div style="text-align: right;">
						<button type="button" onclick="saveOk();"
							class="btn btn-secondary" style="font-size: 17px;">임시작성</button>
					</div>

					<div style="text-align: center;">
						<button type="button" onclick="sendOk();" class="btn btn-success"
							style="font-size: 20px;">${mode=='update'?'수정하기':'결제요청'}</button>
					</div>

					<input type="hidden" name="app_num" value="${dto.app_num }">
					<input type="hidden" name="page" value="${page}"> <input
						type="hidden" name="size" value="${size}"> <input
						type="hidden" name="mode" value="${mode}">
					<c:if test="${mode=='update'}">
						<input type="hidden" name="temp" value="-1">
					</c:if>
					<c:if test="${mode!='update'}">
						<input type="hidden" name="temp" value="1">
					</c:if>
				</form>
			</div>

		</div>

		<!-- empListModal: 사원검색의 사원리스트 모달 start -->
		<div class="modal" id="empListModal" tabindex="-1">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title">사원 검색</h5>
						<button type="button" class="btn-close" data-bs-dismiss="modal"
							aria-label="Close"></button>
					</div>
					<div class="modal-body">
						<p>수신자 사원 리스트</p>
						<div id="empList"></div>
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

		<!-- 사원검색 모달 end -->

	</main>

	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp" />
	</footer>

	<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp" />

	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/se2/js/service/HuskyEZCreator.js"
		charset="utf-8"></script>
	<script type="text/javascript">

const date = new Date();

const year = date.getFullYear();
const month = date.getMonth() + 1;
const day = date.getDate();

var oEditors = [];
nhn.husky.EZCreator.createInIFrame({
	oAppRef: oEditors,
	elPlaceHolder: "ir1",
	sSkinURI: "${pageContext.request.contextPath}/resources/se2/SmartEditor2Skin.html",
	fCreator: "createSEditor2"
});

function submitContents(elClickedObj) {
	 oEditors.getById["ir1"].exec("UPDATE_CONTENTS_FIELD", []);
	 try {
		// elClickedObj.form.submit();
		return check();
	} catch(e) {
	}
}

</script>

</body>

</html>