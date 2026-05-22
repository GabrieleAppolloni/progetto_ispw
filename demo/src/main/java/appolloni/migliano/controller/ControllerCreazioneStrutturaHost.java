package appolloni.migliano.controller;


import appolloni.migliano.bean.BeanStruttura;
import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.entity.Host;
import appolloni.migliano.entity.Struttura;
import appolloni.migliano.entity.Utente;
import appolloni.migliano.exception.CampiVuotiException;
import appolloni.migliano.exception.EmailNonValidaException;
import appolloni.migliano.exception.ErroreDiSistema;
import appolloni.migliano.factory.AbstractFactoryDao;
import appolloni.migliano.factory.FactoryUtenti;
import appolloni.migliano.interfacce.InterfacciaDaoStruttura;
import appolloni.migliano.interfacce.InterfacciaDaoUtente;

public class ControllerCreazioneStrutturaHost {
    private InterfacciaDaoStruttura daoStruttura = AbstractFactoryDao.getDao().getDaoStruttura();
    private InterfacciaDaoUtente daoUtente = AbstractFactoryDao.getDao().getDaoUtente();
    private static final String GESTOREDEFAULT = "system_no_host";

     private boolean esistenzaStruttura(String nomeStruttura) throws  ErroreDiSistema {
     return daoStruttura.cercaStruttura(nomeStruttura, GESTOREDEFAULT) != null;
    }

    private void rivendicaStruttura(BeanStruttura beanDatiNuovi, String emailHost) throws  ErroreDiSistema {
    
     Struttura strutturaAggiornata = new Struttura(
        beanDatiNuovi.getTipo(), 
        beanDatiNuovi.getName(), 
        beanDatiNuovi.getCitta(), 
        beanDatiNuovi.getIndirizzo(), 
        beanDatiNuovi.hasWifi(), 
        beanDatiNuovi.hasRistorazione()
       );
    
     strutturaAggiornata.setGestore(emailHost); 
     strutturaAggiornata.setOrario(beanDatiNuovi.getOrario());
     strutturaAggiornata.setTipoAttivita(beanDatiNuovi.getTipoAttivita());
     strutturaAggiornata.setFoto(beanDatiNuovi.getFoto());

  
     daoStruttura.aggiornaHost(strutturaAggiornata, GESTOREDEFAULT); 
    }


    public void creazioneStrutturaHost(BeanStruttura struttura, BeanUtenti user) throws CampiVuotiException, ErroreDiSistema, EmailNonValidaException{
        validazioneHost(user);
        Utente utente = FactoryUtenti.creazione(user.getTipo(), user.getName(), user.getCognome(), user.getEmail(), user.getCitta(),user.getPassword());

        if(!(utente instanceof Host host)){
            throw new IllegalArgumentException("Tipo utente non abilitato a questa funzione");
        }

        validazioneDatiStruttura(struttura);

        host.setTipoAttivita(user.getTipoAttivita());
        host.setNomeAttivita(user.getNomeAttivita());

        boolean check = esistenzaStruttura(user.getNomeAttivita());

        if(check){
            daoUtente.salvaUtente(host);
            rivendicaStruttura(struttura, host.getEmail());
        }else{
            if(daoStruttura.cercaStruttura(struttura.getName(), host.getEmail()) != null){
                throw new IllegalArgumentException("struttura giò esistente ");

            }
            Struttura struttura2 = new Struttura(struttura.getTipo(), struttura.getName(), struttura.getCitta(), struttura.getIndirizzo(), struttura.hasWifi(), struttura.hasRistorazione());
            struttura2.setGestore(struttura.getGestore());
            struttura2.setFoto(struttura.getFoto());
            struttura2.setTipoAttivita(struttura.getTipoAttivita());
            struttura2.setOrario(struttura.getOrario());

            daoUtente.salvaUtente(host);
            daoStruttura.salvaStruttura(struttura2, host.getEmail());
        }
        

        

    }


    private void validazioneHost(BeanUtenti beanUtenti) throws CampiVuotiException, EmailNonValidaException{
        if(beanUtenti.getName().isBlank() || beanUtenti.getTipo().isBlank() || beanUtenti.getCognome().isBlank() || beanUtenti.getEmail().isBlank() || beanUtenti.getCitta().isBlank() ||beanUtenti.getPassword().isBlank() ){
            throw new CampiVuotiException("Dati mancancati ");

        }
        if (!(beanUtenti.getEmail().contains("@"))) {
            throw new EmailNonValidaException("Formato email non valido");
            
        }

    }

    private void validazioneDatiStruttura(BeanStruttura beanStruttura) throws CampiVuotiException{
        if(beanStruttura.getCitta().isBlank() || beanStruttura.getGestore().isBlank() || beanStruttura.getIndirizzo().isBlank() || beanStruttura.getTipoAttivita().isBlank() || beanStruttura.getName().isBlank() || beanStruttura.getTipo().isBlank() || beanStruttura.getOrario().isBlank()){
            throw new CampiVuotiException("Dati mancanti");
        }
    }
  
}

