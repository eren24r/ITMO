<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="data" class="main.model.UserAreaDatas" scope="session" />
<%@ page import="java.time.format.DateTimeFormatter" %>
<core:set var="userName" value="" scope="page" />

<!DOCTYPE html>
<html lang="ru-RU">
<head>
    <title>WEB LAB 2</title>
    <link rel="stylesheet" href="style/style.css">
    <meta charset="UTF-8">
        <script type="text/javascript">
            function getCookie(name) {
                var cookies = document.cookie.split(';');
                for (var i = 0; i < cookies.length; i++) {
                    var cookie = cookies[i].trim();
                    if (cookie.indexOf(name + '=') === 0) {
                        return cookie.substring(name.length + 1);
                    }
                }
                return null; // Куки с указанным именем не найдены
            }

            var userCookieValue = getCookie('username');
            var password = getCookie('password')

            if (userCookieValue && password) {
                console.log('куки "username"');
            } else {
                window.location.href = "login.jsp";
            }
        </script>

</head>
<body>
<header>
    <img id="itmo_logo" src="https://itmo.ru/promo/itmo-logo-dark.svg">
    <h1 id="lab1">Лабораторная работа №2 Рахматов Нематджон P3233 вариант 5551</h1>
    <div>
        <form action="${pageContext.request.contextPath}/controller" method="post">
            <input type="hidden" name="clear" value="1" />
            <div id="clear-table-container" class="select-container margin">
                <input type="submit" id="clear-table" value="Clear table" />
            </div>
        </form>
    </div>
</header>
<div id="allBody">
    <div id="bodyA">
        <div id = "graff">
            <div id="calculator" style="width: 500px; height: 500px;"></div>
            <script src="https://www.desmos.com/api/v1.8/calculator.js?apiKey=dcb31709b452b1cf9dc26972add0fda6"></script>

            <script src="script/graph.js"></script>

        </div>

        <form action="${pageContext.request.contextPath}/controller" method="post">

            <div id="chosingValTextX"><label>Value of X:</label></div>

            <div id="x_radio">
                <input type="radio" name="xValue" value="-2" />
                <label for="xValue">-2</label>

                <input type="radio" name="xValue" value="-1.5" />
                <label for="xValue">-1.5</label>

                <input type="radio" name="xValue" value="-1" />
                <label for="xValue">-1</label>

                <input type="radio" name="xValue" value="-0.5" />
                <label for="xValue">-0.5</label>

                <input type="radio" name="xValue" value="0" />
                <label for="xValue">0</label>

                <input type="radio" name="xValue" value="0.5" />
                <label for="xValue">0.5</label>

                <input type="radio" name="xValue" value="1" />
                <label for="xValue">1</label>

                <input type="radio"  name="xValue" value="1.5" />
                <label for="xValue">1.5</label>

                <input type="radio" name="xValue" value="2" />
                <label for="xValue">2</label>
            </div>

            <div id="y_text">
                <div id="chosingValTextY"><label>Value of Y:</label></div>
                <input type="text" name="Y-input" id="Y-input" maxlength="6" placeholder="-3...3">
            </div>

            <div id="r_button">
                <div id="chosingValTextR"><label>Value of R:</label></div>
                <input type="hidden" id="r_val" name="r">
                <input type="button" class="r" name="R-button" value="1" onclick="saveR(this)">
                <input type="button" class="r" name="R-button" value="1.5" onclick="saveR(this)">
                <input type="button" class="r" name="R-button" value="2" onclick="saveR(this)">
                <input type="button"  class="r" name="R-button" value="2.5" onclick="saveR(this)">
                <input type="button"  class="r" name="R-button" value="3" onclick="saveR(this)">
            </div>

            <input type="submit" value="Answer" id="submit" class = "submit">
        </form>

        <form action="${pageContext.request.contextPath}/logout" method="post">
            <input type="submit" value="Logout" id="logout" class = "logout">
        </form>
    </div>

    <div id="man">
        <table id="historyTable" class="tab1">
            <thead>
            <tr>
                <th>User</th>
                <th>X</th>
                <th>Y</th>
                <th>R</th>
                <th>Result</th>
                <th>Executed at</th>
                <th>Execution time</th>
            </tr>
            </thead>
            <tbody>
            <%  String ker = null;
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("username".equals(cookie.getName())) {
                        ker = cookie.getValue();
                        request.setAttribute("usernameVar", ker);
                        break;
                    }
                }
            }
            %>
            <core:set var="userName" value="${usernameVar}" scope="page" />
            <core:forEach var="areaData" items="${data.areaDataList}">
                    <core:if test="${areaData.usr.username eq userName}">
                <tr>
                    <td>${areaData.usr.username}</td>
                    <td>${areaData.x}</td>
                    <td>${areaData.y}</td>
                    <td>${areaData.r}</td>
                    <td>${areaData.result ? "Hit / Success" : "Miss / Fail"}</td>
                    <td>${areaData.calculatedAt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))}</td>
                    <td>${areaData.calculationTime}</td>
                </tr>
                </core:if>
            </core:forEach>
            </tbody>
        </table>
        <div id = "resultsbody">
        </div>
    </div>
</div>
<script src="script/script.js"></script>
<script type="text/javascript">
    call();
</script>
</body>
</html>