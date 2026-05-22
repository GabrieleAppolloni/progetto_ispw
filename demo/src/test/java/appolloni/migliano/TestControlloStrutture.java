package appolloni.migliano;

import appolloni.migliano.bean.BeanStruttura;
import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.controller.ControllerCreazioneStrutturaHost;
import appolloni.migliano.controller.ControllerMenuHost;
import appolloni.migliano.controller.ControllerModificaStrutturHost;
import appolloni.migliano.controller.ControllerRegistrazioneUtente;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Appolloni Gabriele 0307344

 class TestControlloStrutture {
    private ControllerCreazioneStrutturaHost controllerStrutture;
    private ControllerRegistrazioneUtente controllerRegistrazioneUtente;
    private ControllerMenuHost controllerGestioneStrutture;
    private BeanStruttura beanStruttura;
    private BeanUtenti beanUtenti;
    private ControllerModificaStrutturHost controllerModificaStrutturHost;


    
   

    @BeforeEach
    void setup() throws Exception{
        Configurazione.setTipoPersistenza("demo");
        controllerStrutture = new ControllerCreazioneStrutturaHost();
        controllerRegistrazioneUtente = new ControllerRegistrazioneUtente();
        controllerGestioneStrutture = new ControllerMenuHost();
        controllerModificaStrutturHost = new ControllerModificaStrutturHost();
        beanStruttura = new BeanStruttura("Pubblica", "Test", "Test", "Test", false, false);
        beanStruttura.setFoto("test.png");
        beanStruttura.setGestore("test@test");
        beanStruttura.setOrario("Test");
        beanStruttura.setTipoAttivita("Bar");

        beanUtenti = new BeanUtenti("Host", "Test", "Test", "test@test", "test", "Test");
        beanUtenti.setTipoAttivita(beanStruttura.getTipoAttivita());
        beanUtenti.setNomeAttivita(beanStruttura.getName());
  

        
        try{
            controllerRegistrazioneUtente.registraUtente(beanUtenti);
        }catch(Exception e){
            e.printStackTrace();
        }


        
    }



    @Test
    void testCheckStruttura(){
        try{
            
            controllerStrutture.creazioneHostStruttura(beanUtenti, beanStruttura);
            BeanStruttura check = controllerGestioneStrutture.visualizzaStrutturaHost(beanStruttura.getGestore());
            controllerGestioneStrutture.cambiaFoto(beanUtenti.getEmail(), "test3");
            beanStruttura.setIndirizzo("via x");
            beanStruttura.setOrario("orario");
            controllerModificaStrutturHost.aggiornaStruttura(check,beanStruttura.getName() );
           
            assertEquals(beanStruttura.getName(), check.getName(),"Il nome dovrebbe essere uguale");
        }catch(Exception e){
            fail("Non doveva lanciare eccezioni: "+ e.getMessage());
        }
    }



    

}
