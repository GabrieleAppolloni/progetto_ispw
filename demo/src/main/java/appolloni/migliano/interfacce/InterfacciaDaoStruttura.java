package appolloni.migliano.interfacce;

import appolloni.migliano.entity.Struttura;
import appolloni.migliano.exception.ErroreDiSistema;
import java.util.List;
public interface InterfacciaDaoStruttura {

     void salvaStruttura(Struttura struttura2, String email) throws ErroreDiSistema;
     Struttura cercaStruttura(String nome, String gestore) throws ErroreDiSistema;
     List<Struttura> ricercaStruttureConFiltri(String nome,String citta, String tipo) throws ErroreDiSistema;
     Struttura recuperaStrutturaPerHost(String email) throws ErroreDiSistema;
     void updateStruttura(Struttura struttura, String vecchioNome) throws ErroreDiSistema;
     List<String> recuperaNomiStrutture(String citta) throws ErroreDiSistema;
     void aggiornaFotoStruttura(String emailHost, String fotoNuova) throws ErroreDiSistema;
     void aggiornaHost(Struttura struttura, String nuovaEmail) throws ErroreDiSistema;
     
     

    
}