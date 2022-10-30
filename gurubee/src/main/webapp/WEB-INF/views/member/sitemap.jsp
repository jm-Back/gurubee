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

ul {
	display: flex;
}

li {
	margin: 10px;
	list-style: none;
}

a {
	text-decoration: none;
	color: black;
}


h4 {
	margin-left: 50px;
}

a:link, a:visited {
     background-color: darkgray;
     color: black;
     padding: 8px 10px;
     text-align: center;
     text-decoration: none;
     display: inline-block;
     border-radius: 10px;
}
 a:hover, a:active {
     font-weight: bold;
}

</style>
</head>
<body>

<h4>전자결재(소연님)</h4>
<ul>
	
   
   <li><a href="${pageContext.request.contextPath}/edoc/write.do">결재문서 작성폼</a></li>
   		<!-- 결재문서 작성 완료  /edoc/write_ok.do -->
   <li><a href="${pageContext.request.contextPath}/edoc/confirm.do">결재하기</a></li>
   		<!-- 결재하기 완료  /edoc/confirm_ok.do -->
   		<li><a href="${pageContext.request.contextPath}/edoc/list.do">결재문서 리스트</a></li>
   <li><a href="${pageContext.request.contextPath}/edoc/list_receive.do">결재문서 리스트 수신함</a></li>
   <li><a href="${pageContext.request.contextPath}/edoc/list_send.do">결재문서 리스트 발신함</a></li>
   <li><a href="${pageContext.request.contextPath}/edoc/update.do">결재문서 수정폼</a></li>
   		<!-- 결재문서 수정 완료  /edoc/update_ok.do -->
   <li><a href="${pageContext.request.contextPath}/edoc/list_temp.do">결재문서 임시보관함</a></li>
   <li><a href="${pageContext.request.contextPath}/edoc/doc.do">결재문서 글보기</a></li>
</ul>   
<hr>
<h4>마이페이지(형우님)</h4>
<ul>
   		<!-- 마이페이지  /mypage/mypage.do -->
   	<li><a href="${pageContext.request.contextPath}/mypage/mypage.do">마이페이지</a></li>
   	<li><a href="${pageContext.request.contextPath}/mypage/mypage_update.do">개인정보 수정폼</a></li>
   		<!-- 개인정보 수정 완료(DB저장)  /mypage/mypage_update_ok.do -->
</ul>
 <ul>  		
   		<!-- 근태 관리 /mypage/att.do -->
 	<li><a href="${pageContext.request.contextPath}/mypage/myatt.do">개인근태관리</a></li>
   	<li><a href="${pageContext.request.contextPath}/mypage/myatt_list.do">월별근태관리 조회</a></li>
   	<li><a href="${pageContext.request.contextPath}/mypage/myatt_write.do">출퇴근등록</a></li>
   		<!-- 출퇴근 등록 완료(DB저장)  /mypage/myatt_write_ok.do -->
   	<!-- 
   	<li><a href="${pageContext.request.contextPath}/mypage/myatt_article.do">총 근무시간 출력</a></li>
   		리스트에 최초 근무일자랑 현재일 불러와서 계산해서 출력...? -->
</ul>
 <ul>    
 	<li><a href="${pageContext.request.contextPath}/mypage/myoff.do">개인연차관리</a></li>
   	<li><a href="${pageContext.request.contextPath}/mypage/myoff.do">연차 사용 현황</a></li>
   	<li><a href="${pageContext.request.contextPath}/mypage/myoff_list.do">연차 신청 내역</a></li>
   	<li><a href="${pageContext.request.contextPath}/mypage/mypage_write.do">연차/휴가 신청</a></li>
</ul>
 <ul>   	
   	<li><a href="${pageContext.request.contextPath}/mypage/mypay.do">개인급여관리</a></li>
   <!-- URI 미수정
   	<li><a href="${pageContext.request.contextPath}/mypage/mypay_article.do">월별 급여 명세서 조회</a></li>
   <li><a href="${pageContext.request.contextPath}/mypage/mypay_article.do">급여명세서 출력</a></li>
   <li><a href="${pageContext.request.contextPath}/mypage/mypay_article.do">개인정보 수정폼</a></li> -->
</ul>
<hr>   
   
<h4>업무일지, 프로젝트(정민님)</h4>
<ul>   
	
   
   <li><a href="${pageContext.request.contextPath}/work/list.do">업무일지 리스트</a></li>
   <li><a href="${pageContext.request.contextPath}/work/write.do">업무일지 등록폼</a></li>
   		<!-- 업무일지 등록 (DB 저장) /work/write_ok.do -->
   <li><a href="${pageContext.request.contextPath}/work/update.do">업무일지 수정</a></li>
   		<!-- 업무일지 수정 완료 (DB 저장) /work/update_ok.do -->
   		<!-- 업무일지 삭제 완료 (DB 저장) /work/delete_ok.do -->
</ul>   
<ul>	
	
   
   <li><a href="${pageContext.request.contextPath}/project/list.do">팀 프로젝트 리스트</a></li>
   <li><a href="${pageContext.request.contextPath}/project/list.do">개인 프로젝트 리스트</a></li>
   <li><a href="${pageContext.request.contextPath}/project/write.do">프로젝트 등록폼</a></li>
   		<!-- 프로젝트 등록  /project/write_ok.do -->
   <li><a href="${pageContext.request.contextPath}/project/article.do">프로젝트 보기(상세내역, 달성률 등)</a></li>
   <li><a href="${pageContext.request.contextPath}/project/progress.do">프로젝트 진행 수정폼</a></li>
   		<!-- 프로젝트 진행 수정 완료  /progress_ok.do -->
   <li><a href="${pageContext.request.contextPath}/project/update.do">프로젝트 수정폼</a></li>
   		<!-- 프로젝트 수정 완료 /update_ok.do -->
   <li><a href="${pageContext.request.contextPath}/project/delete_ok.do">프로젝트 삭제</a></li>
