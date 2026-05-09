package appolloni.migliano.interfacce;

import java.util.List;

import appolloni.migliano.entity.Gruppo;
import appolloni.migliano.exception.ErroreDiSistema;

public interface InterfacciaDaoGruppo {
    void creaGruppo(Gruppo gruppo) throws ErroreDiSistema;
    Gruppo cercaGruppo(String nome) throws ErroreDiSistema;
    List<Gruppo> recuperaGruppiUtente(String email) throws ErroreDiSistema;
    void iscriviUtente(String nomeGruppo, String emailUtente) throws ErroreDiSistema;
    boolean esisteGruppo(String nome) throws ErroreDiSistema;
    List<Gruppo> ricercaGruppiConFiltri(String nome, String citta, String materia) throws ErroreDiSistema;
    void abbandonaGruppo(String nomeGruppo, String emailUtente) throws ErroreDiSistema;
    void eliminaGruppo(String nomeGruppo) throws ErroreDiSistema;

}
