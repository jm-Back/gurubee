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
.form-select {
	border: 1px solid #999; border-radius: 4px; background-color: #fff;
	padding: 4px 5px; 
	font-family: "맑은 고딕", 나눔고딕, 돋움, sans-serif;
	font-size: 12px;
	vertical-align: baseline;
}
.form-select[readonly] { background-color:#f8f9fa; }

.left { text-align: left; padding-left: 7px; }
.center { text-align: center; }
.right { text-align: right; padding-right: 7px; }

</style>

<script type="text/javascript">
$(function(){
	//프로젝트 참여자 버튼 (1)
	$(".att_employee_btn").click(function(){
		$("#attend_Modal1").modal("show");
		
	});
	
	//프로젝트 마스터 선택 버튼 (2)
	$(".att_master_btn").click(function(){
		$("#attend_Modal2").modal("show");
	});
	
	$(".btnClose").click(function(){
		$("#attend_Modal1").modal("hide");
		$("#attend_Modal2").modal("hide");
	});
	
	
	
	//참여자 값 선택해서 등록하기 눌렀을 때 input 에 값 넣기
	$("#employee").click(function(){

		var employeeList = $("#selectEmployee option").get();
		console.log(employeeList);
		
		for(var i=0; i<employeeList.length; i++){
/* 			if( $("#selectEmployee option").indexof(employeeList[i].innerText) != -1){
				break;
			} */
			$(".e_e").append("<span class='delete_emp'>" + employeeList[i].innerHTML + "</span>");
		}
		
		$("#attend_Modal1").modal("hide");
	
	});
	
	//참여자 삭제 버튼
	$(".delete_emp").click(function(){
		$("#selectEmployee option").remove();
		
	});
	
	//마스터를 선택했을 때, input에 값 넣기
	$("#master").click(function(){
		var data = $("#selectMaster option:selected").attr("data-name");
		
		if($("#selectMaster option:selected").length > 1){
			alert("프로젝트 총괄자는 1명만 선택 가능합니다.");
			return false;
		}
		
		
		$("input[name=pro_master]").val(data);
		$("#attend_Modal2").modal("hide");
		
	});
	
});



function itemMove(pos) {
	const f = document.chooseForm_e;
	let source, target;
	
	if(pos === "left") { // right -> left
		source = f.itemRight;
		target = f.itemLeft;
	} else { // left -> right
		source = f.itemLeft;
		target = f.itemRight;
	}
	
	let len = source.length;
	for(let i=0; i<len; i++) {
		if( source.options[i].selected ) { // 선택된 항목만
			target[target.length] = 
				new Option(source.options[i].text, source.options[i].value);
			source[i] = null; // 삭제
			i--;
			len--;
		}
	}
}

//모든 option을 좌 또는 우로 이동
function itemAllMove(pos) {
	const f = document.chooseForm_e;
	let source, target;
	
	if(pos === "left") { // right -> left
		source = f.itemRight;
		target = f.itemLeft;
	} else { // left -> right
		source = f.itemLeft;
		target = f.itemRight;
	}
	
	let len = source.length;
	for(let i=0; i<len; i++) {
		target[target.length] = 
			new Option(source.options[0].text, source.options[0].value);
		source[0] = null; // 삭제
	}
}



//프로젝트 등록
function sendOk(){
	const f = document.projectForm;
	
	f.action = "${pageContext.request.contextPath}/project/${mode}_ok.do";
    f.submit();
	
}

//체크박스 하나만
function clickCheck(target){
	document.querySelectorAll("input[type=checkbox]").forEach((el) => {
		el.checked = false;
	});
	
	target.checked = true;
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
	</div>
			
	
	<!-- 프로젝트 등록화면 -->
<div class="container">
	<form name="projectForm">
		<div>프로젝트 등록</div>
		<table> 
			<tr>
				<td>프로젝트명</td>
				<td>
					<input type="text" name="pro_name">
					<input type="hidden" name="id" value="">
					<input type="hidden" name="pro_clear" >
				</td>
			</tr>
			<tr>
				<td>프로젝트 종류</td>
				<td>
					<input type="checkbox" onclick="clickCheck(this)" name="pro_type" value="DEPT"> DEPT
					<input type="checkbox" onclick="clickCheck(this)" name="pro_type" value="TEAM"> TEAM
					<input type="checkbox" onclick="clickCheck(this)" name="pro_type" value="TFT"> TFT
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
					<textarea name="pro_content"></textarea>
				</td>
			</tr>
			<tr>
				<td>프로젝트 총괄자</td>
				<td>
					<input type="text" name="pro_master" >
					<button type="button" class="att_master_btn">선택하기</button>
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
		
		<div>참여자 등록</div>
		<table>
			<tr>
				<td>참여자 선택하기</td>
				<td>
					<input type="hidden" name="pj_id">
					<button type="button" class="att_employee_btn">선택하기</button>
				</td>
				<td class="e_e">
				</td>
			</tr>
		</table>
	</form>
</div>	
	
	
	<div class="container" style="text-align: center;">
		<button type="button" onclick="sendOk();">등록하기</button>
		<button type="button">취소하기</button>
	</div>
	
	

	
	<!-- 참여자 선택 모달창 -->
<div class="modal fade" id="attend_Modal1" tabindex="-1" 
		data-bs-backdrop="static" data-bs-keyboard="false"
		aria-labelledby="myDialogModalLabel1" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="attend_ModalLabel1">프로젝트 참여자 목록</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			
			<div class="modal-body">
        		<p>프로젝트 참여자를 선택하세요.<p>
					<form name="chooseForm_e" method="post">
						<table class="table form-table">
							<tr>
							    <td width="150"><span>사원전체</span></td>
							    <td width="100">&nbsp;</td>
							    <td width="150"><span>참여자목록</span></td>
							</tr>
							
							<tr>
							    <td class="left">
							        <select id="total" name="itemLeft" multiple="multiple" class="form-select" style="width:150px; height:120px;">
							    		<c:forEach var="dto" items="${list_e}" varStatus="status">
							    			<option value="${dto.id_p}" data-name="${dto.name_p}">${dto.name_p}(${dto.pos_name})/${dto.dep_name}</option>
							    		</c:forEach>
							    	</select>
							    </td>
							    <td class="center">
								    <button type="button" class="btn" onclick="itemMove('right');" style="display:block; width:80px;"> &gt; </button>
								    <button type="button" class="btn" onclick="itemAllMove('right');" style="display:block;width:80px;"> &gt;&gt; </button>
								    <button type="button" class="btn" onclick="itemMove('left');" style="display:block;width:80px;"> &lt; </button>
								    <button type="button" class="btn" onclick="itemAllMove('left');" style="display:block;width:80px;"> &lt;&lt; </button>
							    </td>
							    <td class="right">
							        <select id="selectEmployee" name="itemRight" multiple="multiple" class="form-select" style="width:150px; height:120px;">
							   
							        </select>
							    </td>
							</tr>
						</table>
					</form>	
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary btnClose">닫기</button>
				<button id="employee"  type="button" class="btn btn-primary" >등록하기</button>
			</div>
		</div>
	</div>
</div>


	<!-- 마스터 선택 모달창 -->
<div class="modal fade" id="attend_Modal2" tabindex="-1" 
		data-bs-backdrop="static" data-bs-keyboard="false"
		aria-labelledby="myDialogModalLabel2" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="attend_ModalLabel2">프로젝트 총괄자 목록</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			<div class="modal-body">
        		<p>프로젝트 총괄자를 선택하세요.<p>
					<form name="chooseForm_m" method="post">
						<table class="table form-table">
							<tr>
							    <td width="150"><span>총괄자 목록</span></td>
							</tr>
							<tr>
							    <td class="left">
							        <select id="selectMaster" name="itemLeft" multiple="multiple" class="form-select" style="width:100%; height:120px;">
							    		<c:forEach var="dto" items="${list_m}" varStatus="status">
							    			<option value="${dto.id_p}" data-name="${dto.name_p}">${dto.name_p}(${dto.pos_name})/${dto.dep_name}</option>
							    		</c:forEach>
							    	</select>
							    </td>
							</tr>
						</table>
					</form>	
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary btnClose">닫기</button>
				<button id="master" type="button" class="btn btn-primary">등록하기</button>
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
