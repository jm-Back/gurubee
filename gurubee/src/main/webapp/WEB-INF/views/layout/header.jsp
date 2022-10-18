<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

	<div class="container-fluid bg-light header-top">
		<div class="container">
			<div class="row">
				<div class="col">
					<div class="p-2">
						<i class="bi bi-telephone-inbound-fill"></i> +82-1234-1234
					</div>
				</div>
				<div class="col">
					<div class="d-flex justify-content-end">
						<c:if test="${empty sessionScope.member}">
							<div class="p-2">
								<a href="javascript:dialogLogin();" title="로그인"><i class="bi bi-lock"></i></a>
							</div>
							<div class="p-2">
								<a href="${pageContext.request.contextPath}/member/member.do" title="회원가입"><i class="bi bi-person-plus"></i></a>
							</div>	
						</c:if>
						<c:if test="${not empty sessionScope.member}">
							<div class="p-2">
								<a href="#" title="알림"><i class="bi bi-bell"></i></a>
							</div>
							<div class="p-2">
								<a href="${pageContext.request.contextPath}/member/logout.do" title="로그아웃"><i class="bi bi-unlock"></i></a>
							</div>					
						</c:if>
						<c:if test="${sessionScope.member.userId == 'admin'}">
							<div class="p-2">
								<a href="#" title="관리자"><i class="bi bi-gear"></i></a>
							</div>					
						</c:if>
					
					</div>
					
				</div>
			</div>
		</div>
	</div>

	<nav class="navbar navbar-expand-lg navbar-light">
		<div class="container">
			<a class="navbar-brand" href="${pageContext.request.contextPath}/"><i class="bi bi-app-indicator"></i></a>
			<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
				
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav mx-auto flex-nowrap"> <!-- ms-auto : 우측으로 정렬 -->
					<li class="nav-item">
						<a class="nav-link" aria-current="page" href="${pageContext.request.contextPath}/">홈</a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="#">회사소개</a>
					</li>

					<li class="nav-item">
						<a class="nav-link" href="#">IT 강좌</a>
					</li>

					<li class="nav-item dropdown">
						<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
							커뮤니티
						</a>
						<ul class="dropdown-menu" aria-labelledby="navbarDropdown">
							<li><a class="dropdown-item" href="#">방명록</a></li>
							<li><a class="dropdown-item" href="#">게시판</a></li>
							<li><a class="dropdown-item" href="#">포토갤러리</a></li>
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
							<li><a class="dropdown-item" href="#">질문과답변</a></li>
						</ul>
					</li>
					
					<li class="nav-item dropdown">
						<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
							마이페이지
						</a>
						<ul class="dropdown-menu" aria-labelledby="navbarDropdown">
							<li><a class="dropdown-item" href="#">일정관리</a></li>
							<li><a class="dropdown-item" href="#">사진첩</a></li>
							<li><a class="dropdown-item" href="#">쪽지함</a></li>
							<li><a class="dropdown-item" href="#">가계부</a></li>
							<li><hr class="dropdown-divider"></li>
							<li><a class="dropdown-item" href="#">정보수정</a></li>
						</ul>
					</li>
					
				</ul>
			</div>
			
		</div>
	</nav>
	
	<!-- Login Modal -->
	<script type="text/javascript">
		function dialogLogin() {
		    $("form[name=modelLoginForm] input[name=userId]").val("");
		    $("form[name=modelLoginForm] input[name=userPwd]").val("");
		    
			$("#loginModal").modal("show");	
			
		    $("form[name=modelLoginForm] input[name=userId]").focus();
		}
	
		function sendModelLogin() {
		    var f = document.modelLoginForm;
			var str;
			
			str = f.userId.value;
		    if(!str) {
		        f.userId.focus();
		        return;
		    }
		
		    str = f.userPwd.value;
		    if(!str) {
		        f.userPwd.focus();
		        return;
		    }
		
		    f.action = "${pageContext.request.contextPath}/member/login_ok.do";
		    f.submit();
		}
	</script>
	<div class="modal fade" id="loginModal" tabindex="-1"
			data-bs-backdrop="static" data-bs-keyboard="false" 
			aria-labelledby="loginModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="loginViewerModalLabel">Login</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
	                <div class="p-3">
	                    <form name="modelLoginForm" action="" method="post" class="row g-3">
	                    	<div class="mt-0">
	                    		 <p class="form-control-plaintext">계정으로 로그인 하세요</p>
	                    	</div>
	                        <div class="mt-0">
	                            <input type="text" name="userId" class="form-control" placeholder="아이디">
	                        </div>
	                        <div>
	                            <input type="password" name="userPwd" class="form-control" placeholder="패스워드">
	                        </div>
	                        <div>
	                            <div class="form-check">
	                                <input class="form-check-input" type="checkbox" id="rememberMeModel">
	                                <label class="form-check-label" for="rememberMeModel"> 아이디 저장</label>
	                            </div>
	                        </div>
	                        <div>
	                            <button type="button" class="btn btn-primary w-100" onclick="sendModelLogin();">Login</button>
	                        </div>
	                        <div>
	                    		 <p class="form-control-plaintext text-center">
	                    		 	<a href="#" class="text-decoration-none me-2">패스워드를 잊으셨나요 ?</a>
	                    		 </p>
	                    	</div>
	                    </form>
	                    <hr class="mt-3">
	                    <div>
	                        <p class="form-control-plaintext mb-0">
	                        	아직 회원이 아니세요 ?
	                        	<a href="${pageContext.request.contextPath}/member/member.do" class="text-decoration-none">회원가입</a>
	                        </p>
	                    </div>
	                </div>
	        
				</div>
			</div>
		</div>
	</div>	