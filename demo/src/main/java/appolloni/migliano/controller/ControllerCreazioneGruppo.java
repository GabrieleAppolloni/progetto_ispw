package appolloni.migliano.controller;
import java.util.List;

import appolloni.migliano.bean.BeanGruppo;
import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.entity.Gruppo;
import appolloni.migliano.entity.Studente;
import appolloni.migliano.entity.Utente;
import appolloni.migliano.exception.CampiVuotiException;
import appolloni.migliano.exception.EntitaNonTrovata;
import appolloni.migliano.exception.ErroreDiSistema;
import appolloni.migliano.factory.AbstractFactoryDao;
import appolloni.migliano.interfacce.InterfacciaDaoStruttura;
import appolloni.migliano.interfacce.InterfacciaDaoGruppo;
import appolloni.migliano.interfacce.InterfacciaDaoUtente;


public class ControllerCreazioneGruppo {

        private InterfacciaDaoUtente daoUtente = AbstractFactoryDao.getDao().getDaoUtente();
        private InterfacciaDaoGruppo daoGruppo = AbstractFactoryDao.getDao().getDaoGruppo();
        private InterfacciaDaoStruttura daoStruttura = AbstractFactoryDao.getDao().getDaoStruttura();

        private Studente casting(Utente u) {
             if (u instanceof Studente studente) {
               return studente;
             } else {
                throw new IllegalArgumentException("Errore recupero dati");
             }
         }

        public void creaGruppo(BeanUtenti bean, BeanGruppo beanGruppo) throws CampiVuotiException, ErroreDiSistema, EntitaNonTrovata {

        if(!bean.getTipo().equals("Studente")){
             throw new IllegalArgumentException("L'utente non ha i permessi");
        }
        
         if(beanGruppo.getLuogo().isBlank()){beanGruppo.setLuogo("Sconosciuto");}
    
         if(beanGruppo.getAdmin().isBlank() || beanGruppo.getCitta().isBlank() || beanGruppo.getLuogo().isBlank() || beanGruppo.getMateria().isBlank()|| beanGruppo.getNome().isBlank()){

            throw new CampiVuotiException("Dati mancanti, inserire tutti i campi");
         }
         Utente u1 = daoUtente.cercaUtente(bean.getEmail());

         if(u1 == null){

            throw new EntitaNonTrovata("Utente admin non trovato");
         }
         
         Studente s = casting(u1);
         Gruppo gruppo = s.creaGruppo(beanGruppo.getNome());
         gruppo.setMateria(beanGruppo.getMateria());
         gruppo.setCitta(beanGruppo.getCitta());
         gruppo.setLuogo(beanGruppo.getLuogo());
         
         gruppo.aggiungiMembro(s, s);
         daoGruppo.creaGruppo(gruppo);
         
    }
    public List<String> getListaStruttureDisponibili(String citta) throws CampiVuotiException, ErroreDiSistema{
        if(citta == null || citta.trim().isEmpty()) throw new CampiVuotiException(citta);
        return daoStruttura.recuperaNomiStrutture(citta);
    }
}
