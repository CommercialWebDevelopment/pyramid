<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="/WEB-INF/pages/tabs/admin.jsp" %>
<div class="row-fluid">
    <div class="span12" id="admin-menu">
        <ul class="nav nav-tabs">
            <li><a href="<c:url value="/pyramid/admin/user_settings"/>" data-toogle="tab"><spring:message code="users"/></a></li>
            <li><a href="<c:url value="/pyramid/admin/content_settings"/>" data-toogle="tab"><spring:message code="tabs.text"/></a></li>
            <li><a href="<c:url value="/pyramid/admin/news_settings"/>" data-toogle="tab"><spring:message code="news"/></a></li>
            <li><a href="<c:url value="/pyramid/admin/video_settings"/>" data-toogle="tab"><spring:message code="tabs.video"/></a></li>
            <li><a href="<c:url value="/pyramid/admin/contact_settings"/>" data-toogle="tab"><spring:message code="contacts"/></a></li>
            <li class="active"><a href="<c:url value="/pyramid/admin/application_settings"/>" data-toogle="tab"><spring:message code="tabs.parameters"/></a></li>
            <li><a href="<c:url value="/pyramid/admin/payments"/>" data-toogle="tab"><spring:message code="tabs.payments"/></a></li>
        </ul>
    </div>
</div>

<div class="row-fluid">
    <form:form method="POST" action="/settings/save" modelAttribute="settingsForm">
        <table class="table table-striped">
            <tr>
                <th><spring:message code="settingName"/></th>
                <th><spring:message code="settingValue"/></th>
            </tr>
            <c:forEach items="${settingsForm.settings}" var="setting" varStatus="status">
                <tr>
                    <td><script language="javascript">document.write(I18N.${setting.keyString})</script></td>
                    <td>
                        <input type="hidden" name="settings[${status.index}].id" value="${setting.id}">
                        <input type="hidden" name="settings[${status.index}].keyString" value="${setting.keyString}">
                        <input type="text" class="form-field" name="settings[${status.index}].valueString"
                               value="${setting.valueString}" style="width:100%">
                    </td>
                </tr>
            </c:forEach>
        </table>
        <button type="submit" class="btn btn-primary"><spring:message code="save"/></button>
    </form:form>
</div>
<%@ include file="/WEB-INF/pages/footer.jsp" %>