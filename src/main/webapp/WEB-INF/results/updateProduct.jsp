<%--
  Created by IntelliJ IDEA.
  User: anton
  Date: 14/01/2026
  Time: 22:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>

  <title>Aggiornamento Prodotto - Valiant Systems</title>
  <%@include file="header.jsp" %>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/index-style.css" type="text/css">
</head>
<body>

<div>
  <div class="container container-alt">
    <a href="<%=request.getContextPath()%>/index.jsp" title="Vai alla homepage" style="position: relative"><img src="<%=request.getContextPath()%>/images/logo-form.png" alt="Logo Valiant Systems" id="form-logo"></a>
    <fieldset class="rounded">
      <legend style="z-index: -1;position: absolute;color: white;user-select: none;">Aggiornamento Prodotto</legend>
      <form action="UpdateProduct" method="post" enctype="multipart/form-data" id="form-update-product" onsubmit="return validateFormAddProduct()">
        <h1>Aggiornamento prodotto</h1>
        <input type="hidden" name="source" value="updateProduct">
        <label for="nameProdottoUP">Nome prodotto</label>
        <input type="hidden" value="${prodotto.id}" name="id">
        <input type="text" id="nameProdottoUP" name="nameProdotto" required pattern=".{7,50}" placeholder="7-50 caratteri" value="${prodotto.nome}">

        <label for="prezzoProdottoUP">Prezzo prodotto:</label>
        <input type="text" id="prezzoProdottoUP" name="prezzoProdotto" required pattern="^[1-9]\d{2,6}(\.(\d{1,2}))?$" placeholder="Solo numeri, es: 999,99" value="${prodotto.prezzo}">

        <label for="immagineProdotttoUP">Immagine attuale <strong>${prodotto.immagine}</strong></label><br>
        <label for="immagineProdotttoUP">Sostituisci immagine (opzionale):</label>
        <input type="file" id="immagineProdotttoUP" name="immagineProdottto" accept="image/*">

        <label for="percentualeSaldoUP">Percentuale saldo:</label>
        <input type="number" id="percentualeSaldoUP" name="percentualeSaldo" required min="0" max="99" value="${prodotto.percSaldo}" title="Range permesso: 0-99">

        <label for="quantitàUP">Quantità</label>
        <input type="number" id="quantitàUP" name="quantità" required min="1" value="${prodotto.quantita}" title="Quantità minima = 1">

        <label for="cpuUP">CPU</label>
        <input type="text" id="cpuUP" name="cpu" required pattern=".{3,60}" placeholder="3-60 caratteri" value="${prodotto.CPU}" title="Minimo 3 caratteri - massimo 60">

        <label for="gpuUP">GPU</label>
        <input type="text" id="gpuUP" name="gpu" required pattern=".{3,60}" placeholder="3-60 caratteri" value="${prodotto.GPU}" title="Minimo 3 caratteri - massimo 60">

        <label for="RAMsizeUP">RAM size (GB)</label>
        <input type="number" id="RAMsizeUP" name="RAMsize" required min="4" step="2" value="${prodotto.RAM_size}" title="Minimo 4 - divisibile per w">

        <label for="RAMspeedUP">RAM speed (MHz)</label>
        <input type="number" id="RAMspeedUP" name="RAMspeed" required min="100" max="10000" value="${prodotto.RAM_speed}" title="Minimo 1000 - massimo 10000">

        <label for="SSDsizeUP">SSD size (GB)</label>
        <input type="number" id="SSDsizeUP" name="SSDsize" required min="256" step="256" value="${prodotto.SSD_size}" title="Minimo 256 - divisibile per 4">

        <input type="submit" value="Modifica Prodotto" id="sumbmit-update-prodotto">
      </form>
    </fieldset>

  </div>
</div>

<script>
  function validateFormAddProduct(){
    const name = document.getElementById("nameProdottoUP").value.trim();
    const prezzo = document.getElementById("prezzoProdottoUP").value.trim();
    const saldo = parseInt(document.getElementById("percentualeSaldoUP").value);
    const quantita = parseInt(document.getElementById("quantitàUP").value);
    const cpu = document.getElementById("cpuUP").value.trim();
    const gpu = document.getElementById("gpuUP").value.trim();
    const ramSize = parseInt(document.getElementById("RAMsizeUP").value);
    const ramSpeed = parseInt(document.getElementById("RAMspeedUP").value);
    const ssdSize = parseInt(document.getElementById("SSDsizeUP").value);

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

