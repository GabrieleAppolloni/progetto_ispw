package appolloni.migliano.cli;

import appolloni.migliano.LeggInputCli;

public class HomeCLI {




    public void start() {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n========================================"); //NOSONAR
            System.out.println("        BENVENUTO IN UNIVERSITY SPOT          "); //NOSONAR
            System.out.println("========================================"); //NOSONAR
            System.out.println("1. Accedi (Login)"); //NOSONAR
            System.out.println("2. Registrati (Crea nuovo utente)"); //NOSONAR
            System.out.println("3. Esci"); //NOSONAR

            String scelta = LeggInputCli.leggiStringa("Scegli un'opzione:");

            switch (scelta) {
                case "1" -> vaiALogin();
                case "2" -> vaiARegistrazione();
                case "3" -> {
                    System.out.println("Chiusura applicazione. Arrivederci!"); //NOSONAR
                    exit = true;
                }
                default -> System.out.println(" Scelta non valida, riprova."); //NOSONAR
            }
        }
    }

    private void vaiALogin() {
        
        System.out.println("\n--- Apertura Login ---"); //NOSONAR
        LoginCLI loginView = new LoginCLI();
        loginView.start();
        
        
    }

    private void vaiARegistrazione() {
        
        System.out.println("\n--- Apertura Registrazione ---"); //NOSONAR
        CreazioneUtenteCLI registrazioneView = new CreazioneUtenteCLI();
        registrazioneView.start();
    }


}
