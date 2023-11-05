<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="data" class="main.model.UserAreaDatas" scope="session" />
<%@ page import="java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html lang="ru-RU">
<head>
    <title>WEB LAB 2</title>
    <link rel="stylesheet" href="style/style.css">
    <meta charset="UTF-8">
</head>
<body>
<header>
    <img id="itmo_logo" src="https://itmo.ru/promo/itmo-logo-dark.svg">
    <h1 id="lab1">Лабораторная работа №2 Рахматов Нематджон P3233 вариант 5551</h1>
</header>
<div id="allBody">
    <div id="bodyA">
        <form action="${pageContext.request.contextPath}/adduser" method="post">

            <div id="login">
                <input type="text" name="username" id="username" minlength="3" maxlength="20" placeholder="username">
            </div>

            <div id="logpass">
                <input type="password" name="password" id="password" minlength="3" maxlength="20" placeholder="password">
            </div>

            <input type="submit" value="AddUser" id="addUser" class = "submit">
        </form>

    </div>
</div>
</body>
</html>