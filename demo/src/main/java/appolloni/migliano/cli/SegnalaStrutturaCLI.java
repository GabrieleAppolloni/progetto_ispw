package appolloni.migliano.cli;




import appolloni.migliano.LeggInputCli;
import appolloni.migliano.bean.BeanStruttura;
import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.controller.ControllerGestioneStrutture;


public class SegnalaStrutturaCLI {

    
    private final BeanUtenti studenteLoggato;
    private final ControllerGestioneStrutture controller;

    public SegnalaStrutturaCLI(BeanUtenti utente) {
        this.studenteLoggato = utente;
        this.controller = new ControllerGestioneStrutture();
    }

    public void start() {
        System.out.println("\n========================================"); //NOSONAR
        System.out.println("      SEGNALA UNA NUOVA STRUTTURA       "); //NOSONAR
        System.out.println("========================================"); //NOSONAR
        System.out.println("Studente: " + studenteLoggato.getName() + " " + studenteLoggato.getCognome()); //NOSONAR

        try {
            String nome = LeggInputCli.leggiStringa("Nome struttura da aggiungere: ");
            String citta = LeggInputCli.leggiStringa("Citta: ");
            String tipoStruttura = selezionaOpzione("Tipo Struttura*:", new String[]{"Privata", "Pubblica"});

            String indirizzo = LeggInputCli.leggiStringa("Indirizzo: ");

            String orario = acquisisciOrario();

            String gestore = "Sconosciuto";
            if (gestore.isEmpty()) gestore = "Sconosciuto";

            String tipoAttivita = selezionaOpzione("Tipo Attività:", new String[]{"Bar", "Università", "Biblioteca"});

            boolean wifi = chiediConferma("C'è il WiFi?");
            boolean ristorazione = chiediConferma("C'è un servizio ristorazione?");

          
            BeanStruttura struttura = new BeanStruttura(tipoStruttura, nome, citta, indirizzo, wifi, ristorazione);
            struttura.setOrario(orario);
            struttura.setGestore(gestore); 
            struttura.setTipoAttivita(tipoAttivita);
            struttura.setFoto("Foto non disponibili in versione CLI. ");

            

            controller.creaStruttura(studenteLoggato, struttura);

            System.out.println("\n Grazie! La tua segnalazione è stata salvata."); //NOSONAR
            System.out.println("Premi Invio per tornare al menu..."); //NOSONAR
            LeggInputCli.leggiStringa("");

        } catch (IllegalArgumentException e) {

            System.err.println("\n Attenzione: " + e.getMessage()); //NOSONAR
            System.out.println("Questa struttura è già presente nel nostro database."); //NOSONAR
        } catch (Exception e) {
            System.err.println("\n Errore durante la segnalazione: " + e.getMessage()); //NOSONAR
        }
    }

    private String selezionaOpzione(String titolo, String[] opzioni) {
        while (true) {
            System.out.println("\n" + titolo); //NOSONAR
            for (int i = 0; i < opzioni.length; i++) {
                System.out.println((i + 1) + ") " + opzioni[i]); //NOSONAR
            }
            System.out.print("Scelta: "); //NOSONAR
            try {
                int scelta = Integer.parseInt(LeggInputCli.leggiStringa(""));
                if (scelta >= 1 && scelta <= opzioni.length) return opzioni[scelta - 1];
            } catch (Exception e) {
            System.out.println("Riprova."); //NOSONAR
            }
        }
    }


     private String acquisisciOrario() {
    String input;
    while (true) {
        input = LeggInputCli.leggiStringa("Inserisci orario di apertura (es. 08:00-19:00): ");

        if (input.isEmpty()) return "";

        if (validaFormatoEIntervallo(input)) {
            return input;
        }
        
        System.out.println("[ERRORE] Formato non valido o orario incoerente."); //NOSONAR
    }
}

private boolean validaFormatoEIntervallo(String input) {
    String regex = "^([0-1]?\\d|2[0-3]):[0-5]\\d-([0-1]?\\d|2[0-3]):[0-5]\\d$";
    
  
    if (!input.matches(regex)) {
        return false;
    }

    String[] parti = input.split("-");
    if (parti.length < 2) return false;

    String[] inizio = parti[0].split(":");
    String[] fine = parti[1].split(":");

    if (inizio.length < 2 || fine.length < 2) return false;

   
    int minutiInizio = Integer.parseInt(inizio[0]) * 60 + Integer.parseInt(inizio[1]);
    int minutiFine = Integer.parseInt(fine[0]) * 60 + Integer.parseInt(fine[1]);

    if (minutiFine <= minutiInizio) {
        System.out.println("[ERRORE] L'orario di chiusura deve essere successivo a quello di apertura."); //NOSONAR
        return false;
    }

    return true;
}
    private boolean chiediConferma(String domanda) {
        String risp = (LeggInputCli.leggiStringa(domanda+ "(si/no):")).toLowerCase();
        return risp.equals("s") || risp.equals("si");
    }

}


