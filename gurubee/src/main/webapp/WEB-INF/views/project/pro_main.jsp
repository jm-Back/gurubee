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
			
			
	<div> 프로젝트 메인 </div>		
			

	</main>
	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp" />
	</footer>

</body>

</html>
