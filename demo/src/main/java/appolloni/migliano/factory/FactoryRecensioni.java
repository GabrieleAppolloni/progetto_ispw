package appolloni.migliano.factory;

import appolloni.migliano.entity.Recensione;
import appolloni.migliano.entity.Struttura;
import appolloni.migliano.entity.Utente;

public class FactoryRecensioni {
    
    public static Recensione creazioneRecensione(Utente autore, Struttura struttura, String text, int voto){
        return new Recensione(text, voto, autore, struttura);
    }
}
