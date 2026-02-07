package application;

import storage.Manager;
import storage.ManagerDAO;

import java.util.Scanner;

/**
 * Servlet to delete Manager
 */
public class Delete {
    public static Scanner sc = new Scanner(System.in);

    public static boolean removeManager(ManagerDAO dao) {
        String email;
        try {

            System.out.println("Inserisci email del manager");
            email = sc.nextLine().trim();
        } catch (Exception e) {
            System.out.println("Formato dati non corretto");
            return false;
        }

        //Validation

        if (!Manager.validateEmail(email)) {
            System.out.println("Email non valida");
            return false;
        }

        if(dao.isManager(email)) {
            boolean sucess = dao.deleteManager(email);

            if (sucess) {
                //If are all correct
                System.out.println("Manager successfully deleted");
                return true;
            } else {
                System.out.println("Manager not successfully deleted");
                return false;
            }
        }
        else {
            System.out.println("Nessun account manager associato a quell'email");
            return false;
        }



    }
    public static boolean removeManager(ManagerDAO dao, String email) {
        if (!Manager.validateEmail(email)) return false;
        return dao.deleteManager(email);
    }
}
