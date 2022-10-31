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

<style>
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


.btn_projectAdd {
	border-radius: 60px;
	font-size: 23px;
	font-weight:600;
	cursor: pointer;
	color: white;
	background-color: #01d6b7;
	opacity: 80%;
	width: 300px;
	height: 80px;
	border: none;

};

.body-container {
	max-width: 840px;
}

.project__title {
	font-size: 23px;
	font-weight:600;
	color: white;
	background-color: #01d6b7;
	opacity: 80%;
}

.textDate {
	cursor: pointer;  display: block;
}
.scheduleSubject {
	display:block;
	max-width: 100px;
	margin:1.5px 0;
	font-size:13px;
	color: #555;
	cursor: pointer;
	background: #f8f9fa;
	white-space:nowrap; overflow:hidden; text-overflow:ellipsis;
}

.scheduleMore {
   display:block;
   width:100px;
   margin:0 0 1.5px;
   font-size:13px;
   color:#555;
   cursor: pointer;
   text-align:right;
}

.daySubject {
	cursor: pointer;
}
.daySubject:hover {
	color: #F28011;
}

.title {
	font-size: 24px;
	display: inline-block;
	padding-left: 15px;
}

.size__ {
	width: 400px;
	background-color: #01d6b7;
	color: #fff;	
	border-radius: 10px;
}

.container__2 {
	border: 1px solid #eee;
	border-radius: 10px;
	width: 100%;
	padding-top: 30px;
	padding-bottom: 30px;
	padding-left: 10px;
}

.list__title {
	display: inline-block;
	text-align: center;
	font-size: 19px;
	padding-top: 5px;
	padding-bottom: 5px;
	font-weight: 600;
	border-right: 1px solid #eee;

}


.container__list {
	margin-left:15px;
	
}

.center_item {
	margin-top: 7px;
	text-align: center;
	height: 40px;
	border: 1px solid #eee;
	border-radius: 5px;
	font-size: 16px;
}

.list__item {
	width:  60px;
	margin-left: 27px;
}

.list__item2 {
	width: 200px;
	margin-right: 8px;
}

.list__item3 {
	margin-right: 2px;
}

.list__item6 {
	font-size: 20px;
}

.fa-trash:hover {
	cursor: pointer;
	opacity: 70%;
	color: red;
}

.fa-pencil:hover {
	cursor: pointer;
	opacity: 70%;
	color: blue;
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
		beforeSend:function(jqXHR) {
			jqXHR.setRequestHeader("AJAX", true);
		},
		error:function(jqXHR) {
			if(jqXHR.status === 403) {
				login();
				return false;
			} else if(jqXHR.status === 400) {
				alert("요청 처리가 실패 했습니다.");
				return false;
			}
	    	
			console.log(jqXHR.responseText);
		}
	});
}

$(function(){
    // 실행과 동시에 처음 탭에 출력
    let url = "${pageContext.request.contextPath}/schedule/month_list.do";
    let query = "";
    
    schedule(url, query, "#nav-1");
});

//스케쥴 출력하기
function schedule(url, query, selector) {
	const fn = function(data){
		$(selector).html(data);
		today();
	};
	
	ajaxFun(url, "get", query, "html", fn);	
}


function today() {
	let date = "${today}";
	$(".textDate").each(function (i) {
        let s = $(this).attr("data-date");
        if(s === date) {
        	$(this).parent().css("background", "#FFFFE4");
        }
    });
}


//월별 - 월을 변경하는 경우
function changeMonth(year, month) {
	let url = "${pageContext.request.contextPath}/schedule/month_list.do";
	let query = "year="+year+"&month="+month;
	
	schedule(url, query, "#nav-1");
}

//날짜 클릭, 일정 등록
$(function(){
	$("body").on("click", "#largeCalendar .textDate", function(){
		//폼 
		$("form[name=scheduleForm]").each(function(){
			this.reset();
		});
		
		$("#form-repeat_cycle").hide();
		$("#form-allDay").prop("checked", true);
		$("#form-allDay").removeAttr("disabled");
		$("#form-stime").hide();
		$("#form-etime").hide();
		$("#form-eday").closest("tr").show();
		
		let date = $(this).attr("data-date");
		date = date.substr(0,4) + "-" + date.substr(4,2) + "-" + date.substr(6,2);
		
		$("form[name=scheduleForm] input[name=sch_sdate]").val(date);
		$("form[name=scheduleForm] input[name=sch_edate]").val(date);
		
		$("#myDialogModalLabel").html("스케쥴 등록");
		$("#btnScheduleSendOk").attr("data-mode", "insert"); //수정은 따로
		$("#btnScheduleSendOk").html(" 등록 완료 ");
		$("#btnScheduleSendCancel").html(" 등록 취소 ");
		
		$("#myDialogModal").modal("show");
	});
	
});

