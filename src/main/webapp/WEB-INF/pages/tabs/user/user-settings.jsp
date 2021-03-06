<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="/WEB-INF/pages/tabs/office.jsp" %>
<div class="row-fluid">
    <div class="span12 page-title">
        <div class="title"><h3><spring:message code="userSettings"/></h3></div>
        <div class="back"><a href="Javascript:history.back()"><spring:message code="back"/></a></div>
    </div>
</div>
<%User user = (User) request.getAttribute("user");%>
<c:set var="nodata" scope="request"><spring:message code="nodata"/></c:set>
<c:set var="emailConfirmed"
       value='<%=request.getAttribute("emailConfirmed") != null ? request.getAttribute("emailConfirmed") : false%>'/>
<c:set var="changesSaved"
       value='<%=request.getAttribute("changesSaved") != null ? request.getAttribute("changesSaved") : false%>'/>
<c:set var="invalidPassword"
       value='<%=request.getAttribute("invalidPassword") != null ? request.getAttribute("invalidPassword") : false%>'/>
<c:set var="invalidEmail"
       value='<%=request.getAttribute("invalidEmail") != null ? request.getAttribute("invalidEmail") : false%>'/>
<c:if test="${param.changesSaved || param.invalidPassword || param.invalidEmail || param.emailConfirmed}">
    <c:if test="${param.changesSaved != null && param.changesSaved == true}">
        <div class="alert alert-success alert-block">
            <button type="button" class="close" data-dismiss="alert">×</button>
            <spring:message code="profileSaved"/>
        </div>
    </c:if>
    <c:if test="${param.userExists != null && param.userExists == true}">
        <div class="alert alert-error alert-block">
            <button type="button" class="close" data-dismiss="alert">×</button>
            <spring:message code="exception.userAlreadyExistWithEmail" arguments="${param.emailAddress}"/>
        </div>
    </c:if>
    <c:if test="${param.invalidPassword != null && param.invalidPassword == true}">
        <div class="alert alert-error alert-block">
            <button type="button" class="close" data-dismiss="alert">×</button>
            <spring:message code="invalidPassword"/>
        </div>
    </c:if>
    <c:if test="${param.invalidEmail != null && param.invalidEmail == true}">
        <div class="alert alert-error alert-block">
            <button type="button" class="close" data-dismiss="alert">×</button>
            <spring:message code="email.invalid"/>
        </div>
    </c:if>
    <c:if test="${param.emailConfirmed != null && param.emailConfirmed == true}">
        <div class="alert alert-success alert-block">
            <button type="button" class="close" data-dismiss="alert">×</button>
            <spring:message code="emailConfirmed" arguments="<%=user.getEmail()%>"/>
        </div>
    </c:if>
