package appolloni.migliano.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import appolloni.migliano.DBConnection;
import appolloni.migliano.HelperErrori;
import appolloni.migliano.ManagerScene;
import appolloni.migliano.bean.BeanGruppo;
import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.controller.ControllerGestioneGruppo;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class GUImainMenu {
    
   
  
    @FXML private Label lblSideNome;
    @FXML private Label lblSideEmail;
    @FXML private Label lblSideRuolo;
    @FXML private Label lblSideCitta;
    @FXML private VBox containerGruppi; 
    @FXML private Button bNuovaStruttura;
    @FXML private Button bNuovoGruppo;
    @FXML private Button logout;
    @FXML private Button bProfilo;
    
    private BeanUtenti bean;
    private ControllerGestioneGruppo controllerGruppo = new ControllerGestioneGruppo();
    private ManagerScene managerScene = new ManagerScene();

    public void initData(BeanUtenti utente){
        this.bean = utente;
        
        caricaSidebarProfilo();
        caricaGruppi();
    }

    private void caricaSidebarProfilo() {
        if (bean != null) {
            lblSideNome.setText(bean.getName() + " " + bean.getCognome());
            lblSideEmail.setText(bean.getEmail());
            lblSideRuolo.setText(bean.getTipo().toUpperCase());
            lblSideCitta.setText(bean.getCitta() != null ? bean.getCitta() : "N/D");
        }
    }

    private void caricaGruppi(){
        containerGruppi.getChildren().clear();
        try{
            List<BeanGruppo> gruppoUser = controllerGruppo.visualizzaGruppi(bean);
            
            if (gruppoUser != null && !gruppoUser.isEmpty()) {
                for(BeanGruppo g : gruppoUser){
                    HBox riga = new HBox(10);
                    riga.setAlignment(Pos.CENTER_LEFT);
                    riga.setStyle("-fx-border-color: #eeeeee; -fx-padding: 10; -fx-background-color: white; -fx-background-radius: 5;");

                    VBox info = new VBox(2);
                    Label label = new Label(g.getNome());
                    label.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
                    Label subLabel = new Label(g.getMateria());
                    subLabel.setStyle("-fx-text-fill: #777; -fx-font-size: 12px;");
                    info.getChildren().addAll(label, subLabel);

                    Region spacer = new Region();
                    HBox.setHgrow(spacer, Priority.ALWAYS);

                    Button b = new Button("Apri Chat");
                    b.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-cursor: hand;");
                    b.setOnAction(e -> apriChat(g));

                    riga.getChildren().addAll(info, spacer, b);
                    containerGruppi.getChildren().add(riga);
                }
            } else {
                containerGruppi.getChildren().add(new Label("Non sei iscritto a nessun gruppo."));
            }
        } catch(Exception e){
           HelperErrori.errore("Errore Generico:", e.getMessage());
        }
    }

    private void apriChat(BeanGruppo gruppoSelezionato) {
        try {
         managerScene.avviaChat(gruppoSelezionato, bean, containerGruppi);

        } catch (IOException e) {
            HelperErrori.errore("Errore Generico:", e.getMessage());
        }
    }

    public void clickProfilo(ActionEvent event) {
        try{
         managerScene.apriProfilo(event, bean);
        }catch(IOException e){
            HelperErrori.errore("Errore I/O: ", e.getMessage());

        }
    }

    public void clickRicerca(ActionEvent event) throws IOException{
        managerScene.avviaRicerca(event, bean);
    }

    public void clickNuovaStruttura(ActionEvent event) throws IOException{
        managerScene.avviaNuovaStruttura(event, bean);
    }
    
    public void clickNuovoGruppo(ActionEvent event) throws IOException{
        managerScene.avviaCreazioneGruppo(event, bean);
    }

    public void clickLogout(ActionEvent event) throws IOException, SQLException {
        DBConnection.getInstance().closeConnection();
        managerScene.cambiaScena(event, "/home.fxml");
    }

}