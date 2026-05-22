package appolloni.migliano.interfacce;

import appolloni.migliano.entity.Gruppo;
import appolloni.migliano.entity.Messaggio;
import appolloni.migliano.exception.ErroreDiSistema;
import java.util.List;

public interface InterfacciaDaoMessaggi {
    
    void nuovoMessaggio(Messaggio messaggio) throws ErroreDiSistema;
    List<Messaggio> cercaMessaggio(Gruppo gruppo) throws ErroreDiSistema;

}