<%--
  Created by IntelliJ IDEA.
  User: Blink
  Date: 15/01/2026
  Time: 17:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="/WEB-INF/results/error.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Ordini - Valiant Systems</title>
  <%@include file="/WEB-INF/results/header.jsp" %>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/index-style.css" type="text/css">
</head>
<body>
<fmt:setLocale value = "en_US"/>
<%@ include file="/WEB-INF/navbar.jsp" %>
<div class="flex-wrapper">
  <c:if test="${empty utente || utente.manager}">
    <c:redirect url="/index.jsp" />
  </c:if>

  <c:choose>
    <c:when test="${empty orderList}">
      <div class="container" style="margin-top: 40px; width: 450px">
        <h1>Nessun ordine presente!</h1>
        <p style="margin-top: 40px; margin-bottom: 40px">Si prega di effettuarne uno.</p>
      </div>
    </c:when>

    <c:when test="${!empty orderList}">
      <h1 style="text-align: center; margin-top: 20px;">I miei ordini</h1>
      <div class="container-flex-col-orders all-orders">
        <c:forEach items="${orderList}" var="ordine">
          <div class="single-order">
            <!-- Intestazione ordine -->
            <div class="container-flex-cart order-info" style="background-color: #f3f3f3">
              <div>
                <h3>ID ordine: ${ordine.id}</h3>
                <strong>Data:</strong> <fmt:formatDate pattern="dd-MM-yyyy HH:mm:ss" value="${ordine.dataOrdine}" />
              </div>
              <!-- Indirizzo -->
              <div>
                <h4 style="margin-bottom: 5px;">Spedito a:</h4>
                <p>${ordine.indirizzo.via}, ${ordine.indirizzo.numCiv}</p>
                <p>${ordine.indirizzo.cap}, ${ordine.indirizzo.citta} (${ordine.indirizzo.provincia}) - ${ordine.indirizzo.regione}</p>
              </div>
              <div>
                <p><strong>Track ID:</strong> ${ordine.trackID}</p>
                <p><strong>Totale ordine:</strong> <fmt:formatNumber value="${ordine.totaleOrdine}" groupingUsed="no" maxFractionDigits="2" minFractionDigits="2"/>€</p>
              </div>
            </div>

            <!-- Prodotti -->
            <div class="container-flex-col-orders">
              <c:forEach items="${ordine.dettaglioOrdineList}" var="dettaglio">
                <div class="container-flex order-content">
                  <c:if test="${dettaglio.idProdotto != 0}">
                  <a href="ShowProduct?id=${dettaglio.idProdotto}">
                    </c:if>
                    <img class="order-img" src="<%=request.getContextPath()%>/images/PCimages/${dettaglio.immagine}" alt="${dettaglio.nomeProdotto}">
                    <c:if test="${dettaglio.idProdotto != 0}">
                  </a>
                  </c:if>
                  <div class="order-text">
                    <p>
                      <c:if test="${dettaglio.idProdotto != 0}">
                      <a href="ShowProduct?id=${dettaglio.idProdotto}" style="color: black">
                        </c:if>
                        <strong>${dettaglio.nomeProdotto}</strong>
                        <c:if test="${dettaglio.idProdotto != 0}">
                      </a>
                      </c:if>
                    </p>
                    <p>Quantità: ${dettaglio.quantita}</p>
                    <p>Prezzo unitario: <fmt:formatNumber value="${dettaglio.prezzoUnitario}" groupingUsed="no" maxFractionDigits="2" minFractionDigits="2"/>€</p>
                  </div>
                </div>
              </c:forEach>
            </div>
          </div>
        </c:forEach>
      </div>
    </c:when>
  </c:choose>

  <%@ include file="/WEB-INF/footer.jsp" %>
</div>
</body>
</html>
