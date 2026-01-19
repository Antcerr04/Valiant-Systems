import org.example.Main;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import storage.ManagerDAO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class testRegisterManager {

    private ManagerDAO daoMock;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String username;

    @BeforeEach
    void setUp(){
        daoMock = mock(ManagerDAO.class);
        Main.service=daoMock;
         nome="Mario";
         cognome="Rossi";
         email="mariorossi@gmail.com";
         password="password";
         username="Mario01";





    }

    @Test
    void TC_1_2_1_NomeErrato(){

        // 1. Prepariamo l'input (come avevi fatto tu)
        String nomeErrato="Mari0";
        String input = "1\n"+nomeErrato+"\n0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // 2. Prepariamo la cattura dell'output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // 3. Eseguiamo il main
        org.example.Main.main(new String[]{});

        // 4. Verifichiamo con assertEquals (o assertTrue)
        String output = outContent.toString();

        // Usiamo assertTrue perché il main stampa anche il menu e il benvenuto
        assertTrue(output.contains("Nome non valido"), "Dovrebbe apparire il messaggio di errore per il nome");
    }

    @Test
    void TC_1_2_2_CognomeErrato(){
        String cogmomeErrato="R0ssi";
        String input = "1\n"+nome+"\n"+cogmomeErrato+"\n0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        org.example.Main.main(new String[]{});
        String output = outContent.toString();
        assertTrue(output.contains("Cognome non valido"),"Dovrebbe apparire il messaggio di errore per il cognome");
    }

    @Test
    void TC_1_2_3_EmailErrato(){
        String emailErrato="mariorossigmail.com";
        String input = "1\n"+nome+"\n"+cognome+"\n"+emailErrato+"\n0\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        org.example.Main.main(new String[]{});
        String output = outContent.toString();
        assertTrue(output.contains("Email non valida"),"Dovrebbe apparire il messaggio di errore per l'email");
    }
    @Test
    void TC_1_2_4_EmailEsistente(){

        when(daoMock.existsEmail(email)).thenReturn(true);

        String input = "1\n"+nome+"\n"+cognome+"\n"+email+"\n0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        org.example.Main.main(new String[]{});

        String output = outContent.toString();
        assertTrue(output.contains("Email già esistente"),"Dovrebbe apparire il messaggio di errore per l'email già esistente");
    }

    @Test
    void TC_1_2_5_UsernameErrato(){
        String usernameErrato="mr";
        String input = "1\n"+nome+"\n"+cognome+"\n"+email+"\n"+usernameErrato+"\n0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        org.example.Main.main(new String[]{});
        String output = outContent.toString();
        assertTrue(output.contains("Username non valido"),"Dovrebbe apparire il messaggio di errore per lo username");
    }

    @Test
    void TC_1_2_6_UsernameEsistente(){
        when(daoMock.existsUsername(username)).thenReturn(true);

        String input = "1\n"+nome+"\n"+cognome+"\n"+email+"\n"+username+"\n0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        org.example.Main.main(new String[]{});

        String output = outContent.toString();
        assertTrue(output.contains("Username già esistente"),"Dovrebbe apparire il messaggio di errore per lo username");
    }

    @Test
    void TC_1_2_7_PasswordErrato(){
        String passwordErrato="mariorossi1";
        String input = "1\n"+nome+"\n"+cognome+"\n"+email+"\n"+username+"\n"+passwordErrato+"\n0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        org.example.Main.main(new String[]{});

        String output = outContent.toString();
        assertTrue(output.contains("Password non valida"),"Dovrebbe apparire il messaggio di errore per lo username");
    }


}
