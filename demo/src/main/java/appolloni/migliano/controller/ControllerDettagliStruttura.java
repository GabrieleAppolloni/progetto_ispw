package appolloni.migliano.controller;

import appolloni.migliano.factory.AbstractFactoryDao;
import appolloni.migliano.interfacce.InterfacciaDaoRecensioni;
import appolloni.migliano.entity.Recensione;
import appolloni.migliano.exception.ErroreDiSistema;

import java.util.List;
import appolloni.migliano.bean.BeanRecensioni;
import appolloni.migliano.bean.BeanStruttura;

public class ControllerDettagliStruttura {

    private InterfacciaDaoRecensioni daoRecensioni;

    public ControllerDettagliStruttura(){
        this.daoRecensioni = AbstractFactoryDao.getDao().getDaoRecensioni();
    }

    public List<BeanRecensioni> recuperaRecensioniStruttura(BeanStruttura beanStruttura) throws ErroreDiSistema{

        List<Recensione> recensioni = daoRecensioni.getRecensioniByStruttura(beanStruttura.getName(), beanStruttura.getGestore());
        List <BeanRecensioni> lista = new java.util.ArrayList<>();

        for(Recensione r : recensioni){
            BeanRecensioni bean = new BeanRecensioni(r.getAutore().getEmail(), r.getTesto(), r.getVoto(), r.getStrutturaRecensita().getName(),r.getStrutturaRecensita().getGestore());
            lista.add(bean);

        }
        return lista;
    }
    
}
