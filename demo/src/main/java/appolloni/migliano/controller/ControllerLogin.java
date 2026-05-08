package appolloni.migliano.controller;

import java.sql.SQLException;

import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.entity.Utente;
import appolloni.migliano.exception.CampiVuotiException;
import appolloni.migliano.exception.CreazioneFallita;
import appolloni.migliano.exception.CredenzialiSbagliateException;
import appolloni.migliano.exception.EmailNonValidaException;
import appolloni.migliano.exception.ErroreDiSistema;
import appolloni.migliano.factory.AbstractFactoryDao;
import appolloni.migliano.interfacce.InterfacciaDaoUtente;

public class ControllerLogin {
    private InterfacciaDaoUtente daoUtente = AbstractFactoryDao.getDao().getDaoUtente();

    public BeanUtenti verificaUtente(BeanUtenti bean) throws SQLException,EmailNonValidaException,CredenzialiSbagliateException,CampiVuotiException,CreazioneFallita, ErroreDiSistema{


       if(bean.getEmail().isEmpty() || bean.getPassword().isEmpty()){
         throw new CampiVuotiException("Credenziali mancanti");

       }

        if(!bean.getEmail().contains("@")){
          throw new EmailNonValidaException("Formato email non valida");
        }
        Utente user = daoUtente.cercaUtente(bean.getEmail());

        if(user == null){
           throw new CreazioneFallita("Nessun utente registrato con questa email.");
        }

         if(!(user.getPass()).equals(bean.getPassword())){
            throw new CredenzialiSbagliateException("Password non corretta.");
         }

      return new BeanUtenti(user.getRuolo(), user.getName(), user.getCognome(), user.getEmail(), user.getPass(), user.getCitta());


   

        
   }

}