</c:if>
<div class="row-fluid">
    <div class="span12">
        <div class="tabbable">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="#details" data-toggle="tab"><span class="add-on"><b
                            class="icon-list"></b></span>&nbsp;<spring:message code="details"/></a>
                </li>
                <li><a href="#profile" data-toggle="tab"><span class="add-on"><b
                        class="icon-user"></b></span>&nbsp;<spring:message code="profile"/></a>
                </li>
                <li><a href="#security" data-toggle="tab"><span class="add-on"><b
                        class="icon-lock"></b></span>&nbsp;<spring:message code="security"/></a>
                </li>
            </ul>
            <div class="tab-content">
                <div class="tab-pane active" id="details">
                    <p><b><spring:message code="commonDetails"/></b></p>

                    <div class="span11 profileBox">
                        <div class="row-fluid">
                            <div class="span5"><spring:message code="firstName"/></div>
                            <div class="span4"><b><%=user.getSurname()%>
                            </b></div>
                        </div>
                        <div class="row-fluid">
                            <div class="span5"><spring:message code="secondName"/></div>
                            <div class="span4"><b><%=user.getName()%>
                            </b></div>
                        </div>
                        <div class="row-fluid">
                            <div class="span5"><spring:message code="patronymic"/></div>
                            <div class="span4"><b><%=user.getPatronymic()%>
                            </b></div>
                        </div>
                        <div class="row-fluid">
                            <div class="span5"><spring:message code="dateOfBirth"/></div>
                            <div class="span4"><b><fmt:formatDate value="<%=user.getDateOfBirth()%>"
                                                                  pattern="dd.MM.yyyy"/></b></div>
                        </div>
                        <div class="row-fluid">
                            <div class="span5"><spring:message code="phoneNumber"/></div>
                            <div class="span4"><b><%=user.getPhoneNumber()%>
                            </b></div>
                        </div>
                        <div class="row-fluid">
                            <div class="span5"><spring:message code="serial"/></div>
                            <div class="span4">
                                <c:set var="passportSerial"
                                       value='<%=(user.getPassport() != null) ? user.getPassport().getSerial() : ""%>'/>
                                <b>
                                    <c:if test="${passportSerial != ''}">
                                        ${passportSerial}
                                    </c:if>
                                    <c:if test="${passportSerial == ''}">
                                        ${nodata}
                                    </c:if>
                                </b>
                            </div>
                        </div>
                        <div class="row-fluid">
                            <div class="span5"><spring:message code="number"/></div>
                            <div class="span4">
                                <c:set var="passportNumber"
                                       value='<%=(user.getPassport() != null) ? user.getPassport().getNumber() : ""%>'/>
                                <b>
                                    <c:if test="${passportNumber != ''}">
                                        ${passportNumber}
                                    </c:if>
                                    <c:if test="${passportNumber == ''}">
                                        ${nodata}
                                    </c:if>
                                </b>
                            </div>
                        </div>
                        <div class="row-fluid">
                            <div class="span5"><spring:message code="dateOfIssue"/></div>
                            <div class="span4">
                                <c:set var="passportIssueDate"
                                       value='<%=(user.getPassport() != null && user.getPassport().getDate() != null) ? user.getPassport().getDate() : ""%>'/>
                                <b>
                                    <c:if test="${passportIssueDate != ''}">
                                        ${passportIssueDate}
                                    </c:if>
                                    <c:if test="${passportIssueDate == ''}">
                                        ${nodata}
                                    </c:if>
                                </b>
                            </div>
                        </div>
                        <div class="row-fluid">
                            <div class="span5"><spring:message code="registeredAddress"/></div>
                            <div class="span4">
                                <c:set var="passportRegisteredAddress"
                                       value='<%=(user.getPassport() != null) ? user.getPassport().getRegisteredAddress() : ""%>'/>
                                <b>
                                    <c:if test="${passportRegisteredAddress != ''}">
                                        ${passportRegisteredAddress}
                                    </c:if>
                                    <c:if test="${passportRegisteredAddress == ''}">
                                        ${nodata}
                                    </c:if>
                                </b>
                            </div>
                        </div>
                        <div class="row-fluid">
                            <div class="span5"><spring:message code="residenceAddress"/></div>
                            <div class="span4">
                                <c:set var="passportResidenceAddress"
                                       value='<%=(user.getPassport() != null) ? user.getPassport().getResidenceAddress() : ""%>'/>
                                <b>
                                    <c:if test="${passportResidenceAddress != ''}">
                                        ${passportResidenceAddress}
                                    </c:if>
                                    <c:if test="${passportResidenceAddress == ''}">
                                        ${nodata}
                                    </c:if>
                                </b>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="tab-pane" id="profile">
                    <p><b><spring:message code="userDetails"/></b></p>
                    <form:form action="/user/save_profile" modelAttribute="user">
                        <input type="hidden" name="id" value="<%=user.getId()%>"/>

                        <div class="span11 profileBox">
                            <div class="row-fluid">
                                <div class="span5"><spring:message code="firstName"/></div>
                                <div class="span4"><input type="text" name="surname" class="form-field span12"
                                                          value="<%=user.getSurname()%>"/></div>
                            </div>
                            <div class="row-fluid">
                                <div class="span5"><spring:message code="secondName"/></div>
                                <div class="span4"><input type="text" name="name" class="form-field span12"
                                                          value="<%=user.getName()%>"/></div>
                            </div>
                            <div class="row-fluid">
                                <div class="span5"><spring:message code="patronymic"/></div>
                                <div class="span4"><input type="text" name="patronymic" class="form-field span12"
                                                          value="<%=user.getPatronymic()%>"/></div>
                            </div>
                            <div class="row-fluid">
                                <div class="span5"><spring:message code="phoneNumber"/></div>
                                <div class="span4"><input type="text" name="phoneNumber" class="form-field span12"
                                                          value="<%=user.getPhoneNumber()%>"/></div>
                            </div>
                            <div class="row-fluid">
                                <div class="span5"><spring:message code="emailAddress"/></div>
                                <div class="span4"><%=user.getEmail()%>&nbsp;<a href="#"
                                                                                onclick="Javascript:$('#change-email-form').modal('show')"><spring:message
                                        code="change"/></a>
                                </div>
                            </div>
                            <div class="row-fluid">
                                <div class="span5">&nbsp;</div>
                                <div class="span4"><a
                                        href="${pageContext.request.contextPath}/user/confirm_email"><spring:message
                                        code="confirmEmail"/></a>
                                </div>
                            </div>
                        </div>
                        <button class="btn btn-primary" type="submit" style="margin-top: 10px"><spring:message
                                code="saveChanges"/></button>
                    </form:form>
                </div>
                <div class="tab-pane" id="security">
                    <p><b><spring:message code="accountAccess"/></b></p>

                    <div class="span11 profileBox">
                        <div class="row-fluid">
                            <div class="span5"><spring:message code="emailAddress"/></div>
                            <div class="span4"><%=user.getEmail()%>
                            </div>
                        </div>
                        <div class="row-fluid">
                            <div class="span5"><spring:message code="password"/></div>
                            <div class="span4"><a href="#"
                                                  onclick="Javascript:$('#change-password-form').modal('show')">
                                <spring:message code="changePassword"/></a><br><a
                                    href="${pageContext.request.contextPath}/user/forgot">
                                <spring:message code="forgotPassword"/>?</a></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="change-password-form" class="modal hide fade" tabindex="-1" role="dialog" aria-hidden="true">
    <form:form action="/user/change_password" method="POST" id="form1">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h3><spring:message code="changePassword"/></h3>
        </div>
        <div class="modal-body">
            <input type="text" name="old_password" class="form-field span12"
                   placeholder="<spring:message code="oldPassword"/>" autofocus="true">
            <input type="text" name="new_password" class="form-field span12"
                   placeholder="<spring:message code="newPassword"/>">
        </div>
        <div class="modal-footer">
            <button class="btn btn-primary" type="submit"><spring:message code="save"/></button>
            <button class="btn" type="button" data-dismiss="modal" aria-hidden="true"><spring:message
                    code="cancel"/></button>
        </div>
    </form:form>
</div>
<div id="change-email-form" class="modal hide fade" tabindex="-1" role="dialog" aria-hidden="true">
    <form:form action="/user/change_email" method="POST" id="email-form" onsubmit="return UserSettingsPage.beforeSubmit()">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h3><spring:message code="changeEmailAddress"/></h3>
        </div>
        <div class="modal-body">
            <input type="text" id="email1" name="new_email" class="form-field span12"
                   placeholder="<spring:message code="newEmailAddress"/>">
            <input type="text" id="email2" name="new_email_confirm" class="form-field span12"
                   placeholder="<spring:message code="emailAddressConfirm"/>">
            <input type="text" name="password" class="form-field span12"
                   placeholder="<spring:message code="accountPassword"/>">
        </div>
        <div class="modal-footer">
            <button class="btn btn-primary" type="submit"><spring:message code="save"/></button>
            <button class="btn" type="button" data-dismiss="modal" aria-hidden="true"><spring:message
                    code="cancel"/></button>
        </div>
    </form:form>
</div>
<%@ include file="/WEB-INF/pages/footer.jsp" %>
