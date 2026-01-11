package appolloni.migliano.cli;

import java.util.Scanner;

import appolloni.migliano.bean.BeanStruttura;
import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.controller.ControllerGestioneStrutture;

public class ModificaStrutturaCLI {

    private final Scanner scanner;
    private final BeanStruttura strutturaCorrente;
    private final String vecchioNome;
    private final ControllerGestioneStrutture controllerApp;

    public ModificaStrutturaCLI(BeanUtenti host, BeanStruttura struttura) {
        this.scanner = new Scanner(System.in);
        this.strutturaCorrente = struttura;
        this.vecchioNome = struttura.getName(); 
        this.controllerApp = new ControllerGestioneStrutture();
    }

    public void start() {
        System.out.println("\n========================================");
        System.out.println("       MODIFICA DELLA STRUTTURA        ");
        System.out.println("========================================");
        System.out.println("Lascia vuoto e premi Invio per mantenere il valore attuale.");

        try {

            System.out.print("Nome attuale [" + strutturaCorrente.getName() + "]: ");
            String nuovoNome = scanner.nextLine().trim();
            if (!nuovoNome.isEmpty()) strutturaCorrente.setName(nuovoNome);


            System.out.print("Indirizzo attuale [" + strutturaCorrente.getIndirizzo() + "]: ");
            String nuovoIndirizzo = scanner.nextLine().trim();
            if (!nuovoIndirizzo.isEmpty()) strutturaCorrente.setIndirizzo(nuovoIndirizzo);


            System.out.print("Città attuale [" + strutturaCorrente.getCitta() + "]: ");
            String nuovaCitta = scanner.nextLine().trim();
            if (!nuovaCitta.isEmpty()) strutturaCorrente.setCitta(nuovaCitta);


            System.out.print("Orario attuale [" + strutturaCorrente.getOrario() + "]: ");
            String nuovoOrario = scanner.nextLine().trim();
            if (!nuovoOrario.isEmpty()) strutturaCorrente.setOrario(nuovoOrario);

           
            strutturaCorrente.setWifi(chiediModificaBoolean("WiFi", strutturaCorrente.hasWifi()));

    
            strutturaCorrente.setRistorazione(chiediModificaBoolean("Ristorazione", strutturaCorrente.hasRistorazione()));

        
            if (strutturaCorrente.getName() == null || strutturaCorrente.getName().isEmpty()) {
                System.err.println(" Errore: Il nome non può essere vuoto!");
                return;
            }

          
            controllerApp.aggiornaStruttura(strutturaCorrente, vecchioNome);
            System.out.println("\n Struttura aggiornata con successo!");

        } catch (Exception e) {
            System.err.println("\n Errore durante il salvataggio: " + e.getMessage());
        }
    }


    private boolean chiediModificaBoolean(String campo, boolean valoreAttuale) {
        String stato = valoreAttuale ? "SI" : "NO";
        System.out.print(campo + " attuale [" + stato + "]. Cambiare? (s/n): ");
        String risp = scanner.nextLine().trim().toLowerCase();
        
        if (risp.equals("s") || risp.equals("si")) {
            return !valoreAttuale; 
        }
        return valoreAttuale;
    }
}
