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
::-webkit-scrollbar { 
	width: 15px;
} 

::-webkit-scrollbar-thumb { 
background: linear-gradient(#01d6b7, #ffe498);
background-clip: padding-box;
border: 3px solid transparent;
border-radius: 10px; /*스크롤바 라운드*/}

::-webkit-scrollbar-track { 
background-color: #fff; /*스크롤바 트랙 색상*/ 
border-radius: 10px; /*스크롤바 트랙 라운드*/ 
 /*스크롤바 트랙 안쪽 그림자*/}


.btn_projectAdd {
	border-radius: 60px;
	font-size: 23px;
	font-weight:600;
	cursor: pointer;
	color: white;
	background-color: #01d6b7;
	opacity: 80%;
	width: 300px;
	height: 80px;
	border: none;

};

.btn_projectAdd:hover {
	opacity: 100%;
}

.card {
	border: none;
	border-radius: 10px;
};

.p-details span {
	font-weight: 300;
	font-size: 13px;
};


.p_photo {
    width: 50px;
    height: 50px;
    background-color: #eee;
    border-radius: 15px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 39px
};

.p_state {
    background-color: #fffbec;
    width: 60px;
    height: 25px;
    padding-bottom: 3px;
    padding-right: 5px;
    border-radius: 5px;
    display: flex;
    color: #fed85d;
    justify-content: center;
    align-items: center;
};

.progress {
    height: 10px;
    border-radius: 10px
}

.progress__design {
	margin-top: 20px;
	height: 30px;
	width: 100%;
	border-radius: 30px;
	margin-right: 30px;
	
}

.progress__color {
	background: linear-gradient(to right ,#01d6b7, #ffe498);
	margin: 7px;
	border-radius: 30px;
}

.text1 {
    font-size: 14px;
    font-weight: 600
}

.text2 {
    color: #a5aec0
}

.pointer {
 	cursor: pointer;
}

.clear__state {
	padding: 2px 14px;
	display: inline-block;
	border: 3px solid  #ffd980 ;
	outline: none;
	border-radius: 10px;
	font-size: 19px;
	font-weight: 600;
	background: #ffd980;
	margin-left: 35px;

}


.clear__state__wan {
	padding: 2px 14px;
	display: inline-block;
	border: 3px solid  #ccff99 ;
	outline: none;
	border-radius: 10px;
	font-size: 19px;
	font-weight: 600;
	background: #ccff99;
	margin-left: 35px;
}


.profile__small {
	height: 60px;
	width: 60px;
	object-fit: cover;
    border-radius: 100%;
    border: 2px solid #eee ;
    padding: 3px;
}

h6 {
	font-weight: 600;
}

.btn__list {
	margin-right: 8px;
	margin-top: 20px;
	border-radius: 12px;
	padding: 5px 10px;
	text-align: center;
	font-size: 19px;
	border: 1px solid lightgray;

}


.location__btn {
	float: right;
}

.main__board {
	background-color: #f7f7f7;
	
}

</style>

<script type="text/javascript">

function ajaxFun(url, method, query, dataType, fn) {
	$.ajax({
		type:method,
		url:url,
		data:query,
		dataType:dataType,
		success:function(data){
			fn(data);
		},
		beforeSend:function(jqXHR){
			//ajax 라고 서버한테 한 번 던져주는거다.
			//내가 만든 서버 ajax 란 이름으로 보냄
			//바디보다 헤더가 더 먼저 서버로 가기 때문에, 헤더에 ajax 보내고
			//서블릿에서 보낸것에서 if return 해본다.
			jqXHR.setRequestHeader("AJAX", true);
			
		},
		error:function(jqXHR){
			if(jqXHR.status === 403) {
				login();
				return false;
			}else if(jqXHR.status === 400){
				alert("요청 처리가 실패했습니다.");
				return false;
			}
			console.log(jqXHR.responseText);
		}
	});
}


$(function(){
	listPage(1);
});

function listPage(page){
	let url = "${pageContext.request.contextPath}/project/list_pro.do";

	let query = "pageNo="+page;
	let selector = "#here_pro_list";
	
	const fn = function(data){
		 $(selector).html(data);
		 
	};
	
	ajaxFun(url, "get", query, "html", fn);
	
}


$(function(){
	$("#dept").click(function(){
		let pro_type = $(this).val();

		let url = "${pageContext.request.contextPath}/project/list__filter.do";
		let query = "pro_type="+pro_type;
		let selector = "#here_pro_list";
		
		const fn = function(data){
			$(selector).html(data);
		};
		
		ajaxFun(url, "get", query, "html", fn);

		
	});
});

$(function(){
	$("#per").click(function(){
		let pro_type = $(this).val();

		let url = "${pageContext.request.contextPath}/project/list__filter.do";
		let query = "pro_type="+pro_type;
		let selector = "#here_pro_list";
		
		const fn = function(data){
			$(selector).html(data);
		};
		
		ajaxFun(url, "get", query, "html", fn);

		
	});
});

$(function(){
	$("#together").click(function(){
		let pro_type = $(this).val();

		let url = "${pageContext.request.contextPath}/project/list__filter.do";
		let query = "pro_type="+pro_type;
		let selector = "#here_pro_list";
		
		const fn = function(data){
			$(selector).html(data);
		};
		
		ajaxFun(url, "get", query, "html", fn);

		
	});
});



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
		</div>
	
		<!-- 프로젝트 메인 등록, 내용 -->
<div class="container" >	
	<form name="projectAdd" method="post">
		<div class="location__btn">
			<button type="button" class="btn_projectAdd shadow p-1 rounded " onclick="location.href='${pageContext.request.contextPath}/project/write.do'"><i class="fa-solid fa-plus"></i> 새 프로젝트</button>
		</div>
	</form>
</div>

<form  name="projectList" method="post">
<div class="container">
	<div >
		<span>
			<input type="radio"  class="btn-check" name="pro_type" id="all" onclick="location.href='${pageContext.request.contextPath}/project/list.do';" value="all__list"  required>
	            <label class="btn btn__list  shadow-sm " for="all"> 전체 프로젝트 </label>
			<input type="radio"  class="btn-check" name="pro_type" id="dept" value="부서 프로젝트"  required>
	            <label class="btn btn__list  shadow-sm" for="dept"> 부서 프로젝트 </label>
			<input type="radio"  class="btn-check" name="pro_type" id="per"  value="개인 프로젝트"  required>
	            <label class="btn btn__list  shadow-sm" for="per"> 개인 프로젝트 </label>
			<input type="radio"  class="btn-check" name="pro_type" id="together"  value="협업 프로젝트" required>
	            <label class="btn btn__list  shadow-sm" for="together"> 협업 프로젝트 </label>
		</span>
	</div>
</div>
	
	<!-- 리스트 body -->
	<div class="container mt-5 mb-3 pt-4 pb-3 main__board" id="here_pro_list">
	</div>	
</form>

	</main>
	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp" />
	</footer>

</body>

</html>
