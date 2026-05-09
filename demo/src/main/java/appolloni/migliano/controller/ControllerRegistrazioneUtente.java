package appolloni.migliano.controller;

import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.entity.Utente;
import appolloni.migliano.entity.Host;
import appolloni.migliano.factory.AbstractFactoryDao;
import appolloni.migliano.factory.FactoryUtenti;
import appolloni.migliano.interfacce.InterfacciaDaoUtente;
import appolloni.migliano.exception.CampiVuotiException;
import appolloni.migliano.exception.EmailNonValidaException;
import appolloni.migliano.exception.ErroreDiSistema;

public class ControllerRegistrazioneUtente {
    private InterfacciaDaoUtente daoUtente = AbstractFactoryDao.getDao().getDaoUtente();

    public void registraUtente(BeanUtenti bean) throws CampiVuotiException, EmailNonValidaException, IllegalArgumentException, ErroreDiSistema {
        String nome = bean.getName();
        String tipo = bean.getTipo();
        String cognome = bean.getCognome();
        String email = bean.getEmail();
        String citta = bean.getCitta();
        String pass = bean.getPassword();
        if(nome.isEmpty() || tipo.isEmpty() || cognome.isEmpty() || email.isEmpty() || citta.isEmpty() ||pass.isEmpty() ){
            throw new CampiVuotiException("Informazioni Utente mancanti.");
        }
        if(!email.contains("@")){
            throw new EmailNonValidaException("Formato email non valido.");
        }

        Utente utente = FactoryUtenti.creazione(tipo, nome, cognome, email, citta, pass);
        
         if(daoUtente.cercaUtente(email) == null){
            if(utente instanceof Host host){
                String nomeAttivita = bean.getNomeAttivita();
                String tipoAttivita = bean.getTipoAttivita();

                if(nomeAttivita.isEmpty() || tipoAttivita.isEmpty()){
                    throw new CampiVuotiException("Dati attività mancanti. ");
                }

                host.setNomeAttivita(nomeAttivita);
                host.setTipoAttivita(tipoAttivita);
            }
            daoUtente.salvaUtente(utente);

         }else{
            throw new IllegalArgumentException("Utente esistente.");
         }

    }
    


}
