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
        if (!Form.validateForm(form)) {
            Alert.show(Alert.ERROR, I18N.incorrectFields);
            return false;
        } else {
            return true;

        }
    }
</script>
<div class="row-fluid">
    <div class="text-right"><a href="Javascript:history.back()"><spring:message code="back"/></a></div>
    <form:form action="/paypal/processTransfer" modelAttribute="payPalDetails" id="takeMoneyForm"
               onsubmit="return beforeSubmit()">
        <legend><spring:message code="privateOfficeTakeMoneyFormTitle"/></legend>
        <input type="hidden" name="returnUrl" value="${payPalDetails.returnUrl}"/>
        <input type="hidden" name="cancelUrl" value="${payPalDetails.cancelUrl}"/>
        <input type="hidden" name="senderEmail" value="${payPalDetails.senderEmail}"/>
        <input type="hidden" name="memo" value=""/>

        <div class="span11 text-center">
            <spring:message code="maxAllowedTransferSum"/>
            <b>&nbsp;$<%=(String) request.getAttribute("maxAllowedAmount")%>&nbsp;</b>
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
    <div class="text-warning">
        <spring:message code="payPalDisclaimer"/>
    </div>
</div>
<%@ include file="/WEB-INF/pages/footer.jsp" %>
