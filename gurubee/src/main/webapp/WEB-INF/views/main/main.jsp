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
	background-color: rgba(0, 0, 0, .1);
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
	text-overflow:ellipsis; 
	overflow:hidden; 
	white-space:nowrap;"
}

#infomenu div a:hover{
	color: #98E0AD;	
}

.header-top div a:hover{
	color: #98E0AD;	
}

#nav-item a:hover {
	color: #98E0AD;	
}

.profile {
	width: 120px;
    height: 120px; 
    object-fit: cover;
    border-radius: 100%;
    border: 5px solid aquamarine ;
    padding: 4px;
}

.box_photo{

    overflow: visible;
    text-align: center;
   	width: 100%;
    height: 100%;
    padding-bottom: 20px;
}


</style>
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>

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
			
		<!-- 로그인 사원 정보 -->
		<div class="row">
			<div class="col container">
				<div class="col-md-4 align-items-md-stretch">
					<div class="">
						<div class="h-100 p-5 text-center bg-light border rounded-3" style="font-size: 13px;">
							<div style="margin-bottom: 0px;" >
								<div class="box_photo"><img class="profile" src="${pageContext.request.contextPath}/resources/images/profile.jpg" ></div>
									<span class="fw-bold fs-6"> ${dto.id}&nbsp;${dto.name}님</span>
									<div>${dto.dep_name} (${dto.pos_name}) </div>
							</div>
							<div class="text-center col-12 p-4" >
								<button type="button" class="btn"
									style="background-color: aquamarine;"  data-bs-toggle="modal"
									data-bs-target="#exampleModal">
									&nbsp;출&nbsp;근&nbsp;
								</button>
								<button type="button" class="btn"
									style="background-color: aquamarine;" data-bs-toggle="modal"
									data-bs-target="#exampleModal">
									&nbsp;퇴&nbsp;근&nbsp;
								</button>
							</div>
							
							<div id="infomenu" style="width: 100%; text-align: center;">
								<div style="float: left; width: 33%">
									<a href="#" title="마이페이지"><i class="bi bi-person-circle fa-2x"></i></a>
									<div>정보수정</div>
								</div>
								<div style="float: left; width: 33%">
									<a href="#" title="전자결재"><i class="bi bi-file-earmark fa-2x"></i></a>
									<div>전자결재</div>
								</div>
								<div style="float: left; width: 33%">
									<a href="#" title="쪽지함"><i class="bi bi-envelope fa-2x"></i></a>
									<div>쪽지함</div>
								</div>
							</div>
							
							<!-- 쪽지는 최신 10개까지만 출력 -->
							<div id="notelist" style="height: 60%; width:100%; overflow: auto;">
								<table class="table table-hover table-light" style="table-layout: fixed; margin-top: 20px;">
									<tr>
										<th style="text-overflow:ellipsis; overflow:hidden; white-space:nowrap;">
											안녕하세요 사원님 9월 콘텐츠 촬영 소품 구매 건으로 쪽지드립니다.
										</th>
									</tr>
									<tr>
										<th style="text-overflow:ellipsis; overflow:hidden; white-space:nowrap;">
											안녕하세요 사원님 9월 콘텐츠 촬영 소품 구매 건으로 쪽지드립니다.
										</th>
									</tr>
									<tr>
										<th style="text-overflow:ellipsis; overflow:hidden; white-space:nowrap;">
											안녕하세요 사원님 9월 콘텐츠 촬영 소품 구매 건으로 쪽지드립니다.
										</th>
									</tr>
									<tr>
										<th style="text-overflow:ellipsis; overflow:hidden; white-space:nowrap;">
											안녕하세요 사원님 9월 콘텐츠 촬영 소품 구매 건으로 쪽지드립니다.
										</th>
									</tr>
									<tr>
										<th style="text-overflow:ellipsis; overflow:hidden; white-space:nowrap;">
											안녕하세요 사원님 9월 콘텐츠 촬영 소품 구매 건으로 쪽지드립니다.
										</th>
									</tr>
									<tr>
										<th style="text-overflow:ellipsis; overflow:hidden; white-space:nowrap;">
											안녕하세요 사원님 9월 콘텐츠 촬영 소품 구매 건으로 쪽지드립니다.
										</th>
									</tr>
								</table>
							</div>
	
						</div>
					</div>		
				</div>
			</div>
				<div class="col-md-4">
					<div id="board"></div>
					<div id="board2"></div>
					<div id="board3"></div>
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
