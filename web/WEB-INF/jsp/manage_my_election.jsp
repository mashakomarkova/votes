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

<div class="uk-margin">
    <div class="uk-container">

        <div class="uk-child-width-1-2@m" uk-grid>
            <div>
                <div class="uk-card uk-card-default">
                    <div class="uk-card-header">
                        <h2><fmt:message key="edit.election"/></h2>
                    </div>
                    <div class="uk-card-body">
                        <form action="controller" method="post">
                            <input type="hidden" name="command" value="updateElection">
                            <input type="hidden" name="electionId" value="${myElection.id}">
                            <div class="uk-margin">
                                <label class="uk-form-label"><fmt:message key="question.text"/></label>
                                <div class="uk-form-controls">
                                <textarea name="questionText" class="uk-textarea">
                                    ${myElection.questionText}
                                </textarea>
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
                            <c:forEach var="item" items="${myChoices}">
                                <div class="uk-margin">
                                    <label class="uk-form-label"> </label>
                                    <div class="uk-form-controls">
                                        <input type="text" name="choices" value="${item.choice}" class="uk-input">
                                    </div>
                                    <input type="hidden" value="${item.id}" name="choiceId">

                                </div>
                            </c:forEach>
                            <div class="uk-margin">
                                <label class="uk-form-label"></label>
                                <div class="uk-form-controls">
                                    <input class="uk-input" type="text" value="${electionURL}" id="myUrl">
                                    <input class="uk-button uk-button-default" type="button" value="Copy Url"
                                           onclick="Copy();"/>
                                </div>

                            </div>
                            <div class="uk-margin">
                                <input type="submit" class="uk-button uk-button-primary"
                                       value="<fmt:message key="update.election"/>">
                            </div>
                        </form>

                            <form action="controller" method="post">
                                <input type="hidden" name="command" value="deleteElection">
                                <input type="hidden" name="electionId" value="${myElection.id}">
                                <div class="uk-margin">
                                    <input type="submit" value="<fmt:message key="delete.election"/>"
                                           class="uk-button uk-button-danger">
                                </div>
                            </form>

                    </div>
                </div>
            </div>

            <div>
                <div class="uk-card uk-card-default">
                    <form action="controller" method="post">
                        <input type="hidden" name="command" value="payment">
                        <input type="hidden" name="electionId" value="${myElection.id}">
                        <div class="uk-card-header">
                            <h2><fmt:message key="get.to.top"/></h2>
                        </div>
                        <div class="uk-card-body">
                            <div class="uk-margin">
                                <label class="uk-form-label"><fmt:message key="days.in.top"/> </label>
                                <div class="uk-form-controls">
                                    <input type="text" name="days" class="uk-input">
                                </div>
                            </div>
                            <div class="uk-margin">
                                <input type="submit" class="uk-button uk-button-primary">

                            </div>
                        </div>

                    </form>

                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
