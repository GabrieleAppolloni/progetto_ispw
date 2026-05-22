package appolloni.migliano.dao.gruppo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import appolloni.migliano.entity.Gruppo;
import appolloni.migliano.entity.Utente;
import appolloni.migliano.exception.ErroreDiSistema;
import appolloni.migliano.factory.AbstractFactoryDao;
import appolloni.migliano.factory.FactoryUtenti;
import appolloni.migliano.interfacce.InterfacciaDaoGruppo;
import appolloni.migliano.interfacce.InterfacciaDaoUtente;

public class DaoGruppoDB implements InterfacciaDaoGruppo {

    private final Connection conn;
    private static final Logger logger = Logger.getLogger(DaoGruppoDB.class.getName());
    private static final String INSERTGRUPPO = "INSERT INTO gruppi (nome, materia_studio, email_admin,citta,luogo) VALUES (?, ?, ?,?,?)";
    private static final String INSERTISCRIZIONE = "INSERT INTO iscrizioni (nome_gruppo, email_utente) VALUES (?, ?)";
    private static final String SELECTCERCAGRUPPO = "SELECT nome, materia_studio, email_admin, citta, luogo FROM gruppi WHERE nome =?";
    private static final String RECUPERAGRUPPI = "SELECT g.nome, g.materia_studio, g.email_admin, g.citta, g.luogo " +
                     "FROM gruppi g " +
                     "JOIN iscrizioni i ON g.nome = i.nome_gruppo " +
                     "WHERE i.email_utente = ?";

    private static final String ESISTEGRUPPO = "SELECT count(*) FROM gruppi WHERE nome = ?";
    private static final String DELETEGRUPPO = "DELETE FROM iscrizioni WHERE nome_gruppo = ? AND email_utente = ?";
    private static final String ELIMINAGRUPPO = "DELETE FROM gruppi WHERE nome = ?";
    private static final String ELIMINAMESSAGGI = "DELETE FROM messaggi WHERE nome_gruppo = ?";
    private static final String ELIMINAISCRIZIONI =  "DELETE FROM iscrizioni WHERE nome_gruppo = ?";

    private static final String SELECTRICERCAFILTRI = "SELECT nome, materia_studio, email_admin, citta, luogo FROM gruppi WHERE 1=1 "; 

