<%@ page contentType="text/html; charset=UTF-8"%>
<%@page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="row">
		<c:forEach var="dto" items="${list}" varStatus="status">
			<div class="col-md-4 ">
				<div onclick="location.href='${pageContext.request.contextPath}/project/article.do?pro_code=${dto.pro_code}&pd_code=${dto.pd_code}&id_p=${dto.id_p}'" class="card p-3 mb-4 pointer shadow p-1 rounded">
					<div class="d-flex justify-content-between">
						<div class="d-flex flex-row align-items-center">
							<div class="p_photo"></div>
								<div><img class="profile__small" src="${pageContext.request.contextPath}/resources/images/${dto.pro_profile}"></div>
							<div class="side__place" >
								<div class="ms-2 p-details">
									<input type="hidden" value="${dto.pro_code}" id="pro_code"> 
									<input type="hidden" value="${dto.pd_code}">
									<input type="hidden" value="${dto.id_p}" >
									<h6 class="mb-0 font__bold">${dto.name_p}</h6> <span>${dto.pro_sdate} ~ ${dto.pro_edate}</span>
									<div class="clear__state">${dto.pro_clear}</div>
								</div>
							</div>
						</div>
					</div>
					<div class="mt-5">
						<h3 class="heading">${dto.pro_name}</h3>
						<div class="mt-3">
							<p style="color: #404040;">${dto.pro_outline}</p>
							<div id="here_progress_main">
							</div>
							<div class="mt-3"><span class="text1">${dto.pro_type}</span> &nbsp; <span class="text2">[프로젝트 작성자 : ${dto.pro_writer}]</span></div>
						</div>
					</div>
				</div>
			</div>
		</c:forEach>
<div class="page-navigation page__design">
	${paging}
</div>	
		
</div>

		
