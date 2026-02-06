package storage.gestioneutente;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IndirizzoTest {

    /**
     * Method used to test a complete constructor
     */
    @Test
    public void constructorTest() {
        Indirizzo indirizzo = new Indirizzo("Via rufigliano",15,84021,"Campania","Salerno","Salerno");

        assertEquals("Via rufigliano", indirizzo.getVia());
        assertEquals(15,indirizzo.getNumCiv());
        assertEquals(84021,indirizzo.getCap());
        assertEquals("Campania",indirizzo.getRegione());
        assertEquals("Salerno",indirizzo.getProvincia());
        assertEquals("Salerno", indirizzo.getCittà());
    }

    /**
     * Method used to test via
     */
    @Test
    public void viaTest(){
        Indirizzo indirizzo = new Indirizzo();
        indirizzo.setVia("Via rufigliano  #");
        assertFalse(Indirizzo.validateVia(indirizzo.getVia()));
        assertFalse(Indirizzo.validateVia(null));
        assertTrue(Indirizzo.validateVia("Via Vittorio Emanuele"));
    }

    /**
     * Method used to test numero civico
     */
    @Test
    public void numCivTest(){
        Indirizzo indirizzo = new Indirizzo();
        indirizzo.setNumCiv(1000000);
        assertFalse(Indirizzo.validateNumCiv(indirizzo.getNumCiv()));
        assertFalse(Indirizzo.validateNumCiv(-4));
        assertFalse(Indirizzo.validateNumCiv(0));
        assertFalse(Indirizzo.validateNumCiv(10567));
        assertFalse(Indirizzo.validateNumCiv(84021));
        assertTrue(Indirizzo.validateNumCiv(34));

    }

    /**
     * Method used to test cap
     */
    @Test
    public void capTest(){
        Indirizzo indirizzo = new Indirizzo();
        indirizzo.setCap(245);
        assertFalse(Indirizzo.validateCap(indirizzo.getCap()));
        assertFalse(Indirizzo.validateCap(-4));
        assertFalse(Indirizzo.validateCap(0));
        assertFalse(Indirizzo.validateCap(9999));
        assertFalse(Indirizzo.validateCap(123456));
        assertTrue(Indirizzo.validateCap(84021));
    }

    /**
     * Method used to test città
     */
    @Test
    public void cittàTest(){
        Indirizzo indirizzo = new Indirizzo();
        indirizzo.setCittà("Salerno");
        assertTrue(Indirizzo.validateCittà(indirizzo.getCittà()));
        assertFalse(Indirizzo.validateCittà("Salern0"));
        assertFalse(Indirizzo.validateCittà("C@mpagna"));
        assertTrue(Indirizzo.validateCittà("Ascoli Piceno"));
        assertTrue(Indirizzo.validateCittà("Massa-Carrara"));
        assertFalse(Indirizzo.validateCittà(null));

    }


    /**
     * Method used to test regione
     */
    @Test
    public void regioneTest(){
        assertFalse(Indirizzo.validateRegione("Salern0"));
        assertFalse(Indirizzo.validateRegione(""));
        assertFalse(Indirizzo.validateRegione(null));
        assertFalse(Indirizzo.validateRegione("123445667"));
        assertFalse(Indirizzo.validateRegione("salern@"));
        assertFalse(Indirizzo.validateRegione("Campani@"));
    }


    /**
     * Method used to test provincia
     */
    @Test

    public void provinciaTest(){
    Indirizzo indirizzo = new Indirizzo();
    indirizzo.setRegione("Campania");
    indirizzo.setProvincia("SalernO");
    assertFalse(Indirizzo.validateProvincia(indirizzo.getRegione(), indirizzo.getProvincia()));
    assertFalse(Indirizzo.validateProvincia(indirizzo.getRegione(),"Salen0"));
    assertFalse(Indirizzo.validateProvincia(indirizzo.getRegione(),null));
    assertFalse(Indirizzo.validateProvincia(null,null));
    assertFalse(Indirizzo.validateProvincia("Salerno",null));
    assertFalse(Indirizzo.validateProvincia(null,"Salerno"));
    assertFalse(Indirizzo.validateProvincia("ciao","Salerno"));
    assertFalse(Indirizzo.validateProvincia("Lombardia","Salerno"));
    }

}
