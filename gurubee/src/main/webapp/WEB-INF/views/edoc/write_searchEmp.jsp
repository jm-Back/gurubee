<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<select class="form-select searchEmpSelect" aria-label="Default select example" name="empSelectOption" data-posCode="${pos_code}">
	<c:forEach var="vo" items="${list}" varStatus="loop">
		<option value="${vo.id_apper}" data-name="${vo.name_apper}" data-posName="${vo.pos_name}"> 
			${vo.dep_name}&nbsp;${vo.name_apper}&nbsp;${vo.pos_name}
		</option>
	</c:forEach>
</select>
