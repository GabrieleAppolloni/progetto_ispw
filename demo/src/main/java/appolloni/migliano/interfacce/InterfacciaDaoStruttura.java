package appolloni.migliano.interfacce;

import appolloni.migliano.entity.Struttura;
import java.util.List;
public interface InterfacciaDaoStruttura {

     void salvaStruttura(Struttura struttura2, String email) throws Exception;
     Struttura cercaStruttura(String nome, String gestore) throws Exception;
     List<Struttura> ricercaStruttureConFiltri(String nome,String citta, String tipo) throws Exception;
     Struttura recuperaStrutturaPerHost(String email) throws Exception;
     void updateStruttura(Struttura struttura, String vecchioNome) throws Exception;
     List<String> recuperaNomiStrutture(String citta) throws Exception;
     void aggiornaFotoStruttura(String emailHost, String fotoNuova) throws Exception;
     
     

    
}