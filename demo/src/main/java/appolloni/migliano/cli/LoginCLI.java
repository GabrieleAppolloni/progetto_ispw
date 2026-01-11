package appolloni.migliano.cli;

import java.util.Scanner;

import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.controller.ControllerLogin;

public class LoginCLI {

    private final ControllerLogin controller;
    private final Scanner scanner;

    public LoginCLI() {
        this.controller = new ControllerLogin();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("\n--- LOGIN UTENTE ---");
        
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        System.out.print("Password: ");
        String password = scanner.nextLine();

       
        BeanUtenti beanLogin = new BeanUtenti(null, null, null, null, null,null);
        beanLogin.setEmail(email);
        beanLogin.setPassword(password);

        try {
        
            BeanUtenti utenteLoggato = controller.verificaUtente(beanLogin);
            
            System.out.println("\n Login effettuato! Benvenuto " + utenteLoggato.getName());
            if(utenteLoggato.getTipo().equals("Studente")){
             apriMenuPrincipale(utenteLoggato);
            }else{
                apriMenuHost(utenteLoggato);
            }
            
        } catch (Exception e) {
            
            System.err.println("\n Errore di accesso: " + e.getMessage());
            System.out.println("Riprova oppure scrivi 'esci' per tornare alla home.");
            if (!scanner.nextLine().equalsIgnoreCase("esci")) {
                start();
            }
        }
    }

    private void apriMenuPrincipale(BeanUtenti utente) {
       
        MenuPrincipaleCLI menu = new MenuPrincipaleCLI(utente);
        menu.start();
    }

    private void apriMenuHost(BeanUtenti utente){
        HostMenuCLI hostMenu = new HostMenuCLI(utente);
        hostMenu.start();
    }
}
