
package appolloni.migliano.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.List;
import javafx.scene.image.Image;      
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;  
import java.io.File;

import appolloni.migliano.DBConnection;
import appolloni.migliano.ManagerScene;
import appolloni.migliano.bean.BeanRecensioni;
import appolloni.migliano.bean.BeanStruttura;
import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.controller.ControllerMenuHost;
import appolloni.migliano.exception.CampiVuotiException;
import appolloni.migliano.exception.EntitaNonTrovata;
import appolloni.migliano.exception.ErroreDiSistema;

public class GUIhostMenu {


    @FXML private ImageView imgStruttura; 
    @FXML private Label lblNome;
    @FXML private Button btnModifica;
    @FXML private VBox containerRecensioni;
    @FXML private Label lblNotifiche;
    @FXML private Label lblStrutturaNome;
    @FXML private Label lblTipo; 
    @FXML private Label lblCitta;
    @FXML private Label lblIndirizzo;
    @FXML private Label lblOrario;
    @FXML private Label lblWifi;
    @FXML private Label lblRistorazione;
    @FXML private Label lblTipoAttivita;
    @FXML private Label lblGestore;
    @FXML private Label lblMediaRecensioni;
    
    private BeanUtenti beanUtente;
    private BeanStruttura beanStruttura;
    private ControllerMenuHost controllerStruttura;
    private ManagerScene managerScene = new ManagerScene();

    public void initData(BeanUtenti utente){

        this.beanUtente = utente;
        this.controllerStruttura = new ControllerMenuHost();       
        lblNome.setText(this.beanUtente.getName());

        caricaInformazioni();
        caricaRecensioni();


    }

    private VBox creaBoxRecensione(BeanRecensioni r) {
        VBox box = new VBox(5);
        box.setPadding(new Insets(10));
        box.setStyle("-fx-background-color: #f0f8ff; -fx-border-color: #bad3ff; -fx-border-radius: 5; -fx-background-radius: 5;");
        
        Label lblAutore = new Label(r.getAutore() + " ha scritto:");
        lblAutore.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");
        
        Label lblVoto = new Label("Voto: " + r.getVoto() + "/5 ⭐");
        lblVoto.setStyle("-fx-text-fill: #ff9800; -fx-font-weight: bold;");
        
        Label lblTesto = new Label(r.getTesto());
        lblTesto.setWrapText(true); 
        lblTesto.setStyle("-fx-font-style: italic;");

        box.getChildren().addAll(lblAutore, lblVoto, lblTesto);
        return box;
    }
  
    private void caricaRecensioni() {
      
     try{
        beanStruttura = controllerStruttura.visualizzaStrutturaHost(this.beanUtente.getEmail());
        List<BeanRecensioni> recensioni = controllerStruttura.cercaRecensioniPerStruttura(beanStruttura);
        
        
        containerRecensioni.getChildren().clear();

        if (recensioni.isEmpty()) {
            containerRecensioni.getChildren().add(new Label("Non hai ancora ricevuto recensioni."));
            return;
        }

        
        for (BeanRecensioni r : recensioni) {
            VBox boxRecensione = creaBoxRecensione(r);
            containerRecensioni.getChildren().add(boxRecensione);
        }
      }catch(ErroreDiSistema e){
        managerScene.gestioneErrore("Errore di sistema", e.getMessage(), btnModifica);
      }catch(CampiVuotiException ex){
        managerScene.gestioneErrore("Dati mancanti", ex.getMessage(), btnModifica);

      }
    }
    
