<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="templates/header.jsp"%>

<main>
    <h3 class="category-name-header">Объявления</h3>
    <c:if test="${isNotFound}">
        <div class="alert alert-danger">
            К сожалению, ничего не найдено
        </div>
    </c:if>
    <div class="row" id="search">
        <%@include file="announcements.jsp"%>
    </div>
    <br>
    <c:if test="${!isNotFound}">
        <div class="text-center">
            <button type="button" onclick="showMore()" class="btn main-btn load_more">Показать еще</button>
        </div>
    </c:if>
</main>

<script>
    let offset = ${searchOffset};
    let link = "get_announcements?q=${q}&searchCity=${searchCity}&searchCategory=${searchCategory}&price_from=${price_from}&price_to=${price_to}";
</script>
<script src="${pageContext.request.contextPath}/assets/js/show-more-announcements.js"></script>

<%@include file="templates/footer.jsp"%>
