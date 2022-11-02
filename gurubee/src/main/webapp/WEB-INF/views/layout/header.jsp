<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style>

.guru {
	margin-left: 50px;
}

#main-menu {
	
	font-size: 15px;
    font-weight: 600;
    cursor: pointer;
    color: white;
    background-color: #cfffe5;
    opacity: 90%;
    border: none;
	border-radius: 20px;
    padding: 10px;
    z-index: 2;
}



</style>

<script src="http://code.jquery.com/jquery-3.2.1.min.js"></script>

<script type="text/javascript">
	
	$(function() {
		$(".dropdown").hover(function() {
			$(this).find(".dropdown-menu").slideToggle("fix");
		}).mouseover(function(){
			$(".dropdow-menu").css("position","fixed");
		});
	});
	/*
	.mouseover(function(){
        $(".dropdown-menu").next("ul").slideDown();
    });
	*/
</script>

	<nav class="navbar navbar-expand-xl navbar-light" style="background-color: white;">
		<div class="container h-3 justify-content-start">
		<div class="col justify-content-start">
			<div>
				<a class="nav-link" aria-current="page" href="${pageContext.request.contextPath}/main.do">
					<img src="${pageContext.request.contextPath}/resources/images/logo_main.png" class="img guru" width="200px;" alt="">
				</a>
			</div>
		</div>
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul id="main-menu" class="navbar-nav flex-nowrap align-middle" > <!-- ms-auto : 우측으로 정렬 -->
					
					<li class="nav-item">
						<a class="nav-link" href="${pageContext.request.contextPath}/project/list.do" id="navbarDropdown" role="button"  aria-expanded="false">
							프로젝트
						</a>
					</li>
					
					<li class="nav-item dropdown">
						<a style="margin-top: -1px;" class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
							전자결재
						</a>
						<ul class="dropdown-menu" aria-labelledby="navbarDropdown" >
							<li><a class="dropdown-item" href="${pageContext.request.contextPath}/edoc/write.do" >전자결재 작성</a></li>
							<li><a class="dropdown-item" href="${pageContext.request.contextPath}/edoc/list_receive.do">수신함</a></li>
							<li><a class="dropdown-item" href="${pageContext.request.contextPath}/edoc/list_send.do">발신함</a></li>
							<li><a class="dropdown-item" href="${pageContext.request.contextPath}/edoc/list_temp.do">임시보관함</a></li>
						</ul>
					</li>
					
					<li class="nav-item">
						<a class="nav-link" href="${pageContext.request.contextPath}/schedule/main.do" id="navbarDropdown" role="button"  aria-expanded="false">
							일정관리
						</a>
					</li>
					
					<li class="nav-item dropdown">
						<a style="margin-top: -1px;" class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
							인사관리
						</a>
						<ul class="dropdown-menu" aria-labelledby="navbarDropdown">
						<li><a class="dropdown-item" href="${pageContext.request.contextPath}/eattendance/write.do">근태 등록</a></li>
							<li><a class="dropdown-item" href="${pageContext.request.contextPath}/employee/write.do">신입사원 등록</a></li>
							<li><a class="dropdown-item" href="${pageContext.request.contextPath}/employee/update.do">사원 통합관리</a></li>
							<li><a class="dropdown-item" href="${pageContext.request.contextPath}/payment/list.do">급여 정보</a></li>
							<li><a class="dropdown-item" href="${pageContext.request.contextPath}/salary/sal_list.do">연봉 정보</a></li>
						</ul>
					</li>

					<li class="nav-item dropdown">
						<a style="margin-top: -1px;" class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
							커뮤니티
						</a>
						<ul class="dropdown-menu" aria-labelledby="navbarDropdown">
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
