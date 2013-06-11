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
    <title>Название : Обучение</title>
</head>
<body>

<h1><spring:message code="label.study"></spring:message></h1>

<div class="row-fluid">
    <div class="span10">
        <pre>В данном разделе представлено обучающее видео для ознакомления с принципами работы проекта.</pre>
    </div>
</div>
<%
    List<Video> videos = (List<Video>) request.getAttribute("videos");
    if (videos == null || videos.size() == 0){
        %><%@include file="../missing-data.jsp" %><% } else {
%>
<div class="row-fluid">
    <div class="span10">
        <iframe id="player" type="text/html" width="640" height="390"
                src="http://www.youtube.com/embed/<%=videos.get(0).getExternalId()%>?enablejsapi=1&origin=%>"
                frameborder="0"></iframe>
    </div>
</div>
<br>
<div class="row-fluid">
    <div class="span10">
        <ul class="thumbnails">
            <%
                for (Video video : videos) {
            %>
            <li class="span4">
                <div class="thumbnail">
                    <a href="#" class="thumbnail">
                        <img src="http://img.youtube.com/vi/<%=video.getExternalId()%>/0.jpg" alt="" width="300" height="200">
                    </a>
                    <h3><%=video.getName()%></h3>
                    <p><%=video.getDescription().length() > 100 ? video.getDescription().substring(0, 100) + "..." : video.getDescription()%>
                    </p>
                </div>
            </li>
            <%}%>
        </ul>
    </div>
</div><%}%>
</body>
</html>