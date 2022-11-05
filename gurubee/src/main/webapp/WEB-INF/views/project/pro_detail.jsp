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
						<i class="fa-solid fa-circle-check edit__icon clear__detail ${deo.pd_ing > 0 ? 'change__color' : ''}" data-pd_code="${deo.pd_code}"></i>
						<i class="fa-regular fa-pen-to-square edit__icon" data-bs-toggle="dropdown" aria-expanded="false"></i>
						<ul class="dropdown-menu">
							<li class="dropdown-item updaprofile__project__detailte__detail update__detail" data-pd_code="${deo.pd_code}">프로젝트 챕터 수정</li>
							<li><hr></li>
							<li class="dropdown-item delete__detail" id="delete__detail" data-pd_code="${deo.pd_code}" data-pro_code="${deo.pro_code}">프로젝트 챕터 삭제</li>
						</ul>
							
						<img class="profile__project__detail" src="${pageContext.request.contextPath}/resources/images/${deo.pd_writer}">
						<span class="font__project__detail">&nbsp;&nbsp; ${deo.pd_subject}</span>
						<div style="margin-left: 42px; margin-top: 5px;">${deo.name_p}</<div>	
						<input type="hidden" value="${deo.pd_writer}" name="pd_writer">
						<input type="hidden" value="${deo.pd_subject}" name="pd_subject" id="form-pd_subject">
						<input type="hidden" value="${deo.pd_code}" name="pd_code" id="form-pd_code">
						

					</div>
					<hr class="hr__style"> 
				</div>
		</div>
				<div class="row">
					<div class="col-md-2 pb-3 detail__title" >
						프로젝트 챕터 진행기간 
					</div>
					<input type="hidden" value="${deo.pd_code}" name="pd_code">
					<input type="date" class="col-md-3 pb-3 mt-3 detail__content" value="${deo.pd_sdate}" name="pd_sdate" readonly="readonly" id="form-pd_sdate">
					<input type="date" class="col-md-3 pb-3 mt-3 detail__content" value="${deo.pd_edate}" name="pd_edate" readonly="readonly" id="form-pd_edate">
				</div>
				<div class="row">
					<div class="col-md-2 pb-3 detail__title" >
						프로젝트 챕터 진행상황 
					</div>
					<div class="col-md-8">
						<div class="progress progress__design" >
							<div class="progress-bar ${deo.pd_ing > 0 ? 'progress__color':''}" role="progressbar" style="width: ${deo.pd_ing > 0 ? '100':''}%">
								<input type="hidden" name="pd_part" value="${deo.pd_part}">
								<input type="hidden" name="pd_ing" value="${deo.pd_ing}">
							</div>
						</div>	
					</div>
				</div>
				<div class="row mb-4">
					<div class="col-md-2 pb-3 detail__title">
						프로젝트 챕터 내용
					</div>
					<textarea class="col-md-8 pb-3 detail__content__textarea" readonly="readonly">${deo.pd_content}</textarea>
				</div>
			</div>
		</div>
		</div>
	</div>
</c:forEach>
</form>

<div class="page-navigation page__design">
	${paging}
</div>	