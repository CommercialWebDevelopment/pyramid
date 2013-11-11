<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page session="false" %>

<%@ include file="/WEB-INF/pages/header.jsp" %>
<%--Tabs--%>
<div class="row-fluid">
    <div class="span10">
        <div class="navbar">
            <div class="navbar-inner">
                <div class="container">
                    <ul class="nav">
                        <li class="active"><a href="<c:url value="/app/home"/>"><spring:message code="home"/></a>
                        </li>
                        <security:authorize access="hasAnyRole('SUPER_ADMIN,ADMIN')">
                            <li><a href="<c:url value="/app/admin/"/>"><spring:message code="settings"/></a></li>
                        </security:authorize>
                        <li><a href="<c:url value="/app/office"/>"><spring:message code="privateOffice"/></a></li>
                        <li><a href="<c:url value="/app/news"/>"><spring:message code="news"/></a></li>
                        <li><a href="<c:url value="/app/training"/>"><spring:message code="training"/></a></li>
                        <li><a href="<c:url value="/app/about"/>"><spring:message code="aboutProject"/></a></li>
                        <li><a href="<c:url value="/app/contacts"/>"><spring:message code="contacts"/></a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="row-fluid">
    <div class="span10">
        <div id="tab-content" class="tab-content">
            <%--Content--%>


            <h3>Home</h3>

            <div class="hero-unit">
                <h1>Начните вместе с нами</h1>

                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore
                    et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut
                    aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse
                    cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in
                    culpa qui officia deserunt mollit anim id est laborum</p>

                <p>
                    <a class="btn btn-primary btn-large">
                        Узнать больше
                    </a>
                </p>
            </div>

<%@ include file="/WEB-INF/pages/footer.jsp" %>