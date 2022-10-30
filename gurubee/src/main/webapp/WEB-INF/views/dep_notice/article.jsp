<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp"/>

<style type="text/css">
.body-container {
	max-width: 800px;
}
</style>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/board2.css" type="text/css">

<script type="text/javascript">
<c:if test="${sessionScope.member.id == dto.writer_id || sessionScope.member.id=='admin'}">
	function deleteBoard() {
		if(confirm("게시글을 삭제하시겠습니까?")) {
			let query = "num=${dto.num}&${query}";
			let url = "${pageContext.request.contextPath}/dep_notice/delete.do?" + query;
			location.href = url;
		}
	}
</c:if>
</script>

<script type="text/javascript">
function login() {
	location.href="${pageContext.request.contextPath}/member/login.do";
}

/*
	jqXHR : jQueryXMLHttpRequest -> XMLHttpRequest 객체를 대체
	$.ajax : 비동기식 Ajax를 이용하여 HTTP 요청을 전송
	success(data, textStatus, jqXHR) : 요청이 성공일때 실행되는 callback 함수 (callback 함수 : 다른 함수에 매개변수로 넘겨준 함수)					
	beforeSend : ajax가 서버에 요청하기 전에 실행
*/
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
				login();
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
	
// 페이징 처리
$(function() {
	listPage(1);
});

function listPage(page) {
	let url = "${pageContext.request.contextPath}/dep_notice/listReply.do";
	let query = "num=${dto.num}&pageNo="+page;
	
	let selector = "#listReply";
	
	const fn = function(data) {
		$(selector).html(data);
	};
	ajaxFun(url, "get", query, "html", fn);
}

// 댓글 등록
$(function(){
	$(".btnSendReply").click(function(){
		let num = "${dto.num}";
		// 영문자와 구분하기위해 $ 사용, closest : 가장 가까운 해당 태그로 이동
		const $tb = $(this).closest("table");
		let content = $tb.find("textarea").val().trim(); // find : 자식 요소 검색
		if( ! content ) {
			$tb.find("textarea").focus();
			return false;
		}
		
		content = encodeURIComponent(content);
		
		let url = "${pageContext.request.contextPath}/dep_notice/insertReply.do";
		let query = "num="+num+"&content="+content+"&answer=0";
		
		const fn = function(data) {
			$tb.find("textarea").val("");
			
			if(data.state === "true") {
				listPage(1);
			} else {
				alert("댓글 등록이 실패 했습니다.");
			}
		};
		
		ajaxFun(url, "post", query, "json", fn);
		
	});
});

// 댓글 삭제
$(function(){
	$("body").on("click", ".deleteReply", function(){
		if(! confirm('댓글을 삭제하시겠습니까 ? ')) {
			return false;
		}
		
		let replyNum = $(this).attr("data-replyNum");
		let page = $(this).attr("data-pageNo");
		
		let url = "${pageContext.request.contextPath}/dep_notice/deleteReply.do";
		let query = "replyNum=" + replyNum;
		
		const fn = function(data) {
			listPage(page);
		};
		
		ajaxFun(url, "post", query, "json", fn);
		
	});
});

// 댓글별 답글 리스트
function listReplyAnswer(answer) {
	let url = "${pageContext.request.contextPath}/dep_notice/listReplyAnswer.do";
	let query = "answer="+answer;
	let selector = "#listReplyAnswer" + answer;
	
	const fn = function(data) {
		$(selector).html(data);
	};
	ajaxFun(url, "get", query, "html", fn);
}

// 댓글별 답글 개수
function countReplyAnswer(answer) {
	let url = "${pageContext.request.contextPath}/dep_notice/countReplyAnswer.do";
	let query = "answer="+answer;
	
	const fn = function(data) {
		let count = data.count;
		let selector = "#answerCount"+answer;
		$(selector).html(count);
	};
	
	ajaxFun(url, "post", query, "json", fn);
}

// 댓글별 답글 등록
$(function(){
	$("body").on("click", ".btnSendReplyAnswer", function(){
		
		// 게시물 번호, 댓글 번호, td 위치 찾기
		let num = "${dto.num}";
		let replyNum = $(this).attr("data-replyNum");
		const $td = $(this).closest("td");
		
		// 답글 비어있는지 체크
		let content = $td.find("textarea").val().trim();
		if(! content) {
			$td.find("textarea").focus();
			return false;
		}
		
		// URI로 데이터를 전달하기위해 문자열을 인코딩
		content = encodeURIComponent(content);
		
		let url = "${pageContext.request.contextPath}/dep_notice/insertReply.do";
		let query = "num="+ num + "&content="+content+"&answer="+replyNum;
		
		const fn = function(data) {
			$td.find("textarea").val("");
			
			let state = data.state;
			
			if(state === "true") {
				listReplyAnswer(replyNum);
				countReplyAnswer(replyNum);
			}
		};
		
		ajaxFun(url, "post", query, "json", fn);
		
	});
});

