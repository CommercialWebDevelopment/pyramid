<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="/WEB-INF/pages/header.jsp" %>
<%--Tabs--%>
<div class="row-fluid">
    <div class="span8 offset1">
        <ul class="nav nav-pills" id="tabs">
            <li><a href="<c:url value="/pyramid/home"/>"><spring:message code="home"/></a></li>
            <security:authorize access="hasAnyRole('SUPER_ADMIN,ADMIN')">
                <li><a href="<c:url value="/pyramid/admin/"/>"><spring:message code="settings"/></a></li>
            </security:authorize>
            <li><a href="<c:url value="/pyramid/office"/>"><spring:message code="privateOffice"/></a></li>
            <li><a href="<c:url value="/pyramid/news"/>"><spring:message code="news"/></a></li>
            <li><a href="<c:url value="/pyramid/training"/>"><spring:message code="training"/></a></li>
            <li><a href="<c:url value="/pyramid/about"/>"><spring:message code="aboutProject"/></a></li>
            <li class="active"><a href="<c:url value="/pyramid/contacts"/>"><spring:message code="contacts"/></a></li>
        </ul>
    </div>
</div>
<div class="row-fluid">
    <div class="span10">
        <div id="tab-content" class="tab-content">
            <%--Content--%>

            <h3><spring:message code="contacts"/></h3>

            <c:if test="${param.sent != null}">
            <div class="text-center">
                <c:if test="${param.sent == true}">
                    <div class="alert alert-success alert-block">
                        <button type="button" class="close" data-dismiss="alert">&times;</button>
                        <spring:message code="sendFeedbackSuccess"/></div>
                </c:if>
                <c:if test="${param.sent == false}">
                    <div class="alert alert-error alert-block">
                        <button type="button" class="close" data-dismiss="alert">&times;</button>
                        <spring:message code="sendFeedbackError"/></div>
                </c:if>
            </div>
            </c:if>

            <div class="row-fluid">
                <address>
                    <strong>${address}</strong><br>
                    <abbr title="<spring:message code="phoneNumber"/>"><spring:message
                            code="phoneNumber"/>:</abbr>${phones}
                </address>
                <br>
                <address>
                    <c:forEach var="contact" items="${contacts}" varStatus="status">
                        <c:if test="${contact.person != null}"><strong>${contact.person}</strong><br></c:if>
                        <c:if test="${contact.email != null}"><a
                                href="mailto:${contact.email}">${contact.email}</a><br></c:if>
                        <c:if test="${contact.phone != null}"><strong>${contact.phone}</strong><br></c:if>
                        <c:if test="${status.last == false}"><br></c:if>
                    </c:forEach>
                </address>
            </div>

            <div class="row-fluid">
                <div class="span5">
                    <pre><spring:message code="feedbackForm"/>:</pre>
                    <form:form action="/contacts/feedback" method="POST">
                        <textarea name="feedback" rows="15" style="width:100%; resize:none"></textarea>
                        <button class="btn" type="submit"><spring:message code="send"/></button>
                    </form:form>
                </div>
                <div class="span5">
                    <iframe width="425" height="350" frameborder="0" scrolling="no" marginheight="0"
                            marginwidth="0"
                            src="${mapUrl}"></iframe>
                    <br/>
                    <small><a
                            href="${showFullMapUrl}"
                            style="color:#0000FF;text-align:left"><spring:message code="showFullMap"/></a></small>

                </div>
            </div>
<%@ include file="/WEB-INF/pages/footer.jsp" %>