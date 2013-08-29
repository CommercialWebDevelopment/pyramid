<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="daysLeft"><%=request.getAttribute("daysLeft")%>
</c:set>
<div><b><spring:message code="privateOfficeSum"/></b></div>
<div class="sum">$<%=request.getAttribute("earningsSum")%>
</div>
<div class="separator"></div>
<div><b><spring:message code="privateOfficeActivation"/></b></div>
<c:if test="${daysLeft >= 0}">
    <div style="color:#008000"><b><spring:message code="privateOfficeActivated"/></b></div>
    <div id="bar" class="bar-graph" style="width: 207px">
        <%
            int barWidth = 0;
            int countDays = (Integer) request.getAttribute("monthDays");
            int leftDays = (Integer) request.getAttribute("daysLeft");
            if (countDays > 0) {
                barWidth = leftDays * 207 / countDays;
            }
            Integer[] cases = new Integer[6];
            cases[0] = 2;
            cases[1] = 0;
            cases[2] = 1;
            cases[3] = 1;
            cases[4] = 1;
            cases[5] = 2;
            Integer position = 0;
            if (leftDays % 100 > 4 && leftDays % 100 < 20) {
                position = 2;
            } else {
                position = cases[Math.min(leftDays % 10, 5)];
            }
        %>
        <c:set var="p" value="<%=position%>"/>
        <div class="bar-graph-normal" style="width:<%=barWidth%>px"></div>
    </div>
    <div class="text-center">
        <small>
            <c:if test="${daysLeft > 0}">
                <spring:message code="privateOfficeDueDays" arguments='${daysLeft}'/>
                <c:if test="${p==0}">
                    <spring:message code="daysSingle"/>
                </c:if>
                <c:if test="${p==1}">
                    <spring:message code="daysMulti"/>
                </c:if>
                <c:if test="${p==2}">
                    <spring:message code="daysMany"/>
                </c:if>
            </c:if>
            <c:if test="${daysLeft == 0}">
                <spring:message code="privateOfficeDueToday"/>
            </c:if>
        </small>
    </div>
</c:if>
<c:if test="${daysLeft < 0}">
    <div style="color:#808080"><b><spring:message code="privateOfficeNotActivated"/></b></div>
</c:if>
<div class="separator"></div>
<div class="office-menu">
    <ul class="nav nav-pills">
        <li class="office-menu-item <c:if test='${daysLeft > 0}'><c:out value="disabled"/></c:if>"><a
                href="${pageContext.request.contextPath}/paypal/buyOffice"><spring:message
                code="buyPrivateOffice"/></a></li>
        <li class="office-menu-item"><a
                href="${pageContext.request.contextPath}/paypal/sendMoney"><spring:message
                code="takeMoney"/></a></li>
    </ul>
</div>
<div class="separator"></div>
<div class="office-menu">
    <div><b><spring:message code="privateOfficeEarnings"/></b></div>
    <%@ include file="earnings-report.jsp" %>
</div>