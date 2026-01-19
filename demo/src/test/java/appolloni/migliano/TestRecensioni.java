package appolloni.migliano;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import appolloni.migliano.bean.BeanRecensioni;
import appolloni.migliano.bean.BeanStruttura;
import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.controller.ControllerGestioneStrutture;
import appolloni.migliano.controller.ControllerGestioneUtente;
import appolloni.migliano.controller.ControllerRecensioni;
import appolloni.migliano.exception.CampiVuotiException;

//Marco Migliano 

class TestRecensioni {
    private ControllerRecensioni controllerRecensioni;
    private ControllerGestioneStrutture controllerStrutture;
    private ControllerGestioneUtente controllerUtente;
    
    private BeanUtenti beanGuest;      
    private BeanUtenti beanHost;       
    private BeanStruttura beanStruttura;
    private BeanRecensioni beanRecensione;

    @BeforeEach
    void setup() throws Exception {
        Configurazione.setTipoPersistenza("DEMO");
        controllerRecensioni = new ControllerRecensioni();
        controllerStrutture = new ControllerGestioneStrutture();
        controllerUtente = new ControllerGestioneUtente();

        beanHost = new BeanUtenti("Host", "Proprietario", "Test", "host@test.it", "password", "Test");
        beanHost.setTipoAttivita("Bar");
        beanHost.setNomeAttivita("StrutturaTest");
    
        beanGuest = new BeanUtenti("Studente", "Recensore", "Test", "guest@test.it", "password", "Test");

        try {
            controllerUtente.creazioneUtente(beanHost);
            controllerUtente.creazioneUtente(beanGuest);
        } catch (Exception e) {
            e.printStackTrace();
        }

       
        beanStruttura = new BeanStruttura("Pubblica", "StrutturaTest", "Roma", "Via Test", false, false);
        beanStruttura.setGestore(beanHost.getEmail());
        beanStruttura.setTipoAttivita(beanHost.getTipoAttivita());
        beanStruttura.setOrario("09:00-10:00");
        
        try {
            controllerStrutture.creaStruttura(beanHost, beanStruttura);
        } catch (Exception e) {
            e.printStackTrace();
        }

  
        beanRecensione = new BeanRecensioni(
            beanGuest.getEmail(), 
            "Ottima struttura!", 
            5, 
            beanStruttura.getName(), 
            beanStruttura.getGestore()
        );
    }


    @Test
    void testInserimentoRecensioneSuccesso() {
        try {
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
            
        } catch (Exception e) {
            e.printStackTrace();
            fail("Eccezione imprevista durante l'inserimento: " + e.getMessage());
        }
    }

    @Test
    void testInserimentoVotoErrato() {
        try {
            beanRecensione.setVoto(10); 
            
            assertThrows(IllegalArgumentException.class, () -> {
                controllerRecensioni.inserisciRecensione(beanRecensione);
            });
            
        } catch (Exception e) {
            fail("Errore nel test del voto errato: " + e.getMessage());
        }
    }

    @Test
    void testInserimentoTestoVuoto() {
        try {
            beanRecensione.setTesto(""); 
          
            assertThrows(CampiVuotiException.class, () -> {
                controllerRecensioni.inserisciRecensione(beanRecensione);
            }, "Dovrebbe lanciare un'eccezione se il testo è vuoto");
            
        } catch (Exception e) {
            fail("Errore nel test del testo vuoto: " + e.getMessage());
        }
    }
}
