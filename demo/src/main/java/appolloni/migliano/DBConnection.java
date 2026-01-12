package appolloni.migliano;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static Connection conn;

    // Metodo statico per ottenere l'istanza
    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            try {
                Properties props = new Properties();
                try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
                    props.load(input);
                } catch (IOException e) {
                    
                    throw new SQLException("Impossibile trovare il file config.properties", e);
                }

                String url = props.getProperty("db.url");
                String user = props.getProperty("db.user");
                String pwd = props.getProperty("db.password");

                conn = DriverManager.getConnection(url, user, pwd);

            } catch (SQLException e) {
                
                throw new SQLException("Errore di connessione al Database", e);
            }
        }
        return conn;
    }
}