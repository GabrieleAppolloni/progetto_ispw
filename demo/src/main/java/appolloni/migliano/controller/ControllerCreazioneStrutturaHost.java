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

    public void creazioneHostStruttura(BeanUtenti beanUtente, BeanStruttura beanStruttura) throws CampiVuotiException, EmailNonValidaException, ErroreDiSistema{

        String nome = beanUtente.getName();
        String tipo = beanUtente.getTipo();
        String cognome = beanUtente.getCognome();
        String email = beanUtente.getEmail();
        String citta = beanUtente.getCitta();
        String pass = beanUtente.getPassword();
        
        if(nome.isBlank() || tipo.isBlank() || cognome.isBlank() || email.isBlank() || citta.isBlank() ||pass.isBlank() ){
            throw new CampiVuotiException("Informazioni Utente mancanti.");
        }
        if(!email.contains("@")){
            throw new EmailNonValidaException("Formato email non valido.");
        }
        Utente utente = FactoryUtenti.creazione(tipo, nome, cognome, email, citta, pass);

        if(utente instanceof Host host){

            String nomeAttivita = beanUtente.getNomeAttivita();
            String tipoAttivita = beanUtente.getTipoAttivita();



            if(nomeAttivita.isBlank() || tipoAttivita.isBlank()){
                throw new CampiVuotiException("Dati attività mancanti. ");
            }

            host.setTipoAttivita(tipoAttivita);
            host.setNomeAttivita(nomeAttivita);

            String type = beanStruttura.getTipo();
            String nomeStruttura = beanStruttura.getName();
            String cittaStr = beanStruttura.getCitta();
            String indirizzo = beanStruttura.getIndirizzo();
            String orario = beanStruttura.getOrario();
            boolean wifi = beanStruttura.hasWifi();
            boolean ristorazione = beanStruttura.hasRistorazione();
            String responsabile = beanStruttura.getGestore();
            String tipoAtt = beanStruttura.getTipoAttivita();
            String foto = beanStruttura.getFoto();
      
             if(type.isBlank() || nomeStruttura.isBlank() || cittaStr.isBlank()|| indirizzo.isBlank() || orario.isBlank() ||responsabile.isBlank()|| tipoAtt.isBlank()){
              throw new CampiVuotiException("Completa tutti i campi.");
            }

            
            boolean check = esistenzaStruttura(nomeAttivita);
            if(check){
              daoUtente.salvaUtente(utente);
              rivendicaStruttura(beanStruttura, email);
            }else{
             Struttura struttura = new Struttura(type, nomeStruttura, cittaStr, indirizzo, wifi, ristorazione);
             struttura.setGestore(responsabile);
             struttura.setFoto(foto);
             struttura.setTipoAttivita(tipoAtt);
             struttura.setOrario(orario);

             Struttura struttura2 = daoStruttura.cercaStruttura(nomeStruttura, utente.getEmail());

             if(struttura2 != null){
                throw new IllegalArgumentException("Struttura già esistente per questo Host.");
             }

             daoUtente.salvaUtente(utente);
             daoStruttura.salvaStruttura(struttura, email);
            }
        }else{
            throw new IllegalArgumentException("tipo Utente non valido.");
        }
        

    }
  
}

