<%--
  Created by IntelliJ IDEA.
  User: anton
  Date: 13/01/2026
  Time: 16:34
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: Utente
  Date: 24/05/2025
  Time: 13:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="WEB-INF/results/error.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <title>Aggiorna dati utente - Valiant Systems</title>
  <%@include file="WEB-INF/results/header.jsp" %>
  <link rel="stylesheet" href="css/index-style.css" type="text/css">

</head>
<body>
<c:choose>
  <c:when test="${utente == null}">
    <c:redirect url="login.jsp"/>
  </c:when>
  <c:when test="${utente != null && !utente.manager}"><!-- Se l'utente è nella sessione mostra pagina per modificare i dati -->
    <div>
      <div class="container container-alt">
        <a href="index.jsp" title="Vai alla homepage" style="position: relative"><img src="images/logo-form.png"
                                                                                      alt="Logo Valiant Systems"
                                                                                      id="form-logo"></a>
        <fieldset class="rounded">
          <legend style="z-index: -1;position: absolute;color: white;user-select: none;">Aggiorna dati</legend>
          <form action="Modifica" method="post" id="form-update-utente">
            <input type="hidden" name="source" value="update">
            <!--Serve per vedere che file chiama la servlet modifica -->
            <h1>Aggiorna dati</h1>

            <label for="new-name-utente">Nome</label>
            <input type="text" name="name" placeholder="Name" id="new-name-utente" required
                   autocomplete="on" value="${utente.nome}" pattern="^[A-Za-zà-ù]{2,20}$"
                   title="Solo caratteri di lunghezza da 2 a 20" maxlength="20">
            <label for="new-surname-utente">Cognome</label>
            <input type="text" name="surname" id="new-surname-utente" placeholder="Surname" required
                   autocomplete="on" value="${utente.cognome}" pattern="^[A-Za-zà-ù]{2,20}$"
                   title="Solo caratteri di lunghezza da 2 a 20">

            <label for="new-email-utente">Email</label>
            <input type="email" id="new-email-utente" name="email" placeholder="Email"
                   autocomplete="on" value="${utente.email}" disabled style="cursor:not-allowed">
            <label for="regione">Scegli la regione : </label>
            <select id="regione" name="regione" required>
              <option value="${indirizzo.regione}">${indirizzo.regione}</option>
            </select> <br>
            <label for="provincia">Scegli la provincia</label>
            <select id="provincia" name="provincia" required>
              <option value="${indirizzo.provincia}">${indirizzo.provincia}</option>
            </select>
            <label for="new-street-username">Indirizzo (Via/Corso/Piazza...)</label>
            <input type="text" id="new-street-username" name="via" placeholder="Street" required
                   autocomplete="on" value="${indirizzo.via}" pattern="^[A-Za-z \-]+$"
                   title="Inserisci una via">
            <label for="new-housenumber">Numero civico</label>
            <input type="text" name="house-number" id="new-housenumber" placeholder="House Number" required
                   autocomplete="on"
                   pattern="^\d{1,3}$" title="Inserisci numero civico massimo di 3 cifre"
                   value="${indirizzo.numCiv}">
            <label for="new-cap-utente">CAP</label>
            <input type="text" name="cap" id="new-cap-utente" placeholder="Cap" required autocomplete="on"
                   pattern="^\d{5}$" title="Inserisci CAP di 5 numeri" value="${indirizzo.cap}">
            <label for="new-city">Città</label>
            <input type="text" id="new-city" name="city" placeholder="City" required autocomplete="on"
                   value="${indirizzo.città}" pattern="^[A-Za-z \-]+$" title="Inserisci città">

            <input type="submit" value="Modifica" class="update-submit">


          </form>
        </fieldset>

      </div>
    </div>


    <script src="./javascript/utility.js"></script>
  </c:when>

</c:choose>
</body>
</html>

