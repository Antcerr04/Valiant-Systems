<%--
  Created by IntelliJ IDEA.
  User: anton
  Date: 02/01/2026
  Time: 22:45
  To change this template use File | Settings | File Templates.
  This file contains the code to show the position of the physical shop on the google map
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Dove Siamo - Valiant Systems</title>
    <%@include file="WEB-INF/results/header.jsp" %>

    <link rel="stylesheet" href="./css/index-style.css" type="text/css">
</head>
<body>
<%@include file="WEB-INF/navbar.jsp" %>

<div class="flex-wrapper">
    <div class="container-flex" style="padding: 50px 0; justify-content: center">
        <div class="single-item-map">
            <iframe title="posizione-Valiant Systems" id="map"
                    src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d24171.926606283785!2d14.7914752!3d40.7732224!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x133bc5c7456b88bd%3A0x80bab96149d2993d!2sUniversit%C3%A0%20degli%20Studi%20di%20Salerno!5e0!3m2!1sit!2sit!4v1746785771586!5m2!1sit!2sit"
                    width="600" height="450" style="border: 0;" allowfullscreen="" loading="lazy"
                    referrerpolicy="no-referrer-when-downgrade"></iframe>
        </div>
        <div class="single-item-map map-col2" style="text-align: left">
            <h1>Dove trovarci:</h1>
            <h4>Valiant Systems</h4>
            <p>Via Giovanni Paolo II, 132</p>
            <p>84084</p>
            <p>Fisciano (SA)</p>
            <p><span class="material-icons middle">mail</span> support@ValiantSystems.com</p>
            <p><span class="material-icons middle">call</span>1234-98765</p>

        </div>
    </div>
    <%@include file="WEB-INF/footer.jsp" %>
</div>

</body>
</html>
