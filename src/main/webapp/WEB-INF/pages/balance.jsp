<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/pages/header.jsp" %>
<form:form method="POST" action="/money/balance">
    <input type="text" style="width: 100%" class="form-field" name="accountID" value="100079">
    <input type="text" style="width: 100%" class="form-field" name="password" value="This1isnotReal">
    <button type="submit" class="btn btn-primary">Send</button>
</form:form>

<form:form method="POST" action="/money/payment">
    <input type="text" style="width: 100%" class="form-field" name="accountID" value="100079">
    <input type="text" style="width: 100%" class="form-field" name="password" value="This1isnotReal">
    <button type="submit" class="btn btn-primary">Send</button>
</form:form>

<%@ include file="/WEB-INF/pages/footer.jsp" %>
