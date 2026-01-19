<%--
  Created by IntelliJ IDEA.
  User: Blink
  Date: 16/01/2026
  Time: 12:12
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="/WEB-INF/results/error.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <c:choose>
        <c:when test="${empty checkoutResult}">
            <title>Ordine effettuato! - Valiant Systems</title>
        </c:when>
        <c:when test="${!empty checkoutResult}">
            <title>Ordine non effettuato! - Valiant Systems</title>
        </c:when>
    </c:choose>
    <%@include file="/WEB-INF/results/header.jsp" %>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/index-style.css" type="text/css">
</head>
<body>
<fmt:setLocale value = "en_US"/>
<%@ include file="/WEB-INF/navbar.jsp" %>
<div class="flex-wrapper">
    <c:choose>
        <c:when test="${empty checkoutResult}">
            <div style="text-align: center">
                <h1>Ordine effettuato. Grazie per averci scelto!</h1>
                <p style="margin-top: 30px">Puoi controllare il tuo ordine <a href="<%=request.getContextPath()%>/orders">QUI</a></p>
            </div>
        </c:when>
        <c:when test="${!empty checkoutResult}">
            <div style="text-align: center">
                <h1>Ordine non effettuato!</h1>
                <c:forEach items="${checkoutResult}" var="errore">
                    <p style="margin-top: 30px"><c:out value="${errore}"/></p>
                </c:forEach>
            </div>
        </c:when>
    </c:choose>
    <%@ include file="/WEB-INF/footer.jsp" %>
</div>
</body>
</html>
