package appolloni.migliano.controller_gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

import appolloni.migliano.bean.BeanGruppo;
import appolloni.migliano.bean.BeanStruttura;
import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.controller.ControllerGestioneGruppo;
import appolloni.migliano.controller.ControllerGestioneStrutture;

public class GUIRicerca {

    @FXML private ComboBox<String> comboScelta;
    @FXML private Label lblErrore;
    @FXML private VBox containerRisultati;
    @FXML private Button btnCerca;
    @FXML private VBox boxGruppi;
    @FXML private TextField txtGruppoNome;
    @FXML private TextField txtGruppoCitta;
    @FXML private TextField txtGruppoMateria;

    @FXML private VBox boxStrutture;
    @FXML private TextField txtStrutturaNome;
    @FXML private TextField txtStrutturaCitta;
    @FXML private ComboBox<String> comboStrutturaTipo;

    private BeanUtenti beanUtente;
    private ControllerGestioneGruppo controllerGruppo = new ControllerGestioneGruppo();
    private ControllerGestioneStrutture controllerStrutture= new ControllerGestioneStrutture();

    @FXML
    public void initialize() {
        comboScelta.getItems().addAll("Gruppo", "Struttura");
        comboStrutturaTipo.getItems().addAll("Tutti", "Bar", "Biblioteca", "Università");
        
        comboScelta.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            aggiornaForm(newVal);
        });
        comboScelta.getSelectionModel().selectFirst();
    }

    public void initData(BeanUtenti bean) {
        this.beanUtente = bean;
    }

    private void aggiornaForm(String scelta) {
        lblErrore.setText("");
        containerRisultati.getChildren().clear();
        
        if ("Gruppo".equals(scelta)) {
            boxGruppi.setVisible(true);   boxGruppi.setManaged(true);
            boxStrutture.setVisible(false); boxStrutture.setManaged(false);
        } else if ("Struttura".equals(scelta)) {
            boxStrutture.setVisible(true);  boxStrutture.setManaged(true);
            boxGruppi.setVisible(false);  boxGruppi.setManaged(false);
        }
    }

    @FXML
    public void clickCerca() {
        String scelta = comboScelta.getValue();
        containerRisultati.getChildren().clear();
        lblErrore.setText("");
        
        try {
            if ("Gruppo".equals(scelta)) {
                cercaGruppi();
            } else {
                cercaStrutture();
            }
        } catch (Exception e) {
            e.printStackTrace();
            lblErrore.setText("Errore: " + e.getMessage());
        }
    }

    private void cercaGruppi() throws Exception {
        String nome = txtGruppoNome.getText().trim();
        String citta = txtGruppoCitta.getText().trim();
        String materia = txtGruppoMateria.getText().trim();

        List<BeanGruppo> risultati = controllerGruppo.cercaGruppi(nome, citta, materia);
        System.out.println("DEBUG: Trovati " + risultati.size() + " gruppi.");

        if(risultati.isEmpty()) {
            Label empty = new Label("Nessun gruppo trovato.");
            empty.setStyle("-fx-text-fill: black;");
            containerRisultati.getChildren().add(empty);
            return;
        }

        for (BeanGruppo g : risultati) {
           

            HBox riga = new HBox(10);
            riga.setStyle("-fx-border-color: #ccc; -fx-padding: 10; -fx-background-color: white; -fx-background-radius: 5;");
            riga.setAlignment(Pos.CENTER_LEFT);

            VBox info = new VBox(2);
           
            Label lNome = new Label(g.getNome()); 
            lNome.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: black;");
            
            Label lDettagli = new Label(g.getCitta() + " • " + g.getMateria());
            lDettagli.setStyle("-fx-text-fill: #555;"); 
            
            info.getChildren().addAll(lNome, lDettagli);

            Region spacer = new Region(); HBox.setHgrow(spacer, Priority.ALWAYS);
            
            Button btnJoin = new Button("Unisci");
            btnJoin.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-cursor: hand;");
            
            btnJoin.setOnAction(event -> {
                try {
                    controllerGruppo.aggiungiGruppo(beanUtente, g);
                    btnJoin.setText("Iscritto!");
                    btnJoin.setDisable(true);
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Errore: " + e.getMessage());
                    alert.show();
                }
            });
            
            riga.getChildren().addAll(info, spacer, btnJoin);
            containerRisultati.getChildren().add(riga);
        }
    }

    private void cercaStrutture() throws Exception {
        String nome = txtStrutturaNome.getText().trim();
        String citta = txtStrutturaCitta.getText().trim();
        String tipo = comboStrutturaTipo.getValue();
        if("Tutti".equals(tipo)) tipo = null;

        List<BeanStruttura> risultati = controllerStrutture.cercaStrutture(nome, citta, tipo);
      

        if(risultati.isEmpty()) {
            Label empty = new Label("Nessuna struttura trovata.");
            empty.setStyle("-fx-text-fill: black;");
            containerRisultati.getChildren().add(empty);
            return;
        }

        for (BeanStruttura s : risultati) {
            

            HBox riga = new HBox(10);
            riga.setStyle("-fx-border-color: #ccc; -fx-padding: 10; -fx-background-color: white; -fx-background-radius: 5;");
            riga.setAlignment(Pos.CENTER_LEFT);

            VBox info = new VBox(5);
            
            String nomeStr = (s.getName() != null) ? s.getName() : "Nome sconosciuto";
         
            Label lNome = new Label(nomeStr); 
            lNome.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: black;");
            
            String tipoStr = (s.getTipoAttivita() != null) ? s.getTipoAttivita() : "Attività";
            String cittaStr = (s.getCitta() != null) ? s.getCitta() : "Città ignota";
            Label lDettagli = new Label(tipoStr + " a " + cittaStr);
            lDettagli.setStyle("-fx-text-fill: #555;");

            info.getChildren().addAll(lNome, lDettagli);
            Region spacer = new Region(); HBox.setHgrow(spacer, Priority.ALWAYS);

            Button btnDettagli = new Button("Dettagli");
            btnDettagli.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-cursor: hand;");
            
           
            btnDettagli.setOnAction(e -> {
              try {
               FXMLLoader loader = new FXMLLoader(getClass().getResource("/infoStruttura.fxml"));
               Parent root = loader.load();

               
               GUIdettagliStruttura controllerDettagli = loader.getController();
               controllerDettagli.initData(beanUtente, s); 

               Stage stage = (Stage) containerRisultati.getScene().getWindow();
               stage.getScene().setRoot(root); 

              } catch (IOException ex) {
                 ex.printStackTrace();
              }
            });
    

            riga.getChildren().addAll(info,spacer,btnDettagli);
            containerRisultati.getChildren().add(riga);
        }
    }

    @FXML
    public void clickIndietro(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainMenu.fxml"));
        Parent root = loader.load();
        GUImainMenu mainMenu = loader.getController();
        mainMenu.initData(beanUtente);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
    }
}