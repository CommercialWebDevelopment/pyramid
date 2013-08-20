<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="/WEB-INF/pages/header.jsp" %>
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
        <div class="text-center">
            <c:if test="${param.error != null}">
                <div class="alert alert-error">
                    <spring:message code="invalidCredentials"/>
                </div>
            </c:if>
            <c:if test="${param.error == null}">
                <div class="alert alert-info">
                    <spring:message code="enterMessage"/>
                </div>
            </c:if>
            <h3 style="text-align: left"><spring:message code="enterPrivateOffice"/></h3>
            <form:form method="POST" action="/user/authentication"
                       modelAttribute="authentication">
                <div class="form-horizontal" style="width: 450px">
                    <div class="control-group">
                        <label class="control-label" for="email"><spring:message code="email"/></label>

                        <div class="controls">
                            <input id="email" name="email" type="text" class="form-field span12">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="password"><spring:message code="password"/></label>

                        <div class="controls">
                            <input id="password" name="password" type="password" class="form-field span12">
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="controls">
                            <a href="${pageContext.request.contextPath}/user/forgot"><spring:message code="forgotPassword"/>?</a>
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="controls">
                            <button type="submit" class="btn btn-primary" id="registration-button">
                                <spring:message code="authorization"/></button>
                        </div>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/pages/footer.jsp" %>
