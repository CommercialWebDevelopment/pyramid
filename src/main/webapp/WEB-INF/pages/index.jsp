<%--&lt;%&ndash;--%>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>--%>
<%--<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>--%>

<%--&lt;%&ndash;<html>&ndash;%&gt;--%>
<%--&lt;%&ndash;<head>&ndash;%&gt;--%>
    <%--&lt;%&ndash;<meta http-equiv="Content-Type" content="text/html; charset=utf-8">&ndash;%&gt;--%>
    <%--&lt;%&ndash;<meta name="viewport" content="width=device-width, initial-scale=1.0">&ndash;%&gt;--%>
    <%--&lt;%&ndash;<link rel=stylesheet&ndash;%&gt;--%>
          <%--&lt;%&ndash;href="${pageContext.request.contextPath}/resources/javascript/bootstrap/css/bootstrap.css"&ndash;%&gt;--%>
          <%--&lt;%&ndash;type="text/css">&ndash;%&gt;--%>
    <%--&lt;%&ndash;<link rel=stylesheet href="${pageContext.request.contextPath}/resources/css/loader.css" type="text/css">&ndash;%&gt;--%>
    <%--&lt;%&ndash;<link rel=stylesheet href="${pageContext.request.contextPath}/resources/css/private-office.css" type="text/css">&ndash;%&gt;--%>
    <%--&lt;%&ndash;<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css" type="text/css">&ndash;%&gt;--%>
    <%--&lt;%&ndash;<script type="text/javascript"&ndash;%&gt;--%>
            <%--&lt;%&ndash;src="${pageContext.request.contextPath}/resources/javascript/jquery-1.10.0/jquery-1.10.1.min.js"></script>&ndash;%&gt;--%>
    <%--&lt;%&ndash;<script type="text/javascript"&ndash;%&gt;--%>
            <%--&lt;%&ndash;src="${pageContext.request.contextPath}/resources/javascript/bootstrap/js/bootstrap.js"></script>&ndash;%&gt;--%>
    <%--&lt;%&ndash;<script type="text/javascript"&ndash;%&gt;--%>
            <%--&lt;%&ndash;src="${pageContext.request.contextPath}/resources/javascript/header.js"></script>&ndash;%&gt;--%>
    <%--&lt;%&ndash;<script type="text/javascript"&ndash;%&gt;--%>
            <%--&lt;%&ndash;src="${pageContext.request.contextPath}/resources/javascript/index.js"></script>&ndash;%&gt;--%>
    <%--&lt;%&ndash;<script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/office.js"></script>&ndash;%&gt;--%>
<%--&lt;%&ndash;</head>&ndash;%&gt;--%>
<%--&lt;%&ndash;<body>&ndash;%&gt;--%>
<%--&lt;%&ndash;<div class="row-fluid" style="height: 20px"></div>&ndash;%&gt;--%>
<%--&lt;%&ndash;<div class="container-fluid">&ndash;%&gt;--%>
    <%--&lt;%&ndash;<div class="row-fluid">&ndash;%&gt;--%>
        <%--&lt;%&ndash;<div class="span10 offset2">&ndash;%&gt;--%>
            <%--&lt;%&ndash;&lt;%&ndash;Header&ndash;%&gt;&ndash;%&gt;--%>
            <%--&lt;%&ndash;<div class="row-fluid">&ndash;%&gt;--%>
                <%--&lt;%&ndash;<div class="span9">&ndash;%&gt;--%>
                    <%--&lt;%&ndash;Название&ndash;%&gt;--%>
                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<div class="span1">&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<a href="#" class="thumbnail" id="user-details" data-toggle="popover" data-placement="left">&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<img src="${pageContext.request.contextPath}/resources/images/vcard.png" alt=""&ndash;%&gt;--%>
                             <%--&lt;%&ndash;style="height: 48px" />&ndash;%&gt;--%>
                    <%--&lt;%&ndash;</a>&ndash;%&gt;--%>
                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
            <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
            <%--&lt;%&ndash;&lt;%&ndash;Tabs&ndash;%&gt;&ndash;%&gt;--%>
            <%--&lt;%&ndash;<div class="row-fluid">&ndash;%&gt;--%>
                <%--&lt;%&ndash;<div class="span8 offset2">&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<ul class="nav nav-pills" id="tabs">&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<li><a href="#" url="/pyramid/home" data-toggle="pill">Главная</a></li>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<li><a href="#" url="/pyramid/office" data-toggle="pill">Личный кабинет</a></li>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<li><a href="#" url="/pyramid/news" data-toggle="pill">Новости</a></li>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<li><a href="#" url="/pyramid/training" data-toggle="pill">Обучение</a></li>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<li><a href="#" url="/pyramid/about" data-toggle="pill">О проекте</a></li>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<li><a href="#" url="/pyramid/contacts" data-toggle="pill">Контакты</a></li>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;</ul>&ndash;%&gt;--%>
                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
            <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<div class="row-fluid">&ndash;%&gt;--%>
                <%--&lt;%&ndash;<div class="span10">&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<div id="tab-content" class="tab-content"></div>&ndash;%&gt;--%>
                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
            <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
        <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
    <%--&lt;%&ndash;<div id="footer" class="row-fluid" style="height: 20px">&ndash;%&gt;--%>
        <%--&lt;%&ndash;<div class="span11">&ndash;%&gt;--%>
            <%--&lt;%&ndash;&copy 2013&ndash;%&gt;--%>
        <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
        <%--&lt;%&ndash;<div class="span1">&ndash;%&gt;--%>
            <%--&lt;%&ndash;<div class="contact vk" onclick="window.location.href='http://vk.com'"></div>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<div class="contact fb" onclick="window.location.href='http://facebook.com'"></div>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<div class="contact tw" onclick="window.location.href='http://twitter'"></div>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<div class="contact yt" onclick="window.location.href='http://youtube.com'"></div>&ndash;%&gt;--%>
        <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
