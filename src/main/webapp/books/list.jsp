<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 11/15/2021
  Time: 4:06 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Book Management</title>
</head>
<body>
<div align="center">
    <h2><a href="/Book">QUAN LY SACH</a></h2>
    <h2><a href="/Book?action=create">Them Sach Moi</a></h2>
    <form method="get">
        <h2>Tim Theo Ten Sach:</h2>
        <input type="text" name="name">
        <input type="submit" value="Tim Kiem">
    </form>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Price</th>
            <th>Description</th>
            <th>Category</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>

        <c:forEach items="${bookList}" var="book">
            <tr>
                <td>${book.id}</td>
                <td>${book.name}</td>
                <td style="text-align: center">${book.price}</td>
                <td>${book.description}</td>
                <td><c:forEach items="${book.getCategories()}" var="cate">
                    ${cate.name}<br>
                </c:forEach>
                </td>
                <td><a href="/Book?action=edit&id=${book.id}">Edit</a></td>
                <td><a href="/Book?action=delete&id=${book.id}">Delete</a></td>
            </tr>
        </c:forEach>

    </table>
</div>
</body>
</html>