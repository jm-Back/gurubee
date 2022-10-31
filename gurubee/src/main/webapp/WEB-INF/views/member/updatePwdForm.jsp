<%@ page contentType="text/html; charset=UTF-8"%>
<%@page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Bootstrap Flat Modal Login Modal Form</title>
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<style>

body{
	width:100%;
	height:100vh;
	font-family: 'Varela Round', sans-serif;
}

.jb-box { width: 100%; height: 100%; overflow: hidden;margin: 0; position: relative; }

video { width: 100%; height:100vh; }

#myModal {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -30%);
}

.modal-login {
	width: 350px;
}
.modal-login .modal-content {
	padding: 20px;
	border-radius: 5px;
	border: none;
}
.modal-login .modal-header {
	border-bottom: none;
	position: relative;
	justify-content: center;
}
.modal-login .close {
	position: absolute;
	top: -10px;
	right: -10px;
}
.modal-login h4 {
	color: #636363;
	text-align: center;
	font-size: 20px;
	margin-top: 0;
	font-weight: bold;
}
.modal-login .modal-content {
	color: #999;
	border-radius: 1px;
	margin-bottom: 15px;
	background: #fff;
	border: 1px solid #f3f3f3;
	box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
	padding: 25px;
}
.modal-login .form-group {
	margin-bottom: 20px;
}
.modal-login label {
	font-weight: normal;
	font-size: 13px;
}
.modal-login .form-control {
	min-height: 38px;
	padding-left: 5px;
	box-shadow: none !important;
	border-width: 0 0 1px 0;
	border-radius: 0;
}
.modal-login .form-control:focus {
	border-color: #ccc;
}
.modal-login .input-group-addon {
	max-width: 42px;
	text-align: center;
	background: none;
	/* border-bottom: 1px solid #ced4da; */
	padding-right: 5px;
	border-radius: 0;
}
.modal-login .btn, .modal-login .btn:active {        
	font-size: 16px;
	font-weight: bold;
	background: #19aa8d !important;
	border-radius: 3px;
	border: none;
	min-width: 140px;
}
.modal-login .btn:hover, .modal-login .btn:focus {
	background: #179b81 !important;
}
.modal-login .hint-text {
	text-align: center;
	padding-top: 5px;
	font-size: 13px;
}
.modal-login .modal-footer {
	color: #999;
	border-color: #dee4e7;
	text-align: center;
	margin: 0 -25px -25px;
	font-size: 13px;
	justify-content: center;
}
.modal-login a {
	color: #fff;
	text-decoration: underline;
}
.modal-login a:hover {
	text-decoration: none;
}
.modal-login a {
	color: #19aa8d;
	text-decoration: none;
}	
.modal-login a:hover {
	text-decoration: underline;
}
.modal-login .fa {
	font-size: 21px;
	position: relative;
	top: 6px;
}
.trigger-btn {
	display: inline-block;
	margin: 100px auto;
}

#logo {
	width: 222px;
}

.input-group {
	margin: 3px;
}

i {
	margin-left: 5px;
	margin-bottom: 3px;
	border: none;
}

img {
	width: 150px;
}



</style>

<script type="text/javascript">
	$(document).ready(function() {
		$("#myModal").modal("show");
	});
	
	function check(){
		const f = document.passwordForm;
		
		let regExp = /^[A-Za-z0-9]{6,10}$/;
		
		if(! regExp.test(f.password.value.trim())) {
			f.password.focus();
			alert("비밀번호는 6 ~ 10자의 영어, 숫자입니다.")
			return false;
		}
		
		if(f.password.value !== f.password2.value) {
			alert("새 비밀번호와 비밀번호 확인이 일치하지 않습니다.")
			return false;
		}
		
		f.action="${pageContext.request.contextPath}/member/password_ok.do";
		f.submit();
		
		return true;
	}
</script>

</head>
<body>

<div class="jb-box">
  <video muted autoplay loop>
    <source src="${pageContext.request.contextPath}/resources/videos/video1.mp4" type="video/mp4">
  </video>

	<!-- Modal HTML -->
	<div id="myModal" class="modal fade" data-backdrop="static">
		<div class="modal-dialog modal-login">
			<div class="modal-content">
				<div class="modal-header">				
					<h4 class="modal-title"><img src="${pageContext.request.contextPath}/resources/images/logo_main.png" class="img" width="200px;" alt=""></h4>
				</div>
				<div class="modal-header">				
					<h4 class="modal-title">초기 비밀번호 변경</h4>
				</div>
				<div class="modal-body">
					<form name="passwordForm" method="post">
						<div class="form-group">
							<div class="input-group">
								<input type="text" class="form-control" name="password" placeholder="새 비밀번호" required="required">
							</div>
							<div class="input-group">
								<input type="text" class="form-control" name="password2" placeholder="비밀번호 확인" required="required">
							</div>
						</div>
						<div class="form-group">
							<button type="button" class="btn btn-primary btn-block btn-lg" onclick="check();">비밀번호 변경</button>
						</div>
						
					</form>
				</div>
				<div class="modal-footer"> 인사부  ☎ 1004 </div>
			</div>
		</div>
	</div>
</div>     
</body>
</html>