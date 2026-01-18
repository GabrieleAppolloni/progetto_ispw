package appolloni.migliano.cli;


import appolloni.migliano.LeggInputCli;
import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.controller.ControllerGestioneUtente;
import appolloni.migliano.exception.CampiVuotiException;
import appolloni.migliano.exception.EmailNonValidaException;

public class CreazioneUtenteCLI {

    private final ControllerGestioneUtente controller;

    public CreazioneUtenteCLI() {
        this.controller = new ControllerGestioneUtente();
    
    }

    public void start() {
        System.out.println("\n--- REGISTRAZIONE NUOVO UTENTE ---"); //NOSONAR

        try {
            
            String tipo = selezionaTipoUtente();

        
            String nome = LeggInputCli.leggiStringa("Nome: ");
            String cognome = LeggInputCli.leggiStringa("Cognome: ");
            String email = (LeggInputCli.leggiStringa("Email: ")).toLowerCase();
            String citta = LeggInputCli.leggiStringa("Citta: ");
            String password = LeggInputCli.leggiStringa("Password: ");

            
            if (nome.isEmpty() || cognome.isEmpty() || email.isEmpty() || citta.isEmpty() || password.isEmpty()) {
                throw new CampiVuotiException("Controlli di aver inserito una mail valida o dati correttamente.");
            }
            if(!email.contains("@")){
                throw new EmailNonValidaException("Inserire una email valida.");
            }

           
            BeanUtenti beanUtenti = new BeanUtenti(tipo, nome, cognome, email, password, citta);

            
            if ("Host".equalsIgnoreCase(tipo)) {
                System.out.println("Benvenuto Host! Reindirizzamento alla registrazione dei dettagli della struttura..."); //NOSONAR
                System.out.println("\n--- DATI ATTIVITÀ (Obbligatori per Host) ---"); //NOSONAR
                String tipoatt = selezionaDaLista();
                String strutt = LeggInputCli.leggiStringa("Nome attività: ");

                beanUtenti.setNomeAttivita(strutt);
                beanUtenti.setTipoAttivita(tipoatt);
                new CreazioneStruttureCLI(beanUtenti).start();
                
                
            } else {
                
                salvaUtente(beanUtenti);
                System.out.println("Benvenuto Studente! Reindirizzamento al menu principale..."); //NOSONAR
                new MenuPrincipaleCLI(beanUtenti).start();
                
            }

        } catch (CampiVuotiException | EmailNonValidaException e) {
            System.err.println("\n Errore nei dati inseriti: " + e.getMessage()); //NOSONAR
            String riprova = LeggInputCli.leggiStringa("Vuoi riprovare la registrazione? (si/no)");
            if(riprova.equalsIgnoreCase("si")) start();
        } catch (Exception e) {
            System.err.println("\n Errore tecnico durante la registrazione: " + e.getMessage()); //NOSONAR
        }
    }

    private void salvaUtente(BeanUtenti beanUtenti) {
        try {
            controller.creazioneUtente(beanUtenti);
            System.out.println("\n Registrazione effettuata con successo!"); //NOSONAR
        } catch (Exception e) {
            System.out.println("Errore caricamento: " + e.getMessage()); //NOSONAR
        }
    }

    private String selezionaTipoUtente() {
        while (true) {
            System.out.println("Seleziona il tuo ruolo:"); //NOSONAR
            System.out.println("1) Studente"); //NOSONAR
            System.out.println("2) Host"); //NOSONAR
            String scelta = LeggInputCli.leggiStringa("Scelta: ");
            if (scelta.equals("1")) return "Studente";
            if (scelta.equals("2")) return "Host";
            System.out.println("Scelta non valida. Inserisci 1 o 2."); //NOSONAR
        }
    }

    private String selezionaDaLista() {
        while (true) {
            System.out.println("Seleziona il tipo di struttura:"); //NOSONAR
            System.out.println("1) Privata"); //NOSONAR
            System.out.println("2) Pubblica"); //NOSONAR
            String scelta = LeggInputCli.leggiStringa("Scelta: ");
            if (scelta.equals("1")) return "Privata";
            if (scelta.equals("2")) return "Pubblica";
            System.out.println("Scelta non valida. Inserisci 1 o 2."); //NOSONAR
        }
    }

    
}