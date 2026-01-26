package gestioneutente.testLogin;

import org.junit.jupiter.api.Test;
import storage.gestioneutente.Cliente;
import storage.gestioneutente.Indirizzo;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class TestLoginCliente extends AbstractLoginTest {
    private static final String user = "Mario04";
    private static final String pass = "Mario200@";

    /**
     * Method used to test a correct Client login
     * @throws Exception
     */
    @Test
    void TC_1_3_6_LoginSuccessoCliente() throws Exception {

        Indirizzo indirizzo = new Indirizzo("via Rufigliano", 10, 84022, "Campania", "Salerno", "Salerno");


        Cliente utenteMock = mock(Cliente.class);


        when(utenteMock.getUsername()).thenReturn(user);
        when(utenteMock.getPassword()).thenReturn(pass);
        when(utenteMock.getIndirizzo()).thenReturn(indirizzo);

        when(request.getParameter("username")).thenReturn(user);
        when(request.getParameter("password")).thenReturn(pass);
        when(request.getSession()).thenReturn(session);


        when(daoMock.getUserByCredentials(user, pass)).thenReturn(utenteMock);

        servlet.doPost(request, response);


        verify(session).setAttribute(eq("utente"), eq(utenteMock));


        verify(session).setAttribute(eq("indirizzo"), eq(indirizzo));


        verify(response).addCookie(argThat(cookie ->
                cookie.getName().equals("notification") &&
                        cookie.getValue().contains("Benvenuto-" + user)));

        verify(response).sendRedirect(eq("index.jsp"));

        verify(dispatcher, never()).forward(request, response);
    }
}
