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

<script type="text/javascript">
function check() {
  const f = document.boardForm;

  return true;
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
			<form action="" method="post" name="writeForm">
				<table class="table table-border table-form">
					<tr>
						<th>문서구분</th>
						<td>
							<div class="btn-group-vertical" role="group" aria-label="Vertical button group">	
							<button type="button" class="btn btn-dark" id="">휴가신청서</button>
							<button type="button" class="btn btn-dark" id="">DB접근권한신청서</button>
							<button type="button" class="btn btn-dark" id="">구매요청의뢰서</button>
							<button type="button" class="btn btn-dark" id="">재택근무신청서</button>
							<button type="button" class="btn btn-dark" id="">법인카드지출의뢰서</button>
							<button type="button" class="btn btn-dark" id="">출장신청서</button>
							</div>
						</td>
					</tr>
					
					<tr>
						<th>결재라인</th>
					</tr>
				
					<tr>
						<th>수신자</th>
						<td>
						<input type="text" name="userId" id="sendId" class="form-control" maxlength="10" style="width: 50%;">
						<button type="button">수신자 추가</button>
						</td>
					</tr>
			
					<tr>
						<th>상세내용</th>
						<td>
							<textarea name="content" id="ir1" class="form-control" style="width: 95%; height:270px; ">${dto.content}</textarea>
							
						</td>
					</tr>
				</table>
			</form>
		</div>
	
		</div>
		
	</main>

	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp" />
	</footer>


<script type="text/javascript" src="${pageContext.request.contextPath}/resources/se2/js/service/HuskyEZCreator.js" charset="utf-8"></script>
<script type="text/javascript">
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
