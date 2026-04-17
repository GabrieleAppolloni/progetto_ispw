package appolloni.migliano.factory;

import java.sql.Connection;
import java.sql.SQLException;

import appolloni.migliano.DBConnection;
import appolloni.migliano.dao.gruppo.DaoGruppoDB;
import appolloni.migliano.dao.messaggio.DaoMessaggioDB;
import appolloni.migliano.dao.recensione.DaoRecensioniFile;
import appolloni.migliano.dao.strutture.DAOStruttureFILE;
import appolloni.migliano.dao.utente.DaoUtenteDB;
import appolloni.migliano.interfacce.InterfacciaDaoGruppo;
import appolloni.migliano.interfacce.InterfacciaDaoMessaggi;
import appolloni.migliano.interfacce.InterfacciaDaoRecensioni;
import appolloni.migliano.interfacce.InterfacciaDaoStruttura;
import appolloni.migliano.interfacce.InterfacciaDaoUtente;

public class FactoryDaoFile extends AbstractFactoryDao {

     private static final String ERRMSG = "Impossibile connettersi al Database";

    private Connection getConn(){
        try{
            return DBConnection.getInstance().getConnection();
        }catch(SQLException r){
            throw new IllegalAccessError(ERRMSG);
        }

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
    public InterfacciaDaoRecensioni getDaoRecensioni(){
        return new DaoRecensioniFile();
    }

    @Override 
    public InterfacciaDaoStruttura getDaoStruttura(){
        return new DAOStruttureFILE();
    }

    @Override 
    public InterfacciaDaoUtente getDaoUtente(){
        return new DaoUtenteDB(getConn());
        
    }
    
}
