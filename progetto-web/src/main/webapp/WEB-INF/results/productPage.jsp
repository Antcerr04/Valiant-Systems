<%--
  Created by IntelliJ IDEA.
  User: anton
  Date: 14/01/2026
  Time: 21:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
  <title>${prodotto.nome} - Valiant Systems</title>
  <%@include file="/WEB-INF/results/header.jsp" %>

  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/index-style.css" type="text/css">
</head>
<body>
<fmt:setLocale value = "en_US"/>
<%@ include file="/WEB-INF/navbar.jsp" %>
<div class="flex-wrapper">
  <div class="container-flex" style="padding: 50px 0;margin: 0;max-width: none;">
    <div>
      <img class="display-single" src="images/PCimages/${prodotto.immagine}" alt="Immagine ${prodotto.nome}">
    </div>
    <div class="single-item-nohover" style="width: 500px">
      <p><b>${prodotto.nome}</b></p>
      <c:choose>
        <c:when test="${prodotto.percSaldo == 0}">
          <div class="container-flex-det">
            <span style="text-align: left">Prezzo:</span><span style="text-align: right"><fmt:formatNumber value="${prodotto.prezzo}" groupingUsed="no" maxFractionDigits="2" minFractionDigits="2"/> €</span>
          </div>
        </c:when>
        <c:when test="${prodotto.percSaldo > 0}">
          <div class="container-flex-det" style="text-decoration: line-through;color: #818181;">
            <span style="text-align: left">Prezzo:</span><span style="text-align: right"><fmt:formatNumber value="${prodotto.prezzo}" groupingUsed="no" maxFractionDigits="2" minFractionDigits="2"/> €</span>
          </div>
          <div class="container-flex-det">
            <span style="text-align: left;font-weight: bold">Offerta:</span><span style="text-align: right;font-weight: bold"><fmt:formatNumber value="${prodotto.prezzo - ((prodotto.percSaldo * prodotto.prezzo)/100)}" groupingUsed="no" maxFractionDigits="2" minFractionDigits="2"/> €</span>
          </div>
          <div class="container-flex-det">
            <span style="text-align: left">Percentuale Saldo:</span><span style="text-align: right">${prodotto.percSaldo} %</span>
          </div>
        </c:when>
      </c:choose>
      <div class="container-flex-det">
        <span style="text-align: left">Quantità:</span><span style="text-align: right">${prodotto.quantita}</span>
      </div>
      <div class="container-flex-det">
        <span style="text-align: left">CPU:</span><span style="text-align: right">${prodotto.CPU}</span>
      </div>
      <div class="container-flex-det">
        <span style="text-align: left">GPU:</span><span style="text-align: right">${prodotto.GPU}</span>
      </div>
      <div class="container-flex-det">
        <span style="text-align: left">SSD:</span><span style="text-align: right">
                <c:choose>
                  <c:when test="${prodotto.SSD_size >= 1024}">
                    ${(prodotto.SSD_size) / 1024} TB
                  </c:when>
                  <c:when test="${prodotto.SSD_size < 1024}">
                    ${prodotto.SSD_size} GB
                  </c:when>
                </c:choose>
            </span>
      </div>
      <div class="container-flex-det">
        <span style="text-align: left">RAM:</span><span style="text-align: right">${prodotto.RAM_size} GB @${prodotto.RAM_speed} MHz</span>
      </div>
      <br>
      <c:if test = "${utente.ruolo!='manager'}">
        <c:choose>
          <c:when test="${prodotto.quantita > 0}">
            <form method="get" action="addToCart">
              <input type="hidden" name="id" value="${prodotto.id}">
              <input type="submit" class="hbutton btn-list" style="width: 90%" value="Aggiungi al carrello">
            </form>
          </c:when>
          <c:when test="${prodotto.quantita == 0}">
            <button class="hbutton btn-list btn-disabled" style="width: 300px">Non disponibile</button>
          </c:when>
        </c:choose>
      </c:if>
      <c:if test="${utente.ruolo=='manager'}">
        <form method="post" action="UpdateProduct">
          <input type="hidden" name="id" value="${prodotto.id}">
          <input type="hidden" name="source" value="showAll">
          <input type="submit" class="hbutton btn-list" style="background-color: #ff8537 !important;width: 90%" value="Modifica">
        </form>
      </c:if>
    </div>
  </div>
  <%@ include file="/WEB-INF/footer.jsp" %>
</div>
</body>
</html>
