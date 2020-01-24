<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html>
<c:set var="title" value="Private elections" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="uk-container">
    <table class="uk-table uk-table-striped">
        <tr>
            <td><fmt:message key="number"/></td>
            <td><fmt:message key="question.text"/></td>
            <td><fmt:message key="view.election"/></td>
            <td><fmt:message key="view.results"/></td>
        </tr>
        <c:set var="k" value="0"/>
        <c:forEach var="item" items="${myElections}" varStatus="loop">
            <c:set var="k" value="${k+1}"/>
            <tr>
                <td><c:out value="${k}"/></td>
                <td>${item.questionText}</td>
                <td><a href="controller?command=findMyElectionById&elId=${item.id}"><fmt:message key="view.election"/></a></td>
                <td><a href="controller?command=viewPollResults&elId=${item.id}"><fmt:message key="view.results"/></a></td>
            </tr>
        </c:forEach>

    </table>
</div>


<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
