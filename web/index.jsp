<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Login Page</title>

    <!--Bootsrap 4 CDN-->
    <script src="<c:url value="/js/jquery.js"/>"></script>

    <link rel="stylesheet" href="<c:url value = "/css/libs/bootstrap.css" />" >

    <!--Fontawesome CDN-->
    <link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
<%--    <link rel="stylesheet" href="<c:url value="/css/libs/fontawsome.css"/>" >--%>

    <script src="<c:url value="/js/bootstrap.js"/>"></script>
    <!--Custom styles-->
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/login.css"/>">
</head>
<body>
<div class="container">
    <div class="d-flex justify-content-center h-100">
        <div class="card">
            <div class="card-header">
                <h3>Sign In</h3>
            </div>
            <div class="card-body">
                <form method="post" action="<c:url value="/login"/>">
                    <div class="input-group form-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text"><i class="fa fa-user"></i></span>
                        </div>
                        <input type="text" class="form-control" name="name" placeholder="Name" required>
                    </div>
                    <div class="input-group form-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text"><i class="fa fa-user"></i></span>
                        </div>
                        <input type="text" class="form-control" name="last_name" placeholder="Last Name" required>
                    </div>
                    <div class="input-group form-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text"><i class="fa fa-key"></i></span>
                        </div>
                        <input type="password" class="form-control" name="password" placeholder="password" required>
                    </div>
                    <div class="input-group form-group">
                    <select class="mdb-select md-form colorful-select dropdown-warning" name="role">
                        <option value = "5">Patient</option>
                        <option value = "1">Receptionist</option>
                        <option value="2">Doctor</option>
                        <option value="3">Pharmacist</option>
                        <option value="4">Accountant</option>
                    </select>
                    </div>
                    <div class="row align-items-center remember">
                        <input type="checkbox">Remember Me
                    </div>
                    <div class="form-group">
                        <input type="submit" value="Login" class="btn float-right login_btn">
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
