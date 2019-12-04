<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html>
<c:set var="title" value="Favorite elections" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="container">
    <table class="table table-striped custab">
        <tr>
            <td><fmt:message key="number"/></td>
            <td><fmt:message key="question.text"/></td>
            <td><fmt:message key="participate"/></td>
            <td><fmt:message key="favorite"/></td>
        </tr>
        <c:set var="k" value="0"/>
        <c:forEach var="item" items="${favoriteElections}" varStatus="loop">
                <c:set var="k" value="${k+1}"/>
            <c:choose>
                <c:when test="${fn:contains(participatedElections,item)}">
                    <tr class="success">
                        <td><c:out value="${k}"/></td>
                        <td>${item.questionText}</td>
                        <td>
                            <c:choose>
                                <c:when test="${fn:contains(favoriteElections,item)}">
                                    <a href="controller?command=addToFavorites&electionRemove=${item.id}"> <span
                                            class="star glyphicon glyphicon-star">
                            </span>
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <a href="controller?command=addToFavorites&election=${item.id}"> <span
                                            class="star glyphicon glyphicon-star-empty">
                            </span>
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <a href="controller?command=viewPollResults&elId=${item.id}"><fmt:message
                                    key="view.results"/>
                            </a>
                        </td>

                    </tr>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td><c:out value="${k}"/></td>
                        <td>${item.questionText}</td>
                        <td>
                            <c:choose>
                                <c:when test="${fn:contains(favoriteElections,item)}">
                                    <a href="controller?command=addToFavorites&electionRemove=${item.id}"> <span
                                            class="star glyphicon glyphicon-star">
                            </span>
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <a href="controller?command=addToFavorites&election=${item.id}"> <span
                                            class="star glyphicon glyphicon-star-empty">
                            </span>
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td><a href="controller?command=findElectionById&elId=${item.id}"><fmt:message
                                key="participate"/></a>
                        </td>
                    </tr>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </table>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
