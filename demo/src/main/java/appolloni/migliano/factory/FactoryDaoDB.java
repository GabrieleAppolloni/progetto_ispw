package appolloni.migliano.factory;

import java.sql.Connection;
import java.sql.SQLException;

import appolloni.migliano.Configurazione;
import appolloni.migliano.DBConnection;
import appolloni.migliano.dao.gruppo.DaoGruppoDB;
import appolloni.migliano.dao.messaggio.DaoMessaggioDB;
import appolloni.migliano.dao.recensione.DaoRecensioniDB;
import appolloni.migliano.dao.strutture.DAOStruttureDB;
import appolloni.migliano.dao.utente.DaoUtenteDB;
import appolloni.migliano.interfacce.InterfacciaDaoGruppo;
import appolloni.migliano.interfacce.InterfacciaDaoMessaggi;
import appolloni.migliano.interfacce.InterfacciaDaoRecensioni;
import appolloni.migliano.interfacce.InterfacciaDaoStruttura;
import appolloni.migliano.interfacce.InterfacciaDaoUtente;
import appolloni.migliano.interfacce.InterfacciaFactoryDao;
// fare sigleton 
public class FactoryDaoDB implements InterfacciaFactoryDao {

    private static String tipo = Configurazione.getTipoPersistenza();
    private static final String ERRMSG = "Impossibile connettersi al Database";

    private static Connection getConn(){
        try{
            return DBConnection.getInstance().getConnection();

        }catch(SQLException e){
             throw new IllegalAccessError(ERRMSG);
        }
    }

    @Override
    public InterfacciaDaoUtente getDaoUtente(){
        
            return new DaoUtenteDB(getConn());
    }

    @Override
    public InterfacciaDaoGruppo getDaoGruppo(){
        return new DaoGruppoDB(getConn());

    }

    @Override 
    public InterfacciaDaoMessaggi getDaoMessaggi(){
        return new DaoMessaggioDB(getConn());
    }

    @Override 
    public InterfacciaDaoStruttura getDaoStruttura(){
        return new DAOStruttureDB(getConn());

    }
    @Override
    public InterfacciaDaoRecensioni getDaoRecensioni(){
        return new DaoRecensioniDB(getConn());
    }
}
