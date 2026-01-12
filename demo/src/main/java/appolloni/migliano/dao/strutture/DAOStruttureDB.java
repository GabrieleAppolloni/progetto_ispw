package appolloni.migliano.dao.strutture;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import appolloni.migliano.entity.Struttura;
import appolloni.migliano.interfacce.InterfacciaDaoStruttura;
import appolloni.migliano.factory.FactoryStrutture;

public class DAOStruttureDB implements InterfacciaDaoStruttura {

    private Connection conn;
    private static final String COLONNE_SELECT = "tipo, nome, citta, indirizzo, orario_apertura, wifi, ristorazione, tipo_attivita, gestore, foto";

    public DAOStruttureDB(Connection conn){
        this.conn = conn;
    }
 
    @Override
        public void salvaStruttura(Struttura struttura, String email) throws SQLException{
        String sql = "INSERT INTO strutture(nome,tipo,citta,indirizzo,orario_apertura,wifi,ristorazione,tipo_attivita,gestore,foto) VALUES (?,?,?,?,?,?,?,?,?,?)";
        final String GESTORE_DEFAULT = "system_no_host";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, struttura.getName());
            ps.setString(2, struttura.getTipo());
            ps.setString(3, struttura.getCitta());
            ps.setString(4, struttura.getIndirizzo());
            ps.setString(5, struttura.getOrario());
            ps.setBoolean(6, struttura.hasWifi());
            ps.setBoolean(7, struttura.hasRistorazione());
            ps.setString(8, struttura.getTipoAttivita());

            if(email != null && !email.isEmpty()){
              ps.setString(9, email);
            }else{
                ps.setString(9,GESTORE_DEFAULT);
            }
            ps.setString(10, struttura.getFoto());

              ps.executeUpdate();
             if (!conn.getAutoCommit()) {
                conn.commit();
            }
        }
    }

    @Override
    public Struttura cercaStruttura(String nome, String gestore) throws SQLException{
        String sql = "SELECT " + COLONNE_SELECT + "FROM strutture where nome = ? AND gestore = ?";
        Struttura struttura = null;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nome);
            ps.setString(2, gestore);

            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()){

                    struttura = FactoryStrutture.creazioneStrutture(
                        rs.getString(1), 
                        rs.getString(2), 
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getBoolean(6),
                        rs.getBoolean(7),
                        rs.getString(8),
                        rs.getString(9)
                    );

                    String foto = rs.getString(10);
                    if (foto == null || foto.isEmpty()) foto = "placeholder.png";
                    struttura.setFoto(foto);
                }
            }
        }
        return struttura;
    }

    @Override
    public List<Struttura> ricercaStruttureConFiltri(String nome, String citta, String tipo) throws SQLException {
        List<Struttura> lista = new ArrayList<>();
        String sql = "SELECT " + COLONNE_SELECT + " FROM strutture WHERE 1=1 ";

        if (nome != null) sql += "AND nome LIKE ? ";
        if (citta != null) sql += "AND citta LIKE ? ";
        if (tipo != null) sql += "AND tipo_attivita = ? "; 

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            int index = 1;
            if (nome != null) ps.setString(index++, "%" + nome + "%");
            if (citta != null) ps.setString(index++, "%" + citta + "%");
            if (tipo != null) ps.setString(index++, tipo);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String foto = rs.getString(10);
                    if (foto == null || foto.isEmpty()) foto = "placeholder.png";
                     Struttura s = FactoryStrutture.creazioneStrutture(
                        rs.getString(1), rs.getString(2), rs.getString(3), 
                        rs.getString(4), rs.getString(5), 
                        rs.getBoolean(6), rs.getBoolean(7),rs.getString(8), rs.getString(9));
                        s.setFoto(foto);
                    lista.add(s);
                }
            }
        }
        return lista;
    }


    @Override

    public Struttura recuperaStrutturaPerHost(String emailHost) throws SQLException {
        Struttura struttura = null;
        String sql = "SELECT " + COLONNE_SELECT + " FROM strutture WHERE gestore = ?"; 

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, emailHost);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                
                    struttura = FactoryStrutture.creazioneStrutture(
                        rs.getString(1),          
                        rs.getString(2),        
                        rs.getString(3),         
                        rs.getString(4),     
                        rs.getString(5),
                        rs.getBoolean(6),         
                        rs.getBoolean(7), 
                        rs.getString(8),
                        rs.getString(9)        
                    );

                    String foto = rs.getString(10); 
                    if(foto == null || foto.isEmpty()) {
                    foto = "placeholder.png";
                     }
                    struttura.setFoto(foto); 
                }
            }
        } 
        return struttura;
    }
    
    @Override
     public void updateStruttura(Struttura s, String vecchioNome) throws SQLException {
        
        String sql = "UPDATE strutture SET " +
                     "nome = ?, " +
                     "indirizzo = ?, " +
                     "citta = ?, " +
                     "orario_apertura = ?, " + 
                     "wifi = ?, " +
                     "ristorazione = ?, " +
                     "tipo_attivita = ?, " +
                     "gestore = ?, " +
                     "foto = ? " +
                     "WHERE nome = ?"; 

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getName());
            ps.setString(2, s.getIndirizzo());
            ps.setString(3, s.getCitta());
            ps.setString(4, s.getOrario());
            ps.setBoolean(5, s.hasWifi());
            ps.setBoolean(6, s.hasRistorazione());
            ps.setString(7, s.getTipoAttivita());
            ps.setString(8, s.getGestore());
            ps.setString(9, s.getFoto());
            
            ps.setString(10, vecchioNome);

            int righeAggiornate = ps.executeUpdate();
            
            if (righeAggiornate == 0) {
                throw new SQLException("Aggiornamento fallito, nessuna struttura trovata con nome: " + vecchioNome);
            }
        }
    }

    @Override
    public void aggiornaFotoStruttura(String emailHost, String nomeNuovaFoto) throws SQLException {
     String sql = "UPDATE strutture SET foto = ? WHERE gestore = ?";
     try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, nomeNuovaFoto);
        ps.setString(2, emailHost);
        ps.executeUpdate();
     }
    }

    @Override
    public List<String> recuperaNomiStrutture(String citta) throws SQLException {
     List<String> nomi = new ArrayList<>();
     String sql = "SELECT nome FROM strutture WHERE citta = ?";
  
     try (PreparedStatement ps = conn.prepareStatement(sql)){
         ps.setString(1, citta);

         try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
        
                   nomi.add(rs.getString("nome"));
                }
             
        }
     }
    return nomi;
 }


    
}
