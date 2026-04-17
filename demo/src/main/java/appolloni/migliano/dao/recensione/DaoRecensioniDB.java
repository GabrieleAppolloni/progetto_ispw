package appolloni.migliano.dao.recensione;

import appolloni.migliano.interfacce.InterfacciaDaoRecensioni;
import appolloni.migliano.interfacce.InterfacciaDaoStruttura;
import appolloni.migliano.interfacce.InterfacciaDaoUtente;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import appolloni.migliano.entity.Recensione;
import appolloni.migliano.entity.Utente;
import appolloni.migliano.factory.AbstractFactoryDao;
import appolloni.migliano.entity.Struttura;



public class DaoRecensioniDB implements InterfacciaDaoRecensioni {

    private final Connection conn;
    private final InterfacciaDaoUtente daoUtente;
    private final InterfacciaDaoStruttura daoStruttura;

    private static final String SELECTDB = "SELECT autore, nome_struttura, gestore_struttura, voto, testo  FROM recensioni WHERE nome_struttura = ? AND gestore_struttura = ?";
    private static final String INSERTRECENSIONE =  "INSERT INTO recensioni(autore, nome_struttura, gestore_struttura, voto, testo) VALUES (?, ?, ?, ?, ?)";

    public DaoRecensioniDB(Connection conn) {
        this.conn = conn;
        this.daoStruttura = AbstractFactoryDao.getDao().getDaoStruttura();
        this.daoUtente = AbstractFactoryDao.getDao().getDaoUtente();
    }

    @Override
    public void salvaRecensione(Recensione r) throws SQLException {

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
    }

    }

    public List<Recensione> getRecensioniByStruttura(String idStruttura, String nomeGestore) throws SQLException, IOException {
        List<Recensione> lista = new ArrayList<>();
        String sql = SELECTDB;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idStruttura);
            ps.setString(2,nomeGestore);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    
                     Struttura struttura = daoStruttura.cercaStruttura(rs.getString("nome_struttura"),rs.getString("gestore_struttura"));
                     Utente autore = daoUtente.cercaUtente(rs.getString("autore"));
                     Recensione r = new Recensione(rs.getString("testo"),rs.getInt("voto"),autore,struttura);
                     lista.add(r);
                    
                }
            }

        }
        return lista;
    }
}