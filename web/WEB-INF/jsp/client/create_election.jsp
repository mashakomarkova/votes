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
        b.setAttribute("onclick","removeInputField(this)");
        b.textContent="Remove";
        b.setAttribute("type","button");
        y.setAttribute("class","input_field");
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
    <form action="controller" method="post">
        <input type="hidden" name="command" value="createElection">
        <div class="form-group">
            <label><fmt:message key="question.text"/>
                <textarea name="questionText" class="form-control"></textarea>
            </label>
        </div>
        <div class="form-group">
            <select name="access" class="form-control">
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
                <input type="text" name="topic" class="form-control">
            </label>
        </div>
        <div class="form-group" id="create_election">
            <fmt:message key="choice"/>
            <button onclick="myFunction()" type="button">Add choice</button>
            <%--            <c:forEach var="i" begin="1" end="5">--%>
            <div class="input_field">
                <input type="text" name="choices" class="form-control">
<%--                <button onclick="removeInputField(this)" type="button">Remove choice</button>--%>
            </div>
            <%--            </c:forEach>--%>
        </div>
        <input type="submit" class="btn btn-lg btn-success" value="<fmt:message key="create.election"/>">
    </form>

</div>


<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
