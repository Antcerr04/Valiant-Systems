function caricaRegioni() {

    fetch("jsons/regioni_province_italia.json")
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

