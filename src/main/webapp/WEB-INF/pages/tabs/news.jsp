<%@ page import="com.financial.pyramid.domain.News" %>
<%@ page import="com.financial.pyramid.web.form.PageForm" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
            <li class="active"><a href="<c:url value="/pyramid/news"/>"><spring:message code="news"/></a></li>
            <li><a href="<c:url value="/pyramid/training"/>"><spring:message code="training"/></a></li>
            <li><a href="<c:url value="/pyramid/about"/>"><spring:message code="aboutProject"/></a></li>
            <li><a href="<c:url value="/pyramid/contacts"/>"><spring:message code="contacts"/></a></li>
        </ul>
    </div>
</div>
<div class="row-fluid">
    <div class="span10">
        <div id="tab-content" class="tab-content">
            <%--Content--%>


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
        Integer pages = 0;
        if (pageForm != null){
            pages = (int) Math.ceil(Double.valueOf(pageForm.getTotal() / 10.0));
        }
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
