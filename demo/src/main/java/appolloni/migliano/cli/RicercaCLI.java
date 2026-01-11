package appolloni.migliano.cli;

import java.util.List;
import java.util.Scanner;

import appolloni.migliano.bean.BeanGruppo;
import appolloni.migliano.bean.BeanStruttura;
import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.controller.ControllerGestioneGruppo;
import appolloni.migliano.controller.ControllerGestioneStrutture;

public class RicercaCLI {

    private final ControllerGestioneGruppo controllerGruppo;
    private final ControllerGestioneStrutture controllerStrutture;
    private final Scanner scanner;
    private BeanUtenti utenti;

    public RicercaCLI(BeanUtenti u) {
        this.utenti = u;
        this.controllerGruppo = new ControllerGestioneGruppo();
        this.controllerStrutture = new ControllerGestioneStrutture();
        this.scanner = new Scanner(System.in);
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
        System.out.print("Filtra per nome (lascia vuoto per saltare): ");
        String nome = promptInput();
        
        System.out.print("Filtra per città (lascia vuoto per saltare): ");
        String citta = promptInput();
        
        System.out.print("Filtra per materia (lascia vuoto per saltare): ");
        String materia = promptInput();

        try {
            List<BeanGruppo> risultati = controllerGruppo.cercaGruppi(nome, citta, materia);
            mostraRisultatiGruppi(risultati);
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
        
        System.out.print("Tipo (es: Biblioteca, Aula Studio - vuoto per skip): ");
        String tipo = promptInput();

        try {
            List<BeanStruttura> risultati = controllerStrutture.cercaStrutture(nome, citta, tipo);
            mostraRisultatiStrutture(risultati);
        } catch (Exception e) {
            System.err.println("Errore durante la ricerca: " + e.getMessage());
        }
    }

    private void mostraRisultatiGruppi(List<BeanGruppo> risultati) {
        if (risultati.isEmpty()) {
            System.out.println("Nessun gruppo trovato.");
            return;
        }
        System.out.println("\nGRUPPI TROVATI:");
        System.out.printf("%-20s | %-15s | %-15s%n", "NOME", "CITTÀ", "MATERIA");
        System.out.println("-".repeat(55));
        for (BeanGruppo g : risultati) {
            System.out.printf("%-20s | %-15s | %-15s%n", g.getNome(), g.getCitta(), g.getMateria());
        }
    }

    private void mostraRisultatiStrutture(List<BeanStruttura> risultati) {
        if (risultati.isEmpty()) {
            System.out.println("Nessuna struttura trovata.");
            return;
        }
        System.out.println("\nSTRUTTURE TROVATE:");
        for (BeanStruttura s : risultati) {
            System.out.println("- " + s.getName() + " (" + s.getCitta() + ")");
        }
    }

    private String promptInput() {
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? null : input;
    }
}
