<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.financial.pyramid.domain.Video" %>
<%@ page import="java.util.List" %>
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
            <li class="active"><a href="<c:url value="/pyramid/training"/>"><spring:message code="training"/></a></li>
            <li><a href="<c:url value="/pyramid/about"/>"><spring:message code="aboutProject"/></a></li>
            <li><a href="<c:url value="/pyramid/contacts"/>"><spring:message code="contacts"/></a></li>
        </ul>
    </div>
</div>
<div class="row-fluid">
    <div class="span10">
        <div id="tab-content" class="tab-content">
<%--Content--%>


<script type="text/javascript">
    <%@ include file="/resources/javascript/training.js" %>
</script>

<h3><spring:message code="label.study"/></h3>

<div class="row-fluid">
    <div class="span10">
        <pre><spring:message code="label.study.description"/></pre>
    </div>
</div>
<%
    String defaultVideo = (String) request.getAttribute("defaultVideo");
    List<Video> videos = (List<Video>) request.getAttribute("videos");
    if (videos == null || videos.size() == 0) {
%>
<%@include file="../missing-data.jsp" %>
<% } else {
%>
<div class="row-fluid">
    <div class="span10">
        <iframe id="player" type="text/html" width="748" height="455" src='<%=defaultVideo%>' frameborder="0"></iframe>
    </div>
</div>
<br>

<div class="row-fluid">
    <div class="span10">
        <ul class="thumbnails">
            <%
                int i = 0;
                for (Video video : videos) {
                    if (i < 3) {
            %>
            <li class="span4">
                <div class="thumbnail">
                    <a href='Javascript:load("<%=video.getExternalId()%>")' class="thumbnail">
                        <img src='<%=video.getThumbnailUrl()%>'
                             alt="" width="300" height="200">
                    </a>
                    <h3><%=video.getName()%></h3>
                    <p><%=video.getDescription().length() > 100 ? video.getDescription().substring(0, 100) + "..." : video.getDescription()%></p>
                </div>
            </li>
            <% } else {
                if (i == 3) {
            %></ul><div class="row-fluid"><h2><spring:message code="label.study.otherVideo"></spring:message>:</h2><%
            }
        %>
            <div><a href='Javascript:load("<%=video.getExternalId()%>")'><%=video.getName()%>
            </a></div>
            <%
                    }
                    i++;
                }%>
        </div>
    </div>
</div>
<%}%>
<%@ include file="/WEB-INF/pages/footer.jsp" %>
