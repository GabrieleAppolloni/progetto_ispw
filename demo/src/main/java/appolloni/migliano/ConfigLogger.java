package appolloni.migliano;
import java.util.logging.*;
import java.io.IOException;
import appolloni.migliano.exception.ErroreDiSistema;

public class ConfigLogger {
    private ConfigLogger(){
        throw new IllegalArgumentException("Impossibile istanziare");
    }
    public static void inizializzaLog() throws ErroreDiSistema {
        try {
            Logger rootLogger = Logger.getLogger(""); 
            FileHandler fileHandler = new FileHandler("app_errori.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            rootLogger.addHandler(fileHandler);
            rootLogger.setLevel(Level.WARNING);

        } catch (IOException e) {
            throw new ErroreDiSistema("Impossibile creare il file di log!", e);
        }
    }
}
