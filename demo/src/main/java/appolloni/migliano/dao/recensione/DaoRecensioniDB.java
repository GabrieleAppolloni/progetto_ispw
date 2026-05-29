package appolloni.migliano.dao.recensione;

import appolloni.migliano.interfacce.InterfacciaDaoRecensioni;
import appolloni.migliano.interfacce.InterfacciaDaoStruttura;
import appolloni.migliano.interfacce.InterfacciaDaoUtente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import appolloni.migliano.entity.Recensione;
import appolloni.migliano.entity.Utente;
import appolloni.migliano.exception.ErroreDiSistema;
import appolloni.migliano.factory.AbstractFactoryDao;
import appolloni.migliano.entity.Struttura;
import appolloni.migliano.entity.Studente;



public class DaoRecensioniDB implements InterfacciaDaoRecensioni {

    private final Connection conn;
    private final InterfacciaDaoUtente daoUtente;
    private final InterfacciaDaoStruttura daoStruttura;

    private static final Logger logger = Logger.getLogger(DaoRecensioniDB.class.getName());

    private static final String SELECTDB = "SELECT autore, nome_struttura, gestore_struttura, voto, testo  FROM recensioni WHERE nome_struttura = ? AND gestore_struttura = ?";
    private static final String INSERTRECENSIONE =  "INSERT INTO recensioni(autore, nome_struttura, gestore_struttura, voto, testo) VALUES (?, ?, ?, ?, ?)";

    public DaoRecensioniDB(Connection conn) {
        this.conn = conn;
        this.daoStruttura = AbstractFactoryDao.getDao().getDaoStruttura();
        this.daoUtente = AbstractFactoryDao.getDao().getDaoUtente();
    }
    private Studente casting(Utente u) {
     if (u instanceof Studente studente) {
        return studente;
     } else {
        throw new IllegalArgumentException("Errore recupero dati");
     }
    }

    @Override
    public void salvaRecensione(Recensione r) throws ErroreDiSistema{

        String sql = INSERTRECENSIONE;

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        
        String emailPulita = r.getAutore().getEmail().trim();
       
        ps.setString(1, emailPulita); 
        ps.setString(2, r.getStrutturaRecensita().getName().trim()); 
        ps.setString(3, r.getStrutturaRecensita().getGestore().trim()); 
        ps.setInt(4, r.getVoto()); 
        ps.setString(5, r.getTesto().trim()); 

        ps.executeUpdate();

        if (!conn.getAutoCommit()) {
            conn.commit();
        }
    }catch(SQLException e){
        logger.log(Level.SEVERE,"Errore salvataggio di recensione",e);
        throw new ErroreDiSistema("Errore salvataggio recensione", e);

    }

    }

    public List<Recensione> getRecensioniByStruttura(String idStruttura, String nomeGestore) throws ErroreDiSistema {
        List<Recensione> lista = new ArrayList<>();
        String sql = SELECTDB;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idStruttura);
            ps.setString(2,nomeGestore);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    
                     Struttura struttura = daoStruttura.cercaStruttura(rs.getString("nome_struttura"),rs.getString("gestore_struttura"));
                     Studente s = casting(daoUtente.cercaUtente(rs.getString("autore")));
                     Recensione r = new Recensione(rs.getString("testo"),rs.getInt("voto"),s,struttura);
                     lista.add(r);
                    
                }
            }

        }catch(SQLException e){
            logger.log(Level.SEVERE,"Errore recupero recensioni struttura",e);
            throw new ErroreDiSistema("Errore recupero recensioni struttura", e);
        }
        return lista;
    }
}