<%--
  Created by IntelliJ IDEA.
  User: anton
  Date: 29/12/2025
  Time: 12:39
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
    <c:if test="${errorMSG!= null}">
        <div style="text-align: center;margin-top: auto;margin-bottom: auto;padding: 50px 0;">
            <span class="material-icons" style="color: #e01721; font-size: 60px">error</span>
            <h1>Errore!</h1>
            <p style="margin-top: 30px">Messaggio: <c:out value="${errorMSG}"/></p>
        </div>
    </c:if>
    <c:if test="${exception!= null}">
        <div style="text-align: center;margin-top: auto;margin-bottom: auto;padding: 50px 0;">
            <div>An exception was raised: <br><c:out value="${exception}"/></div>
            <div style="margin-top: 30px">Exception message is: <br><b><c:out value="${exception.message}"/></b></div>
        </div>
    </c:if>
    <c:if test="${(exception == null) && (errorMSG == null) }">
        <div style="text-align: center">
            <h1>Errore generico</h1>
            <p style="margin-top: 30px">Questo messaggio è visibile solo in caso di errore nella codifica della view richiesta.</p>
        </div>
    </c:if>
    <%@ include file="/WEB-INF/footer.jsp" %>
</div>
</body>
</html>
