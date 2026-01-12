package appolloni.migliano.controller_gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;

import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.bean.BeanStruttura;
import appolloni.migliano.controller.ControllerGestioneStrutture;
import appolloni.migliano.exception.CampiVuotiException; 

public class GUISegnalaStruttura {

    @FXML private TextField txtNome;
    @FXML private TextField txtCitta;
    @FXML private TextField txtIndirizzo;
    @FXML private TextField txtGestore;
    @FXML private TextField txtOrario;
    @FXML private ComboBox<String> comboTipoAttivita;
    @FXML private ComboBox<String> comboTipo;
    @FXML private CheckBox checkWifi;
    @FXML private CheckBox checkRistorazione;
    @FXML private Label lblRisultato;

    private BeanUtenti studenteLoggato;
    private ControllerGestioneStrutture controllerApp = new ControllerGestioneStrutture(); 

    public void initData(BeanUtenti utente) {
        this.studenteLoggato = utente;
        comboTipo.getItems().addAll("Privata","Pubblica");
        comboTipoAttivita.getItems().addAll("Bar","Università","Biblioteca");
    }

    @FXML
    public void clickSegnala(ActionEvent event) {
        try {
         
            if(comboTipo.getValue() == null || txtNome.getText().isEmpty() || txtCitta.getText().isEmpty()) {
                lblRisultato.setText("Compila i campi.");
                lblRisultato.setStyle("-fx-text-fill: red;");
                return;
            }

            String nomeGestore = txtGestore.getText().trim();
            if (nomeGestore.isEmpty()) {
                nomeGestore = "Sconosciuto"; 
            }

            BeanStruttura struttura = new BeanStruttura(
                comboTipo.getValue(), 
                txtNome.getText().trim(), 
                txtCitta.getText().trim(), 
                txtIndirizzo.getText().trim(), 
                txtOrario.getText().trim(), 
                checkWifi.isSelected(), 
                checkRistorazione.isSelected(), 
                comboTipoAttivita.getValue(), 
                nomeGestore
            );
            
            controllerApp.CreaStruttura(studenteLoggato, struttura); 
            

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successo");
            alert.setHeaderText(null);
            alert.setContentText("Grazie! La struttura è stata segnalata con successo.");
            alert.showAndWait(); 

            clickIndietro(event);
        }catch(CampiVuotiException e){
            lblRisultato.setText("Errore, compilare tutti i campi");
             lblRisultato.setStyle("-fx-text-fill: red;");

        } catch (Exception e) {
            e.printStackTrace();
            lblRisultato.setText("Errore: " + e.getMessage());
            lblRisultato.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    public void clickIndietro(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainMenu.fxml")); 
            Parent root = loader.load();
            
            GUImainMenu controllerMenu = loader.getController();
            controllerMenu.initData(studenteLoggato); 

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            
            stage.getScene().setRoot(root); 

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}