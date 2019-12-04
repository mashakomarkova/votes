<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html>
<c:set var="title" value="My election" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<script type="text/javascript">
    function Copy() {
        var copyText = document.getElementById("myUrl");
        copyText.select();
        copyText.setSelectionRange(0, 99999)
        document.execCommand("copy");
    }
</script>

<div class="container">

    <div class="form-group row">
        <div class="col-md-5">
            <div class="panel panel-default">
                <div class="panel-heading clearfix">
                    <h2 class="panel-title"><fmt:message key="edit.election"/></h2>
                </div>
                <div class="panel-body">
                    <form action="controller" method="post">
                        <input type="hidden" name="command" value="updateElection">
                        <input type="hidden" name="electionId" value="${myElection.id}">
                        <div class="form-group">
                            <label><fmt:message key="question.text"/>
                                <textarea name="questionText" class="form-control">
                                    ${myElection.questionText}
                                </textarea>
                            </label>
                        </div>
                        <div class="form-group">
                            <select name="access" class="form-control">
                                <option value="private">
                                    <fmt:message key="private"/>
                                </option>
                                <option value="public">
                                    <fmt:message key="public"/>
                                </option>
                            </select>
                        </div>
                        <c:forEach var="item" items="${myChoices}">
                            <div class="form-group">
                                <label>
                                    <input type="text" name="choices" value="${item.choice}" class="form-control">
                                    <input type="hidden" value="${item.id}" name="choiceId">
                                </label>
                            </div>
                        </c:forEach>
                        <div class="form-group">
                            <label>
                                <input type="text" value="${electionURL}" class="form-control" id="myUrl">
                                <input type="button" value="Copy Url" onclick="Copy();"/>
                            </label>
                        </div>
                        <input type="submit" class="btn btn-lg btn-success"
                               value="<fmt:message key="update.election"/>">
                    </form>
                </div>
            </div>
        </div>
        <div class="col-md-5">
            <div class="panel panel-default" style="height: 450px">
                <form action="controller" method="post">
                    <input type="hidden" name="command" value="payment">
                    <input type="hidden" name="electionId" value="${myElection.id}">
                    <div class="panel-heading clearfix">
                        <h2 class="panel-title"><fmt:message key="get.to.top"/></h2>
                    </div>
                    <div class="form-group">
                        <label><fmt:message key="days.in.top"/>
                            <input type="text" name="days" class="form-control">
                        </label>
                    </div>

                    <!-- CREDIT CARD FORM STARTS HERE -->
                    <div class="panel panel-default credit-card-box">
                        <div class="panel-heading display-table" >
                            <div class="row display-tr" >
                                <h3 class="panel-title display-td" >Payment Details</h3>
                                <div class="display-td" >
                                    <img class="img-responsive pull-right" src="https://developer.paysafe.com/fileadmin/content/logos/accepted_cards_by_paysafejs.png">
                                </div>
                            </div>
                        </div>
                        <div class="panel-body">
                                <div class="row">
                                    <div class="col-xs-12">
                                        <div class="form-group">
                                            <label for="cardNumber">CARD NUMBER</label>
                                            <div class="input-group">
                                                <div
                                                        class="form-control"
                                                        id="cardNumber"
                                                > </div>
                                                <span class="input-group-addon"><i class="fa fa-credit-card"></i></span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-7 col-md-7">
                                        <div class="form-group">
                                            <label for="cardExpiry"><span class="hidden-xs">EXPIRATION</span><span class="visible-xs-inline">EXP</span> DATE</label>
                                            <div
                                                    class="form-control"
                                                    id="cardExpiry"
                                            ></div>
                                        </div>
                                    </div>
                                    <div class="col-xs-5 col-md-5 pull-right">
                                        <div class="form-group">
                                            <label for="cardCVC">CV CODE</label>
                                            <div
                                                    class="form-control"
                                                    id="cardCVC"
                                            ></div>
                                        </div>
                                    </div>
                                </div>
                        </div>
                    </div>
                    <!-- CREDIT CARD FORM ENDS HERE -->

                    <input type="submit" class="btn btn-lg btn-success">
                </form>
            </div>
        </div>
    </div>


    <form action="controller" method="post">
        <input type="hidden" name="command" value="deleteElection">
        <input type="hidden" name="electionId" value="${myElection.id}">
        <input type="submit" value="<fmt:message key="delete.election"/>" class="btn btn-lg btn-danger">
    </form>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
