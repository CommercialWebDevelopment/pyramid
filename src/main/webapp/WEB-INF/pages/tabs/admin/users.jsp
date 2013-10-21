<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="/WEB-INF/pages/tabs/admin.jsp" %>
<div class="row-fluid">
    <div class="span12" id="admin-menu">
        <ul class="nav nav-tabs">
            <li class="active"><a href="<c:url value="/app/admin/user_settings"/>" data-toogle="tab"><spring:message
                    code="users"/></a></li>
            <li><a href="<c:url value="/app/admin/content_settings"/>" data-toogle="tab"><spring:message
                    code="tabs.text"/></a></li>
            <li><a href="<c:url value="/app/admin/news_settings"/>" data-toogle="tab"><spring:message
                    code="news"/></a></li>
            <li><a href="<c:url value="/app/admin/video_settings"/>" data-toogle="tab"><spring:message
                    code="tabs.video"/></a></li>
            <li><a href="<c:url value="/app/admin/contact_settings"/>" data-toogle="tab"><spring:message
                    code="contacts"/></a></li>
            <li><a href="<c:url value="/app/admin/application_settings"/>" data-toogle="tab"><spring:message
                    code="tabs.parameters"/></a></li>
            <li><a href="<c:url value="/app/admin/payments"/>" data-toogle="tab"><spring:message code="tabs.payments"/></a></li>
        </ul>
    </div>
</div>
<div class="row-fluid">
    <div class="span12">
        <%--grid--%>
        <div id="user-grid" class="span12"></div>
    </div>
</div>
<div class="modal hide fade" aria-hidden="true" tabindex="-1" role="dialog" id="add-user-form">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h5 id="add-user-form-abel"><spring:message code="user.add"/></h5>
            </div>
            <div class="modal-body" style="height: 515px">
                <form:form method="POST" id="register_user" action="/user/update" modelAttribute="registration">
                    <input id="id" name="id" type="hidden"/>

                    <div class="row-fluid control-group success">
                        <div class="span5">
                            <label for="surname" class="required_label form-field"><spring:message
                                    code="firstName"/><span class="asterisk_red">*</span></label>
                        </div>
                        <div class="span7 controls">
                            <input id="surname" name="surname" type="text" class="form-field">
                        </div>
                    </div>
                    <div class="row-fluid control-group success">
                        <div class="span5">
                            <label for="name" class="required_label form-field"><spring:message
                                    code="secondName"/><span class="asterisk_red">*</span></label>
                        </div>
                        <div class="span7 controls">
                            <input id="name" name="name" type="text" class=" form-field">
                        </div>
                    </div>
                    <div class="row-fluid control-group success">
                        <div class="span5">
                            <label for="patronymic"
                                   class="required_label form-field"><spring:message
                                    code="patronymic"/><span class="asterisk_red">*</span></label>
                        </div>
                        <div class="span7 controls">
                            <input id="patronymic" name="patronymic" type="text"
                                   class=" form-field">
                        </div>
                    </div>
                    <div class="row-fluid control-group success">
                        <div class="span5">
                            <label for="date_of_birth"
                                   class="required_label form-field"><spring:message
                                    code="dateOfBirth"/><span class="asterisk_red">*</span></label>
                        </div>
                        <div class="span7 controls">
                            <input id="date_of_birth" name="dateOfBirth" type="text"
                                   class="form-field"
                                   placeholder="дд-мм-гггг">
                        </div>
                    </div>
                    <div class="row-fluid control-group success">
                        <div class="span5">
                            <label for="email" class="required_label form-field"><spring:message
                                    code="email"/><span class="asterisk_red">*</span></label>
                        </div>
                        <div class="span7 controls">
                            <div class="input-prepend form-field">
                                            <span class="add-on" style="height: 15px"><i
                                                    class="icon-envelope"></i></span>
                                <input id="email" name="email" type="text" class="form-field">
                            </div>
                        </div>
                    </div>
                    <div class="row-fluid control-group success">
                        <div class="span5">
                            <label for="phone-number"
                                   class="required_label form-field"><spring:message
                                    code="phoneNumber"/><span
                                    class="asterisk_red">*</span></label>
                        </div>
                        <div class="span7 controls">
                            <input id="phone-number" name="phoneNumber" type="text"
                                   class="form-field">
                        </div>
                    </div>
                    <div class="row-fluid">
                        <div class="span12">
                            <legend><spring:message code="passport.info"/></legend>
                        </div>
                    </div>
                    <%--Паспорт--%>
                    <div class="row-fluid">
                        <div class="span5">
                            <label for="serial"
                                   class="required_label form-field"><spring:message
                                    code="serial"/></label>
                        </div>
                        <div class="span7">
                            <input id="serial" name="passportSerial" type="text"
                                   class="form-field">
                        </div>
                    </div>
                    <div class="row-fluid">
                        <div class="span5">
                            <label for="number"
                                   class="required_label form-field"><spring:message
                                    code="number"/></label>
                        </div>
                        <div class="span7">
                            <input id="number" name="passportNumber" type="text"
                                   class="form-field">
                        </div>
                    </div>
                    <div class="row-fluid">
                        <div class="span5">
                            <label for="date_of_issue"
                                   class="required_label form-field"><spring:message
                                    code="dateOfIssue"/></label>
                        </div>
                        <div class="span7">
                            <input id="date_of_issue" name="passportDate" type="text"
                                   class="form-field" placeholder="дд-мм-гггг">
                        </div>
                    </div>
                    <div class="row-fluid">
                        <div class="span5">
                            <label for="issued_by"
                                   class="required_label form-field"><spring:message
                                    code="issuedBy"/></label>
                        </div>
                        <div class="span7">
                            <input id="issued_by" name="passportIssuedBy" type="text"
                                   class=" form-field">
                        </div>
                    </div>
                    <div class="row-fluid">
                        <div class="span5">
                            <label for="registered_address"
                                   class="required_label form-field"><spring:message
                                    code="registeredAddress"/></label>
                        </div>
                        <div class="span7">
                            <input id="registered_address" name="registeredAddress" type="text"
                                   class=" form-field">
                        </div>
                    </div>
                    <div class="row-fluid">
                        <div class="span5">
                            <label for="residence_address"
                                   class="required_label form-field"><spring:message
                                    code="residenceAddress"/></label>
                        </div>
                        <div class="span7">
                            <input id="residence_address" name="residenceAddress" type="text"
                                   class="form-field">
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true"><spring:message
                    code="cancel"/></button>
            <button class="btn btn-primary" type="submit" form="register_user"><spring:message
                    code="save"/></button>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/pages/footer.jsp" %>