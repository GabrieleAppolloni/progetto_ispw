package appolloni.migliano;
import java.util.logging.*;
import java.io.IOException;

public class ConfigLogger {
    public static void inizializzaLog() {
        try {
            Logger rootLogger = Logger.getLogger(""); 
            FileHandler fileHandler = new FileHandler("app_errori.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            rootLogger.addHandler(fileHandler);
            rootLogger.setLevel(Level.WARNING);

        } catch (IOException e) {
            System.err.println("Impossibile creare il file di log!");
        }
    }
}
