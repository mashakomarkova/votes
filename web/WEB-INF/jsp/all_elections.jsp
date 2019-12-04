<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html>
<c:set var="title" value="Elections" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<script>
    $(function () {
        var countryList = [
            "Afghanistan",
            "Albania",
            "Algeria",
            "American Samoa",
            "Andorra",
            "Angola",
            "Anguilla",
            "Antarctica",
            "Antigua and Barbuda",
            "Argentina",
            "Armenia",
            "Aruba",
            "Australia",
            "Austria",
            "Azerbaijan",
            "Bahamas (the)",
            "Bahrain",
            "Bangladesh",
            "Barbados",
            "Belarus",
            "Belgium",
            "Belize",
            "Benin",
            "Bermuda",
            "Bhutan",
            "Bolivia (Plurinational State of)",
            "Bonaire, Sint Eustatius and Saba",
            "Bosnia and Herzegovina",
            "Botswana",
            "Bouvet Island",
            "Brazil",
            "British Indian Ocean Territory (the)",
            "Brunei Darussalam",
            "Bulgaria",
            "Burkina Faso",
            "Burundi",
            "Cabo Verde",
            "Cambodia",
            "Cameroon",
            "Canada",
            "Cayman Islands (the)",
            "Central African Republic (the)",
            "Chad",
            "Chile",
            "China",
            "Christmas Island",
            "Cocos (Keeling) Islands (the)",
            "Colombia",
            "Comoros (the)",
            "Congo (the Democratic Republic of the)",
            "Congo (the)",
            "Cook Islands (the)",
            "Costa Rica",
            "Croatia",
            "Cuba",
            "Curaçao",
            "Cyprus",
            "Czechia",
            "Côte d'Ivoire",
            "Denmark",
            "Djibouti",
            "Dominica",
            "Dominican Republic (the)",
            "Ecuador",
            "Egypt",
            "El Salvador",
            "Equatorial Guinea",
            "Eritrea",
            "Estonia",
            "Eswatini",
            "Ethiopia",
            "Falkland Islands (the) [Malvinas]",
            "Faroe Islands (the)",
            "Fiji",
            "Finland",
            "France",
            "French Guiana",
            "French Polynesia",
            "French Southern Territories (the)",
            "Gabon",
            "Gambia (the)",
            "Georgia",
            "Germany",
            "Ghana",
            "Gibraltar",
            "Greece",
            "Greenland",
            "Grenada",
            "Guadeloupe",
            "Guam",
            "Guatemala",
            "Guernsey",
            "Guinea",
            "Guinea-Bissau",
            "Guyana",
            "Haiti",
            "Heard Island and McDonald Islands",
            "Holy See (the)",
            "Honduras",
            "Hong Kong",
            "Hungary",
            "Iceland",
            "India",
            "Indonesia",
            "Iran (Islamic Republic of)",
            "Iraq",
            "Ireland",
            "Isle of Man",
            "Israel",
            "Italy",
            "Jamaica",
            "Japan",
            "Jersey",
            "Jordan",
            "Kazakhstan",
            "Kenya",
            "Kiribati",
            "Korea (the Democratic People's Republic of)",
            "Korea (the Republic of)",
            "Kuwait",
            "Kyrgyzstan",
            "Lao People's Democratic Republic (the)",
            "Latvia",
            "Lebanon",
            "Lesotho",
            "Liberia",
            "Libya",
            "Liechtenstein",
            "Lithuania",
            "Luxembourg",
            "Macao",
            "Madagascar",
            "Malawi",
            "Malaysia",
            "Maldives",
            "Mali",
            "Malta",
            "Marshall Islands (the)",
            "Martinique",
            "Mauritania",
            "Mauritius",
            "Mayotte",
            "Mexico",
            "Micronesia (Federated States of)",
            "Moldova (the Republic of)",
            "Monaco",
            "Mongolia",
            "Montenegro",
            "Montserrat",
            "Morocco",
            "Mozambique",
            "Myanmar",
            "Namibia",
            "Nauru",
            "Nepal",
            "Netherlands (the)",
            "New Caledonia",
            "New Zealand",
            "Nicaragua",
            "Niger (the)",
            "Nigeria",
            "Niue",
            "Norfolk Island",
            "Northern Mariana Islands (the)",
            "Norway",
            "Oman",
            "Pakistan",
            "Palau",
            "Palestine, State of",
            "Panama",
            "Papua New Guinea",
            "Paraguay",
            "Peru",
            "Philippines (the)",
            "Pitcairn",
            "Poland",
            "Portugal",
            "Puerto Rico",
            "Qatar",
            "Republic of North Macedonia",
            "Romania",
            "Russian Federation (the)",
            "Rwanda",
            "Réunion",
            "Saint Barthélemy",
            "Saint Helena, Ascension and Tristan da Cunha",
            "Saint Kitts and Nevis",
            "Saint Lucia",
            "Saint Martin (French part)",
            "Saint Pierre and Miquelon",
            "Saint Vincent and the Grenadines",
            "Samoa",
            "San Marino",
            "Sao Tome and Principe",
            "Saudi Arabia",
            "Senegal",
            "Serbia",
            "Seychelles",
            "Sierra Leone",
            "Singapore",
            "Sint Maarten (Dutch part)",
            "Slovakia",
            "Slovenia",
            "Solomon Islands",
            "Somalia",
            "South Africa",
            "South Georgia and the South Sandwich Islands",
            "South Sudan",
            "Spain",
            "Sri Lanka",
            "Sudan (the)",
            "Suriname",
            "Svalbard and Jan Mayen",
            "Sweden",
            "Switzerland",
            "Syrian Arab Republic",
            "Taiwan (Province of China)",
            "Tajikistan",
            "Tanzania, United Republic of",
            "Thailand",
            "Timor-Leste",
            "Togo",
            "Tokelau",
            "Tonga",
            "Trinidad and Tobago",
            "Tunisia",
            "Turkey",
            "Turkmenistan",
            "Turks and Caicos Islands (the)",
            "Tuvalu",
            "Uganda",
            "Ukraine",
            "United Arab Emirates (the)",
            "United Kingdom of Great Britain and Northern Ireland (the)",
            "United States Minor Outlying Islands (the)",
            "United States of America (the)",
            "Uruguay",
            "Uzbekistan",
            "Vanuatu",
            "Venezuela (Bolivarian Republic of)",
            "Viet Nam",
            "Virgin Islands (British)",
            "Virgin Islands (U.S.)",
            "Wallis and Futuna",
            "Western Sahara",
            "Yemen",
            "Zambia",
            "Zimbabwe",
            "Åland Islands"
        ];
        $("#tags").autocomplete({
            source: countryList
        });
    });
