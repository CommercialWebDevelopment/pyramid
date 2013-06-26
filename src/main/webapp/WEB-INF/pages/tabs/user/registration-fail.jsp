<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="/WEB-INF/pages/tabs/office.jsp" %>

<h3>Регистрация не удаласть</h3>
<div>Извините, во время регистрации произошла неизвестная ошибка</div><br>
<div>Пожалуйста нажмите кнопку "Регистрация", что бы повторить попытку регистрации</div><br>
<a href="${pageContext.request.contextPath}/pyramid/office" class="btn btn-primary" type="button">Регистрация</a>

<%@ include file="/WEB-INF/pages/footer.jsp" %>
