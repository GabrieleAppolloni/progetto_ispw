package appolloni.migliano;

import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.controller.ControllerGestioneUtente;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Appolloni Gabriele 0307344

 class TestUtente {
    
    private ControllerGestioneUtente controllerGestioneUtente;
    private BeanUtenti beanUtenti;
   

    @BeforeEach
    void setup(){
        Configurazione.setTipoPersistenza("DEMO");
        controllerGestioneUtente = new ControllerGestioneUtente();
    }


  

    @Test
    void testCreaStruttura(){
        try{
            beanUtenti = new BeanUtenti("Host", "Test", "Test", "test@test3", "test", "Test");
            beanUtenti.setTipoAttivita("Bar");
            beanUtenti.setNomeAttivita("Test");
            controllerGestioneUtente.creazioneUtente(beanUtenti);

            BeanUtenti bean = controllerGestioneUtente.recuperaInformazioniUtenti(beanUtenti);
            assertEquals(beanUtenti.getEmail(),bean.getEmail() ,"Il nome dovrebbe essere uguale");
        }catch(Exception e){
            fail("Non doveva lanciare eccezioni: "+ e.getMessage());
        }
    }



    

}
