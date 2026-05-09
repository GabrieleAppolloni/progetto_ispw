package appolloni.migliano.controller;

import appolloni.migliano.bean.BeanRecensioni;
import appolloni.migliano.bean.BeanStruttura;
import appolloni.migliano.entity.Recensione;
import appolloni.migliano.entity.Struttura;
import appolloni.migliano.entity.Utente;
import appolloni.migliano.exception.CampiVuotiException;
import appolloni.migliano.exception.ErroreDiSistema;
import appolloni.migliano.factory.AbstractFactoryDao;
import appolloni.migliano.interfacce.InterfacciaDaoRecensioni;
import appolloni.migliano.interfacce.InterfacciaDaoStruttura;
import appolloni.migliano.interfacce.InterfacciaDaoUtente;
import java.util.ArrayList;
import java.util.List;


public class ControllerRecensioni {

    private InterfacciaDaoRecensioni daoRecensioni = AbstractFactoryDao.getDao().getDaoRecensioni();
    private InterfacciaDaoUtente daoUtente = AbstractFactoryDao.getDao().getDaoUtente();
    private InterfacciaDaoStruttura daoStrutture = AbstractFactoryDao.getDao().getDaoStruttura();

    public void inserisciRecensione(BeanRecensioni beanRecensione) throws CampiVuotiException, ErroreDiSistema{

        Utente user = daoUtente.cercaUtente(beanRecensione.getAutore());
        Struttura struttura = daoStrutture.cercaStruttura(beanRecensione.getIdStruttura(),beanRecensione.getGestoreStruttura());
        String autore = user.getEmail();
        String testo = beanRecensione.getTesto();
        int voto = beanRecensione.getVoto();

        
        
        if (autore == null || autore.isBlank())
            throw new CampiVuotiException("Autore mancante");

        if (testo == null || testo.isBlank())
            throw new CampiVuotiException("Testo recensione mancante");

        if (voto < 1 || voto > 5)
            throw new IllegalArgumentException("Voto non valido");


        Recensione recensione = new Recensione(testo,voto,user, struttura);
        struttura.aggiungiRecensione(recensione);
        daoRecensioni.salvaRecensione(recensione);
      
    }

    public List<BeanRecensioni> cercaRecensioniPerStruttura(BeanStruttura beanStruttura) throws ErroreDiSistema {
    List<BeanRecensioni> listaBean = new ArrayList<>();
         List<Recensione> listaEntity = daoRecensioni.getRecensioniByStruttura(
            beanStruttura.getName(), 
            beanStruttura.getGestore()
         );
         for (Recensione r : listaEntity) {
            BeanRecensioni b = new BeanRecensioni(r.getAutore().getEmail(),r.getTesto(),r.getVoto(),r.getStrutturaRecensita().getName(),r.getStrutturaRecensita().getGestore());
            listaBean.add(b);
           }
        
     return listaBean;
    }

    
}