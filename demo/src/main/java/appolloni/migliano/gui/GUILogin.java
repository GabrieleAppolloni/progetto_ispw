package appolloni.migliano.gui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

import appolloni.migliano.controller.ControllerLogin;
import appolloni.migliano.exception.CredenzialiSbagliateException;
import appolloni.migliano.exception.EmailNonValidaException;
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
        String email = txtEmail.getText().trim();
        String pass = txtPass.getText().trim();

        if(email.isEmpty() || pass.isEmpty()){
            lblRisultato.setText("Errore: informazioni mancanti");
            lblRisultato.setStyle(RED);
            return;
        }

        try {
         BeanUtenti userBean = new BeanUtenti(null, null,null, null, null, null);
         userBean.setEmail(email);
         userBean.setPassword(pass);
         BeanUtenti beanUtenteLoggato = login.verificaUtente(userBean);

        
         if((beanUtenteLoggato.getTipo()).equals("Studente")){
               managerScene.avviaMainMenu(event, beanUtenteLoggato);
          }else{
                managerScene.avviaMenuHost(event, beanUtenteLoggato);
          }

        }catch(CredenzialiSbagliateException e){
          lblRisultato.setText("Credenziali errate, riprova.");
          lblRisultato.setStyle(RED);


        }catch(EmailNonValidaException e){
            lblRisultato.setText("Formato email non corretto.");
            lblRisultato.setStyle(RED);
        }catch(Exception e){
            lblRisultato.setText("Errore generico.");
            lblRisultato.setStyle(RED);
        }

        txtEmail.clear();
        txtPass.clear();
    }
    
    public void clickIndietro(ActionEvent event) throws IOException{
           managerScene.cambiaScena(event, "/home.fxml");
    }
}
