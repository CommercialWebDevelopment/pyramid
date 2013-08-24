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
    <div class="span10">
        <div class="title"><h3><spring:message code="editNews" /></h3></div>
        <div class="back"><a href="Javascript:history.back()"><spring:message code="back"/></a></div>
    </div>
</div>
<form:form action="/news/save" method="POST" modelAttribute="news" id="editForm">
    <label><spring:message code="newsName"/>:</label>
    <input type="text" name="name" class="form-field" style="width: 100%" value="${news.name}">
    <label><spring:message code="newsDescription"/>:</label>
    <textarea style="width: 100%; resize: none" maxlength="500" name="description">${news.description}</textarea>
    <input type="hidden" name="content" id="content"/>
    <input type="hidden" name="id" value="${news.id}"/>
    <%@ include file="/WEB-INF/pages/wysiwyg.jsp" %>
    <div id="editor" contenteditable="true" style="overflow:scroll; height: 300px">${news.content}</div>
</form:form>
<div class="text-left">
    <button class="btn btn-primary" type="button" onclick="onSubmit()"><spring:message code="save"/></button>
</div>
<%@ include file="/WEB-INF/pages/footer.jsp" %>
<script>
    function onSubmit() {
        var content = $("#editor").html();
        $("#content").val(content);
        $("#editForm").submit();
    }
</script>