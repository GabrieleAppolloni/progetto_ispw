package appolloni.migliano.factory;

import appolloni.migliano.entity.Gruppo;
import appolloni.migliano.entity.Messaggio;
import appolloni.migliano.entity.Utente;

public class FactoryMessaggi {
    public static Messaggio creaMessaggio(String testo, Gruppo gruppo, Utente utente){
        return new Messaggio(null, null, null);
    }
    
}
