<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="/WEB-INF/pages/tabs/admin.jsp" %>
<script type="text/javascript">
    <%@ include file="/resources/javascript/wysiwyg/bootstrap-wysiwyg.js" %>
</script>
<script type="text/javascript">
    <%@ include file="/resources/javascript/news.js" %>
</script>
<div class="row-fluid" style="height: 500px; overflow: hidden">
    <c:set var="page" value="${newsForm.page}"/>
    <c:choose>
        <c:when test="${page > 0}">
            <div class="pagination">
                <ul>
                    <li><a href="${page-1}">Prev</a></li>
                    <c:forEach var="i" begin="1" end="${newsForm.total}}">
                        <li><a href="/admin/news_settings/${i}">${i}</a></li>
                    </c:forEach>
                    <li><a href="/admin/news_settings/${page+1}">Next</a></li>
                </ul>
            </div>
        </c:when>
    </c:choose>
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