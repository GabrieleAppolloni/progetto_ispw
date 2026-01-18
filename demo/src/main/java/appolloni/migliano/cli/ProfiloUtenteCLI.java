package appolloni.migliano.cli;



import appolloni.migliano.LeggInputCli;
import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.controller.ControllerGestioneUtente;

public class ProfiloUtenteCLI {

    private final ControllerGestioneUtente controller;
    private final BeanUtenti utenteLoggato;

    public ProfiloUtenteCLI(BeanUtenti utente) {
        this.controller = new ControllerGestioneUtente();
        this.utenteLoggato = utente;
    }

    public void start() {
        boolean back = false;

        while (!back) {
            System.out.println("\n--- GESTIONE PROFILO ---"); //NOSONAR
            System.out.println("1. Visualizza le mie informazioni"); //NOSONAR
            System.out.println("2. Cambia Password"); //NOSONAR
            System.out.println("3. Torna al menu principale"); //NOSONAR

            String scelta = LeggInputCli.leggiStringa("Scelta");

            switch (scelta) {
                case "1" -> mostraInfoUI();
                case "2" -> cambiaPasswordUI();
                case "3" -> back = true;
                default -> System.out.println("Scelta non valida."); //NOSONAR
            }
        }
    }

    private void mostraInfoUI() {
        try {
            BeanUtenti risultato = controller.recuperaInformazioniUtenti(utenteLoggato);
            
            System.out.println("\n--- DETTAGLI PROFILO ---"); //NOSONAR
            System.out.println("Nome:    " + risultato.getName()); //NOSONAR
            System.out.println("Cognome: " + risultato.getCognome()); //NOSONAR
            System.out.println("Email:   " + risultato.getEmail()); //NOSONAR
            System.out.println("Città:   " + risultato.getCitta()); //NOSONAR
        } catch (Exception e) {
            System.out.println("Errore nel recupero dati"); //NOSONAR
        }
    }

    private void cambiaPasswordUI() {
        try {
            String vecchia = LeggInputCli.leggiStringa("Inserisci la vecchia password: ");
            
            String nuova = LeggInputCli.leggiStringa("Inserisci la nuova password: ");

            boolean successo = controller.modificaPassword(vecchia, nuova, utenteLoggato);
            
            if (successo) {
                System.out.println("Password aggiornata con successo!"); //NOSONAR
            } else {
                System.err.println("Errore: La password attuale inserita non è corretta."); //NOSONAR
            }
        } catch (Exception e) {
            System.err.println("Errore durante l'operazione: " + e.getMessage()); //NOSONAR
        }
    }

}