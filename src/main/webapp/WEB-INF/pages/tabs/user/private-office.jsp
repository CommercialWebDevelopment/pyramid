<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="/WEB-INF/pages/tabs/office.jsp" %>
<%@ include file="/WEB-INF/pages/tabs/user/alert-panel.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/tree.js"></script>

<div class="row-fluid">
    <div class="span8">
        <div id="users-tree" class="text-center">
            <div id="tree-panel">
                <div id="addUser" class="icon-plus-sign" title='<spring:message code="addUserWithAutomatic"/>'></div>
                <div id="usersLarge" class="icon-th-large" title='<spring:message code="bigPhotos"/>'></div>
                <div id="usersSmall" class="icon-th" title='<spring:message code="smallPhotos"/>'></div>
                <div id="viewSwitcher" class="icon-resize-full" title='<spring:message code="extendedView"/>'></div>
                <div id="top-user" class="icon-user" title='<spring:message code="displayYourTree"/>'></div>
                <div id="up-users" class="icon-arrow-up" title='<spring:message code="displayUsersInOver"/>'></div>
            </div>
            <div id="tree-widget"><%=request.getAttribute("userBinaryTree")%></div>
        </div>
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
