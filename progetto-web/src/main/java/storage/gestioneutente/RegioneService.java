package storage.gestioneutente;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Class used to get Regioni and Provincie
 */
public class RegioneService {

    private static final Map<String, Set<String>> REGIONI = new HashMap<>();

    static {
        try (InputStreamReader reader = new InputStreamReader(
                Objects.requireNonNull(RegioneService.class.getClassLoader()
                        .getResourceAsStream("regioni_province_italia.json")))) {

            JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
            JsonArray regioni = root.getAsJsonArray("regioni");

            for (JsonElement elem : regioni) {
                JsonObject r = elem.getAsJsonObject();
                String nome = r.get("nome").getAsString();

                Set<String> province = new HashSet<>();
                for (JsonElement p : r.getAsJsonArray("province")) {
                    province.add(p.getAsString());
                }

                REGIONI.put(nome, province);
            }

        } catch (Exception e) {
            throw new RuntimeException("Errore caricamento JSON regioni", e);
        }
    }

    /**
     * Function to validate Regione
     * @param regione to validate
     * @return true if regione is valid, else return false
     */
    public static boolean validateRegione(String regione) {
        return REGIONI.containsKey(regione);
    }

    /**
     * Function to validate Provincia
     * @param regione of the province to validate
     * @param provincia to validate
     * @return true if province is valid else return false
     */
    public static boolean validateProvincia(String regione, String provincia) {
        return REGIONI.containsKey(regione)
                && REGIONI.get(regione).contains(provincia);
    }

    /**
     * Function to get Regioni
     * @return regioni
     */
    public static Set<String> getRegioni() {
        return REGIONI.keySet();
    }

    /**
     * Function to get Province
     * @param regione of the province
     * @return province of the Regione
     */
    public static Set<String> getProvince(String regione) {
        return REGIONI.getOrDefault(regione, Set.of());
    }
}
