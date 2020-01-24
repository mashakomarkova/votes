<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html>
<c:set var="title" value="Participation" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="uk-margin">
    <div class="uk-container">
        <div class="uk-card uk-card-default uk-width-1-1@m">
            <div class="uk-card-header">
                <h3>
                    ${election.questionText}
                </h3>
            </div>
            <div class="uk-card-body">
                <form action="controller" method="post">
                    <input type="hidden" name="command" value="participateInElection">

                    <input type="hidden" value="${election.id}" name="elId">
                    <ul class="uk-list uk-list-large uk-list-divider">
                        <c:forEach var="item" items="${choices}">
                            <li>
                                <div class="radio">
                                    <label class="labelC">
                                        <input type="radio" value="${item.id}" name="participationChoice">
                                            ${item.choice}
                                    </label>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>

                    <c:if test="${election.access=='private'}">
                        <div class="uk-margin">
                            <label class="uk-form-label" for="loginBio">Enter your login in BioSky</label>
                            <div class="uk-form-controls">
                                <input type="text" name="login" id="loginBio" class="uk-input">
                            </div>
                        </div>

                        <div class="uk-margin">
                            <label class="uk-form-label" for="tId">Enter t-id</label>
                            <div class="uk-form-controls">
                                <input type="text" name="tid" id="tId" class="uk-input">
                            </div>
                        </div>
                    </c:if>

                    <div class="uk-card-footer">
                        <input type="submit" value="<fmt:message key="submit"/>" class="uk-button uk-button-primary">
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
