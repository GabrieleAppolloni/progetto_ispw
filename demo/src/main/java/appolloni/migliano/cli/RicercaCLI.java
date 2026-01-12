package appolloni.migliano.cli;

import java.util.List;
import java.util.Scanner;

import appolloni.migliano.bean.BeanGruppo;
import appolloni.migliano.bean.BeanStruttura;
import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.controller.ControllerGestioneGruppo;
import appolloni.migliano.controller.ControllerGestioneStrutture;
import appolloni.migliano.controller.ControllerGestioneUtente;

public class RicercaCLI {

    private final ControllerGestioneGruppo controllerGruppo;
    private final ControllerGestioneStrutture controllerStrutture;
    private final Scanner scanner;
    private final BeanUtenti beanUtente; // Aggiunto per gestire l'utente loggato

    public RicercaCLI(BeanUtenti beanUtente) {
        this.controllerGruppo = new ControllerGestioneGruppo();
        this.controllerStrutture = new ControllerGestioneStrutture();
        this.scanner = new Scanner(System.in);
        this.beanUtente = beanUtente;
    }

    public void start() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- MENU RICERCA ---");
            System.out.println("1. Cerca Gruppi di Studio");
            System.out.println("2. Cerca Strutture");
            System.out.println("3. Torna al menu precedente");
            System.out.print("Scelta: ");

            String scelta = scanner.nextLine();

            switch (scelta) {
                case "1" -> gestioneRicercaGruppi();
                case "2" -> gestioneRicercaStrutture();
                case "3" -> back = true;
                default -> System.out.println("Scelta non valida.");
            }
        }
    }

    private void gestioneRicercaGruppi() {
        System.out.println("\n--- RICERCA GRUPPI ---");
        System.out.print("Filtra per nome (vuoto per skip): ");
        String nome = promptInput();
        System.out.print("Filtra per città (vuoto per skip): ");
        String citta = promptInput();
        System.out.print("Filtra per materia (vuoto per skip): ");
        String materia = promptInput();

        try {
            List<BeanGruppo> risultati = controllerGruppo.cercaGruppi(nome, citta, materia);
            if (risultati.isEmpty()) {
                System.out.println("Nessun gruppo trovato.");
            } else {
                mostraRisultatiGruppi(risultati);
                gestioneSelezioneGruppo(risultati); // Aggiunta logica "Unisci"
            }
        } catch (Exception e) {
            System.err.println("Errore durante la ricerca: " + e.getMessage());
        }
    }

    private void gestioneRicercaStrutture() {
        System.out.println("\n--- RICERCA STRUTTURE ---");
        System.out.print("Nome struttura (vuoto per skip): ");
        String nome = promptInput();
        System.out.print("Città (vuoto per skip): ");
        String citta = promptInput();
        System.out.print("Tipo (Tutti, Bar, Biblioteca, Università): ");
        String tipo = promptInput();

        try {
            List<BeanStruttura> risultati = controllerStrutture.cercaStrutture(nome, citta, tipo);
            if (risultati.isEmpty()) {
                System.out.println("Nessuna struttura trovata.");
            } else {
                mostraRisultatiStrutture(risultati);
                gestioneSelezioneStruttura(risultati); // Aggiunta logica "Dettagli"
            }
        } catch (Exception e) {
            System.err.println("Errore durante la ricerca: " + e.getMessage());
        }
    }

    private void mostraRisultatiGruppi(List<BeanGruppo> risultati) {
        System.out.println("\nGRUPPI TROVATI:");
        for (int i = 0; i < risultati.size(); i++) {
            BeanGruppo g = risultati.get(i);
            System.out.printf("%d) %-20s | %-15s | %-15s%n", (i + 1), g.getNome(), g.getCitta(), g.getMateria());
        }
    }

    private void gestioneSelezioneGruppo(List<BeanGruppo> risultati) {
        System.out.print("\nInserisci il numero del gruppo per unirti (o 0 per annullare): ");
        try {
            int indice = Integer.parseInt(scanner.nextLine()) - 1;
            if (indice >= 0 && indice < risultati.size()) {
                controllerGruppo.aggiungiGruppo(beanUtente, risultati.get(indice));
                System.out.println("[OK] Ti sei unito al gruppo con successo!");
            }
        } catch (Exception e) {
            System.out.println("Operazione non riuscita: " + e.getMessage());
        }
    }

    private void mostraRisultatiStrutture(List<BeanStruttura> risultati) {
        System.out.println("\nSTRUTTURE TROVATE:");
        for (int i = 0; i < risultati.size(); i++) {
            BeanStruttura s = risultati.get(i);
            System.out.println((i + 1) + ") " + s.getName() + " [" + s.getTipoAttivita() + "] - " + s.getCitta());
        }
    }

    private void gestioneSelezioneStruttura(List<BeanStruttura> risultati) {
        System.out.print("\nInserisci il numero della struttura per i dettagli (o 0 per annullare): ");
        try {
            int indice = Integer.parseInt(scanner.nextLine()) - 1;
            if (indice >= 0 && indice < risultati.size()) {
                visualizzaDettagliStruttura(risultati.get(indice));
            }
        } catch (Exception e) {
            System.out.println("Scelta non valida.");
        }
    }

    private void visualizzaDettagliStruttura(BeanStruttura s) {
        System.out.println("\n--- DETTAGLI STRUTTURA ---");
        System.out.println("Nome:       " + s.getName());
        System.out.println("Città:      " + s.getCitta());
        System.out.println("Indirizzo:  " + s.getIndirizzo());
        System.out.println("Tipo:       " + s.getTipoAttivita());
        System.out.println("Orario:     " + s.getOrario());
        System.out.println("WiFi:       " + (s.hasWifi() ? "Sì" : "No"));
        System.out.println("Ristorazione: " + (s.hasRistorazione() ? "Sì" : "No"));
        System.out.println("--------------------------");
        System.out.println("Premere INVIO per tornare ai risultati.");
        scanner.nextLine();
    }

    private String promptInput() {
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? null : input;
    }
}