<%--&lt;%&ndash;</div>&ndash;%&gt;--%>
<%--&lt;%&ndash;<div id="loader" class="loader">&ndash;%&gt;--%>
    <%--&lt;%&ndash;<div class="loader-icon"></div>&ndash;%&gt;--%>
    <%--&lt;%&ndash;<div class="loader-text"></div>&ndash;%&gt;--%>
<%--&lt;%&ndash;</div>&ndash;%&gt;--%>
<%--&lt;%&ndash;</body>&ndash;%&gt;--%>
<%--&lt;%&ndash;</html>&ndash;%&gt;<html>--%>
<%--<head>--%>
    <%--<meta http-equiv="Content-Type" content="text/html; charset=utf-8">--%>
    <%--<meta name="viewport" content="width=device-width, initial-scale=1.0">--%>
    <%--<link rel=stylesheet--%>
          <%--href="${pageContext.request.contextPath}/resources/javascript/bootstrap/css/bootstrap.css"--%>
          <%--type="text/css">--%>
    <%--<link rel=stylesheet href="${pageContext.request.contextPath}/resources/css/loader.css" type="text/css">--%>
    <%--<link rel=stylesheet href="${pageContext.request.contextPath}/resources/css/private-office.css" type="text/css">--%>
    <%--<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css" type="text/css">--%>
    <%--<script type="text/javascript"--%>
            <%--src="${pageContext.request.contextPath}/resources/javascript/jquery-1.10.0/jquery-1.10.1.min.js"></script>--%>
    <%--<script type="text/javascript"--%>
            <%--src="${pageContext.request.contextPath}/resources/javascript/bootstrap/js/bootstrap.min.js"></script>--%>
    <%--<script type="text/javascript"--%>
            <%--src="${pageContext.request.contextPath}/resources/javascript/header.js"></script>--%>
    <%--<script type="text/javascript"--%>
            <%--src="${pageContext.request.contextPath}/resources/javascript/index.js"></script>--%>
    <%--<script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/settings.js"></script>--%>

<%--</head>--%>
<%--<body>--%>
<%--<div class="row-fluid" style="height: 20px"></div>--%>
<%--<div class="container-fluid">--%>
    <%--<div class="row-fluid">--%>
        <%--<div class="span10 offset2">--%>
            <%--&lt;%&ndash;Header&ndash;%&gt;--%>
            <%--<div class="row-fluid">--%>
                <%--<div class="span9">--%>
                    <%--Название--%>
                <%--</div>--%>
                <%--<div class="span1">--%>
                    <%--<a href="#" class="thumbnail" id="user-details" data-toggle="popover" data-placement="left">--%>
                        <%--<img src="${pageContext.request.contextPath}/resources/images/vcard.png" alt=""--%>
                             <%--style="height: 48px"/>--%>
                    <%--</a>--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--&lt;%&ndash;Tabs&ndash;%&gt;--%>
            <%--<div class="row-fluid">--%>
                <%--<div class="span8 offset2">--%>
                    <%--<ul class="nav nav-pills" id="tabs">--%>
                        <%--<li><a href="#" url="/pyramid/home" data-toggle="pill">Главная</a></li>--%>
                        <%--<li><a href="#" url="/pyramid/office" data-toggle="pill">Личный кабинет</a></li>--%>
                        <%--<li><a href="#" url="/pyramid/news" data-toggle="pill">Новости</a></li>--%>
                        <%--<li><a href="#" url="/pyramid/training" data-toggle="pill">Обучение</a></li>--%>
                        <%--<li><a href="#" url="/pyramid/about" data-toggle="pill">О проекте</a></li>--%>
                        <%--<li><a href="#" url="/pyramid/contacts" data-toggle="pill">Контакты</a></li>--%>
                    <%--</ul>--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--<div class="row-fluid">--%>
                <%--<div class="span10">--%>
                    <%--<div id="tab-content" class="tab-content"></div>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</div>--%>
    <%--<div id="footer" class="row-fluid" style="height: 20px">--%>
        <%--<div class="span11">--%>
            <%--&copy 2013--%>
        <%--</div>--%>
        <%--<div class="span1">--%>
            <%--<div class="contact vk" onclick="window.location.href='http://vk.com'"></div>--%>
            <%--<div class="contact fb" onclick="window.location.href='http://facebook.com'"></div>--%>
            <%--<div class="contact tw" onclick="window.location.href='http://twitter'"></div>--%>
            <%--<div class="contact yt" onclick="window.location.href='http://youtube.com'"></div>--%>
        <%--</div>--%>
    <%--</div>--%>
<%--</div>--%>
<%--<div id="loader" class="loader">--%>
    <%--<div class="loader-icon"></div>--%>
    <%--<div class="loader-text"></div>--%>
<%--</div>--%>
<%--</body>--%>
<%--</html>&ndash;%&gt;--%>