// 답글 버튼(댓글별 답글 등록 폼 및 답글 리스트)
$(function(){
	$("body").on("click", ".btnReplyAnswerLayout", function(){
		const $tr = $(this).closest("tr").next();
		
		// 두번째 tr 보이게끔 설정 (tr class='reply-answer')
		let isVisible = $tr.is(":visible");
		let replyNum = $(this).attr("data-replyNum");
		
		if(isVisible) {
			$tr.hide();
		} else {
			$tr.show();
			
			// 답글 리스트
			listReplyAnswer(replyNum);
			
			// 답글 개수
			countReplyAnswer(replyNum);
		}
		
	});
});

// 댓글별 답글 삭제
$(function(){
	$("body").on("click", ".deleteReplyAnswer", function(){
		
		if(! confirm("답글을 삭제 하시겠습니까 ?")) {
			return false;
		}
		
		let replyNum = $(this).attr("data-replyNum");
		let answer = $(this).attr("data-answer");
		
		let url = "${pageContext.request.contextPath}/dep_notice/deleteReplyAnswer.do";
		let query = "replyNum=" + replyNum;
		
		const fn = function(data) {
			
			listReplyAnswer(answer);
			countReplyAnswer(answer);
			
		}
		
		ajaxFun(url, "post", query, "json", fn);
		
	});
});

</script>

</head>
<body>

<header>
	<jsp:include page="/WEB-INF/views/layout/header.jsp"/>
	<jsp:include page="/WEB-INF/views/layout/sidebar.jsp"/>
</header>

<main>
	<div class="container">
		<div class="body-container">	
			<div class="body-title">
				<h3>회사 공지사항</h3>
			</div>
			
			<div class="body-main">
				
				<table class="table">
					<thead>
						<tr>
							<td colspan="2" align="center">
								${dto.notice_title}
							</td>
						</tr>
					</thead>
					
					<tbody>
						<tr>
							<td width="50%">
								작성자 : ${dto.writer_name}&nbsp;${dto.pos_name}
							</td>
							<td align="right">
								${dto.regdate} | 조회 ${dto.views}
							</td>
						</tr>
						
						<tr>
							<td colspan="2" valign="top" height="200">
								${dto.notice_content}
							</td>
						</tr>
						
						<tr>
							<td colspan="2">
								파&nbsp;&nbsp;일 : 
								<c:if test="${not empty dto.save_filename}">
									<a href="${pageContext.request.contextPath}/dep_notice/download.do?num=${dto.num}">${dto.ori_filename}</a>
								</c:if>
							</td>
						</tr>

						<tr>
							<td colspan="2">
								이전글 :
								<c:if test="${not empty preReadDto}">
									<a href="${pageContext.request.contextPath}/dep_notice/article.do?${query}&num=${preReadDto.num}">${preReadDto.notice_title}</a>
								</c:if>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								다음글 :
								<c:if test="${not empty nextReadDto}">
									<a href="${pageContext.request.contextPath}/dep_notice/article.do?${query}&num=${nextReadDto.num}">${nextReadDto.notice_title}</a>
								</c:if>
							</td>
						</tr>
					</tbody>
				</table>
				
				<table class="table table-borderless">
					<tr>
						<td width="50%">
							<c:choose>
								<c:when test="${sessionScope.member.id==dto.writer_id}">
									<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/dep_notice/update.do?num=${dto.num}&page=${page}';">수정</button>
								</c:when>
								<c:otherwise>
									<button type="button" class="btn btn-light" disabled="disabled">수정</button>
								</c:otherwise>
							</c:choose>
					    	
							<c:choose>
					    		<c:when test="${sessionScope.member.id==dto.writer_id || sessionScope.member.id=='admin'}">
					    			<button type="button" class="btn btn-light" onclick="deleteBoard();">삭제</button>
					    		</c:when>
					    		<c:otherwise>
					    			<button type="button" class="btn btn-light" disabled="disabled">삭제</button>
					    		</c:otherwise>
					    	</c:choose>
						</td>
						<td class="text-end">
							<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/dep_notice/list.do?${query}';">리스트</button>
						</td>
					</tr>
				</table>
				
				<div class="reply">
					<form name="replyForm" method="post">
						<div class='form-header'>
							<span class="bold">Q n A</span><span> 클린한 댓글을 사용하는 gurubee人이 됩시다! </span>
						</div>
						
						<table class="table table-borderless reply-form">
							<tr>
								<td>
									<textarea class='form-control' name="content"></textarea>
								</td>
							</tr>
							<tr>
							   <td align='right'>
							        <button type='button' class='btn btn-light btnSendReply'>질문 등록</button>
							    </td>
							 </tr>
						</table>
					</form>
					
					<div id="listReply"></div>
				</div>
				
			</div>
		</div>
	</div>
</main>

<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</footer>

<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>
</body>
</html>