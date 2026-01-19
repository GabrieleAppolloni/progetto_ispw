package appolloni.migliano.cli;

import java.sql.SQLException;
import java.util.List;

import appolloni.migliano.LeggInputCli;
import appolloni.migliano.bean.BeanGruppo;
import appolloni.migliano.bean.BeanMessaggi;
import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.controller.ControllerChat;

public class ChatCLI {
    private final ControllerChat controller;
    
    private final BeanUtenti utenteLoggato;
    private final BeanGruppo gruppoCorrente;

    public ChatCLI(BeanUtenti utente, BeanGruppo gruppo) {
        this.controller = new ControllerChat();
        this.utenteLoggato = utente;
        this.gruppoCorrente = gruppo;
    }

    public void start() {
        boolean exit = false;
        while (!exit) {
            try { 
                System.out.println("\n--- CHAT: " + gruppoCorrente.getNome() + " ---"); //NOSONAR
                System.out.println("Struttura di riferimento: " + gruppoCorrente.getLuogo()); //NOSONAR
                
                mostraMessaggi(); 
                
                System.out.println("\n[1] Invia Messaggio | [2] Refresh | [3] Abbandona Gruppo | [4] Esci"); //NOSONAR

                String scelta = LeggInputCli.leggiStringa("Scelta");

                switch (scelta) {
                    case "1" -> inviaMessaggioUI();
                    case "2" -> System.out.println("Messaggi aggiornati..."); //NOSONAR
                    case "3" -> {
                        abbandonaGruppoUI();
                        exit = true;
                    }
                    case "4" -> exit = true;
                    default -> System.out.println("Scelta non valida."); //NOSONAR
                }
            } catch (SQLException e) {
                 System.out.println("Errore Database"); //NOSONAR
            }
        }
    }

    private void mostraMessaggi() throws SQLException {
        try {
            List<BeanMessaggi> messaggi = controller.recuperaMessaggi(gruppoCorrente);
        if (messaggi.isEmpty()) {
            System.out.println("(Nessun messaggio presente)"); //NOSONAR
        } else {
            for (BeanMessaggi m : messaggi) {
                System.out.println("[" + m.getMittente() + "]: " + m.getMess()); //NOSONAR
            }
        }
            
        } catch (Exception e) {
            System.out.println("Errore caricamento"); //NOSONAR

        } 
    }

    private void inviaMessaggioUI() throws SQLException{
        
        String testo = LeggInputCli.leggiStringa("Scrivi Messaggio:");
       try {
           controller.inviaMessaggio(utenteLoggato, gruppoCorrente, testo);
       } catch (Exception e) {
         System.out.println("Errore"); //NOSONAR

       } 
    }

    private void abbandonaGruppoUI() throws SQLException {
        String inp = LeggInputCli.leggiStringa("Sei sicuro di voler lasciare/eliminare il gruppo? (si/no):");
        if (inp.equalsIgnoreCase("si")) {
           try {
               controller.abbandonaGruppo(utenteLoggato, gruppoCorrente);
           } catch (Exception e) {
             System.out.println("Errore"); //NOSONAR

           } 
            System.out.println("Gruppo abbandonato."); //NOSONAR
        }
    }
}










