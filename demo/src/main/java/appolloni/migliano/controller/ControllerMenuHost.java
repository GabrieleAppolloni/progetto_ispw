package appolloni.migliano.controller;


import appolloni.migliano.entity.Recensione;
import appolloni.migliano.entity.Struttura;
import appolloni.migliano.exception.CampiVuotiException;
import appolloni.migliano.exception.EntitaNonTrovata;
import appolloni.migliano.exception.ErroreDiSistema;
import appolloni.migliano.factory.AbstractFactoryDao;
import appolloni.migliano.interfacce.InterfacciaDaoRecensioni;
import appolloni.migliano.interfacce.InterfacciaDaoStruttura;
import java.util.ArrayList;
import java.util.List;

import appolloni.migliano.bean.BeanRecensioni;
import appolloni.migliano.bean.BeanStruttura;
import appolloni.migliano.bean.BeanUtenti;



public class ControllerMenuHost {

  
   private InterfacciaDaoStruttura daoStrutture = AbstractFactoryDao.getDao().getDaoStruttura();
   private InterfacciaDaoRecensioni daoRecensioni = AbstractFactoryDao.getDao().getDaoRecensioni();
  
   
   

   
    public BeanStruttura visualizzaStrutturaHost(String emailHost) throws IllegalArgumentException, ErroreDiSistema, CampiVuotiException {
        if(emailHost == null || emailHost.isBlank() ){
            throw new CampiVuotiException("Host non vaido");
        }
      
         Struttura struttura = daoStrutture.recuperaStrutturaPerHost(emailHost);
         if(struttura == null){ throw new IllegalArgumentException("Struttura non trovata");}
         BeanStruttura beanStruttura = new BeanStruttura(struttura.getTipo(), struttura.getName(), struttura.getCitta(), struttura.getIndirizzo(), struttura.hasWifi(), struttura.hasRistorazione());
         beanStruttura.setFoto(struttura.getFoto());
         beanStruttura.setOrario(struttura.getOrario());
         beanStruttura.setTipoAttivita(struttura.getTipoAttivita());
         beanStruttura.setGestore(struttura.getGestore());
         beanStruttura.setFoto(struttura.getFoto());
        return beanStruttura;

    }

    
    public void cambiaFoto(String emailHost, String nomeFoto) throws CampiVuotiException, ErroreDiSistema {
     if (emailHost == null || nomeFoto == null) {throw new CampiVuotiException("Dati mancanti");}
      daoStrutture.aggiornaFotoStruttura(emailHost, nomeFoto);
    
    }



  public List<BeanRecensioni> cercaRecensioniPerStruttura(BeanStruttura beanStruttura) throws ErroreDiSistema  {
    List<BeanRecensioni> listaBean = new ArrayList<>();
         List<Recensione> listaEntity = daoRecensioni.getRecensioniByStruttura(
            beanStruttura.getName(), 
            beanStruttura.getGestore()
         );
         for (Recensione r : listaEntity) {
            BeanRecensioni b = new BeanRecensioni(r.getAutore().getEmail(),r.getTesto(),r.getVoto(),r.getStrutturaRecensita().getName(),r.getStrutturaRecensita().getGestore());
            listaBean.add(b);
           }
        
     return listaBean;
    }

  public double calcolaMediaVoti(BeanStruttura beanStruttura, BeanUtenti beanUtenti) throws  ErroreDiSistema, EntitaNonTrovata{
    Struttura struttura = daoStrutture.cercaStruttura(beanStruttura.getName(), beanUtenti.getEmail());

    if(struttura == null){
        throw new EntitaNonTrovata("Struttura non trovata.");

    }
    List<Recensione> list = daoRecensioni.getRecensioniByStruttura(struttura.getName(), struttura.getGestore());
    struttura.setRecensioni(list);
    return struttura.calcolaMediaVoto();
    
  }

}
    


