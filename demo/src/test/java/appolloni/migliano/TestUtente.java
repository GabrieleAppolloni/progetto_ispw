package appolloni.migliano;

import appolloni.migliano.bean.BeanGruppo;
import appolloni.migliano.bean.BeanMessaggi;
import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.controller.ControllerChat;
import appolloni.migliano.controller.ControllerGestioneGruppo;
import appolloni.migliano.controller.ControllerGestioneUtente;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

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
    void testChat(){
        try{
            beanUtenti = new BeanUtenti("Studente", "Test", "Test", "test@test3", "test", "Test");
            controllerGestioneUtente.creazioneUtente(beanUtenti);

            BeanUtenti bean = controllerGestioneUtente.recuperaInformazioniUtenti(beanUtenti);
            if(beanUtenti == null){ fail("Utente non creao");}
            controllerGestioneUtente.modificaPassword("test", "test1", bean);
            ControllerGestioneGruppo controllerGestioneGruppo = new ControllerGestioneGruppo();
            BeanGruppo beanGruppo = new BeanGruppo("test", "test",bean.getEmail(), "test", "test");

            controllerGestioneGruppo.creaGruppo(bean, beanGruppo);
            List<BeanGruppo> lista = new ArrayList<>();
            lista = controllerGestioneGruppo.cercaGruppi(beanGruppo.getNome(), null, null);
            if(lista.isEmpty()){fail("Creazione gruppo fallita");}
            lista = controllerGestioneGruppo.visualizzaGruppi(bean);
            if(lista.isEmpty()){fail("Gruppo non trovato");}

            ControllerChat controllerChat = new ControllerChat();
            controllerChat.inviaMessaggio(bean, beanGruppo, "test");
            List<BeanMessaggi> listaMess = new ArrayList<>();
            listaMess =  controllerChat.recuperaMessaggi(beanGruppo);
            assertNotNull(listaMess,"Dovrebbe esserci un messaggio");

            //assertEquals(beanUtenti.getEmail(),bean.getEmail() ,"Il nome dovrebbe essere uguale");
        }catch(Exception e){
            fail("Non doveva lanciare eccezioni: "+ e.getMessage());
        }
    }




    

}
