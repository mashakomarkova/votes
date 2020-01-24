<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html>
<c:set var="title" value="Elections" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>


<div class="uk-container">
    <table class="uk-table uk-table-striped">
        <tr>
            <td><fmt:message key="number"/></td>
            <td><fmt:message key="login"/></td>
            <td><fmt:message key="first.name"/></td>
            <td><fmt:message key="last.name"/></td>
            <td><fmt:message key="question.text"/></td>
            <td><fmt:message key="date.of.register"/></td>
            <td><fmt:message key="city"/></td>
            <td><fmt:message key="country"/></td>
            <td><fmt:message key="delete.election"/></td>
        </tr>
        <c:set var="k" value="0"/>

        <c:forEach var="item" items="${userElectionBean}" varStatus="loop">
            <c:set var="k" value="${k+1}"/>
            <tr>
                <td><c:out value="${k}"/></td>
                <td>${item.username}</td>
                <td>${item.firstName}</td>
                <td>${item.lastName}</td>
                <td>${item.questionText}</td>
                <td>${item.dateOfRegister}</td>
                <td>${item.city}</td>
                <td>${item.country}</td>
                <td><a href="controller?command=deleteElectionAdmin&electionId=${item.id}"><fmt:message key="delete.election"/>
                </a></td>
            </tr>
        </c:forEach>
    </table>

</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
