<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="templates/header.jsp"%>

<main>
    <div class="row">
        <div class="col-md-12 announcement-name">
            <h2>${announcement.getName()}</h2>
            Дата публикации: ${announcement.getFormattedCreatedAt()}
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div id="photos" class="carousel slide" data-ride="carousel">

                <!-- The slideshow -->
                <div class="carousel-inner text-center">
                    <div class="carousel-item active">
                        <img src="${pageContext.request.contextPath}/pictures/${announcement.getMainPicture()}">
                    </div>
                    <c:if test="${isHasExtraPictures}">
                        <c:forEach items="${announcement.getPicturesArray()}" var="picture">
                            <div class="carousel-item">
                                <img src="${pageContext.request.contextPath}/pictures/${picture}">
                            </div>
                        </c:forEach>
                    </c:if>
                </div>

                <!-- Left and right controls -->
                <a class="carousel-control-prev" href="#photos" data-slide="prev">
                    <span class="carousel-control-prev-icon"></span>
                </a>
                <a class="carousel-control-next" href="#photos" data-slide="next">
                    <span class="carousel-control-next-icon"></span>
                </a>

            </div>
        </div>
        <div class="col-md-3">

        </div>
    </div>
    <br>
    <h3>Описание</h3>
    <div class="row">
        <div class="col-md-3">
            <div class="main-block">
                <div class="content">
                    <b>Информация о пользователе</b>
                    <hr>
                    <h6 class="without-margin-bottom">${user.getName()} ${user.getSurname()}</h6>
                    <span class="small">На сервисе с ${user.getFormattedRegisteredAt()}</span>
                    <br>
                    <a href="mailto:${user.getEmail()}" class="mobile-block">
                        ${user.getEmail()}
                    </a>
                </div>
            </div>
        </div>
        <div class="col-md-9">
            <div class="main-block">
                <div class="content-padding">
                    <p>
                        <b>Цена (в рублях):</b> ${announcement.getPrice()}<br>
                        <b>Категория:</b> ${announcement.getCategory()}<br>
                        <b>Город:</b> ${announcement.getCity()}<br>
                        <b>Тип:</b>
                        <c:choose>
                            <c:when test="${announcement.isNew()}">
                                Новая вещь
                            </c:when>
                            <c:otherwise>
                                Б/у вещь
                            </c:otherwise>
                        </c:choose>
                    </p>
                    ${announcement.getDescription()}
                </div>
            </div>
        </div>
    </div>
    <br><br>
</main>

<%@include file="templates/footer.jsp"%>