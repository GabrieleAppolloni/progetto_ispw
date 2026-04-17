package appolloni.migliano.controller;


import appolloni.migliano.entity.Recensione;
import appolloni.migliano.entity.Struttura;
import appolloni.migliano.exception.CampiVuotiException;
import appolloni.migliano.factory.AbstractFactoryDao;
import appolloni.migliano.interfacce.InterfacciaDaoRecensioni;
import appolloni.migliano.interfacce.InterfacciaDaoStruttura;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import appolloni.migliano.bean.BeanRecensioni;
import appolloni.migliano.bean.BeanStruttura;
import appolloni.migliano.bean.BeanUtenti;



public class ControllerMenuHost {

  
   private InterfacciaDaoStruttura daoStrutture = AbstractFactoryDao.getDao().getDaoStruttura();
   private InterfacciaDaoRecensioni daoRecensioni = AbstractFactoryDao.getDao().getDaoRecensioni();
  
   
   

   
    public BeanStruttura visualizzaStrutturaHost(String emailHost) throws SQLException, IOException, IllegalArgumentException {
        if(emailHost == null ){
            throw new IllegalArgumentException("Host non vaido");

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

    // aggiorna struttura e  foto vanno messe in un altro caso d'uso
    public void cambiaFoto(String emailHost, String nomeFoto) throws CampiVuotiException, SQLException,IOException {
     if (emailHost == null || nomeFoto == null) {throw new CampiVuotiException("Dati mancanti");}
      daoStrutture.aggiornaFotoStruttura(emailHost, nomeFoto);
    
    }


    public void aggiornaStruttura(BeanStruttura struttura, String vecchionNome) throws IOException, SQLException, CampiVuotiException{
        if(struttura.getCitta().isEmpty() || struttura.getGestore().isEmpty() || struttura.getIndirizzo().isEmpty()|| struttura.getName().isEmpty()|| struttura.getOrario().isEmpty()||struttura.getTipoAttivita().isEmpty()){
            throw new CampiVuotiException("Dati mancanti");
        }
        Struttura struttura2 = new Struttura(struttura.getTipo(), struttura.getName(), struttura.getCitta(), struttura.getIndirizzo(), struttura.hasWifi(), struttura.hasRistorazione());
        struttura2.setTipoAttivita(struttura.getTipoAttivita());
        struttura2.setGestore(struttura.getGestore());
        struttura2.setOrario(struttura.getOrario());
        struttura2.setFoto(struttura.getFoto());
        daoStrutture.updateStruttura(struttura2, vecchionNome);
    
  }
  public List<BeanRecensioni> cercaRecensioniPerStruttura(BeanStruttura beanStruttura) throws SQLException, IOException {
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

  public double calcolaMediaVoti(BeanStruttura beanStruttura, BeanUtenti beanUtenti) throws SQLException,IOException{
    Struttura struttura = daoStrutture.cercaStruttura(beanStruttura.getName(), beanUtenti.getEmail());

    if(struttura == null){
        throw new IllegalArgumentException("Struttura non trovata.");

    }
    List<Recensione> list = daoRecensioni.getRecensioniByStruttura(struttura.getName(), struttura.getGestore());
    struttura.setRecensioni(list);
    return struttura.calcolaMediaVoto();
    
  }

}
    


