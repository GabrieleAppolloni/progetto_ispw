package appolloni.migliano.interfacce;

import java.sql.SQLException;
import java.util.List;

import appolloni.migliano.entity.Gruppo;

public interface InterfacciaGruppo {
    void creaGruppo(Gruppo gruppo) throws SQLException, Exception;
    Gruppo cercaGruppo(String nome) throws SQLException, Exception;
    List<Gruppo> recuperaGruppiUtente(String email) throws SQLException, Exception;
    void iscriviUtente(String nomeGruppo, String emailUtente) throws SQLException, Exception;
    boolean esisteGruppo(String nome) throws SQLException,Exception;
    List<Gruppo> ricercaGruppiConFiltri(String nome, String citta, String materia) throws SQLException, Exception;
    void abbandonaGruppo(String nomeGruppo, String emailUtente) throws SQLException, Exception;
    void eliminaGruppo(String nomeGruppo) throws SQLException, Exception;

}
