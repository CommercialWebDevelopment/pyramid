<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="/WEB-INF/pages/tabs/admin.jsp" %>
<div class="row-fluid">
    <form:form method="POST" action="/settings/save" modelAttribute="settingsForm">
        <table class="table table-bordered">
            <tr>
                <th>Название параметра</th>
                <th>Значение параметра</th>
            </tr>
            <c:forEach items="${settingsForm.settings}" var="setting" varStatus="status">
                <tr>
                    <td>${setting.keyString}</td>
                    <td>
                        <input type="hidden" name="settings[${status.index}].id" value="${setting.id}">
                        <input type="hidden" name="settings[${status.index}].keyString" value="${setting.keyString}">
                        <input type="text" class="form-field" name="settings[${status.index}].valueString"
                               value="${setting.valueString}" style="width:100%">
                    </td>
                </tr>
            </c:forEach>
        </table>
        <button type="submit" class="btn btn-primary">Сохранить</button>
    </form:form>
</div>
<%@ include file="/WEB-INF/pages/footer.jsp" %>