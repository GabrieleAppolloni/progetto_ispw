package appolloni.migliano.dao.messaggio;

import appolloni.migliano.entity.Gruppo;
import appolloni.migliano.entity.Messaggio;
import appolloni.migliano.entity.Studente;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import appolloni.migliano.entity.Utente;
import appolloni.migliano.exception.ErroreDiSistema;
import appolloni.migliano.factory.AbstractFactoryDao;
import appolloni.migliano.interfacce.InterfacciaDaoMessaggi;
import appolloni.migliano.interfacce.InterfacciaDaoUtente;

import java.sql.*;

public class DaoMessaggioDB implements InterfacciaDaoMessaggi {
    private static final Logger logger = Logger.getLogger(DaoMessaggioDB.class.getName());
    private static final String CERCAMESSAGGIO = "SELECT testo, nome_gruppo, email_mittente, data_invio FROM messaggi WHERE nome_gruppo = ? ORDER BY data_invio ASC";
    private static final String NUOVOMESS = "INSERT INTO MESSAGGI (testo,nome_gruppo, email_mittente, data_invio ) VALUES (?,?,?,?) ";
   
    private Connection conn;
    public DaoMessaggioDB(Connection connessione){
        this.conn = connessione;
    }

    private Studente casting(Utente u) {
     if (u instanceof Studente) {
        return (Studente) u;
     } else {
        throw new IllegalArgumentException("Errore recupero dati");
     }
    }
    
    @Override
    public void nuovoMessaggio(Messaggio messaggio) throws ErroreDiSistema {

        String sql = NUOVOMESS;
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, messaggio.getMess());
            ps.setString(2, messaggio.getGruppo().getNome());
            ps.setString(3,messaggio.getMittente().getEmail() );
            ps.setTimestamp(4, messaggio.getTime());

            ps.executeUpdate();
        }catch(SQLException e){
            logger.log(Level.SEVERE,"Errore creazione messaggio",e);
            throw new ErroreDiSistema("Errore creazione messsaggio", e);
        }

    }
    
    @Override
    public List<Messaggio> cercaMessaggio(Gruppo gruppo) throws ErroreDiSistema{
        List<Messaggio> messaggi = new ArrayList<>();

        String sql = CERCAMESSAGGIO;

        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, gruppo.getNome());
           try( ResultSet rs = ps.executeQuery()){
    
             while(rs.next()){
                 String mess = rs.getString(1);
                 String emailMitt= rs.getString(3);
                 Timestamp time = rs.getTimestamp(4);
                 InterfacciaDaoUtente dao = AbstractFactoryDao.getDao().getDaoUtente();
                 Utente mittente = dao.cercaUtente(emailMitt);
                 Studente m = casting(mittente);
                 Messaggio messaggio = new Messaggio(mess, gruppo, m);
                 messaggio.setTime(time);
                 messaggi.add(messaggio);
             }

            }
        }catch(SQLException e){
            logger.log(Level.SEVERE,"Errore ricerca messaggi", e);
            throw new ErroreDiSistema("Errore ricerca messaggi", e);

        }



        return messaggi;
    }
}