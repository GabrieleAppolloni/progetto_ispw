package appolloni.migliano.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import appolloni.migliano.bean.BeanStruttura;
import appolloni.migliano.entity.Struttura;
import appolloni.migliano.factory.FactoryDAO;
import appolloni.migliano.interfacce.InterfacciaDaoStruttura;


public class ControllerRicercaStrutture {
    private final InterfacciaDaoStruttura daoStruttura = FactoryDAO.getDAOStrutture();
      public List<BeanStruttura> cercaStrutture(String nome, String citta, String tipo) throws IOException, SQLException {
    
        if(nome != null && nome.isEmpty()) {nome = null;}
        if(citta != null && citta.isEmpty()) {citta = null;}
        if(tipo != null && (tipo.isEmpty() || tipo.equals("Tutti"))) {tipo = null;}

        List<BeanStruttura> listaBeans = new ArrayList<>();
        List<Struttura> listaEntities = daoStruttura.ricercaStruttureConFiltri(nome, citta, tipo);
        
        for (Struttura s : listaEntities) {
            BeanStruttura b = new BeanStruttura(s.getTipo(),s.getName(),s.getCitta(),s.getIndirizzo(),s.hasWifi(),s.hasRistorazione());
            b.setFoto(s.getFoto()); 
            b.setOrario(s.getOrario());
            b.setTipoAttivita(s.getTipoAttivita());
            b.setGestore(s.getGestore());
            
            listaBeans.add(b);
        }

        return listaBeans;
  }


}