</script>
<div class="container">

    <form action="controller" method="post" class="form-inline">
        <input type="hidden" name="command" value="search">
        <input type="hidden" name="elections" value="${elections}">
        <label><fmt:message key="search"/>
            <input class="form-control" type="text" name="keys">
        </label>
        <label><fmt:message key="country"/>
            <input type="text" class="form-control" name="country" id="tags">

        </label>
        <label><fmt:message key="city"/>
            <input type="text" class="form-control" name="city">
        </label>
        <label><fmt:message key="select.topic"/>
            <myL:topicTag/>
        </label>
        <input class="btn btn-primary" type="submit" value="<fmt:message key="search"/>">
    </form>

    <form action="controller" method="post">
        <input type="hidden" name="command" value="sort">
        <div class="form-group">
            <select name="option" class="form-control">
                <option value="date_of_register">
                    <fmt:message key="recent"/>
                </option>
                <option value="popular">
                    <fmt:message key="popular"/>
                </option>
            </select>
        </div>

        <input type="submit" class="btn btn-primary" value="<fmt:message key="sort"/>">
    </form>


    <h2><fmt:message key="top.votes"/></h2>
    <table class="table table-striped custab">
        <tr>
            <td><fmt:message key="number"/></td>
            <td><fmt:message key="question.text"/></td>
            <td><fmt:message key="favorite"/></td>
            <td><fmt:message key="participate"/></td>
        </tr>
        <c:set var="k" value="0"/>

        <c:forEach var="item" items="${topElections}" varStatus="loop">

            <c:set var="k" value="${k+1}"/>
            <c:if test="${item.status eq 'top'}">

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
            </c:if>
        </c:forEach>
    </table>

    <h2><fmt:message key="plain.votes"/></h2>
    <table class="table table-striped custab">
        <tr>
            <td><fmt:message key="number"/></td>
            <td><fmt:message key="question.text"/></td>
            <td><fmt:message key="favorite"/></td>
            <td><fmt:message key="participate"/></td>
        </tr>
        <c:set var="k" value="0"/>
        <c:forEach var="item" items="${elections}" varStatus="loop">
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
    <ul class="pagination">
        <c:forEach var="i" begin="1" end="${pages}">
            <c:choose>
                <c:when test="${pa eq i}">
                    <li class="active">
                        <a href="controller?command=pageElections&p=${i}&t=${total}&numOfPages=${pages}">${i}</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li>
                        <a href="controller?command=pageElections&p=${i}&t=${total}&numOfPages=${pages}">${i}</a>
                    </li>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </ul>
    <br>
    <br>
    <br>
    <br>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
