<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html lang="${param.currentLocale}">
<c:set var="title" value="Main" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div id="home" class="banner" style="background-image: url('https://images.pexels.com/photos/1029624/pexels-photo-1029624.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260');">
    <div class="overlay"></div>
    <div class="banner-content">
        <div class="container">
            <div class="row">
                <div class="col-md-10 col-md-offset-1 text-center">
                    <h1>Effective <span>&</span> Creative</h1>
                    <p>We are Effective & Creative agency are many variations of passages of Lorem Ipsum available, but the majority have suffered.
                    </p>
                    <a href="#services" class="btn btn-primary page-scroll" role="button">Our Services</a>
                    <a href="#" class="btn btn-primary" role="button">Purchase Now</a>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
