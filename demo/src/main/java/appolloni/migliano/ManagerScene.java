package appolloni.migliano;

import java.io.IOException;
import java.sql.SQLException;
import javafx.stage.Stage;
import appolloni.migliano.bean.BeanGruppo;
import appolloni.migliano.bean.BeanStruttura;
import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.exception.ErroreDiSistema;
import appolloni.migliano.gui.GUICreazioneStrutture;
import appolloni.migliano.gui.GUIChat;
import appolloni.migliano.gui.GUICreazioneGruppo;
import appolloni.migliano.gui.GUIModificaStruttura;
import appolloni.migliano.gui.GUIRicerca;
import appolloni.migliano.gui.GUIScriviRecensione;
import appolloni.migliano.gui.GUISegnalaStruttura;
import appolloni.migliano.gui.GUIdettagliStruttura;
import appolloni.migliano.gui.GUIhostMenu;
import appolloni.migliano.gui.GUImainMenu;
import appolloni.migliano.gui.GUIprofiloUtente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;


public class ManagerScene {


     @FXML
    public void cambiaScena(ActionEvent event, String file) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(file));
        Parent root = loader.load();

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);

    }

    @FXML

    public void avviaMainMenu(ActionEvent event, BeanUtenti beanUtente) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainMenu.fxml"));
        Parent root = loader.load();
        GUImainMenu guimainMenu = loader.getController();
        guimainMenu.initData(beanUtente);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
    }

    public void avviaMenuHost(ActionEvent event, BeanUtenti beanUtenti) throws IOException{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hostMenu.fxml"));
        Parent root = loader.load();
        GUIhostMenu hIhostMenu = loader.getController();
        hIhostMenu.initData(beanUtenti);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
    }
    

    public void avviaCreazioneStrutture(ActionEvent event, BeanUtenti beanUtenti) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/creazioneStrutture.fxml"));
        Parent root = loader.load();
        GUICreazioneStrutture creazioneGUIStrutture = loader.getController();
        creazioneGUIStrutture.initData(beanUtenti);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
        stage.setTitle("Registrazione Struttura");

    }

    public void avviaChat(BeanGruppo beanGruppo, BeanUtenti bean, VBox containerGruppi) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/chat.fxml")); 
        Parent root = loader.load();

        GUIChat chatController = loader.getController();
        chatController.initData(bean, beanGruppo);

        Stage stage = (Stage) containerGruppi.getScene().getWindow();
        stage.getScene().setRoot(root); 


    }

    public void apriProfilo(ActionEvent event, BeanUtenti beanUtenti) throws IOException{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/profiloUtente.fxml")); 
        Parent root = loader.load();

        GUIprofiloUtente profiloUtente = loader.getController();
        profiloUtente.initData(beanUtenti);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);

    }

    public void avviaRicerca(ActionEvent event, BeanUtenti beanUtenti) throws IOException{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ricerca.fxml")); 
        Parent root = loader.load();

        GUIRicerca ricerca = loader.getController();
        ricerca.initData(beanUtenti);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);

    }

    public void avviaNuovaStruttura(ActionEvent event, BeanUtenti beanUtenti) throws IOException{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/segnalaStruttura.fxml")); 
        Parent root = loader.load();

        GUISegnalaStruttura segnalaStruttura = loader.getController();
        segnalaStruttura.initData(beanUtenti);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);

    }

    public void avviaGestioneGruppo(ActionEvent event, BeanUtenti beanUtenti) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/creazioneGruppo.fxml")); 
        Parent root = loader.load();

        GUICreazioneGruppo creazioneGruppo = loader.getController();
        creazioneGruppo.initData(beanUtenti);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);

    }
    
    public void avviaDettagliStruttura(BeanUtenti beanUtenti, BeanStruttura beanStruttura, VBox containerRisultati) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/infoStruttura.fxml"));
        Parent root = loader.load();
        GUIdettagliStruttura controllerDettagli = loader.getController();
        controllerDettagli.initData(beanUtenti, beanStruttura); 

        Stage stage = (Stage) containerRisultati.getScene().getWindow();
        stage.getScene().setRoot(root); 

    }
    
    public void scriviRecensione(BeanUtenti beanUtente, BeanStruttura beanStruttura, Node nodoSorgente) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scriviRecensione.fxml"));
        Parent root = loader.load();
        
        GUIScriviRecensione controller = loader.getController();
        controller.initData(beanUtente, beanStruttura); 

        Stage newStage = new Stage(); 
        newStage.setTitle("Scrivi Recensione");
        newStage.setScene(new Scene(root));
        newStage.initOwner(nodoSorgente.getScene().getWindow());

        newStage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        newStage.showAndWait(); 
    }

    public void modificaDatiHost(BeanUtenti beanUtente,BeanStruttura beanStruttura, Node nodoSorgente) throws IOException, IllegalArgumentException,SQLException, ErroreDiSistema{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/modificaStruttura.fxml"));
        Parent root = loader.load();

        GUIModificaStruttura controllerModifica = loader.getController();
        controllerModifica.initData(beanStruttura);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Modifica Struttura");
        stage.initOwner(nodoSorgente.getScene().getWindow());
        stage.initModality(javafx.stage.Modality.WINDOW_MODAL);
        stage.showAndWait();
    }

   private void tornaHomeDaNodo(Node nodoCorrente) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/home.fxml"));
        Parent root = loader.load();
        
        Stage stageAttuale = (Stage) nodoCorrente.getScene().getWindow();


        if (stageAttuale.getOwner() != null) {
            Stage finestraMadre = (Stage) stageAttuale.getOwner();
            stageAttuale.close(); 
            finestraMadre.setScene(new Scene(root));
            finestraMadre.setTitle("Home");
        } else {
            stageAttuale.setScene(new Scene(root));
            stageAttuale.setTitle("Home");
        }
    }
    public void gestioneErrore(String codice, String messaggio, Node nodo){
        HelperErrori.errore(codice, messaggio);
        try{
            tornaHomeDaNodo(nodo);
        }catch(IOException e){
            HelperErrori.errore("Errore", "impossibile caricare la schermata");
        }
    }
    
}
