<%--
  Created by IntelliJ IDEA.
  User: anton
  Date: 14/01/2026
  Time: 22:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="storage.gestioneutente.Utente" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="WEB-INF/results/error.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Inserimento Prodotto - Valiant System</title>
    <%@include file="WEB-INF/results/header.jsp" %>
    <link rel="stylesheet" href="css/index-style.css" type="text/css">
</head>
<body>
<%
    Utente utente = (Utente) request.getSession().getAttribute("utente");
    if (utente == null || (utente.getRuolo()!="manager")) {
        request.setAttribute("errorMSG", "Accesso negato. La risorsa richiesta richiede privilegi di manager.");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/error.jsp");
        dispatcher.forward(request, response);
    }
%>
<div>
    <div class="container container-alt">
        <a href="index.jsp" title="Vai alla homepage" style="position: relative"><img src="images/logo-form.png" alt="Logo Valiant Systems" id="form-logo"></a>
        <fieldset class="rounded">
            <legend style="z-index: -1;position: absolute;color: white;user-select: none;">Inserimento prodotto</legend>
            <form action="addProduct" method="post" enctype="multipart/form-data" id="form-create-product" onsubmit="return validateFormAddProduct()">
                <h1>Inserimento prodotto</h1>
                <label for="nameProdotto">Nome prodotto:</label>
                <input type="text" id="nameProdotto" name="nameProdotto" required pattern=".{7,50}" placeholder="7-50 caratteri" title="Minimo 7 caratteri - massimo 50" autofocus>

                <label for="prezzoProdotto">Prezzo prodotto:</label>
                <input type="text" id="prezzoProdotto" name="prezzoProdotto" required pattern="^[1-9]\d{2,6}(\.(\d{1,2}))?$" placeholder="Solo numeri, es: 999.99" title="Solo numeri [min 100]. FORMATI accettati: 100 100.1 100.11">

                <label for="immagineProdottto">Immagine prodotto: (Campo vuoto = immagine di default)</label>
                <input type="file" id="immagineProdottto" name="immagineProdottto" accept="image/*">

                <label for="percentualeSaldo">Percentuale saldo:</label>
                <input type="number" id="percentualeSaldo" name="percentualeSaldo" required min="0" max="99" title="Range permesso: 0-99">

                <label for="quantità">Quantità:</label>
                <input type="number" id="quantità" name="quantita" required min="1" title="Quantità minima = 1">

                <label for="cpu">CPU:</label>
                <input type="text" id="cpu" name="cpu" required pattern=".{3,60}" placeholder="3-60 caratteri" title="Minimo 3 caratteri - massimo 60">

                <label for="gpu">GPU:</label>
                <input type="text" id="gpu" name="gpu" required pattern=".{3,60}" placeholder="3-60 caratteri" title="Minimo 3 caratteri - massimo 60">

                <label for="RAMsize">RAM size (GB):</label>
                <input type="number" id="RAMsize" name="RAMsize" required min="4" step="2" title="Minimo 4 - divisibile per 2">

                <label for="RAMspeed">RAM speed (MHz):</label>
                <input type="number" id="RAMspeed" name="RAMspeed" required min="1000" max="10000" title="Minimo 1000 - massimo 10000">

                <label for="SSDsize">SSD size (GB):</label>
                <input type="number" id="SSDsize" name="SSDsize" required min="256" step="256" title="Minimo 256 - divisibile per 4">

                <input type="submit" value="Crea prodotto" id="sumbmit-crea-prodotto">
            </form>
        </fieldset>

    </div>
</div>

<script>
    function validateFormAddProduct(){
        const name = document.getElementById("nameProdotto").value.trim();
        const prezzo = document.getElementById("prezzoProdotto").value.trim();
        const saldo = parseInt(document.getElementById("percentualeSaldo").value);
        const quantita = parseInt(document.getElementById("quantità").value);
        const cpu = document.getElementById("cpu").value.trim();
        const gpu = document.getElementById("gpu").value.trim();
        const ramSize = parseInt(document.getElementById("RAMsize").value);
        const ramSpeed = parseInt(document.getElementById("RAMspeed").value);
        const ssdSize = parseInt(document.getElementById("SSDsize").value);

        // Nome prodotto
        if (name.length < 7 || name.length > 50) {
            alert("Il nome del prodotto deve essere tra 7 e 50 caratteri.");
            return false;
        }

        // Prezzo (espressione regolare: solo numeri, opzionale virgola finale)
        const prezzoRegex = /^[1-9]\d{2,6}(\.(\d{1,2}))?$/; //OLD regex /^[0-9]{2,7}([.][0-9]{1,2})?$/   <- changed since 011 was permitted, leading to sql exception (min price = 100)
        if (!prezzoRegex.test(prezzo)) {
            alert("Inserisci un prezzo valido (es: 999.99).");
            return false;
        }

        // Percentuale saldo
        if (isNaN(saldo) || saldo < 0 || saldo > 99) {
            alert("La percentuale di saldo deve essere tra 0 e 99.");
            return false;
        }

        // Quantità
        if (isNaN(quantita) || quantita < 1) {
            alert("La quantità deve essere almeno 1.");
            return false;
        }

        // CPU e GPU
        if (cpu.length < 3 || cpu.length > 40) {
            alert("Il campo CPU deve contenere tra 3 e 40 caratteri.");
            return false;
        }

        if (gpu.length < 3 || gpu.length > 40) {
            alert("Il campo GPU deve contenere tra 3 e 40 caratteri.");
            return false;
        }

        // RAM size
        if (isNaN(ramSize) || ramSize < 4 || ramSize % 2 !== 0) {
            alert("La RAM size deve essere almeno 4 ed essere un multiplo di 2.");
            return false;
        }

        // RAM speed
        if (isNaN(ramSpeed) || ramSpeed < 1000 || ramSpeed > 10000) {
            alert("La RAM speed deve essere tra 4 e 10000 MHz.");
            return false;
        }

        // SSD size
        if (isNaN(ssdSize) || ssdSize < 256 || ssdSize % 4 !== 0) {
            alert("La SSD size deve essere almeno 256 ed essere un multiplo di 4.");
            return false;
        }

        // Tutto ok
        return true;

    }
</script>

</body>
</html>

