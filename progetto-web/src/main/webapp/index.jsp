<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="WEB-INF/results/error.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Valiant Systems - Homepage</title>
    <%@include file="WEB-INF/results/header.jsp" %>
    <link rel="stylesheet" href="css/index-style.css" type="text/css">
</head>
<body>

<%@ include file="WEB-INF/navbar.jsp" %>
<div class="header">
    <div style="max-width: 1650px;/*padding: 20px 50px 20px 50px;*/margin: 0 auto 0 auto;">
        <div class="headcol1">
            <h1>Il
                <blue>TUO</blue>
                prossimo PC.
            </h1>
            <p style="font-size: 17px">Tutti i PC sono stati assemblati da esperti con i migliori componenti per fornire la potenza e l'affidabilità
                di cui hai bisogno.</p>
            <a href="show">
                <button class="hbutton" style="font-size: 16px">Guarda tutti i PC</button>
            </a>
        </div>

        <div class="headcol2">
            <img src="images/header-white.png" alt="header image">
        </div>
    </div>
</div>
<div style="background-color: #f9f9fb;color: #414141;border-bottom: 8px solid #f3f3f3;">
    <div class="container-flex">
        <div class="single-item-nohover pad-nohover">
            <span class="material-icons icon-big">speed</span>
            <p>Performance garantite</p>
        </div>
        <div class="single-item-nohover pad-nohover">
            <span class="material-icons icon-big">money_off</span>
            <p>Spedizione gratuita</p>
        </div>
        <div class="single-item-nohover pad-nohover">
            <span class="material-icons icon-big">local_shipping</span>
            <p>Spedizione entro 24h</p>
        </div>
        <div class="single-item-nohover pad-nohover">
            <span class="material-icons icon-big">support_agent</span>
            <p>Assistenza gratuita</p>
        </div>
    </div>
</div>
<div style="max-width: 1400px;padding: 20px 50px 20px 50px;margin: 20px auto 20px auto;font-size: 17px">
    <h2>Dummy content</h2>
    <div class="container-flex">
        <div class="home-text">
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin tincidunt diam sit amet lacus sollicitudin, non
            scelerisque mi porta. Nullam scelerisque rhoncus magna quis maximus. Mauris porta eu felis sed malesuada. Ut
            pretium massa eu pretium vestibulum. Ut ipsum felis, pharetra sit amet feugiat at, aliquet et massa. Orci varius
            natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.
            Fusce aliquam libero quis aliquam tincidunt. Aenean ut nulla nisl. Curabitur quis scelerisque tortor. Quisque
            vitae tempor lacus. Mauris eget tempus urna.
        </div>
        <div class="home-text">Pellentesque et velit tellus. Mauris imperdiet leo ut eleifend semper. Quisque finibus vel ligula eget suscipit.
            Sed sed ante non enim elementum tincidunt. Donec condimentum quam accumsan, blandit tortor et, pharetra justo.
            Integer est eros, sagittis eu pellentesque at, pretium non nulla. Etiam at elit vitae eros laoreet efficitur. In
            facilisis lorem ut varius varius. Proin a enim et leo dictum sollicitudin.
            Nulla nibh ipsum, cursus sed pulvinar a, pulvinar nec nulla.
            Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.
        </div>
    </div>
</div>

<%@ include file="WEB-INF/footer.jsp" %>
</body>
</html>
