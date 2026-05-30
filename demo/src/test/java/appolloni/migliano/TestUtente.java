package appolloni.migliano;

import appolloni.migliano.bean.BeanGruppo;
import appolloni.migliano.bean.BeanMessaggi;
import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.controller.ControllerChat;
import appolloni.migliano.controller.ControllerCreazioneGruppo;
import appolloni.migliano.controller.ControllerProfiloUtente;
import appolloni.migliano.controller.ControllerRegistrazioneUtente;
import appolloni.migliano.controller.ControllerRicerca;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Appolloni Gabriele 0307344

 class TestUtente {
    
    private ControllerProfiloUtente controllerGestioneUtente;
    private ControllerRegistrazioneUtente controllerRegistrazioneUtente;
    private BeanUtenti beanUtenti;

   
@BeforeEach
    void setup(){
        Configurazione.setTipoPersistenza("DEMO");
        controllerGestioneUtente = new ControllerProfiloUtente();
        controllerRegistrazioneUtente = new ControllerRegistrazioneUtente();
    }

    @Test
    void testFlussoCompletoChat() { 
        try {
            beanUtenti = new BeanUtenti("Studente", "Test", "Test", "test@test3", "test", "test");
            controllerRegistrazioneUtente.registraUtente(beanUtenti);
            BeanUtenti bean = controllerGestioneUtente.recuperaInformazioniUtente(beanUtenti);
            
            if(bean == null){ 
                fail("Utente non trovato nel database DEMO");
            }
    
            ControllerCreazioneGruppo controllerCreazioneGruppo = new ControllerCreazioneGruppo();
            ControllerRicerca controllerRicerca = new ControllerRicerca();
            BeanGruppo beanGruppo = new BeanGruppo("test", "test", bean.getEmail(), "test", "test");

            controllerCreazioneGruppo.creaGruppo(bean, beanGruppo);
            List<BeanGruppo> lista = controllerRicerca.ricercaGruppi(beanGruppo);
            
            if(lista.isEmpty()){
                fail("Creazione gruppo fallita, lista vuota");
            }

            String testoInviato = "Ciao, questo è un test!";
            ControllerChat controllerChat = new ControllerChat();
            controllerChat.inviaMessaggio(bean, beanGruppo, testoInviato);
            
            List<BeanMessaggi> listaMess = controllerChat.recuperaMessaggi(beanGruppo);

            assertNotNull(listaMess, "Dovrebbe esserci una lista di messaggi");
            assertEquals(1, listaMess.size(), "Dovrebbe esserci esattamente 1 messaggio");
            assertEquals(testoInviato, listaMess.get(0).getMess(), "Il testo del messaggio recuperato deve coincidere con quello inviato");

        } catch(Exception e) {
            fail("Non doveva lanciare eccezioni: " + e.getMessage());
        }
    }


}
