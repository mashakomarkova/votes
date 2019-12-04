<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html>
<c:set var="title" value="Error page" scope="page"/>

<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<c:if test="${electionsAreOver}">
    <fmt:message key="dont.have.elections.enough"/>
    <form action="controller" method="post">
        <input type="hidden" name="command" value="paymentForElection">
        <input type="hidden" name="electionId" value="${electionId}">
        <div class="form-group">
            <h2>
                <fmt:message key="get.to.top"/>
            </h2>
        </div>
        <div class="form-group">
            <label><fmt:message key="elections.days"/>
                <input type="text" name="electionDays" class="form-control">
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
</c:if>

<c:if test="${hasAlreadyParticipated}">
    <fmt:message key="you.cannot.participate.twice"/>
</c:if>

<c:if test="${emptyLogin}">
    <fmt:message key="empty.login"/>
</c:if>

<c:if test="${incorrectUser}">
    <fmt:message key="incorrect.user"/>
</c:if>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
