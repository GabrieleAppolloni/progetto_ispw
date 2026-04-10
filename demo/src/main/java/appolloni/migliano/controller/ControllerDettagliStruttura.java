package appolloni.migliano.controller;

import appolloni.migliano.factory.FactoryDAO;
import appolloni.migliano.interfacce.InterfacciaDaoRecensioni;
import appolloni.migliano.entity.Recensione;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import appolloni.migliano.bean.BeanRecensioni;
import appolloni.migliano.bean.BeanStruttura;

public class ControllerDettagliStruttura {

    private InterfacciaDaoRecensioni daoRecensioni;

    public ControllerDettagliStruttura(){
        this.daoRecensioni = FactoryDAO.getDaoRecensioni();
    }

    public List<BeanRecensioni> recuperaRecensioniStruttura(BeanStruttura beanStruttura) throws SQLException, IOException{

        List<Recensione> recensioni = daoRecensioni.getRecensioniByStruttura(beanStruttura.getName(), beanStruttura.getGestore());
        List <BeanRecensioni> lista = new java.util.ArrayList<>();

        for(Recensione r : recensioni){
            BeanRecensioni bean = new BeanRecensioni(r.getAutore().getEmail(), r.getTesto(), r.getVoto(), r.getStrutturaRecensita().getName(),r.getStrutturaRecensita().getGestore());
            lista.add(bean);

        }
        return lista;
    }
    
}
