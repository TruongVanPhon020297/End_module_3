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
    <div class="row">
        <div class="col-sm-12">
            <h3>Add New Student</h3>
            <form method="post">
                <c:forEach items="${requestScope['studentList']}" var="item">
                    <div class="form-group">
                        <label for="name">Name</label>
                        <input type="text" value="${item.getName()}" required class="form-control" id="name" placeholder="Enter Name" name="student_name">
                    </div>
                    <div class="form-group">
                        <label for="email">Email:</label>
                        <input type="email" value="${item.getEmail()}" required class="form-control" id="email" placeholder="Enter Email" name="email">
                    </div>
                    <div class="form-group">
                        <label for="quantity">Date of Birth:</label>
                        <input type="date" value="${item.getDob()}" required class="form-control" id="quantity" name="dob">
                    </div>
                    <div class="form-group">
                        <label for="color">Address:</label>
                        <input type="text" value="${item.getAddress()}"  required class="form-control" id="color" placeholder="Enter Address" name="address">
                    </div>
                    <div class="form-group">
                        <label for="description">Phone Number:</label>
                        <input type="text" value="${item.getPhoneNumber()}" required class="form-control" id="description" name = "phone_number" placeholder="Enter Phone Number">
                    </div>
                    <div class="form-group">
                        <label for="inputState">Category:</label>
                        <select id="inputState" class="form-control" name="class_id">
                            <c:forEach items="${requestScope['classroomList']}" var="item1">
                                <c:choose>
                                    <c:when test="${item.getIdClass() == item1.getId()}">
                                        ...<option value="${item.getIdClass()}" selected>${item1.getClassName()}</option>
                                    </c:when>
                                    <c:when test="${item.getIdClass() != item1.getId()}">
                                        ...<option value="${item1.getId()}">${item1.getClassName()}</option>
                                    </c:when>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </div>
                </c:forEach>
                <button type="submit" class="btn btn-primary">Edit</button>
                <a href="/homepage" class="btn btn-primary">Back</a>
            </form>
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
</body>
</html>
