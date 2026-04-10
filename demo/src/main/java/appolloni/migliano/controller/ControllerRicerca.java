package appolloni.migliano.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import appolloni.migliano.bean.BeanGruppo;
import appolloni.migliano.bean.BeanStruttura;
import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.entity.Gruppo;
import appolloni.migliano.entity.Struttura;
import appolloni.migliano.factory.FactoryDAO;
import appolloni.migliano.interfacce.InterfacciaDaoGruppo;
import appolloni.migliano.interfacce.InterfacciaDaoStruttura;

public class ControllerRicerca {

    private InterfacciaDaoGruppo daoGruppo;
    private InterfacciaDaoStruttura daoStruttura;

    public ControllerRicerca(){
        this.daoGruppo = FactoryDAO.getDaoGruppo();
        this.daoStruttura = FactoryDAO.getDAOStrutture();
    }

    public List<BeanGruppo> ricercaGruppi(BeanGruppo beanGruppo) throws SQLException{
        List<BeanGruppo> list = new java.util.ArrayList<>();
        if(beanGruppo.getNome() != null && beanGruppo.getNome().isEmpty()) beanGruppo.setNome(null);
        if(beanGruppo.getCitta() != null && beanGruppo.getCitta().isEmpty()) beanGruppo.setCitta(null);
        if(beanGruppo.getMateria() != null && beanGruppo.getMateria().isEmpty()) beanGruppo.setMateria(null);

        List<Gruppo> gruppi = daoGruppo.ricercaGruppiConFiltri(beanGruppo.getNome(), beanGruppo.getCitta(), beanGruppo.getMateria());

        for(Gruppo g : gruppi){
            BeanGruppo bean = new BeanGruppo(g.getNome(), g.getMateria(), g.getAdmin().getEmail(), g.getLuogo(), g.getCitta());
            list.add(bean);
        }

        // verifica se la lista è vuota e  gestisci caso
        return list;

    }

    public List<BeanStruttura> ricercaStruttura(BeanStruttura beanStruttura) throws SQLException, IOException{
        List<BeanStruttura> listStrutture = new java.util.ArrayList<>();

        if(beanStruttura.getName() != null && beanStruttura.getName().isEmpty()) beanStruttura.setName(null);
        if(beanStruttura.getCitta() != null && beanStruttura.getCitta().isEmpty()) beanStruttura.setCitta(null);
        if(beanStruttura.getTipoAttivita() != null && (beanStruttura.getTipoAttivita().equals("Tutti")|| beanStruttura.getTipoAttivita().isEmpty())) beanStruttura.setTipoAttivita(null);

        List<Struttura> list = daoStruttura.ricercaStruttureConFiltri(beanStruttura.getName(), beanStruttura.getCitta(), beanStruttura.getTipoAttivita());

        for( Struttura s: list){
            BeanStruttura bean = new BeanStruttura(s.getTipo(),  s.getName(),s.getCitta(), s.getIndirizzo(), s.hasWifi(), s.hasRistorazione());
            bean.setOrario(s.getOrario());
            bean.setFoto(s.getFoto());
            bean.setTipoAttivita(s.getTipoAttivita());
            bean.setGestore(s.getGestore());

            listStrutture.add(bean);

        }

        //verifica controllo lista vuota
        return listStrutture;
        
    }
    
    public void aggiungiGruppo(BeanUtenti beanUtente,BeanGruppo beanGruppo) throws SQLException{
        daoGruppo.iscriviUtente(beanGruppo.getNome(), beanUtente.getEmail());

    }

    
    
}
