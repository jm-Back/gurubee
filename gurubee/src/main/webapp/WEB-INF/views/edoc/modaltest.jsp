<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>GURUBEE</title>
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>

<script type="text/javascript">
$(function(){
	$(".btnClose").click(function(){
		$("#myDialogModal2").modal("hide");
	});
});

$(function(){
	$(".btnDialog3").click(function(){
		$("#myDialogModal3").modal("show");
	});
});

$(function(){
	$(".btnDialog4").click(function(){
		$("#myDialogModal4").modal("show");
	});
});
</script>
</head>

<body>

<header>
	<div class="container-fluid mb-2 p-3 bg-success text-white">
		<h5>Modal dialog</h5>
	</div>
</header>
	
<main>
	<div class="container-fluid mb-2 p-2">
		<button type="button" class="btn btn-primary"
			data-bs-toggle="modal" data-bs-target="#myDialogModal">대화상자</button>
		<button type="button" class="btn btn-primary"
			data-bs-toggle="modal" data-bs-target="#myDialogModal2">대화상자 2</button>
		<button type="button" class="btn btn-primary btnDialog3">대화상자 3</button>
		<button type="button" class="btn btn-primary btnDialog4">대화상자 4</button>
	</div>
</main>

<footer>
</footer>

	<div class="modal fade" id="myDialogModal" tabindex="-1" 
		aria-labelledby="myDialogModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="myDialogModalLabel">대화상자제목</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			<div class="modal-body">
        		<h4>대화상자</h4>
        		<p>대화상자 밖을 클릭하면 대화상자가 사라진다.</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
				<button type="button" class="btn btn-primary">등록하기</button>
			</div>
		</div>
	</div>
</div>

</body>
</html>