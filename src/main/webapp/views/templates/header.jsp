<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <title>Доска объявлений</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;600&display=swap" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/style.css" rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
<header>
    <nav class="navbar navbar-expand-sm">
        <ul class="navbar nav mr-auto">
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}" class="nav-link">Главная</a>
            </li>
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/search" class="nav-link">Поиск</a>
            </li>
        </ul>
        <a href="${pageContext.request.contextPath}">
            <img src="${pageContext.request.contextPath}/assets/img/semestrovka.png">
        </a>
        <ul class="navbar nav ml-auto">
            <c:choose>
                <c:when test="${isAuthorize}">
                    <li class="nav-item">
                        <a href="${pageContext.request.contextPath}/main" class="nav-link">Личный кабинет</a>
                    </li>
                    <li class="nav-item">
                        <a href="${pageContext.request.contextPath}/main/logout" class="btn main-btn">Выход</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="nav-item">
                        <a href="${pageContext.request.contextPath}/login" class="nav-link">Вход</a>
                    </li>
                    <li class="nav-item">
                        <a href="${pageContext.request.contextPath}/registration" class="btn main-btn">Регистрация</a>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>
    </nav>

        <div class="search-block">
            <form action="${pageContext.request.contextPath}/search" method="get" class="form-inline">
                <div class="row" style="width: 100%">
                    <div class="col-md-5 form-col">
                        <input type="search" name="q" value="${q}" class="form-control" placeholder="Поиск по объявлениям">
                    </div>
                    <div class="col-md-2 form-col">
                        <select name="searchCity" class="form-control">
                            <option value="">Выберите город</option>
                            <c:forEach items="${cities}" var="city">
                                <option <c:if test="${searchCity == city}">selected</c:if> >${city}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-3 form-col">
                        <select name="searchCategory" class="form-control">
                            <option value="">Выберите категорию</option>
                            <c:forEach items="${categories}" var="category">
                                <option <c:if test="${searchCategory == category}">selected</c:if>>
                                        ${category}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-2 form-col">
                        <input type="submit" value="Поиск" class="btn main-btn">
                    </div>
                </div>
                <a href="#" class="extend-search-btn">Расширенный поиск</a>
                <div class="extend-search">
                    <div class="row">
                        <div class="col-md-4">
                            <h6>Цена</h6>
                            <input type="text" class="form-control" value="${price_from}" name="price_from" placeholder="От">
                            <input type="text" class="form-control bottom-input" value="${price_to}" name="price_to" placeholder="До">
                        </div>
                    </div>
                </div>
            </form>
        </div>

</header>