package appolloni.migliano;
import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.controller.ControllerProfiloUtente;
import appolloni.migliano.controller.ControllerRegistrazioneUtente;

import static org.junit.jupiter.api.Assertions.*;

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
    void testCreazioneUtente() throws Exception { 
            beanUtenti = new BeanUtenti("Studente", "Test", "Test", "test@test3", "test", "test");
            controllerRegistrazioneUtente.registraUtente(beanUtenti);
            BeanUtenti bean = controllerGestioneUtente.recuperaInformazioniUtente(beanUtenti);
            assertNotNull(bean,"L'utente dovrebbe essere stati creato e recuperato correttamente");
            assertEquals(beanUtenti.getEmail(), bean.getEmail(), "Le email dovrebbero coincidere");    
        }
}