//수정 버튼
$(function(){
	$("body").on("click", ".fa-pencil", function(){
		let date = $(this).attr("data-date");
		let num = $(this).attr("data-num");
		
		let sch_name = $(".container__list input[name=sch_name]").val();
		let sc_code = $(".container__list input[name=sc_code]").val();
		let allDay = $(".container__list input[name=allDay]").val();
		let sch_stime = $(".container__list input[name=sch_stime]").val();
		let sch_sdate = $(".container__list input[name=sch_sdate]").val();
		let sch_edate = $(".container__list input[name=sch_edate]").val();
		if(! sch_edate ) sch_edate = sch_sdate;
		let sch_etime = $(".container__list input[name=sch_etime]").val();
		let sch_repeat = $(".container__list input[name=sch_repeat]").val();
		let sch_repeat_c = $(".container__list input[name=sch_repeat_c]").val();
		let sch_content = $(".container__list input[name=sch_content]").val();
		
		$("#form-num").val(num);
		$("#form-subject").val(sch_name);
		$("#form-color").val(sc_code);
		if(allDay === "1") {
			$("#form-allDay").prop("checked", true);
		} else {
			$("#form-allDay").prop("checked", false);
		}
		$("#form-sday").val(sch_sdate);
		$("#form-stime").val(sch_stime);
		$("#form-eday").val(sch_edate);
		$("#form-etime").val(sch_etime);
		if(sch_stime) {
			$("#form-stime").show();
			$("#form-etime").show()
		} else {
			$("#form-stime").hide();
			$("#form-etime").hide()
		}	
		
		$("#form-repeat").val(sch_repeat);
		$("#form-repeat_cycle").val(sch_repeat_c);
		if(sch_repeat === "1") {
			$("#form-repeat_cycle").show();
			$("#form-eday").closest("tr").hide();
		} else {
			$("#form-repeat_cycle").val("");
			$("#form-repeat_cycle").hide();
			$("#form-eday").closest("tr").show();
		}
		
		$("#form-memo").val(sch_content);
		
		$("#myDialogModalLabel").html("일정 수정하기");
		$("#btnScheduleSendOk").attr("data-mode", "update");
		$("#btnScheduleSendOk").attr("data-num", num);
		$("#btnScheduleSendOk").attr("data-date", date);
		
		$("#btnScheduleSendOk").html(" 수정 완료 ");
		$("#btnScheduleSendCancel").html(" 수정 취소 ");
		
		$("#myDialogModal").modal("show");
		
	});
});


//등록 완료 버튼
$(function(){
	$("#btnScheduleSendOk").click(function(){
		if(! check()){
			return false;
		}
		
		let mode = $("#btnScheduleSendOk").attr("data-mode");
		let query = $("form[name=scheduleForm]").serialize();
		let url = "${pageContext.request.contextPath}/schedule/"+mode+".do";
		
		const fn = function(data){
			let state = data.state;
			if(state ==="true"){
				if(mode === "insert"){
					let dd = $("#form-sday").val().split("-");
					let y = dd[0];
					let m = dd[1];
					if(m.substr(0,1) ==="0" ) {
						m = m.substr(1,1);
					}
					let url = "${pageContext.request.contextPath}/schedule/month_list.do";
					let query = "year="+y+"&month="+m;
					schedule(url, query, "#nav-1");
					
				}else if(mode==="update"){
					let num = $("#btnScheduleSendOk").attr("data-num");
					let date = $("#btnScheduleSendOk").attr("data-date");
					
					let url = "${pageContext.request.contextPath}/schedule/month_list.do";
					let query = "date="+date+"&num="+num;
					
					schedule(url, query, "#nav-1");
					
				}
			}
			
			$("#myDialogModal").modal("hide");
			
		};
		
		ajaxFun(url, "post", query, "json", fn);	
		
	});
});

