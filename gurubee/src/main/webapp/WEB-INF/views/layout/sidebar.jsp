<%@ page contentType="text/html; charset=UTF-8"%>
<%@page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script src="https://kit.fontawesome.com/6bed0ac21e.js" ></script>

<style type="text/css">

/* VARIABLES CSS */
:root {
	/*메뉴바*/
    --nav--width: 70px;

    /* 배경화면,색상 */
    --bg-color: #00D1B3;
    --sub-color: #fff;
    --white-color: #fff;

    /* Fuente y tipografia */
    --body-font: 'Poppins', sans-serif;
    --normal-font-size: 1rem;
    --small-font-size: .875rem;

    /* z index */
    --z-fixed: 10;
} 

/* BASE */
*, ::before, ::after {
    box-sizing: border-box;
}


/* l NAV */
.side_l-navbar {
    position: fixed;
    top: 0;
    left: 0;
    width: var(--nav--width);
    height: 100vh;
    background-color: var(--bg-color);
    color: var(--white-color);
    padding: 2rem .9rem 2rem;
    transition: .10s;
}


/* NAV */
.side_nav {
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    overflow: hidden;
}

.side_nav__brand {
    display: grid;
    grid-template-columns: max-content max-content;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 2rem;
}

.side_nav__toggle {
    font-size: 1.5rem;
    padding: .6rem;
    cursor: pointer;
}

.side_nav__logo {
    color: var(--white-color);
    font-weight: 600;
    padding: .3rem;
}

.side_nav__link {
    display: grid;
    grid-template-columns: max-content max-content;
    align-items: center;
    column-gap: .75rem;
    padding: .6rem;
    color: var(--white-color);
    border-radius: .5rem;
    margin-bottom: 1rem;
    transition: .3s;
    cursor: pointer;
}

.side_nav__icon {
    font-size: 20px;
}

/* Add padding body*/
.body-pd {
    padding: 2rem 0 0 16rem;
}


.bott {
	position: fixed;
	bottom: 0;
	padding-bottom: 30px;
}

.size__big {
	font-size: 22px;
}

</style>


<body id="body-pd">
    <div class="side_l-navbar" id="navbar">
        <nav class="side_nav">
            <div>
                <div class="side_nav__brand">
                    <i class="fa-solid fa-bars side_nav__toggle"></i>
                </div>
                <div class="">
                    <a href="${pageContext.request.contextPath}/main.do" class="side_nav__link active">
                       <i class="fa-solid fa-house-chimney  side_nav__icon"></i>
                    </a>
                    <a href="${pageContext.request.contextPath}/mypage/myatt.do" class="side_nav__link active">
                       <i class="fa-solid fa-clipboard-user size__big"></i>
                    </a>
                    <a href="${pageContext.request.contextPath}/mypage/mypage.do" style="float: left" class="side_nav__link">
                       <i class="fa-regular fa-user side_nav__icon"></i>
                    </a>
                    <a href="${pageContext.request.contextPath}/edoc/list_send.do" class="side_nav__link">
                        <i class="fa-solid fa-file-signature size__big"></i>
                    </a>
                    <a href="${pageContext.request.contextPath}/project/list.do" class="side_nav__link">
                        <i class="fa-regular fa-folder-open size__big"></i>
                    </a>
                    <a href="${pageContext.request.contextPath}/comp_notice/list.do" class="side_nav__link">
                        <i class="fa-regular fa-rectangle-list side_nav__icon"></i>
                    </a>
                    <a href="${pageContext.request.contextPath}/schedule/main.do" class="side_nav__link ">
                        <i class="fa-regular fa-calendar-check size__big"></i>
                    </a>
	            </div>
	        </div>
        </nav>
    </div>

