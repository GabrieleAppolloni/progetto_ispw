package appolloni.migliano.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import appolloni.migliano.bean.BeanGruppo;
import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.entity.Gruppo;
import appolloni.migliano.factory.AbstractFactoryDao;
import appolloni.migliano.interfacce.InterfacciaDaoGruppo;

public class ControllerMainMenu {

    private InterfacciaDaoGruppo daoGruppo;

    public ControllerMainMenu(){
        this.daoGruppo = AbstractFactoryDao.getDao().getDaoGruppo();
    }

    public List<BeanGruppo>  recuperaGruppiUtente(BeanUtenti beanUtente) throws SQLException, IOException{

        List<Gruppo> gruppi = daoGruppo.recuperaGruppiUtente(beanUtente.getEmail());
        List<BeanGruppo> listaBean = new java.util.ArrayList<>();

        for(Gruppo g : gruppi){
            BeanGruppo bean = new BeanGruppo(g.getNome(), g.getMateria(), g.getAdmin().getEmail(), g.getLuogo(), g.getCitta());
            listaBean.add(bean);

        }

        return listaBean;


    }
    
}
