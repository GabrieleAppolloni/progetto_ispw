package appolloni.migliano.controller;

import appolloni.migliano.factory.AbstractFactoryDao;
import appolloni.migliano.interfacce.InterfacciaDaoUtente;


import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.entity.Utente;
import appolloni.migliano.exception.EntitaNonTrovata;
import appolloni.migliano.exception.ErroreDiSistema;

public class ControllerProfiloUtente {
    private InterfacciaDaoUtente daoUtente = AbstractFactoryDao.getDao().getDaoUtente();

    public BeanUtenti recuperaInformazioniUtente(BeanUtenti utente) throws  ErroreDiSistema, EntitaNonTrovata{
        Utente utente2 = daoUtente.cercaUtente(utente.getEmail());
        if(utente2 == null){ throw new EntitaNonTrovata("Errore caricamento utente");}
        BeanUtenti beanUtente = new BeanUtenti(utente2.getRuolo(), utente2.getName(), utente2.getCognome(), utente2.getEmail(), utente2.getPass(), utente2.getCitta());
        return beanUtente;
    }

    public boolean modificaPassword(String vecchiaPass, String nuovaPass, BeanUtenti utente) throws ErroreDiSistema, EntitaNonTrovata{
        Utente u = daoUtente.cercaUtente(utente.getEmail());
        if(u == null){ throw new EntitaNonTrovata("Errore recupero utente"); }
        if(u.getPass().equals(vecchiaPass)){
            daoUtente.aggiornaPassword(u.getEmail(), nuovaPass);
            return true;

        }else{
            return false;
        }

    }
}
