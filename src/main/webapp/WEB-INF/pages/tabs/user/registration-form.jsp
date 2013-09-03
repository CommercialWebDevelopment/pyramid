<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="/WEB-INF/pages/tabs/office.jsp" %>

<div class="row-fluid">
    <div class="span12">
        <div class="span6 offset4">
            <h4><spring:message code="newUserRegistration"/></h4>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/pages/tabs/user/alert-panel.jsp" %>
<form:form method="POST" action="/user/registration" modelAttribute="registration" onsubmit="return Form.validate()">
    <div id="registration-form" class="container-fluid">
        <div id="user-form-step">
            <div class="row-fluid">
                <div class="span6">
                    <input id="invitationId" name="invitationId" type="hidden" value="${registration.invitationId}">

                    <div class="row-fluid control-group error">
                        <div class="span5">
                            <label for="surname" class="required_label form-field"><spring:message
                                    code="firstName"/><span class="asterisk_red">*</span></label>
                        </div>
                        <div class="span7 controls">
                            <input id="surname" name="surname" type="text" class="form-field"
                                   onchange="Form.validateTextField(this, value)">
                        </div>
                    </div>
                    <div class="row-fluid control-group error">
                        <div class="span5">
                            <label for="name" class="required_label form-field"><spring:message code="secondName"/><span
                                    class="asterisk_red">*</span></label>
                        </div>
                        <div class="span7 controls">
                            <input id="name" name="name" type="text" class=" form-field"
                                   onchange="Form.validateTextField(this, value)">
                        </div>
                    </div>
                    <div class="row-fluid control-group error">
                        <div class="span5">
                            <label for="patronymic" class="required_label form-field"><spring:message
                                    code="patronymic"/><span class="asterisk_red">*</span></label>
                        </div>
                        <div class="span7 controls">
                            <input id="patronymic" name="patronymic" type="text" class=" form-field"
                                   onchange="Form.validateTextField(this, value)">
                        </div>
                    </div>
                    <div class="row-fluid control-group error">
                        <div class="span5">
                            <label for="date_of_birth" class="required_label form-field"><spring:message
                                    code="dateOfBirth"/><span class="asterisk_red">*</span></label>
                        </div>
                        <div class="span7 controls">
                            <input id="date_of_birth" name="dateOfBirth" type="text" class="form-field"
                                   placeholder="<spring:message code="form.datePattern"/>"
                                   onchange="Form.validateDateField(this, value)">
                        </div>
                    </div>
                    <div class="row-fluid control-group error">
                        <div class="span5">
                            <label for="phone-number" class="required_label form-field"><spring:message
                                    code="phoneNumber"/><span
                                    class="asterisk_red">*</span></label>
                        </div>
                        <div class="span7 controls">
                            <input id="phone-number" name="phoneNumber" type="text" class="form-field"
                                   onchange="Form.validatePhoneNumberField(this, value)">
                        </div>
                    </div>
                </div>
                    <%--Photo--%>
                <div class="span6">
                    <div class="span4 offset5">
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
                                <label for="serial" class="required_label form-field"><spring:message
                                        code="serial"/></label>
                            </div>
                            <div class="span4">
                                <input id="serial" name="passportSerial" type="text" class="form-field">
                            </div>
                            <div class="span2">
                                <label for="number" class="required_label form-field"><spring:message
                                        code="number"/></label>
                            </div>
                            <div class="span4">
                                <input id="number" name="passportNumber" type="text" class="form-field">
                            </div>
                        </div>
                        <div class="row-fluid">
                            <div class="span2">
                                <label for="date_of_issue" class="required_label form-field"><spring:message
                                        code="dateOfIssue"/></label>
                            </div>
                            <div class="span4">
                                <input id="date_of_issue" name="passportDate" type="text" class="form-field"
                                       placeholder="<spring:message code="form.datePattern"/>">
                            </div>
                        </div>
                        <div class="row-fluid">
                            <div class="span2">
                                <label for="issued_by" class="required_label form-field"><spring:message
                                        code="issuedBy"/></label>
                            </div>
                            <div class="span10">
                                <input id="issued_by" name="passportIssuedBy" type="text" class=" form-field"
                                       style="width: 625px;">
                            </div>
                        </div>
                        <div class="row-fluid">
                            <div class="span3">
                                <label for="registered_address" class="required_label form-field"><spring:message
                                        code="registeredAddress"/></label>
                            </div>
                            <div class="span9">
                                <input id="registered_address" name="registeredAddress" type="text" class=" form-field"
                                       style="width: 555px;">
                            </div>
                        </div>
                        <div class="row-fluid">
                            <div class="span4">
                                <label for="residence_address" class="required_label form-field"><spring:message
                                        code="residenceAddress"/></label>
                            </div>
                            <div class="span8">
                                <input id="residence_address" name="residenceAddress" type="text" class=" form-field"
                                       style="width: 485px;">
                            </div>
                        </div>
                    </fieldset>
                </div>
            </div>
                <%--Send--%>
            <div class="row-fluid">
                <div class="span12">
                    <div class="span2 offset10">
                        <button id="next-button" class="btn btn-primary" type="button"><spring:message
                                code="next"/></button>
                    </div>
                </div>
            </div>
        </div>
        <div id="contract-step" style="display: none">
            <div class="row-fluid">
                <div class="span12">
                    <iframe src="${pageContext.request.contextPath}/user/contract_offer" width="100%" height="300px">
                        Ваш браузер не поддерживает плавающие фреймы!
                    </iframe>
                </div>
            </div>

            <div class="row-fluid">
                <div class="span10">
                    <label class="checkbox">
                        <input id="contract-offer-accepted" name="contractOfferAccepted" type="checkbox">
                        <spring:message code="acceptContractOffer"/>
                    </label>
                </div>
            </div>

                <%--Send--%>
            <div class="row-fluid">
                <div class="span2">
                    <div class="row-fluid">
                        <div class="span3">
                            <img src="${pageContext.request.contextPath}/resources/images/download-img.png">
                        </div>
                        <div class="span9">
                            <a href="${pageContext.request.contextPath}/user/contract_offer_pdf">Скачать</a>
                        </div>
                    </div>
                </div>
                <div class="span8 offset2">
                    <div class="span5 offset7">
                        <div class="span4">
                            <button id="back-button" class="btn btn-primary" type="button"><spring:message code="back"/></button>
                        </div>
                        <div class="span6">
                            <button id="registration-form-send-button" class="btn btn-primary" type="submit" disabled><spring:message
                                    code="registration"/></button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form:form>


<%@ include file="/WEB-INF/pages/footer.jsp" %>
