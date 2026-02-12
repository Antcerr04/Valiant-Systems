<%--
  Created by IntelliJ IDEA.
  User: anton
  Date: 05/01/2026
  Time: 18:13
  To change this template use File | Settings | File Templates.
  This file contains code to show register form for the clients
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="WEB-INF/results/error.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Registrazione utente - Valiant System</title>
    <%@include file="WEB-INF/results/header.jsp" %>
    <link rel="stylesheet" href="css/index-style.css" type="text/css">
    <script src="javascript/formvalidate.js" defer></script>
    <script src="javascript/PasswordCriteria.js"></script>
    <script src="javascript/utility.js"></script>
    <style>
        .valid {
            color: green;
        }

        .invalid {
            color: red;
        }
    </style>
</head>
<body>
<c:if test="${utente != null}">
    <c:redirect url="index.jsp"/>
</c:if>
<div class="container container-alt">
    <a href="index.jsp" title="Vai alla homepage" style="position: relative">
        <img src="images/logo-form.png" alt="Logo Valiant Systems" id="form-logo">
    </a>

    <fieldset class="rounded">
        <legend style="z-index: -1;position: absolute;color: white;user-select: none;">Crea account</legend>
        <form action="Register" method="post" name="form" id="form-register">
            <h1>Crea account</h1>
            <label for="name">Nome</label>
            <input type="text" name="name" id="name" required
                   pattern="^[A-Za-zàèéìòùÀÈÉÌÒÙ \-']{2,30}$" title="Solo caratteri da 2 a 30" maxlength="30">
            <label for="surname">Cognome</label>
            <input type="text" name="surname" id="surname" required
                   pattern="^[A-Za-zàèéìòùÀÈÉÌÒÙ \-']{2,30}$" title="Solo caratteri da 2 a 30" maxlength="30">
            <label for="register-email">E-mail</label>
            <input type="email" name="email" maxlength="75" id="register-email" required oninput="validateEmail(this)">
            <div class="feedback-email" style="display: none">
                <p style="color: red">Email già registrata</p>
                <button type="button" style="margin-top: 2%;background-color: #359DE0; color: white; cursor: pointer;"
                        onclick="location.href='login.jsp'">Vai al login
                </button>
            </div>
            <label for="username">Username</label>
            <input type="text" name="username" id="username" pattern="^[a-zA-Z0-9._]{4,20}$" maxlength="20" title="Lo username deve contenere da 3 e 20 caratteri(lettere,numeri,punto o underscore" required oninput="validateUsername(this)">
            <p class="feedback-username" style="color: red; display: none">Username già esistente</p>
            <label for="registerPassword">Password</label>
            <input type="password" id="registerPassword" name="password"
                   required title="Almeno 8 caratteri, una maiuscola, un numero e un carattere speciale" maxlength="50">

            <div id="feedback" style="display: none">
                <p id="lenght" class="invalid">Almeno 8 caratteri</p>
                <p id="uppercase" class="invalid">Almeno una lettera maiuscola</p>
                <p id="number" class="invalid">Almeno un numero</p>
                <p id="special" class="invalid">Almeno un carattere speciale (!@#$...)</p>
            </div>

            <label for="regione">Scegli la regione:</label>
            <select id="regione" name="regione" required>
                <option value="">--- Seleziona una regione ---</option>
            </select>

            <label for="provincia">Scegli la provincia:</label>
            <select id="provincia" name="provincia" required>
                <option value="">--- Seleziona una provincia ---</option>
            </select>
            <label for="via">Indirizzo (Via/Corso/Piazza...)</label>
            <input type="text" name="via" id="via" required pattern="^[A-Za-zàèéìòùÀÈÉÌÒÙ \-'.]{2,50}$" title="Inserisci una via">
            <label for="house-number">Numero civico</label>
            <input type="text" name="house-number" id="house-number" required pattern="^\d{1,4}$"
                   title="Numero civico massimo 4 cifre" maxlength="4">
            <label for="cap">CAP</label>
            <input type="text" name="cap" id="cap" required pattern="^\d{5}$" title="Inserisci CAP di 5 numeri > 10000 e <99999" maxlength="5">
            <label for="city">Città</label>
            <input type="text" name="city" id="city" required pattern="^[A-Za-zàèéìòùÀÈÉÌÒÙ \-']{2,20}$" title="Inserisci una città con lunghezza compresa tra 2 e 20 caratteri">

            <input type="submit" value="Registrati">
        </form>
    </fieldset>
</div>


</body>
</html>
