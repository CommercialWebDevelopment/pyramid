<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="/WEB-INF/pages/tabs/admin.jsp" %>
<div class="row-fluid" style="text-align: right">
    <a href="#uploadVideoForm"><button class="btn">Загрузить</button></a>
    <a href="#addVideoForm"><button class="btn">Добавить</button></a>
</div>
<h1>
    <small>Загруженные видео:</small>
</h1>

<c:forEach items="${videosForm.videos}" var="video" varStatus="status">
    <div class="row-fluid" id="#${video.id}">
        <form:form method="POST" action="/video/save">
            <div class="span12">
                <legend>Видео №${status.count}</legend>
            </div>
            <div class="span8">
                <input type="hidden" name="id" value="${video.id}">
                <input type="hidden" name="externalId" value="${video.externalId}">
                <input type="hidden" name="thumbnailUrl" value="${video.thumbnailUrl}">
                <label>Название:</label>
                <input type="text" name="name" value="${video.name}" style="width: 100%" class="form-field">
                <label>Описание:</label>
                <textarea style="width:100%; height: 130px; resize:none" maxlength="500"
                          name="description">${video.description}</textarea>
            </div>
            <div class="span3">
                <div>
                    <img src="${video.thumbnailUrl}" width="210px" height="150px"
                         title="${video.externalId}"/>
                </div>
                <div style="text-align: center"><br>
                    <button type="submit" class="btn btn-primary">Сохранить</button>
                    &nbsp;<a href="/video/remove/${video.id}">
                        <button class="btn">Удалить</button>
                    </a></div>
            </div>
        </form:form>
    </div>
</c:forEach>
<h1>
    <small>Загрузить новое видео на YouTube:</small>
</h1>
<div class="row-fluid" id="uploadVideoForm">
    загрузить
</div>
<h1>
    <small>Добавить новое видео с YouTube:</small>
</h1>
<div class="row-fluid" id="addVideoForm"><br>
    <form:form action="/video/save" method="POST" id="addVideo">
        <div class="span8">
            <input type="hidden" id="thumbnailUrl" name="thumbnailUrl">
            <label>Название:</label>
            <input type="text" name="name" class="form-field" style="width: 100%">
            <label>Описание:</label>
            <textarea style="width:100%; height: 130px; resize:none" maxlength="500" name="description"></textarea>
            <label>Идентификатор YouTube:</label>
            <input type="text" name="externalId" class="form-field" style="width: 100%"/>
        </div>
        <div class="span8"><button type="submit" class="btn btn-primary">Добавить</button></div>
    </form:form>
</div>
<%@ include file="/WEB-INF/pages/footer.jsp" %>