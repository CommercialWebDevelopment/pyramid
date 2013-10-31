<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Users Tree</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel=stylesheet href="${pageContext.request.contextPath}/resources/javascript/bootstrap/css/bootstrap.css"
          type="text/css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/tree.css"
          type="text/css">
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/javascript/jquery-1.10.0/jquery-1.10.1.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/javascript/bootstrap/js/bootstrap.js"></script>
</head>
<body>
<script type="text/javascript">
    $(document).ready(function () {
        $(".user-photo").popover({placement: "right", animation: true, html: true, trigger: "hover"});
    })
</script>
<div id="users-tree" class="text-center"><%=request.getAttribute("userBinaryTree")%></div>
</body>
</html>