package appolloni.migliano.controller;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import appolloni.migliano.bean.BeanGruppo;
import appolloni.migliano.bean.BeanMessaggi;
import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.entity.Gruppo;
import appolloni.migliano.entity.Messaggio;
import appolloni.migliano.entity.Utente;
import appolloni.migliano.factory.AbstractFactoryDao;
import appolloni.migliano.interfacce.InterfacciaDaoGruppo;
import appolloni.migliano.interfacce.InterfacciaDaoMessaggi;
import appolloni.migliano.interfacce.InterfacciaDaoUtente;

public class ControllerChat {

    private InterfacciaDaoGruppo daoGruppo;
    private InterfacciaDaoMessaggi daoMessaggi;
    private InterfacciaDaoUtente daoUtente;

    public ControllerChat() {
        this.daoGruppo = AbstractFactoryDao.getDao().getDaoGruppo();
        this.daoMessaggi = AbstractFactoryDao.getDao().getDaoMessaggi();
        this.daoUtente = AbstractFactoryDao.getDao().getDaoUtente();
    }
    public BeanGruppo recuperaInfoGruppo(BeanGruppo beanInput) throws  SQLException{
        Gruppo g = daoGruppo.cercaGruppo(beanInput.getNome());
        
        if (g == null){ throw new SQLException("Gruppo non esistente");}
         return new BeanGruppo(g.getNome(),g.getMateria(),g.getAdmin().getName(),g.getLuogo(),g.getMateria());
    }

    public List<BeanMessaggi> recuperaMessaggi(BeanGruppo beanGruppo) throws SQLException {
        List<BeanMessaggi> listaBeans = new ArrayList<>();

        Gruppo entityGruppo = daoGruppo.cercaGruppo(beanGruppo.getNome());
        
        if (entityGruppo != null) {
            List<Messaggio> listaEntity = daoMessaggi.cercaMessaggio(entityGruppo);
            
            for (Messaggio m : listaEntity) {
                BeanMessaggi b = new BeanMessaggi();
                b.setMess(m.getMess());
                String nomeMittente = (m.getMittente() != null) ? m.getMittente().getName() : "Sconosciuto";
                b.setMittente(nomeMittente); 
                
                listaBeans.add(b);
            }
        }
        return listaBeans;
     
    }

    public void inviaMessaggio(BeanUtenti mittente, BeanGruppo gruppo, String testo) throws SQLException, IllegalArgumentException{
        if( testo.trim().isEmpty()) {throw new IllegalArgumentException("Inserire il messaggio, impossibile inviare un messsaggio vuoto");}
        Utente user = daoUtente.cercaUtente(mittente.getEmail());
        Gruppo g = daoGruppo.cercaGruppo(gruppo.getNome());
        Messaggio messaggio = new Messaggio(testo, g, user);
        g.addMess(messaggio);
        daoMessaggi.nuovoMessaggio(messaggio);
    
    }

    public void abbandonaGruppo(BeanUtenti utente, BeanGruppo gruppo) throws SQLException {


        Utente user = daoUtente.cercaUtente(utente.getEmail());
        Gruppo gruppoUtente = daoGruppo.cercaGruppo(gruppo.getNome());
        if(utente.getEmail().equals(gruppo.getAdmin())){

            daoGruppo.eliminaGruppo(gruppoUtente.getNome());

        }else{
            daoGruppo.abbandonaGruppo(gruppoUtente.getNome(), user.getEmail());
        }
    
    }
}