<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>spring</title>
<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp"/>

<style type="text/css">
.body-container {
	max-width: 800px;
}
</style>

</head>
<body>

<header>
	<jsp:include page="/WEB-INF/views/layout/header.jsp"/>
</header>

<main>
	<div class="container">
		<div class="body-container">	

	        <div class="row justify-content-md-center mt-5">
	            <div class="col-md-8">
	                <div class="border bg-light mt-5 p-4">
                        <h4 class="text-center fw-bold">${title}</h4>
                        <hr class="mt-4">
                        
		                <div class="d-grid p-3">
							<p class="text-center">${message}</p>
		                </div>
                        
                        <div class="d-grid">
                            <button type="button" class="btn btn-lg btn-primary">확인 <i class="bi bi-check2"></i> </button>
                        </div>
	                </div>

	            </div>
	        </div>

		</div>
	</div>
</main>

<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</footer>

<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>

</body>
</html>