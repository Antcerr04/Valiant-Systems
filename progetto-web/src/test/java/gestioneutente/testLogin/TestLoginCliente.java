package gestioneutente.testLogin;

import org.junit.jupiter.api.Test;
import storage.gestioneutente.Cliente;
import storage.gestioneutente.Indirizzo;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class TestLoginCliente extends AbstractLoginTest{
    @Test
    void TC_1_3_6_LoginSuccessoCliente() throws Exception {
        String user = "Mario2004";
        String pass = "Mario2004@";
        Indirizzo indirizzo = new Indirizzo("via Rufigliano", 10, 84022, "Campania", "Salerno", "Salerno");


        Cliente utenteMock = mock(Cliente.class);


        when(utenteMock.getUsername()).thenReturn(user);
        when(utenteMock.getPassword()).thenReturn(pass);
        when(utenteMock.getIndirizzo()).thenReturn(indirizzo); // Serve per la riga dell'indirizzo

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
