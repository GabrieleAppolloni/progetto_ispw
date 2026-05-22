package appolloni.migliano.cli;
import appolloni.migliano.LeggInputCli;
import appolloni.migliano.ManagerCLI;
import appolloni.migliano.bean.BeanStruttura;
import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.controller.ControllerCreazioneStrutturaHost;
import appolloni.migliano.exception.CampiVuotiException;
import appolloni.migliano.exception.EmailNonValidaException;
import appolloni.migliano.exception.ErroreDiSistema;

public class CreazioneStruttureCLI {

    
    private final ControllerCreazioneStrutturaHost controllerCreazioneStrutturaHost;
    private final BeanUtenti utenteCorrente;

    public CreazioneStruttureCLI(BeanUtenti bean) {
       
        this.controllerCreazioneStrutturaHost = new ControllerCreazioneStrutturaHost();
        this.utenteCorrente = bean;
    }

    public void start() {
        System.out.println("\n========================================"); //NOSONAR
        System.out.println("    REGISTRAZIONE DELLA TUA STRUTTURA    "); //NOSONAR
        System.out.println("========================================"); //NOSONAR
        System.out.println("Benvenuto " + utenteCorrente.getName());  //NOSONAR
        System.out.println("Struttura: " + utenteCorrente.getNomeAttivita() + " (" + utenteCorrente.getTipoAttivita() + ")");  //NOSONAR

        try {
            
            String citta = LeggInputCli.leggiStringa("Città:");
             
            String indirizzo = LeggInputCli.leggiStringa("Indirizzo: ");
            
            String orario = LeggInputCli.acquisisciOrario();

            boolean wifi = chiediConferma("La struttura dispone di WiFi?");
            boolean ristorazione = chiediConferma("La struttura dispone di servizio ristorazione?");

            
            String nomeFotoFinale = "Foto non disponibili in versione CLI.";  
            System.out.print("Foto non disponibili in versione CLI. ");  //NOSONAR
             

            //controllerUtente.registraUtente(utenteCorrente);
            System.out.println("Account Host creato correttamente...");  //NOSONAR

           
            BeanStruttura beanStruttura = new BeanStruttura(utenteCorrente.getTipoAttivita(), utenteCorrente.getNomeAttivita(), citta, indirizzo, wifi, ristorazione);
            beanStruttura.setOrario(orario);
            beanStruttura.setTipoAttivita(utenteCorrente.getTipoAttivita());
            beanStruttura.setGestore(utenteCorrente.getEmail()); 
            beanStruttura.setFoto(nomeFotoFinale);

       
           
            controllerCreazioneStrutturaHost.creazioneStrutturaHost(beanStruttura,utenteCorrente);
            

            System.out.println("\n[OK] Registrazione completata con successo!");  //NOSONAR
            System.out.println("Premi invio per accedere al tuo pannello...");  //NOSONAR
            LeggInputCli.leggiStringa("");
            
            ManagerCLI.getInstance().avviaMenuHost(utenteCorrente);

        } catch (CampiVuotiException e) {
            System.err.println("\n[ERRORE] Dati mancanti: " + e.getMessage());  //NOSONAR
            riprova();
        } catch(ErroreDiSistema | EmailNonValidaException e){
             System.err.println("\n[ERRORE] " + e.getMessage());  //NOSONAR 
        }
    }

    private void riprova() {  
        String scelta = (LeggInputCli.leggiStringa("Vuoi riprovare l'inserimento? (si/no): ")).toLowerCase();
        if(scelta.equalsIgnoreCase("s") || scelta.equalsIgnoreCase("si")) {
            start();
        }
    }




    private boolean chiediConferma(String domanda) {
        String risp = (LeggInputCli.leggiStringa(domanda + "(si/no)")).toLowerCase();
        return risp.equals("s") || risp.equals("si");
    }

}



