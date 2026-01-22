package org.example;

import application.Delete;
import application.Insert;
import storage.Manager;
import storage.ManagerDAO;

import java.util.Date;
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
                        "1|             Inserisci Manager\n" +
                        "2|             Elimina Manager");

                scelta = sc.nextInt();
                switch (scelta) {
                    case 0:
                        break;
                    case 1: // Register Manager
                        Insert.InsertManager(Main.service);
                        break;

                    case 2: //Remove Manager
                        Delete.removeManager(Main.service);
                        break;
                    default:
                        System.out.println("Scelta non valida");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Formato inserito non valido");
                break;
            }


        } while (scelta != 0);

    }
}