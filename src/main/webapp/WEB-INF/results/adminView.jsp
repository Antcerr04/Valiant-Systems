<%--
  Created by IntelliJ IDEA.
  User: anton
  Date: 09/01/2026
  Time: 19:23
  To change this template use File | Settings | File Templates.
  This file is used to show the products on Manager's point of view--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="/WEB-INF/results/error.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Amministrazione - Valiant Systems</title>
    <%@include file="/WEB-INF/results/header.jsp" %>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/index-style.css" type="text/css">
</head>
<body>
<%@ include file="/WEB-INF/navbar.jsp" %>
<div class="flex-wrapper">
    <c:if test="${utente.username != 'admin'}">
        <c:redirect url="/index.jsp"/>
    </c:if>
    <c:choose>
        <c:when test="${empty prodottoList}">
            <div class="container" style="padding: 50px;width: 450px">
                <h1>Nessun PC presente nel database.</h1>
                <h3>Si prega di inserire un PC.</h3>
            </div>
        </c:when>
        <c:when test="${!empty prodottoList}">
            <h1 style="text-align: center">Inventario</h1>
            <div>
                <div class="container-flex" style="min-height: 500px;">
                    <div style="width: 100%;overflow-x:auto;">
                        <table style="margin-bottom: 175px">
                            <tr class="headrow">
                                <th>Azioni</th>
                                <th>ID</th>
                                <th>Immagine</th>
                                <th>Nome</th>
                                <th>Prezzo</th>
                                <th>Quantità</th>
                                <th>% Saldo</th>
                                <th>CPU</th>
                                <th>GPU</th>
                                <th>RAM (GB)</th>
                                <th>RAM (MHz)</th>
                                <th>SSD (GB)</th>
                            </tr>
                            <c:forEach items="${prodottoList}" var="prodotto">
                                <tr>
                                    <td style="white-space: nowrap;">
                                        <!-- Contenitore flessibile per le icone -->
                                        <div style="display: flex; gap: 8px; align-items: center;">
                                            <!-- Form DELETE -->
                                            <form id="deleteForm-${prodotto.id}" action="delete" method="post"
                                                  style="margin: 0;">
                                                <input type="hidden" name="id" value="${prodotto.id}">
                                                <button type="button" onclick="confirmDelete(${prodotto.id})"
                                                        style="border: none; background: none; cursor: pointer;"
                                                        title="Elimina prodotto">
                                                    <span class="material-icons table-btn">delete</span>
                                                </button>
                                            </form>

                                            <!-- Form UPDATE -->
                                            <form action="UpdateProduct" method="post" enctype="multipart/form-data"
                                                  style="margin: 0;">
                                                <input type="hidden" name="source" value="showAll">
                                                <input type="hidden" name="id" value="${prodotto.id}">
                                                <button type="submit"
                                                        style="border: none; background: none; cursor: pointer;"
                                                        title="Modifica prodotto">
                                                    <span class="material-icons table-botton-update">mode_edit</span>
                                                </button>
                                            </form>
                                        </div>
                                    </td>

                                    <td>${prodotto.id}</td>
                                    <td>
                                        <div class="dropdown-hvr" style="width: 100%;">
                                                ${prodotto.immagine}
                                            <div class="dropdown-content-hvr"
                                                 style="padding: 16px 16px; border-radius: 99px">
                                                <img class="img-dropdown-hvr"
                                                     src="<%=request.getContextPath()%>/images/PCimages/${prodotto.immagine}"
                                                     alt="Immagine di PC ${prodotto.nome}">
                                            </div>
                                        </div>
                                    </td>
                                    <td>${prodotto.nome}</td>
                                    <td><fmt:formatNumber value="${prodotto.prezzo}" groupingUsed="no"
                                                          maxFractionDigits="2" minFractionDigits="2"/>€
                                    </td>
                                    <td
                                            <c:if test="${prodotto.quantita == 0}">style="background-color: #ffc6c6;"</c:if>>${prodotto.quantita}</td>
                                    <td>${prodotto.percSaldo}</td>
                                    <td>${prodotto.CPU}</td>
                                    <td>${prodotto.GPU}</td>
                                    <td>${prodotto.RAM_size}</td>
                                    <td>${prodotto.RAM_speed}</td>
                                    <td>${prodotto.SSD_size}</td>
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

    function confirmDelete(id) {
        if (confirm('Operazione irreversibile.\nVuoi davvero cancellare questo prodotto?')) {
            document.getElementById('deleteForm-' + id).submit();
        }

    }
</script>
</html>

