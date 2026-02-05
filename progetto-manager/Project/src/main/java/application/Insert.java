package application;

import storage.Manager;
import storage.ManagerDAO;

import java.util.Scanner;

/**
 * Servlet to insert manager
 */
public class Insert {

    public static Scanner sc = new Scanner(System.in);

    public static boolean InsertManager(ManagerDAO service) {

        try {

            System.out.println("Inserimento Manager");
            System.out.print("Inserisci nome");
            String nome = sc.next().trim();
            //Validation name
            if (!Manager.validateNome(nome)) {
                System.out.println("Nome non valido");
                return false;
            }
            //Validation surname
            System.out.print("Inserisci cognome");
            String cognome = sc.next().trim();
            if (!Manager.validateCognome(cognome)) {
                System.out.println("Cognome non valido");
                return false;
            }

            //Validation email
            System.out.print("Inserisci email");
            String email = sc.next().trim();
            if (!Manager.validateEmail(email)) {
                System.out.println("Email non valida");
                return false;
            }
            //Control if already exists email
            if (service.existsEmail(email)) {
                System.out.println("Email già esistente");
                return false;
            }
            //Validation username
            System.out.print("Inserisci username");
            String username = sc.next().trim();

            if (!Manager.validateUsername(username)) {
                System.out.println("Username non valido");
                return false;
            }

            //Control if already exists username
            if (service.existsUsername(username)) {
                System.out.println("Username già esistente");
                return false;
            }
            System.out.print("Inserisci password");
            String password = sc.next().trim();
            if (!Manager.validatePassword(password)) {
                System.out.println("Password non valida");
                return false;
            }
            //Create manager
            Manager manager = new Manager(nome, cognome, username, email, password);
            manager.setPasswordHash(password); //Set a passwordHash

            boolean sucess = service.insertManager(manager);  //save manager into db
            if (sucess) {
                System.out.println("Inserimento completato");
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Si è verificato un errore");
        }
        return false;
    }

    // METODO PER LA GUI (Usato dalla AdministratorInterface)
    public static String insertManagerGUI(ManagerDAO service, Manager m) {
        if (!Manager.validateNome(m.getNome())) return "Nome non valido";
        if (!Manager.validateCognome(m.getCognome())) return "Cognome non valido";
        if (!Manager.validateEmail(m.getEmail())) return "Email non valida";
        if (service.existsEmail(m.getEmail())) return "Email già esistente";
        if (!Manager.validateUsername(m.getUsername())) return "Username non valido";
        if (service.existsUsername(m.getUsername())) return "Username già esistente";
        if (!Manager.validatePassword(m.getPassword())) return "Password non valida";

        m.setPasswordHash(m.getPassword());
        return service.insertManager(m) ? "OK" : "Errore database";
    }
}
