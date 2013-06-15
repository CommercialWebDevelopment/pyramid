<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="/WEB-INF/pages/header.jsp" %>

<h3>Администрирование</h3>

<div class="tabbable">
    <ul class="nav nav-tabs">
        <li><a href="#tab1" data-toggle="tab">Section 1</a></li>
        <li><a href="#tab2" data-toggle="tab">Section 2</a></li>
    </ul>
    <div class="tab-content">
        <div class="tab-pane" id="tab1">
            <p>I'm in Section 1.</p>
        </div>
        <div class="tab-pane" id="tab2">
            <p>Howdy, I'm in Section 2.</p>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/pages/footer.jsp" %>