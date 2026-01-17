package appolloni.migliano;

import appolloni.migliano.bean.BeanStruttura;
import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.controller.ControllerGestioneStrutture;
import appolloni.migliano.controller.ControllerGestioneUtente;
import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Appolloni Gabriele 0307344

 class TestControlloStrutture {
    private ControllerGestioneStrutture controllerGestioneStrutture;
    private ControllerGestioneUtente controllerGestioneUtente;
    private BeanStruttura beanStruttura;
    private BeanUtenti beanUtenti;
   

    @BeforeEach
    void setup() throws Exception{
        Configurazione.setTipoPersistenza("DEMO");
        controllerGestioneStrutture = new ControllerGestioneStrutture();
        controllerGestioneUtente = new ControllerGestioneUtente();
        beanStruttura = new BeanStruttura("Pubblica", "Test", "Test", "Test", false, false);
        beanStruttura.setFoto("test.png");
        beanStruttura.setGestore("test@test");
        beanStruttura.setOrario("Test");
        beanStruttura.setTipoAttivita("Bar");

        beanUtenti = new BeanUtenti("Host", "Test", "Test", "test@test", "test", "Test");
        beanUtenti.setTipoAttivita(beanStruttura.getTipoAttivita());
        beanUtenti.setNomeAttivita(beanStruttura.getName());

  

        
        try{
            controllerGestioneUtente.creazioneUtente(beanUtenti);
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    @AfterEach
    void annullaOp() throws Exception{


    }

    @Test
    void testCreaStruttura(){
        try{
            //beanStruttura.setName("Pippo");
            controllerGestioneStrutture.creaStruttura(beanUtenti, beanStruttura);
            BeanStruttura check = controllerGestioneStrutture.visualizzaStrutturaHost(beanStruttura.getGestore());
            assertEquals(beanStruttura.getName(), check.getName(),"Il nome dovrebbe essere uguale");
        }catch(Exception e){
            fail("Non doveva lanciare eccezioni: "+ e.getMessage());
        }
    }



    

}
