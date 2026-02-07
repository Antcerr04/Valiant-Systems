package application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.Manager;
import storage.ManagerDAO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class InsertManagerGUITest {

    private ManagerDAO daoMock;
    private Manager manager;

    @BeforeEach
    void setUp() {
        daoMock = mock(ManagerDAO.class);
        manager = new Manager();
        manager.setNome("Mario");
        manager.setCognome("Rossi");
        manager.setEmail("mario@valiant.it");
        manager.setUsername("mario_manager");
        manager.setPassword("Password123!");
    }

    @Test
    void testInsertSuccess() {
        when(daoMock.existsEmail(anyString())).thenReturn(false);
        when(daoMock.existsUsername(anyString())).thenReturn(false);
        when(daoMock.insertManager(any(Manager.class))).thenReturn(true);

        String result = Insert.insertManagerGUI(daoMock, manager);
        assertEquals("OK", result);
    }

    @Test
    void testNomeNonValido() {
        manager.setNome(""); // Nome vuoto o non valido
        String result = Insert.insertManagerGUI(daoMock, manager);
        assertEquals("Nome non valido", result);
    }

    @Test
    void testCognomeNonValido() {
        manager.setCognome(""); // Nome vuoto o non valido
        String result = Insert.insertManagerGUI(daoMock, manager);
        assertEquals("Cognome non valido", result);
    }

    @Test
    void testUsernameNonValido() {
        manager.setUsername(""); // Nome vuoto o non valido
        String result = Insert.insertManagerGUI(daoMock, manager);
        assertEquals("Username non valido", result);
    }

    @Test
    void testEmailNonValida() {
        manager.setEmail(""); // Nome vuoto o non valido
        String result = Insert.insertManagerGUI(daoMock, manager);
        assertEquals("Email non valida", result);
    }

    @Test
    void testEmailGiaEsistente() {

        when(daoMock.existsEmail("mario@valiant.it")).thenReturn(true);

        String result = Insert.insertManagerGUI(daoMock, manager);
        assertEquals("Email già esistente", result);
    }

    @Test
    void testUsernameGiaEsistente() {

        when(daoMock.existsUsername("mario_manager")).thenReturn(true);

        String result = Insert.insertManagerGUI(daoMock, manager);
        assertEquals("Username già esistente", result);
    }

    @Test
    void testErroreDatabase() {
        when(daoMock.existsEmail(anyString())).thenReturn(false);
        when(daoMock.existsUsername(anyString())).thenReturn(false);
        when(daoMock.insertManager(any(Manager.class))).thenReturn(false);

        String result = Insert.insertManagerGUI(daoMock, manager);
        assertEquals("Errore database", result);
    }


    @Test
    void testPasswordNonValida() {
        manager.setPassword("Mario2004"); // Nome vuoto o non valido
        String result = Insert.insertManagerGUI(daoMock, manager);
        assertEquals("Password non valida", result);
    }
}