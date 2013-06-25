<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="/WEB-INF/pages/tabs/admin.jsp" %>

<div class="span10">
    <%--grid--%>
    <div id="user-grid" class="span12"></div>

    <div id="add-user-form" style="width: 800px" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="add-user-form-abel" aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h5 id="add-user-form-abel"><spring:message code="user.add"/></h5>
        </div>
        <div class="modal-body">
            <form:form method="POST" id="register_user" action="/user/add" modelAttribute="registration">
                <div id="registration-form" class="container-fluid">
                    <input id="id" name="id" type="text" style="display: none">
                    <div class="row-fluid">
                        <div class="span8">
                            <div class="row-fluid control-group success">
                                <div class="span5">
                                    <label for="surname" class="required_label form-field"><spring:message code="firstName"/><span class="asterisk_red">*</span></label>
                                </div>
                                <div class="span7 controls">
                                    <input id="surname" name="surname" type="text" class="form-field">
                                </div>
                            </div>
                            <div class="row-fluid control-group success">
                                <div class="span5">
                                    <label for="name" class="required_label form-field"><spring:message code="secondName"/><span class="asterisk_red">*</span></label>
                                </div>
                                <div class="span7 controls">
                                    <input id="name" name="name" type="text" class=" form-field">
                                </div>
                            </div>
                            <div class="row-fluid control-group success">
                                <div class="span5">
                                    <label for="patronymic" class="required_label form-field"><spring:message code="patronymic"/><span class="asterisk_red">*</span></label>
                                </div>
                                <div class="span7 controls">
                                    <input id="patronymic" name="patronymic" type="text" class=" form-field">
                                </div>
                            </div>
                            <div class="row-fluid control-group success">
                                <div class="span5">
                                    <label for="date_of_birth" class="required_label form-field"><spring:message code="dateOfBirth"/><span class="asterisk_red">*</span></label>
                                </div>
                                <div class="span7 controls">
                                    <input id="date_of_birth" name="dateOfBirth" type="text" class="form-field" placeholder="дд-мм-гггг">
                                </div>
                            </div>
                            <div class="row-fluid control-group success">
                                <div class="span5">
                                    <label for="email" class="required_label form-field"><spring:message code="email"/><span class="asterisk_red">*</span></label>
                                </div>
                                <div class="span7 controls">
                                    <div class="input-prepend form-field">
                                        <span class="add-on" style="height: 15px"><i class="icon-envelope"></i></span>
                                        <input id="email" name="email" type="text" class="form-field">
                                    </div>
                                </div>
                            </div>
                            <div class="row-fluid control-group success">
                                <div class="span5">
                                    <label for="phone-number" class="required_label form-field"><spring:message code="phoneNumber"/><span
                                            class="asterisk_red">*</span></label>
                                </div>
                                <div class="span7 controls">
                                    <input id="phone-number" name="phoneNumber" type="text" class="form-field">
                                </div>
                            </div>
                            <div class="row-fluid control-group success">
                                <div class="span5">
                                    <label for="login" class="required_label form-field"><spring:message code="login"/><span class="asterisk_red">*</span></label>
                                </div>
                                <div class="span7 controls">
                                    <input id="login" name="login" type="text" class=" form-field">
                                </div>
                            </div>
                            <div class="row-fluid control-group success">
                                <div class="span5">
                                    <label for="password" class="required_label form-field"><spring:message code="password"/><span class="asterisk_red">*</span></label>
                                </div>
                                <div class="span7 controls">
                                    <input id="password" name="password" type="text" class=" form-field">
                                </div>
                            </div>
                        </div>
                            <%--Photo--%>
                        <div class="span4">
                            <div class="span6 offset3">
                                <a href="#" class="thumbnail" id="user-details" data-toggle="popover" data-placement="left">
                                    <img src="${pageContext.request.contextPath}/resources/images/vcard.png" alt=""
                                         style="height: 120px"/>
                                </a>
                            </div>
                        </div>
                    </div>

                        <%--Паспорт--%>
                    <div class="row-fluid">
                        <div class="span12">
                            <fieldset>
                                <legend><spring:message code="passport.info"/></legend>
                                <div class="row-fluid">
                                    <div class="span2">
                                        <label for="serial" class="required_label form-field"><spring:message code="serial"/></label>
                                    </div>
                                    <div class="span4">
                                        <input id="serial" name="passportSerial" type="text" class="form-field">
                                    </div>
                                    <div class="span2">
                                        <label for="number" class="required_label form-field"><spring:message code="number"/></label>
                                    </div>
                                    <div class="span4">
                                        <input id="number" name="passportNumber" type="text" class="form-field">
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class="span2">
                                        <label for="date_of_issue" class="required_label form-field"><spring:message code="dateOfIssue"/></label>
                                    </div>
                                    <div class="span4">
                                        <input id="date_of_issue" name="passportDate" type="text" class="form-field" placeholder="дд-мм-гггг">
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class="span2">
                                        <label for="issued_by" class="required_label form-field"><spring:message code="issuedBy"/></label>
                                    </div>
                                    <div class="span10">
                                        <input id="issued_by" name="passportIssuedBy" type="text" class=" form-field" style="width: 100%;">
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class="span3">
                                        <label for="registered_address" class="required_label form-field"><spring:message code="registeredAddress"/></label>
                                    </div>
                                    <div class="span9">
                                        <input id="registered_address" name="registeredAddress" type="text" class=" form-field" style="width: 100%;">
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class="span4">
                                        <label for="residence_address" class="required_label form-field"><spring:message code="residenceAddress"/></label>
                                    </div>
                                    <div class="span8">
                                        <input id="residence_address" name="residenceAddress" type="text" class=" form-field" style="width: 100%;">
                                    </div>
                                </div>
                            </fieldset>
                        </div>
                    </div>
                </div>
            </form:form>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true"><spring:message code="cancel"/></button>
            <button class="btn btn-primary" type="submit" form="register_user"><spring:message code="save"/></button>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/pages/footer.jsp" %>