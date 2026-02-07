package application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.Manager;
import storage.ManagerDAO;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DeleteManagerGUITest {
    private ManagerDAO daoMock;
    private Manager manager;

    @BeforeEach
    void setUp() {
        daoMock = mock(ManagerDAO.class);
        manager = new Manager();
        manager.setEmail("mariorossi@gmail.com");
    }


    @Test
    void testEmailNonValida() {
        manager.setEmail("mariorossigmail.com");
        boolean result = Delete.removeManager(daoMock, manager.getEmail());
        assertFalse( result);
    }

    @Test
    void testDeleteManager() {
        when(daoMock.deleteManager(anyString())).thenReturn(true);
       boolean result = Delete.removeManager(daoMock, manager.getEmail());
       assertTrue(result);
    }


}
