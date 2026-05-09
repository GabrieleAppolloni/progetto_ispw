package appolloni.migliano.controller;

import java.util.List;

import appolloni.migliano.bean.BeanStruttura;
import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.entity.Struttura;
import appolloni.migliano.exception.CampiVuotiException;
import appolloni.migliano.exception.ErroreDiSistema;
import appolloni.migliano.factory.AbstractFactoryDao;
import appolloni.migliano.interfacce.InterfacciaDaoStruttura;

public class ControllerSegnalaStruttura {
    private InterfacciaDaoStruttura daoStruttura = AbstractFactoryDao.getDao().getDaoStruttura();
    private static final String GESTOREDEFAULT = "system_no_host";

    public void segnalaStruttura(BeanUtenti utente, BeanStruttura beanStruttura) throws IllegalArgumentException, CampiVuotiException, ErroreDiSistema{

        if(beanStruttura.getName().isEmpty() || beanStruttura.getCitta().isEmpty() || beanStruttura.getIndirizzo().isEmpty() || beanStruttura.getTipoAttivita().isEmpty() || beanStruttura.getTipo().isEmpty()){
            throw new CampiVuotiException("Dati mancati per la creazione della struttura");
        }

        List<Struttura> result = daoStruttura.ricercaStruttureConFiltri(beanStruttura.getName(), beanStruttura.getCitta(), null);
        for(Struttura s: result){
            if(s.getName().equals(beanStruttura.getName())){
                throw new IllegalArgumentException("Struttura già presente nel sistema");

            }

        }

        Struttura struttura = new Struttura(beanStruttura.getTipo(),beanStruttura.getName(),beanStruttura.getCitta(),beanStruttura.getIndirizzo(),beanStruttura.hasWifi(),beanStruttura.hasRistorazione());
        struttura.setOrario(beanStruttura.getOrario());
        struttura.setFoto(beanStruttura.getFoto());
        struttura.setGestore(GESTOREDEFAULT);
        struttura.setTipoAttivita(beanStruttura.getTipoAttivita());
        
        daoStruttura.salvaStruttura(struttura, GESTOREDEFAULT);


    
 }
}
