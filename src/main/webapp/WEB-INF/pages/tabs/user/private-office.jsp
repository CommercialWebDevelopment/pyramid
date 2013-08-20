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
    function transferMoney(){
        window.location.href = "/paypal/transferMoney";
    }
</script>

<div class="row-fluid">
    <button class="btn btn-primary" onclick="processPayment()"><spring:message code="buyPrivateOffice"/></button>
    <button class="btn btn-primary" onclick="transferMoney()"><spring:message code="takeMoney"/></button>
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
                        if (tree.itIsRight()) {
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
        <div class="span3" style="border: 1px solid #808080">
            <%--Send email to user--%>
            <div class="modal hide fade" aria-hidden="true" tabindex="-1" role="dialog" id="user-email-form">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h5 id="add-user-form-abel"><spring:message code="sendAnInvitation"/></h5>
                        </div>
                        <div class="modal-body">
                            <form:form method="POST" id="send-email-to-user" action="/user/add"
                                       modelAttribute="registration">
                                <div class="row-fluid control-group error">
                                    <div class="span3">
                                        <label for="email" class="required_label form-field"><spring:message
                                                code="email"/><span class="asterisk_red">*</span></label>
                                    </div>
                                    <div class="span9 controls">
                                        <div class="form-field">
                                            <input id="email" name="email" type="text" class="form-field"
                                                   onkeyup="Registration.validateEMailField(this, value)">
                                        </div>
                                    </div>
                                </div>
                            </form:form>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button class="btn" data-dismiss="modal" aria-hidden="true"><spring:message
                                code="cancel"/></button>
                        <button class="btn btn-primary" type="submit" form="send-email-to-user"><spring:message
                                code="send"/></button>
                    </div>
                </div>
            </div>
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
