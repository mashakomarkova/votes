<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="controller" method="post">
    <input type="hidden" name="command" value="register"/>
    <div class="form-group">
        <label for="username">Username</label>
        <input type="hidden" name="command" value="register"/>
        <input class="form-control" type="text" name="username" id="username" required pattern="^[a-z0-9_-]{3,16}$">
    </div>
    <div class="form-group">
        <label for="email">Email</label>
        <input class="form-control" type="text" name="email" id="email" required pattern="^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$">
    </div>
    <div class="form-group">
        <label for="password">Password</label>
        <input class="form-control" type="password" name="password" id="password" required>
    </div>
    <div class="form-group">
        <label for="first_name">First name</label>
        <input class="form-control" type="text" name="first_name" id="first_name" required >
    </div>
    <div class="form-group">
        <label for="last_name">Last name</label>
        <input class="form-control" type="text" name="last_name" id="last_name" required >
    </div>
    <input class="btn btn-primary" type="submit" value="Register">
</form>
</body>
</html>
