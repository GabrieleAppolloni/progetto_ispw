package appolloni.migliano.controller;



import appolloni.migliano.bean.BeanGruppo;
import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.entity.Gruppo;
import appolloni.migliano.factory.FactoryDAO;
import appolloni.migliano.interfacce.InterfacciaDaoGruppo;



import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ControllerGestioneGruppo {

    private InterfacciaDaoGruppo daoGruppo = FactoryDAO.getDaoGruppo();



    public List<BeanGruppo> visualizzaGruppi(BeanUtenti utenteLoggato) throws SQLException {
        List<BeanGruppo> listaBeans = new ArrayList<>();
           
            List<Gruppo> listaEntities = daoGruppo.recuperaGruppiUtente(utenteLoggato.getEmail());
            
            for (Gruppo gruppo : listaEntities) {
                BeanGruppo bean = new BeanGruppo(
                    gruppo.getNome(),
                    gruppo.getMateria(),
                    gruppo.getAdmin().getEmail(),
                    gruppo.getLuogo(),
                    gruppo.getCitta()
                );
                
                if (gruppo.getAdmin() != null) {
                    bean.setAdmin(gruppo.getAdmin().getEmail());
                }
                
                listaBeans.add(bean);
            }

        return listaBeans;
    }

    
     public void aggiungiGruppo(BeanUtenti beanUtenti, BeanGruppo gruppoScelto) throws SQLException{ 
         daoGruppo.iscriviUtente(gruppoScelto.getNome(), beanUtenti.getEmail());
        
    }
    
    
    public List<BeanGruppo> cercaGruppi(String nome, String citta, String materia) throws SQLException {
        if(nome != null && nome.isEmpty()) nome = null;
        if(citta != null && citta.isEmpty()) citta = null;
        if(materia != null && materia.isEmpty()) materia = null;

        
        List<BeanGruppo> list = new ArrayList<>();
        List<Gruppo> listGruppo = daoGruppo.ricercaGruppiConFiltri(nome, citta, materia);
        
        
        for(Gruppo g : listGruppo){
            BeanGruppo beanGruppo = new BeanGruppo(g.getNome(), g.getMateria(), g.getAdmin().getEmail(), g.getLuogo(),g.getCitta());
            list.add(beanGruppo);
        }
        return list;
    }



}
