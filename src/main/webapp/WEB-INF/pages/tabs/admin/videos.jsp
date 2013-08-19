<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="/WEB-INF/pages/tabs/admin.jsp" %>

<div class="row-fluid">
    <div class="span12" id="admin-menu">
        <ul class="nav nav-tabs">
            <li><a href="<c:url value="/pyramid/admin/user_settings"/>" data-toogle="tab"><spring:message code="users"/></a></li>
            <li><a href="<c:url value="/pyramid/admin/content_settings"/>" data-toogle="tab"><spring:message code="tabs.text"/></a></li>
            <li><a href="<c:url value="/pyramid/admin/news_settings"/>" data-toogle="tab"><spring:message code="news"/></a></li>
            <li class="active"><a href="<c:url value="/pyramid/admin/video_settings"/>" data-toogle="tab"><spring:message code="tabs.video"/></a></li>
            <li><a href="<c:url value="/pyramid/admin/contact_settings"/>" data-toogle="tab"><spring:message code="contacts"/></a></li>
            <li><a href="<c:url value="/pyramid/admin/application_settings"/>" data-toogle="tab"><spring:message code="tabs.parameters"/></a></li>
            <li><a href="<c:url value="/pyramid/admin/payments"/>" data-toogle="tab"><spring:message code="tabs.payments"/></a></li>
        </ul>
    </div>
</div>


<%--
<div class="row-fluid" style="text-align: right">
    <a href="#uploadVideoForm">
        <button class="btn">Загрузить</button>
    </a>
    <a href="#addVideoForm">
        <button class="btn">Добавить</button>
    </a>
</div>
--%>
<h1>
    <small><spring:message code="uploadedVideos"/>:</small>
</h1>
<c:forEach items="${videosForm.videos}" var="video" varStatus="status">
    <div class="row-fluid" id="#${video.id}">
        <form:form method="POST" action="/video/save">
            <div class="span12">
                <legend><spring:message code="videoNumber"/> #${status.count}</legend>
            </div>
            <div class="span8">
                <input type="hidden" name="id" value="${video.id}">
                <input type="hidden" name="externalId" value="${video.externalId}">
                <input type="hidden" name="thumbnailUrl" value="${video.thumbnailUrl}">
                <label><spring:message code="videoName"/>:</label>
                <input type="text" name="name" value="${video.name}" style="width: 100%" class="form-field">
                <label><spring:message code="videoDescription"/>:</label>
                <textarea style="width:100%; height: 130px; resize:none" maxlength="500"
                          name="description">${video.description}</textarea>
            </div>
            <div class="span3">
                <div>
                    <img src="${video.thumbnailUrl}" width="210px" height="150px"
                         title="${video.externalId}"/>
                </div>
                <div style="text-align: center"><br>
                    <spring:message code="label.removeVideo" var="removeVideo"/>
                    <button type="submit" class="btn btn-primary"><spring:message code="save"/></button>
                    &nbsp;
                    <button type="button" class="btn"
                            onclick="Alert.confirm('${removeVideo}', function(){
                                    window.location.href = '/video/remove/${video.id}';
                                    })"><spring:message code="remove"/>
                    </button>
                </div>
            </div>
        </form:form>
    </div>
</c:forEach>
<%--
<h2>
    <small>Загрузить новое видео на YouTube:</small>
</h2>
<div class="row-fluid" id="uploadVideoForm">
    <div class="span8">
        <form:form action="/video/upload" enctype="multipart/form-data" method="POST" modelAttribute="videoUploadForm">
            <label>Название:<span class="asterisk_red">*</span></label>
            <input type="text" name="video.name" class="form-field" style="width: 100%">
            <label>Описание:</label>
            <textarea style="width:100%; height: 130px; resize:none" maxlength="500" name="video.description"></textarea>
            <label>Выберите файл:<span class="asterisk_red">*</span></label>
            <input id="file" type="file" name="file" class="form-field"/>
            <button class="btn btn-primary" type="submit">Загрузить</button>
        </form:form>
    </div>
</div>
--%>
<h2>
    <small><spring:message code="videoUploadFromYouTube"/>:</small>
</h2>

<form:form action="/video/save" method="POST" id="addVideo" modelAttribute="video">
    <div class="row-fluid" id="addVideoForm">
        <div class="span8">
            <input type="hidden" id="thumbnailUrl" name="thumbnailUrl">
            <label><spring:message code="videoName"/>:<span class="asterisk_red">*</span></label>
            <input type="text" name="name" class="form-field" style="width: 100%">
            <label><spring:message code="videoDescription"/>:</label>
            <textarea style="width:100%; height: 130px; resize:none" maxlength="500" name="description"></textarea>
            <label><spring:message code="videoExternalID"/>:<span class="asterisk_red">*</span></label>
            <input type="text" name="externalId" class="form-field" style="width: 100%"/>
        </div>
    </div>
    <div class="row-fluid">
        <button type="submit" class="btn btn-primary"><spring:message code="save"/></button>
    </div>
</form:form>
<%@ include file="/WEB-INF/pages/footer.jsp" %>