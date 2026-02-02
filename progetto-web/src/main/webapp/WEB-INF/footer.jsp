<%--
  Created by IntelliJ IDEA.
  User: anton
  Date: 29/12/2025
  Time: 12:33
  To change this template use File | Settings | File Templates.
  This file contains the code to create a footer to avoid wrting it in every file
--%>
<footer>
    <div class="container-flex" style="justify-content: flex-start">
        <div class="single-item-nohover item-foot">

            <h3>Valiant Systems</h3>
            <p>
                Negozio di informatica che offre una vasta gamma di PC assemblati da esperti nel settore per soddisfare i clienti pi&ugrave; esigenti.
            </p>
            <p>
                <a href="https://www.facebook.com/" target="_blank"><img src="<%=request.getContextPath()%>/images/fb.png" alt="Facebook" style="width: 40px;"></a>
                <a href="https://www.twitter.com/" target="_blank"><img src="<%=request.getContextPath()%>/images/tw.png" alt="Twitter" style="width: 40px;"></a>
                <a href="https://www.instagram.com/" target="_blank"><img src="<%=request.getContextPath()%>/images/ig.png" alt="Instagram" style="width: 40px;"></a>
                <a href="https://www.youtube.com/" target="_blank"><img src="<%=request.getContextPath()%>/images/yt.png" alt="YouTube" style="width: 40px;"></a>
            </p>
        </div>
        <div class="single-item-nohover item-foot" >
            <h3><span class="material-icons ">info</span> Informazioni</h3>
            <p>Via Giovanni Paolo II, 132</p>
            <p>84084</p>
            <p>Fisciano (SA)</p>
            <p><span class="material-icons middle">mail</span> support@valiantsystems.com</p>
            <p><span class="material-icons middle">call</span> 1234-98765</p>
        </div>
    </div>
    <c:set var="now" value="<%= new java.util.Date()%>" />

    <div class="copyright">Copyright <span class="material-icons" style="font-size: 15px">copyright</span> <fmt:formatDate pattern="yyyy" value="${now}" /> Valiant Systems. Tutti i diritti riservati.</div>
</footer>
