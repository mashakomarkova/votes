<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html>
<c:set var="title" value="Login"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="uk-container">
    <div class="uk-margin">
        <div class="uk-card uk-card-default uk-card-body uk-width-1-1">
            <div class="uk-card-header">
                <h3><fmt:message key="sign.in"/></h3>
            </div>
            <div class="uk-card-body">
                <form action="controller" method="post">
                    <div class="uk-margin">
                        <input type="hidden" name="command" value="login"/>
                        <label for="email" class="uk-form-label"><fmt:message key="email"/></label>
                        <div class="uk-form-controls">
                            <input class="uk-input" type="text" name="email" id="email" required>
                        </div>
                    </div>
                    <div class="uk-margin">
                        <label for="password" class="uk-form-label"><fmt:message key="password" /></label>
                        <div class="uk-form-controls">
                            <input class="uk-input" type="password" name="password" id="password"
                                   required>
                        </div>
                    </div>
                    <div class="uk-margin">
                        <input class="uk-button-primary uk-button-large" type="submit" value="<fmt:message key="login"/>">
                    </div>
                </form>
            </div>
            <div class="uk-card-footer">
                <a href="register.jsp"><fmt:message key="register"/></a>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>