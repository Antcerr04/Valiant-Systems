package gestioneutente;

import application.gestioneutente.Modifica;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import storage.FacadeDAO;
import storage.gestioneutente.Cliente;
import storage.gestioneutente.Indirizzo;

import static org.mockito.Mockito.*;

public class testUpdate {
    private Modifica servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;
    private FacadeDAO daoMock;

    /**
     * A scenario with all correct data
     */
    @BeforeEach
    public void setUp() {
        servlet = new Modifica();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);
        daoMock = mock(FacadeDAO.class);
        servlet.setFaceDAO(daoMock);

        Indirizzo indirizzomock = new Indirizzo("Via Vittorio Emanuele", 10, 84023, "Campania", "Salerno", "Salerno");
        Cliente utentemock = new Cliente("Mario", "Rossi", "Mar04", "Mariorossi@gmail.com", "Mario2004@", indirizzomock);

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("source")).thenReturn("update");
        when(session.getAttribute("utente")).thenReturn(utentemock);
        when(session.getAttribute("indirizzo")).thenReturn(indirizzomock);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("name")).thenReturn("Mario");
        when(request.getParameter("surname")).thenReturn("Rossi");
        when(request.getParameter("via")).thenReturn("Via Vittorio Emanuele");
        when(request.getParameter("house-number")).thenReturn("10");
        when(request.getParameter("cap")).thenReturn("84023");
        when(request.getParameter("city")).thenReturn("Salerno");
        when(request.getParameter("provincia")).thenReturn("Salerno");
        when(request.getParameter("regione")).thenReturn("Campania");

        //Mock of the dispatcher (used in the error cases)
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
    }


    /**
     * Method used to test a failure when name isn't correct
     *
     * @throws Exception
     */
    @Test
    void TC_1_4_1_NomeErrato() throws Exception {
        //Override only the parameter name
        when(request.getParameter("name")).thenReturn("M@rio");
        servlet.doPost(request, response);
        //verify the dispatcher
        verify(dispatcher).forward(request, response);
        //Verify that error attribute is setting
        verify(request).setAttribute(eq("errorMSG"), eq("Nome o cognome non corretti"));
    }

    /**
     * Method used to test a failure when surname isn't correct
     *
     * @throws Exception
     */
    @Test
    void TC_1_4_2_CognomeErrato() throws Exception {
        //Override only the parameter surname

        when(request.getParameter("name")).thenReturn("R0ssi");
        servlet.doPost(request, response);
        //verify the dispatcher
        verify(dispatcher).forward(request, response);
        //Verify that error attribute is setting
        verify(request).setAttribute(eq("errorMSG"), eq("Nome o cognome non corretti"));
    }

    /**
     * Method used to test a failure when regione isn't correct
     *
     * @throws Exception
     */
    @Test
    void TC_1_4_3_ReginoneErrato() throws Exception {
        //Override only the parameter regione

        when(request.getParameter("regione")).thenReturn("");
        servlet.doPost(request, response);
        //verify the dispatcher
        verify(dispatcher).forward(request, response);
        //Verify that error attribute is setting
        verify(request).setAttribute(eq("errorMSG"), eq("Dati indirizzo non validi"));
    }

    /**
     * Method used to test a failure when provincia isn't correct
     *
     * @throws Exception
     */
    @Test
    void TC_1_4_4_ProvinciaErrato() throws Exception {
        //Override only the parameter provincia

        when(request.getParameter("provincia")).thenReturn("");
        servlet.doPost(request, response);
        //verify the dispatcher
        verify(dispatcher).forward(request, response);
        //Verify that error attribute is setting
        verify(request).setAttribute(eq("errorMSG"), eq("Dati indirizzo non validi"));
    }

    /**
     * Method to test a failure when via isn't correct
     *
     * @throws Exception
     */
    @Test
    void TC_1_4_5_ViaErrato() throws Exception {
        //Override only the parameter via

        when(request.getParameter("via")).thenReturn("V1a Vittorio Emanuele");
        servlet.doPost(request, response);
        //verify the dispatcher
        verify(dispatcher).forward(request, response);
        //Verify that error attribute is setting
        verify(request).setAttribute(eq("errorMSG"), eq("Dati indirizzo non validi"));
    }

    /**
     * Method used to test a failure when numero civico isn't correct
     *
     * @throws Exception
     */
    @Test
    void TC_1_4_6_NumCivicoErrato() throws Exception {
        //Override only the parameter numero civico

        when(request.getParameter("house-number")).thenReturn("1o");
        servlet.doPost(request, response);
        //verify the dispatcher
        verify(dispatcher).forward(request, response);
        //Verify that error attribute is setting
        verify(request).setAttribute(eq("errorMSG"), eq("Errore non puoi inserire una lettera nel numero civico"));
    }

    /**
     * Method used to test a failure when cap isn't correct
     *
     * @throws Exception
     */
    @Test
    void TC_1_4_7_capErrato() throws Exception {
        //Override only the parameter numero civico

        when(request.getParameter("cap")).thenReturn("84o23");
        servlet.doPost(request, response);
        //verify the dispatcher
        verify(dispatcher).forward(request, response);
        //Verify that error attribute is setting
        verify(request).setAttribute(eq("errorMSG"), eq("Errore non puoi inserire una lettera nel numero civico"));
    }

    /**
     * Method used to test when città isnt't correct
     *
     * @throws Exception
     */

    @Test
    void TC_1_4_8_cittòErrato() throws Exception {
        //Override only the parameter numero civico

        when(request.getParameter("city")).thenReturn("Salern0");
        servlet.doPost(request, response);
        //verify the dispatcher
        verify(dispatcher).forward(request, response);
        //Verify that error attribute is setting
        verify(request).setAttribute(eq("errorMSG"), eq("Dati indirizzo non validi"));
    }

}
