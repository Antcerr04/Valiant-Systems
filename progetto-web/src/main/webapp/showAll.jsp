<%--
  Created by IntelliJ IDEA.
  User: anton
  Date: 09/01/2026
  Time: 17:46
  To change this template use File | Settings | File Templates.
  This file is used to show all products.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="WEB-INF/results/error.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Lista PC - Valiant Systems</title>
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
                <h1>Nessun PC disponibile.</h1>
                <h3>Presto in arrivo!</h3>
            </div>
        </c:when>

        <c:when test="${!empty prodottoList}">
            <h1 style="text-align: center">Lista PC</h1>

            <div class="filter-div">
                <form method="post" action="show">
                    <div><b>Trova PC per combinazione CPU-GPU:</b></div>
                    <div class="container-flex container-filter">
                        <div>
                            <label for="cpu">CPU:</label>
                            <select name="cpu" id="cpu" class="filter-select" required>
                                <option value="">1) Seleziona CPU</option>
                            </select>
                        </div>
                        <div>
                            <label for="gpu">GPU:</label>
                            <select name="gpu" id="gpu" class="filter-select" required>
                                <option value="">2) Seleziona GPU</option>
                            </select>
                        </div>
                        <input type="submit" value="Applica" class="hbutton filter-btn" style="margin: 0">
                        <input type="reset" value="Resetta" class="hbutton filter-btn"
                               style="background-color: #e01721;"
                               onclick="window.location.assign('<%=request.getContextPath()%>/show')">
                    </div>
                </form>
            </div>


            <div class="container-flex" style="padding: 50px 0;column-gap: 10px">

                <c:forEach items="${prodottoList}" var="prodotto">
                    <div class="single-item">
                        <c:if test="${(prodotto.percSaldo > 0) && (prodotto.quantita > 0)}">
                            <div style="position:relative">
                                <div class="sale"></div>
                            </div>
                        </c:if>
                        <form method="get" action="ShowProduct">
                            <img class="display" src="images/PCimages/${prodotto.immagine}"
                                 alt="Immagine ${prodotto.nome}">
                            <c:if test="${prodotto.quantita == 0}">
                                <div style="position:relative">
                                    <div class="sold-out"></div>
                                </div>
                            </c:if>

                            <input type="hidden" name="id" value="${prodotto.id}">
                            <p><b>${prodotto.nome}</b></p>

                            <c:choose>
                                <c:when test="${prodotto.percSaldo == 0}">
                                    <p>Prezzo: € <fmt:formatNumber value="${prodotto.prezzo}" groupingUsed="no"
                                                                   maxFractionDigits="2" minFractionDigits="2"/></p>
                                </c:when>
                                <c:when test="${prodotto.percSaldo > 0}">
                                    <p>
                                        Offerta:
                                        <span style="text-decoration: line-through; color: #818181">€ <fmt:formatNumber
                                                value="${prodotto.prezzo}" groupingUsed="no" maxFractionDigits="2"
                                                minFractionDigits="2"/></span>
                                        <b>€ <fmt:formatNumber
                                                value="${prodotto.prezzo - ((prodotto.percSaldo * prodotto.prezzo)/100)}"
                                                groupingUsed="no" maxFractionDigits="2" minFractionDigits="2"/></b>
                                    </p>
                                </c:when>
                            </c:choose>
                            <input type="submit" class="hbutton btn-list" value="Visualizza">
                        </form>

                        <c:if test="${utente.manager}">
                            <form method="post" action="UpdateProduct">
                                <input type="hidden" name="id" value="${prodotto.id}">
                                <input type="hidden" name="source" value="showAll">
                                <input type="submit" class="hbutton btn-list"
                                       style="background-color: #ff8537 !important;" value="Modifica">
                            </form>
                        </c:if>
                    </div>
                </c:forEach>

            </div>
        </c:when>
    </c:choose>

    <%@ include file="WEB-INF/footer.jsp" %>
</div>
<script>
    //function to populate select
    async function fetchCPUList() {
        fetch("getCPU")
            .then(response => response.json())
            .then(data => {
                const cpuList = data;
                const selectedCPU = "${cpuValue}";
                const selectedGPU = "${gpuValue}";
                const SelectCPU = document.getElementById("cpu");
                const SelectGPU = document.getElementById("gpu");
                cpuList.forEach(cpu => {
                    const option = document.createElement("option");
                    option.value = cpu;
                    option.textContent = cpu;
                    option.selected = (selectedCPU == cpu);
                    SelectCPU.append(option);
                });
                if (selectedGPU) {
                    const params = new URLSearchParams();
                    params.append("cpu", selectedCPU);
                    fetch("getGPU?" + params)
                        .then(response => response.json())
                        .then(gpudata => {
                            const gpulist = gpudata;
                            gpulist.forEach(gpu => {
                                const gpuoption = document.createElement("option");
                                gpuoption.value = gpu;
                                gpuoption.textContent = gpu;
                                gpuoption.selected = (selectedGPU == gpu);
                                SelectGPU.append(gpuoption);
                            });
                        })
                }
                SelectCPU.addEventListener("change", () => {
                    const selectedCPU = cpuList.find(name => name == SelectCPU.value);
                    SelectGPU.innerHTML = '<option value="">2) Seleziona GPU</option>';
                    if (selectedCPU) {
                        const params = new URLSearchParams();
                        params.append("cpu", selectedCPU);
                        fetch("getGPU?" + params)
                            .then(response => response.json())
                            .then(gpudata => {
                                const gpulist = gpudata;
                                gpulist.forEach(gpu => {
                                    const gpuoption = document.createElement("option");
                                    gpuoption.value = gpu;
                                    gpuoption.textContent = gpu;
                                    SelectGPU.append(gpuoption);
                                });
                            })
                    }
                });
            })
    }

    fetchCPUList();
</script>
</body>
</html>

