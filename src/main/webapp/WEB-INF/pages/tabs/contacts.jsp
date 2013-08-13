<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="/WEB-INF/pages/header.jsp" %>
<%--Tabs--%>
<div class="row-fluid">
    <div class="span8 offset1">
        <ul class="nav nav-pills" id="tabs">
            <li><a href="<c:url value="/pyramid/home"/>"><spring:message code="home"/></a></li>
            <security:authorize access="hasAnyRole('SUPER_ADMIN,ADMIN')">
                <li><a href="<c:url value="/pyramid/admin"/>"><spring:message code="settings"/></a></li>
            </security:authorize>
            <li><a href="<c:url value="/pyramid/office"/>"><spring:message code="privateOffice"/></a></li>
            <li><a href="<c:url value="/pyramid/news"/>"><spring:message code="news"/></a></li>
            <li><a href="<c:url value="/pyramid/training"/>"><spring:message code="training"/></a></li>
            <li><a href="<c:url value="/pyramid/about"/>"><spring:message code="aboutProject"/></a></li>
            <li class="active"><a href="<c:url value="/pyramid/contacts"/>"><spring:message code="contacts"/></a></li>
        </ul>
    </div>
</div>
<div class="row-fluid">
    <div class="span10">
        <div id="tab-content" class="tab-content">
            <%--Content--%>

<h3>Контакты</h3>

<div class="row-fluid">
    <address>
        <strong>г.Тольятти, Автозаводское шоссе 1</strong><br>
        <abbr title="Телефон">Телефон:</abbr> (123) 456-7890, (123) 456-7891, (123) 456-7892
    </address>
    <br>
    <address>
        <strong>Иван Петров</strong><br>
        <a href="mailto:ivan.pertov@example.com">ivan.pertov@example.com</a>
    </address>
</div>

<div class="row-fluid">
    <div class="span5">
        <pre>Нам важно ваше мнение! Напишите нам:</pre>
        <textarea rows="15" style="width:100%; resize:none"></textarea>
        <button class="btn" type="submit">Отправить</button>
    </div>
    <div class="span5">
        <iframe width="425" height="350" frameborder="0" scrolling="no" marginheight="0"
                marginwidth="0"
                src="https://maps.google.com/maps?f=q&amp;source=s_q&amp;hl=ru&amp;geocode=&amp;q=%D0%A2%D0%BE%D0%BB%D1%8C%D1%8F%D1%82%D1%82%D0%B8&amp;aq=&amp;sll=53.526399,49.400546&amp;sspn=0.057859,0.169086&amp;t=m&amp;ie=UTF8&amp;hq=&amp;hnear=%D0%A2%D0%BE%D0%BB%D1%8C%D1%8F%D1%82%D1%82%D0%B8,+%D0%B3%D0%BE%D1%80%D0%BE%D0%B4+%D0%A2%D0%BE%D0%BB%D1%8C%D1%8F%D1%82%D1%82%D0%B8,+%D0%A1%D0%B0%D0%BC%D0%B0%D1%80%D1%81%D0%BA%D0%B0%D1%8F+%D0%BE%D0%B1%D0%BB%D0%B0%D1%81%D1%82%D1%8C,+%D0%A0%D0%BE%D1%81%D1%81%D0%B8%D1%8F&amp;z=11&amp;ll=53.509513,49.417527&amp;output=embed"></iframe>
        <br/>
        <small><a
                href="https://maps.google.com/maps?f=q&amp;source=embed&amp;hl=ru&amp;geocode=&amp;q=%D0%A2%D0%BE%D0%BB%D1%8C%D1%8F%D1%82%D1%82%D0%B8&amp;aq=&amp;sll=53.526399,49.400546&amp;sspn=0.057859,0.169086&amp;t=m&amp;ie=UTF8&amp;hq=&amp;hnear=%D0%A2%D0%BE%D0%BB%D1%8C%D1%8F%D1%82%D1%82%D0%B8,+%D0%B3%D0%BE%D1%80%D0%BE%D0%B4+%D0%A2%D0%BE%D0%BB%D1%8C%D1%8F%D1%82%D1%82%D0%B8,+%D0%A1%D0%B0%D0%BC%D0%B0%D1%80%D1%81%D0%BA%D0%B0%D1%8F+%D0%BE%D0%B1%D0%BB%D0%B0%D1%81%D1%82%D1%8C,+%D0%A0%D0%BE%D1%81%D1%81%D0%B8%D1%8F&amp;z=11&amp;ll=53.509513,49.417527"
                style="color:#0000FF;text-align:left">Просмотреть увеличенную карту</a></small>

    </div>
</div>
<%@ include file="/WEB-INF/pages/footer.jsp" %>