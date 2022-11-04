<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style>

.guru {
	margin-left: 50px;
}

@font-face {
	font-family: 'menuFont';
	scr  : url(esanmaru Medium.ttf)
}

#main-menu {
	font-family: 'menuFont';
	font-size: 15px;
    font-weight: 600;
    cursor: pointer;
    color: white;"C:/Users/A/Downloads/S-Core_Dream_OTF/SCDream5.otf"
    opacity: 90%;
    border: none;
	border-radius: 20px;
    padding: 10px;
    z-index: 2;
    
    
}

#mar {
	margin-right: 30px;
}

.menu:after{display:block; content:''; clear:both;}
.menu > li{position:relative; float:left; margin-right:5px;}
.menu > li > a{display:block; padding:0 15px; height:40px; line-height:40px; color:#fff;}
.menu > li:hover .depth_1 {display:block;}
.menu .depth_1{display:none; position:absolute; left:0; right:0; text-align:center; }
.menu .depth_1 a{display:block; padding:5px; color:#5e6576;}

.drop-downmenu {
	background-color: #78FF87;;
}

*/

.div-mynavbar {
	color: #5CFF87;

}

.navbar .nav-item .nav-link:hover,
.navbar .nav-item .nav-link.active {
	color: #5CFF87;;
	text-decoration: none;
}

</style>

<script src="http://code.jquery.com/jquery-3.2.1.min.js"></script>

<script type="text/javascript">
	/*
	$(function() {
		$(".dropdown").hover(
				function() { $(this).find(".dropdown-menu").slideToggle(400); 
		});
	});
	*/
	
</script>

	<nav class="navbar navbar-expand-xl navbar-light" style="background-color: white; font-family:esamanru Medinum; ">
		<div class="container h-3 justify-content-start">
		<div class="col justify-content-start">
			<div>
				<a class="nav-link" aria-current="page" href="${pageContext.request.contextPath}/main.do">
					<img src="${pageContext.request.contextPath}/resources/images/logo_main.png" class="img guru" width="200px;" alt="">
				</a>
			</div>
		</div>
			<div class="collapse navbar-collapse div-mynavbar" id="navbarSupportedContent">
				<ul id="main-menu" class="menu navbar-nav flex-nowrap align-middle" > <!-- ms-auto : 우측으로 정렬 -->
					
					<li class="nav-item">
						<a class="nav-link" href="${pageContext.request.contextPath}/project/list.do" id="navbarDropdown" role="button"  aria-expanded="false">
							프로젝트
						</a>
					</li>
					
					<li class="nav-item dropdown">
						<a style="margin-top: -1px;" class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
							전자결재
						</a>
						<ul id="mar" class="depth_1 dropdown-menu" aria-labelledby="navbarDropdown">
							<li><a class="dropdown-item" href="${pageContext.request.contextPath}/edoc/write.do" >전자결재 작성</a></li>
							<li><a class="dropdown-item" href="${pageContext.request.contextPath}/edoc/list_receive.do">수신함</a></li>
							<li><a class="dropdown-item" href="${pageContext.request.contextPath}/edoc/list_send.do">발신함</a></li>
							<li><a class="dropdown-item" href="${pageContext.request.contextPath}/edoc/list_temp.do">임시보관함</a></li>
						</ul>
					
					
					<li class="nav-item">
						<a class="nav-link" href="${pageContext.request.contextPath}/schedule/main.do" id="navbarDropdown" role="button"  aria-expanded="false">
							일정관리
						</a>
					</li>
					
					<li class="nav-item dropdown">
						<a style="margin-top: -1px;" class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
							인사관리
						</a>
						<ul class="depth_1 dropdown-menu" aria-labelledby="navbarDropdown">
							<li><a class="dropdown-item" href="${pageContext.request.contextPath}/employee/write.do">신입사원 등록</a></li>
							<li><a class="dropdown-item" href="${pageContext.request.contextPath}/employee/list.do">사원 통합관리</a></li>
							<li><a class="dropdown-item" href="${pageContext.request.contextPath}/pay/write.do">급여 정보</a></li>
							<li><a class="dropdown-item" href="${pageContext.request.contextPath}/pay/sal_pay_main.do">연봉 정보</a></li>
						</ul>
					</li>
					<li class="nav-item dropdown">
						<a style="margin-top: -1px;" class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
							커뮤니티
						</a>
						<ul class="depth_1 dropdown-menu" aria-labelledby="navbarDropdown">
							<li><a class="dropdown-item" href="${pageContext.request.contextPath}/comp_notice/list.do">회사 공지사항</a></li>
							<li><a class="dropdown-item" href="${pageContext.request.contextPath}/dep_notice/list.do">부서 공지사항</a></li>
							<li><a class="dropdown-item" href="${pageContext.request.contextPath}/community/list.do">익명 커뮤니티</a></li>
						</ul>
					</li>
					
					<li class="nav-item">
						<a class="nav-link" href="${pageContext.request.contextPath}/mypage/mypage.do" id="navbarDropdown" role="button"  aria-expanded="false">
							마이페이지
						</a>
					</li>
					
				</ul>
			</div>
		</div>
	</nav>