/// 모달 취소
$(function(){
	$("#btnScheduleSendCancel").click(function(){
		$("#myDialogModal").modal("hide");
	});
});

//종일 일정 버튼 눌렀을 때 
$(function(){
	$("body").on("click", "#form-allDay", function(){
		
		if(this.checked) {
			$("#form-stime").val("").hide();
			$("#form-etime").val("").hide();
		} else if(this.checked === false && $("#form-repeat").val() === "0"){
			$("#form-stime").val("00:00").show();
			$("#form-etime").val("00:00").show();
		}
	});

	$("body").on("change", "#form-sday", function(){
		$("#form-eday").val($("#form-sday").val());
	});

	$("body").on("change", "#form-repeat", function(){
		if($(this).val() === "0") {
			$("#form-repeat_cycle").val("").hide();
			
			$("#form-allDay").prop("checked", true);
			$("#form-allDay").removeAttr("disabled");
			
			$("#form-eday").val($("#form-sday").val());
			$("#form-eday").closest("tr").show();
		} else {
			$("#form-repeat_cycle").show();
			
			$("#form-allDay").prop("checked", true);
			$("#form-allDay").attr("disabled","disabled");

			$("#form-stime").val("").hide();
			$("#form-eday").val("");
			$("#form-etime").val("");
			$("#form-eday").closest("tr").hide();
		}
	});
});

//등록내용 유효성 검사
function check() {
	if(! $("#form-subject").val()) {
		$("#form-subject").focus();
		return false;
	}

	if(! $("#form-sday").val()) {
		$("#form-sday").focus();
		return false;
	}

	if($("#form-eday").val()) {
		let s1 = $("#form-sday").val().replace("-", "");
		let s2 = $("#form-eday").val().replace("-", "");
		if(s1 > s2) {
			$("#form-sday").focus();
			return false;
		}
	}
	
	if($("#form-etime").val()) {
		let s1 = $("#form-stime").val().replace(":", "");
		let s2 = $("#form-etime").val().replace(":", "");
		if(s1 > s2) {
			$("#form-stime").focus();
			return false;
		}
	}	
	
	if($("#form-repeat").val() != "0" && ! /^(\d){1,2}$/g.test($("#form-repeat_cycle").val())) {
		$("#form-repeat_cycle").focus();
		return false;
	}
	
	if($("#form-repeat").val() != "0" && $("#form-repeat_cycle").val()<1) {
		$("#form-repeat_cycle").focus();
		return false;
	}
	
	return true;
}

//월별 - 스케쥴 제목을 클릭한 경우
$(function(){
	$("body").on("click", ".scheduleSubject", function(){
		let date = $(this).attr("data-date");
		let num = $(this).attr("data-num");
		
		let url = "${pageContext.request.contextPath}/schedule/detail.do"
		let query = "date="+date+"&num="+num;
		let selector = "#here";
		
		const fn = function(data){
			$(selector).html(data);
		};
		
		ajaxFun(url, "get", query, "html", fn);
		
	});
});

//삭제 버튼
$(function(){
	$("body").on("click", ".fa-trash", function(){
		if(! confirm("일정을 삭제 하시겠습니까 ? ")) {
			return false;
		}
		
		let date = $(this).attr("data-date");
		let sch_num = $(this).attr("data-num");
		let url = "${pageContext.request.contextPath}/schedule/delete.do";

		let query = "num="+sch_num;
		
		const fn = function(data){
			if(data.state === "true"){
				let url = "${pageContext.request.contextPath}/schedule/main.do";
				let query = "date="+date+"&num="+sch_num;
				schedule(url, query, "#nav-1");
			}
		};
		
		ajaxFun(url, "post", query, "json", fn);

	});
});


</script>


