<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="templates/header.jsp"%>

<main>
    <div class="main-block">
        <div class="header">
            ${type}
        </div>
        <div class="content">
            <%@include file="templates/errors.jsp"%>
            <c:if test="${isSuccess == true}">
                <div class="alert alert-success">
                    ${successText}
                </div>
            </c:if>
            <form class="form" method="post" enctype="multipart/form-data">
                <input type="hidden" value="${csrf}" name="_csrf">
                <div class="form-group">
                    <label>Название объявления</label>
                    <input type="text" name="name" value="${name}" class="form-control" required>
                </div>
                <div class="form-group">
                    <label>Описание объявления</label>
                    <textarea rows="10" name="description" class="form-control" required>${description}</textarea>
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
                    <label>Категория</label>
                    <select name="category" class="form-control" required>
                        <option value="">Выберите категорию</option>
                        <c:forEach items="${categories}" var="lsCategory">
                            <option <c:if test="${lsCategory == category}">selected</c:if>>
                                    ${lsCategory}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label>Тип</label>
                    <select name="is_new" class="form-control" required>
                        <option value="">Выберите тип</option>
                        <option value="1" <c:if test="${is_new == '1'}">selected</c:if>>Новая вещь</option>
                        <option value="0" <c:if test="${is_new == '0'}">selected</c:if>>Б/у вещь</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>Цена (в рублях)</label>
                    <input type="text" name="price" value="${price}" class="form-control" required>
                </div>
                <div class="form-group">
                    <label>
                        <c:choose>
                            <c:when test="${is_edit}">
                                Сменить главную фотографию
                            </c:when>
                            <c:otherwise>
                                Главная фотография
                            </c:otherwise>
                        </c:choose>
                    </label>
                    <input type="file" name="main_picture" class="form-control" <c:if test="${!is_edit}">required</c:if>>
                    <c:if test="${is_edit}">
                        <br>
                        <h5>Текущая главная фотография:</h5>
                        <img src="${pageContext.request.contextPath}/pictures/${main_picture}" style="max-width: 30%">
                    </c:if>
                </div>
                <div class="form-group">
                    <label>Остальные фотографии (не более 5)</label>
                    <input type="file" name="pictures[]" class="form-control" multiple>
                    <c:if test="${is_edit}">
                        <br>
                        <h5>Текущие фотографии:</h5>
                        <c:forEach items="${pictures}" var="picture">
                            <img src="${pageContext.request.contextPath}/pictures/${picture}" style="max-width: 30%">
                        </c:forEach>
                    </c:if>
                </div>
                <input type="submit" class="btn main-btn" value="Отправить">
            </form>
        </div>
    </div>
</main>

<%@include file="templates/footer.jsp"%>
