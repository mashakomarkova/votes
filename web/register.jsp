<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %><html>
<c:set var="title" value="Register" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<script>
    function checkRegistration() {
        var mailformat = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
        var text = "";
        var username = document.getElementById("username").value;
        var email = document.getElementById("email").value;
        var password = document.getElementById("password").value;
        var fistName = document.getElementById("first_name").value;
        var lastName = document.getElementById("last_name").value;
        var FLname= '/^[a-zA-Z]+|[А-Яа-яёЁЇїІіЄєҐґ]+$/';
        if (username.length<3){
            text += "Username must be 3+ symbols";
        }
        else if(!(email.match(mailformat))){
            text += "Incorrect email";
        }
        else if(password.length < 8){
            text += "Password must have minimum eight characters, at least one letter and one number.";
        }
        else if(!(fistName.match(FLname))){
            text += "Incorrect input";

        }
        else if(!(lastName.match(FLname))){
            text += "Incorrect input";
        }
        document.getElementById("valid").innerHTML = text;
    }
    function checkNewPassword() {
        var p1 = document.getElementById("password");
        var p2 = document.getElementById("confirm_password");
        if (p1.value !== p2.value) {
            document.getElementById("valid").textContent = 'Not matching';
            document.getElementById("valid").style.color = 'red';
            document.getElementById("confirm_password").style.border = '1px solid red';
        } else {
            document.getElementById("valid").textContent = 'Matching';
            document.getElementById("valid").style.color = 'green';
            document.getElementById("confirm_password").style.border = '1px solid #ccc';
        }
    }
</script>

<div class="container">
    <form action="controller" method="post">
        <input type="hidden" name="command" value="register"/>
        <div class="form-group">
            <label for="username"><fmt:message key="username"/> </label>
            <input type="hidden" name="command" value="register"/>
            <input class="form-control" type="text" name="username" id="username" required pattern="^[a-z0-9_-]{3,16}$">
        </div>
        <div class="form-group">
            <label for="email"><fmt:message key="email"/></label>
            <input class="form-control" type="text" name="email" id="email" required pattern="^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$">
        </div>
        <div class="form-group">
            <label for="password"><fmt:message key="password"/></label>
            <input class="form-control" type="password" name="password" id="password" required pattern="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$">

            <label for="confirm_password"><fmt:message key="repeat.new.password"/></label>
            <input class="form-control" type="password" name="conf" id="confirm_password" required pattern="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$"
                   onkeyup="checkNewPassword(this.value)">
        </div>
        <div class="form-group">
            <label for="first_name"><fmt:message key="first.name"/></label>
            <input class="form-control" type="text" name="first_name" id="first_name" required pattern="^[a-zA-Z]+|[А-Яа-яёЁЇїІіЄєҐґ]+$">
        </div>
        <div class="form-group">
            <label for="last_name"><fmt:message key="last.name"/></label>
            <input class="form-control" type="text" name="last_name" id="last_name" required pattern="^[a-zA-Z]+|[А-Яа-яёЁЇїІіЄєҐґ]+$">
        </div>
        <input class="btn btn-primary" type="submit" value="Register" onclick="checkRegistration()">

        <p id="valid"></p>
    </form>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
