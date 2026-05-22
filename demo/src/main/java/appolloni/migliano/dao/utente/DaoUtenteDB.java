package appolloni.migliano.dao.utente;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import appolloni.migliano.entity.Utente;
import appolloni.migliano.exception.ErroreDiSistema;
import appolloni.migliano.factory.FactoryUtenti;
import appolloni.migliano.entity.Host;
import appolloni.migliano.interfacce.InterfacciaDaoUtente;

public class DaoUtenteDB implements InterfacciaDaoUtente{
    private static final String ERRORESQL = "ERRORE SALVATGGIO UTENTE SQL";
    private static final Logger logger = Logger.getLogger(DaoUtenteDB.class.getName());
    private static final String SALVAUTENTE = "INSERT INTO utenti(dtype,nome,cognome,email,citta,password,nome_attivita,tipo_attivita)" + "VALUES (?,?,?,?,?,?,?,?)";
    private static final String CERCAUTENTE = "SELECT dtype, nome, cognome, email, citta, password, nome_attivita, tipo_attivita FROM utenti WHERE email = ?" ;
    private static final String UPDATE = "UPDATE utenti SET password = ? WHERE email = ?";
    private Connection conn; 
    public DaoUtenteDB(Connection connessione){
        this.conn = connessione;
    }

    @Override
    public void salvaUtente(Utente u) throws ErroreDiSistema{

        String sql = SALVAUTENTE;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(2, u.getName());
            ps.setString(3, u.getCognome());
            ps.setString(4, u.getEmail());
            ps.setString(5, u.getCitta());
            ps.setString(6, u.getPass());

            if(u instanceof Host host){
                ps.setString(1, "Host");
                ps.setString(7, host.getNomeAttivita());
                ps.setString(8, host.getTipoAttivita());

            }else{
                ps.setString(1,"Studente");
                ps.setNull(7, Types.VARCHAR);
                ps.setNull(8, Types.VARCHAR);
            }
            ps.executeUpdate();
        }catch(SQLException e){

             logger.log(Level.SEVERE, e, () -> ERRORESQL + u.getEmail());

            throw new ErroreDiSistema("Errore salvataggio utente",e);
        }
    }

    @Override
    public Utente cercaUtente(String search) throws ErroreDiSistema{
        String sql = CERCAUTENTE;
        Utente u = null;

        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, search);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
             String tipo = rs.getString("dtype");
             if("Host".equals(tipo)){
                u = FactoryUtenti.creazione(tipo, rs.getString("nome"),rs.getString("cognome"),rs.getString("email"),rs.getString("citta"),rs.getString("password"));
                ((Host)u).setTipoAttivita(rs.getString("tipo_attivita"));
                ((Host)u).setNomeAttivita(rs.getString("nome_attivita"));
             }else{
                u = FactoryUtenti.creazione(tipo, rs.getString("nome"),rs.getString("cognome"),rs.getString("email"),rs.getString("citta"),rs.getString("password"));


             }
            }
        }catch(SQLException e){
            logger.log(Level.SEVERE, e, () -> ERRORESQL );
            throw new ErroreDiSistema("Errore ricerca Utente.",e);

        }

        return u;
    }


    @Override
    public void aggiornaPassword(String email, String nuovaPass) throws ErroreDiSistema{
        String sql = UPDATE;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nuovaPass);
            ps.setString(2, email);
            ps.executeUpdate();
        }catch(SQLException e){
            logger.log(Level.SEVERE, e, () -> ERRORESQL + email);
            throw new ErroreDiSistema("Aggiornamento Password fallito.",e);

        }
    }

}