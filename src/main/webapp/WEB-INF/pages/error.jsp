<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%@ include file="/WEB-INF/pages/header.jsp" %>
<div class="row-fluid">
    <div class="span10">
        <div class="text-right"><a href="Javascript:history.back()">Назад</a></div>
        <div class="alert alert-warning alert-block">
            <h4><spring:message code="serverError"/></h4>
            <span class="text">${message}</span>
        </div>

<%@ include file="/WEB-INF/pages/footer.jsp" %>
