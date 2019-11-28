<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html>
<c:set var="title" value="Transactions" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="container">
    <table class="table table-striped custab">
        <tr>
            <td><fmt:message key="number"/></td>
            <td><fmt:message key="transaction.first.date"/></td>
            <td><fmt:message key="transaction.last.date"/></td>
            <td><fmt:message key="sum"/></td>
        </tr>
        <c:set var="k" value="0"/>
        <c:forEach var="item" items="${myTransactions}" varStatus="loop">
            <c:set var="k" value="${k+1}"/>
            <tr>
                <td><c:out value="${k}"/></td>
                <td>${item.firstDate}</td>
                <td>${item.lastDate}</td>
                <td>${item.sum}</td>
            </tr>
        </c:forEach>
    </table>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
