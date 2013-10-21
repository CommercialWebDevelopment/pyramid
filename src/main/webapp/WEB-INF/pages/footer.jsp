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
                <span class="copyright">&nbsp;&copy&nbsp;<script>Copyright.get()</script></span>

                <div class="copyright">
                    &nbsp;<spring:message code="projectName"/>
                </div>
            </div>
            <div class="span9">
                <div><spring:message code="sections"/>:</div>
                <span><a href="${pageContext.request.contextPath}/app/home"><spring:message code="home"/></a></span>
                |
                <security:authorize access="hasAnyRole('SUPER_ADMIN,ADMIN')">
                    <span><a href="${pageContext.request.contextPath}/app/admin/"><spring:message
                            code="settings"/></a></span> |
                </security:authorize>
                <span><a href="${pageContext.request.contextPath}/app/office"><spring:message
                        code="privateOffice"/></a></span> |
                <span><a href="${pageContext.request.contextPath}/app/news"><spring:message code="news"/></a></span>
                |
                <span><a href="${pageContext.request.contextPath}/app/training"><spring:message
                        code="training"/></a></span> |
                <span><a href="${pageContext.request.contextPath}/app/about"><spring:message
                        code="aboutProject"/></a></span> |
                <span><a href="${pageContext.request.contextPath}/app/contacts"><spring:message
                        code="contacts"/></a></span>
            </div>
            <div class="contacts">
                <a href="${pageContext.request.contextPath}/app/redirect?to=vkontakte" target="_blank">
                    <div class="contact vk"></div>
                </a>
                <a href="${pageContext.request.contextPath}/app/redirect?to=facebook" target="_blank">
                    <div class="contact fb"></div>
                </a>
                <a href="${pageContext.request.contextPath}/app/redirect?to=twitter" target="_blank">
                    <div class="contact tw"></div>
                </a>
                <a href="${pageContext.request.contextPath}/app/redirect?to=youtube" target="_blank">
                    <div class="contact yt"></div>
                </a>
            </div>
        </div>
    </div>
</div>
<%@include file="alerts.jsp" %>
</body>
</html>