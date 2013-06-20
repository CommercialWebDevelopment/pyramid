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
    <h2><small>Добавить новость</small></h2>
    <label>Название</label>
    <input type="text" name="name" class="form-field" style="width: 100%">
    <%@ include file="/WEB-INF/pages/wysiwyg.jsp" %>
    <div id="editor" contenteditable="true" style="overflow:scroll; height: 300px"></div>
    <br><button class="btn btn-primary" type="submit">Сохранить</button>
</div>
<%@ include file="/WEB-INF/pages/footer.jsp" %>