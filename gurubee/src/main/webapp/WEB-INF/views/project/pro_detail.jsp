<%@ page contentType="text/html; charset=UTF-8"%>
<%@page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

	<!--  상세 프로젝트 챕터 추가하기 -->
<form id="project__detail__form" method="post">
<c:forEach var="deo" items="${list_detail}">
	<div class="row">
		<div class="col-md-12">
			<div class="p-3 mb-4 project__detail__design shadow p-1 rounded">
				<div class="row">
					<div class="col-md-12 pt-2 pb-4 justify-content-between">
						<img class="profile__project__detail" src="${pageContext.request.contextPath}/resources/images/${me.pro_profile}">
						<span class="font__project__detail">&nbsp;&nbsp; ${deo.pd_subject}</span>
						<input type="hidden" value="${deo.pd_writer}" name="pd_writer">
						<i class="fa-regular fa-pen-to-square edit__icon" data-bs-toggle="dropdown" aria-expanded="false"></i>
						<ul class="dropdown-menu">
							<li><a class="dropdown-item" href="${pageContext.request.contextPath}/project/">상세 프로젝트 수정</a></li>
							<li><a class="dropdown-item" href="${pageContext.request.contextPath}/project/">프로젝트 달성률 수정</a></li>
							<li><hr></li>
							<li><a class="dropdown-item " href="${pageContext.request.contextPath}/project/">상세 프로젝트 삭제</a></li>
						</ul>
					</div>
					<hr class="hr__style"> 
				</div>

				<div class="row">
					<div class="col-md-2 pb-3 detail__title" >
						세부 프로젝트 진행기간 
					</div>
					<input type="hidden" value="${deo.pd_code}" name="pd_code">
					<input type="date" class="col-md-3 pb-3 mt-3 detail__content" value="${deo.pd_sdate}" name="pd_sdate" readonly="readonly">
					<input type="date" class="col-md-3 pb-3 mt-3 detail__content" value="${deo.pd_edate}" name="pd_edate" readonly="readonly">
				</div>
				<div class="row">
					<div class="col-md-2 pb-3 detail__title" >
						세부 프로젝트 진행상황 
					</div>
					<div class="col-md-8">
						<div class=" progress progress-bar-striped  progress-bar-animated progress__design" role="progressbar" style="width: 103%" aria-valuenow="${deo.pd_ing}" aria-valuemin="0" aria-valuemax="${deo.pd_part}"></div>
						<input type="hidden" name="pd_part" value="${deo.pd_part}">
						<input type="hidden" name="pd_ing" value="${deo.pd_ing}">
					</div>
				</div>
				<div class="row mb-4">
					<div class="col-md-2 pb-3 detail__title">
						세부 프로젝트 내용
					</div>
					<textarea class="col-md-8 pb-3 detail__content__textarea" readonly="readonly">${deo.pd_content}</textarea>
				</div>
			</div>
		</div>
	</div>
</c:forEach>
</form>

<div class="page-navigation page__design">
	${paging}
</div>	