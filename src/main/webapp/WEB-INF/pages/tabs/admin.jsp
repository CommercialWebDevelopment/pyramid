<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="/WEB-INF/pages/header.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/admin/admin.js"></script>

<%--Tabs--%>
<div class="row-fluid">
    <div class="span10">
        <div class="navbar">
            <div class="navbar-inner">
                <div class="container">
                    <ul class="nav">
                        <li><a href="<c:url value="/app/home"/>"><spring:message code="home"/></a></li>
                        <security:authorize access="hasAnyRole('SUPER_ADMIN,ADMIN')">
                            <li class="active"><a href="<c:url value="/app/admin/"/>"><spring:message code="settings"/></a></li>
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