<%--
  Created by IntelliJ IDEA.
  User: anton
  Date: 09/01/2026
  Time: 17:52
  To change this template use File | Settings | File Templates.
  This file is used to show all sale products
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="WEB-INF/results/error.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Saldi - Valiant Systems</title>
    <%@include file="WEB-INF/results/header.jsp" %>

    <link rel="stylesheet" href="./css/index-style.css" type="text/css">
</head>
<body>
<fmt:setLocale value="en_US"/>
<%@ include file="WEB-INF/navbar.jsp" %>

<div class="flex-wrapper">

    <c:choose>
        <c:when test="${empty prodottoList}">
            <div class="container" style="padding: 50px;width: 450px">
                <h1>Nessun PC in saldo.</h1>
                <h3>Presto in arrivo!</h3>
            </div>
        </c:when>
        <c:when test="${!empty prodottoList}">
            <h1 style="text-align: center"><span class="material-icons"
                                                 style="vertical-align:top;font-size:35px">sell</span> PC in saldo</h1>
            <div class="container-flex" style="padding: 50px 0">

                <c:forEach items="${prodottoList}" var="prodotto">
                    <div class="single-item">
                        <form method="get" action="ShowProduct">
                            <img class="display" src="images/PCimages/${prodotto.immagine}"
                                 alt="Immagine ${prodotto.nome}">
                            <input type="hidden" name="id" value="${prodotto.id}">
                            <p><b>${prodotto.nome}</b></p>
                            <p style="text-decoration: line-through;color: #818181;">Prezzo: € <fmt:formatNumber
                                    value="${prodotto.prezzo}" groupingUsed="no" maxFractionDigits="2"
                                    minFractionDigits="2"/></p>
                            <p>Offerta: € <fmt:formatNumber
                                    value="${prodotto.prezzo - ((prodotto.percSaldo * prodotto.prezzo)/100)}"
                                    groupingUsed="no" maxFractionDigits="2" minFractionDigits="2"/></p>
                            <input type="submit" class="hbutton btn-list" value="Visualizza">
                        </form>
                    </div>
                </c:forEach>
            </div>
        </c:when>
    </c:choose>

    <%@ include file="WEB-INF/footer.jsp" %>
</div>

</body>
</html>

