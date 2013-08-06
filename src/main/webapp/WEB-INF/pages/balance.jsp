<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
    <title></title>
</head>
<body>
<form:form method="POST" action="/money/balance">
    <input type="text" style="width: 100%" class="form-field" name="accountID" value="100079">
    <input type="text" style="width: 100%" class="form-field" name="password" value="This1isnotReal">
    <button type="submit" class="btn btn-primary">Send</button>
</form:form>
</body>
</html>