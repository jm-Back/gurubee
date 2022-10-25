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
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/se2/js/service/HuskyEZCreator.js" charset="utf-8"></script>

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
  	
	let edoc = $(".edocSelect option:selected").val();
	let content = f.content.value.trim();
	let temp = 1; // 임시구분
	
	alert(content);
	alert(edoc);
	
  
  	if(! edoc) {
      	alert("문서구분을 선택하세요. ");
  	    return false;
 	}
  
 	if(!content || content==="<p><br></p>") {
    	alert("상세내용을 입력하세요. ");
    	$("#content").focus();
    	return false;
  	}
 	
 	if(id_apper1==="" && id_apper2==="" && id_apper3==="" && id_apper4===""){
 		alert("수신자는 1명 이상 선택해야합니다.");
 		return false;
 	}
 	
  
 	f.action = "${pageContext.request.contextPath}/edoc/write_ok.do";
  
  	f.submit();
}

// 문서 임시저장
function saveOk() {
	
}

$(function(){
	// 작성일자
	$('input[name=date]').attr('value', year+"-"+month+"-"+day);
	
	
	// 사원검색 모달 
    $(".empSearch").on("click", function() {
    	let pos_code = $(this).attr("data-posCode");
    	
    	let url = "${pageContext.request.contextPath}/edoc/write_searchEmp.do"
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
    	}
        
        ajaxFun(url, "post", query, "text", fn);
	});
    
   // 모달에서 select 된 사원
    $("body").on("click", "#btnEmpSelect", function() {
    	let $ele = $(this).closest(".modal-content").find("select");
		let id_apper = $ele.val();
    	let name_apper = $ele.find("option:selected").attr("data-name"); 
    	let pos_code = $ele.attr("data-posCode");
    	
    	alert(id_apper+": "+name_apper+": "+pos_code);
    	if(pos_code === "3") {
    		$("#empId1").val(id_apper);
			$("#empSearch1").val(name_apper);
    	} else if (pos_code === "4") {
    		$("#empId2").val(id_apper);
			$("#empSearch2").val(name_apper);
    	} else if (pos_code === "5") {
    		$("#empId3").val(id_apper);
			$("#empSearch3").val(name_apper);
    	} else if (pos_code === "6") {
    		$("#empId4").val(id_apper);
			$("#empSearch4").val(name_apper);
    	}
	});
   
   /*
   // 수신자 사원 선택 btnEmpSelect
	$("#btnEmpSelect").click(function() {
		alert("선택햇삼");
		let id_apper = $(this).val();
    	let name_apper = $(this).find("option:selected").attr("data-name"); 
    	let pos_code = $(this).find("option:selected").attr("data-posCode");
    	$("body").on("click", ".btnEmpSelect", function() {
		});
    	if(pos_code === "3") {
    		$("#empId1").attr("value", id_apper);
			$("#empSearch1").attr("value", name_apper);
    	} else if (pos_code === "4") {
    		$("#empId2").attr("value", id_apper);
			$("#empSearch2").attr("value", name_apper);
    	} else if (pos_code === "5") {
    		$("#empId3").attr("value", id_apper);
			$("#empSearch3").attr("value", name_apper);
    	} else if (pos_code === "6") {
    		$("#empId4").attr("value", id_apper);
			$("#empSearch4").attr("value", name_apper);
    	}
	});
   */
   
   // 수신자 사원 삭제 btnEmpCancle
   	$("#btnEmpCancle").click(function() {
   		alert("취소햇삼");
   		let pos_code = $(this).find("option:selected").attr("data-posCode");
   		alert(pos_code);
   		if(pos_code === "3") {
    		$("#empId1").attr("value", '');
			$("#empSearch1").attr("value", '');
    	} else if (pos_code === "4") {
    		$("#empId2").attr("value", '');
			$("#empSearch2").attr("value", '');
    	} else if (pos_code === "5") {
    		$("#empId3").attr("value", '');
			$("#empSearch3").attr("value", '');
    	} else if (pos_code === "6") {
    		$("#empId4").attr("value", '');
			$("#empSearch4").attr("value", '');
    	}
	});
   
   
    
});

