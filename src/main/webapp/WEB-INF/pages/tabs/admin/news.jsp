<%@ page import="com.financial.pyramid.domain.News" %>
<%@ page import="com.financial.pyramid.web.form.PageForm" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="/WEB-INF/pages/tabs/admin.jsp" %>
<script type="text/javascript">
    <%@ include file="/resources/javascript/wysiwyg/bootstrap-wysiwyg.js" %>
</script>
<script type="text/javascript">
    <%@ include file="/resources/javascript/news.js" %>
</script>
<div class="row-fluid">
<c:forEach items="${newsForm.rows}" var="news" varStatus="status">
     <h1><small>${news.name}</small></h1>
     <h5><fmt:formatDate value="${news.date}" type="both" timeStyle="short" dateStyle="short"/></h5>
    <div>${news.content}</div>
</c:forEach>
    <%
        PageForm<News> pageForm = (PageForm<News>) request.getAttribute("newsForm");
        if (pageForm.getTotal() > 10){
            %>
            <div class="pagination pagination-centered">
            <ul>
            <li><a href="/admin/news_settings/<%=pageForm.getPage()-1%>">«</a></li>
                <%
                    for (int i=1;i<=pageForm.getTotal()/10;i++){
                        String active = "";
                        if (pageForm.page == i){
                            active = "active";
                        }
                        %><li class="<%=active%>"><a href="/admin/news_settings/<%=i%>"><%=i%></a></li><%
                    }
                %>
            <li><a href="/admin/news_settings/<%=pageForm.getPage()+1%>">»</a></li>
            </ul>
            </div><%
        }
    %>
    </div>
    <div class="row-fluid" style="height: 500px; overflow: hidden">
    <form:form action="/news/save" method="POST" modelAttribute="news" id="newsForm">
        <h2>
            <small>Добавить новость</small>
        </h2>
        <label>Название</label>
        <input type="text" name="name" class="form-field" style="width: 100%">
        <input type="hidden" name="content" id="content"/>
        <%@ include file="/WEB-INF/pages/wysiwyg.jsp" %>
        <div id="editor" contenteditable="true" style="overflow:scroll; height: 300px"></div>
        <br>
        <button class="btn btn-primary" type="button" onclick="onSubmit()">Сохранить</button>
    </form:form>
</div>
<%@ include file="/WEB-INF/pages/footer.jsp" %>
<script>
    function onSubmit() {
        var content = $("#editor").html();
        $("#content").val(content);
        $("#newsForm").submit();
    }
</script>