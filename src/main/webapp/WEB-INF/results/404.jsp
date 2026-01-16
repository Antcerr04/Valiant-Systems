<%--
  Created by IntelliJ IDEA.
  User: Blink
  Date: 16/01/2026
  Time: 14:07
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error - Valiant Systems</title>
    <%@include file="/WEB-INF/results/header.jsp" %>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/index-style.css" type="text/css">
</head>
<body>
<%@ include file="/WEB-INF/navbar.jsp" %>
<div class="flex-wrapper">

    <div style="text-align: center;margin-top: auto;margin-bottom: auto;padding: 50px 0;">
        <span class="material-icons" style="color: #e01721; font-size: 60px">wrong_location</span>
        <h1>Errore 404 - Pagina non trovata!</h1>
        <div style="margin-top: 30px">La risorsa richiesta non è disponibile.</div>
    </div>

    <%@ include file="/WEB-INF/footer.jsp" %>
</div>
</body>
</html>
