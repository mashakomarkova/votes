<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>
<c:set var="title" value="Poll result" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<c:forEach var="item" items="${result}" >
<p> ${item.choice} - ${item.counts}</p>
</c:forEach>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
