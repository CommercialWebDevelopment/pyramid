<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="/WEB-INF/pages/header.jsp" %>

<div class="row-fluid">
    <div class="span12" id="admin-menu">
            <ul class="nav nav-tabs">
                <li class= <%= request.getAttribute("admin-page-name").equals("user_settings") ? "active" : "" %>><a href="${pageContext.request.contextPath}/pyramid/admin/user_settings" data-toogle="tab">Пользователи</a></li>
                <li class= <%= request.getAttribute("admin-page-name").equals("content_settings") ? "active" : "" %>><a href="${pageContext.request.contextPath}/pyramid/admin/content_settings" data-toogle="tab">Тексты</a></li>
                <li class= <%= request.getAttribute("admin-page-name").equals("news_settings") ? "active" : "" %>><a href="${pageContext.request.contextPath}/pyramid/admin/news_settings" data-toogle="tab">Новости</a></li>
                <li class= <%= request.getAttribute("admin-page-name").equals("video_settings") ? "active" : "" %>><a href="${pageContext.request.contextPath}/pyramid/admin/video_settings" data-toogle="tab">Видео</a></li>
                <li class= <%= request.getAttribute("admin-page-name").equals("contact_settings") ? "active" : "" %>><a href="${pageContext.request.contextPath}/pyramid/admin/contact_settings" data-toogle="tab">Контакты</a></li>
                <li class= <%= request.getAttribute("admin-page-name").equals("application_settings") ? "active" : "" %>><a href="${pageContext.request.contextPath}/pyramid/admin/application_settings" data-toogle="tab">Параметры</a></li>
            </ul>
        </div>
    </div>



<%--<div class="tabbable">--%>
    <%--<ul class="nav nav-tabs">--%>
        <%--<li class= <%= request.getAttribute("admin-page-name").equals("user_settings") ? "active" : "" %>><a href="${pageContext.request.contextPath}/pyramid/admin/user_settings" data-toogle="tab">Пользователи</a></li>--%>
        <%--<li class= <%= request.getAttribute("admin-page-name").equals("content_settings") ? "active" : "" %>><a href="${pageContext.request.contextPath}/pyramid/admin/content_settings" data-toogle="tab">Тексты</a></li>--%>
        <%--<li class= <%= request.getAttribute("admin-page-name").equals("video_settings") ? "active" : "" %>><a href="${pageContext.request.contextPath}/pyramid/admin/video_settings" data-toogle="tab">Видео</a></li>--%>
        <%--<li class= <%= request.getAttribute("admin-page-name").equals("contact_settings") ? "active" : "" %>><a href="${pageContext.request.contextPath}/pyramid/admin/contact_settings" data-toogle="tab">Контакты</a></li>--%>
        <%--<li class= <%= request.getAttribute("admin-page-name").equals("application_settings") ? "active" : "" %>><a href="${pageContext.request.contextPath}/pyramid/admin/application_settings" data-toogle="tab">Параметры</a></li>--%>
    <%--</ul>--%>
    <%--<div id="admin-tab-content" class="tab-content"></div>--%>
<%--</div>--%>
