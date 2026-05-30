package appolloni.migliano;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import appolloni.migliano.bean.BeanGruppo;
import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.controller.ControllerCreazioneGruppo;
import appolloni.migliano.controller.ControllerRegistrazioneUtente;

import appolloni.migliano.controller.ControllerLogin;
import appolloni.migliano.controller.ControllerMainMenu;

//Appolloni Gabriele 0307344
 class TestRicercaGruppo {

    private ControllerCreazioneGruppo controllerCrezioneGruppo;
    private ControllerRegistrazioneUtente controllerRegistrazioneUtente;
    private ControllerMainMenu controllerMainMenu;
    private BeanGruppo gruppo;
    private BeanUtenti studente;



    @BeforeEach

    void setup() throws Exception{
        Configurazione.setTipoPersistenza("DEMO");
        controllerCrezioneGruppo = new ControllerCreazioneGruppo();
        controllerRegistrazioneUtente = new ControllerRegistrazioneUtente();
        controllerMainMenu = new ControllerMainMenu();
        studente = new BeanUtenti("Studente", "Test", "Test", "test@test4", "Test", "Test");
        gruppo = new BeanGruppo("Test", "Test",studente.getEmail(), "Test", "Test");

       controllerRegistrazioneUtente.registraUtente(studente);

    }


    @Test
    void testCreaGruppo(){

        try{
        
         ControllerLogin controllerLogin = new ControllerLogin();
         controllerLogin.verificaUtente(studente);

         controllerCrezioneGruppo.creaGruppo(studente, gruppo);
         List<BeanGruppo> gruppi =controllerMainMenu.recuperaGruppiUtente(studente);
         
         assertEquals(1, gruppi.size(), "Dovrebbe esserci esattamente 1 gruppo nella lista");
         assertEquals(gruppo.getNome(), gruppi.get(0).getNome(), "Il nome del gruppo recuperato deve coincidere con quello creato");
        }catch(Exception e){
           
            fail("Il test ha lanciato un'eccezione imprevista: " + e.getMessage());
        }
    }
    
}
