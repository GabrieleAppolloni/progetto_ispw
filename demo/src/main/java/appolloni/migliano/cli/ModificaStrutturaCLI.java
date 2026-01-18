package appolloni.migliano.cli;


import appolloni.migliano.LeggInputCli;
import appolloni.migliano.bean.BeanStruttura;
import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.controller.ControllerGestioneStrutture;

public class ModificaStrutturaCLI {

    private final BeanStruttura strutturaCorrente;
    private final String vecchioNome;
    private final ControllerGestioneStrutture controllerApp;
    private final BeanUtenti host;

    public ModificaStrutturaCLI(BeanUtenti host, BeanStruttura struttura) {
        this.strutturaCorrente = struttura;
        this.vecchioNome = struttura.getName(); 
        this.controllerApp = new ControllerGestioneStrutture();
        this.host = host;
    }

    public void start() {
        System.out.println("\n========================================"); //NOSONAR
        System.out.println("       MODIFICA DELLA STRUTTURA        "); //NOSONAR
        System.out.println("========================================"); //NOSONAR
        System.out.println("Ciao " + host.getName() + ", stai modificando la tua struttura."); //NOSONAR
        System.out.println("Lascia vuoto e premi Invio per mantenere il valore attuale."); //NOSONAR

        try {
            String nuovoNome = LeggInputCli.leggiStringa("Nome attuale [" + strutturaCorrente.getName() + "]: ");
            if (!nuovoNome.isEmpty()) strutturaCorrente.setName(nuovoNome);

            String nuovoIndirizzo = LeggInputCli.leggiStringa("Indirizzo attuale [" + strutturaCorrente.getIndirizzo() + "]: ");
            if (!nuovoIndirizzo.isEmpty()) strutturaCorrente.setIndirizzo(nuovoIndirizzo);

            String nuovaCitta = LeggInputCli.leggiStringa("Città attuale [" + strutturaCorrente.getCitta() + "]: ");
            if (!nuovaCitta.isEmpty()) strutturaCorrente.setCitta(nuovaCitta);

            System.out.print("Orario attuale [" + strutturaCorrente.getOrario() + "]: "); //NOSONAR
            String nuovoOrario = acquisisciOrario();
            if (!nuovoOrario.isEmpty()) strutturaCorrente.setOrario(nuovoOrario);

            // 5. Modifica Wifi
            strutturaCorrente.setWifi(chiediModificaBoolean("WiFi", strutturaCorrente.hasWifi()));

            // 6. Modifica Ristorazione
            strutturaCorrente.setRistorazione(chiediModificaBoolean("Ristorazione", strutturaCorrente.hasRistorazione()));

            System.out.print("Foto non disponibili in versione CLI. "); //NOSONAR
            
            
            if (strutturaCorrente.getName() == null || strutturaCorrente.getName().isEmpty()) {
                System.out.println("Il nome non può essere vuoto! Inserire il nome"); //NOSONAR
                return;
            }

            // SALVATAGGIO
            controllerApp.aggiornaStruttura(strutturaCorrente, vecchioNome);
            System.out.println("\n Struttura aggiornata con successo!"); //NOSONAR

        } catch (Exception e) {
             System.out.println("Errore salvataggio"); //NOSONAR
        }
    }


   private String acquisisciOrario() {
    String input;
    while (true) {
        input = LeggInputCli.leggiStringa("Orario apertura: (es: 08:00 - 19:00)");

        if (input.isEmpty()) return "";

        if (LeggInputCli.validaFormatoEIntervallo(input)) {
            return input;
        }
        
        System.out.println("[ERRORE] Formato non valido o orario incoerente."); //NOSONAR
    }
}


  
    private boolean chiediModificaBoolean(String campo, boolean valoreAttuale) {
        String stato = valoreAttuale ? "SI" : "NO";
        String risp = LeggInputCli.leggiStringa(campo + " attuale [" + stato + "]. Cambiare? (si/no): ");
        
        if (risp.equals("s") || risp.equals("si")) {
            return !valoreAttuale; 
        }
        return valoreAttuale; 
    }

}





