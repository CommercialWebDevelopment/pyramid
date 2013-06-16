<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="/WEB-INF/pages/header.jsp" %>

<h3>Администрирование</h3>

<div class="tabbable">
    <ul class="nav nav-tabs">
        <li class= <%= request.getAttribute("page-name").equals("user_settings") ? "active" : "" %>><a href="${pageContext.request.contextPath}/pyramid/admin/user_settings" data-toogle="tab">Пользователи</a></li>
        <li class= <%= request.getAttribute("page-name").equals("content_settings") ? "active" : "" %>><a href="${pageContext.request.contextPath}/pyramid/admin/content_settings" data-toogle="tab">Тексты</a></li>
        <li class= <%= request.getAttribute("page-name").equals("video_settings") ? "active" : "" %>><a href="${pageContext.request.contextPath}/pyramid/admin/video_settings" data-toogle="tab">Видео</a></li>
        <li class= <%= request.getAttribute("page-name").equals("contact_settings") ? "active" : "" %>><a href="${pageContext.request.contextPath}/pyramid/admin/contact_settings" data-toogle="tab">Контакты</a></li>
        <li class= <%= request.getAttribute("page-name").equals("application_settings") ? "active" : "" %>><a href="${pageContext.request.contextPath}/pyramid/admin/application_settings" data-toogle="tab">Параметры</a></li>
    </ul>
    <div id="admin-tab-content" class="tab-content"></div>
</div>
