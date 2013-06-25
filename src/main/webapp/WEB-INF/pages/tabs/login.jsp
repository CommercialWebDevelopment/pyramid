<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="/WEB-INF/pages/header.jsp" %>

<div class="row-fluid">
    <div class="span12">
        <form:form method="POST" action="/user/authentication" modelAttribute="authentication">
            <div class="row-fluid">
                <div class="span1">
                    <label for="name" class="required_label form-field"><spring:message code="login"/></label>
                </div>
                <div class="span7">
                    <input id="name" name="name" type="text" class="form-field">
                </div>
            </div>
            <div class="row-fluid">
                <div class="span1">
                    <label for="password" class="required_label form-field"><spring:message code="password"/></label>
                </div>
                <div class="span7">
                    <input id="password" name="password" type="password" class="form-field">
                </div>
            </div>
            <div class="row-fluid">
                <div class="span12">
                    <div class="span1 offset1">
                        <button id="registration-button" class="btn btn-primary" type="submit"><spring:message code="authorization"/></button>
                    </div>
                    <div class="span1 offset1">
                        <a href="${pageContext.request.contextPath}/pyramid/registration" class="btn btn-primary" type="button"><spring:message code="registration"/></a>
                    </div>
                </div>
            </div>
        </form:form>
    </div>
</div>

<%@ include file="/WEB-INF/pages/footer.jsp" %>
