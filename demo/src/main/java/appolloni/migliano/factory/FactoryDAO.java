package appolloni.migliano.factory;

import appolloni.migliano.DAO.DaoDB.DAOStruttureDB;
import appolloni.migliano.DAO.DaoDB.DAOStruttureDemo;
import appolloni.migliano.DAO.DaoDB.DAOStruttureFILE;
import appolloni.migliano.DAO.DaoGruppo.DaoGruppoDB;
import appolloni.migliano.DAO.DaoGruppo.DaoGruppoDemo;
import appolloni.migliano.DAO.DaoMessaggi.DaoMessaggioDB;
import appolloni.migliano.DAO.DaoMessaggi.DaoMessaggioDemo;
import appolloni.migliano.DAO.DaoRecensioni.DaoRecensioniDB;
import appolloni.migliano.DAO.DaoRecensioni.DaoRecensioniDemo;
import appolloni.migliano.DAO.DaoRecensioni.DaoRecensioniFile;
import appolloni.migliano.DAO.DaoUtente.DaoUtenteDB;
import appolloni.migliano.DAO.DaoUtente.DaoUtenteDEMO;
import appolloni.migliano.interfacce.InterfacciaDaoRecensioni;
import appolloni.migliano.interfacce.InterfacciaDaoStruttura;
import appolloni.migliano.interfacce.InterfacciaGruppo;
import appolloni.migliano.interfacce.InterfacciaMessaggi;
import appolloni.migliano.interfacce.InterfacciaUtente;
import appolloni.migliano.DBConnection;
import appolloni.migliano.Configurazione;


public class FactoryDAO {
    private static final String tipo = Configurazione.getTipoPersistenza();

    public static InterfacciaDaoStruttura getDAOStrutture(){
        if(tipo.equals(Configurazione.JDBC)){
            return new DAOStruttureDB(DBConnection.getConnection());

        }else if(tipo.equals(Configurazione.DEMO)){
            return new DAOStruttureDemo();
        }else{
            return new DAOStruttureFILE();
        }

    }

    public static InterfacciaDaoRecensioni getDaoRecensioni() {
        if (tipo.equals(Configurazione.JDBC)) {
            return new DaoRecensioniDB(DBConnection.getConnection());
        }else if(tipo.equals(Configurazione.DEMO)){
            return new DaoRecensioniDemo();
        } else {
            return new DaoRecensioniFile();
        }
    }
    
    public static InterfacciaGruppo getDaoGruppo(){
        if(tipo.equals(Configurazione.JDBC)){
            return new DaoGruppoDB(DBConnection.getConnection());
        }else{
            return new DaoGruppoDemo();
        }
    }

    public static InterfacciaMessaggi getDaoMessaggi(){
        if(tipo.equals(Configurazione.JDBC)){
            return new DaoMessaggioDB(DBConnection.getConnection());

        }else{
            return new DaoMessaggioDemo();

        }
    }

    public static InterfacciaUtente getDaoUtente(){
        if(tipo.equals(Configurazione.JDBC)){
            return new DaoUtenteDB(DBConnection.getConnection());
        }else{
            return new DaoUtenteDEMO();
        }
    }
}

