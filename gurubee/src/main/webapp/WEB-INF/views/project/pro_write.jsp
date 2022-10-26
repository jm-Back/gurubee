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

.e_e {padding-right: 10px;	}
.delete_emp { cursor:pointer;  display:inline-block; border: 1px solid #fff; border-radius: 30px; color:#fff; font-weight:600; padding: 5px 10px; margin: 3px 3px; background: #01d6b7;}
.fa-padding {padding-left: 10px; padding-top: 4px;}

.end__btn__design {
	background: #01d6b7;
	font-weight: 600;
	color: #fff;
	border: none;
	padding: 10px 20px;
	
	
}


::-webkit-scrollbar { 
min-width: 15px; /*스크롤바의 너비*/
max-width: 15px;
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


</style>

<script type="text/javascript">
$(function(){
	//프로젝트 마스터 선택 버튼 (2)
	$(".att_master_btn").click(function(){
		$("#attend_Modal2").modal("show");
	});
	
	//마스터를 선택했을 때, input에 값 넣기
	$("#master").click(function(){
		var data = $("#selectMaster option:selected").attr("data-name");
		var data2 = $("#selectMaster option:selected").val();
		
		console.log(data);
		
		if($("#selectMaster option:selected").length > 1){
			alert("프로젝트 총괄자는 1명만 선택 가능합니다.");
			return false;
		}
		
		
		$("input[name=pro_master_name]").val(data);
		$("input[name=pro_master]").val(data2);
		$("#attend_Modal2").modal("hide");
		
	});
	
	
	$(".btnClose").click(function(){
		$("#attend_Modal1").modal("hide");
		$("#attend_Modal2").modal("hide");
	});
	
	
	
	//프로젝트 참여자 버튼 (1)
	$(".att_employee_btn").click(function(){
		$("#attend_Modal1").modal("show");
		
	});
	
	//참여자 값 선택해서 등록하기 눌렀을 때 input 에 값 넣기
	$("#employee").click(function(){

		var employeeList = $("#selectEmployee option").get();
		var data_e = $("#selectEmployee option:selected").val();

		$(".e_e").empty();
		for(var i=0; i<employeeList.length; i++){
			
				$(".e_e").append("<span class='delete_emp' name='employee_name' value = '" + employeeList[i].value + "' >" + employeeList[i].innerHTML + 
						" <i class='fa-solid fa-xmark fa-padding'></i><input type='hidden' name='pj_id' value = '" + employeeList[i].value + "'></span>");
		}
		
		$("#attend_Modal1").modal("hide");
	
	});
	
	//참여자 삭제 버튼
	$(".delete_emp").click(function(){
		console.log("하하");
		$(this).remove();
	});
});

//참여자 삭제 버튼 인덱스
$(document).on("click", ".delete_emp", (e) => {
  const index = $(e.target).index();
  
  $(".delete_emp").eq(index).remove();
  console.log(index);
  
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


</script>

<style type="text/css">
@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;700;900&display=swap');

.form-holder {
      
      min-height: 700px;
      max-width: 700px;
      flex-direction: column;
      justify-content: flex-start;
      align-items: center;
      text-align: center;
      margin: 0 auto;

}


.form-content .form-items {
	background-color: #f7f7f7;
    padding: 40px;
    display: inline-block;
    width: 100%;
    min-width: 540px;
    -webkit-border-radius: 30px;
    -moz-border-radius: 3px;
    border-radius: 10px;
    text-align: left;
    -webkit-transition: all 0.4s ease;
}

.form-content h3 {
    text-align: left;
    font-size: 28px;
    font-weight: 600;
    margin-bottom: 5px;
}

.form-content h3.form-title {
    margin-bottom: 30px;
}


.form-content p {
    text-align: left;
    font-size: 15px;
    font-weight: 300;
    line-height: 20px;
    margin-bottom: 30px;
    padding-top: 6px;
}

.form-content input[type=text],  .form-content input[type=email], .form-content select .form-content textarea  {
    width: 100%;
    padding: 9px 20px;
    text-align: left;
    border: 0;
    outline: 0;
    border-radius: 6px;
    background-color: #fff;
    font-size: 15px;
    font-weight: 300;
    color: #01d6b7;
    font-weight: 600;
    -webkit-transition: all 0.3s ease;
    transition: all 0.3s ease;
    margin-top: 16px;
}


.form-content input[type=date] {
	display: inline-block;
	width: 80%;
    padding: 9px 20px;
    text-align: left;
    border: 0;
    outline: 0;
    border-radius: 6px;
    background-color: #fff;
    font-size: 15px;
    font-weight: 600;
    color: #01d6b7;
    -webkit-transition: all 0.3s ease;
    transition: all 0.3s ease;
    margin-top: 16px;
}


.em_design {
    width: 100%;
    padding: 9px 20px;
    text-align: left;
    border: 0;
    outline: 0;
    border-radius: 6px;
    background-color: #fff;
    font-size: 15px;
    font-weight: 600;
    color: #8D8D8D;
    -webkit-transition: all 0.3s ease;
    transition: all 0.3s ease;
    margin-top: 16px;
}

.btn-primary:hover, .btn-primary:focus, .btn-primary:active{
    background-color: #01d6b7;
    outline: none !important;
    border: none !important;
    box-shadow: none;
}


.form-content textarea {
    width: 100%;
    padding: 8px 20px;
    border-radius: 6px;
    text-align: left;
    background-color: #fff;
    border: 0;
    font-size: 15px;
    font-weight: 600;
    color: #01d6b7;
    outline: none;
    resize: vertical;
    height: 120px;
    -webkit-transition: none;
    transition: none;
    margin-bottom: 14px;
    margin-top: 20px;
}

.form-content textarea:hover, .form-content textarea:focus {
    border: 0;
    background-color: #ebeff8;
    color: #01d6b7;
}

.design_date {
 	padding-left: 5px;
 	font-weight: 600;
}

.btn-outline-secondary__2:hover {
	background: aqua;
	font-weight: 600;
	
}

</style>

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
	
	
<div class="container">
	<div class="form-body">
		<div class="row">
			<div class="form-holder mb-5">
				<div class="form-content">
					<div class="form-items shadow rounded">
						<h3>새 프로젝트 등록</h3>
						<p>혁신적인 GURUBEE 프로젝트를 등록하세요.</p>
						<form class="requires-validation" name="projectForm" method="post">
							<div class="col-md-12">
								<input type="radio"  class="btn-check" name="pro_type" id="dept" value="부서 프로젝트" ${mode=='update' && dto.pro_type=='부서 프로젝트'?'checked="checked"':""} required>
                            <label class="btn btn-primary  shadow-sm " for="dept"> 부서 프로젝트 </label>
								<input type="radio"  class="btn-check" name="pro_type" id="personal" value="개인 프로젝트" ${mode=='update' && dto.pro_type=='개인 프로젝트'?'checked="checked"':""} required>
                            <label class="btn btn-primary  shadow-sm" for="personal"> 개인 프로젝트 </label>
								<input type="radio"  class="btn-check" name="pro_type" id="together" value="협업 프로젝트" ${mode=='update' && dto.pro_type=='협업 프로젝트'?'checked="checked"':""} required>
                            <label class="btn btn-primary  shadow-sm" for="together"> 협업 프로젝트 </label>
							</div>
							<div class="col-md-12">
								<div class="design_date"> 프로젝트 시작일&nbsp;    
								<input class="form-control shadow-sm" type="date" name="pro_sdate" placeholder="프로젝트 시작일" value="${dto.pro_sdate}">
								</div>
							</div>
							<div class="col-md-12">
								<div class="design_date"> 프로젝트 종료일&nbsp;   
								<input class="form-control shadow-sm" type="date" name="pro_edate" placeholder="프로젝트 종료일" value="${dto.pro_edate }">
								</div>
							</div>
							<div class="col-md-12">
								<input class="form-control shadow-sm" type="text" name="pro_name" placeholder="프로젝트명" value="${dto.pro_name }">
				<!-- 작성자 or 수정자 사번!! -->
								<input type="hidden" name="id" value="${dto.id_p}">
								<input type="hidden" name="pro_clear" >
								<input type="hidden" name="pro_code" value="${dto.pro_code}" >
							</div>

							<div class="col-md-12">
								<input class="form-control shadow-sm" type="text" name="pro_outline" placeholder="프로젝트 개요" value="${dto.pro_outline }">
							</div>
							<div class="col-md-12">
								<textarea class="form-control shadow-sm" name="pro_content" placeholder="프로젝트 내용" style="height: 120px;" >${dto.pro_content}</textarea>
							</div>
							<h3></h3>
						<div class="row">
							<div class="col-md-12" style="margin-bottom: -15px;">
								<button type="button" class="att_master_btn btn btn-primary mb-3 shadow-sm" ${mode=='update'?'disabled="disabled"': ''}>담당자 선택하기</button>
								<input class="shadow-sm" type="hidden" name="pro_master" value="${dto.pro_master}">
								<c:if test="${mode=='write'}">
									<button type="button" class="att_employee_btn btn btn-primary mb-3 shadow-sm" >참여자 선택하기</button>
								</c:if>
							</div>
							<div class="col-md-12">
								<input class="form-control shadow-sm" type="text" name="pro_master_name" readonly="readonly" placeholder="프로젝트 담당자" value="${dto.name_p}">
							</div>
						<c:if test="${mode=='write'}">
							<div class="col-md-12 ">
								<div class="form-control em_design shadow-sm" > 프로젝트 참여자
									<div class="mt-3 e_e" id="emlist">
									</div>
								</div>
							</div>
						</c:if>
						</div>
						<div class="form-button mt-5" style="text-align: center;">
							<button class="btn btn-primary end__btn__design shadow-sm" type="button" onclick="sendOk();">${mode=='update'?'수정완료':'등록하기'}</button>
							<button class="btn btn-primary end__btn__design shadow-sm" type="reset" onclick="location.href='${pageContext.request.contextPath}/project/list.do'">${mode=='update'?'수정취소':'등록취소'}</button>
						</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
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
							    			<option value="${dto.id_p}" data-id="${dto.id_p}" data-name="${dto.name_p}">${dto.name_p}(${dto.pos_name})/${dto.dep_name}</option>
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
