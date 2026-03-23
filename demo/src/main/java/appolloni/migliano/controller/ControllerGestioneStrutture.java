package appolloni.migliano.controller;


import appolloni.migliano.entity.Struttura;
import appolloni.migliano.exception.CampiVuotiException;

import appolloni.migliano.factory.FactoryDAO;
import appolloni.migliano.interfacce.InterfacciaDaoStruttura;
import java.io.IOException;
import java.sql.SQLException;

import appolloni.migliano.bean.BeanStruttura;



public class ControllerGestioneStrutture {

  
   private InterfacciaDaoStruttura daoStrutture = FactoryDAO.getDAOStrutture();
  
   
   

   
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


}
    


