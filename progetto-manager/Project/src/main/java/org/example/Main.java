package org.example;

import storage.Manager;
import storage.ManagerDAO;

import java.util.Scanner;

public class Main {
    public static ManagerDAO service = new ManagerDAO();

    public static void main(String[] args) {
        System.out.println("Welcome Administrator");
        int scelta;
        Scanner sc = new Scanner(System.in);


        do {
            try {
                System.out.println("Scegli l'operazione da svolgere");
                System.out.println("0|          Esci\n" +
                        "1|             Inserisci Manager");

                scelta = sc.nextInt();
                switch (scelta) {
                    case 0:
                        break;
                    case 1: // Register Manager
                        System.out.println("Inserimento Manager");
                        System.out.print("Inserisci nome");
                        String nome = sc.next();
                        //Validation name
                        if (!Manager.validateNome(nome)) {
                            System.out.println("Nome non valido");
                            break;
                        }
                        //Validation surname
                        System.out.print("Inserisci cognome");
                        String cognome = sc.next();
                        if (!Manager.validateCognome(cognome)) {
                            System.out.println("Cognome non valido");
                            break;
                        }

                        //Validation email
                        System.out.print("Inserisci email");
                        String email = sc.next();
                        if (!Manager.validateEmail(email)) {
                            System.out.println("Email non valida");
                            break;
                        }
                        //Control if already exists email
                        if (service.existsEmail(email)) {
                            System.out.println("Email già esistente");
                            break;
                        }
                        //Validation username
                        System.out.print("Inserisci username");
                        String username = sc.next();

                        if (!Manager.validateUsername(username)) {
                            System.out.println("Username non valido");
                            break;
                        }

                        //Control if already exists username
                        if (service.existsUsername(username)) {
                            System.out.println("Username già esistente");
                            break;
                        }
                        System.out.print("Inserisci password");
                        String password = sc.next();
                        if (!Manager.validatePassword(password)) {
                            System.out.println("Password non valida");
                            break;
                        }
                        //Create manager
                        Manager manager = new Manager(nome, cognome, username, email, password);
                        manager.setPasswordHash(password); //Set a passwordHash

                        boolean sucess = service.insertManager(manager);  //save manager into db
                        if (sucess) {
                            System.out.println("Inserimento Completato");
                            break;
                        }
                }
            } catch (NumberFormatException e) {
                System.out.println("Formato inserito non valido");
                break;
            }


        } while (scelta != 0);

    }
}