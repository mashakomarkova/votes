<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html>
<c:set var="title" value="Profile" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<script src="${pageContext.request.contextPath}/js/validation.js"></script>
<script>function checkNewPassword() {
    var p1 = document.getElementById("newPassword");
    var p2 = document.getElementById("repeatPassword");
    if (p1.value !== p2.value) {
        document.getElementById("message").textContent = 'Not matching';
        document.getElementById("message").style.color = 'red';
        document.getElementById("newPassword").style.border = '1px solid red';
    } else {
        document.getElementById("message").textContent = 'Matching';
        document.getElementById("message").style.color = 'green';
        document.getElementById("newPassword").style.border = '1px solid #ccc';
    }
}</script>
<div class="uk-container">

    <div uk-grid>
        <div class="uk-width-1-4@s">
            <form action="controller" method="post" enctype="multipart/form-data">
                    <input type="hidden" id="hidden" name="command" value="updatePicture"/>
                    <div class="uk-margin">
                        <div class="preview text-center">
                            <c:choose>
                                <c:when test="${userCurrent.userPicture == null}">
                                    <img id="image" src="http://simpleicon.com/wp-content/uploads/account.png"
                                         class="img-circle img-responsive">
                                    <div class="browse-button">
                                        <input type="file" name="file" class="browse-input"
                                               onchange="readURL(this);"
                                               value="${userImage}">
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <img id="image" src="data:image/png;base64,${userImage}">
                                    <div class="browse-button">
                                        <input id="imgInput" type="file" name="file"
                                               class="browse-input"
                                               onchange="readURL(this);"
                                               value="${userImage}">
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>

                <div class="uk-margin">
                    <input type="submit" value="Update" class="uk-button-primary uk-button-large">
                </div>
            </form>
        </div>

        <div class="uk-width-1-4@s">
            <form action="controller" method="post">
                <input type="hidden" name="command" value="updateSettings"/>

                <div class="uk-margin">
                    <label for="first_name" class="uk-form-label"><fmt:message key="first.name"/></label>
                    <div class="uk-form-controls">
                        <input id="first_name" name="first_name" class="uk-input" required
                               pattern="[A-Z][a-z]*|[А-Я][а-я]*"
                               value="${userCurrent.firstName}">
                    </div>
                </div>

                <div class="uk-margin">
                    <label for="last_name" class="uk-form-label"><fmt:message key="last.name"/></label>
                    <div class="uk-form-controls">
                        <input id="last_name" name="last_name" class="uk-input" required
                               pattern="[A-Z][a-z]*|[А-Я][а-я]*"
                               value="${userCurrent.lastName}">
                    </div>
                </div>

                <div class="uk-margin">
                    <input type="submit" value="Update" class="uk-button-primary uk-button-large">
                </div>
            </form>
        </div>


        <div class="uk-width-1-4@s">
            <form action="controller" method="post">
                <input type="hidden" name="command" value="updateEm"/>
                <div class="uk-margin">
                    <label class="uk-form-label" for="email"><fmt:message key="email"/></label>
                    <div class="uk-form-controls">
                        <input name="email" class="uk-input" id="email" required
                               pattern="^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$"
                               value="${userCurrent.email}">
                    </div>
                </div>
                <div class="uk-margin">
                    <input type="submit" value="Update" class="uk-button-primary uk-button-large">
                </div>
            </form>
        </div>

        <div class="uk-width-1-4@s">
            <form action="controller" method="post">

                <input type="hidden" name="command" value="updatePass"/>
                <div class="uk-margin">
                    <label for="old_password" class="uk-form-label"><fmt:message key="old.password"/></label>
                    <div class="uk-form-controls">
                        <input type="password" name="old_password" id="old_password" class="uk-input" required>
                    </div>
                </div>
                <div class="uk-margin">
                    <label for="newPassword" class="uk-form-label"><fmt:message key="new.password"/></label>
                    <div class="uk-form-controls">
                        <input type="password" name="password" class="uk-input" id="newPassword" required
                               pattern="(?=.{8,})"
                               onkeyup="checkNewPassword(this.value)">
                    </div>
                </div>
                <div class="uk-margin">
                    <label for="repeatPassword" class="uk-form-label"><fmt:message key="repeat.new.password"/></label>
                    <div class="uk-form-controls">
                        <input type="password" name="repeatPassword" class="uk-input" id="repeatPassword" required
                               pattern="(?=.{8,})"
                               onkeyup="checkNewPassword(this.value)">

                    </div>
                </div>
                <span id='message'></span>
                <div class="uk-margin">
                    <input type="submit" value="Update" class="uk-button-primary uk-button-large">
                </div>
            </form>
        </div>
    </div>
    <%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
