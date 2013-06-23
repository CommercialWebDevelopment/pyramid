<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="/WEB-INF/pages/header.jsp" %>

<% if (request.getAttribute("error") != null && request.getAttribute("error").equals("email_confirm_out_of_date")) {%>
<div class="alert alert-error">
    <button type="button" class="close" data-dismiss="alert">×</button>
    <strong>Ошибка!</strong> Данные устарели
</div>
<%}%>

<div class="row-fluid">
    <div class="span12">
        <div class="alert alert-error">
            <button type="button" class="close" data-dismiss="alert">×</button>
            <strong>Ошибка!</strong> Пользователь с такими данными отсутсвует в системе.
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/pages/footer.jsp" %>
