<%--
  Created by IntelliJ IDEA.
  User: Blink
  Date: 16/01/2026
  Time: 17:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="/WEB-INF/results/error.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Gestione Ordini - Valiant Systems</title>
    <%@include file="/WEB-INF/results/header.jsp" %>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/index-style.css" type="text/css">
</head>
<body>
<%@ include file="/WEB-INF/navbar.jsp" %>
<div class="flex-wrapper">
    <c:if test="${utente.ruolo!='manager'}">
        <c:redirect url="/index.jsp"/>
    </c:if>
    <c:choose>
        <c:when test="${empty orderList}">
            <div class="container" style="padding: 50px;width: 450px">
                <h1>Nessun ordine da gestire</h1>
                <h3>Controlla in secondo momento.</h3>
            </div>
        </c:when>
        <c:when test="${!empty orderList}">
            <h1 style="text-align: center">Gestione ordini da spedire</h1>
            <div>
                <div class="container-flex" style="min-height: 500px;">
                    <div style="width: 100%;overflow-x:auto;">
                        <table style="margin-bottom: 175px">
                            <tr class="headrow">
                                <th>Azioni</th>
                                <th>ID</th>
                                <th>Data Ordine</th>
                                <th>Dettaglio Ordine</th>
                                <th>Destinatario</th>
                                <th>Totale</th>
                            </tr>
                            <c:forEach items="${orderList}" var="ordine">
                                <tr>
                                    <td style="white-space: nowrap;">
                                        <div style="display: flex; gap: 8px; align-items: center;">
                                            <!-- SEND ORDER FORM -->
                                            <form id="sendForm-${ordine.id}" action="manage-orders/send" method="post"
                                                  style="margin: 0;">
                                                <input type="hidden" name="id" value="${ordine.id}">
                                                <button type="button" onclick="confirmSend(${ordine.id})"
                                                        style="border: none; background: none; cursor: pointer;"
                                                        title="Invia ordine">
                                                    <span class="material-icons table-btn" style="background-color: #009924">local_shipping</span>
                                                </button>
                                            </form>
                                        </div>
                                    </td>

                                    <td>${ordine.id}</td>
                                    <td><fmt:formatDate pattern="dd-MM-yyyy HH:mm:ss" value="${ordine.dataOrdine}" /></td>
                                    <td>
                                        <div class="dropdown-hvr" style="width: 100%;">
                                                Visualizza
                                            <div class="dropdown-content-hvr"
                                                 style="min-width: 200px;max-width: 350px; border-radius: 5px;top: 20px">
                                                <c:forEach items="${ordine.dettaglioOrdineList}" var="dettaglio">
                                                    <div style="margin: 2px 5px">
                                                            ${dettaglio.quantita}x - <a href="ShowProduct?id=${dettaglio.idProdotto}">${dettaglio.nomeProdotto}</a>
                                                    </div>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </td>
                                    <td>${ordine.nome} ${ordine.cognome}, ${ordine.indirizzo.via} ${ordine.indirizzo.numCiv} - ${ordine.indirizzo.cap}, ${ordine.indirizzo.città} (${ordine.indirizzo.provincia}) - ${ordine.indirizzo.regione}</td>
                                    <td><fmt:formatNumber value="${ordine.totaleOrdine}" groupingUsed="no" maxFractionDigits="2" minFractionDigits="2"/>€</td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </div>
            </div>
        </c:when>
    </c:choose>

    <%@ include file="/WEB-INF/footer.jsp" %>
</div>
</body>
<script>

    function confirmSend(id) {
        if (confirm('Operazione irreversibile.\nVuoi davvero marcare l\'ordine come inviato?')) {
            document.getElementById('sendForm-' + id).submit();
        }

    }
</script>
</html>

