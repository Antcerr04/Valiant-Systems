<%--
  Created by IntelliJ IDEA.
  User: anton
  Date: 29/12/2025
  Time: 12:36
  To change this template use File | Settings | File Templates.
  This jsp file contains code to avoid to writing navbar's code in everyy file
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<nav id="mainNav">
  <a class="home" href="<%=request.getContextPath()%>/index.jsp"><img id="logo" src="<%=request.getContextPath()%>/images/logo.png" alt="Valiant System Homepage" title="Vai alla homepage" ></a>
  <a href="show">Lista PC</a>
  <a href="sale">Saldi</a>
  <a href="<%=request.getContextPath()%>/about.jsp">Dove Siamo</a>
  <ul id="sideMain1">
    <li class="right dropdown"><a href="#search" title="Cerca un PC" id="link-search"><span class="material-icons">search</span></a>
      <div class="dropdown-content"  id="search-div" style="display: none">
        <input type="text" id="s" name="q" style="width: 100%;text-align: center;height: 28px;font-size: 18px;position: sticky;top: -11px;" oninput="loadDoc()">
        <p id="result">

        </p>
      </div>
    </li>
    <c:if test = "${!utente.manager}">
      <li class="right"><a href="<%=request.getContextPath()%>/cart" title="Vai al carrello"><span class="material-icons">shopping_cart</span><c:if test="${!empty carrelloList.carrelloItemList}"><span class="material-icons cart-dot">circle</span></c:if></a></li>
    </c:if>
    <c:choose>
      <c:when test="${utente == null}">
        <li class="right"><a href="<%=request.getContextPath()%>/login.jsp" title="Accedi/Registrati"><span class="material-icons">login</span></a></li>
      </c:when>
      <c:when test="${utente != null}">
        <c:if test = "${utente.manager}">
          <li class="right dropdown-hvr">
            <a href="#" title="Menu utente"><span class="material-icons">manage_accounts</span></a>
            <div class="dropdown-content-hvr user-drop-hvr">
              <a class="dropdown-result user-drop-hvr-child" href="<%=request.getContextPath()%>/manage" title="Vai all'inventario dei PC"><span class="material-icons" style="width: 30px">lists</span> Inventario</a>
              <a class="dropdown-result user-drop-hvr-child" href="<%=request.getContextPath()%>/insertProduct.jsp" title="Inserisci un nuovo PC"><span class="material-icons" style="width: 30px">add_to_queue</span> Aggiungi PC</a>
              <a class="dropdown-result user-drop-hvr-child" href="<%=request.getContextPath()%>/logout" title="Disconnetti utente"><span class="material-icons" style="width: 30px">logout</span> Logout</a>
            </div>
          </li>
        </c:if>
        <c:if test = "${!utente.manager}">
          <li class="right dropdown-hvr">
            <a href="#" title="Menu utente"><span class="material-icons">person</span></a>
            <div class="dropdown-content-hvr user-drop-hvr">
              <a class="dropdown-result user-drop-hvr-child" href="<%=request.getContextPath()%>/orders" title="Vai alla lista ordini"><span class="material-icons" style="width: 30px">receipt_long</span> Ordini</a>
              <a class="dropdown-result user-drop-hvr-child" href="<%=request.getContextPath()%>/update.jsp" title="Modifica dati utente"><span class="material-icons" style="width: 30px">edit_square</span> Modifica dati</a>
              <a class="dropdown-result user-drop-hvr-child" href="<%=request.getContextPath()%>/logout" title="Disconnetti utente"><span class="material-icons" style="width: 30px">logout</span> Logout</a>
            </div>
          </li>
        </c:if>
      </c:when>
    </c:choose>
  </ul>
</nav>
<script>
  function loadDoc(){
    var query = document.getElementById("s").value.trim();
    if (query === ""){
      document.getElementById("result").innerHTML = "";
      return;
    }
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function (){
      if(this.readyState == 4 && this.status == 200){
        var resultHTML = "";
        var resultJSON = JSON.parse(this.responseText);
        var textLenght = 0;
        if(resultJSON.length !== 0 ) {
          for (var i = 0; i < resultJSON.length; i++) {
            resultHTML += "<a class=\"dropdown-result\" href=\"./ShowProduct?id=" + resultJSON[i].id + "\"><p><img class=\"img-dropdown\" alt=\"Immagine PC\" src=\"./images/PCimages/" + resultJSON[i].immagine + "\"><b>" + resultJSON[i].nome + "</b></p></a><br>";
            var len = resultJSON[i].nome;
            if(textLenght < len.length)
              textLenght = len.length;
          }
        }else
          resultHTML+="<p style=\"text-align: center\">Nessun Risultato.</p>";

        if(textLenght > 20) {
          document.getElementById("search-div").style.minWidth = "330px";
        }else{
          document.getElementById("search-div").style.minWidth = "300px";
        }
        document.getElementById("result").innerHTML = resultHTML;

      }
    };
    xhttp.open("POST", "search", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send("q="+query);


  }
  document.getElementById("link-search").addEventListener("click",function (e){
    e.preventDefault();
    if(document.getElementById("search-div").style.display==="none") {
      document.getElementById("search-div").style.display = "block";
      const input = document.getElementById("s");
      input.focus();
      //input.select();
    }else {
      document.getElementById("search-div").style.display="none";
      document.getElementById("s").value="";
      document.getElementById("result").innerHTML="";
    }

  })

  document.querySelectorAll('.dropdown-hvr').forEach(function(dropdown){
    const trigger = dropdown.querySelector('a'); // primo link che apre il menu

    trigger.addEventListener('click', function(e){
      e.preventDefault(); // serve per non seguire il link "#"
      // toggla la classe open
      dropdown.classList.toggle('open');
    });

    // intercetta i click dentro il menu
    dropdown.querySelector('.dropdown-content-hvr').addEventListener('click', function(e){
      // se hai cliccato un link, chiudi il menu (ma senza prevenire il comportamento)
      if(e.target.closest('a')){
        dropdown.classList.remove('open');
      }
    });
  });

</script>
<c:if test="${!empty cookie.notification}">
  <div id="error-banner">
    <div style="word-wrap: break-word;padding:5px 0">${fn:replace(cookie.notification.value, '-', ' ')}</div>
  </div>


  <script>
    const errTimeout = setTimeout(hideFunction, 1000);
    function hideFunction(){
      document.getElementById("error-banner").style.visibility = "hidden";
      document.getElementById("error-banner").style.opacity = 0;
      document.getElementById("error-banner").style.transition = "visibility 0s 2s, opacity 2s linear";
    }
  </script>

</c:if>