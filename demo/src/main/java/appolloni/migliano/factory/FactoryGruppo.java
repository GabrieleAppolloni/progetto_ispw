package appolloni.migliano.factory;

import appolloni.migliano.entity.Gruppo;
import appolloni.migliano.entity.Utente;

public class FactoryGruppo {
    public static Gruppo creaGruppo(String nomeGruppo, Utente admin){
        return new Gruppo(nomeGruppo, admin);
    }
    
}
