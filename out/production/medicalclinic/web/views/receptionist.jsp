<%--
  Created by IntelliJ IDEA.
  User: dorina
  Date: 2/2/2020
  Time: 2:27 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${sessionScope.role != 1}">
    <c:redirect url="/"/>
</c:if>
<html>
<head>
    <script src="<c:url value="/js/jquery.js"/>"></script>

    <link rel="stylesheet" href="<c:url value = "/css/libs/bootstrap.css" />" >

    <!--Fontawesome CDN-->
    <link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
    <%--    <link rel="stylesheet" href="<c:url value="/css/libs/fontawsome.css"/>" >--%>

    <script src="<c:url value="/js/bootstrap.js"/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/receptionist.css"/>">

    <head>
        <style>
            html,body{
                background-image: <c:url value="/blur-hospital.jpg" />;
                background-size: cover;
                background-repeat: no-repeat;
                height: 100%;
                font-family: 'Numans', sans-serif;
            }
        </style>
    </head>
</head>
<body>
<%--<jsp:include page="header.jsp"/>--%>
<header>
    <div class="row mb-5 p-2" style="background-color: #35859785">
        <div class="offset-10">
            <form method="get" action="<c:url value="/logout"/>" style="margin: unset">
                <input type="hidden" value="logout">
                <button class="btn btn-block" style="background-color: #F4A896"><span><i class="fa fa-user"></i></span>Logout</button>
            </form>
        </div>
    </div>
</header>

<div class="container">
    <c:if test="${sessionScope.appointment != null}" >
        <jsp:useBean id="appointment" scope="session" type="models.beans.Appointment"/>
        <div class="row p-3 m-3" style="background-color: antiquewhite">
            <h4>Appointment scheduled at ${appointment.scheduled_at} with Dr. ${appointment.doctor.name} ${appointment.doctor.lastName}</h4>
        </div>
    </c:if>
    <main>
        <div class="row">
                    <div class="col-7">
                        <div class="card">
                            <div class="card-header">
                                <h3>Register new Appointment</h3>
                            </div>
                            <div class="card-body">
                                <form method="post" action="<c:url value="/registerAppointment"/>">
                                    <div class="input-group form-group">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text"><i class="fa fa-user"></i></span>
                                        </div>
                                        <input type="text" class="form-control" name="name" placeholder="Patient Name">
                                    </div>
                                    <div class="input-group form-group">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text"><i class="fa fa-user"></i></span>
                                        </div>
                                        <input type="text" class="form-control" name="last_name" placeholder="Patient Last Name">
                                    </div>
                                    <div class="input-group form-group">
                                        <input type="number" class="form-control" name="age" placeholder="Age">
                                    </div>
                                    <div class="input-group form-group" style="margin-bottom: 20px">
                                        <select name="gender">
                                            <option selected value="Gender">Gender</option>
                                            <jsp:useBean id="genders" scope="request" type="java.util.List"/>
                                            <c:forEach var="gender" items="${genders}">
                                                <option value="${gender.id}">${gender.gender}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="input-group form-group" style="margin-bottom: 40px">
                                        <select class="mdb-select md-form colorful-select dropdown-warning" name="emergency">
                                            <option selected value="Gender">Emergency</option>
                                            <jsp:useBean id="emergencies" scope="request" type="java.util.List"/>
                                            <c:forEach var="emergency" items="${emergencies}">
                                                <option value="${emergency.id}">${emergency.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="input-group form-group" style="margin-bottom:40px">
                                        <select class="mdb-select md-form colorful-select dropdown-warning" name="s1">
                                            <option selected value="Symptoms"> Symptoms</option>
                                            <jsp:useBean id="symptoms" scope="request" type="java.util.List"/>
                                            <c:forEach var="symptom" items="${symptoms}">
                                                <option value="${symptom.id}">${symptom.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="input-group form-group" style="margin-bottom: 40px">
                                        <select class="mdb-select md-form colorful-select dropdown-warning" name="s2">
                                            <option selected value="Symptoms"> Symptoms</option>
                                            <c:forEach var="symptom" items="${symptoms}">
                                                <option value="${symptom.id}">${symptom.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="input-group form-group" style="margin-bottom: 40px">
                                        <select class="mdb-select md-form colorful-select dropdown-warning" name="s3">
                                            <option selected value="Symptoms"> Symptoms</option>
                                            <c:forEach var="symptom" items="${symptoms}">
                                                <option value="${symptom.id}">${symptom.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="input-group form-group">
                                        <input type="number" class="form-control" name="price" placeholder="Registration Fee">
                                    </div>
                                    <div class="form-group">
                                        <input type="submit" value="Register" class="btn float-right login_btn">
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <div class="col-5" id="appointments">
                        <h5 style="border-bottom: inset">Today's appointments</h5>
                        <table class="table table-hover" style="background-color: #358597">
                            <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">Patient</th>
                                <th scope="col">Doctor</th>
                                <th scope="col">Scheduled At</th>
                            </tr>
                            </thead>
                            <tbody>
                            <jsp:useBean id="appointments" scope="request" type="java.util.List"/>
                            <c:forEach var="appointment" items="${appointments}">
                                <tr>
                                    <th scope="row">${appointment.id}</th>
                                    <td>${appointment.patient.name} </td>
                                    <td>${appointment.doctor.name}</td>
                                    <td>${appointment.scheduled_at}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>

                    </div>
                </div>
    </main>
</div>