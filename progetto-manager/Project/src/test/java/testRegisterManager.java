import application.Insert;
import org.example.Main;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import storage.ManagerDAO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;
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

    @Test
    void TC_1_2_1_NomeErrato(){
        simulaInput("Mari0");  //Only the first parameter, beacuse the method return false right away
        Insert.InsertManager(daoMock);

        //Verify that the message is present in the buffer
        assertTrue(outContent.toString().contains("Nome non valido"));

    }

    @Test
    void TC_1_2_1_CognomeErrato(){

        simulaInput(NOME,"R0ssi");
        Insert.InsertManager(daoMock);
        assertTrue(outContent.toString().contains("Cognome non valido"));
    }

    @Test
    void TC_1_2_3_EmailErrato(){
        simulaInput(NOME,COGNOME,"mariorossigmail.com");
        Insert.InsertManager(daoMock);
        assertTrue(outContent.toString().contains("Email non valida"));
    }

    @Test
    void TC_1_2_4_EmailEsistente(){
        when(daoMock.existsEmail(EMAIL)).thenReturn(true);
        simulaInput(NOME,COGNOME,EMAIL);
        Insert.InsertManager(daoMock);
        assertTrue(outContent.toString().contains("Email già esistente"));
    }

    @Test
    void TC_1_2_5_UsernameErrato(){
        simulaInput(NOME,COGNOME,EMAIL,"mr");
        Insert.InsertManager(daoMock);
        assertTrue(outContent.toString().contains("Username non valido"));

    }

    @Test
    void TC_1_2_6_UsernameEsistente(){
        when(daoMock.existsUsername(USERNAME)).thenReturn(true);
        simulaInput(NOME,COGNOME,EMAIL,USERNAME);
        Insert.InsertManager(daoMock);
        assertTrue(outContent.toString().contains("Username già esistente"));
    }

    @Test
    void TC_1_2_7_PasswordErrato(){
        simulaInput(NOME,COGNOME,EMAIL,USERNAME,"Mariorossi1");
        Insert.InsertManager(daoMock);
        assertTrue(outContent.toString().contains("Password non valida"));
    }


}
