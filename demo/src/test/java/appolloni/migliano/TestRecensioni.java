package appolloni.migliano;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import appolloni.migliano.bean.BeanRecensioni;
import appolloni.migliano.bean.BeanStruttura;
import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.controller.ControllerCreazioneStrutturaHost;
import appolloni.migliano.controller.ControllerRegistrazioneUtente;
import appolloni.migliano.controller.ControllerRecensioni;
import appolloni.migliano.exception.CampiVuotiException;

//Marco Migliano 0308634

class TestRecensioni {
    private ControllerRecensioni controllerRecensioni;
    private ControllerCreazioneStrutturaHost controllerStrutture;
    private ControllerRegistrazioneUtente controllerUtente;
    
    private BeanUtenti beanGuest;      
    private BeanUtenti beanHost;       
    private BeanStruttura beanStruttura;
    private BeanRecensioni beanRecensione;

    @BeforeEach
    void setup() throws Exception {
        Configurazione.setTipoPersistenza("DEMO");
        controllerRecensioni = new ControllerRecensioni();
        controllerStrutture = new ControllerCreazioneStrutturaHost();
        controllerUtente = new ControllerRegistrazioneUtente();

        beanHost = new BeanUtenti("Host", "Proprietario", "Test", "host@test.it", "password", "Test");
        beanHost.setTipoAttivita("Bar");
        beanHost.setNomeAttivita("StrutturaTest");
    
        beanGuest = new BeanUtenti("Studente", "Recensore", "Test", "guest@test.it", "password", "Test");

        
        controllerUtente.registraUtente(beanHost);
        controllerUtente.registraUtente(beanGuest);
        

       
        beanStruttura = new BeanStruttura("Pubblica", "StrutturaTest", "Roma", "Via Test", false, false);
        beanStruttura.setGestore(beanHost.getEmail());
        beanStruttura.setTipoAttivita(beanHost.getTipoAttivita());
        beanStruttura.setOrario("09:00-10:00");
        
        
        controllerStrutture.creazioneStrutturaHost(beanStruttura,beanHost);
        

  
        beanRecensione = new BeanRecensioni(
            beanGuest.getEmail(), 
            "Ottima struttura!", 
            5, 
            beanStruttura.getName(), 
            beanStruttura.getGestore()
        );
    }


    @Test
    void testInserimentoRecensioneSuccesso() throws Exception {
            controllerRecensioni.inserisciRecensione(beanRecensione);
            
            List<BeanRecensioni> recensioni = controllerRecensioni.cercaRecensioniPerStruttura(beanStruttura);
            boolean trovata = false;
            for (BeanRecensioni r : recensioni) {
                if (r.getAutore().equals(beanGuest.getEmail()) && r.getVoto() == 5) {
                    trovata = true;
                    break;
                }
            }
            assertTrue(trovata, "La recensione dovrebbe essere salvata correttamente nel database");
            
       
    }

    @Test
    void testInserimentoVotoErrato() {
            beanRecensione.setVoto(10); 
            
            assertThrows(IllegalArgumentException.class, () -> {
                controllerRecensioni.inserisciRecensione(beanRecensione);
            });
            
       
    }

    @Test
    void testInserimentoTestoVuoto() {
        beanRecensione.setTesto(""); 
          
        assertThrows(CampiVuotiException.class, () -> {
                controllerRecensioni.inserisciRecensione(beanRecensione);
        }, "Dovrebbe lanciare un'eccezione se il testo è vuoto");
            
    }
}
