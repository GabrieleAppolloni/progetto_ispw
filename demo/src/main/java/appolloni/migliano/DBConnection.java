package appolloni.migliano;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static Connection conn;


    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            try {
                Properties props = new Properties();
              try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("config.properties")) {
                    
                    if (input == null) {
                        throw new SQLException("File config.properties non trovato nelle risorse!");
                    }
                    props.load(input);
                    
                } catch (IOException e) {
                    throw new SQLException("Errore lettura config.properties", e);
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


    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            
        }
    }
}