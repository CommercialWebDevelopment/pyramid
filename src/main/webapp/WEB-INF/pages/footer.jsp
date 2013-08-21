</div>
</div>
</div>
</div>
</div>
</div>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="footer" class="row-fluid">
    <div class="row-fluid">
        <div id="footer-content" class="span12">
            <div class="span2">
                <span>&nbsp;&copy
                <script language="javascript">
                    var date = new Date().getFullYear();
                    if (date > 2013) {
                        document.write("2013 -" + date.toString());
                    } else {
                        document.write(date.toString());
                    }
                </script>
                </span>
                <div>
                    &nbsp;Название
                </div>
            </div>
            <div class="span9">
                <div>Разделы:</div>
                <span><a href="/pyramid/home"><spring:message code="home"/></a></span> |
                <security:authorize access="hasAnyRole('SUPER_ADMIN,ADMIN')">
                    <span><a href="/pyramid/admin/"><spring:message code="settings"/></a></span> |
                </security:authorize>
                <span><a href="/pyramid/office"><spring:message code="privateOffice"/></a></span> |
                <span><a href="/pyramid/news"><spring:message code="news"/></a></span> |
                <span><a href="/pyramid/training"><spring:message code="training"/></a></span> |
                <span><a href="/pyramid/about"><spring:message code="aboutProject"/></a></span> |
                <span><a href="/pyramid/contacts"><spring:message code="contacts"/></a></span>
            </div>
            <div class="span1">
                <div class="contact vk" onclick="window.location.href=Settings.getProperty('vkontakte')"></div>
                <div class="contact fb" onclick="window.location.href=Settings.getProperty('facebook')"></div>
                <div class="contact tw" onclick="window.location.href=Settings.getProperty('twitter')"></div>
                <div class="contact yt" onclick="window.location.href=Settings.getProperty('youtube')"></div>
            </div>
        </div>
    </div>
</div>
<%@include file="alerts.jsp" %>
</body>
</html>