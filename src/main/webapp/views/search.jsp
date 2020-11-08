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
    <div class="row">
        <c:forEach items="${announcements}" var="announcement">
            <a class="col-md-4 announcement-block" href="announcement?id=${announcement.getId()}">
                <div class="img" style="background-image: url(${pageContext.request.contextPath}/pictures/${announcement.getMainPicture()})">
                    <div class="category-name">
                            ${announcement.getCategory()}
                    </div>
                </div>
                <div class="content">
                    <h5>${announcement.getName()}</h5>
                    <div class="text">
                            ${announcement.getDescription()}
                    </div>
                </div>
            </a>
        </c:forEach>
    </div>
</main>

<%@include file="templates/footer.jsp"%>
