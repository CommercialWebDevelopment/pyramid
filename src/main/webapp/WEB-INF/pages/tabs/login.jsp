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
                        <li class="active"><a href="<c:url value="/app/office"/>"><spring:message code="privateOffice"/></a>
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
        <div class="text-center">
            <c:choose>
                <c:when test="${param.error != null}">
                    <div class="alert alert-error">
                        <spring:message code="invalidCredentials"/>
                    </div>
                </c:when>
                <c:when test="${alert_success != null}">
                    <div class="alert alert-success">
                        <c:out value="${alert_success}"/>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="alert alert-info">
                        <spring:message code="enterMessage"/>
                    </div>
                </c:otherwise>
            </c:choose>
            <form:form method="POST" action="/user/authentication"
                       modelAttribute="authentication">
                <div style="width: 350px; margin: 0 auto">
                    <div class="control-group">
                        <div class="controls">
                            <input id="email" name="email" type="text" class="form-field span12"
                                   placeholder="<spring:message code="emailAddress"/>" autofocus="true">
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="controls">
                            <input id="password" name="password" type="password"
                                   placeholder="<spring:message code="password"/>" class="form-field span12">
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="controls">
                            <button type="submit" class="btn btn-primary btn-large" id="registration-button">
                                <spring:message code="authorization"/></button>
                        </div>
                    </div>
                    <br>
                    <div class="control-group">
                        <div class="controls">
                            <a href="${pageContext.request.contextPath}/user/forgot"><spring:message
                                    code="forgotPassword"/>?</a>
                        </div>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/pages/footer.jsp" %>
