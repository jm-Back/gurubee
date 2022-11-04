<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<style type="text/css">



li {
	margin: 10px;
	list-style: none;
}

a {
	text-decoration: none;
	color: black;
	
}

#title {
	margin-left: 30px;
	margin-top: 60px;
	font-size: 1.5rem;
    font-family: 'Pretendard-Regular' ,sans-serif;
    font-weight: bold;
    margin-bottom: -35px;
}


li > a:link,  li > a:visited {
     color: black;
     padding: 3px 10px;
     text-align: center;
     text-decoration: none;
     display: inline-block;
     border-radius: 10px;
}
 a:hover, a:active {
     font-weight: bold;
}

.flex {
	display: flex;
	justify-content: center;
	margin: 70px 0;
}

.site {
	width: 900px;
	margin: 0 auto;
	margin-top: 20px;
}

.main-menu > h4 {
	font-weight: 600;
	margin-left: 50px;
	border-bottom: 2px solid aquamarine;
	padding-bottom: 3px;
	font-size: 1.2rem;
	font-family: 'Pretendard-Regular' ,sans-serif;
}

.main-menu a {
	font-family: 'Pretendard-Regular' ,sans-serif;
}

.main-menu {
	width:250px;
}

@font-face {
    font-family: 'Pretendard-Regular';
    src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Regular.woff') format('woff');
    font-weight: 400;
    font-style: normal;
}

</style>
</head>

<header>
	<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp"/>
	<jsp:include page="/WEB-INF/views/layout/header.jsp"/>
	<jsp:include page="/WEB-INF/views/layout/sidebar.jsp"/>
</header>

<body>
<div class="site">
	<h2 id="title">사이트 맵</h2>
	<div class="flex">
		<div class="main-menu">
			<h4>일정관리</h4>
			<ul>
				<li><a href="${pageContext.request.contextPath}/schedule/main.do">내 일정리스트</a></li>
			</ul>
		</div>
		<div class="main-menu">
			<h4>프로젝트</h4>
			<ul>
				<li><a href="${pageContext.request.contextPath}/project/write.do">프로젝트 등록</a></li>	
			   	<li><a href="${pageContext.request.contextPath}/project/list.do">프로젝트 리스트</a></li>
			</ul> 
		</div>  
		<div class="main-menu">
			<h4>전자결재</h4>
			<ul>
			   <li><a href="${pageContext.request.contextPath}/edoc/write.do">결재문서 작성하기</a></li>
			   <li><a href="${pageContext.request.contextPath}/edoc/list_receive.do">결재문서 수신함</a></li>
			   <li><a href="${pageContext.request.contextPath}/edoc/list_send.do">결재문서 발신함</a></li>
			   <li><a href="${pageContext.request.contextPath}/edoc/list_temp.do">결재문서 임시보관함</a></li>
			</ul>  
		</div> 

		<div class="main-menu">	
			<h4>인사관리</h4>
			<ul>
			   <li><a href="${pageContext.request.contextPath}/employee/write.do">신입사원 등록</a></li>
			   <li><a href="${pageContext.request.contextPath}/employee/list.do">사원통합관리</a></li>
			   <li><a href="${pageContext.request.contextPath}/pay/pay_write.do">급여정보 등록</a></li>
			   <li><a href="${pageContext.request.contextPath}/pay/pay_list.do">급여정보 리스트</a></li>
			   <li><a href="${pageContext.request.contextPath}/pay/sal_pay_main.do">연봉정보 등록</a></li>
			   <li><a href="${pageContext.request.contextPath}/pay/sal_list.do">연봉정보 리스트</a></li>
			</ul>   
		</div> 	
	</div>
	<div class="flex">
		<div class="main-menu">
			<h4>회사공지</h4>
			<ul>
			   <li><a href="${pageContext.request.contextPath}/comp_notice/write.do">회사공지 글쓰기</a></li>
			   <li><a href="${pageContext.request.contextPath}/comp_notice/list.do">회사공지 리스트</a></li>	
			</ul>
		</div>
		
		<div class="main-menu">
			<h4>부서공지</h4>
			<ul>   		
				<li><a href="${pageContext.request.contextPath}/dep_notice/write.do">부서공지 글쓰기</a></li>
			   	<li><a href="${pageContext.request.contextPath}/dep_notice/list.do">부서공지 리스트</a></li>	
			</ul>   
		</div>
		 
		<div class="main-menu">
			<h4>커뮤니티</h4>
			<ul>	
				<li><a href="${pageContext.request.contextPath}/community/write.do">커뮤니티 글쓰기</a></li>
			    <li><a href="${pageContext.request.contextPath}/community/list.do">커뮤니티 리스트</a></li>  		
			</ul>	
		</div> 
		
		<div class="main-menu">
			<h4>마이페이지</h4>
			<ul>
			   	<li><a href="${pageContext.request.contextPath}/mypage/mypage.do">마이페이지</a></li>
			   	<li><a href="${pageContext.request.contextPath}/mypage/myatt.do">개인근태관리</a></li>
			   	<li><a href="${pageContext.request.contextPath}/mypage/myatt_list.do">월별근태관리 조회</a></li>
			   	<li><a href="${pageContext.request.contextPath}/mypage/myatt_write.do">출퇴근등록</a></li>
			   	<!-- 
			   	<li><a href="${pageContext.request.contextPath}/mypage/myatt_article.do">총 근무시간 출력</a></li>
			   		리스트에 최초 근무일자랑 현재일 불러와서 계산해서 출력...? -->
			   		
			   	<!-- URI 미수정
			   	<li><a href="${pageContext.request.contextPath}/mypage/mypay_article.do">월별 급여 명세서 조회</a></li>
			   	<li><a href="${pageContext.request.contextPath}/mypage/mypay_article.do">급여명세서 출력</a></li>
			   	<li><a href="${pageContext.request.contextPath}/mypage/mypay_article.do">개인정보 수정폼</a></li> -->
			</ul>
		</div>
	</div>	
</div>	

<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
	<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>
</footer>



</body>
</html>