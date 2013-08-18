<%@ page import="com.financial.pyramid.web.tree.BinaryTree" %>
<%@ page import="com.financial.pyramid.web.tree.BinaryTreeWidget" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="/WEB-INF/pages/tabs/office.jsp" %>

<script language="javascript">
    function processPayment(){
        window.location.href = "/paypal/payment";
    }
    function takeMoney(){
        window.location.href = "/paypal/take";
    }
</script>

<div class="row-fluid">
    <button class="btn btn-primary" onclick="processPayment()"><spring:message code="buyPrivateOffice"/></button>
    <button class="btn btn-primary" onclick="takeMoney()"><spring:message code="takeMoney"/></button>
</div>
<br>
<div class="row-fluid">
    <div class="span12">
        <div class="span9" style="border: 1px solid blue; overflow: auto">
            <c:set var="addUser">
                <spring:message code='user.add'/>
            </c:set>
            <%
                BinaryTree tree = (BinaryTree) request.getAttribute("userBinaryTree");
                BinaryTreeWidget widget = new BinaryTreeWidget(tree);
                widget.setStubText((String) pageContext.getAttribute("addUser"));

                while (tree != null) {
                    widget.addUserToWidget(tree);

                    if (tree.isChild()) {
                        tree = tree.getLeft() != null ? tree.getLeft() : tree.getRight();
                    } else {
                        if (tree.isParent() && tree.getParent().getRight() == tree) {
                            tree = tree.getParent().getParent();
                            if (tree == null) continue;
                        }
                        while (!tree.isRight() && tree.isParent()) {
                            tree = tree.getParent();
                        }
                        tree = tree.getRight();
                    }
                }
            %>
            <%=widget.getRootElement()%>
        </div>
        <div class="span3" style="border: 1px solid #808080"></div>
    </div>
    <%--<a href="<c:url value="/user/logout" />" class="btn btn-primary" type="button">Logout</a>--%>
</div>
<div class="row-fluid">
    <div class="span12">
        <!--     Reports and Graphs    -->
        <%@ include file="earnings-report.jsp" %>
    </div>
</div>

<%@ include file="/WEB-INF/pages/footer.jsp" %>