    public DaoGruppoDB(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void creaGruppo(Gruppo gruppo) throws ErroreDiSistema {
        String sqlGruppo = INSERTGRUPPO;
        String sqlIscrizione = INSERTISCRIZIONE;

        try {
       
            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(sqlGruppo)) {
                ps.setString(1, gruppo.getNome());
                ps.setString(2, gruppo.getMateria());
                ps.setString(3, gruppo.getAdmin().getEmail());
                ps.setString(4, gruppo.getCitta());
                ps.setString(5, gruppo.getLuogo());
                ps.executeUpdate();
            }

            try (PreparedStatement ps = conn.prepareStatement(sqlIscrizione)) {
                ps.setString(1, gruppo.getNome());
                ps.setString(2, gruppo.getAdmin().getEmail());
                ps.executeUpdate();
            }
            
            conn.commit();

        } catch (SQLException e) {
           
            try {
                conn.rollback();
            } catch (SQLException ex) {
                
                logger.log(Level.SEVERE, "Impossibile eseguire il rollback della transazione!", ex);
            }
            
           
            logger.log(Level.SEVERE, "Errore SQL durante la creazione del gruppo: " + gruppo.getNome(), e);
            
           
            throw new ErroreDiSistema("Errore di sistema: impossibile creare il gruppo.", e);
            
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, "Impossibile ripristinare l'auto-commit sulla connessione.", ex);
            }
        }
    }

    @Override
   public Gruppo cercaGruppo(String nome) throws ErroreDiSistema{
        Gruppo gruppoCercato = null;
        String sql = SELECTCERCAGRUPPO;
        
        try (PreparedStatement ps= conn.prepareStatement(sql)){
            ps.setString(1, nome);
            try (ResultSet rs = ps.executeQuery()){

                if(rs.next()){
                    String nomeGruppo = rs.getString(1);
                    String materia = rs.getString(2);
                    String admin = rs.getString(3);
                    String citta = rs.getString(4);
                    String luogo = rs.getString(5);

                    InterfacciaDaoUtente dao = AbstractFactoryDao.getDao().getDaoUtente();
                    Utente user = dao.cercaUtente(admin);
                
                    gruppoCercato = new Gruppo(nomeGruppo, user);
                    gruppoCercato.setMateria(materia);
                    gruppoCercato.setCitta(citta);
                    gruppoCercato.setLuogo(luogo);
                }
            } 
        } catch (SQLException e) {
            logger.log(Level.SEVERE,"Errore ricerca Gruppo"+ nome,e);
            throw new ErroreDiSistema("Errore ricerca Gruppo", e);
        }
        return gruppoCercato;
    }
    @Override
    public List<Gruppo> recuperaGruppiUtente(String emailUtente) throws ErroreDiSistema {
        List<Gruppo> listaGruppi = new ArrayList<>();
        
 
        String sql =  RECUPERAGRUPPI;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, emailUtente);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String nome = rs.getString(1);
                    String materia = rs.getString(2);
                    String adminEmail = rs.getString(3);
                    
       
                    String citta = rs.getString(4);
                    String luogo = rs.getString(5);

                    Utente admin = FactoryUtenti.creazione("Studente", null, null, emailUtente, materia, null);
                    admin.setEmail(adminEmail);
                    
                    Gruppo g = new Gruppo(nome, admin);
                    g.setMateria(materia);
                    
                    g.setCitta(citta);
                    g.setLuogo(luogo);
                    
                    listaGruppi.add(g);
                }
            }
        }catch(SQLException e){
            logger.log(Level.SEVERE,"Errore recupero gruppi Utente"+ emailUtente,e);
            throw new ErroreDiSistema("Errore recupero gruppi Utente", e);

        }
        return listaGruppi;
    }
    @Override
    public void iscriviUtente(String nomeGruppo, String emailUtente) throws ErroreDiSistema {
        String sql = INSERTISCRIZIONE;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nomeGruppo);
            ps.setString(2, emailUtente);
            ps.executeUpdate();
        }catch(SQLException e){
            logger.log(Level.SEVERE,"Errore iscrizione Utente al Gruppo "+ nomeGruppo+ emailUtente,e);
            throw new ErroreDiSistema("errore iscrizione al gruppo", e);

        }
    }
    
    @Override
    public boolean esisteGruppo(String nomeGruppo) throws ErroreDiSistema{
        String sql = ESISTEGRUPPO;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nomeGruppo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }catch(SQLException e){

        }
        return false;
    }

    
   @Override
    public List<Gruppo> ricercaGruppiConFiltri(String nome, String citta, String materia) throws ErroreDiSistema {
        List<Gruppo> lista = new ArrayList<>();
        InterfacciaDaoUtente daoUtente = AbstractFactoryDao.getDao().getDaoUtente();
        
        String sql = SELECTRICERCAFILTRI; 
        
        if (nome != null) sql += "AND nome LIKE ? ";
        if (citta != null) sql += "AND citta LIKE ? ";
        if (materia != null) sql += "AND materia_studio LIKE ? ";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            int index = 1;
            if (nome != null) ps.setString(index++, "%" + nome + "%");
            if (citta != null) ps.setString(index++, "%" + citta + "%");
            if (materia != null) ps.setString(index++, "%" + materia + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Utente u= daoUtente.cercaUtente(rs.getString("email_admin"));
                    Gruppo g = new Gruppo(rs.getString("nome"), u);
                    g.setCitta(rs.getString("citta"));
                    g.setLuogo(rs.getString("luogo"));
                    g.setMateria(rs.getString("materia_studio"));
                    g.setAdmin(u);  
                    lista.add(g);
                }
            }
        }catch(SQLException e){
            logger.log(Level.SEVERE,"Errore ricerca gruppi con filtri"+ citta,e);
            throw new ErroreDiSistema("Errore ricerca Gruppi con filtri", e);

        }
        return lista;
    }

    @Override
    public void abbandonaGruppo(String nomeGruppo, String emailUtente) throws ErroreDiSistema {
        String sql = DELETEGRUPPO;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nomeGruppo);
            ps.setString(2, emailUtente);
            ps.executeUpdate();
        }catch(SQLException e){
            logger.log(Level.SEVERE,"Errore abbandono Gruppo "+ nomeGruppo+ emailUtente,e);
            throw new ErroreDiSistema("Errore abbandono gruppo", e);

        }
    }

    @Override
  public void eliminaGruppo(String nomeGruppo) throws ErroreDiSistema {

    String sqlEliminaMessaggi = ELIMINAMESSAGGI;

    String sqlEliminaIscrizioni = ELIMINAISCRIZIONI;

    String sqlEliminaGruppo = ELIMINAGRUPPO;

    try {
        conn.setAutoCommit(false); 
        eseguiUpdate (sqlEliminaMessaggi,nomeGruppo);
        eseguiUpdate(sqlEliminaIscrizioni,nomeGruppo);
        eseguiUpdate(sqlEliminaGruppo, nomeGruppo);
        conn.commit();
        

    } catch (SQLException e) {
        try {
              conn.rollback(); 
            
        } catch (SQLException ex) {
            logger.log(Level.SEVERE,"Impossibile eseguire il rollback durante l'eliminazione del gruupo", ex);
            throw new ErroreDiSistema("Errore di sistema", ex);
        }
        
    } finally {
        try{
            conn.setAutoCommit(true);
        }catch(SQLException ex){
            logger.log(Level.SEVERE,"Errore autocommit",ex);
            throw new ErroreDiSistema("Errore di sistema", ex);

        }
       
    }
}

private void eseguiUpdate(String sql, String parametro) throws ErroreDiSistema {
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, parametro);
        ps.executeUpdate();
    }catch(SQLException e){
        logger.log(Level.SEVERE,"errore update",e);
        throw new ErroreDiSistema("Errore aggiornamento", e);
    }
}

}