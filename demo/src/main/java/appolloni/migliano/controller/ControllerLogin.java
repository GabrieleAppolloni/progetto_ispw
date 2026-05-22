package appolloni.migliano.controller;
import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.entity.Utente;
import appolloni.migliano.exception.CampiVuotiException;
import appolloni.migliano.exception.EntitaNonTrovata;
import appolloni.migliano.exception.CredenzialiSbagliateException;
import appolloni.migliano.exception.EmailNonValidaException;
import appolloni.migliano.exception.ErroreDiSistema;
import appolloni.migliano.factory.AbstractFactoryDao;
import appolloni.migliano.interfacce.InterfacciaDaoUtente;

public class ControllerLogin {
    private InterfacciaDaoUtente daoUtente = AbstractFactoryDao.getDao().getDaoUtente();

    public BeanUtenti verificaUtente(BeanUtenti bean) throws EmailNonValidaException,CredenzialiSbagliateException,CampiVuotiException,EntitaNonTrovata, ErroreDiSistema{


       if(bean.getEmail().isBlank() || bean.getPassword().isBlank()){
         throw new CampiVuotiException("Credenziali mancanti");

       }

        if(!bean.getEmail().contains("@")){
          throw new EmailNonValidaException("Formato email non valida");
        }
        Utente user = daoUtente.cercaUtente(bean.getEmail());

         if(!(user.getPass()).equals(bean.getPassword()) || user == null){
            throw new CredenzialiSbagliateException("Credenziali non valide.");
         }

      return new BeanUtenti(user.getRuolo(), user.getName(), user.getCognome(), user.getEmail(), user.getPass(), user.getCitta());

        
   }

}
