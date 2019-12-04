<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html>
<c:set var="title" value="Login"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="uk-container">
    <div class="uk-card uk-card-default uk-card-body uk-width-1-1">
        <div class="uk-card-header">
            <h3><fmt:message key="sign.in"/></h3>
        </div>
        <div class="uk-card-body">
            <form action="controller" method="post">
                <div class="form-group">
                    <input type="hidden" name="command" value="login"/>
                    <label for="email">Email</label>
                    <input class="form-control input-sm chat-input" type="text" name="email" id="email" required>
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input class="form-control input-sm chat-input" type="password" name="password" id="password"
                           required>
                </div>
                <input class="btn btn-primary" type="submit" value="Login">
            </form>
        </div>
        <div class="uk-card-footer">
            <a href="register.jsp"><fmt:message key="register"/></a>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>