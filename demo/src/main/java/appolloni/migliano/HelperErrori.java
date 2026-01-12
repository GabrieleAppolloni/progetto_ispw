package appolloni.migliano;
import javafx.scene.control.Alert;


public class HelperErrori {


    
    private HelperErrori() {
        throw new UnsupportedOperationException("Questa è una classe di utilità e non può essere istanziata");
    }
    public static void info(String titolo, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titolo);
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }

    public static void errore(String titolo, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titolo);
        alert.setHeaderText("Si è verificato un errore");
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
}
