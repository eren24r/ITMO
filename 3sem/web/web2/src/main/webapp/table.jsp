<%@page contentType="text/html" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="data" scope="session" class="main.model.UserAreaDatas"/>
<%@ page import="java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="style/style.css">
    <title>Result</title>
</head>
<body>
<div id="calculator" style="width: 500px; height: 500px;"></div>
<script src="https://www.desmos.com/api/v1.8/calculator.js?apiKey=dcb31709b452b1cf9dc26972add0fda6"></script>

<script src="script/graph.js"></script>
<c:if test="${data.lastResult != null}">
    <script type="text/javascript">
        drawPoint(${data.lastResult.x}, ${data.lastResult.y}, ${data.lastResult.r});
    </script>
</c:if>

</div>

<table>
    <thead>
    <tr>
        <th>X</th>
        <th>Y</th>
        <th>R</th>
        <th>Result</th>
        <th>Executed at</th>
        <th>Execution time</th>
    </tr>
    </thead>
    <tbody>
    <c:if test="${data.lastResult != null}">
        <tr>
            <td>${data.lastResult.x}</td>
            <td>${data.lastResult.y}</td>
            <td>${data.lastResult.r}</td>
            <td>${data.lastResult.result ? "Попадание" : "Промах"}</td>
            <td>${data.lastResult.calculatedAt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))}</td>
            <td>${data.lastResult.calculationTime}</td>
        </tr>
    </c:if>
    </tbody>
</table>

<br>
<div style="text-align: center;">
    <a href="${pageContext.request.contextPath}">Back Menu</a>
</div>
</body>
</html>