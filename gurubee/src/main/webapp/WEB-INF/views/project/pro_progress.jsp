<%@ page contentType="text/html; charset=UTF-8"%>
<%@page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="progress progress__design">
	<div class="progress-bar progress__color " id="here_progress" data-pro_code="${dto.pro_code}" role="progressbar" style="width: ${progressCount}%" >${dto.pro_code}</div>
</div>