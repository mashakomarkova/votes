<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html>
<c:set var="title" value="Success" scope="page"/>

<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="uk-margin">
    <div class="uk-container">

        <h3><fmt:message key="data.success"/></h3>
        <c:if test="${isParticipated}">
            <h3 class="uk-text-success">
                <fmt:message key="thanks.participation"/>
            </h3>
        </c:if>
        <c:if test="${isUpdatedElection}">
            <h3 class="uk-text-success">
                <fmt:message key="data.success"/>
            </h3>
        </c:if>
        <c:if test="${isPaidForTop}">
            <h3 class="uk-text-success">
                <fmt:message key="thanks.payment"/>
            </h3>
        </c:if>
        <c:if test="${emailConfirmed}">
            <h3 class="uk-text-success">
                <fmt:message key="data.success"/>
            </h3>
        </c:if>
        <c:if test="${electionCreated}">
            <h3 class="uk-text-success">
                <fmt:message key="data.success"/>
            </h3>
        </c:if>
        <c:if test="${language}">
            <h3 class="uk-text-success">
                <fmt:message key="language.changed"/>
            </h3>
        </c:if>
    </div>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
