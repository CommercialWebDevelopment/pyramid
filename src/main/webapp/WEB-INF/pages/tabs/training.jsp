<%@ page import="com.financial.pyramid.domain.Video" %>
<%@ page import="java.util.List" %>
<%--
  User: Danil
  Date: 03.06.13
  Time: 20:17
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page session="false" %>
<html>
<head>
    <title></title>
    <script src="${pageContext.request.contextPath}/resources/javascript/training.js"/>
</head>
<body>

<h1><spring:message code="label.study"/></h1>

<div class="row-fluid">
    <div class="span10">
        <pre><spring:message code="label.study.description"/></pre>
    </div>
</div>
<%
    List<Video> videos = (List<Video>) request.getAttribute("videos");
    if (videos == null || videos.size() == 0) {
%>
<%@include file="../missing-data.jsp" %>
<% } else {
%>
<div class="row-fluid">
    <div class="span10">
        <iframe id="player" type="text/html" width="748" height="455"
                src="http://www.youtube.com/embed/<%=videos.get(0).getExternalId()%>?enablejsapi=1&origin="
                frameborder="0"></iframe>
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
                        <img src="http://img.youtube.com/vi/<%=video.getExternalId()%>/0.jpg" alt="" width="300"
                             height="200">
                    </a>

                    <h3><%=video.getName()%>
                    </h3>

                    <p><%=video.getDescription().length() > 100 ? video.getDescription().substring(0, 100) + "..." : video.getDescription()%>
                    </p>
                </div>
            </li>
            <% } else {
                if (i == 3) {
            %><h2><spring:message code="label.study.otherVideo"></spring:message>:</h2><%
            }
        %>
            <div><a href='Javascript:load("<%=video.getExternalId()%>")'><%=video.getName()%>
            </a></div>
            <%
                    }
                    i++;
                }%>
        </ul>
    </div>
</div>
<%}%>
</body>
</html>