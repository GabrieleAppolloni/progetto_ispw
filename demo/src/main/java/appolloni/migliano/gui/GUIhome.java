package appolloni.migliano.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

import appolloni.migliano.HelperErrori;
import appolloni.migliano.ManagerScene;

public class GUIhome {

    private ManagerScene managerScene = new ManagerScene();

    @FXML
    public void clickRegistrazione(ActionEvent event) {
        try{
         managerScene.cambiaScena(event,"/creazioneUtente.fxml");
        }catch(IOException e){
             HelperErrori.errore("Errore grave di sistema", "Impossibile caricare l'interfaccia grafica.");
        }
    }

    @FXML
    public void clickAccedi(ActionEvent event) {
      try{  
       managerScene.cambiaScena(event,"/login.fxml");
      }catch(IOException e){
         HelperErrori.errore("Errore grave di sistema", "Impossibile caricare l'interfaccia grafica.");
      }
    }
}
