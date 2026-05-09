package appolloni.migliano.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import appolloni.migliano.HelperErrori;
import appolloni.migliano.ManagerScene;
import appolloni.migliano.bean.BeanStruttura;
import appolloni.migliano.controller.ControllerModificaStrutturHost;
import appolloni.migliano.exception.CampiVuotiException;
import appolloni.migliano.exception.ErroreDiSistema;

public class GUIModificaStruttura {

    @FXML private TextField txtNome;
    @FXML private TextField txtIndirizzo;
    @FXML private TextField txtCitta;
    @FXML private TextField txtOrario;
    @FXML private CheckBox chkWifi;
    @FXML private CheckBox chkRistorazione;

    private BeanStruttura strutturaCorrente;

    private String vecchioNome; 
    private ManagerScene managerScene = new ManagerScene();

    private ControllerModificaStrutturHost controllerApp = new ControllerModificaStrutturHost();
    public void initData(BeanStruttura struttura) {
        this.strutturaCorrente = struttura;
        this.vecchioNome = struttura.getName(); 

            txtNome.setText(struttura.getName());
            txtIndirizzo.setText(struttura.getIndirizzo());
            txtCitta.setText(struttura.getCitta());
            txtOrario.setText(struttura.getOrario());
            chkWifi.setSelected(struttura.hasWifi());
            chkRistorazione.setSelected(struttura.hasRistorazione());
    }

    @FXML
    public void clickSalva(ActionEvent event) {
        try {

            if (txtNome.getText().trim().isEmpty()) {
                HelperErrori.errore("Il nome non può essere vuoto!","Inserire il nome");
            }
            strutturaCorrente.setName(txtNome.getText().trim());
            strutturaCorrente.setIndirizzo(txtIndirizzo.getText().trim());
            strutturaCorrente.setCitta(txtCitta.getText().trim());
            strutturaCorrente.setOrario(txtOrario.getText().trim());
            strutturaCorrente.setWifi(chkWifi.isSelected());
            strutturaCorrente.setRistorazione(chkRistorazione.isSelected());


            controllerApp.aggiornaStruttura(strutturaCorrente, vecchioNome);

            chiudiFinestra(event);

        }catch(ErroreDiSistema e){
            managerScene.gestioneErrore("Errore di sistema", e.getMessage(), chkRistorazione);
        }catch(CampiVuotiException e){
            HelperErrori.errore("Dati mancanti ", "Perfavore inserire tutti i dati");
        }
    }

    @FXML
    public void clickAnnulla(ActionEvent event) {
        chiudiFinestra(event);
    }

    private void chiudiFinestra(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
 
}