</ul>   

<ul>
	
	
   <li><a href="${pageContext.request.contextPath}/schedule/mlist.do">미니 달력</a></li>
   <li><a href="${pageContext.request.contextPath}/schedule/mlist.do">일정 리스트(월간)</a></li>
   <li><a href="${pageContext.request.contextPath}/schedule/wlist.do">일정 리스트(주간)</a></li>
   <li><a href="${pageContext.request.contextPath}/schedule/wlist.do">일정 리스트(주간)</a></li>
   <li><a href="${pageContext.request.contextPath}/schedule/main.do">일정 상세보기</a></li>
   <li><a href="${pageContext.request.contextPath}/schedule/write.do">일정 등록폼</a></li>
   		<!-- 일정 등록 /write_ok.do -->
   <li><a href="${pageContext.request.contextPath}/schedule/write.do">일정 수정폼</a></li>
   		<!-- 일정 수정 완료 /write_ok.do -->
   		<!-- 일정 삭제 /delete_ok.do -->
</ul>
<hr> 		
<h4>인사관리, 급여관리, 근태관리, 쪽지(유선님)</h4>
<ul>
	
 
   <li><a href="${pageContext.request.contextPath}/employee/write.do">신입사원 정보등록폼</a></li>
   		<!-- 신입사원 정보등록  완료 /write_ok.do -->
   <li><a href="${pageContext.request.contextPath}/employee/update.do">인사이동 (수정)</a></li>
   <li><a href="${pageContext.request.contextPath}/salary/sal_write.do">연봉정보등록폼</a></li>
   <li><a href="${pageContext.request.contextPath}/salary/sal_update.do">연봉정보 수정</a></li>
   <li><a href="${pageContext.request.contextPath}/salary/sal_list.do">연봉정보 리스트</a></li>
</ul>   

<ul>
	
   
   <li><a href="${pageContext.request.contextPath}/payment/write.do">급여정보등록 폼</a></li>
   		<!--  급여정보등록 완료 /write_ok.do --> 
   <li><a href="${pageContext.request.contextPath}/payment/update.do">급여정보 수정</a></li>
   		<!-- 급여정보 수정완료 update_ok.do -->
   <li><a href="${pageContext.request.contextPath}/payment/list.do">급여정보 리스트</a></li>
   
   <li><a href="${pageContext.request.contextPath}/attendance/write.do">근태등록</a></li>
   
   <!-- 쪽지 -->
   <li><a href="${pageContext.request.contextPath}/attendance/write.do">쪽지함</a></li>
</ul>  
<hr>   
<h4>게시판(회사공지, 부서공지, 커뮤니티) (재혁님)</h4>
<ul>
   
   <li><a href="${pageContext.request.contextPath}/comp_notice/list.do">회사공지 글 리스트</a></li>
   <li><a href="${pageContext.request.contextPath}/comp_notice/write.do">회사공지 글쓰기 폼</a></li>
   <li><a href="${pageContext.request.contextPath}/comp_notice/write_ok.do">회사공지 글 등록</a></li>
   <li><a href="${pageContext.request.contextPath}/comp_notice/article.do">회사공지 글 보기</a></li>
   <li><a href="${pageContext.request.contextPath}/comp_notice/update.do">회사공지 글 수정폼</a></li>
   		<!-- 회사공지 글 수정 완료 /comp_notice/update_ok.do -->
   		<!-- 회사공지 글 삭제 /comp_notice/delete_ok.do -->
</ul>
<ul>   		
   <li><a href="${pageContext.request.contextPath}/dep_notice/list.do">부서공지 글 리스트</a></li>
   <li><a href="${pageContext.request.contextPath}/dep_notice/write.do">부서공지 글쓰기 폼</a></li>
   <li><a href="${pageContext.request.contextPath}/dep_notice/write_ok.do">부서공지 글 등록</a></li>
   <li><a href="${pageContext.request.contextPath}/dep_notice/article.do">부서공지 글 보기</a></li>
   <li><a href="${pageContext.request.contextPath}/dep_notice/update.do">부서공지 글 수정폼</a></li>
   		<!-- 부서공지 글 수정 완료 /dep_notice/update_ok.do -->
   		<!-- 부서공지 글 삭제 /dep_notice/delete_ok.do -->
</ul>   
<ul>	
   <li><a href="${pageContext.request.contextPath}/community/list.do">커뮤니티 글 리스트</a></li>
   <li><a href="${pageContext.request.contextPath}/community/write.do">커뮤니티 글쓰기 폼</a></li>
   <li><a href="${pageContext.request.contextPath}/community/write_ok.do">커뮤니티 글 등록</a></li>
   <li><a href="${pageContext.request.contextPath}/community/article.do">커뮤니티 글 보기</a></li>
   <li><a href="${pageContext.request.contextPath}/community/update.do">커뮤니티 글 수정폼</a></li>
   		<!-- 커뮤니티 글 수정 완료 /community/update_ok.do -->
   		<!-- 커뮤니티 글 삭제 /community/delete_ok.do -->
</ul>	
   
  
   
   
   
   
   
   


</body>
</html>