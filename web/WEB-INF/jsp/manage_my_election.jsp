<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html>
<c:set var="title" value="My election" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<script type="text/javascript">
    function Copy() {
        var copyText = document.getElementById("myUrl");
        copyText.select();
        copyText.setSelectionRange(0, 99999)
        document.execCommand("copy");
    }
</script>
<div class="container">
    <form action="controller" method="post">
        <input type="hidden" name="command" value="updateElection">
        <input type="hidden" name="electionId" value="${myElection.id}">
        <div class="form-group">
            <label><fmt:message key="question.text"/>
                <textarea name="questionText" class="form-control">
                    ${myElection.questionText}
                </textarea>
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
        <c:forEach var="item" items="${myChoices}">
            <div class="form-group">
                <label>
                    <input type="text" name="choices" value="${item.choice}" class="form-control">
                    <input type="hidden" value="${item.id}" name="choiceId">
                </label>
            </div>
        </c:forEach>
        <div class="form-group">
            <label>
                <input type="text" value="${electionURL}" class="form-control" id="myUrl">
                <input type="button" value="Copy Url" onclick="Copy();"/>
            </label>
        </div>
        <input type="submit" class="btn btn-lg btn-success" value="<fmt:message key="update.election"/>">
    </form>

    <form action="controller" method="post">
        <input type="hidden" name="command" value="deleteElection">
        <input type="hidden" name="electionId" value="${myElection.id}">
        <input type="submit" value="<fmt:message key="delete.election"/>" class="btn btn-lg btn-danger">
    </form>
    <form action="controller" method="post">
        <input type="hidden" name="command" value="payment">
        <input type="hidden" name="electionId" value="${myElection.id}">
        <div class="form-group">
            <h2>
                <fmt:message key="get.to.top"/>
            </h2>
        </div>
        <div class="form-group">
            <label><fmt:message key="days.in.top"/>
                <input type="text" name="days" class="form-control">
            </label>
        </div>
        <div class="form-group">
            <label><fmt:message key="card.number"/>
                <input type="text" name="cardNumber" class="form-control">
            </label>
        </div>
        <div class="form-group">
            <label><fmt:message key="validity"/>
                <input type="text" name="validity" class="form-control">
            </label>
        </div>
        <div class="form-group">
            <label><fmt:message key="cvv"/>
                <input type="text" name="cvv" class="form-control">
            </label>
        </div>
        <input type="submit" class="btn btn-lg btn-success">
    </form>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
