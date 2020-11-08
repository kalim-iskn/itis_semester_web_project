<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${isErrors}">
    <div class="alert alert-danger">
        <c:forEach items="${errors}" var="item">
            ${item}<br>
        </c:forEach>
    </div>
</c:if>