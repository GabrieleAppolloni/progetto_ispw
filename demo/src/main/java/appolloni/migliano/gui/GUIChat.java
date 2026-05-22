package appolloni.migliano.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;
import appolloni.migliano.ManagerScene;
import appolloni.migliano.bean.BeanGruppo;
import appolloni.migliano.bean.BeanMessaggi;
import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.controller.ControllerChat;
import appolloni.migliano.exception.CampiVuotiException;
import appolloni.migliano.exception.EntitaNonTrovata;
import appolloni.migliano.exception.ErroreDiSistema;

public class GUIChat {

    @FXML private Label lbNomeGruppo;
    @FXML private ListView<String> listaMessaggi;
    @FXML private TextField txtMessaggio;
    @FXML private Label lbInvio;
    @FXML private Label lbMateria;
    @FXML private Label lbCitta;
    @FXML private Label lbLuogo;

    private BeanUtenti beanUtente;
    private BeanGruppo beanGruppo;
    private ManagerScene managerScene = new ManagerScene();
    
    private ControllerChat controllerChat = new ControllerChat();

    public void initData(BeanUtenti bean, BeanGruppo gruppoCorr) {
        this.beanUtente = bean;
        this.beanGruppo = gruppoCorr;

        lbNomeGruppo.setText(gruppoCorr.getNome());
        caricaInformazioniGruppo();
        aggiornaLista();
    }


    private void caricaInformazioniGruppo() {

        try{
          BeanGruppo infoComplete = controllerChat.recuperaInfoGruppo(this.beanGruppo);
          if (infoComplete != null) {
            lbMateria.setText(infoComplete.getMateria());
            lbCitta.setText(infoComplete.getCitta());
            lbLuogo.setText(infoComplete.getLuogo() != null ? infoComplete.getLuogo() : "Non specificato");
        }
        }catch(ErroreDiSistema | EntitaNonTrovata e){
         managerScene.gestioneErrore("Errore di sistema", e.getMessage(), lbCitta);
        }
    }

    private void aggiornaLista() {
        listaMessaggi.getItems().clear();
        try {
            List<BeanMessaggi> lista = controllerChat.recuperaMessaggi(this.beanGruppo);
            
            for (BeanMessaggi b : lista) {
               
                String riga = b.getMittente() + ": " + b.getMess();
                listaMessaggi.getItems().add(riga);
            }

            if (!listaMessaggi.getItems().isEmpty()) {
                listaMessaggi.scrollTo(listaMessaggi.getItems().size() - 1);
            }
        } catch (ErroreDiSistema e) {
           managerScene.gestioneErrore("Errore di sistema", e.getMessage(), lbCitta);
        }catch(IllegalArgumentException e){ 
            managerScene.gestioneErrore("Errore generico", e.getMessage(), lbCitta);
        }catch(EntitaNonTrovata ex){
            managerScene.gestioneErrore("Errore caricamento", ex.getMessage(), lbCitta);

        }
    }

    @FXML
    public void inviaMessaggio() {
        String text = txtMessaggio.getText().trim();

        try {
            controllerChat.inviaMessaggio(beanUtente, beanGruppo, text);
            
            aggiornaLista();
            txtMessaggio.clear();
            lbInvio.setText(""); 
        }catch(ErroreDiSistema e){
            managerScene.gestioneErrore("Errore di sistema", e.getMessage(), lbCitta);
        }catch(IllegalArgumentException e){
            managerScene.gestioneErrore("Errore generico", e.getMessage(), lbCitta);
        }catch(EntitaNonTrovata e){
            managerScene.gestioneErrore("Errore caricamento dati", e.getMessage(), lbCitta);
        }catch(CampiVuotiException e){
            managerScene.gestioneErrore("Dati mancanti", e.getMessage(), lbCitta);

        }
    }
    
    @FXML
    public void clickAbbandona(ActionEvent event) {
        try {
            controllerChat.abbandonaGruppo(beanUtente, beanGruppo);
            tornaIndietro(event);
        } catch(ErroreDiSistema e) {
          managerScene.gestioneErrore("Errore di sistema", e.getMessage(), lbCitta);
        }catch(EntitaNonTrovata e){
            managerScene.gestioneErrore("Errore:", e.getMessage(), lbCitta);

        }
    }

    @FXML
    public void tornaIndietro(ActionEvent event) {
        try {
            managerScene.avviaMainMenu(event, beanUtente);

        } catch (IOException e) {
         managerScene.gestioneErrore("Errore grave di sistema", "Impossibile caricare l'interfaccia grafica.", lbCitta);
        }
    }

}


    