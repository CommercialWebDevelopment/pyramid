<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="/WEB-INF/pages/tabs/office.jsp" %>
<script language="javascript">
    function beforeSubmit() {
        var form = $("#payForm");
        var emailField = form.find("#emailField");
        Form.validateEMailField(emailField, emailField.val());
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
    <form:form action="/paypal/pay" modelAttribute="payPalDetails" id="payForm" onsubmit="return beforeSubmit()">
        <legend><spring:message code="privateOfficeBuyFormTitle"/></legend>
        <input type="hidden" name="returnUrl" value="${payPalDetails.returnUrl}"/>
        <input type="hidden" name="cancelUrl" value="${payPalDetails.cancelUrl}"/>
        <input type="hidden" name="receiverEmail" value="${payPalDetails.receiverEmail}"/>
        <input type="hidden" name="memo" value=""/>

        <div class="span11 text-center">
            <spring:message code="privateOfficePrice"/><b> $${payPalDetails.amount}</b>
        </div>
        <div class="span11 text-center">
            <div class="control-group">
                <input type="hidden" name="amount" value="${payPalDetails.amount}"/>
                <label class="control-label" for="emailField"><spring:message code="payPalLogin"/>:</label>

                <div class="controls">
                    <div class="input-prepend">
                        <span class="add-on"><b>@</b></span>
                        <input class="span12" id="emailField" type="text" name="senderEmail">
                        </div>
                </div>
            </div>
        </div>
        <div class="span11 text-center">
            <img alt="" border="0" src="https://www.sandbox.paypal.com/en_GB/i/scr/pixel.gif" width="5"
                 height="1">
            <input type="image" src="https://www.sandbox.paypal.com/en_GB/i/btn/btn_paynow_LG.gif"
                   border="0"
                   name="submit"
                   alt="PayPal - The safer, easier way to pay online!">
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
