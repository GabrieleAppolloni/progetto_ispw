package appolloni.migliano.factory;

import appolloni.migliano.dao.gruppo.DaoGruppoDemo;
import appolloni.migliano.dao.messaggio.DaoMessaggioDemo;
import appolloni.migliano.dao.recensione.DaoRecensioniDemo;
import appolloni.migliano.dao.strutture.DAOStruttureDemo;
import appolloni.migliano.dao.utente.DaoUtenteDEMO;
import appolloni.migliano.interfacce.InterfacciaDaoGruppo;
import appolloni.migliano.interfacce.InterfacciaDaoMessaggi;
import appolloni.migliano.interfacce.InterfacciaDaoRecensioni;
import appolloni.migliano.interfacce.InterfacciaDaoStruttura;
import appolloni.migliano.interfacce.InterfacciaDaoUtente;

public class FactoryDaoDemo extends AbstractFactoryDao {

    @Override
    public InterfacciaDaoUtente getDaoUtente(){
        return new DaoUtenteDEMO();
    }

    @Override
    public InterfacciaDaoGruppo getDaoGruppo(){
        return new DaoGruppoDemo();
    }
    
    @Override 
    public InterfacciaDaoMessaggi getDaoMessaggi(){
        return new DaoMessaggioDemo();
    }

    @Override 
    public InterfacciaDaoRecensioni getDaoRecensioni(){
        return new DaoRecensioniDemo();
    }

    @Override 
    public InterfacciaDaoStruttura getDaoStruttura(){
        return new DAOStruttureDemo();
    }
}
