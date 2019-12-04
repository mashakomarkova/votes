<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>
<c:set var="title" value="Poll result" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="container">
    <div class="row vote-results results">

        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" style="margin-left: 5px;">
            <c:forEach var="item" items="${result}">
                <h2>${item.choice} - ${item.counts}</h2>
                <div class="progress" style="height: 50px">
                    <div class="progress-bar progress-bar-${colors.get(rand.nextInt(colors.size()))}" role="progressbar"
                         aria-valuenow="" aria-valuemin="0"
                         aria-valuemax="100" style="width: ${(item.counts*100)/sum}%;display: flex;
                            justify-content: center;
                            align-items: center; font-size: large">
                        ${(item.counts*100)/sum}%

                    </div>
                </div>
            </c:forEach>
        </div>
        <div>
            <fmt:message key="all.votes"/>:
            ${numberOfVotes}
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
