package appolloni.migliano;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Connection conn; // L'unica connessione condivisa

    // Parametri (meglio se letti da un file config, ma per ora ok qui)
    private static final String URL = "jdbc:mysql://localhost:3306/corso_java";
    private static final String USER = "root";
    private static final String PWD = "RomaCavalloVergogna20!";

    // Metodo statico per ottenere l'istanza
    public static Connection getConnection() {
        try {
            // Se la connessione è chiusa o nulla, la apriamo
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(URL, USER, PWD);
                System.out.println("Nuova connessione al DB aperta.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
    
    // Metodo per chiudere (da chiamare quando chiudi l'app)
    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}