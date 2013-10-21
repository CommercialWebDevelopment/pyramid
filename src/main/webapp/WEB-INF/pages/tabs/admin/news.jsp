<%@ page import="com.financial.pyramid.domain.News" %>
<%@ page import="com.financial.pyramid.web.form.PageForm" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="/WEB-INF/pages/tabs/admin.jsp" %>
<div class="row-fluid">
    <div class="span12" id="admin-menu">
        <ul class="nav nav-tabs">
            <li><a href="<c:url value="/app/admin/user_settings"/>" data-toogle="tab"><spring:message code="users"/></a></li>
            <li><a href="<c:url value="/app/admin/content_settings"/>" data-toogle="tab"><spring:message code="tabs.text"/></a></li>
            <li class="active"><a href="<c:url value="/app/admin/news_settings"/>" data-toogle="tab"><spring:message code="news"/></a></li>
            <li><a href="<c:url value="/app/admin/video_settings"/>" data-toogle="tab"><spring:message code="tabs.video"/></a></li>
            <li><a href="<c:url value="/app/admin/contact_settings"/>" data-toogle="tab"><spring:message code="contacts"/></a></li>
            <li><a href="<c:url value="/app/admin/application_settings"/>" data-toogle="tab"><spring:message code="tabs.parameters"/></a></li>
            <li><a href="<c:url value="/app/admin/payments"/>" data-toogle="tab"><spring:message code="tabs.payments"/></a></li>
        </ul>
    </div>
</div>

<script type="text/javascript">
    <%@ include file="/resources/javascript/wysiwyg/bootstrap-wysiwyg.js" %>
</script>
<script type="text/javascript">
    <%@ include file="/resources/javascript/admin/news.js" %>
</script>
<div class="row-fluid" style="word-wrap: normal">
    <spring:message var="removeNewsConfirm" code="removeNews"/>
    <c:forEach items="${newsForm.rows}" var="news" varStatus="status">
        <h1>
            <small>${news.name}</small>
        </h1>
        <h5><fmt:formatDate value="${news.date}" type="both" timeStyle="short" dateStyle="short"/></h5>

        <div>${news.description}...<br><a href="/news/get/${news.id}"><spring:message code="readNews"/> >></a></div>
        <div class="text-right">
            <button class="btn btn-primary" onclick="window.location.href = '/news/edit/${news.id}'"><spring:message code="edit"/></button>
            <button class="btn" onclick="Alert.confirm('${removeNewsConfirm}', function(){
                    window.location.href = '/news/remove/${news.id}'
                    })"><spring:message code="remove" />
            </button>
        </div>
    </c:forEach>
    <%
        PageForm<News> pageForm = (PageForm<News>) request.getAttribute("newsForm");
        Integer pages = (int) Math.ceil(Double.valueOf(pageForm.getTotal() / 10.0));
        if (pages > 1) {
    %>
    <div class="pagination pagination-centered">
        <ul>
            <li>
                <a href="/app/admin/news_settings?page=<%=(pageForm.getPage()-1 > 0 ? pageForm.getPage()-1 : 1)%>">«</a>
            </li>
            <%
                for (int i = 1; i <= pages; i++) {
                    String active = "";
                    pageForm.page = pageForm.page == 0 ? 1 : pageForm.page;
                    if (pageForm.page == i) {
                        active = "active";
                    }
            %>
            <li class="<%=active%>"><a href="/app/admin/news_settings?page=<%=i%>"><%=i%>
            </a></li>
            <%
                }
            %>
            <li><a href="/app/admin/news_settings?page=<%=pageForm.getPage()+1%>">»</a></li>
        </ul>
    </div>
    <%
        }
    %>
</div>
<div id="add-news-form" style="width: 100%; left: 0 !important; margin-left: 0 !important;" class="modal hide fade" tabindex="-1" role="dialog"
     aria-labelledby="add-news-form-label" aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h5 id="add-news-form-label"><spring:message code="addNewsTitle"/></h5>
    </div>
    <div class="modal-body">
        <form:form action="/news/save" method="POST" modelAttribute="news" id="addForm">
            <label><spring:message code="newsName"/>:</label>
            <input type="text" name="name" class="form-field" style="width: 100%">
            <label><spring:message code="newsDescription"/>:</label>
            <textarea style="width: 100%; resize: none" maxlength="500" name="description"></textarea>
            <input type="hidden" name="content" id="content"/>
            <%@ include file="/WEB-INF/pages/wysiwyg.jsp" %>
            <div id="editor" contenteditable="true" style="overflow:scroll; height: 300px; max-height: 300px"></div>
        </form:form>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary" type="button" onclick="NewsAdmin.beforeAdd()"><spring:message code="save"/></button>
    </div>
</div>
<div class="text-center">
    <button class="btn btn-primary" onclick="NewsAdmin.addForm()"><spring:message code="addNewsTitle"/></button>
</div>
<%@ include file="/WEB-INF/pages/footer.jsp" %>