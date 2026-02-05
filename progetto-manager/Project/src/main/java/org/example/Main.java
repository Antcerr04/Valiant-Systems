package org.example;

import application.Delete;
import application.Insert;
import storage.Manager;
import storage.ManagerDAO;

import javax.swing.*;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static ManagerDAO service = new ManagerDAO();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Applica il look and feel del sistema operativo (opzionale, ma consigliato)
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

                AdministratorInterface frame = new AdministratorInterface();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}