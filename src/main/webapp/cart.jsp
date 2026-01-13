<%--
  Created by IntelliJ IDEA.
  User: Blink
  Date: 13/01/2026
  Time: 22:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="WEB-INF/results/error.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Carrello - Valiant Systems</title>
    <%@include file="WEB-INF/results/header.jsp" %>

    <link rel="stylesheet" href="./css/index-style.css" type="text/css">
</head>
<body>
<fmt:setLocale value = "en_US"/>
<%@ include file="WEB-INF/navbar.jsp" %>

<div class="flex-wrapper">
    <c:if test = "${utente.manager}">
        <c:redirect url = "/index.jsp" />
    </c:if>
    <c:choose>
        <c:when test="${empty carrelloList.carrelloItemList}">
            <div class="container" style="padding: 50px">
                <h1>Carrello vuoto!</h1>
            </div>
        </c:when>
        <c:when test="${!empty carrelloList.carrelloItemList}">
            <h1 style="text-align: center"><span class="material-icons" style="vertical-align:top;font-size:35px">shopping_cart</span> Carrello</h1>
            <div class="container-flex-cart-main">
                <div class="container-flex-col">
                    <c:set var="total" value="${0.0}" />
                    <c:forEach items="${carrelloList.carrelloItemList}" var="carrello">
                        <div class="single-item-cart">
                            <div class="container-flex-cart">
                                <div><img class="display-cart" src="images/PCimages/${carrello.prodotto.immagine}" alt="Immagine ${carrello.prodotto.nome}"></div>
                                <div>
                                    ID: ${carrello.prodotto.id}<br>
                                    <b>${carrello.prodotto.nome}</b><br>
                                    Prezzo: € <fmt:formatNumber value="${carrello.prodotto.prezzo}" groupingUsed="no" maxFractionDigits="2" minFractionDigits="2"/><br>
                                    PERCENTUALE SALDO: ${carrello.prodotto.percSaldo}%<br>
                                    QTA DISP: ${carrello.prodotto.quantita}<br>
                                </div>
                                <c:set var="unfmtPrice" value="${(carrello.prodotto.prezzo -( (carrello.prodotto.percSaldo * carrello.prodotto.prezzo)/100))}" />
                                <fmt:formatNumber value="${unfmtPrice}" groupingUsed="no" maxFractionDigits="2" minFractionDigits="2" var="salePrice"/>
                                <c:set var="total" value="${total + (carrello.quantita * salePrice)}" />
                                <div>
                                    <p>TOTALE: <b>€ <fmt:formatNumber value="${carrello.quantita * salePrice}" groupingUsed="no" maxFractionDigits="2" minFractionDigits="2"/></b></p>
                                    <div style="text-align: center">
                                        <a href="removeFromCart?id=${carrello.prodotto.id}"><span class="material-icons middle-big-cart">remove</span></a>
                                            ${carrello.quantita}
                                        <c:choose>
                                            <c:when test="${carrello.quantita < carrello.prodotto.quantita}">
                                                <a href="addToCart?id=${carrello.prodotto.id}"><span class="material-icons middle-big-cart">add</span></a>
                                            </c:when>
                                            <c:when test="${carrello.quantita >= carrello.prodotto.quantita}">
                                                <span class="material-icons middle-big-cart" style="color: #b6b6b6;cursor: not-allowed">add</span>
                                            </c:when>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <div class="container-flex-col checkout">
                    <div style="width: 210px">
                        <div style="float: left">Subtotale:</div> <div style="float: right">€ <fmt:formatNumber value="${total}" groupingUsed="no" maxFractionDigits="2" minFractionDigits="2"/></div>
                    </div>
                    <div style="width: 210px">
                        <div style="float: left">Spedizione:</div> <div style="float: right">€ 0</div>
                    </div>
                    <div style="width: 210px">
                        <div style="float: left"><b>Totale:</b></div>&nbsp;<div style="float: right"><b>€ <fmt:formatNumber value="${total}" groupingUsed="no" maxFractionDigits="2" minFractionDigits="2"/></b></div>
                    </div>
                    <div><a href="checkout"><button class="hbutton btn-list" style="width: 210px">Ordina</button></a></div>
                </div>

            </div>

        </c:when>
    </c:choose>

    <%@ include file="WEB-INF/footer.jsp" %>
</div>

</body>
</html>
