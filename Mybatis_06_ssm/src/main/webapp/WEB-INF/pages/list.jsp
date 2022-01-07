<%--
  Created by IntelliJ IDEA.
  User: 12468
  Date: 2022/1/7
  Time: 18:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>员工列表</title>
</head>
<body>
    <h1>这是员工列表</h1>
    <table>
        <tr>
            <td>id</td>
            <td>lastName</td>
            <td>gender</td>
            <td>email</td>
        </tr>
        <c:forEach items="${emps}" var="emp">
            <tr>
                <td>${emp.id}</td>
                <td>${emp.lastName}</td>
                <td>${emp.gender}</td>
                <td>${emp.email}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
