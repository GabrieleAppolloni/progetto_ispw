package appolloni.migliano.gui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

import appolloni.migliano.controller.ControllerLogin;
import appolloni.migliano.exception.CampiVuotiException;
import appolloni.migliano.exception.CredenzialiSbagliateException;
import appolloni.migliano.exception.EmailNonValidaException;
import appolloni.migliano.exception.ErroreDiSistema;
import appolloni.migliano.ManagerScene;
import appolloni.migliano.bean.BeanUtenti;

public class GUILogin {
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPass;
    @FXML private Label lblRisultato;
    private ManagerScene managerScene = new ManagerScene();
    private static final String RED = "-fx-text-fill: red;";

    private ControllerLogin login = new ControllerLogin();
    
    public void clickAccedi(ActionEvent event){


        try {
         BeanUtenti userBean = new BeanUtenti(null, null,null, txtEmail.getText().trim(),txtPass.getText().trim(), null);
         BeanUtenti beanUtenteLoggato = login.verificaUtente(userBean);

        
         if((beanUtenteLoggato.getTipo()).equals("Studente")){
               managerScene.avviaMainMenu(event, beanUtenteLoggato);
          }else{
                managerScene.avviaMenuHost(event, beanUtenteLoggato);
          }

        }catch(CredenzialiSbagliateException | CampiVuotiException | EmailNonValidaException e){
          lblRisultato.setText(e.getMessage());
          lblRisultato.setStyle(RED);
        }catch(ErroreDiSistema  e){
            managerScene.gestioneErrore("Errore di sistema", e.getMessage(), lblRisultato);

        }catch(IOException e){
             managerScene.gestioneErrore("Errore grave di sistema", "Impossibile caricare l'interfaccia", lblRisultato);
        }

        txtEmail.clear();
        txtPass.clear();
    }
    
    public void clickIndietro(ActionEvent event) {
        try{
           managerScene.cambiaScena(event, "/home.fxml");
        }catch(IOException e){
             managerScene.gestioneErrore("Errore grave di sistema", "Impossibile caricare l'interfaccia grafica.", lblRisultato);
        }
    }
}
