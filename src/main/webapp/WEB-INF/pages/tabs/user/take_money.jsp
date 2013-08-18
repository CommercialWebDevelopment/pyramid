<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="/WEB-INF/pages/tabs/office.jsp" %>

<div class="row-fluid">
<div class="text-right"><a href="Javascript:history.back()">Назад</a></div>
<form:form action="/paypal/pay" modelAttribute="payPalDetails">
    <legend><spring:message code="privateOfficeTakeMoneyFormTitle"/></legend>
    <input type="hidden" name="returnUrl" value="${payPalDetails.returnUrl}"/>
    <input type="hidden" name="cancelUrl" value="${payPalDetails.cancelUrl}"/>
    <input type="hidden" name="senderEmail" value="${payPalDetails.senderEmail}"/>
    <input type="hidden" name="memo" value="Payment for private office"/>

    <div class="span11 text-center">
        <spring:message code="maxAllowedTransferSum"/>
        <b> $<%=(String) request.getAttribute("maxAllowedAmount")%>
        </b>
        <spring:message code="perDay"/>
    </div>
    <div class="span11 text-center">
        <div class="control-group">
            <label class="control-label" for="inputIcon1"><spring:message code="payPalLogin"/>:</label>
            <div class="controls">
                <div class="input-prepend">
                    <span class="add-on"><i class="icon-envelope"></i></span>
                    <input class="span12" id="inputIcon1" type="text" name="receiverEmail"
                           value="${payPalDetails.receiverEmail}">
                </div>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="inputIcon2"><spring:message code="moneySum"/>:</label>
            <div class="controls">
                <div class="input-prepend">
                    <span class="add-on"><b>$</b></span>
                    <input class="span12" id="inputIcon2" type="text" name="amount" value="${payPalDetails.amount}">
                </div>
            </div>
        </div>
        <button class="btn btn-primary">Перевести</button>
    </div>
    <div class="text-warning">
        <spring:message code="payPalDisclaimer"/>
    </div>
    </div>
</form:form>
</div>
<%@ include file="/WEB-INF/pages/footer.jsp" %>
