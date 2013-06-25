<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel=stylesheet
          href="${pageContext.request.contextPath}/resources/javascript/bootstrap/css/bootstrap.css"
          type="text/css">
    <link rel=stylesheet href="${pageContext.request.contextPath}/resources/css/loader.css" type="text/css">
    <link rel=stylesheet href="${pageContext.request.contextPath}/resources/css/private-office.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/javascript/flexigrid/css/flexigrid.css" type="text/css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/jquery-1.10.0/jquery-1.10.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/bootstrap/js/bootstrap.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/main.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/office.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/settings.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/alerts.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/flexigrid/js/flexigrid.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/admin.js"></script>
</head>
<body>

<%@ include file="/WEB-INF/pages/i18n.jsp" %>

<div class="row-fluid" style="height: 20px"></div>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span10 offset2">
            <%--Header--%>
            <div class="row-fluid" style="height: 80px;">
                <div class="span8">
                    Название
                </div>
                <div class="span4">
                    <div class="span2">
                        <img src="<c:url value="/resources/images/flag-en.png"/>">
                        <a href="?lang=en">en</a>
                    </div>
                    <div class="span2">
                        <img src="<c:url value="/resources/images/flag-ru.png"/>">
                        <a href="?lang=ru">ru</a>
                    </div>
                </div>
            </div>
                <%--Tabs--%>
                <div class="row-fluid">
                    <div class="span8 offset1">
                        <ul class="nav nav-pills" id="tabs">
                            <li class= <%= request.getAttribute("page-name").equals("home") ? "active" : "" %>><a href="${pageContext.request.contextPath}/pyramid/home"><spring:message code="home"/></a></li>
                            <security:authorize access="hasAnyRole('ADMIN','SUPER_ADMIN')">
                                <li class= <%= request.getAttribute("page-name").equals("admin") ? "active" : "" %>><a href="${pageContext.request.contextPath}/pyramid/admin"><spring:message code="settings"/></a></li>
                            </security:authorize>
                            <li class= <%= request.getAttribute("page-name").equals("office") ? "active" : "" %>><a href="${pageContext.request.contextPath}/pyramid/office"><spring:message code="privateOffice"/></a></li>
                            <li class= <%= request.getAttribute("page-name").equals("news") ? "active" : "" %>><a href="${pageContext.request.contextPath}/pyramid/news"><spring:message code="news"/></a></li>
                            <li class= <%= request.getAttribute("page-name").equals("training") ? "active" : "" %>><a href="${pageContext.request.contextPath}/pyramid/training"><spring:message code="training"/></a></li>
                            <li class= <%= request.getAttribute("page-name").equals("about") ? "active" : "" %>><a href="${pageContext.request.contextPath}/pyramid/about"><spring:message code="aboutProject"/></a></li>
                            <li class= <%= request.getAttribute("page-name").equals("contacts") ? "active" : "" %>><a href="${pageContext.request.contextPath}/pyramid/contacts"><spring:message code="contacts"/></a></li>
                        </ul>
                    </div>
                </div>
                <div class="row-fluid">
                    <div class="span10">
                        <div id="tab-content" class="tab-content">