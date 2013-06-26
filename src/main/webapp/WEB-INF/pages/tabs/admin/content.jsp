<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="/WEB-INF/pages/tabs/admin.jsp" %>
<div class="row-fluid">
    <div class="span12" id="admin-menu">
        <ul class="nav nav-tabs">
            <li><a href="<c:url value="/pyramid/admin/user_settings"/>" data-toogle="tab"><spring:message code="users"/></a></li>
            <li class="active"><a href="<c:url value="/pyramid/admin/content_settings"/>" data-toogle="tab"><spring:message code="tabs.text"/></a></li>
            <li><a href="<c:url value="/pyramid/admin/news_settings"/>" data-toogle="tab"><spring:message code="news"/></a></li>
            <li><a href="<c:url value="/pyramid/admin/video_settings"/>" data-toogle="tab"><spring:message code="tabs.video"/></a></li>
            <li><a href="<c:url value="/pyramid/admin/contact_settings"/>" data-toogle="tab"><spring:message code="contacts"/></a></li>
            <li><a href="<c:url value="/pyramid/admin/application_settings"/>" data-toogle="tab"><spring:message code="tabs.parameters"/></a></li>
        </ul>
    </div>
</div>

<div class="row-fluid">
    content
</div>
<%@ include file="/WEB-INF/pages/footer.jsp" %>