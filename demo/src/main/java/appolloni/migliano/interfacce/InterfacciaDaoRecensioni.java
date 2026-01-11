package appolloni.migliano.interfacce;

import java.util.List;

import appolloni.migliano.entity.Recensione;

public interface InterfacciaDaoRecensioni {

    void salvaRecensione(Recensione rec) throws Exception;
    List<Recensione> getRecensioniByStruttura(String nomeStruttura, String nomeGestore) throws Exception;

    
}