function clickEmpSearch(object) {
	let pos_code = object.attr("data-posCode");
	alert(pos_code);
	
	let url = "${pageContext.request.contextPath}/edoc/write_searchEmp.do"
	let query = "pos_code="+encodeURIComponent(pos_code);
	
	const fn = function(empList) {
		$("#empList").html(empList);
		$("#empListModal").modal("show");
	}
	
    ajaxFun(url, "post", query, "text", fn);
}


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
			
		<div>
			<form action="" method="post" name="writeForm" enctype="multipart/form-data">
				<table class="table table-border table-form">
					<tr>
						<th class="fs-6">문서구분</th>
						<td>
							<!-- 
							<div class="btn-group-vertical" role="group" aria-label="Vertical button group">	
							<button type="button" class="btn btn-dark" id="">휴가신청서</button>
							<button type="button" class="btn btn-dark" id="">DB접근권한신청서</button>
							<button type="button" class="btn btn-dark" id="">구매요청의뢰서</button>
							<button type="button" class="btn btn-dark" id="">재택근무신청서</button>
							<button type="button" class="btn btn-dark" id="">법인카드지출결의서</button>
							<button type="button" class="btn btn-dark" id="">출장신청서</button>
							</div>
							 -->
							<select class="form-select" aria-label="Default select example" name="edocSelect" id="edocSelect"
								style="width: 50%;">
								<option selected>문서구분 선택</option>
								<option value="휴가신청서">휴가신청서 - 각종 휴가 신청서 사용 양식</option>
								<option value="DB접근권한신청서">DB접근권한신청서 - DB 계정 신청시 사용 양식</option>
								<option value="구매요청의뢰서">구매요청의뢰서 - 회사 비품 구매 신청서 사용 양식</option>
								<option value="재택근무신청서">재택근무신청서 - 재택근무 신청 양식</option>
								<option value="법인카드지출결의서">법인카드지출결의서 - 법인카드 지출결의를 위한 양식</option>
								<option value="출장신청서">출장신청서 - 출장 전 품의 결재시 사용 양식</option>
							</select>
						</td>
					</tr>
					
					<tr>
						<th class="fs-6">작성자</th>
						<td class="fs-6">
							<input type="text" name="name" value="${sessionScope.member.name}" class="form-control" 
								readonly="readonly" style="width: 27%;">
						</td>
					</tr>
					
					<tr>
						<th class="fs-6">작성일자</th>
						<td>
							<input type="text" name="date" value=" " class="form-control" 
								readonly="readonly" style="width: 27%;">
						</td>
					</tr>
					
					<tr>
						<th class="fs-6">수신자</th>
						<td>
							<div>
								<div>
									<p><input type="text" id="empSearch1" name="empSearch" class="form-control" data-posCode="3"
                                	style="width: 27%;" placeholder="대리 - 사원 검색" readonly="readonly">
                                	<input type="hidden" id="empId1" name="empId">
                                	</p>
                                	
                                	<p><input type="text" id="empSearch2" name="empSearch" class="form-control" data-posCode="4"
                                	style="width: 27%;" placeholder="과장 - 사원 검색" readonly="readonly">
                                	<input type="hidden" id="empId2" name="empId">
                                	</p>
                                	 
                                	<p><input type="text" id="empSearch3" name=empSearch class="form-control" data-posCode="5"
                                	style="width: 27%;" placeholder="차장 - 사원 검색" readonly="readonly"> 	
                                	<input type="hidden" id="empId2" name="empId">
                                	</p> 
                                	
                                	<p><input type="text" id="empSearch4" name="empSearch" class="form-control" data-posCode="6"
                                	style="width: 27%;" placeholder="부장 - 사원 검색" readonly="readonly">
                                	<input type="hidden" id="empId2" name="empId">
                        		</div>
							</div>
						</td>
					</tr>
			
					<tr>
						<th class="fs-6">상세내용</th>
						<td>
							<textarea name="content" id="ir1" class="form-control"style="width: 90%; height:500px; ">${formdto.doc_form}</textarea>
							
						</td>
					</tr>
					
					<tr>
						<th class="fs-6">첨부파일</th>
						<td> 
							<div class="mb-3">
  							<input class="form-control" type="file" id="formFileMultiple" multiple style="width: 50%;">
							</div>
						</td>
					</tr>
				</table>
				
				<div style="text-align: right;">
					<button type="button" onclick="saveOk();" class="btn btn-secondary" style="font-size: 17px;">임시작성</button>
				</div>
				
				<div style="text-align: center;">
					<button type="button" onclick="sendOk();" class="btn btn-success" style="font-size: 20px;">결제요청</button>
				</div>
				<input type="hidden" name="id" value="${sessionScope.member.id}" class="form-control">
			</form>
		</div>
	
		</div>

		<!-- empListModal: 사원검색의 사원리스트 모달 start -->
				<div class="modal" id="empListModal" tabindex="-1">
  					<div class="modal-dialog">
    					<div class="modal-content">
      						<div class="modal-header">
        						<h5 class="modal-title">사원 검색</h5>
        						<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      						</div>
     						 <div class="modal-body">
        							<p>수신자 사원 리스트</p>
        							<div id="empList">  </div>
      						</div>
      						<div class="modal-footer">
        						<button type="button" class="btn btn-secondary" id="btnEmpSelect" data-bs-dismiss="modal" aria-label="Close">선택하기</button>
       							<button type="button" class="btn btn-primary" id="btnEmpCancle" data-bs-dismiss="modal">취소하기</button>
      						</div>
    					</div>
  					</div>
				</div>

		<!-- 사원검색 모달 end -->
		
	</main>

	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp" />
	</footer>

	<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/se2/js/service/HuskyEZCreator.js" charset="utf-8"></script>
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