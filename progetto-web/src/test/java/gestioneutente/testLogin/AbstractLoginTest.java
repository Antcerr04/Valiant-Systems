package gestioneutente.testLogin;

import application.gestioneutente.Login;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import storage.FacadeDAO;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
@Disabled
public class AbstractLoginTest {

        protected Login servlet;
        protected HttpServletRequest request;
        protected HttpServletResponse response;
        protected HttpSession session;
        protected RequestDispatcher dispatcher;
        protected FacadeDAO daoMock;

        @BeforeEach
        void setUp() {
            servlet = new Login();
            request = mock(HttpServletRequest.class);
            response = mock(HttpServletResponse.class);
            session = mock(HttpSession.class);
            dispatcher = mock(RequestDispatcher.class);
            daoMock = mock(FacadeDAO.class);
            servlet.setFaceDAO(daoMock);

            when(request.getSession()).thenReturn(session);
            when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);

            when(request.getParameter("username")).thenReturn("Mario2004");
            when(request.getParameter("password")).thenReturn("Mario2004@");
        }


        /**
         * Method to verify a failure
         *
         * @throws Exception
         */
        private void verifyFailure() throws Exception {
            //Verify the failure (back to register page)
            verify(dispatcher).forward(request, response);
            //Verify that error attribute is setting
            verify(request).setAttribute(eq("errorMSG"), anyString());
        }

        /**
         * Method to Test a failure when user isn't correct
         *
         * @throws Exception
         */
        @Test
        void TC_1_3_1_UsernameErrato() throws Exception {
            when(request.getParameter("username")).thenReturn("Ma");
            servlet.doPost(request, response);
            verifyFailure();
        }

        /**
         * Method to test a failure if not exist a user with a specific username
         *
         * @throws Exception
         */
        @Test
        void TC_1_3_2_UsernameNonCorrispondente() throws Exception {
            String usernameInserito = "Mario200";
            String passwordInserita = "Mario2004@";

            when(request.getParameter("username")).thenReturn(usernameInserito);
            when(request.getParameter("password")).thenReturn(passwordInserita);

            when(daoMock.getUserByCredentials(usernameInserito, passwordInserita)).thenReturn(null);

            servlet.doPost(request, response);

            verify(request).setAttribute(eq("errorMSG"), eq("Username o password incorretti."));

            verify(dispatcher).forward(request, response);

            verify(session, never()).setAttribute(eq("utente"), any());
        }

        /**
         * Method used to test a failure when a password isn't correct
         *
         * @throws Exception
         */
        @Test
        void TC_1_3_3_PasswordErrata() throws Exception {
            when(request.getParameter("password")).thenReturn("Mario2004");
            servlet.doPost(request, response);
            verifyFailure();
        }

        /**
         * Method used to test a failure when not exist a user with a specific password
         *
         * @throws Exception
         */
        @Test
        void TC_1_3_4_PasswordNonCorrispondente() throws Exception {
            String usernameInserito = "Mario2004";
            String passwordInserita = "Mario2004@@";

            when(request.getParameter("username")).thenReturn(usernameInserito);
            when(request.getParameter("password")).thenReturn(passwordInserita);

            when(daoMock.getUserByCredentials(usernameInserito, passwordInserita)).thenReturn(null);

            servlet.doPost(request, response);

            verify(request).setAttribute(eq("errorMSG"), eq("Username o password incorretti."));

            verify(dispatcher).forward(request, response);

            verify(session, never()).setAttribute(eq("utente"), any());
        }

        /**
         * Method used to test an error in DB
         * @throws Exception
         */
        @Test
        void TC_1_3_5_LoginErroreImprevisto() throws Exception {
            //Force DAO to throw a generics exception
            when(daoMock.getUserByCredentials(anyString(), anyString()))
                    .thenThrow(new RuntimeException("Errore DB"));

            servlet.doPost(request, response);

            //Verify that we are in the catch
            verify(request).setAttribute(eq("exception"), any(RuntimeException.class));
            verify(dispatcher).forward(request, response);
        }

}
