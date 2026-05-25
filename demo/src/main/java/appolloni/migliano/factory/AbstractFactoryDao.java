package appolloni.migliano.factory;

import appolloni.migliano.Configurazione;
import appolloni.migliano.interfacce.InterfacciaDaoGruppo;
import appolloni.migliano.interfacce.InterfacciaDaoMessaggi;
import appolloni.migliano.interfacce.InterfacciaDaoRecensioni;
import appolloni.migliano.interfacce.InterfacciaDaoStruttura;
import appolloni.migliano.interfacce.InterfacciaDaoUtente;

public abstract class AbstractFactoryDao {

    private static final String Tipo = Configurazione.getTipoPersistenza();

    public static AbstractFactoryDao getDao(){

        if(Configurazione.JDBC.equals(Tipo)){
            return FactoryDaoDB.getInstance();

        }else if(Configurazione.FILE.equals(Tipo)){
            return FactoryDaoFile.getInstance();
        }else{
            return FactoryDaoDemo.getInstance();
        }
    }
 
  
    public abstract InterfacciaDaoUtente getDaoUtente();
    public abstract InterfacciaDaoGruppo getDaoGruppo();
    public abstract InterfacciaDaoMessaggi getDaoMessaggi();
    public abstract InterfacciaDaoStruttura getDaoStruttura();
    public abstract InterfacciaDaoRecensioni getDaoRecensioni();
    
}
    



