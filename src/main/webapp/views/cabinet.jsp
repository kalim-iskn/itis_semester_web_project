<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="templates/header.jsp"%>
<main>
    <div class="main-block">
        <div class="header">
            Мой кабинет
        </div>
        <div class="content">
            <a href="main/announcement/add" class="btn main-btn">
                Добавить объявление
            </a>
            <a href="main/edit" class="btn btn-info">
                Редактировать информацию о себе
            </a>
            <hr>
            <h3>Мои объявления</h3>
            <c:forEach items="${userAnnouncements}" var="announcement">
                <a href="announcement?id=${announcement.getId()}">
                    <hr>
                    ${announcement.getName()} - от ${announcement.getFormattedCreatedAt()}
                    <br>
                    <a href="main/announcement/edit?id=${announcement.getId()}" class="btn btn-info">
                        Редактировать
                    </a>
                    <a href="main/announcement/delete?id=${announcement.getId()}" class="btn btn-danger">
                        Удалить
                    </a>
                </a>
            </c:forEach>
        </div>
    </div>
</main>

<%@include file="templates/footer.jsp"%>
