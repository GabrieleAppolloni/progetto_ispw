package appolloni.migliano.cli;

import java.util.Arrays;

import appolloni.migliano.LeggInputCli;
import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.controller.ControllerLogin;

public class LoginCLI {

    private final ControllerLogin controller;

    public LoginCLI() {
        this.controller = new ControllerLogin();
    }

    public void start() {
        System.out.println("\n--- LOGIN UTENTE ---"); //NOSONAR
        
        String email = LeggInputCli.leggiStringa("Email: ");
        
        System.out.print("Password (I caratteri inseriti non saranno visibili): "); //NOSONAR
        String password = "";
        char[] passwordChars = System.console().readPassword();
        password = new String(passwordChars);
        Arrays.fill(passwordChars, ' '); 
        
        if (email.isEmpty() || password.isEmpty()) {
            System.err.println("Errore: informazioni mancanti!"); //NOSONAR
            start();
            return;
        }

        BeanUtenti beanLogin = new BeanUtenti(null, null, null, null, null, null);
        beanLogin.setEmail(email);
        beanLogin.setPassword(password);

        try {
            BeanUtenti utenteLoggato = controller.verificaUtente(beanLogin);
            
            if (utenteLoggato != null) {
                System.out.println("\nLogin effettuato! Benvenuto " + utenteLoggato.getName()); //NOSONAR
                reindirizzaUtente(utenteLoggato);
            }
            
        } catch (Exception e) {
            System.err.println("\n Errore di accesso: " + e.getMessage()); //NOSONAR
            System.out.println("Riprova oppure scrivi 'esci' per terminare."); //NOSONAR
            String comando =LeggInputCli.leggiStringa("");
            if (!comando.equalsIgnoreCase("esci")) {
                start();
            }
        }
    }

    private void reindirizzaUtente(BeanUtenti utente) {
        if ("Studente".equalsIgnoreCase(utente.getTipo())) {
            System.out.println("[Sistema] Caricamento Menu Studente..."); //NOSONAR
            new MenuPrincipaleCLI(utente).start();
        } else {
            System.out.println("[Sistema] Caricamento Menu Host..."); //NOSONAR
            new HostMenuCLI(utente).start();
        }
    }
}
