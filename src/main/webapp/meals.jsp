<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<html>
<head>
    <title>Meals</title>
</head>

<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
<table>
    <tr>
        <th>Description</th>
        <th>Time</th>
        <th>Calories</th>
    </tr>
    <c:forEach items="${mealWithExceeds}" var="meal">
        <tr>
            <td>${meal.description}</td>
            <td>${localDateTimeFormat.parse(meal.dateTime)}</td>
            <c:if test="${meal.exceed}">
                <td bgcolor="red">${meal.calories}</td>
            </c:if>
            <c:if test="${!meal.exceed}">
                <td bgcolor="green">${meal.calories}</td>
            </c:if>
        </tr>
    </c:forEach>
</table>
</body>
</html>
