package appolloni.migliano;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import appolloni.migliano.bean.BeanGruppo;
import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.controller.ControllerCreazioneGruppo;
import appolloni.migliano.controller.ControllerRicerca;
import appolloni.migliano.controller.ControllerRegistrazioneUtente;

//Appolloni Gabriele 0307344
 class TestGruppo {

    private ControllerCreazioneGruppo controllerCrezioneGruppo;
    private ControllerRicerca controllerRicerca;
    private ControllerRegistrazioneUtente controllerRegistrazioneUtente;
    private BeanGruppo gruppo;
    private BeanUtenti s;



    @BeforeEach

    void setup() throws Exception{
        Configurazione.setTipoPersistenza("DEMO");
        controllerCrezioneGruppo = new ControllerCreazioneGruppo();
        controllerRegistrazioneUtente = new ControllerRegistrazioneUtente();
        controllerRicerca = new ControllerRicerca();
        s = new BeanUtenti("Studente", "Test", "Test", "test@test", "Test", "Test");
        gruppo = new BeanGruppo("Test", "Test",s.getEmail(), "Test", "Test");
        controllerRegistrazioneUtente.registraUtente(s);
    }


    @Test
    void testCreaGruppo() throws Exception{

        controllerCrezioneGruppo.creaGruppo(s, gruppo);
        List<BeanGruppo> gruppi = controllerRicerca.ricercaGruppi(gruppo);
        assertNotNull(gruppi,"La lista dei gruppi non dovrebbe essere nulla");
        assertEquals(1, gruppi.size(), "Dovrebbe esserci esattamente 1 gruppo nella lista");
        assertEquals(gruppo.getNome(), gruppi.get(0).getNome(), "Il nome del gruppo recuperato deve coincidere con quello creato");
        assertEquals(gruppo.getAdmin(), gruppi.get(0).getAdmin(), "L'admin del gruppo non corrisponde");
         
    }
    
}
