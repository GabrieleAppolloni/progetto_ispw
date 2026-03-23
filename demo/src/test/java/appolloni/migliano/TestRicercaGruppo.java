package appolloni.migliano;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import appolloni.migliano.bean.BeanGruppo;
import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.controller.ControllerCreazioneGruppo;
import appolloni.migliano.controller.ControllerGestioneGruppo;
import appolloni.migliano.controller.ControllerRegistrazioneUtente;
import appolloni.migliano.controller.ControllerLogin;

//Appolloni Gabriele 0307344
 class TestRicercaGruppo {

    private ControllerCreazioneGruppo controllerCrezioneGruppo;
    private ControllerRegistrazioneUtente controllerRegistrazioneUtente;
    private ControllerGestioneGruppo controllerGestioneGruppo;
    private BeanGruppo gruppo;
    private BeanUtenti studente;



    @BeforeEach

    void setup() throws Exception{
        Configurazione.setTipoPersistenza("DEMO");
        controllerCrezioneGruppo = new ControllerCreazioneGruppo();
        controllerCrezioneGruppo = new ControllerCreazioneGruppo();
        controllerRegistrazioneUtente = new ControllerRegistrazioneUtente();
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

         BeanGruppo beanGruppo = new BeanGruppo("test4", "test4", "teststud", "test4", "test4");

         controllerGestioneGruppo.aggiungiGruppo(studente, beanGruppo);
         List<BeanGruppo> gruppi =controllerGestioneGruppo.cercaGruppi(gruppo.getNome(),null, null);
         
         assertFalse(gruppi.isEmpty(), "La lista dei gruppi non deve essere vuota dopo l'inserimento");
        }catch(Exception e){
           
            fail("Il test ha lanciato un'eccezione imprevista: " + e.getMessage());
        }
    }
    
}
