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
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-4">
            <form action="controller" method="post" enctype="multipart/form-data" class="prospects_form">
                <input type="hidden" id="hidden" name="command" value="updateSettings"/>
                <div class="col-sm-5">
                    <div class="preview text-center">
                        <c:choose>
                            <c:when test="${userCurrent.userPicture == null}">
                                <img id="image" src="http://simpleicon.com/wp-content/uploads/account.png"
                                     class="img-circle img-responsive">
                                <div class="browse-button">
                                    <input type="file" name="file" class="form-control browse-input"
                                           onchange="readURL(this);"
                                           value="${userImage}">
                                </div>
                            </c:when>
                            <c:otherwise>
                                <%--                                <div id="image" style="background-image: url('data:image/png;base64,${userImage}')"  class="img-circle img-responsive"></div>--%>
                                <img id="image" src="data:image/png;base64,${userImage}"
                                     >
                                <div class="browse-button">
                                    <input id="imgInput" type="file" name="file" class="form-control browse-input"
                                           onchange="readURL(this);"
                                           value="${userImage}">
                                </div>
                            </c:otherwise>
                        </c:choose>


                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="form-group">
                        <label><fmt:message key="first.name"/>
                            <input id="first_name" name="first_name" class="form-control" required
                                   pattern="[A-Z][a-z]*|[А-Я][а-я]*"
                                   value="${userCurrent.firstName}">
                        </label>
                    </div>
                    <div class="form-group">
                        <label><fmt:message key="last.name"/>
                            <input id="last_name" name="last_name" class="form-control" required
                                   pattern="[A-Z][a-z]*|[А-Я][а-я]*"
                                   value="${userCurrent.lastName}">
                        </label>
                    </div>
                    <div class="form-group">
                        <input type="submit" value="Update" class="btn btn-lg btn-success">
                    </div>
                </div>
            </form>
        </div>
        <div class="col-sm-3">
            <form action="controller" method="post" class="prospects_form">
                <input type="hidden" name="command" value="updateEm"/>
                <div class="form-group">
                    <label><fmt:message key="email"/>
                        <input name="email" class="form-control" required
                               pattern="^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$"
                               value="${userCurrent.email}">
                    </label>
                </div>
                <div class="form-group">
                    <input type="submit" value="Update" class="btn btn-lg btn-success">
                </div>
            </form>

        </div>
        <div class="col-sm-3">

            <form action="controller" method="post" class="prospects_form">
                <input type="hidden" name="command" value="updatePass"/>
                <div class="form-group">
                    <label><fmt:message key="old.password"/>
                        <input type="password" name="old_password" class="form-control" required>
                    </label>
                </div>
                <div class="form-group">
                    <label><fmt:message key="new.password"/>
                        <input type="password" name="password" class="form-control" id="newPassword" required
                               pattern="(?=.{8,})"
                               onkeyup="checkNewPassword(this.value)">
                    </label>
                </div>
                <div class="form-group">
                    <label><fmt:message key="repeat.new.password"/>
                        <input type="password" name="repeatPassword" class="form-control" id="repeatPassword" required
                               pattern="(?=.{8,})"
                               onkeyup="checkNewPassword(this.value)">
                    </label>
                </div>
                <span id='message'></span>
                <div class="form-group">
                    <input type="submit" value="Update" class="btn btn-lg btn-success">
                </div>
            </form>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
