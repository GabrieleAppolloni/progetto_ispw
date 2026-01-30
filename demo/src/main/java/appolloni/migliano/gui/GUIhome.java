package appolloni.migliano.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

import appolloni.migliano.ManagerScene;

public class GUIhome {

    private ManagerScene managerScene = new ManagerScene();

    @FXML
    public void clickRegistrazione(ActionEvent event) throws IOException {

        managerScene.cambiaScena(event,"/creazioneUtente.fxml");
    }

    @FXML
    public void clickAccedi(ActionEvent event) throws IOException {
      managerScene.cambiaScena(event,"/login.fxml");
    }
}
