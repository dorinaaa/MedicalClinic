<%--
  Created by IntelliJ IDEA.
  User: dorina
  Date: 2/2/2020
  Time: 2:27 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="<c:url value="/js/jquery.js"/>"></script>

    <link rel="stylesheet" href="<c:url value = "/css/libs/bootstrap.css" />" >

    <!--Fontawesome CDN-->
    <link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
    <%--    <link rel="stylesheet" href="<c:url value="/css/libs/fontawsome.css"/>" >--%>

    <script src="<c:url value="/js/bootstrap.js"/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/receptionist.css"/>">
    <style>
        html,body{
            background-image: <c:url value="/doctor.jpg" />;
            background-size: cover;
            background-repeat: no-repeat;
            height: 100%;
            font-family: 'Numans', sans-serif;
        }
    </style>
</head>
<body>
<c:if test="${sessionScope.role != 2}">
    <c:redirect url="/"/>
</c:if>
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
    <main>
        <div class="row">
            <div class="col-8 offset-1">
                <h5 class="mb-3" style="border-bottom: inset">Appointments</h5>
                <table class="table table-hover" style="background-color: #358597">
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Patient</th>
                        <th scope="col">Symptoms</th>
                        <th scope="col">Diagnosis</th>
                        <th scope="col">Next appointment</th>
                        <th scope="col">Medicament</th>
                        <th scope="col">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <jsp:useBean id="appointments" scope="request" type="java.util.List"/>
                    <c:forEach var="appointment" items="${appointments}">
                        <tr>
                            <form method="post" action="<c:url value="/updateAppointment" />">
                                <input hidden name="id" value="${appointment.id}">
                            <th scope="row">${appointment.id}</th>
                            <td>${appointment.patient.name} </td>
                                <td><ul>
                                    <c:forEach var="symptom" items="${appointment.patient.symptoms}">
                                        <li>${symptom.name}</li>
                                    </c:forEach>
                                </ul>
                                </td>
                                <td>
                                    <input type="text" name="diagnosis" value="${appointment.diagnosis}" placeholder="Change diagnosis">
                                </td>
                            <td>
                                <input type="date" name="next_app">
                            </td>
                                <td>
                                    <select name="medicament">
                                        <jsp:useBean id="medicaments" scope="request" type="java.util.List"/>
                                        <c:forEach var="medicament" items="${medicaments}">
                                            <option value="${medicament.id}">${medicament.name}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td><button type="submit" class="btn" style="background-color: #F4A896">Save</button></td>
                            </form>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

        </div>
    </main>

</div>
</body>
</html>
