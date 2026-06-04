package appolloni.migliano.entity;

import java.time.LocalDateTime;;

public class Messaggio {
    private String text;
    private Gruppo gruppo; 
    private Studente mittente;
    private LocalDateTime dataInvio;
    
    public Messaggio(String messaggio, Gruppo gruppo, Studente user){
        this.dataInvio = LocalDateTime.now();
        this.gruppo = gruppo;
        this.text = messaggio;
        this.mittente = user;

    }
    public LocalDateTime getTime(){
        return this.dataInvio;
    }
    public void setTime(LocalDateTime time){
        this.dataInvio = time;
    }
    public void setMess(String testo){

        this.text = testo;
    }
    
    public String getMess(){
        return this.text;
    }

    public void setGruppo(Gruppo g){
        this.gruppo = g;

    }
    public Gruppo getGruppo(){
        return this.gruppo;
    }
    public void setMittente(Studente u){
        this.mittente = u;
    }
    public Utente getMittente(){
        return this.mittente;
    }
}
