<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    if (request.getAttribute("alert_error") != null) {
%>
<div class="row-fluid">
    <div class="span12">
        <div class="alert alert-error">
            <button type="button" class="close" data-dismiss="alert">×</button>
            <strong><spring:message code='error'/>!</strong> <c:out value="${alert_error}"/>.
        </div>
    </div>
</div>
<%
    }
    if (request.getAttribute("alert_success") != null) {
%>
<div class="row-fluid">
    <div class="span12">
        <div class="alert alert-success">
            <button type="button" class="close" data-dismiss="alert">×</button>
            <strong><spring:message code='success'/>!</strong> <c:out value="${alert_success}"/>.
        </div>
    </div>
</div>
<%
    }
%>