<%@ page import="com.financial.pyramid.domain.News" %>
<%@ page import="com.financial.pyramid.web.form.PageForm" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="/WEB-INF/pages/header.jsp" %>

<h3>Новости</h3>

<div class="row-fluid" style="word-wrap: normal">
    <c:forEach items="${newsForm.rows}" var="news" varStatus="status">
        <h1>
            <small>${news.name}</small>
        </h1>
        <h5><fmt:formatDate value="${news.date}" type="both" timeStyle="short" dateStyle="short"/></h5>

        <div>${news.description}...<br><a href="/news/get/${news.id}">Читать далее >></a></div>
    </c:forEach>
    <%
        PageForm<News> pageForm = (PageForm<News>) request.getAttribute("newsForm");
        Integer pages = (int) Math.ceil(Double.valueOf(pageForm.getTotal() / 10.0));
        if (pages > 1) {
    %>
    <div class="pagination pagination-centered">
        <ul>
            <li>
                <a href="/pyramid/admin/news_settings?page=<%=(pageForm.getPage()-1 > 0 ? pageForm.getPage()-1 : 1)%>">«</a>
            </li>
            <%
                for (int i = 1; i <= pages; i++) {
                    String active = "";
                    pageForm.page = pageForm.page == 0 ? 1 : pageForm.page;
                    if (pageForm.page == i) {
                        active = "active";
                    }
            %>
            <li class="<%=active%>"><a href="/pyramid/admin/news_settings?page=<%=i%>"><%=i%>
            </a></li>
            <%
                }
            %>
            <li><a href="/pyramid/admin/news_settings?page=<%=pageForm.getPage()+1%>">»</a></li>
        </ul>
    </div>
    <%
        }
    %>
</div>

<%@ include file="/WEB-INF/pages/footer.jsp" %>
