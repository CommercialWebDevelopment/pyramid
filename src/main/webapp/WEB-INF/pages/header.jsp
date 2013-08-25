<%@ page import="com.financial.pyramid.domain.User" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel=stylesheet href="${pageContext.request.contextPath}/resources/javascript/bootstrap/css/bootstrap.css"
          type="text/css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/javascript/flexigrid/css/flexigrid.pack.css"
          type="text/css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/main.css" type="text/css">
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/javascript/jquery-1.10.0/jquery-1.10.1.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/javascript/bootstrap/js/bootstrap.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/main.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/office.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/settings.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/alerts.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/javascript/flexigrid/js/flexigrid.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/loader.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/modal.js"></script>
</head>
<body>
<script type="text/javascript">
    <%@ include file="/resources/javascript/i18n.js" %>
</script>

<div id="loader" class="loader">
    <div class="loader-icon"></div>
    <div class="loader-text"></div>
</div>
<c:set var="localeCode" value="${pageContext.response.locale}"/>
<div class="row-fluid" style="height: 20px"></div>
<div id="page-wrap" class="container-fluid">
    <div class="page-scroll">↑</div>
    <div class="row-fluid">
        <div class="span10 offset2">
            <%--Header--%>
            <div id="header" class="row-fluid">
                <div class="span6">
                    <h1>Название |
                        <small>Финансовый проект №1</small>
                    </h1>
                </div>
                <div class="span4" style="overflow: visible">
                    <ul class="nav nav-pills" style="float: right">
                        <li class="dropdown">
                            <a class="dropdown-toggle" id="languageLabel" role="button" data-toggle="dropdown" href="#">
                                <c:if test="${localeCode == 'ru'}">
                                    Русский
                                </c:if>
                                <c:if test="${localeCode == 'en'}">
                                    English
                                </c:if>
                                <b class="caret"></b>
                            </a>
                            <ul class="dropdown-menu" role="menu" aria-labelledby="languageLabel">
                                <li><a href="?lang=en" tabindex="-1"><img
                                        src="${pageContext.request.contextPath}/resources/images/flag-en.png"/>&nbsp;English</a>
                                </li>
                                <li><a href="?lang=ru" tabindex="-1"><img
                                        src="${pageContext.request.contextPath}/resources/images/flag-ru.png"/>&nbsp;Русский</a>
                                </li>
                            </ul>
                        </li>
                        <sec:authorize ifAnyGranted="ROLE_ANONYMOUS">
                            <li role="button">
                                <a href="${pageContext.request.contextPath}/pyramid/office"><spring:message
                                        code="signIn"/></a>
                            </li>
                        </sec:authorize>
                        <sec:authorize ifNotGranted="ROLE_ANONYMOUS">
                            <%User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();%>
                            <li class="dropdown">
                                <a class="dropdown-toggle" id="profileLabel" role="button" data-toggle="dropdown"
                                   href="#">
                                    <div style="max-width: 228px; text-overflow: ellipsis"><%=user.getShortName()%></div>
                                </a>
                                <ul id="profileDetails" class="dropdown-menu" role="menu"
                                    aria-labelledby="profileLabel">
                                    <li><span class="profileItem"><%=user.getShortName()%></span></li>
                                    <li><span class="profileItem"><%=user.getEmail()%></span></li>
                                    <li class="divider"></li>
                                    <li>
                                        <a href="${pageContext.request.contextPath}/user/profile">
                                            <span class="add-on"><b class="icon-user"></b>
                                            <spring:message code="userProfile"/>
                                            </span>
                                        </a>
                                    </li>
                                    <li><a href="${pageContext.request.contextPath}/user/logout">
                                        <span class="add-on"><b class="icon-off"></b>
                                        <spring:message code="logout"/></span></a></li>
                                </ul>
                            </li>
                        </sec:authorize>
                    </ul>
                </div>
            </div>