</head>
<body>
	<main>
		<!-- 메인 화면 -->
		<div class="container py-4 ">
			<header class="pb-3 mb-4 border-bottom ">
				<jsp:include page="/WEB-INF/views/layout/header.jsp" />
				<jsp:include page="/WEB-INF/views/layout/sidebar.jsp" />
			</header>
		</div>
	
		<!-- 프로젝트 메인 등록, 내용 -->
	<div class="container" >
		<div class="body-container">
			<div class="project__title shadow p-3 pt-3 rounded">
				<div><i class="bi bi-calendar2-event"></i> 일정관리 </div>
			</div>
			
			<div class="body-main pt-3">
				<div class="tab-content" id="nav-tabContent">
					<div class="tab-pane fade show active" id="nav-1" role="tabpanel" aria-labelledby="nav-tab-1"></div>
					<div class="tab-pane fade" id="nav-2" role="tabpanel" aria-labelledby="nav-tab-2"></div>
					<div class="tab-pane fade" id="nav-3" role="tabpanel" aria-labelledby="nav-tab-2"></div>
				</div>
			</div>
			
		</div>
	</div>
	
	
	
<!--등록 Modal -->
<div class="modal fade" id="myDialogModal"
		data-bs-backdrop="static" data-bs-keyboard="false" 
		tabindex="-1" aria-labelledby="imyDialogModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="myDialogModalLabel">일정 등록</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			<div class="modal-body pt-0 pb-0">
        		<form name="scheduleForm">
        			<table class="table">
						<tr>
							<td class="col-2">일정 제목</td>
							<td>
								<input type="text" name="sch_name" id="form-subject" class="form-control">
								<small class="form-control-plaintext help-block">
									* 일정 제목은 필수 입니다.
								</small>
							</td>
						</tr>
						
						<tr>
							<td class="col-2">일정분류</td>
							<td>
								<select name="sc_code" id="form-color" class="form-select">
									<option value="100">회사 일정</option>
									<option value="200">부서 일정</option>
									<option value="300">개인 일정</option>
								</select>
							</td>
						</tr>
						
						<tr>
							<td class="col-2">종일일정</td>
							<td>
	                            <div class="form-check form-control-plaintext">
	                                <input class="form-check-input" type="checkbox" name="allDay" id="form-allDay" value="1">
	                                <label class="form-check-label" for="form-allDay">하루종일</label>
	                            </div>
							</td>
						</tr>

						<tr>
							<td class="col-2">시작일자</td>
							<td>
								<div class="row">
									<div class="col col-sm-4 pe-1">
										<input type="date" name="sch_sdate" id="form-sday" class="form-control">
									</div>
									<div class="col col-sm-3">
										<input type="time" name="sch_stime" id="form-stime" class="form-control" style="display: none;">
									</div>
								</div>
								<small class="form-control-plaintext help-block">
									* 시작날짜는 필수입니다.
								</small>
							</td>
						</tr>

						<tr>
							<td class="col-2">종료일자</td>
							<td>
								<div class="row">
									<div class="col col-sm-4 pe-1">
										<input type="date" name="sch_edate" id="form-eday" class="form-control">
									</div>
									<div class="col col-sm-3">
										<input type="time" name="sch_etime" id="form-etime" class="form-control" style="display: none;">
									</div>
								</div>
								<small class="form-control-plaintext help-block">
									종료일자는 선택사항이며, 시작일자보다 작을 수 없습니다.
								</small>
							</td>
						</tr>

						<tr>
							<td class="col-2">일정반복</td>
							<td>
								<div class="row">
									<div class="col col-sm-4 pe-1">
										<select name="sch_repeat" id="form-repeat" class="form-select">
											<option value="0">반복안함</option>
											<option value="1">년반복</option>
										</select>
									</div>
									<div class="col col-sm-3">
										<input type="text" name="sch_repeat_c" id="form-repeat_cycle" class="form-control">
									</div>
								</div>
							</td>
						</tr>
						
						<tr>
							<td class="col-2">메 모</td>
							<td>
								<textarea name="sch_content" id="form-memo" class="form-control" style="height: 70px;"></textarea>
							</td>
						</tr>
						
						<tr>
							<td colspan="2" class="text-center" style="border-bottom: none;">
								<input type="hidden" name="num"id="form-num"  value="0">
								<button type="button" class="btn btn-dark" id="btnScheduleSendOk"> 등록 완료 </button>
								<button type="button" class="btn btn-light" id="btnScheduleSendCancel"> 등록 취소 </button>
							</td>
						</tr>
						
        			</table>
        		</form>
			</div>
		</div>
	</div>
</div>	



<div id="here">
</div>


	</main>
	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp" />
		<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>
	</footer>

</body>

</html>
