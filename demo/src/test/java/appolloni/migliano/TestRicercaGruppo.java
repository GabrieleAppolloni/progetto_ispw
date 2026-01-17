package appolloni.migliano;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import appolloni.migliano.bean.BeanGruppo;
import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.controller.ControllerGestioneGruppo;
import appolloni.migliano.controller.ControllerGestioneUtente;

//Appolloni Gabriele 0307344
 class TestRicercaGruppo {

    private ControllerGestioneGruppo controllerGestioneGruppo;
    private ControllerGestioneUtente controllerGestioneUtente;
    private BeanGruppo gruppo;
    private BeanUtenti studente;



    @BeforeEach

    void setup() throws Exception{
        Configurazione.setTipoPersistenza("DEMO");
        controllerGestioneGruppo = new ControllerGestioneGruppo();
        controllerGestioneUtente = new ControllerGestioneUtente();
        studente = new BeanUtenti("Studente", "Test", "Test", "test@test4", "Test", "Test");
        gruppo = new BeanGruppo("Test", "Test",studente.getEmail(), "Test", "Test");

       controllerGestioneUtente.creazioneUtente(studente);

    }

    @AfterEach

    void clean() throws Exception{

    }

    @Test
    void testCreaGruppo(){

        try{
         controllerGestioneGruppo.creaGruppo(studente, gruppo);
         List<BeanGruppo> gruppi =controllerGestioneGruppo.cercaGruppi(gruppo.getNome(),null, null);
         assertFalse(gruppi.isEmpty(), "La lista dei gruppi non deve essere vuota dopo l'inserimento");
        }catch(Exception e){
           
            fail("Il test ha lanciato un'eccezione imprevista: " + e.getMessage());
        }
    }
    
}
