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
<c:if test="${sessionScope.role != 5}">
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
            <div class="col-6">
                <h5 class="mb-3" style="border-bottom: inset">Appointments</h5>
                <table class="table table-hover" style="background-color: #358597">
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Doctor</th>
                        <th scope="col">Symptoms</th>
                        <th scope="col">Diagnosis</th>
                    </tr>
                    </thead>
                    <tbody>
                    <jsp:useBean id="appointments" scope="request" type="java.util.List"/>
                    <c:forEach var="appointment" items="${appointments}">
                        <tr>
                            <th scope="row">${appointment.id}</th>
                            <td>${appointment.doctor.name} </td>
                            <td><ul>
                                <c:forEach var="symptom" items="${appointment.patient.symptoms}">
                                    <li>${symptom.name}</li>
                                </c:forEach>
                            </ul>
                            </td>
                            <td>
                                    ${appointment.diagnosis}
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

            </div>
            <div class="col-6">
                <h5 class="mb-3" style="border-bottom: inset">Transactions</h5>
                <table class="table table-hover" style="background-color: #F4A896">
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Type</th>
                        <th scope="col">Price</th>
                        <th scope="col"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <jsp:useBean id="transactions" scope="request" type="java.util.List"/>
                    <c:forEach var="transaction" items="${transactions}">
                        <tr>
                            <th scope="row">${transaction.id}</th>
                            <td>${transaction.transaction_type} </td>
                            <td> ${transaction.price}
                            </td>
                            <td>
                                    ${transaction.created_at}
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="row">
            <div class="col-6" style="background-color: #F4A896">
                <h5 class="mb-3" style="border-bottom: inset">Medicaments</h5>
                <ul>
                <jsp:useBean id="medicaments" scope="request" type="java.util.List"/>
                <c:forEach var="medicament" items="${medicaments}">
                    <li>${medicament.name}</li>
                </c:forEach>
                </ul>
            </div>
            <div class="col-6">
                <div class="form-group">
                    <label for="comment">Comment:</label>
                    <textarea class="form-control" rows="5" id="comment"></textarea>
                </div>
            </div>
        </div>
    </main>

</div>
</body>
</html>
