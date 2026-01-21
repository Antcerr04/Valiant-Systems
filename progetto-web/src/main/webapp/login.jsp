<%--
  Created by IntelliJ IDEA.
  User: anton
  Date: 02/01/2026
  Time: 20:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>Login - Valiant Systems</title>
    <%@include file="WEB-INF/results/header.jsp" %>
    <link rel="stylesheet" href="css/index-style.css" type="text/css">
    <script src="javascript/formvalidate.js" defer></script> <!--Ancora da definire-->
    <script src="javascript/utility.js" defer></script>
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
<c:if test="${!empty cookie.notification}">
    <div id="error-banner-login" style="
position: absolute;
width: 100%;
background-color: #009924;
text-align: center;
padding: 7px 5px;
top: 0;
color: white;
font-size: 18px;">
        <div style="word-wrap: break-word; padding: 5px 0">${fn.replace(cookie.notification.value,'-',' ')}</div>
    </div>

    <script>
        const errTimeout = setTimeout(hideFunction, 1000);

        function hideFunction() {
            document.getElementById("error-banner-login").style.visibility = "hidden";
            document.getElementById("error-banner-login").styke.opacity = 0;
            document.getElementById("error-banner-login").style.transaction = "visibility 0s 2s, opacity 2s linear";
        }
    </script>
</c:if>

<div>
    <div class="container">
        <a href="index.jsp" title="Vai alla homepage" style="position: relative"><img src="images/logo-form.png"
                                                                                      alt="Logo Valiant Systems"
                                                                                      id="form-logo"></a>
        <fieldset class="rounded">
            <legend style="z-index: -1; position: absolute; color: white;user-select: none;">Login utente</legend>
            <form action="Login" method="post" id="form-login">
                <h1>Accedi</h1>
                <label for="username">Username</label>
                <input type="text" id="username" maxlength="20" required name="username" autofocus pattern="^[a-zA-Z0-9._]{4,20}$" title="Lo username deve contenere da 3 e 20 caratteri(lettere,numeri,punto o underscore">
                <label for="password">Password</label>
                <input type="password" maxlength="50" required id="password" name="password" title="Almeno 8 caratteri, una maiuscola, un numero e un carattere speciale">

                <a href="#" id="reset-password" style="margin-bottom: 10px; text-align: right;">Password
                    dimenticata?</a>
                <input type="submit" class="btnlog hbutton" value="Accedi">
                <p>Non hai un account? <a href="register.jsp">Registrati </a></p>
            </form>

            <!--Form to show when client click on "Password dimenticata"-->
            <form action="Modifica" method="post" id="form-reset" name="update" style="display: none">
                <h1>Resetta password</h1>
                <input type="hidden" name="source" value="reset">

                <div id="step-email">
                    <label for="reset-email">E-mail</label>
                    <input type="email" id="reset-email" maxlength="75" required name="email" style="width: 100%">
                    <p id="email-not-found" style="color: red; display: none"> Email non trovata nel sistema</p>
                    <button type="button" class="btnlog hbutton" id="btn-invia-codice" onclick="handleInviaCodice()" style="margin-top: 10px">
                        Invia codice di verifica
                    </button>
                </div>

                <div id="step-verification" style="display: none">
                    <label for="codice-verifica">Codice di verifica (inviato via email)</label>
                    <input type="text" id="codice-verifica" name="codice" maxlength="6" pattern="^\d{6}$" style="width: 100%" required>

                    <label for="resetPassword">Nuova password</label>
                    <input type="password" id="resetPassword" name="newPassword" style="width: 100%" required>
                    <div id="feedback" style="display: none">
                        <p id="lenght" class="invalid">Almeno 8 caratteri</p>
                        <p id="uppercase" class="invalid">Almeno una lettera maiuscola</p>
                        <p id="number" class="invalid">Almeno un numero</p>
                        <p id="special" class="invalid">Almeno un carattere speciale (!@#$...)</p>
                    </div>
                    <input type="submit" class="red btnlog hbutton" value="Conferma Reset" style="background-color: red;margin-top: 10px">
                </div>
            </form>
        </fieldset>
    </div>
</div>

</body>
</html>


