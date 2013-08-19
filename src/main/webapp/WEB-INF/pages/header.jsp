<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel=stylesheet href="${pageContext.request.contextPath}/resources/javascript/bootstrap/css/bootstrap.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/javascript/flexigrid/css/flexigrid.pack.css" type="text/css">
    <link rel=stylesheet href="${pageContext.request.contextPath}/resources/css/loader.css" type="text/css">
    <link rel=stylesheet href="${pageContext.request.contextPath}/resources/css/private-office.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin.css" type="text/css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/jquery-1.10.0/jquery-1.10.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/bootstrap/js/bootstrap.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/main.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/office.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/settings.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/alerts.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/flexigrid/js/flexigrid.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/admin.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/loader.js"></script>
</head>
<body>
<script type="text/javascript">
    <%@ include file="/resources/javascript/i18n.js" %>
</script>

<div id="loader" class="loader">
    <div class="loader-icon"></div>
    <div class="loader-text"></div>
</div>

<div class="row-fluid" style="height: 20px"></div>
<div class="container-fluid">
    <div class="page-scroll">↑</div>
    <div class="row-fluid">
        <div class="span10 offset2">
            <%--Header--%>
            <div class="row-fluid" style="height: 80px;">
                <div class="span8">
                    <h1>Название | <small>Финансовый проект №1</small>
                </h1>
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
