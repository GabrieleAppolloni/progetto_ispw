package appolloni.migliano.cli;

import java.util.Scanner;

import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.controller.ControllerGestioneUtente;

public class CreazioneUtenteCLI {

    private final ControllerGestioneUtente controller;
    private final Scanner scanner;

    public CreazioneUtenteCLI() {
        this.controller = new ControllerGestioneUtente();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("\n--- REGISTRAZIONE NUOVO UTENTE ---");

        try {
            
            String tipo = selezionaTipoUtente();

 
            System.out.print("Nome: ");
            String nome = scanner.nextLine().trim();
            System.out.print("Cognome: ");
            String cognome = scanner.nextLine().trim();
            System.out.print("Email: ");
            String email = scanner.nextLine().trim().toLowerCase();
            System.out.print("Città: ");
            String citta = scanner.nextLine().trim();
            System.out.print("Password: ");
            String password = scanner.nextLine().trim();

            
            if (nome.isEmpty() || cognome.isEmpty() || email.isEmpty() || citta.isEmpty() || password.isEmpty()) {
                throw new IllegalArgumentException("Tutti i campi sono obbligatori.");
            }

      
            BeanUtenti beanUtente = new BeanUtenti(tipo, nome, cognome, email, password, citta);

      
            if ("Host".equalsIgnoreCase(tipo)) {
                System.out.println("\n--- DATI ATTIVITÀ (Obbligatori per Host) ---");
                System.out.print("Nome Attività: ");
                String nomeAtt = scanner.nextLine().trim();
                
                String tipoAtt = selezionaTipoAttivita();

                if (nomeAtt.isEmpty()) {
                    throw new IllegalArgumentException("L'Host deve specificare il nome dell'attività.");
                }

                beanUtente.setNomeAttivita(nomeAtt);
                beanUtente.setTipoAttivita(tipoAtt);
            }

            
            controller.creazioneUtente(beanUtente);
            System.out.println("\n Registrazione effettuata con successo!");

       
            if ("Host".equalsIgnoreCase(tipo)) {
                System.out.println("Benvenuto Host! Reindirizzamento alla registrazione dei dettagli della struttura...");
                
               
                new CreazioneStruttureCLI(beanUtente).start();
                new HostMenuCLI(beanUtente).start();
                
            } else {
                System.out.println("Benvenuto Studente! Reindirizzamento al menu principale...");
                
                
                new MenuPrincipaleCLI(beanUtente).start();
            }

        } catch (IllegalArgumentException e) {
            System.err.println("\n Errore nei dati inseriti: " + e.getMessage());
            System.out.println("Vuoi riprovare la registrazione? (s/n)");
            if(scanner.nextLine().equalsIgnoreCase("s")) start();
        } catch (Exception e) {
            System.err.println("\n Errore tecnico durante la registrazione: " + e.getMessage());
            
        }
    }

    private String selezionaTipoUtente() {
        while (true) {
            System.out.println("Seleziona il tuo ruolo:");
            System.out.println("1) Studente");
            System.out.println("2) Host");
            System.out.print("Scelta: ");
            String scelta = scanner.nextLine();
            if (scelta.equals("1")) return "Studente";
            if (scelta.equals("2")) return "Host";
            System.out.println("Scelta non valida. Inserisci 1 o 2.");
        }
    }

 
    private String selezionaTipoAttivita() {
        while (true) {
            System.out.println("Seleziona la tipologia di attività:");
            System.out.println("1) Bar");
            System.out.println("2) Università");
            System.out.println("3) Biblioteca");
            System.out.print("Scelta: ");
            String scelta = scanner.nextLine();
            switch (scelta) {
                case "1" -> { return "Bar"; }
                case "2" -> { return "Università"; }
                case "3" -> { return "Biblioteca"; }
                default -> System.out.println("Scelta non valida.");
            }
        }
    }
}