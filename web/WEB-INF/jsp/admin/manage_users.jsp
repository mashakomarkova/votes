<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html>
<c:set var="title" value="Manage " scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<script>
    function openForm(){
        document.getElementById("myForm").style.display = "block";
    }
    function closeForm() {
        document.getElementById("myForm").style.display = "none";

    }

</script>
<div class="uk-container">
    <table class="uk-table uk-table-striped">
        <tr>
            <td><fmt:message key="number"/></td>
            <td><fmt:message key="login"/></td>
            <td><fmt:message key="email"/></td>
            <td><fmt:message key="first.name"/></td>
            <td><fmt:message key="last.name"/></td>
            <td><fmt:message key="delete.user"/></td>
        </tr>
        <c:set var="k" value="0"/>

        <c:forEach var="item" items="${allUsers}" varStatus="loop">
            <c:set var="k" value="${k+1}"/>
            <tr>
                <td><c:out value="${k}"/></td>
                <td>${item.login}</td>
                <td>${item.email}</td>
                <td>${item.firstName}</td>
                <td>${item.lastName}</td>
                <td><a href="controller?command=deleteUserById&uId=${item.id}"><fmt:message
                        key="delete.user"/></a></td>
            </tr>
        </c:forEach>
    </table>

</div>


<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
