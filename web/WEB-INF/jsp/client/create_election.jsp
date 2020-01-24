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
        b.setAttribute("class", "uk-button-danger");
        y.setAttribute("class", "input_field uk-form-controls");
        x.setAttribute("type", "text");
        x.setAttribute("name", "choices");
        x.setAttribute("class", "uk-input");
        y.appendChild(x);
        y.appendChild(b);
        document.getElementById("create_election").appendChild(y);
    }

    function removeInputField(selectedField) {
        selectedField.closest('.input_field').remove();
    }
</script>

<div class="uk-margin">
    <div class="uk-container">
        <div class="uk-card uk-card-default uk-card-body uk-width-1-1">
            <div class="uk-card-header">
                <h3>
                    <fmt:message key="create.election"/>
                </h3>
            </div>
            <div class="uk-card-body">
                <form action="controller" method="post">
                    <input type="hidden" name="command" value="createElectionCommand">
                    <div class="uk-margin">
                        <label class="uk-form-label" for="tx"><fmt:message key="question.text"/> </label>
                        <div class="uk-form-controls">
                            <textarea id="tx" name="questionText" class="uk-textarea" rows="3"></textarea>
                        </div>

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
                    <div class="uk-margin">
                        <label class="uk-form-label" for="country"><fmt:message key="country"/></label>
                        <div class="uk-form-controls">
                            <input class="uk-input" type="text" name="country" id="country">
                        </div>

                    </div>
                    <div class="uk-margin">
                        <label class="uk-form-label" for="city"><fmt:message key="city"/></label>
                        <div class="uk-form-controls">
                            <input type="text" id="city" name="city" class="uk-input">
                        </div>

                    </div>
                    <div class="uk-margin">
                        <label class="uk-form-label"><fmt:message key="topic"/></label>
                        <div class="uk-form-controls">
                            <myL:topicTag/>
                        </div>
                    </div>
                    <div class="uk-margin" id="create_election">
                        <fmt:message key="choice"/>
                        <button onclick="myFunction()" type="button" class="uk-button-default">Add choice</button>
                        <div class="input_field uk-form-controls">
                            <input type="text" name="choices" class="uk-input ">
                        </div>
                    </div>
                    <input type="submit" class="uk-button uk-button-default"
                           value="<fmt:message key="create.election"/>">
                </form>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
