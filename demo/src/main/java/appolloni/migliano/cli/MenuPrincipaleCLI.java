package appolloni.migliano.cli;

import java.util.List;
import java.util.Scanner;

import appolloni.migliano.bean.BeanGruppo;
import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.controller.ControllerGestioneGruppo;

public class MenuPrincipaleCLI {

    private final Scanner scanner;
    private final BeanUtenti bean;
    private final ControllerGestioneGruppo gestioneGruppi;

    public MenuPrincipaleCLI(BeanUtenti utente) {
        this.scanner = new Scanner(System.in);
        this.bean = utente;
        this.gestioneGruppi = new ControllerGestioneGruppo();
    }

    public void start() {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n========================================");
            System.out.println("          STUDIO-APP: HOME             ");
            System.out.println("========================================");
            System.out.println("Ciao, " + bean.getName() + "!");
            System.out.println("----------------------------------------");

            List<BeanGruppo> gruppiUtente = null;

            
            try {
                gruppiUtente = gestioneGruppi.visualizzaGruppi(bean);
                
                System.out.println("I TUOI GRUPPI:");
                if (gruppiUtente == null || gruppiUtente.isEmpty()) {
                    System.out.println("[ Nessun gruppo iscritto ]");
                } else {
                    for (int i = 0; i < gruppiUtente.size(); i++) {
                        System.out.println((i + 1) + ") Entra nella Chat di: " + gruppiUtente.get(i).getNome());
                    }
                }
            } catch (Exception e) {
              
                System.err.println(" Errore nel caricamento dei gruppi: " + e.getMessage());
            }

            System.out.println("----------------------------------------");
            System.out.println("AZIONI:");
            System.out.println("R) Cerca Gruppi o Strutture");
            System.out.println("P) Visualizza Profilo");
            System.out.println("S) Segnala Struttura");
            System.out.println("L) Logout");
            System.out.print("\n Scelta: ");

            String scelta = scanner.nextLine().toUpperCase();

           
            if (scelta.matches("\\d+")) { 
                gestioneSceltaChat(scelta, gruppiUtente);
            } else {
                switch (scelta) {
                    case "R" -> new RicercaCLI(bean).start();
                    case "P" -> new ProfiloUtenteCLI(bean).start();
                    case "G" -> {
                       new CreazioneGruppoCLI(bean).start();
                    }
                    case "S" -> {
                       new SegnalaStrutturaCLI(bean).start();
                    }
                    case "L" -> {
                        System.out.println("Logout effettuato. Arrivederci!");
                        exit = true; 
                    }
                    default -> System.out.println("Scelta non valida.");
                }
            }
        }
    }

    private void gestioneSceltaChat(String scelta, List<BeanGruppo> gruppi) {
        int indice = Integer.parseInt(scelta) - 1;
        if (gruppi != null && indice >= 0 && indice < gruppi.size()) {
            BeanGruppo selezionato = gruppi.get(indice);
            System.out.println("\n--- Apertura Chat: " + selezionato.getNome() + " ---");
            new ChatCLI(bean, selezionato).start();
        } else {
            System.out.println("Indice gruppo non valido.");
        }
    }
}