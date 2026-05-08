package appolloni.migliano.interfacce;


import appolloni.migliano.entity.Utente;
import appolloni.migliano.exception.ErroreDiSistema;

public interface InterfacciaDaoUtente {

    void salvaUtente(Utente u) throws ErroreDiSistema;
    Utente cercaUtente(String email) throws ErroreDiSistema;
    void aggiornaPassword(String email, String nuovaPass) throws ErroreDiSistema;
    
}
