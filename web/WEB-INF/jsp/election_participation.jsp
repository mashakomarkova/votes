<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html>
<c:set var="title" value="Participation" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="container">
    <form action="controller" method="post">
        <input type="hidden" name="command" value="participateInElection">
        <p>
            ${election.questionText}
        </p>
        <input type="hidden" value="${election.id}" name="elId">
        <c:forEach var="item" items="${choices}">
            <div class="form-group">
                <label>
                        ${item.choice}
                    <input type="radio" value="${item.id}" name="participationChoice" class="form-control">
                </label>
            </div>
        </c:forEach>
        <input type="submit" value="<fmt:message key="submit"/>">
    </form>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
