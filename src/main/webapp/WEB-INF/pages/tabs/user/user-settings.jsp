<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="/WEB-INF/pages/tabs/office.jsp" %>
<div class="row-fluid">
    <div class="span10 page-title">
        <div class="title"><h3><spring:message code="userSettings"/></h3></div>
        <div class="back"><a href="Javascript:history.back()"><spring:message code="back"/></a></div>
    </div>
</div>
<%User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();%>
<div class="row-fluid">
    <div class="span10">
        <div class="tabbable">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="#details" data-toggle="tab"><span class="add-on"><b class="icon-list"></b></span>&nbsp;Сведения</a>
                </li>
                <li><a href="#profile" data-toggle="tab"><span class="add-on"><b class="icon-user"></b></span>&nbsp;Профиль</a>
                </li>
                <li><a href="#security" data-toggle="tab"><span class="add-on"><b class="icon-lock"></b></span>&nbsp;Безопасность</a>
                </li>
            </ul>
            <div class="tab-content">
                <div class="tab-pane active" id="details">
                    <p>Общие сведения об аккаунте</p>

                    <div style="border: 1px dotted #DDDDDD; padding: 15px" class="span10">
                        <div class="row-fluid">
                        </div>
                    </div>
                </div>
                <div class="tab-pane" id="profile">
                    <p>Сведения о пользователе</p>

                    <div style="border: 1px dotted #DDDDDD; padding: 15px" class="span10">
                        <div class="row-fluid">
                            <div class="span5"><spring:message code="firstName"/></div>
                            <div class="span4"><input type="text" class="form-field span12"
                                                      value="<%=user.getSurname()%>"/></div>
                        </div>
                        <div class="row-fluid">
                            <div class="span5"><spring:message code="secondName"/></div>
                            <div class="span4"><input type="text" class="form-field span12"
                                                      value="<%=user.getName()%>"/></div>
                        </div>
                        <div class="row-fluid">
                            <div class="span5"><spring:message code="patronymic"/></div>
                            <div class="span4"><input type="text" class="form-field span12"
                                                      value="<%=user.getPatronymic()%>"/></div>
                        </div>
                        <div class="row-fluid">
                            <div class="span5"><spring:message code="phoneNumber"/></div>
                            <div class="span4"><input type="text" class="form-field span12"
                                                      value="<%=user.getPhoneNumber()%>"/></div>
                        </div>
                        <div class="row-fluid">
                            <div class="span5"><spring:message code="emailAddress"/></div>
                            <div class="span4"><%=user.getEmail()%>&nbsp;<a href="#"
                                                                            onclick="Javascript:$('#change-email-form').modal('show')">изменить</a>
                            </div>
                        </div>
                    </div>
                    <button class="btn btn-primary" type="submit" style="margin-top: 10px">Сохранить изменения</button>
                </div>
                <div class="tab-pane" id="security">
                    <p><b>Доступ в аккаунт</b></p>

                    <div style="border: 1px dotted #DDDDDD; padding: 15px" class="span10">
                        <div class="row-fluid">
                            <div class="span5"><spring:message code="emailAddress"/></div>
                            <div class="span4"><%=user.getEmail()%>
                            </div>
                        </div>
                        <div class="row-fluid">
                            <div class="span5"><spring:message code="password"/></div>
                            <div class="span4"><a href="#"
                                                  onclick="Javascript:$('#change-password-form').modal('show')">Изменить
                                пароль</a><br><a href="/user/forgot"><spring:message code="forgotPassword"/>?</a></div>
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
        <h3>Изменить пароль</h3>
    </div>
    <div class="modal-body">
        <form:form action="/user/change_password" method="POST" modelAttribute="news" id="form1">
            <input type="text" name="old_password" class="form-field span12" placeholder="Старый пароль">
            <input type="text" name="new_password" class="form-field span12" placeholder="Новый пароль">
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
        <h3>Изменить адрес электронной почты</h3>
    </div>
    <div class="modal-body">
        <form:form action="/user/change_email" method="POST" modelAttribute="news" id="form2">
            <input type="text" name="new_email" class="form-field span12" placeholder="Новый адрес электронной почты">
            <input type="text" name="new_email_confirm" class="form-field span12" placeholder="Подтверждение адреса электронной почты">
            <input type="text" name="password" class="form-field span12" placeholder="Пароль от аккаунта">
        </form:form>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary" type="submit"><spring:message code="save"/></button>
        <button class="btn" type="button" data-dismiss="modal" aria-hidden="true"><spring:message
                code="cancel"/></button>
    </div>
</div>
<%@ include file="/WEB-INF/pages/footer.jsp" %>
