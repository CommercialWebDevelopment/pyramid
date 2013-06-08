<%@ page import="java.util.List" %>
<%@ page import="com.financial.pyramid.domain.Video" %>
<%--
  User: Danil
  Date: 03.06.13
  Time: 20:17
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html>
<head>
    <title>Название : Обучение</title>
</head>
<body>

<h1><spring:message code="label.study"></spring:message></h1>
<div class="row-fluid">
    <div class="span10">
        <%=request.getAttribute("videos")%>
        <c:forEach var="element" items="${videos}">
        ${element.name}<br/>
        </c:forEach>
    </div>
</div>

<div class="row-fluid">
    <div class="span10">
        <pre>В данном разделе представлено обучающее видео для ознакомления с принципами работы проекта.</pre>
    </div>
</div>
<div class="row-fluid">
    <div class="span10">
        <iframe id="player" type="text/html" width="640" height="390"
                src="http://www.youtube.com/embed/79uY4KixmYo?enablejsapi=1&origin=http://example.com"
                frameborder="0"></iframe>
    </div>
</div>
<div class="row-fluid">
    <div class="span10">
        <ul class="thumbnails">
            <li class="span4">
                <div class="thumbnail">
                    <a href="#" class="thumbnail">
                        <img src="http://img.youtube.com/vi/79uY4KixmYo/0.jpg" alt="" width="300" height="200">
                    </a>

                    <h3>Thumbnail label</h3>

                    <p>Thumbnail caption...</p>
                </div>
            </li>
            <li class="span4">
                <div class="thumbnail">
                    <a href="#" class="thumbnail">
                        <img src="http://img.youtube.com/vi/79uY4KixmYo/0.jpg" alt="" width="300" height="200">
                    </a>

                    <h3>Thumbnail label</h3>

                    <p>Thumbnail caption...</p>
                </div>
            </li>
            <li class="span4">
                <div class="thumbnail">
                    <a href="#" class="thumbnail">
                        <img src="http://img.youtube.com/vi/79uY4KixmYo/0.jpg" alt="" width="300" height="200">
                    </a>

                    <h3>Thumbnail label</h3>

                    <p>Thumbnail caption...</p>
                </div>
            </li>
        </ul>
    </div>
</div>
</body>
</html>