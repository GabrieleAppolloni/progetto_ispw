package appolloni.migliano.interfacce;

import java.sql.SQLException;

import appolloni.migliano.entity.Utente;

public interface InterfacciaUtente {

    void salvaUtente(Utente u) throws SQLException, Exception;
    Utente cercaUtente(String email) throws SQLException, Exception;
    void aggiornaPassword(String email, String nuovaPass) throws SQLException, Exception;
    
}
