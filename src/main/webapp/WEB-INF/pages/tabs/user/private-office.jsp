<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="/WEB-INF/pages/tabs/office.jsp" %>
<%@ include file="/WEB-INF/pages/tabs/user/alert-panel.jsp" %>

<link rel=stylesheet href="${pageContext.request.contextPath}/resources/css/tree.css" type="text/css">

<div class="row-fluid">
    <div class="span8">
        <div id="users-tree" class="text-center"><%=request.getAttribute("userBinaryTree")%></div>
    </div>
    <div id="sidebar" class="span4">
        <%@include file="office-sidebar.jsp" %>
    </div>
</div>
<%@include file="invitation-form.jsp" %>
</div>
</div>
</div>
<%@ include file="/WEB-INF/pages/footer.jsp" %>
