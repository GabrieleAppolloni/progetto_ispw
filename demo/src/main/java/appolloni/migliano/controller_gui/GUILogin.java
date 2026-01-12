package appolloni.migliano.controller_gui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import appolloni.migliano.controller.ControllerLogin;
import appolloni.migliano.exception.CredenzialiSbagliateException;
import appolloni.migliano.exception.EmailNonValidaException;
import appolloni.migliano.bean.BeanUtenti;

public class GUILogin {
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPass;
    @FXML private Label lblRisultato;

    private ControllerLogin login = new ControllerLogin();
    
    public void clickAccedi(ActionEvent event){
        String email = txtEmail.getText().trim();
        String pass = txtPass.getText().trim();

        if(email.isEmpty() || pass.isEmpty()){
            lblRisultato.setText("Errore: informazioni mancanti");
            lblRisultato.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
         BeanUtenti userBean = new BeanUtenti(null, null,null, null, null, null);
         userBean.setEmail(email);
         userBean.setPassword(pass);
         BeanUtenti beanUtenteLoggato = login.verificaUtente(userBean);

        
        if((beanUtenteLoggato.getTipo()).equals("Studente") && beanUtenteLoggato != null){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainMenu.fxml"));
                Parent root = loader.load();
                GUImainMenu guimainMenu = loader.getController();
                guimainMenu.initData(beanUtenteLoggato);

                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.getScene().setRoot(root);
            }else if(beanUtenteLoggato != null ){

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/hostMenu.fxml"));
                Parent root = loader.load();
                GUIhostMenu hIhostMenu = loader.getController();
                hIhostMenu.initData(beanUtenteLoggato);

                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.getScene().setRoot(root);
            
             }

        }catch(CredenzialiSbagliateException e){
          lblRisultato.setText("Credenziali errate, riprova.");
          lblRisultato.setStyle("-fx-text-fill: red;");


        }catch(EmailNonValidaException e){
            lblRisultato.setText("Formato email non corretto.");
            lblRisultato.setStyle("-fx-text-fill: red;");
        }catch(Exception e){
            lblRisultato.setText("Errore.");
            lblRisultato.setStyle("-fx-text-fill: red;");

            e.printStackTrace();
        }

        txtEmail.clear();
        txtPass.clear();
    }
    
    public void clickIndietro(ActionEvent event) throws IOException{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/home.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.getScene().setRoot(root);
    }
    
}
