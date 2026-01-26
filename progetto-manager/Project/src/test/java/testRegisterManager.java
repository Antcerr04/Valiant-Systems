import application.Insert;
import org.example.Main;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import storage.Manager;
import storage.ManagerDAO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class testRegisterManager {

        private ManagerDAO daoMock;
        private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        private final PrintStream originalOut = System.out;

        //Data of default valid
    private final String NOME= "Mario";
    private final String COGNOME="Rossi";
    private final String EMAIL= "mariorossi@gmail.com";
    private final String USERNAME= "Mario01";
    private final String PASSWORD= "Mario2004@";

    @BeforeEach
    void setUp() {
        daoMock = mock(ManagerDAO.class);

        Main.service=daoMock;
        outContent.reset();

        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restore(){
        //Clear console after every test
        System.setOut(originalOut);
    }

    //Helper to simulate input of user
    private void simulaInput(String... strings) {
        String data = String.join("\n", strings)+"\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));

        Insert.sc= new Scanner(System.in);
    }

    /**
     * Method used to test a failure when a name isn't correct
     */
    @Test
    void TC_1_2_1_NomeErrato(){
        simulaInput("Mari0");  //Only the first parameter, beacuse the method return false right away
        Insert.InsertManager(daoMock);

        //Verify that the message is present in the buffer
        assertTrue(outContent.toString().contains("Nome non valido"));

    }

    /**
     * Method used to test a failure when a surname isn't correct
     */
    @Test
    void TC_1_2_1_CognomeErrato(){

        simulaInput(NOME,"R0ssi");
        Insert.InsertManager(daoMock);
        assertTrue(outContent.toString().contains("Cognome non valido"));
    }

    /**
     * Method used to test a failure when email isn't correct
     */
    @Test
    void TC_1_2_3_EmailErrato(){
        simulaInput(NOME,COGNOME,"mariorossigmail.com");
        Insert.InsertManager(daoMock);
        assertTrue(outContent.toString().contains("Email non valida"));
    }

    /**
     *Method used to test a failure when email already exists
     */
    @Test
    void TC_1_2_4_EmailEsistente(){
        when(daoMock.existsEmail(EMAIL)).thenReturn(true);
        simulaInput(NOME,COGNOME,EMAIL);
        Insert.InsertManager(daoMock);
        assertTrue(outContent.toString().contains("Email già esistente"));
    }

    /**
     * Method used to test a failure when username isn't correct
     */
    @Test
    void TC_1_2_5_UsernameErrato(){
        simulaInput(NOME,COGNOME,EMAIL,"mr");
        Insert.InsertManager(daoMock);
        assertTrue(outContent.toString().contains("Username non valido"));

    }

    /**
     * Method used to test a failure when username already exist
     */
    @Test
    void TC_1_2_6_UsernameEsistente(){
        when(daoMock.existsUsername(USERNAME)).thenReturn(true);
        simulaInput(NOME,COGNOME,EMAIL,USERNAME);
        Insert.InsertManager(daoMock);
        assertTrue(outContent.toString().contains("Username già esistente"));
    }

    /**
     * Method used to test a failure when password isn't correct
     */

    @Test
    void TC_1_2_7_PasswordErrato(){
        simulaInput(NOME,COGNOME,EMAIL,USERNAME,"Mariorossi1");
        Insert.InsertManager(daoMock);
        assertTrue(outContent.toString().contains("Password non valida"));
    }

    /**
     * Method used to test an error into Database
     */
    @Test
    void TC_1_2_8_Errore(){

        when(daoMock.existsEmail(EMAIL)).thenThrow(new RuntimeException("Database offline"));
        simulaInput(NOME,COGNOME,EMAIL);
        boolean result=Insert.InsertManager(daoMock);
        assertFalse(result,"Dovrebbe esserci un errore nel DB");
    }

    /**
     * Method used to test a correct Registration of the Manager
     */
    @Test
    void TC_1_2_9_RegistrazioneAvvenuta(){
        when(daoMock.existsUsername(USERNAME)).thenReturn(false);
        when(daoMock.existsEmail(EMAIL)).thenReturn(false);
        simulaInput(NOME,COGNOME,EMAIL,USERNAME,PASSWORD);
        when(daoMock.insertManager(any(Manager.class))).thenReturn(true);
       Insert.InsertManager(daoMock);
        assertTrue(outContent.toString().contains("Inserimento completato"));
    }

}
