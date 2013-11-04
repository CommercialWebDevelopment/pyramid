<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="/WEB-INF/pages/tabs/office.jsp" %>
<%@ include file="/WEB-INF/pages/tabs/user/alert-panel.jsp" %>

<div class="row-fluid">
    <div class="span8">
        <div id="users-tree" class="text-center">
            <div id="tree-panel">
                <div id="usersLarge" class="icon-th-large" title='<spring:message code="bigPhotos"/>'></div>
                <div id="usersSmall" class="icon-th" title='<spring:message code="smallPhotos"/>'></div>
                <div id="viewSwitcher" class="icon-resize-full" title='<spring:message code="extendedView"/>'></div>
            </div>
            <%=request.getAttribute("userBinaryTree")%>
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
