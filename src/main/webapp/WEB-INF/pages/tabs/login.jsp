<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="/WEB-INF/pages/header.jsp" %>

<div class="row-fluid">
    <div class="span12">
        <form:form method="POST" action="/authentication/check" modelAttribute="authentication">
            <div class="row-fluid">
                <div class="span1">
                    <label for="name" class="required_label form-field">Логин</label>
                </div>
                <div class="span7">
                    <input id="name" name="name" type="text" class="form-field">
                </div>
            </div>
            <div class="row-fluid">
                <div class="span1">
                    <label for="password" class="required_label form-field">Пароль</label>
                </div>
                <div class="span7">
                    <input id="password" name="password" type="text" class="form-field">
                </div>
            </div>
            <div class="row-fluid">
                <div class="span12">
                    <div class="span1 offset1">
                        <button id="registration-button" class="btn btn-primary" type="submit">Авторизация</button>
                    </div>
                    <div class="span1 offset1">
                        <a href="${pageContext.request.contextPath}/pyramid/registration" class="btn btn-primary" type="button">Регистрация</a>
                    </div>
                </div>
            </div>
        </form:form>
    </div>
</div>

<%@ include file="/WEB-INF/pages/footer.jsp" %>
