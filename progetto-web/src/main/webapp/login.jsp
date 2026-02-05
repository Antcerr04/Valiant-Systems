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
    <script src="javascript/formvalidate.js" defer></script>
    <script src="javascript/PasswordCriteria.js"></script>
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
    <div id="error-banner" style="top: 0">
        <div style="word-wrap: break-word;padding:5px 0">${fn:replace(cookie.notification.value, '-', ' ')}</div>
    </div>


    <script>
        const errTimeout = setTimeout(hideFunction, 1000);
        function hideFunction(){
            document.getElementById("error-banner").style.visibility = "hidden";
            document.getElementById("error-banner").style.opacity = 0;
            document.getElementById("error-banner").style.transition = "visibility 0s 2s, opacity 2s linear";
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
            <div id="formVerifyEmail" style="display: none">
                <form action="Recovery" method="post" id="formVerifyEmailRecovery" onsubmit="event.preventDefault(); handleInviaCodice();">
                    <h1>Resetta password</h1>
                    <input type="hidden" name="source" value="verifyEmail">
                    <label for="reset-email">Inserisci la tua email</label>
                    <input type="email" required maxlength="75" name="email" style="width: 100%" id="reset-email">
                   <p class="feedback-email"></p>
                    <input type="submit" class="btnlog hbutton" value="Invia email">
                </form>
            </div>
            <div id="formRecoveryPasswordContainer" style="display: none">
                <form action="Recovery" method="post" id="formRecoveryPassword">
                    <h1>Resetta password</h1>
                    <input type="hidden" name="source" value="recoveryPassword">
                    <label for="codiceInserito">Inserisci codice</label>
                    <input type="text" name="codice" required id="codiceInserito">
                    <input type="hidden" name="email" id="emailInserita">
                    <label for="registerPassword">Inserisci nuova password</label>
                    <input type="password" id="registerPassword" name="newPassword">
                    <div id="feedback" style="display: none">
                        <p id="lenght" class="invalid">Almeno 8 caratteri</p>
                        <p id="uppercase" class="invalid">Almeno una lettera maiuscola</p>
                        <p id="number" class="invalid">Almeno un numero</p>
                        <p id="special" class="invalid">Almeno un carattere speciale (!@#$...)</p>
                    </div>
                    <input type="submit" class="btnlog hbutton" value="Ripristina Password">
                </form>
            </div>
        </fieldset>
    </div>
</div>

</body>
</html>
<script src="javascript/formvalidate.js"></script>
<script>
    async function handleInviaCodice() {
        const email=document.getElementById("reset-email");
        const emailValue=email.value;

        const isAvailable= await validateEmail(email);
        if (isAvailable) {  //if isAvailable the email doesn't exist
            alert("Email "+emailValue+" non trovata nel database");
            return;
        }
        else {


        fetch("EmailServlet", {
            method: "Post",
            headers : {"Content-Type" : "application/x-www-form-urlencoded"},
            body : "action=send&email="+encodeURIComponent(email.value)
        })
            .then(response=> response.json())
            .then(data=> {
                if(data.status == "success") {
                    alert("Controlla la tua email! Ti abbiamo inviato il codice");
                    document.getElementById("formVerifyEmail").style.display="none";
                    document.getElementById("formRecoveryPasswordContainer").style.display="block";
                    document.getElementById("emailInserita").value=email.value;

                    document.addEventListener("submit", function () {
                        const password=document.getElementById("registerPassword");
                        const validate = validatePassword(password.value);
                        if(!validate) {
                            event.preventDefault();
                            alert("Formato della password inserita non corretto");
                            return;
                        }
                    })
                }
            })
            .catch(error => {
                console.error(("Errore",error));
                alert("Errore durante l'invio della mail");
            })
        }


    }
</script>


