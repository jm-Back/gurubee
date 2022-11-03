<%@ page contentType="text/html; charset=UTF-8"%>
<%@page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">

<script type="text/javascript">
function ajaxFun(url, method, query, dataType, fn) {
	$.ajax({
		type:method,
		url:url,
		data:query,
		dataType:dataType,
		success:function(data) {
			fn(data);
		},
		beforeSend:function(jqXHR) {
			jqXHR.setRequestHeader("AJAX", true);
		},
		error:function(jqXHR) {
			if(jqXHR.status === 403) {
				login();
				return false;
			} else if(jqXHR.status === 400) {
				alert("요청 처리가 실패 했습니다.");
				return false;
			}
	    	
			console.log(jqXHR.responseText);
		}
	});
}

$(function(){
    // 실행과 동시에 처음 탭에 출력
    let url = "${pageContext.request.contextPath}/mini/month_list.do";
    let query = "";
    
});


function today() {
	let date = "${today}";
	$(".textDate").each(function (i) {
        let s = $(this).attr("data-date");
        if(s === date) {
        	$(this).parent().css("background", "#FFFFE4");
        }
    });
}


//월별 - 월을 변경하는 경우
function changeMonth(year, month) {
	let url = "${pageContext.request.contextPath}/schedule/month_list.do";
	let query = "year="+year+"&month="+month;
	
	schedule(url, query, "#nav-1");
}


</script>



</head>
<body>

	<main>
		<!-- 메인 화면 -->
		<div class="container py-4 ">
			<header class="pb-3 mb-4 border-bottom ">
				<jsp:include page="/WEB-INF/views/layout/header.jsp" />
				<jsp:include page="/WEB-INF/views/layout/sidebar.jsp" />
			</header>
		</div>
	
		<!-- 프로젝트 메인 등록, 내용 -->
	<div class="container" >
		<div class="body-container">
			<div class="project__title shadow p-3 pt-3 rounded">
				<div><i class="bi bi-calendar2-event"></i> 일정관리 </div>
			</div>
			
			<div class="body-main pt-3">
				<div class="tab-content" id="nav-tabContent">
					<div class="tab-pane fade show active" id="nav-1" role="tabpanel" aria-labelledby="nav-tab-1"></div>
					<div class="tab-pane fade" id="nav-2" role="tabpanel" aria-labelledby="nav-tab-2"></div>
					<div class="tab-pane fade" id="nav-3" role="tabpanel" aria-labelledby="nav-tab-2"></div>
				</div>
			</div>
			
		</div>
	</div>
	</main>
	
	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp" />
		<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>
	</footer>


</body>
</html>