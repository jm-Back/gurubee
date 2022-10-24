<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<table class="table table-border table-hover table-form" id="empListTable">
	<c:forEach var="vo" items="${list}" varStatus="loop">
		<tr> 
			<td style="width: 10%;"> ${vo.name_apper} </td>
			<td style="width: 10%;"> ${vo.pos_name} </td>
			<td style="width: 50%;"> ${vo.dep_name} </td>
			<td style="display: none;"> ${vo.id_apper}</td>
		</tr>
	</c:forEach>
</table>