<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="/WEB-INF/pages/tabs/office.jsp" %>
<div class="row-fluid">
    <div class="span10 page-title">
        <div class="title"><h3><spring:message code="userSettings"/></h3></div>
        <div class="back"><a href="Javascript:history.back()"><spring:message code="back"/></a></div>
    </div>
</div>
<%User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();%>
<c:set var="nodata" scope="request"><spring:message code="nodata"/></c:set>
<c:if test="${param.result == true}">
    <div class="row-fluid">
        <div class="span10">
            <div class="alert alert-success alert-block">
                <button type="button" class="close" data-dismiss="alert">×</button>
                <strong><spring:message code="profileSaved"/></strong>
            </div>
        </div>
    </div>
</c:if>
<div class="row-fluid">
    <div class="span10">
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

                    <div style="border: 1px dotted #DDDDDD; padding: 15px" class="span10">
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
                                       value='<%=(user.getPassport() != null) ? user.getPassport().getDate() : ""%>'/>
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

                        <div style="border: 1px dotted #DDDDDD; padding: 15px" class="span10">
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
                                                                                onclick="Javascript:$('#change-email-form').modal('show')">изменить</a>
                                </div>
                            </div>
                        </div>
                        <button class="btn btn-primary" type="submit" style="margin-top: 10px"><spring:message
                                code="saveChanges"/></button>
                    </form:form>
                </div>
                <div class="tab-pane" id="security">
                    <p><b><spring:message code="accountAccess"/></b></p>

                    <div style="border: 1px dotted #DDDDDD; padding: 15px" class="span10">
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
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3><spring:message code="changePassword"/></h3>
    </div>
    <div class="modal-body">
        <form:form action="/user/change_password" method="POST" modelAttribute="news" id="form1">
            <input type="text" name="old_password" class="form-field span12"
                   placeholder="<spring:message code="oldPassword"/>">
            <input type="text" name="new_password" class="form-field span12"
                   placeholder="<spring:message code="newPassword"/>">
        </form:form>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary" type="submit"><spring:message code="save"/></button>
        <button class="btn" type="button" data-dismiss="modal" aria-hidden="true"><spring:message
                code="cancel"/></button>
    </div>
</div>
<div id="change-email-form" class="modal hide fade" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3><spring:message code="changeEmailAddress"/></h3>
    </div>
    <div class="modal-body">
        <form:form action="/user/change_email" method="POST" modelAttribute="news" id="form2">
            <input type="text" name="new_email" class="form-field span12"
                   placeholder="<spring:message code="newEmailAddress"/>">
            <input type="text" name="new_email_confirm" class="form-field span12"
                   placeholder="<spring:message code="emailAddressConfirm"/>">
            <input type="text" name="password" class="form-field span12"
                   placeholder="<spring:message code="accountPassword"/>">
        </form:form>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary" type="submit"><spring:message code="save"/></button>
        <button class="btn" type="button" data-dismiss="modal" aria-hidden="true"><spring:message
                code="cancel"/></button>
    </div>
</div>
<%@ include file="/WEB-INF/pages/footer.jsp" %>
