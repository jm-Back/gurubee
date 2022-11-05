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
	href="${pageContext.request.contextPath}/resources/css/calendar.css"
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


.bd-placeholder-img {
	font-size: 1.125rem;
	text-anchor: middle;
	-webkit-user-select: none;
	-moz-user-select: none;
	user-select: none;
}

@media ( min-width : 768px) {
	.bd-placeholder-img-lg {
		font-size: 3.5rem;
	}
}

.b-example-divider {
	height: 3rem;
	border: solid rgba(0, 0, 0, .15);
	border-width: 1px 0;
	box-shadow: inset 0 .5em 1.5em rgba(0, 0, 0, .1), inset 0 .125em .5em
		rgba(0, 0, 0, .15);
}

.b-example-vr {
	flex-shrink: 0;
	width: 1.5rem;
	height: 100vh;
}

.bi {
	vertical-align: -.125em;
	fill: currentColor;
}

.nav-scroller {
	position: relative;
	z-index: 2;
	height: 2.75rem;
	overflow-y: hidden;
}

.nav-scroller .nav {
	display: flex;
	flex-wrap: nowrap;
	padding-bottom: 1rem;
	margin-top: -1px;
	overflow-x: auto;
	text-align: center;
	white-space: nowrap;
	-webkit-overflow-scrolling: touch;
}

#boardmenu ul li {
	list-style: none;
	float: left;
	margin-right: 10px;
	text-align: center;
}

#boardmenu ul:nth-child(4) {
	list-style: none;
	float: right;
	color: #01d6b7
}

#boardmenu ul li a:hover {
	background: #01d6b7;
	color: #fff;
}

#boardlist {
	width: 100%;
	height: 80%;
	overflow: auto;
}

#notelist {
	table-layout: fixed;
}

#notelist tr td {
	text-overflow: ellipsis;
	overflow: hidden;
	white-space: nowrap;
}

#infomenu div a:hover {
	color: #01d6b7;
}

.header-top div a:hover {
	color: #01d6b7;
}

#nav-item a:hover {
	color: #01d6b7;
}

.main-div {
	width: 1850px;
	height: 1200px;
	margin-left: auto;
	margin-right: auto;
	padding-right: 23px;
	justify-content: center;
}

.profile {
	width: 160px;
	height: 160px;
	object-fit: cover;
	border-radius: 100%;
	border: 7px solid #01d6b7;
	padding: 4px;
}

.box_photo {
	overflow: visible;
	text-align: center;
	width: 100%;
	height: 200px;
	padding-bottom: 10px;
	padding-top: 30px;
}

