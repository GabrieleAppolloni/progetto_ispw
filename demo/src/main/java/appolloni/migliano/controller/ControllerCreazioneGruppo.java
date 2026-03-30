package appolloni.migliano.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import appolloni.migliano.bean.BeanGruppo;
import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.entity.Gruppo;
import appolloni.migliano.entity.Studente;
import appolloni.migliano.entity.Utente;
import appolloni.migliano.exception.CampiVuotiException;
import appolloni.migliano.factory.FactoryDAO;
import appolloni.migliano.interfacce.InterfacciaDaoStruttura;
import appolloni.migliano.interfacce.InterfacciaDaoGruppo;
import appolloni.migliano.interfacce.InterfacciaDaoUtente;


public class ControllerCreazioneGruppo {

        private InterfacciaDaoUtente daoUtente = FactoryDAO.getDaoUtente();
        private InterfacciaDaoGruppo daoGruppo = FactoryDAO.getDaoGruppo();
        private InterfacciaDaoStruttura daoStruttura = FactoryDAO.getDAOStrutture();

        public void creaGruppo(BeanUtenti bean, BeanGruppo beanGruppo) throws SQLException, CampiVuotiException {

        if(!bean.getTipo().equals("Studente")){
             throw new IllegalArgumentException("L'utente non ha i permessi");
        }
        
         if(beanGruppo.getLuogo().isEmpty()){beanGruppo.setLuogo("Sconosciuto");}
    
         if(beanGruppo.getAdmin().isEmpty() || beanGruppo.getCitta().isEmpty() || beanGruppo.getLuogo().isEmpty() || beanGruppo.getMateria().isEmpty()|| beanGruppo.getNome().isEmpty()){

            throw new CampiVuotiException("Dati mancanti, inserire tutti i campi");
         }
         Utente u1 = daoUtente.cercaUtente(bean.getEmail());

         if(u1 == null){

            throw new SQLException("Utente admin non trovato");
         }
 
         Gruppo gruppo = new Gruppo(beanGruppo.getNome(), u1);
         gruppo.setMateria(beanGruppo.getMateria());
         gruppo.setCitta(beanGruppo.getCitta());
         gruppo.setLuogo(beanGruppo.getLuogo());
         if(u1 instanceof Studente studente){
          studente.addGruppo(gruppo);
         }
         gruppo.aggiungiMembro(u1, u1);
         daoGruppo.creaGruppo(gruppo);
         
    }
    public List<String> getListaStruttureDisponibili(String citta) throws CampiVuotiException, SQLException, IOException{
        if(citta == null || citta.trim().isEmpty()) throw new CampiVuotiException(citta);
        return daoStruttura.recuperaNomiStrutture(citta);
    }
}
