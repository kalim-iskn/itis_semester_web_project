<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="templates/header.jsp"%>

<main class="text-center">
    <div class="main-block centered-block">
        <div class="header">
            Регистрация
        </div>
        <div class="content">
            <%@include file="templates/errors.jsp"%>
            <form class="form" method="post">
                <input type="hidden" value="${csrf}" name="_csrf">
                <div class="form-group">
                    <label>Email</label>
                    <input type="email" value="${email}" name="email" class="form-control" required>
                </div>
                <div class="form-group">
                    <label>Имя</label>
                    <input type="text" name="name" value="${name}" class="form-control" required>
                </div>
                <div class="form-group">
                    <label>Фамилия</label>
                    <input type="text" value="${surname}" name="surname" class="form-control" required>
                </div>
                <div class="form-group">
                    <label>Город</label>
                    <select name="city" class="form-control" required>
                        <option value="">Выберите город</option>
                        <c:forEach items="${cities}" var="lsCity">
                            <option <c:if test="${lsCity == city}">selected</c:if>>
                                ${lsCity}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label>Пароль</label>
                    <input type="password" name="password" class="form-control" required>
                </div>
                <div class="form-group">
                    <label>Повторите пароль</label>
                    <input type="password" name="re_password" class="form-control" required>
                </div>
                <input type="submit" value="Войти" class="btn main-btn">
            </form>
        </div>
    </div>
</main>

<%@include file="templates/footer.jsp"%>
