<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html>
<c:set var="title" value="Create election" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<script>
    function myFunction() {
        var x = document.createElement("input");
        var y = document.createElement("div");
        var b = document.createElement("button");
        b.setAttribute("onclick", "removeInputField(this)");
        b.textContent = "Remove";
        b.setAttribute("type", "button");
        b.setAttribute("class", "input-group-text btn btn-sm btn-danger");
        y.setAttribute("class", "input_field");
        x.setAttribute("type", "text");
        x.setAttribute("name", "choices");
        x.setAttribute("class", "form-control");
        y.appendChild(x);
        y.appendChild(b);
        document.getElementById("create_election").appendChild(y);
    }

    function removeInputField(selectedField) {
        selectedField.closest('.input_field').remove();
    }
</script>

<div class="container">
    <div class="panel panel-success">
        <div class="panel-heading">
            <h3 class="panel-title">
                <fmt:message key="create.election"/>
            </h3>
        </div>
        <div class="panel-body text-center">
            <form action="controller" method="post">
                <input type="hidden" name="command" value="createElection">
                <div class="uk-margin">
                    <label for="tx"><fmt:message key="question.text"/> </label>
                    <textarea id="tx" name="questionText" class="uk-textarea" rows="3" ></textarea>

                </div>
                <div class="uk-margin">
                    <select name="access" class="uk-select">
                        <option value="private">
                            <fmt:message key="private"/>
                        </option>
                        <option value="public">
                            <fmt:message key="public"/>
                        </option>
                    </select>
                </div>
                <div class="form-group">
                    <label><fmt:message key="country"/>
                        <input type="text" name="country" class="form-control">
                    </label>
                </div>
                <div class="form-group">
                    <label><fmt:message key="city"/>
                        <input type="text" name="city" class="form-control">
                    </label>
                </div>
                <div class="form-group">
                    <label><fmt:message key="topic"/>
                        <myL:topicTag  />
                    </label>
                </div>
                <div class="form-group" id="create_election">
                    <fmt:message key="choice"/>
                    <div class="row">
                        <button onclick="myFunction()" type="button" class="btn btn-sm btn-success">Add choice</button>
                        <div class="input_field">
                            <input type="text" name="choices" class="form-control">
                        </div>
                    </div>
                </div>
                <input type="submit" class="btn btn-lg btn-success" value="<fmt:message key="create.election"/>">
            </form>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
