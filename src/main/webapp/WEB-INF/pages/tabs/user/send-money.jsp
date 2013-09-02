<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="/WEB-INF/pages/tabs/office.jsp" %>
<script language="javascript">
    function beforeSubmit() {
        var form = $("#takeMoneyForm");
        var emailField = form.find("#emailField");
        var amountField = form.find("#amountField");
        Form.validateEMailField(emailField, emailField.val());
        Form.validateFloatField(amountField, amountField.val());
        var invalidSum = amountField.val() > ${maxAllowedAmount};
        if (invalidSum) {
            Form.setInvalidField(amountField);
            Alert.show(Alert.ERROR, I18N.invalidSum);
            return false;
        } else if (!Form.validateForm(form)) {
            Alert.show(Alert.ERROR, I18N.incorrectFields);
            return false;
        } else {
            LoadingBar.show(I18N.sendToPayPal);
            return true;
        }
    }
</script>
<div class="row-fluid">
    <div class="span10 page-title">
        <div class="title"><h3><spring:message code="takeMoney"/></h3></div>
        <div class="back"><a href="Javascript:history.back()"><spring:message code="back"/></a></div>
    </div>
</div>
<c:if test="${param.error != null && param.error == 'limit_reached'}">
    <div class="alert alert-error alert-block">
        <button type="button" class="close" data-dismiss="alert">×</button>
        <strong><spring:message code='transferLimitIsReached'/></strong>
    </div>
</c:if>
<c:if test="${param.error != null && param.error == 'not_allowed_to_be_transferred'}">
    <div class="alert alert-error alert-block">
        <button type="button" class="close" data-dismiss="alert">×</button>
        <strong><spring:message code='transferAllowedSum' arguments="${param.transfer_sum}"/></strong>
    </div>
</c:if>
<c:if test="${param.error != null && param.error == 'not_enough_money'}">
    <div class="alert alert-error alert-block">
        <button type="button" class="close" data-dismiss="alert">×</button>
        <strong><spring:message code='notEnoughMoney'/></strong>
    </div>
</c:if>
<div class="row-fluid">
    <form:form action="/paypal/sendFunds" modelAttribute="payPalDetails" id="takeMoneyForm"
               onsubmit="return beforeSubmit()">
        <legend><spring:message code="privateOfficeTakeMoneyFormTitle"/></legend>
        <input type="hidden" name="returnUrl" value="${payPalDetails.returnUrl}"/>
        <input type="hidden" name="cancelUrl" value="${payPalDetails.cancelUrl}"/>
        <input type="hidden" name="senderEmail" value="${payPalDetails.senderEmail}"/>
        <input type="hidden" name="memo" value=""/>

        <div class="span11 text-center">
            <spring:message code="maxAllowedTransferSum"/>
            <b>&nbsp;$${maxAllowedAmount}&nbsp;</b>
            <spring:message code="perDay"/>
        </div>
        <div class="span11 text-center">
            <div class="control-group">
                <label class="control-label" for="emailField"><spring:message code="payPalLogin"/>:</label>

                <div class="controls">
                    <div class="input-prepend">
                        <span class="add-on"><b>@</b></span>
                        <input class="span12" type="text" id="emailField" name="receiverEmail"
                               value="${payPalDetails.receiverEmail}"
                               onkeyup="Form.validateEMailField(this, value)">
                    </div>
                </div>
            </div>
        </div>
        <div class="span11 text-center">
            <div class="control-group">
                <label class="control-label" for="amountField"><spring:message code="moneySum"/>:</label>

                <div class="controls">
                    <div class="input-prepend">
                        <span class="add-on"><b>$</b></span>
                        <input class="span12" type="text" id="amountField" name="amount"
                               value="${payPalDetails.amount}" onkeyup="Form.validateFloatField(this, value)">
                    </div>
                </div>
            </div>
            <br>

            <div class="control-group">
                <div class="controls">
                    <button class="btn btn-primary"><spring:message code="sendMoney"/></button>
                </div>
            </div>
        </div>
    </form:form>
</div>
<br>

<div class="row-fluid">
    <div class="alert alert-warning alert-block"><spring:message code="payPalDisclaimer"/></div>
</div>
<%@ include file="/WEB-INF/pages/footer.jsp" %>
