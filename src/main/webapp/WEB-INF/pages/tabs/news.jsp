<%@ page import="com.financial.pyramid.web.form.PageForm" %>
<%@ page import="com.financial.pyramid.domain.News" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="/WEB-INF/pages/header.jsp" %>

<h3>Новости</h3>
<div class="row-fluid" style="word-wrap: normal">
    <c:forEach items="${newsForm.rows}" var="news" varStatus="status">
        <h1><small>${news.name}</small></h1>
        <h5><fmt:formatDate value="${news.date}" type="both" timeStyle="short" dateStyle="short"/></h5>
        <div>${news.description}...<br><a href="/news/get/${news.id}">Читать далее >></a></div>
    </c:forEach>
    <%
        PageForm<News> pageForm = (PageForm<News>) request.getAttribute("newsForm");
        if (pageForm.getTotal() > 10){
    %>
    <div class="pagination pagination-centered">
        <ul>
            <li><a href="/news/<%=(pageForm.getPage()-1 > 0 ? pageForm.getPage()-1 : 1)%>">«</a></li>
            <%
                for (int i=1;i<=pageForm.getTotal()/10;i++){
                    String active = "";
                    pageForm.page = pageForm.page == 0 ? 1 : pageForm.page;
                    if (pageForm.page == i){
                        active = "active";
                    }
            %><li class="<%=active%>"><a href="/news/<%=i%>"><%=i%></a></li><%
            }
        %>
            <li><a href="/news/<%=pageForm.getPage()+1%>">»</a></li>
        </ul>
    </div><%
    }
%>
</div>

<%@ include file="/WEB-INF/pages/footer.jsp" %>
