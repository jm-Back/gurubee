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
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/se2/js/service/HuskyEZCreator.js" charset="utf-8"></script>


<script type="text/javascript">

function sendOk() {

  const f = document.writeForm;
  
  let id_write = '<%=(String)session.getAttribute("id")%>';
  let name_write = '<%=(String)session.getAttribute("name")%>';
  let date = document.getElementById("today");
  let id_apper = document.getElementById("id_apper");
  
  
  str = f.subject.value.trim();
  if(!str) {
      alert("제목을 입력하세요. ");
      f.subject.focus();
      return false;
  }
  
  return true;
}

$(function(){
	$(".empRemoveBtn").hide();
	$('input[name=date]').attr('value', year+"-"+month+"-"+day);
	
    $(".empAddBtn").click(function(){
        $(".empRemoveBtn").show();
        const p = $(this).parent().parent().find("div:first-child  :first").clone().wrapAll("<div>").parent().html();
        $(p).find("input").each(function(){
        	$(this).val("");
        });

        $(this).parent().parent().find("div:first").append(p);
    });
    
    $("body").on("click", ".empRemoveBtn", function(){
        if($(this).closest("div").find("p").length<=1) { // data-empSearch="1"
            return;
        }
        
        $(this).parent().remove();
        
        if($(".empRemoveBtn").closest("div").find("p").length<=1) {
            $(".empRemoveBtn").hide();
        }
    });
    /*
    $("form input[name=empSearch]").click(function() {
		alert("사원 검색");
		// EdocDAO - insertEmp() AJAX 로 가져오기
	});
    */
    
    $("body").on("click", ".empSearch", function() {
		alert("사원 검색");
		// EdocDAO - insertEmp() AJAX 로 가져오기
	});
    
    
});

function changeEdocValue() { // 문서구분 값 가져오기
	var edoc = document.getElementById("edoc");
	app_doc = edoc.options[edoc.selectedIndex].value;
	
	// alert("셀렉트 : "+value);
}


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
			
		<div>
			<form action="" method="post" name="writeForm">
				<table class="table table-border table-form">
					<tr>
						<th class="fs-6">문서구분</th>
						<td>
							<!-- 
							<div class="btn-group-vertical" role="group" aria-label="Vertical button group">	
							<button type="button" class="btn btn-dark" id="">휴가신청서</button>
							<button type="button" class="btn btn-dark" id="">DB접근권한신청서</button>
							<button type="button" class="btn btn-dark" id="">구매요청의뢰서</button>
							<button type="button" class="btn btn-dark" id="">재택근무신청서</button>
							<button type="button" class="btn btn-dark" id="">법인카드지출결의서</button>
							<button type="button" class="btn btn-dark" id="">출장신청서</button>
							</div>
							 -->
							<select class="form-select" aria-label="Default select example" name="edoc" id="edoc"
								onchange="changeEdocValue()" style="width: 50%;">
								<option selected>문서구분 선택</option>
								<option value="휴가신청서">휴가신청서 - 각종 휴가 신청서 사용 양식</option>
								<option value="접근권한신청서">DB접근권한신청서 - DB 계정 신청시 사용 양식</option>
								<option value="구매요청의뢰서">구매요청의뢰서 - 회사 비품 구매 신청서 사용 양식</option>
								<option value="재택근무신청서">재택근무신청서 - 재택근무 신청 양식</option>
								<option value="법인카드지출결의서">법인카드지출결의서 - 법인카드 지출결의를 위한 양식</option>
								<option value="출장신청서">출장신청서 - 출장 전 품의 결재시 사용 양식</option>
							</select>
						</td>
					</tr>
					
					<tr>
						<th class="fs-6">작성자</th>
						<td class="fs-6">
							<input type="text" name="name" value="${sessionScope.member.name}" class="form-control" readonly="readonly">
						</td>
					</tr>
					
					<tr>
						<th class="fs-6">작성일자</th>
						<td>
							<input type="text" name="date" value="${sessionScope.member.name}" class="form-control" readonly="readonly">
						</td>
					</tr>
					
					<tr>
						<th class="fs-6">수신자</th>
						<td>
							<div>
								<div>
                            		<p>
                                		<input type="text" name="empSearch" class="form-control empSearch"
                                		style="width: 27%;" placeholder="사원 검색" readonly="readonly">
                                 		<span class="empRemoveBtn" style="float: right;"><i class="far fa-minus-square"></i></span>
                            		</p>
                        		</div>
                        		<div style="margin-top: 10px;">
                            		<button type="button" class="btn btn-success empAddBtn">수신자 추가</button>
                        		</div>
							</div>
						</td>
					</tr>
			
					<tr>
						<th class="fs-6">상세내용</th>
						<td>
							<textarea name="content" id="ir1" class="form-control" style="width: 50%; height:270px; ">${dto.content}</textarea>
							
						</td>
					</tr>
					
					<tr>
						<th class="fs-6">첨부파일</th>
						<td> 
							<div class="mb-3">
  							<input class="form-control" type="file" id="formFileMultiple" multiple style="width: 50%;">
							</div>
						</td>
					</tr>
				</table>
				
				<div style="text-align: right;">
					<button type="button" name="tempOk();" class="btn btn-secondary" style="font-size: 17px;">임시작성</button>
				</div>
				
				<div style="text-align: center;">
					<button type="button" name="sendOk();" class="btn btn-success" style="font-size: 20px;">결제요청</button>
				</div>
				
			</form>
		</div>
	
		</div>
		
	</main>

	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp" />
	</footer>


<script type="text/javascript" src="${pageContext.request.contextPath}/resources/se2/js/service/HuskyEZCreator.js" charset="utf-8"></script>
<script type="text/javascript">

const date = new Date();

const year = date.getFullYear();
const month = date.getMonth() + 1;
const day = date.getDate();

var oEditors = [];
nhn.husky.EZCreator.createInIFrame({
	oAppRef: oEditors,
	elPlaceHolder: "ir1",
	sSkinURI: "${pageContext.request.contextPath}/resources/se2/SmartEditor2Skin.html",
	fCreator: "createSEditor2"
});

function submitContents(elClickedObj) {
	 oEditors.getById["ir1"].exec("UPDATE_CONTENTS_FIELD", []);
	 try {
		// elClickedObj.form.submit();
		return check();
	} catch(e) {
	}
}

</script>

<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>	
</body>

</html>