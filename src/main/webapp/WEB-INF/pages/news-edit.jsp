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
<h5>Редактирование новости</h5>
<div class="text-right"><a href="Javascript:history.back()"><spring:message code="back"/></a></div>
<form:form action="/news/save" method="POST" modelAttribute="news" id="editForm">
    <label>Название:</label>
    <input type="text" name="name" class="form-field" style="width: 100%" value="${news.name}">
    <label>Краткое описание:</label>
    <textarea style="width: 100%; resize: none" maxlength="500" name="description">${news.description}</textarea>
    <input type="hidden" name="content" id="content"/>
    <input type="hidden" name="id" value="${news.id}"/>
    <%@ include file="/WEB-INF/pages/wysiwyg.jsp" %>
    <div id="editor" contenteditable="true" style="overflow:scroll; height: 300px">${news.content}</div>
</form:form>
<div class="text-left">
    <button class="btn btn-primary" type="button" onclick="onSubmit()">Сохранить</button>
</div>
<%@ include file="/WEB-INF/pages/footer.jsp" %>
<script>
    function onSubmit() {
        var content = $("#editor").html();
        $("#content").val(content);
        $("#editForm").submit();
    }
</script>