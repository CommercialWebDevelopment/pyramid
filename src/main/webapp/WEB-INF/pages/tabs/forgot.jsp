<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="/WEB-INF/pages/header.jsp" %>

<script language="javascript">
    function beforeSubmit() {
        var form = $("#restorePasswordForm");
        var email = form.find("#email");
        Form.validateEMailField(email, email.val());
        if (!Form.validateForm(form)) {
            return false;
        } else {
            return true;
        }
    }
</script>
<%--Tabs--%>
<div class="row-fluid">
    <div class="span8 offset1">
        <ul class="nav nav-pills" id="tabs">
            <li><a href="<c:url value="/pyramid/home"/>"><spring:message code="home"/></a></li>
            <security:authorize access="hasAnyRole('SUPER_ADMIN,ADMIN')">
                <li><a href="<c:url value="/pyramid/admin/"/>"><spring:message code="settings"/></a></li>
            </security:authorize>
            <li class="active"><a href="<c:url value="/pyramid/office"/>"><spring:message code="privateOffice"/></a>
            </li>
            <li><a href="<c:url value="/pyramid/news"/>"><spring:message code="news"/></a></li>
            <li><a href="<c:url value="/pyramid/training"/>"><spring:message code="training"/></a></li>
            <li><a href="<c:url value="/pyramid/about"/>"><spring:message code="aboutProject"/></a></li>
            <li><a href="<c:url value="/pyramid/contacts"/>"><spring:message code="contacts"/></a></li>
        </ul>
    </div>
</div>
<div class="row-fluid">
    <div class="span10">
        <div class="text-right"><a href="Javascript:history.back()"><spring:message code="back"/></a></div>
    </div>
</div>
<div class="row-fluid">
    <div class="span10">
        <div class="text-center">
            <c:if test="${param.result != null}">
                <c:if test="${param.result == true}">
                    <div class="alert alert-success">
                        <spring:message code="restoredSuccess" arguments="${param.email}"/>
                    </div>
                </c:if>
                <c:if test="${param.result == false}">
                    <div class="alert alert-error">
                        <spring:message code="userNotFound" arguments="${param.email}"/>
                    </div>
                </c:if>
            </c:if>
            <c:if test="${param.result == null}">
                <div class="alert alert-info">
                    <spring:message code="restorePasswordInformation"/>
                </div>
                <form:form action="/user/restore" method="post" onsubmit="return beforeSubmit()"
                           id="restorePasswordForm">
                    <div class="control-group">
                    <div class="controls">
                        <input type="text" class="form-field span1" id="email" name="email"
                               placeholder="<spring:message code="emailAddress"/>" style="width: 250px"
                               onkeyup="Form.validateEMailField(this, value)">
                    </div>
                    <button class="btn btn-primary"><spring:message code="getPassword"/></button>
                </form:form>
                </div>
            </c:if>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/pages/footer.jsp" %>
