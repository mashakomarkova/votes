<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html>
<c:set var="title" value="Success" scope="page"/>

<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<h3><fmt:message key="data.success"/></h3>
<c:if test="${isParticipated}">
    <fmt:message key="thanks.participation"/>
</c:if>
<c:if test="${isUpdatedElection}">
    <fmt:message key="data.success"/>
</c:if>
<c:if test="${isPaidForTop}">
    <fmt:message key="thanks.payment"/>
</c:if>
<c:if test="${emailConfirmed}">
    <fmt:message key="data.success"/>
</c:if>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
