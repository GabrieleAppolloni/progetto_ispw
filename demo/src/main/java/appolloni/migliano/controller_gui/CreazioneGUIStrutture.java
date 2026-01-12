package appolloni.migliano.controller_gui;
import java.io.IOException;

import appolloni.migliano.HelperErrori;
import appolloni.migliano.bean.BeanStruttura;
import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.controller.ControllerGestioneStrutture;
import appolloni.migliano.controller.ControllerGestioneUtente;
import appolloni.migliano.exception.CampiVuotiException;
import appolloni.migliano.exception.EmailNonValidaException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;




public class CreazioneGUIStrutture {
    @FXML private ComboBox<String> comboTipo;
    @FXML private Label lblTipoAttivita;
    @FXML private CheckBox checkWifi;
    @FXML private CheckBox checkRistorazione;
    @FXML private Label lblNomeAttivita;;
    @FXML private TextField txtCitta;
    @FXML private TextField txtIndirizzo;
    @FXML private Label lblGestore;
    @FXML private Label lblRisultato;
    @FXML private TextField txtOrario;
    @FXML private ImageView imgAnteprima;
    @FXML private Label lblNomeFile;
    private File fileImmagineSelezionato = null;
    private BeanUtenti beanCurr;
    private BeanStruttura beanStruttura;

    private final String COLORE = "-fx-text-fill: red;";
    private ControllerGestioneStrutture controllerCreazioneStrutture = new ControllerGestioneStrutture();

     @FXML
     public void initialize(){
        comboTipo.getItems().addAll("Privata","Pubblica");
     }

     @FXML
     public void initData(BeanUtenti bean){
        this.beanCurr = bean;
         System.out.println("Benvenuto " + beanCurr.getName() + ", ora crea la tua struttura.");
         if (beanCurr != null) {
            lblGestore.setText(beanCurr.getName()); 
            lblNomeAttivita.setText(beanCurr.getNomeAttivita());
            lblTipoAttivita.setText(beanCurr.getTipoAttivita());
        }
     }


     @FXML
    public void clickCaricaFoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Scegli foto struttura");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Immagini", "*.png", "*.jpg", "*.jpeg"));
        
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        fileImmagineSelezionato = fileChooser.showOpenDialog(stage);

        if (fileImmagineSelezionato != null) {
            imgAnteprima.setImage(new Image(fileImmagineSelezionato.toURI().toString()));
            lblNomeFile.setText(fileImmagineSelezionato.getName());
        }
    }

     @FXML
     public void clickRegistra(){
        String tipoAttivita = beanCurr.getTipoAttivita();
        String nome = beanCurr.getNomeAttivita();
        String tipo = comboTipo.getValue();
        String citta = txtCitta.getText().trim();
        String indirizzo = txtIndirizzo.getText().trim();
        boolean wifi = checkWifi.isSelected();
        boolean ristorazione = checkRistorazione.isSelected();
        String gestore = beanCurr.getName();
        String orario = txtOrario.getText().trim();
        ControllerGestioneUtente controllerGestioneUtente = new ControllerGestioneUtente();

        
        try{
            String nomeFotoFinale = "placeholder.png";

            if (fileImmagineSelezionato != null) {
                nomeFotoFinale = salvaFileSuDisco(fileImmagineSelezionato);
            }
            controllerGestioneUtente.creazioneUtente(beanCurr);
            beanStruttura = new BeanStruttura(tipo, nome, citta, indirizzo, orario, wifi, ristorazione,tipoAttivita, gestore,nomeFotoFinale);
            try{
             controllerCreazioneStrutture.CreaStruttura(beanCurr,beanStruttura);
             lblRisultato.setText("Registrazione Effettuata con Successo!");
             lblRisultato.setStyle("-fx-text-fill: green;");
            }catch(CampiVuotiException e){
                lblRisultato.setText(e.getMessage());
                lblRisultato.setStyle(COLORE);

            }catch(EmailNonValidaException e){
                lblRisultato.setText(e.getMessage());
                lblRisultato.setStyle(COLORE);
            }catch(SQLException e){
                HelperErrori.errore("Errore base di dati: ", e.getMessage());

            }catch (Exception e){
                lblRisultato.setText(e.getMessage());
                lblRisultato.setStyle(COLORE);
            }
            
            
            pulisci(); 

            FXMLLoader loader =new FXMLLoader(getClass().getResource("/hostMenu.fxml"));
             Parent root = loader.load();
             GUIhostMenu hostMenu = loader.getController();
             hostMenu.initData(beanCurr);

            Stage stage = (Stage)((Node)lblRisultato).getScene().getWindow();
            stage.getScene().setRoot(root);
            
        


        }catch(CampiVuotiException e){

            lblRisultato.setText(e.getMessage());
            lblRisultato.setStyle(COLORE);
        
        }catch(SQLException e){
            HelperErrori.errore("Errore creazione struttura:", e.getMessage());

        }catch(IOException e){
            HelperErrori.errore("Errore salvataggio:", e.getMessage());
        }catch(Exception e){
            lblRisultato.setText("Errore: "+ e.getMessage());
            lblRisultato.setStyle(COLORE);
        }




     }
     @FXML 
     public void clickIndietro(ActionEvent event) throws IOException{
        try{
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/creazioneUtente.fxml"));
            Parent root = loader.load();
            CreazioneGUIController creazioneUtente = loader.getController();
        


            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
             stage.getScene().setRoot(root);
            
    
        } catch (Exception e) {
            e.printStackTrace();
            lblRisultato.setText("errore, impossibile tornare indietro");
        }



     }


     private String salvaFileSuDisco(File fileInput) throws IOException {

        String folderPath = System.getProperty("user.home") + File.separator + "IspwImages";
        File folder = new File(folderPath);
        if (!folder.exists()) folder.mkdir();

        String nuovoNome = "Struttura_" + System.currentTimeMillis() + "_" + fileInput.getName();
        File destinazione = new File(folder, nuovoNome);

        Files.copy(fileInput.toPath(), destinazione.toPath(), StandardCopyOption.REPLACE_EXISTING);
        
        return nuovoNome;
    }

    private void pulisci(){
   
        txtIndirizzo.clear();
        txtCitta.clear();
        comboTipo.getSelectionModel().clearSelection();
        checkRistorazione.setSelected(false);
        checkWifi.setSelected(false);
    }
}
