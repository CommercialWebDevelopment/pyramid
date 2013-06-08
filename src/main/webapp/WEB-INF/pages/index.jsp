<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel=stylesheet
          href="${pageContext.request.contextPath}/resources/javascript/bootstrap/css/bootstrap.css"
          type="text/css">
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/javascript/jquery-1.10.0/jquery-1.10.1.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/javascript/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/javascript/header.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/javascript/index.js"></script>

</head>
<body>
<div class="row-fluid" style="height: 20px"></div>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span10 offset2">
            <%--Header--%>
            <div class="row-fluid">
                <div class="span9">
                    Название
                </div>
                <div class="span1">
                    <a href="#" class="thumbnail" id="user-details" data-toggle="popover" data-placement="left">
                        <img src="${pageContext.request.contextPath}/resources/images/avatar.jpg" alt="" style="height: 60px"/>
                    </a>
                </div>
            </div>
            <%--Tabs--%>
            <div class="row-fluid">
                <div class="span8 offset2">
                    <ul class="nav nav-pills" id="tabs">
                        <li class="active"><a href="#home" url="/home/">Главная</a></li>
                        <li><a href="#office" url="/office/">Личный кабинет</a></li>
                        <li><a href="#news" url="/news/">Новости</a></li>
                        <li><a href="#training" url="/training/">Обучение</a></li>
                        <li><a href="#about" url="/about/">О проекте</a></li>
                        <li><a href="#contacts" url="/contacts/">Контакты</a></li>
                    </ul>
                </div>
            </div>
                <div class="row-fluid">
                    <div class="span10">
                        <div id="tab-content" class="tab-content">
                            <div class="tab-pane active" id="home"></div>
                            <div class="tab-pane" id="news"></div>
                            <div class="tab-pane" id="office"></div>
                            <div class="tab-pane" id="training"></div>
                            <div class="tab-pane" id="about"></div>
                            <div class="tab-pane" id="contacts"></div>
                        </div>
                    </div>
                </div>
        </div>
    </div>
</div>
</body>
</html>