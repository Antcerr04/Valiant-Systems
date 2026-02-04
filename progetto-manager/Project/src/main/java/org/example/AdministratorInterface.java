package org.example;

import application.Delete;
import application.Insert;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import storage.Manager;
import storage.ManagerDAO;
import storage.ConPool;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AdministratorInterface extends JFrame {
    private JPanel MyPanel;
    private JLabel label_welcome;
    private JLabel label_managerName;
    private JLabel label_managerSurname;
    private JLabel label_managerEmail;
    private JLabel label_managerUsername;
    private JLabel label_managerPassword;
    private JTextField textFieldNameUsername; // Corrisponde al Nome nel tuo form
    private JTextField textFieldCognomeManager;
    private JTextField textFieldEmailManager;
    private JTextField textFieldUsernameManager;
    private JTextField textFieldPasswordManager;
    private JButton inserisciMangerButton;
    private JButton eliminaManagerButton;

    // Servizio per il database
    private ManagerDAO service = new ManagerDAO();

    public AdministratorInterface() {
        $$$setupUI$$$();
        // Configurazione della finestra
        setTitle("Valiant Systems - Administrator Panel");
        setContentPane(MyPanel); // Collega il pannello disegnato nel .form
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        pack(); // Adatta la dimensione al contenuto disegnato
        setLocationRelativeTo(null);

        // --- AZIONE INSERISCI ---
        inserisciMangerButton.addActionListener(e -> {
            // 1. Creiamo l'oggetto Manager con i dati del form
            Manager nuovoManager = new Manager();
            nuovoManager.setNome(textFieldNameUsername.getText().trim()); // Attenzione al nome della variabile
            nuovoManager.setCognome(textFieldCognomeManager.getText().trim());
            nuovoManager.setEmail(textFieldEmailManager.getText().trim());
            nuovoManager.setUsername(textFieldUsernameManager.getText().trim());
            nuovoManager.setPassword(textFieldPasswordManager.getText().trim());

            // 2. Chiamiamo la logica di inserimento
            String esito = Insert.insertManagerGUI(service, nuovoManager);

            // 3. Gestiamo la risposta graficamente
            if (esito.equals("OK")) {
                JOptionPane.showMessageDialog(this, "Inserimento completato con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                pulisciCampi(); // Il metodo che hai già creato per svuotare i campi
            } else {
                JOptionPane.showMessageDialog(this, esito, "Attenzione", JOptionPane.WARNING_MESSAGE);
            }
        });

        // --- AZIONE ELIMINA ---
        eliminaManagerButton.addActionListener(e -> {
            // 1. Recupero l'email dal campo di testo che hai già nel form
            String email = textFieldEmailManager.getText().trim();

            // 2. Controllo rapido se il campo è vuoto
            if (email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Per favore, inserisci l'email del manager da eliminare.", "Campo mancante", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 3. Chiedo conferma (sempre meglio per le eliminazioni!)
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Sei sicuro di voler eliminare il manager con email: " + email + "?",
                    "Conferma eliminazione", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                // 4. Chiamo il metodo della classe Delete
                boolean success = Delete.removeManager(service, email);

                if (success) {
                    JOptionPane.showMessageDialog(this, "Manager eliminato con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                    textFieldEmailManager.setText(""); // Pulisco il campo
                } else {
                    JOptionPane.showMessageDialog(this, "Impossibile eliminare: email non valida o manager non trovato.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // --- GESTIONE CHIUSURA (Addio Warning!) ---
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    // 1. Chiudi il pool di connessioni
                    ConPool.closePool();

                    // 2. Forza la chiusura dei thread MySQL rimasti appesi
                    AbandonedConnectionCleanupThread.checkedShutdown();
                } finally {
                    // 3. Termina il processo JVM definitivamente
                    System.exit(0);
                }
            }
        });
    }

    private void pulisciCampi() {
        textFieldNameUsername.setText("");
        textFieldCognomeManager.setText("");
        textFieldEmailManager.setText("");
        textFieldUsernameManager.setText("");
        textFieldPasswordManager.setText("");
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        MyPanel = new JPanel();
        MyPanel.setLayout(new GridLayoutManager(6, 12, new Insets(0, 0, 0, 0), -1, -1));
        label_welcome = new JLabel();
        label_welcome.setText("Benvenuto Amministratore, Scegli l'operazione:");
        MyPanel.add(label_welcome, new GridConstraints(0, 0, 1, 7, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        label_managerName = new JLabel();
        label_managerName.setText("Nome Manager : ");
        MyPanel.add(label_managerName, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        label_managerSurname = new JLabel();
        label_managerSurname.setText("Cognome del manager: ");
        MyPanel.add(label_managerSurname, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        label_managerEmail = new JLabel();
        label_managerEmail.setText("Email Manager :");
        MyPanel.add(label_managerEmail, new GridConstraints(3, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        label_managerUsername = new JLabel();
        label_managerUsername.setText("Username Manager :");
        MyPanel.add(label_managerUsername, new GridConstraints(4, 0, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        label_managerPassword = new JLabel();
        label_managerPassword.setText("Password Manager :");
        MyPanel.add(label_managerPassword, new GridConstraints(5, 0, 1, 5, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textFieldNameUsername = new JTextField();
        MyPanel.add(textFieldNameUsername, new GridConstraints(1, 1, 1, 5, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textFieldCognomeManager = new JTextField();
        MyPanel.add(textFieldCognomeManager, new GridConstraints(2, 2, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textFieldEmailManager = new JTextField();
        MyPanel.add(textFieldEmailManager, new GridConstraints(3, 3, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textFieldUsernameManager = new JTextField();
        MyPanel.add(textFieldUsernameManager, new GridConstraints(4, 4, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textFieldPasswordManager = new JTextField();
        MyPanel.add(textFieldPasswordManager, new GridConstraints(5, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        eliminaManagerButton = new JButton();
        eliminaManagerButton.setText("Elimina");
        MyPanel.add(eliminaManagerButton, new GridConstraints(0, 8, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        inserisciMangerButton = new JButton();
        inserisciMangerButton.setText("Inserisci");
        MyPanel.add(inserisciMangerButton, new GridConstraints(0, 7, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return MyPanel;
    }

}