<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="templates/header.jsp"%>

<main>
    <div class="main-block">
        <div class="header">
            Редактирование информации о себе
        </div>
        <div class="content">
            <%@include file="templates/errors.jsp"%>
            <c:if test="${isSuccess}">
                <div class="alert alert-success">
                    Изменения успешно применены
                </div>
            </c:if>
            <form method="post">
                <input type="hidden" value="${csrf}" name="_csrf">
                <div class="form-group">
                    <label>Имя</label>
                    <input type="text" value="${user.getName()}" name="name" class="form-control">
                </div>
                <div class="form-group">
                    <label>Фамилия</label>
                    <input type="text" value="${user.getSurname()}" name="surname" class="form-control">
                </div>
                <div class="form-group">
                    <label>Город</label>
                    <select class="form-control" name="city">
                        <c:forEach items="${cities}" var="city">
                        <option <c:if test="${user.getCity() == city}">selected</c:if> >${city}</option>
                        </c:forEach>
                    </select>
                </div>
                <input type="submit" value="Редактировать" class="btn main-btn">
            </form>
        </div>
    </div>
</main>

<%@include file="templates/footer.jsp"%>
