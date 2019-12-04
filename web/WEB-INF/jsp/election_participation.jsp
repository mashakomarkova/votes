<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html>
<c:set var="title" value="Participation" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="container">
    <div class="panel-group">
        <div class="panel panel-info">
            <div class="panel-heading">
                <span class="glyphicon glyphicon-arrow-right"></span>
                <h3 class="panel-title">
                    ${election.questionText}
                </h3>
            </div>
            <div class="panel-body">
                <form action="controller" method="post">
                    <input type="hidden" name="command" value="participateInElection">

                    <input type="hidden" value="${election.id}" name="elId">
                    <ul class="list-group">
                        <c:forEach var="item" items="${choices}">
                            <li class="list-group-item">
                                <div class="radio">
                                    <label class="labelC">
                                        <input type="radio" value="${item.id}" name="participationChoice">
                                            ${item.choice}
                                    </label>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                    <div class="form-group text-center">
                        <input type="submit" value="<fmt:message key="submit"/>" class="btn btn-primary btn-lg">
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
