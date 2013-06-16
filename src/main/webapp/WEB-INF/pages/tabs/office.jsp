<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="/WEB-INF/pages/header.jsp" %>

<div class="row-fluid">
    <div class="span12">
        <div class="span6 offset4">
            <h4>Ваш офис</h4>
            <a href="<c:url value="/j_spring_security_logout" />" class="btn btn-primary" type="button">Logout</a>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/pages/footer.jsp" %>
