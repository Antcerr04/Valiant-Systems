package application;

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

public class DeleteManagerTest {

    private ManagerDAO daoMock;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    //Data of default valid

    private final String EMAIL= "mariorossi@gmail.com";

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

        Delete.sc= new Scanner(System.in);

    }


    /**
     * Method used to test when email isn't correct
     */
    @Test
    void TC_1_6_1_EmailErrato(){
        simulaInput("mariorossigmail.com");
        Delete.removeManager(daoMock);
        assertTrue(outContent.toString().contains("Email non valida"));
    }

    /**
     * Method used to test when email doesn't exist
     */
    @Test
    void TC_1_6_2_EmailInesistente(){
        when(daoMock.isManager(EMAIL)).thenReturn(false);
        simulaInput(EMAIL);
        Delete.removeManager(daoMock);
        assertTrue(outContent.toString().contains("Nessun account manager associato a quell'email"));
    }

    /**
     * Method used to test an error into Database
     */
    @Test
    void TC_1_6_3_testErrore(){
        when(daoMock.isManager(EMAIL)).thenReturn(true);
        when(daoMock.deleteManager(EMAIL)).thenReturn(false);
        simulaInput(EMAIL);
        Delete.removeManager(daoMock);
        assertTrue(outContent.toString().contains("Manager not successfully deleted"));
    }

    /**
     * Method used to test a correct remove of the manager
     */
    @Test
    void TC_1_6_4_testDeleteManager(){
        when(daoMock.isManager(EMAIL)).thenReturn(true);
        when(daoMock.deleteManager(EMAIL)).thenReturn(true);
        simulaInput(EMAIL);
        Delete.removeManager(daoMock);
        assertTrue(outContent.toString().contains("Manager successfully deleted"));
    }




}
