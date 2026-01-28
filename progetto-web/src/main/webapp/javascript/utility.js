function caricaRegioni() {

    fetch("resources/regioni_province_italia.json")
        .then(response => response.json())
        .then(data => {
            const regioni = data.regioni;
            const SelectRegione = document.getElementById("regione");
            const SelectProvincia = document.getElementById("provincia");

            //insert the regions into the select
            regioni.forEach(regione => {
                const option = document.createElement("option");
                option.value = regione.nome;
                option.textContent = regione.nome;
                SelectRegione.appendChild(option);
            });
            //when select the region, insert the province into the select
            SelectRegione.addEventListener("change", () => {
                const regioneSelezionata = regioni.find(r => r.nome == SelectRegione.value);
                SelectProvincia.innerHTML = '<option value="">-- Seleziona una provincia --</option>';
                if (regioneSelezionata) {
                    regioneSelezionata.province.forEach(provincia => {
                        const option = document.createElement("option");
                        option.value = provincia;
                        option.textContent = provincia;
                        SelectProvincia.appendChild(option);
                    });
                }
            });


        })
        .catch(error => {
            console.error("Errore nel caricamento del file JSON : ", error);
        });
}

caricaRegioni();

function handleInviaCodice() {
    const email=document.getElementById("reset-email").value;
    const btn= document.getElementById("btn-invia-codice");
    const errorMsg= document.getElementById("email-not-foud");

    if(email == "" || !email.includes("@")) {
        alert("Inserisci un'email valida");
        return;
    }

    btn.innerText="Invio in corso...";
    btn.disabled=true;

    //Chiamata AJAX alla servlet che gaantisce l'invio
    fetch("EmailServlet", {
        method : "POST",
        headers : {"Content-Type": "application/x-www-form-urlencoded"},
        body : "action=send&email="+encodeURIComponent(email)
    })
        .then(response=> response.json())
        .then(data => {
            if(data.status == "success") {
            document.getElementById("step-email").style.display="none";
            document.getElementById("step-verification").style.display="block";
            alert("Controlla la tua email! Ti abbiamo inviato il codice.");
        } else {
            //Se la servlet risponde con errore
        if(errorMsg) errorMsg.style.display = "block";
        btn.innerText = "Invia codice di verifica";
        btn.disabled = false;
    }
    })
    .catch(error => {
        console.error(("Errore",error));
        alert("Errore durante l'invio della mail");
        btn.disabled=false;
    });
}
