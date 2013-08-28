<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="/WEB-INF/pages/tabs/office.jsp" %>
<%@ include file="/WEB-INF/pages/tabs/user/alert-panel.jsp" %>
<c:set var="daysLeft"><%=request.getAttribute("daysLeft")%>
</c:set>
<div class="row-fluid">
    <div class="span12">
        <div class="span9" style="border: 1px dotted blue; height: 589px; overflow: auto; padding: 10px" align="center">
            <%=request.getAttribute("userBinaryTree")%>
        </div>
        <div id="sidebar" class="span3">
            <div><b><spring:message code="privateOfficeSum"/></b></div>
            <div class="sum">$<%=request.getAttribute("earningsSum")%>
            </div>
            <div class="separator"></div>
            <div><b><spring:message code="privateOfficeActivation"/></b></div>
            <c:if test="${daysLeft > 0}">
                <div style="color:#008000"><b><spring:message code="privateOfficeActivated"/></b></div>
                <div id="bar" class="bar-graph" style="width: 207px">
                    <%
                        int countDays = (Integer) request.getAttribute("monthDays");
                        int leftDays = (Integer) request.getAttribute("daysLeft");
                        int barWidth = leftDays * 207 / countDays;
                    %>
                    <div class="bar-graph-normal" style="width:<%=barWidth%>px"></div>
                </div>
                <div class="text-center">
                    <small><spring:message code="privateOfficeDueDays"
                                           arguments='${daysLeft}'/></small>
                </div>
            </c:if>
            <c:if test="${daysLeft <= 0}">
                <div style="color:#808080"><b><spring:message code="privateOfficeNotActivated"/></b></div>
            </c:if>
            <div class="separator"></div>
            <div class="office-menu">
                <ul class="nav nav-pills">
                    <li class="office-menu-item <c:if test="${daysLeft <= 0}">disabled</c:if>"><a
                            href="${pageContext.request.contextPath}/paypal/buyOffice"><spring:message
                            code="buyPrivateOffice"/></a></li>
                    <li class="office-menu-item"><a
                            href="${pageContext.request.contextPath}/paypal/sendMoney"><spring:message
                            code="takeMoney"/></a></li>
                </ul>
            </div>
            <div class="separator"></div>
            <div class="office-menu">
                <div><b><spring:message code="privateOfficeEarnings"/></b></div>
                <%@ include file="earnings-report.jsp" %>
            </div>
        </div>
    </div>
</div>
<%--Send email to user--%>
<div class="modal hide fade" aria-hidden="true" tabindex="-1" role="dialog" id="user-email-form">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h5 id="add-user-form-abel"><spring:message code="sendAnInvitation"/></h5>
            </div>
            <div class="modal-body">
                <form:form method="POST" id="send-email-to-user" action="/invitation/send"
                           modelAttribute="invitation">

                    <input id="parentId" name="parentId" type="hidden">
                    <input id="position" name="position" type="hidden">

                    <div class="row-fluid control-group error">
                        <div class="span3">
                            <label for="email" class="required_label form-field"><spring:message
                                    code="email"/><span class="asterisk_red">*</span></label>
                        </div>
                        <div class="span9 controls">
                            <div class="form-field">
                                <input id="email" name="email" type="text" class="form-field"
                                       onkeyup="Form.validateEMailField(this, value)">
                            </div>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true"><spring:message
                    code="cancel"/></button>
            <button class="btn btn-primary" type="submit" form="send-email-to-user"><spring:message
                    code="send"/></button>
        </div>
    </div>
</div>
</div>
</div>
</div>
<%@ include file="/WEB-INF/pages/footer.jsp" %>
