package appolloni.migliano.cli;

import appolloni.migliano.DBConnection;


public class MainCLI {
    public static void main(String[] args) {
        System.out.println("Avvio applicazione in modalità CLI...");

        try {
            
            DBConnection.getInstance().getConnection(); 
            HomeCLI home = new HomeCLI();
            home.start();

        } catch (Exception e) {
            System.err.println("Errore fatale durante l'avvio: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBConnection.getInstance().closeConnection();
            System.out.println("Connessione DB chiusa. Applicazione terminata.");
        }
    }
}