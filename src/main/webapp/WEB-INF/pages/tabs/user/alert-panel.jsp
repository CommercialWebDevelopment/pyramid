<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${alert_error != null}">
    <div class="alert alert-error alert-block">
        <button type="button" class="close" data-dismiss="alert">×</button>
        <spring:message code='error'/>! <c:out value="${alert_error}"/>.
    </div>
</c:if>
<c:if test="${alert_success != null}">
    <div class="alert alert-success alert-block">
        <button type="button" class="close" data-dismiss="alert">×</button>
        <c:out value="${alert_success}"/>
    </div>
</c:if>
