<%@ page import="com.financial.pyramid.domain.User" %>
<%@ page import="com.financial.pyramid.utils.Session" %>
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
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/javascript/flexigrid/js/flexigrid.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/main.js"></script>
    <sec:authorize ifAnyGranted="SUPER_ADMIN,ADMIN">
        <script type="text/javascript"
                src="${pageContext.request.contextPath}/resources/javascript/admin/admin.js"></script>
    </sec:authorize>
</head>
<body>
<script type="text/javascript">
    <%@ include file="/resources/javascript/i18n.js" %>
</script>
<div id="loader" class="loader">
    <div class="loader-icon">
        <img id="loader-img" src="${pageContext.request.contextPath}/resources/images/loader.gif" width="32"/>
    </div>
    <div class="loader-text"></div>
</div>
<c:set var="localeCode" value="<%=pageContext.getResponse().getLocale().toString()%>"/>
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
                                <c:if test="${localeCode == 'ru_RU' || localeCode == 'ru'}">
                                    Русский
                                </c:if>
                                <c:if test="${localeCode == 'en_US' || localeCode == 'en'}">
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
                        <sec:authorize ifNotGranted="SUPER_ADMIN,ADMIN,USER">
                            <li role="button">
                                <a href="${pageContext.request.contextPath}/pyramid/office"><spring:message
                                        code="signIn"/></a>
                            </li>
                        </sec:authorize>
                        <sec:authorize ifAnyGranted="SUPER_ADMIN,ADMIN,USER">
                            <%
                                User user = null;
                                if (Session.getAuthentication() != null) {
                                    user = Session.getCurrentUser();
                                }
                            %>
                            <li class="dropdown">
                                <a class="dropdown-toggle" id="profileLabel" role="button" data-toggle="dropdown"
                                   href="#">
                                    <div class="user-profile"><%=(user != null) ? user.getShortName() : ""%>
                                    </div>
                                </a>
                                <ul id="profileDetails" class="dropdown-menu" role="menu"
                                    aria-labelledby="profileLabel">
                                    <li><span class="profileItem"><%=(user != null) ? user.getShortName() : ""%></span>
                                    </li>
                                    <li><span class="profileItem"><%=(user != null) ? user.getEmail() : ""%></span></li>
                                    <li class="divider"></li>
                                    <li>
                                        <a href="${pageContext.request.contextPath}/user/settings">
                                            <span class="add-on"><b class="icon-wrench"></b>
                                            <spring:message code="userSettings"/>
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
