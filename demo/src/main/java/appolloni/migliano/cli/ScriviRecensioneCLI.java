package appolloni.migliano.cli;

import appolloni.migliano.LeggInputCli;
import appolloni.migliano.bean.BeanRecensioni;
import appolloni.migliano.bean.BeanStruttura;
import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.controller.ControllerRecensioni;

public class ScriviRecensioneCLI {

   
    private final BeanUtenti beanUtente;
    private final BeanStruttura beanStruttura;
    private final ControllerRecensioni controllerRecensioni;

    public ScriviRecensioneCLI(BeanUtenti utente, BeanStruttura struttura) {
        this.beanUtente = utente;
        this.beanStruttura = struttura;
        this.controllerRecensioni = new ControllerRecensioni();
    }

    public void start() {
        System.out.println("\n========================================"); //NOSONAR
        System.out.println("       SCRIVI UNA RECENSIONE           "); //NOSONAR
        System.out.println("========================================"); //NOSONAR
        System.out.println("Struttura: " + beanStruttura.getName()); //NOSONAR
        System.out.println("Città:     " + beanStruttura.getCitta()); //NOSONAR
        System.out.println("----------------------------------------"); //NOSONAR

        try {
            int voto = richiediVoto();

            
            String testo = LeggInputCli.leggiStringa("Scrivi il tuo commento: ");

            if (testo.isEmpty()) {
                System.out.println("Nota: Hai inviato una recensione senza testo."); //NOSONAR
            }

            BeanRecensioni beanRecensioni = new BeanRecensioni(
                beanUtente.getEmail(), 
                testo, 
                voto, 
                beanStruttura.getName(), 
                beanStruttura.getGestore()
            );

            controllerRecensioni.inserisciRecensione(beanRecensioni);

            System.out.println("\n Recensione inviata con successo!"); //NOSONAR
            System.out.println("Voto assegnato: " + voto + "/5"); //NOSONAR

        } catch (Exception e) {
            System.err.println("\n Errore durante l'invio: " + e.getMessage()); //NOSONAR
        }
        
        System.out.println("\nPremi Invio per tornare indietro..."); //NOSONAR
        LeggInputCli.leggiStringa("");
    }

   
    private int richiediVoto() {
        int voto = -1;
        while (voto < 1 || voto > 5) {
            String input = LeggInputCli.leggiStringa("Inserisci un voto da 1 a 5: ");
            try {
                voto = Integer.parseInt(input);
                if (voto < 1 || voto > 5) {
                    System.out.println("Errore: Il voto deve essere compreso tra 1 e 5."); //NOSONAR
                }
            } catch (NumberFormatException e) {
                System.out.println("Errore: Inserisci un numero valido."); //NOSONAR
            }
        }
        return voto;
    }
}