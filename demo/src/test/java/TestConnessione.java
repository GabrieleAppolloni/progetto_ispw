import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnessione {
    public static void main(String[] args) {
        
        // 1. Configurazione:
        // jdbc:mysql:// -> protocollo
        // localhost:3306 -> indirizzo del server
        // /corso_java -> nome del tuo database
        String url = "jdbc:mysql://localhost:3306/corso_java"; 
        String user = "root";
        String password = "RomaCavalloVergogna20!"; // <--- CAMBIA QUESTA!

        System.out.println("--- Inizio Test Connessione ---");

        try {
            // Tenta la connessione
            Connection conn = DriverManager.getConnection(url, user, password);
            
            // Se arrivi qui, ha funzionato!
            System.out.println(" SUCCESSO! Connessione stabilita con il database 'corso_java'");
            System.out.println("Oggetto connessione: " + conn.getClass().getName());
            
            // Chiudi sempre
            conn.close();
            
        } catch (SQLException e) {
            System.out.println("ERRORE: Impossibile connettersi.");
            System.out.println("Messaggio errore: " + e.getMessage());
        }
    }
}
