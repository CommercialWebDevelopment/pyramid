<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="/WEB-INF/pages/header.jsp" %>

<%--Tabs--%>
<div class="row-fluid">
    <div class="span10">
        <div class="navbar">
            <div class="navbar-inner">
                <div class="container">
                    <ul class="nav">
                        <li><a href="<c:url value="/app/home"/>"><spring:message code="home"/></a></li>
                        <security:authorize access="hasAnyRole('SUPER_ADMIN,ADMIN')">
                            <li><a href="<c:url value="/app/admin/"/>"><spring:message code="settings"/></a></li>
                        </security:authorize>
                        <li class="active"><a href="<c:url value="/app/office"/>"><spring:message
                                code="privateOffice"/></a>
                        </li>
                        <li><a href="<c:url value="/app/news"/>"><spring:message code="news"/></a></li>
                        <li><a href="<c:url value="/app/training"/>"><spring:message code="training"/></a></li>
                        <li><a href="<c:url value="/app/about"/>"><spring:message code="aboutProject"/></a></li>
                        <li><a href="<c:url value="/app/contacts"/>"><spring:message code="contacts"/></a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="row-fluid">
    <div class="span10">
        <div class="title"><h3><spring:message code="restorePassword"/></h3></div>
        <div class="back"><a href="Javascript:history.back()"><spring:message code="back"/></a></div>
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
                <form:form action="/user/restore" method="post" onsubmit="return RestorePasswordPage.beforeSubmit()"
                           id="restorePasswordForm">
                    <div class="control-group">
                    <div class="controls">
                        <input type="text" class="form-field span12" id="email" name="email"
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
