<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="/WEB-INF/pages/tabs/office.jsp" %>

<% if (request.getAttribute("error") != null && request.getAttribute("error").equals("email_confirm_out_of_date")) {%>
<div class="alert alert-error">
    <button type="button" class="close" data-dismiss="alert">×</button>
    <spring:message code="error"/>! <spring:message code="oldData"/>
</div>
<%}%>

<div class="row-fluid">
    <div class="span12">
        <div class="alert alert-error">
            <button type="button" class="close" data-dismiss="alert">×</button>
            <spring:message code="error"/>! <spring:message code="userAlreadyExists"/>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/pages/footer.jsp" %>
