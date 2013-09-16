<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="/WEB-INF/pages/tabs/admin.jsp" %>
<div class="row-fluid">
    <div class="span12" id="admin-menu">
        <ul class="nav nav-tabs">
            <li><a href="<c:url value="/pyramid/admin/user_settings"/>" data-toogle="tab"><spring:message
                    code="users"/></a></li>
            <li><a href="<c:url value="/pyramid/admin/content_settings"/>" data-toogle="tab"><spring:message
                    code="tabs.text"/></a></li>
            <li><a href="<c:url value="/pyramid/admin/news_settings"/>" data-toogle="tab"><spring:message
                    code="news"/></a></li>
            <li><a href="<c:url value="/pyramid/admin/video_settings"/>" data-toogle="tab"><spring:message
                    code="tabs.video"/></a></li>
            <li class="active"><a href="<c:url value="/pyramid/admin/contact_settings"/>"
                                  data-toogle="tab"><spring:message code="contacts"/></a></li>
            <li><a href="<c:url value="/pyramid/admin/application_settings"/>" data-toogle="tab"><spring:message
                    code="tabs.parameters"/></a></li>
            <li><a href="<c:url value="/pyramid/admin/payments"/>" data-toogle="tab"><spring:message
                    code="tabs.payments"/></a></li>
        </ul>
    </div>
</div>

<div class="row-fluid">
    <spring:message var="removeContactConfirm" code="removeContact"/>
    <table class="table table-striped">
        <tr>
            <th><spring:message code="contactName"/></th>
            <th><spring:message code="contactPhone"/></th>
            <th><spring:message code="contactEmail"/></th>
            <th>&nbsp;</th>
            <th>&nbsp;</th>
        </tr>
        <c:forEach items="${contacts}" var="contact" varStatus="status">
        <tr>
            <td>${contact.person}</td>
            <td>${contact.phone}</td>
            <td>${contact.email}</td>
            <td style="width: 20px">
                <button class="btn"
                        onclick="ContactsAdmin.editForm('${contact.person}','${contact.phone}','${contact.email}', '${contact.id}')">
                    <spring:message code="edit"/></button>
            </td>
            <td style="width: 20px">
                <button class="btn" onclick="Alert.confirm('${removeContactConfirm}', function(){
                        window.location.href = '/contacts/remove/${contact.id}'
                        })"><spring:message code="remove"/>
                </button>
            </td>
            </c:forEach>
    </table>
</div>
<div id="edit-contact-form" class="modal hide fade"
     tabindex="-1" role="dialog"
     aria-labelledby="edit-contact-form-label" aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h5 id="edit-contact-form-label"></h5>
    </div>
    <div class="modal-body">
        <form:form action="/contacts/save" method="POST" modelAttribute="contact" id="addForm">
            <label><spring:message code="contactName"/>:</label>
            <input id="person" type="text" name="person" class="form-field span12">
            <label><spring:message code="contactPhone"/>:</label>
            <input id="phone" type="text" name="phone" class="form-field span12">
            <label><spring:message code="contactEmail"/>:</label>
            <input id="email" type="text" name="email" class="form-field span12">
            <input type="hidden" name="id" id="id" value=""/>
        </form:form>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary" type="button" onclick="ContactsAdmin.onSubmit()"><spring:message code="save"/></button>
    </div>
</div>

<div class="text-center">
    <button class="btn btn-primary" onclick="ContactsAdmin.addForm()"><spring:message code="add"/></button>
</div>
<%@include file="/WEB-INF/pages/footer.jsp" %>
