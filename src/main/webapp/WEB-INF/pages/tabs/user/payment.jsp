<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="/WEB-INF/pages/tabs/office.jsp" %>

<div class="row-fluid">
    <div class="text-right"><a href="Javascript:history.back()">Назад</a></div>
    <form:form action="/paypal/pay" modelAttribute="payPalDetails">
        <legend><spring:message code="privateOfficeBuyFormTitle"/></legend>
        <input type="hidden" name="returnUrl" value="${payPalDetails.returnUrl}"/>
        <input type="hidden" name="cancelUrl" value="${payPalDetails.cancelUrl}"/>
        <input type="hidden" name="receiverEmail" value="${payPalDetails.receiverEmail}"/>
        <input type="hidden" name="memo" value="Payment for private office"/>

        <div class="span6"><spring:message code="privateOfficePrice"/><b> $${payPalDetails.amount}</b></div>
        <div class="control-group">
            <input type="hidden" name="amount" value="${payPalDetails.amount}"/>
            <label class="control-label" for="inputIcon"><spring:message code="payPalLogin"/>:</label>

            <div class="controls">
                <div class="input-prepend">
                    <span class="add-on"><i class="icon-envelope"></i></span>
                    <input class="span12" id="inputIcon" type="text" name="senderEmail">
                    <img alt="" border="0" src="https://www.sandbox.paypal.com/en_GB/i/scr/pixel.gif" width="5"
                         height="1">
                    <input type="image" src="https://www.sandbox.paypal.com/en_GB/i/btn/btn_paynow_LG.gif" border="0"
                           name="submit"
                           alt="PayPal - The safer, easier way to pay online!">
                </div>
            </div>
            <div class="text-warning">
                <spring:message code="payPalDisclaimer" />
            </div>
        </div>
    </form:form>
</div>
<%@ include file="/WEB-INF/pages/footer.jsp" %>
