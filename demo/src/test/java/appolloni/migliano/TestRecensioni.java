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
import appolloni.migliano.controller.ControllerRecensioni;
import appolloni.migliano.controller.ControllerRegistrazioneUtente;
 
//Marco Migliano 0308634
 
class TestRecensioni {
    private ControllerRecensioni controllerRecensioni;
    private ControllerCreazioneStrutturaHost controllerStrutture;
    private ControllerRegistrazioneUtente controllerRegistrazioneUtente;
   
    private BeanUtenti beanGuest;      
    private BeanUtenti beanHost;      
    private BeanStruttura beanStruttura;
    private BeanRecensioni beanRecensione;
 
    @BeforeEach
    void setup() throws Exception {
        Configurazione.setTipoPersistenza("DEMO");
        controllerRecensioni = new ControllerRecensioni();
        controllerStrutture = new ControllerCreazioneStrutturaHost();
        controllerRegistrazioneUtente = new ControllerRegistrazioneUtente();
 
        long timestamp = System.currentTimeMillis();
        String emailHostUnique = "host_" + timestamp + "@test.it";
        String emailGuestUnique = "guest_" + timestamp + "@test.it";
        String nomeStrutturaUnique = "StrutturaTest_" + timestamp;
 
        beanHost = new BeanUtenti("Host", "Proprietario", "Test", emailHostUnique, "password", "Test");
        beanHost.setTipoAttivita("Bar");
        beanHost.setNomeAttivita(nomeStrutturaUnique);
   
        beanGuest = new BeanUtenti("Studente", "Recensore", "Test", emailGuestUnique, "password", "Test");
 
        controllerRegistrazioneUtente.registraUtente(beanHost);
        controllerRegistrazioneUtente.registraUtente(beanGuest);
       
        beanStruttura = new BeanStruttura("Pubblica", nomeStrutturaUnique, "Roma", "Via Test", false, false);
        beanStruttura.setGestore(beanHost.getEmail());
        beanStruttura.setTipoAttivita(beanHost.getTipoAttivita());
        beanStruttura.setOrario("09:00-10:00");
       
        controllerStrutture.creazioneStrutturaHost(beanStruttura, beanHost);
 
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
        assertTrue(trovata, "La recensione dovrebbe essere salvata correttamente");
    }
 
    @Test
    void testInserimentoVotoErrato() {
        beanRecensione.setVoto(10);
       
        assertThrows(Exception.class, () -> {
            controllerRecensioni.inserisciRecensione(beanRecensione);
        }, "Il sistema non dovrebbe permettere l'inserimento di un voto pari a 10");
    }
 
    @Test
    void testInserimentoTestoVuoto() {
        beanRecensione.setTesto("");
       
        assertThrows(Exception.class, () -> {
            controllerRecensioni.inserisciRecensione(beanRecensione);
        }, "Il sistema non dovrebbe permettere l'inserimento di una recensione senza testo");
    }
}
 