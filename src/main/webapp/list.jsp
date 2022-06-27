<%--
  Created by IntelliJ IDEA.
  User: PC
  Date: 26/06/2022
  Time: 6:44 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" integrity="sha512-9usAa10IRO0HhonpyAIVpjrylPvoDwiPUiKdWk5t3PyolY1cOd4DSE0Ga+ri4AuTroPR5aQvXU9xC6qOPnzFeg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>
<div class="container">
    <div class="row" style="margin: 50px 0">
        <div class="col-sm-6">
            <a href="/homepage?action=create" class="btn btn-primary"><i class="fa-solid fa-plus"></i> Add New Student</a>
        </div>
        <div class="col-sm-6">
            <form method="post" action="/homepage?action=search">
                <input type="text" class="form-control" placeholder="Search" id="search" name="search" style="width: 50%;display: inline-block">
                <button type="submit" class="btn btn-primary"><i class="fa-solid fa-magnifying-glass"></i> Search</button>
            </form>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-12" style="border: 1px solid black">
            <p style="margin-top: 20px;">
                <i class="fa-solid fa-list"></i>
                Student List
            </p>
            <table class="table table-bordered table-hover">
                <thead>
                <tr>
                    <th>#</th>
                    <th>Name</th>
                    <th>Date of Birth</th>
                    <th>Phone Number</th>
                    <th>Email</th>
                    <th>Address</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestScope['studentList']}" var="item">
                    <tr>
                        <td>${item.getId()}</td>
                        <td>${item.getName()}</td>
                        <td>${item.getDob()}</td>
                        <td>${item.getPhoneNumber()}</td>
                        <td>${item.getEmail()}</td>
                        <td>${item.getAddress()}</td>
                        <c:forEach items="${requestScope['classroomList']}" var="item1">
                            <c:if test="${item.getIdClass() == item1.getId()}">
                                <td>${item1.getClassName()}</td>
                            </c:if>
                        </c:forEach>
                        <td>
                            <a href="/homepage?action=edit&id=${item.getId()}" class="btn btn-success"><i class="fa-solid fa-pen-to-square"></i></a>
                            <a href="javascript: void(0);" onclick="active(${item.getId()})" class="btn btn-danger"><i class="fa-solid fa-trash-can"></i></a>
                        </td>
                    </tr>

                </c:forEach>
                </tbody>
            </table>
            <ul>
                <c:forEach items="${requestScope['errors']}" var="item">
                    <li>${item}</li>
                </c:forEach>
            </ul>
            <h4>
                <c:if test="${requestScope['success'] != null}" >
                    ${requestScope['success']}
                </c:if>
            </h4>
        </div>
    </div>
</div>
<script>
    function active(id){
        if (confirm("Do You Delete!") == true) {
            location.href ='/homepage?action=delete&id=' + id;
        }
    }
</script>
</body>
</html>
