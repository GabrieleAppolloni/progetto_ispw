package appolloni.migliano;

import appolloni.migliano.bean.BeanStruttura;
import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.controller.ControllerCreazioneStrutturaHost;
import appolloni.migliano.controller.ControllerRicerca;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

// Appolloni Gabriele 0307344

 class TestControlloStrutture {
    private ControllerCreazioneStrutturaHost controllerStrutture;
    private BeanStruttura beanStruttura;
    private BeanUtenti beanUtenti;
    private ControllerRicerca controllerRicercaStruttura;


    @BeforeEach
    void setup() throws Exception{
        Configurazione.setTipoPersistenza("demo");
        controllerStrutture = new ControllerCreazioneStrutturaHost();
        controllerRicercaStruttura = new ControllerRicerca();
        beanStruttura = new BeanStruttura("Pubblica", "Test", "Test", "Test", false, false);
        beanStruttura.setFoto("test.png");
        beanStruttura.setGestore("test@test");
        beanStruttura.setOrario("Test");
        beanStruttura.setTipoAttivita("Bar");

        beanUtenti = new BeanUtenti("Host", "Test", "Test", "test@test", "test", "Test");
        beanUtenti.setTipoAttivita(beanStruttura.getTipoAttivita());
        beanUtenti.setNomeAttivita(beanStruttura.getName());

        
    }

    @Test
    void testFlussoCompletoStrutturaHost() throws Exception{ 
     controllerStrutture.creazioneStrutturaHost(beanStruttura, beanUtenti);
     List<BeanStruttura> strutture = controllerRicercaStruttura.ricercaStruttura(beanStruttura);
     assertNotNull(strutture, "La lista delle strutture recuperate non dovrebbe essere nulla");
     assertEquals(1, strutture.size(), "Dovrebbe essere stata creata esattamente 1 struttura");
     assertEquals(beanStruttura.getName(),strutture.get(0).getName(),"Il nome della struttura creata non coincide");
     assertEquals(beanStruttura.getGestore(),strutture.get(0).getGestore(), "I gestori non coincidono" );
            
    }

}
