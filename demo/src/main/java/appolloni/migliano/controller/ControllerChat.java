package appolloni.migliano.controller;

import java.util.ArrayList;
import java.util.List;

import appolloni.migliano.bean.BeanGruppo;
import appolloni.migliano.bean.BeanMessaggi;
import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.entity.Gruppo;
import appolloni.migliano.entity.Messaggio;
import appolloni.migliano.entity.Studente;
import appolloni.migliano.entity.Utente;
import appolloni.migliano.exception.CampiVuotiException;
import appolloni.migliano.exception.EntitaNonTrovata;
import appolloni.migliano.exception.ErroreDiSistema;
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

    private Studente casting(Utente u) {
     if (u instanceof Studente studente) {
        return studente;
     } else {
        throw new IllegalArgumentException("Errore recupero dati");
     }
    }
    public BeanGruppo recuperaInfoGruppo(BeanGruppo beanInput) throws  ErroreDiSistema, EntitaNonTrovata{
        Gruppo g = daoGruppo.cercaGruppo(beanInput.getNome());
        
        if (g == null){ throw new EntitaNonTrovata ("Gruppo non esistente" );}
         return new BeanGruppo(g.getNome(),g.getMateria(),g.getAdmin().getName(),g.getLuogo(),g.getCitta());
    }

    public List<BeanMessaggi> recuperaMessaggi(BeanGruppo beanGruppo) throws ErroreDiSistema, EntitaNonTrovata {
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
        }else{
            throw new EntitaNonTrovata("Gruppo non trovato");
        }
        return listaBeans;
     
    }

    public void inviaMessaggio(BeanUtenti mittente, BeanGruppo gruppo, String testo) throws ErroreDiSistema, CampiVuotiException, EntitaNonTrovata{
        if( testo.trim().isEmpty()) {throw new CampiVuotiException("Inserire il messaggio, impossibile inviare un messsaggio vuoto");}
        Studente user = casting(daoUtente.cercaUtente(mittente.getEmail()));
        Gruppo g = daoGruppo.cercaGruppo(gruppo.getNome());
        if(user == null || g == null){ throw new EntitaNonTrovata("Errore recupero dati.");}
        Messaggio messaggio = new Messaggio(testo, g, user);
        g.addMess(messaggio);
        daoMessaggi.nuovoMessaggio(messaggio);
    
    }

    public void abbandonaGruppo(BeanUtenti utente, BeanGruppo gruppo) throws  ErroreDiSistema, EntitaNonTrovata{


        Utente user = daoUtente.cercaUtente(utente.getEmail());
        Gruppo gruppoUtente = daoGruppo.cercaGruppo(gruppo.getNome());
        if(user == null || gruppoUtente == null){ throw new EntitaNonTrovata("Impossobile uscire dal gruppo, riprovare.");}
        if(utente.getEmail().equals(gruppo.getAdmin())){

            daoGruppo.eliminaGruppo(gruppoUtente.getNome());

        }else{
            daoGruppo.abbandonaGruppo(gruppoUtente.getNome(), user.getEmail());
        }
    
    }
}