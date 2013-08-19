<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="/WEB-INF/pages/header.jsp" %>

<!--[if lte IE 8]><script language="javascript" type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/flot-0.8.1/excanvas.min.js"></script><![endif]-->
<script language="javascript" type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/flot-0.8.1/jquery.js"></script>
<script language="javascript" type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/flot-0.8.1/jquery.flot.js"></script>

<%--Tabs--%>
<div class="row-fluid">
    <div class="span8 offset1">
        <ul class="nav nav-pills" id="tabs">
            <li><a href="<c:url value="/pyramid/home"/>"><spring:message code="home"/></a></li>
            <security:authorize access="hasAnyRole('SUPER_ADMIN,ADMIN')">
                <li><a href="<c:url value="/pyramid/admin/"/>"><spring:message code="settings"/></a></li>
            </security:authorize>
            <li class="active"><a href="<c:url value="/pyramid/office"/>"><spring:message code="privateOffice"/></a></li>
            <li><a href="<c:url value="/pyramid/news"/>"><spring:message code="news"/></a></li>
            <li><a href="<c:url value="/pyramid/training"/>"><spring:message code="training"/></a></li>
            <li><a href="<c:url value="/pyramid/about"/>"><spring:message code="aboutProject"/></a></li>
            <li><a href="<c:url value="/pyramid/contacts"/>"><spring:message code="contacts"/></a></li>
        </ul>
    </div>
</div>
<div class="row-fluid">
    <div class="span10">
        <div id="tab-content" class="tab-content">
            <%--Content--%>


