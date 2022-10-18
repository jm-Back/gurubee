<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


	<nav class="navbar navbar-expand-xl navbar-light" style="background-color: white;">
		<div class="container h-3 justify-content-start">
		<div class="col justify-content-start">
			<div class=""><img src="${pageContext.request.contextPath}/resources/images/logo_main.png" class="img" width="200px;" alt=""></div>
		</div>
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav flex-nowrap align-middle "> <!-- ms-auto : 우측으로 정렬 -->
					<li class="nav-item">
						<a class="nav-link" aria-current="page" href="${pageContext.request.contextPath}/">홈</a>
					</li>
					<li class="nav-item dropdown">
						<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
							전자결재
						</a>
						<ul class="dropdown-menu" aria-labelledby="navbarDropdown">
							<li><a class="dropdown-item" href="#"></a></li>
							<li><a class="dropdown-item" href="#">게시판</a></li>
							<li><a class="dropdown-item" href="#">포토갤러리</a></li>
							<li><a class="dropdown-divider" href="#"></a></li>
							<li><a class="dropdown-item" href="#">자료실</a></li>
						</ul>
					</li>

					<li class="nav-item">
						<a class="nav-link" href="${pageContext.request.contextPath}/sbbs/list.do">IT 강좌</a>
					</li>

					<li class="nav-item dropdown">
						<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
							커뮤니티
						</a>
						<ul class="dropdown-menu" aria-labelledby="navbarDropdown">
							<li><a class="dropdown-item" href="#">방명록</a></li>
							<li><a class="dropdown-item" href="${pageContext.request.contextPath}/bbs/list.do">게시판</a></li>
							<li><a class="dropdown-item" href="${pageContext.request.contextPath}/photo/list.do">포토갤러리</a></li>
							<li><hr class="dropdown-divider"></li>
							<li><a class="dropdown-item" href="#">자료실</a></li>
						</ul>
					</li>
					
					<li class="nav-item dropdown">
						<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
							고객센터
						</a>
						<ul class="dropdown-menu" aria-labelledby="navbarDropdown">
							<li><a class="dropdown-item" href="#">자주하는질문</a></li>
							<li><a class="dropdown-item" href="#">공지사항</a></li>
							<li><hr class="dropdown-divider"></li>
							<li><a class="dropdown-item" href="${pageContext.request.contextPath}/board/list.do">질문과답변</a></li>
						</ul>
					</li>
					
					<li class="nav-item dropdown">
						<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
							마이페이지
						</a>
						<ul class="dropdown-menu" aria-labelledby="navbarDropdown">
							<li><a class="dropdown-item" href="${pageContext.request.contextPath}/schedule/main.do">일정관리</a></li>
							<li><a class="dropdown-item" href="${pageContext.request.contextPath}/sphoto/list.do">사진첩</a></li>
							<li><a class="dropdown-item" href="#">쪽지함</a></li>
							<li><a class="dropdown-item" href="#">가계부</a></li>
							<li><hr class="dropdown-divider"></li>
							<li><a class="dropdown-item" href="${pageContext.request.contextPath}/member/pwd.do?mode=update">정보수정</a></li>
						</ul>
					</li>
					
				</ul>
			</div>
		</div>
	</nav>
