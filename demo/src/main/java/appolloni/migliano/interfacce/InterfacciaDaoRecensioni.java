package appolloni.migliano.interfacce;
import java.util.List;

import appolloni.migliano.entity.Recensione;
import appolloni.migliano.exception.ErroreDiSistema;

public interface InterfacciaDaoRecensioni {

    void salvaRecensione(Recensione rec) throws ErroreDiSistema;
    List<Recensione> getRecensioniByStruttura(String nomeStruttura, String nomeGestore) throws ErroreDiSistema;

    
}