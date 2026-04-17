package appolloni.migliano.controller;

import appolloni.migliano.factory.AbstractFactoryDao;
import appolloni.migliano.interfacce.InterfacciaDaoUtente;

import java.sql.SQLException;

import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.entity.Utente;

public class ControllerProfiloUtente {
    private InterfacciaDaoUtente daoUtente = AbstractFactoryDao.getDao().getDaoUtente();

    public BeanUtenti recuperaInformazioniUtente(BeanUtenti utente) throws SQLException{
        Utente utente2 = daoUtente.cercaUtente(utente.getEmail());
        BeanUtenti beanUtente = new BeanUtenti(utente2.getRuolo(), utente2.getName(), utente2.getCognome(), utente2.getEmail(), utente2.getPass(), utente2.getCitta());
        return beanUtente;
    }

    public boolean modificaPassword(String vecchiaPass, String nuovaPass, BeanUtenti utente) throws SQLException{
        Utente u = daoUtente.cercaUtente(utente.getEmail());
        if(u.getPass().equals(vecchiaPass)){
            daoUtente.aggiornaPassword(u.getEmail(), nuovaPass);
            return true;

        }else{
            return false;
        }

    }
}
