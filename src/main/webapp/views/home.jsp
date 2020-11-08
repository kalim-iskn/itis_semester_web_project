<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="templates/header.jsp"%>

<main>
    <c:forEach items="${announcements}" var="category">
        <c:if test="${category.getValue() != null}">
            <h3 class="category-name-header">${category.getKey()}</h3>
            <div class="row">
                <c:forEach items="${category.getValue()}" var="announcement">
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
        </c:if>
    </c:forEach>
</main>

<%@include file="templates/footer.jsp"%>