.form-control[readonly] { background-color:#fff; }

textarea.form-control { height: 170px; resize : none; }

.form-select {
	border: 1px solid #999; border-radius: 4px; background-color: #fff;
	padding: 4px 5px; 
	font-family: "맑은 고딕", 나눔고딕, 돋움, sans-serif;
	vertical-align: baseline;
}
.form-select[readonly] { background-color:#fff; }

.div-total {
	
}

.div-title {
	font-weight: 600;
	font-size: 21px;
	font: bold;
	padding-top: 15px;
}

.div-cal {
	height: 300px;
}

.div-edoc {
	margin-bottom: 10px;
	height: 400px;
	margin-top: -30px;
}

.div-project {
	width: 700px;
	height: 450px;
	margin-top: 30px;
	margin-left: 30px;
}

.div-board {
	margin-left: 10px;
	width: 500px;
	height: 1050px;
}	

.attbtn {
	background-color: #01d6b7;
	width: 90px;
	height: 50px;
	font-size: 40px;
}

.div-tabmenu {
	padding-top: 29px;
}

ul.tabs {
	margin: 0px;
	padding: 0px;
	list-style: none;
}

ul.tabs li {
	background: none;
	color: #222;
	display: inline-block;
	padding: 10px 15px;
	cursor: pointer;
}

ul.tabs li.current {
	background: #ededed;
	color: #222;
}

.tab-content {
	display: none;
	background: #ededed;
	padding: 15px;
}

.tab-content.current {
	display: inherit;
}

.myboard {
	width: 100%;	
	
}

.myedoc {
	margin-top: -10px;
	width: 100%;	
	
}

.mypro {
	width: 100%;
	margin-top: -10px;
}

.shadow {
	box-shadow: 3px 3px 3px;
}

.div-container {
	justify-content: center;
}

.profile__size {
	height: 448px;
}

.doc__btn {
	width: 62%;
	font-size: 19px;
	color: #fff;
	font-weight: 600;
	text-align: center;
	height: 100px;
	border-radius: 15px;
	margin-right: 10px;
	margin-left: -23px;
	background: #01d6b7;
	opacity: 80%;
	padding-top: 33px;
}

.doc__btn2 {
	width: 62%;
	font-size: 19px;
	color: #fff;
	font-weight: 600;
	text-align: center;
	height: 100px;
	border-radius: 15px;
	margin-right: 1px;
	vertical-align: middle;
	background: #01d6b7;
	opacity: 80%;
	padding-top: 33px;
}

.doc__btn:hover, .doc__btn2:hover {
	opacity: 100%;
	cursor: pointer;
}

.size__btn {
	width: 100%;
	display: flex;

}

/*결제문서 갯수 카운트 영역*/
.doc__count {
	margin-top: 12px;
	width: 108%;
	border:  1px solid #eee;
	border-radius: 10px;
	margin-left: -20px;
	height: 250px;
	
}

.doc__count__title {
	font-size: 20px;
	padding-top: 10px;
	padding-left: 15px;
	font-weight: 600;
}

.doc__count__content {
	padding-top: 20px;
	padding-left: 15px;
	font-size: 18px;
	padding-bottom: 10px;
}

.input__style {
	border: none;
	font-size: 30px;
	color: orange;
	font-weight: 600;
}

/*a 태그 수정하기 */
.a__style {
	color: #fff;
}

.attIn-btn {
	width: 120px;
	height: 50px;
	border-radius: 10px;
	border: 1px solid #eee;
	background: #fff;
	font-size: 17px;
	font-weight: 600;
}

.attOut-btn {
	width: 120px;
	height: 50px;
	border-radius: 10px;
	border: 1px solid #eee;
	background: #fff;
	font-size: 17px;
	font-weight: 600;
}

.sch__btn {
	margin-left: -20px;
	background: #01d6b7;
	width: 930px;
	height: 80px;
	margin-top: 100px;
	font-size: 23px;
	vertical-align: middle;
	border-radius: 10px;
	color: #fff;
	padding-top: 20px;
	padding-left: 20px;
	font-weight: 600;
}

.sch__btn__cont {
	width: 930px;
	height: 48px;
	border: 1px solid #eee;
	margin-left: -20px;
	border-radius: 10px;
}

.sch__btn:hover {
	cursor: pointer;
}


/*프로젝트 디자인 */
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

.btn_projectAdd:hover {
	opacity: 100%;
}

.card {
	border: none;
	border-radius: 10px;
};

.p-details span {
	font-weight: 300;
	font-size: 13px;
};


.p_photo {
    width: 50px;
    height: 50px;
    background-color: #eee;
    border-radius: 15px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 39px
};

.p_state {
    background-color: #fffbec;
    width: 60px;
    height: 25px;
    padding-bottom: 3px;
    padding-right: 5px;
    border-radius: 5px;
    display: flex;
    color: #fed85d;
    justify-content: center;
    align-items: center;
};

.progress {
    height: 10px;
    border-radius: 10px
}

.progress__design {
	margin-top: 20px;
	height: 30px;
	width: 100%;
	border-radius: 30px;
	margin-right: 30px;
	
}

.progress__color {
	background: linear-gradient(to right ,#01d6b7, #ffe498);
	margin: 7px;
	border-radius: 30px;
}

.text1 {
    font-size: 14px;
    font-weight: 600
}

.text2 {
    color: #a5aec0
}

.pointer {
 	cursor: pointer;
}

.clear__state {
	padding: 2px 14px;
	display: inline-block;
	border: 3px solid  #ffd980 ;
	outline: none;
	border-radius: 10px;
	font-size: 19px;
	font-weight: 600;
	background: #ffd980;
	margin-left: 35px;

}


.clear__state__wan {
	padding: 2px 14px;
	display: inline-block;
	border: 3px solid  #ccff99 ;
	outline: none;
	border-radius: 10px;
	font-size: 19px;
	font-weight: 600;
	background: #ccff99;
	margin-left: 35px;
}


.profile__small {
	height: 60px;
	width: 60px;
	object-fit: cover;
    border-radius: 100%;
    border: 2px solid #eee ;
    padding: 3px;
}

h6 {
	font-weight: 600;
}

.btn__list {
	margin-right: 8px;
	margin-top: 20px;
	border-radius: 12px;
	padding: 5px 10px;
	text-align: center;
	font-size: 19px;
	border: 1px solid lightgray;

}


.location__btn {
	float: right;	
}

.main__board {
	background-color: #f7f7f7;
	
}

.size__cont {
	width: 97%;

}



</style>

<script type="text/javascript">
function ajaxFun(url, method, query, dataType, fn) {
	$.ajax({
		type:method, // ex) GET, POST
		url:url, // 클라이언트가 HTTP 요청을 보낼 서버의 주소
		data:query, // 요청과 함께 서버에 보내는 string 또는 json
		dataType:dataType, // 서버에서 내려온 data 형식 (default : xml, json, script, text, html)
		success:function(data) {
			fn(data);
		}, 
		beforeSend:function(jqXHR) {
			//    HTTP 요청 헤더값 설정
			//    setRequestHeader(헤더이름, 헤더값);
			jqXHR.setRequestHeader("AJAX", true);
		},
		error:function(jqXHR) {
			// 403 : 서버측 클라이언트 접근 거부 
			//       ex) 권한에 맞지 않은 접속 요청
			if(jqXHR.status === 403) {
				alert("권한에 맞지 않은 접속요청입니다.");
				return false;
			
			// 400 : 잘못된 요청으로 인한 문법 오류
			// 		 ex) url을 잘못 입력했을 경우
			} else if(jqXHR.status === 400) {
				alert("요청 처리가 실패했습니다.");
				return false;
			}
			// responseText : 서버에 요청하여 응답받은 데이터를 문자열로 반환
			console.log(jqXHR.responseText);
		}
	});
}

$(function(){
	listPage(1);
});


function listPage(page){
	let url = "${pageContext.request.contextPath}/project/list_main_pro.do";

	let query = "pageNo="+page;
	let selector = "#proList";
	
	const fn = function(data){
		 $(selector).html(data);
		 
	};
	
	ajaxFun(url, "get", query, "html", fn);
	
}


$(document).ready(
	function listNoticeAll() {
		let url = "${pageContext.request.contextPath}/comp_notice/mainList.do";
		let query = "page="+1;
		
		let selector = "#board";
		
		const fn = function(data) {
			$(selector).html(data);
		};
		ajaxFun(url, "get", query, "html", fn);

});

$(document).ready(
		function listNoticeDept() {
			let url = "${pageContext.request.contextPath}/dep_notice/mainList.do";
			let query = "";
			
			let selector = "#board2";
			
			const fn = function(data) {
				$(selector).html(data);
			};
			ajaxFun(url, "get", query, "html", fn);

	});
	
$(document).ready(
		function listCommunity() {
			let url = "${pageContext.request.contextPath}/community/mainList.do";
			let query = "";
			
			let selector = "#board3";
			
			const fn = function(data) {
				$(selector).html(data);
			};
			ajaxFun(url, "get", query, "html", fn);

	});
	
$(document).ready(
		function listSendEdoc() {
			let apperId = "${sessionScope.member.id}";
			let url = "${pageContext.request.contextPath}/edoc/mainListSend.do";
			let query = "apperId="+encodeURIComponent(apperId);
			
			let selector = "#mainSendList"; 
			
			const fn = function(data) {
				$(selector).html(data);
			};
			ajaxFun(url, "get", query, "html", fn);

	});

$(document).ready(
		function listSendEdoc() {
			let apperId = "${sessionScope.member.id}";
			let url = "${pageContext.request.contextPath}/edoc/countTodayEdoc.do";
			let query = "apperId="+encodeURIComponent(apperId);
			// 오늘 수신된
			let selector = "#countAppTodayEdoc"; 
			
			const fn = function(data) {
				$(selector).val(data.cnt);
			};
			ajaxFun(url, "get", query, "json", fn);

	});

$(document).ready(
		function listSendEdoc() {
			let apperId = "${sessionScope.member.id}";
			let url = "${pageContext.request.contextPath}/edoc/countAppReadyEdoc.do";
			let query = "apperId="+encodeURIComponent(apperId);
			// 결재 대기 중인
			let selector = "#countAppReadyEdoc"; 
			
			const fn = function(data) {
				$(selector).val(data.cnt);
			};
			ajaxFun(url, "get", query, "json", fn);

	});
	
	// 탭메뉴 
	$(function() {
		$(document).ready(function() {
			$('ul.tabs li').click(function() {
				var tab_id = $(this).attr('data-tab');

				$('ul.tabs li').removeClass('current');
				$('.tab-content').removeClass('current');

				$(this).addClass('current');
				$("#" + tab_id).addClass('current');
			})

		})

	});
	
	$(function(){
		$("body").on("click", ".attIn-btn", function(){
			if(! confirm("출근 처리를 진행하시겠습니까 ? ")) {
				return false;
			}
			
			let url = "${pageContext.request.contextPath}/mypage/myatt_write.do";
			let query = null;

			const fn = function(data){
				if(data.state == "true") {
					$(".attIn-btn").prop("disabled", true);
					$(".attOut-btn").prop("disabled", false);
					
					let att_id = data.att_id;
					$(".attOut-btn").attr("data-att_id", att_id);
				}
			};
			ajaxFun(url, "post", query, "json", fn);
			
		});
		
		$("body").on("click", ".attOut-btn", function(){
			if(! confirm("퇴근 처리를 진행하시겠습니까 ? ")) {
				return false;
			}
			
			let url = "${pageContext.request.contextPath}/mypage/myatt_update.do";
			let query = "att_id="+$(".attOut-btn").attr("data-att_id");
			let year = "${year}";
			let month = "${month}";
			
			const fn = function(data){
				$(".attOut-btn").prop("disabled", true);			
			};
			
			ajaxFun(url, "post", query, "json", fn);
			
		});	
	});
</script>


</head>
<body>
	<main>

		<div class="main-div">
			<header class="pb-3 mb-4 border-bottom">
				<jsp:include page="/WEB-INF/views/layout/header.jsp" />
				<jsp:include page="/WEB-INF/views/layout/sidebar.jsp" />
			</header>

			<div class="row div-total div-container">
				<!-- 사원 정보 -->
				<div class="col-2 align-items-md-stretch ">
					<div class="row " style="padding-bottom: 10px;">
						<div class="row p-1 text-center border rounded-3 shadow profile__size"
							style="font-size: 13px;">
							<div class="box_photo">
								<img class="profile"
									src="${pageContext.request.contextPath}/uploads/profile/${sessionScope.member.ori_filename}">
							</div>
							<span class="fw-bold fs-6 dept__size"> ${sessionScope.member.name}&nbsp;${sessionScope.member.pos_name}님</span> 
							<span class="fs-6">${sessionScope.member.dep_name}</span> <input type="hidden" value="" id="">
							<div class="text-center" style="padding-top: 10px;">
								<button type="button" class="attIn-btn shadow" 
									${not empty todayAttendance.att_start ? "disabled='disabled'":""}>&nbsp;출&nbsp;근&nbsp;</button>
								<button type="button" class="attOut-btn shadow"
								    data-att_id="${todayAttendance.att_id}"
									${empty todayAttendance.att_start or not empty todayAttendance.att_end ? "disabled='disabled'":""}>&nbsp;퇴&nbsp;근&nbsp;</button>
							</div>
						</div>
					</div>
					<div class="size__btn">
						<div class="doc__btn shadow"><a class="a__style" href="${pageContext.request.contextPath}/edoc/write.do">결재문서 작성</a>
							
						</div>
						<div class="doc__btn2 shadow"><a class="a__style" href="${pageContext.request.contextPath}/project/write.do">새 프로젝트</a>
						</div>
		
					</div>
					<div class="doc__count shadow">
						<div class="doc__count__title  ">
							&nbsp;<i class="fa-solid fa-file-signature"></i> 결재문서 리스트
						</div>
						<div class="doc__count__content"> 
							결제 대기 문서 : <div style="display: inline;"><input class="input__style" type="text" id="countAppReadyEdoc" value="" readonly="readonly" style="width: 50px;"></input></div>
						</div>
						<hr>
						<div class="doc__count__content">
							오늘 요청된 문서 : <div style="display: inline;"><input class="input__style" type="text" id="countAppTodayEdoc" value="" readonly="readonly" style="width: 20px;"></input></div>
						</div>
					</div>
					<div class="sch__btn shadow">
					 	<div onclick="location.href='${pageContext.request.contextPath}/schedule/main.do'"><i class="fa-regular fa-calendar-check "></i>&nbsp; 내 일정 보러가기</div>
					</div>
					<div class="sch__btn__cont shadow">
					</div>
					
				</div>

				<div class=" col-4 align-items-md-stretch ">
					<!-- 수신함 -->
				
					<div class="row border rounded-3 shadow">
					<div class="div-edoc">
						<div class="div-title">&nbsp;문서 발신함</div>
						<div class="div-tabmenu">
						</div>
						<div class="myedoc" id="mainSendList" style="text-overflow: ellipsis; overflow: hidden; white-space: nowrap;"></div>
					</div>
					</div>

					<!-- 플젝 -->
					<div class="row border  rounded-3 shadow" style="margin-top: 30px;">
					<div class="div-project ">
						<div class="div-title">&nbsp;프로젝트 현황</div>
						<div class="div-tabmenu">
						</div>
						<div>
							<div class="mypro" id="proList" style="text-overflow: ellipsis; overflow: hidden; white-space: nowrap;"></div>
						</div>

					</div>
				</div>	
				</div>

				<!-- 게시판 -->
				<div class="col-4 border rounded-3 shadow div-board">
					<div class="div-title">&nbsp;공지사항</div>
					<div class="div-tabmenu">
					</div>
					<div>
						<!-- 부서:board2, 공지:board3-->
						<div class="myboard" id="board" style="text-overflow: ellipsis; overflow: hidden; white-space: nowrap;"></div>
						<div class="myboard" id="board2" style="text-overflow: ellipsis; overflow: hidden; white-space: nowrap;"></div>
						<div class="myboard" id="board3" style="text-overflow: ellipsis; overflow: hidden; white-space: nowrap;"></div>
					</div>
				</div>
				
			</div>
		</div>


	</main>

	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp" />
	</footer>

	<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp" />
</body>

</html>
