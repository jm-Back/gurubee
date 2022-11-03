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
	color: #ccc
}

#boardmenu ul li a:hover {
	background: #98E0AD;
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
	color: #98E0AD;
}

.header-top div a:hover {
	color: #98E0AD;
}

#nav-item a:hover {
	color: #98E0AD;
}

.main-div {
	width: 1800px;
	height: 1000px;
	margin-left: auto;
	margin-right: auto;
	padding-left: 50px;
	justify-content: center;
}

.profile {
	width: 120px;
	height: 120px;
	object-fit: cover;
	border-radius: 100%;
	border: 5px solid #01d6b7;
	padding: 4px;
}

.box_photo {
	overflow: visible;
	text-align: center;
	width: 100%;
	height: 70%;
	padding-bottom: 10px;
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
	font-size: 17px;
	font: bold;
	padding-top: 15px;
}

.div-info {
	height: 300px;
	padding-bottom: 10px;
}

.div-cal {
	height: 300px;
}

.div-edoc {
	margin-bottom: 10px;
	height: 290px;
}

.div-project {
	height: 300px;
}

.div-board {
	margin-left: 10px;
	width: 500px;
}

.attbtn {
	background-color: aquamarine;
}

.div-tabmenu {
	padding-top: 20px;
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
	width: 90%;
	
}

.shadow {
	box-shadow: 3px 3px 3px;
}

.div-container {
	justify-content: center;
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

$(document).ready(
	function listNoticeAll() {
		let url = "${pageContext.request.contextPath}/comp_notice/mainList.do";
		let query = "";
		
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
</script>


</head>
<body>
	<main>

		<div class="py-4 main-div">
			<header class="pb-3 mb-4 border-bottom">
				<jsp:include page="/WEB-INF/views/layout/header.jsp" />
				<jsp:include page="/WEB-INF/views/layout/sidebar.jsp" />
			</header>

			<div class="row div-total div-container">
				<!-- 사원 정보 -->
				<div class="col-3 align-items-md-stretch layoutdivt">
					<div class="row div-info" style="padding-bottom: 10px;">
						<div class="row h-100 p-5 text-center border rounded-3 shadow"
							style="font-size: 13px;">
							<div class="box_photo">
								<img class="profile"
									src="${pageContext.request.contextPath}/resources/images/profile.jpg">
							</div>
							<span class="fw-bold fs-6"> ${sessionScope.member.name}&nbsp;${sessionScope.member.pos_name}님</span> <span
								class="fs-6">${sessionScope.member.dep_name}</span> <input type="hidden" value="" id="">
							<div class="text-center" style="padding-top: 10px;">
								<button type="button" class="btn attbtn" data-bs-toggle="modal"
									data-bs-target="#exampleModal">&nbsp;출&nbsp;근&nbsp;</button>
								<button type="button" class="btn attbtn" data-bs-toggle="modal"
									data-bs-target="#exampleModal">&nbsp;퇴&nbsp;근&nbsp;</button>
							</div>
						</div>
					</div>

					<div class="row div-cal">
						<div class="row text-center border rounded-3 shadow"
							style="font-size: 13px;">
							<div class="div-title">&nbsp;일정</div>
							<div>
<<<<<<< HEAD
								<jsp:include page="/WEB-INF/views/schedule/month.jsp" />
=======
								<jsp:include page="/WEB-INF/views/mini/minisch.jsp" />
>>>>>>> branch 'main' of https://github.com/jm-Back/gurubee.git
							</div>
						</div>
					</div>
				</div>

				<div class="col-4 align-items-md-stretch layoutdiv">
					<!-- 수신함 -->
					<div class="row border rounded-3 shadow div-edoc">
						<div class="div-title">&nbsp;문서 수신함</div>
						<table class="table table-hover"
							style="table-layout: fixed; margin-top: 20px;">
							<tr>
								<th
									style="text-overflow: ellipsis; overflow: hidden; white-space: nowrap;">
									안녕하세요 사원님 9월 콘텐츠 촬영 소품 구매 건으로 쪽지드립니다.</th>
							</tr>
							<tr>
								<th
									style="text-overflow: ellipsis; overflow: hidden; white-space: nowrap;">
									안녕하세요 사원님 9월 콘텐츠 촬영 소품 구매 건으로 쪽지드립니다.</th>
							</tr>
							<tr>
								<th
									style="text-overflow: ellipsis; overflow: hidden; white-space: nowrap;">
									안녕하세요 사원님 9월 콘텐츠 촬영 소품 구매 건으로 쪽지드립니다.</th>
							</tr>
							<tr>
								<th
									style="text-overflow: ellipsis; overflow: hidden; white-space: nowrap;">
									안녕하세요 사원님 9월 콘텐츠 촬영 소품 구매 건으로 쪽지드립니다.</th>
							</tr>
							<tr>
								<th
									style="text-overflow: ellipsis; overflow: hidden; white-space: nowrap;">
									안녕하세요 사원님 9월 콘텐츠 촬영 소품 구매 건으로 쪽지드립니다.</th>
							</tr>
						</table>
					</div>

					<!-- 플젝 -->
					<div class="row border rounded-3 shadow div-project">
						<div class="div-title">&nbsp;프로젝트 현황</div>
						<table class="table table-hover"
							style="table-layout: fixed; margin-top: 20px;">
							<tr>
								<th
									style="text-overflow: ellipsis; overflow: hidden; white-space: nowrap;">
									안녕하세요 사원님 9월 콘텐츠 촬영 소품 구매 건으로 쪽지드립니다.</th>
							</tr>
							<tr>
								<th
									style="text-overflow: ellipsis; overflow: hidden; white-space: nowrap;">
									안녕하세요 사원님 9월 콘텐츠 촬영 소품 구매 건으로 쪽지드립니다.</th>
							</tr>
							<tr>
								<th
									style="text-overflow: ellipsis; overflow: hidden; white-space: nowrap;">
									안녕하세요 사원님 9월 콘텐츠 촬영 소품 구매 건으로 쪽지드립니다.</th>
							</tr>
							<tr>
								<th
									style="text-overflow: ellipsis; overflow: hidden; white-space: nowrap;">
									안녕하세요 사원님 9월 콘텐츠 촬영 소품 구매 건으로 쪽지드립니다.</th>
							</tr>
							<tr>
								<th
									style="text-overflow: ellipsis; overflow: hidden; white-space: nowrap;">
									안녕하세요 사원님 9월 콘텐츠 촬영 소품 구매 건으로 쪽지드립니다.</th>
							</tr>
						</table>

					</div>
				</div>

				<!-- 게시판 -->
				<div class="col-4 border rounded-3 shadow div-board">
					<div class="div-title">&nbsp;공지사항</div>
					<div class="div-tabmenu">
						<ul class="tabs">
							<li class="tab-link current" data-tab="board1">전체</li>
							<li class="tab-link" data-tab="board2">부서</li>
							<li class="tab-link" data-tab="board3">커뮤니티</li>
						</ul>
					</div>
					<div>
						<!-- 부서:board2, 공지:board3-->
						<div class="myboard" id="board"></div>
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
