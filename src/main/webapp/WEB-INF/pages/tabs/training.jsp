<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.financial.pyramid.domain.Video" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="/WEB-INF/pages/header.jsp" %>

<script type="text/javascript">
    <%@ include file="/resources/javascript/training.js" %>
</script>

<h1><spring:message code="label.study"/></h1>

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
<%@ include file="/WEB-INF/pages/footer.jsp" %>