    private void caricaInformazioni(){
       
        try {
            double mediaVoto = 0.0;

            BeanStruttura struttura = controllerStruttura.visualizzaStrutturaHost(this.beanUtente.getEmail());
            try{
             mediaVoto = controllerStruttura.calcolaMediaVoti(struttura, beanUtente);
            }catch(EntitaNonTrovata e){
                managerScene.gestioneErrore("Dati mancanti", e.getMessage(), btnModifica);

            }


            lblMediaRecensioni.setText(String.format("Voto medio: %.2f/5",Double.toString(mediaVoto) ));
            lblStrutturaNome.setText(struttura.getName());
            lblCitta.setText(struttura.getCitta());
            lblIndirizzo.setText(struttura.getIndirizzo());
            lblGestore.setText(struttura.getGestore());
            lblOrario.setText(struttura.getOrario());
            lblTipo.setText(struttura.getTipo());
            lblTipoAttivita.setText(struttura.getTipoAttivita());

            if(struttura.hasWifi()){
                 lblWifi.setText( "Si");


            }else{
               lblWifi.setText( "No");
            }

            if(struttura.hasRistorazione()){
              lblRistorazione.setText( "Si");


            }else{
               lblRistorazione.setText( "No");
            }
            String nomeFoto = struttura.getFoto();
      
         String pathCompleto = System.getProperty("user.home") + File.separator + "IspwImages" + File.separator + nomeFoto;
         File fileFoto = new File(pathCompleto);
        
       
         if (nomeFoto != null && !nomeFoto.isEmpty() && fileFoto.exists()) {
             String imageUrl = fileFoto.toURI().toString();
             imgStruttura.setImage(new Image(imageUrl));
         } 
        }catch (ErroreDiSistema e){
            managerScene.gestioneErrore("Errore di sistema", e.getMessage(), btnModifica);
            try{
             managerScene.cambiaScena(null, "/login.fxml");
            }catch(IOException e2){
              managerScene.gestioneErrore("Errore caricamento interfaccia", "Riprovare", btnModifica);

            }
        }catch(CampiVuotiException ex){
         managerScene.gestioneErrore("Errore, dati mancanti",ex.getMessage() , btnModifica);
        }
    }
    @FXML
    public void clickModificaImmagini(ActionEvent event) {
     
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Cambia foto struttura");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Immagini", "*.jpg", "*.png", "*.jpeg"));
        
        
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        File fileSelezionato = fileChooser.showOpenDialog(stage);

        if (fileSelezionato != null) {
            try {
                
                String nuovoNomeFile = salvaFileSuDisco(fileSelezionato);

              
                controllerStruttura.cambiaFoto(beanUtente.getEmail(), nuovoNomeFile);

                imgStruttura.setImage(new Image(fileSelezionato.toURI().toString()));

            }catch(IOException e){
                 managerScene.gestioneErrore("Errore grave di sistema", "Impossibile salvare i dati.", btnModifica);
            } catch(ErroreDiSistema e){
                managerScene.gestioneErrore("Errore di sistema", e.getMessage(), btnModifica);
            }catch(CampiVuotiException e){
                managerScene.gestioneErrore("Errore caricamento", e.getMessage(), btnModifica);
            }
        }
    }


    private String salvaFileSuDisco(File fileInput) throws IOException {
        String folderPath = System.getProperty("user.home") + File.separator + "IspwImages";
        File folder = new File(folderPath);
        if (!folder.exists()) folder.mkdir();

        
        String nuovoNome = "FotoStruttura_" + System.currentTimeMillis() + "_" + fileInput.getName();
        File destinazione = new File(folder, nuovoNome);

        Files.copy(fileInput.toPath(), destinazione.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return nuovoNome;
    }

    @FXML
  public void clickAggiornaDati(ActionEvent event) {
    try {
       managerScene.modificaDatiHost( beanUtente, beanStruttura, btnModifica);
       caricaInformazioni();

    } catch (ErroreDiSistema e) {
        managerScene.gestioneErrore("Errore di sistema", e.getMessage(), btnModifica);
    }catch(IOException | SQLException e){
         managerScene.gestioneErrore("Errore grave di sistema", "Errore durante l'accesso ai dati.", btnModifica);
    }
  }


    @FXML
    public void clickLogout(ActionEvent event) {
        try{
         DBConnection.getInstance().closeConnection();
         managerScene.cambiaScena(event, "/home.fxml");
        }catch(IOException |SQLException e ){
             managerScene.gestioneErrore("Errore grave di sistema", "Impossibile caricare l'interfaccia grafica.", btnModifica);

        }
    }




}
