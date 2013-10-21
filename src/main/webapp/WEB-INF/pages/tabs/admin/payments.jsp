<%@ page import="com.financial.pyramid.domain.Operation" %>
<%@ page import="com.financial.pyramid.web.form.PageForm" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="/WEB-INF/pages/tabs/admin.jsp" %>
<div class="row-fluid">
    <div class="span12" id="admin-menu">
        <ul class="nav nav-tabs">
            <li><a href="<c:url value="/app/admin/user_settings"/>" data-toogle="tab"><spring:message
                    code="users"/></a></li>
            <li><a href="<c:url value="/app/admin/content_settings"/>" data-toogle="tab"><spring:message
                    code="tabs.text"/></a></li>
            <li><a href="<c:url value="/app/admin/news_settings"/>" data-toogle="tab"><spring:message
                    code="news"/></a></li>
            <li><a href="<c:url value="/app/admin/video_settings"/>" data-toogle="tab"><spring:message
                    code="tabs.video"/></a></li>
            <li><a href="<c:url value="/app/admin/contact_settings"/>" data-toogle="tab"><spring:message
                    code="contacts"/></a></li>
            <li><a href="<c:url value="/app/admin/application_settings"/>" data-toogle="tab"><spring:message
                    code="tabs.parameters"/></a></li>
            <li class="active"><a href="<c:url value="/app/admin/payments"/>" data-toogle="tab"><spring:message
                    code="tabs.payments"/></a></li>
        </ul>
    </div>
</div>
<div class="row-fluid">
    <div class="span12">
        <table class="table table-striped" style="font-size: 10px">
            <tr>
                <th><spring:message code="type"/></th>
                <th><spring:message code="date"/></th>
                <th><spring:message code="payer"/></th>
                <th><spring:message code="receiver"/></th>
                <th><spring:message code="memo"/></th>
                <th><spring:message code="amount"/></th>
                <th><spring:message code="error"/></th>
                <th><spring:message code="result"/></th>
            </tr>
            <c:forEach items="${paymentsForm.rows}" var="payments" varStatus="status">
                <tr>
                    <td>${payments.type}</td>
                    <td>${payments.date}</td>
                    <td>${payments.payer}</td>
                    <td>${payments.payee}</td>
                    <td>${payments.memo}</td>
                    <td>${payments.amount}</td>
                    <td>${payments.error}</td>
                    <td>${payments.result}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div class="row-fluid">
        <div class="span12">
            <%
                PageForm<Operation> pageForm = (PageForm<Operation>) request.getAttribute("paymentsForm");
                Integer pages = (int) Math.ceil(Double.valueOf(pageForm.getTotal() / 10.0));
                if (pages > 1) {
            %>
            <div class="pagination pagination-centered">
                <ul>
                    <li>
                        <a href="/app/admin/payments?page=<%=(pageForm.getPage()-1 > 0 ? pageForm.getPage()-1 : 1)%>">«</a>
                    </li>
                    <%
                        for (int i = 1; i <= pages; i++) {
                            String active = "";
                            pageForm.page = pageForm.page == 0 ? 1 : pageForm.page;
                            if (pageForm.page == i) {
                                active = "active";
                            }
                    %>
                    <li class="<%=active%>"><a href="/app/admin/payments?page=<%=i%>"><%=i%>
                    </a></li>
                    <%
                        }
                    %>
                    <li><a href="/app/admin/payments?page=<%=pageForm.getPage()+1%>">»</a></li>
                </ul>
            </div>
            <%
                }
            %>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/pages/footer.jsp" %>