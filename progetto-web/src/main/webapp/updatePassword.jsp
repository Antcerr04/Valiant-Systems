<%--
  Created by IntelliJ IDEA.
  User: anton
  Date: 21/01/2026
  Time: 21:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="WEB-INF/results/error.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Modifica Password - Valiant System</title>
  <%@include file="WEB-INF/results/header.jsp" %>
  <link rel="stylesheet" href="css/index-style.css" type="text/css">

  <style>
    .valid { color: green; }
    .invalid { color: red; }

    .container-alt { margin-top: 50px; margin-bottom: 50px; }
    label { font-weight: bold; display: block; margin-top: 15px; }
    input[type=password] { width: 100%; padding: 10px; margin-top: 5px; box-sizing: border-box; }
    #form-updatePassword h1 { text-align: center; margin-bottom: 20px; }
  </style>
</head>
<body>

<%-- Se l'utente NON è loggato, non può cambiare password: reindirizza alla home --%>
<c:if test="${utente == null}">
  <c:redirect url="index.jsp"/>
</c:if>

<div class="container container-alt">
  <a href="index.jsp" title="Vai alla homepage">
    <img src="images/logo-form.png" alt="Logo Valiant Systems" id="form-logo" style="display: block; margin: 0 auto;">
  </a>

  <fieldset class="rounded">

    <legend style="display: none;">Modifica Password</legend>


    <form action="UpdatePassword" method="post" name="form" id="form-updatePassword">
      <h1>Modifica Password</h1>

      <label for="actualPassword">Password attuale</label>
      <input type="password" name="actualPassword" id="actualPassword"
             required placeholder="Inserisci la password corrente" maxlength="50">

      <label for="registerPassword" oninput="initPasswordValidation()">Nuova password</label>
      <input type="password" id="registerPassword" name="registerPassword"
             required title="Almeno 8 caratteri, una maiuscola, un numero e un carattere speciale"
             maxlength="50" placeholder="Inserisci la nuova password">

      <div id="feedback" style="display: none; padding: 10px; background: #f9f9f9; border-radius: 5px; margin-top: 10px;">
        <p id="lenght" class="invalid">Almeno 8 caratteri</p>
        <p id="uppercase" class="invalid">Almeno una lettera maiuscola</p>
        <p id="number" class="invalid">Almeno un numero</p>
        <p id="special" class="invalid">Almeno un carattere speciale (!@#$...)</p>
      </div>

      <label for="repeatPassword">Ripeti nuova password</label>
      <input  oninput="VerificaCorrispondenza()"  type="password"  name="repeatPassword" id="repeatPassword"
             required placeholder="Conferma la nuova password" maxlength="50">

      <p id="feedbackPassword" style="color: red; display: none" >Le password non coindidono</p>

      <input type="submit" value="Salva Modifiche" class="btnlog hbutton" style="margin-top: 30px; cursor: pointer;">
    </form>
  </fieldset>
</div>

</body>
</html>

<script>
  // Questa funzione controlla se le due password sono identiche
  function VerificaCorrispondenza() {
    const feedback = document.getElementById("feedbackPassword");
    const first = document.getElementById("registerPassword").value;
    const second = document.getElementById("repeatPassword").value;

    if (second.length > 0) {
      feedback.style.display = "block";
      if (first === second) {
        feedback.textContent = "Le password coincidono";
        feedback.style.color = "green";
      } else {
        feedback.textContent = "Le password non coincidono";
        feedback.style.color = "red";
      }
    } else {
      feedback.style.display = "none";
    }
  }

  // Questa funzione attiva i pallini verde/rosso per i requisiti
  function attivaValidazioneRequisiti() {
    const inputPassword = document.getElementById("registerPassword");
    const divFeedback = document.getElementById("feedback");

    inputPassword.addEventListener("focus", () => {
      divFeedback.style.display = "block";
    });

    inputPassword.addEventListener("blur", () => {
      divFeedback.style.display = "none";
    });

    inputPassword.addEventListener("input", () => {
      const val = inputPassword.value;
      // Correzione ID "lenght" come scritto nella tua JSP
      document.getElementById("lenght").className = val.length >= 8 ? "valid" : "invalid";
      document.getElementById("uppercase").className = /[A-Z]/.test(val) ? "valid" : "invalid";
      document.getElementById("number").className = /[0-9]/.test(val) ? "valid" : "invalid";
      document.getElementById("special").className = /[!@#\$%\^\&*\)\(+=._-]/.test(val) ? "valid" : "invalid";
    });
  }

  // Fai partire la validazione non appena la pagina è pronta
  document.addEventListener("DOMContentLoaded", attivaValidazioneRequisiti);
</script>
