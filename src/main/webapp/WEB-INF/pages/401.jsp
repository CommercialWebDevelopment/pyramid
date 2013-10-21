<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="/WEB-INF/pages/header.jsp" %>
<%--Tabs--%>
<div class="row-fluid">
    <div class="span8 offset1">
        <ul class="nav nav-pills" id="tabs">
            <li><a href="<c:url value="/app/home"/>"><spring:message code="home"/></a></li>
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
<div class="row-fluid">
    <div class="span10">
        <div id="tab-content" class="tab-content">
            <div class="text-right"><a href="Javascript:history.back()"><spring:message code="back"/></a></div><br>
            <div class="alert alert-error alert-block">
                <h4>401</h4>
                <span class="text">Access denied</span>
            </div>

            <%@ include file="/WEB-INF/pages/footer.jsp" %>
