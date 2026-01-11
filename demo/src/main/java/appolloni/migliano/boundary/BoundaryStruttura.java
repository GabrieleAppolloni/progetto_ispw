package appolloni.migliano.boundary;

import java.util.Scanner;

public class BoundaryStruttura {

    private Scanner input = new Scanner(System.in);

    public String chiediTipo(){
        String tempTipo;
        do{
            System.out.println("Inserisci il tipo di struttura (Pubblica/Privata): ");
            tempTipo = input.nextLine().trim();
            if(!tempTipo.equalsIgnoreCase("Privata") && !tempTipo.equalsIgnoreCase("Pubblica")){
                System.out.println("Errore, tipo di struttura non valido, riprovare.");

            }

        }while(!tempTipo.equalsIgnoreCase("Privata") && !tempTipo.equalsIgnoreCase("Pubblica"));
        if (tempTipo.equalsIgnoreCase("Privata")){
            tempTipo = "Privata";
        } else {
            tempTipo = "Pubblica";
        }
        return tempTipo;

    }

    public boolean chiediBool(String text){
        String tempBool;
        do{
         System.out.println(text);
         tempBool = input.nextLine().trim();
            if(!tempBool.equalsIgnoreCase("si") && !tempBool.equalsIgnoreCase("no")){
                System.out.println("Risposta non valida, riprovare.");
            }
        }while(!tempBool.equalsIgnoreCase("si") && !tempBool.equalsIgnoreCase("no"));

        if(tempBool.equalsIgnoreCase("si")){
            return true;
        }else{
            return false;
        }   

    }

    public Boolean chiediWifi(){
        return chiediBool("La struttura h ail wifi?: (si/no");
    }

    public Boolean chiediRistorazione(){
        return chiediBool("La struttura dispone di una cucina? (si/no)");
    }

    public String chiediInput(String text){
        String tempString;

        do{
            System.out.println(text);
            tempString = input.nextLine().trim();
            if(tempString.isEmpty()){
                System.out.println("L'input non è valido, riprovare inserendo un input non vuoto.");
            }
        }while(tempString.isEmpty());
        return tempString;
    }

    public String chiediNomeAttivita(){
        return chiediInput("Inserisci il nome della struttura: ");
    }

    public String chiediCitta(){
        return chiediInput("Inserisci la citta in cui è la struttura: ");
    }

    public String chiediIndirizzo(){
        return chiediInput("Inserire la via della struttura: ");

    }

    public String chiediGestore(){
        return chiediInput("Inserire il gestore");
    }

    public String chiediTipoAtt(){
        return chiediInput("Inserire il tipo di attività: ");
    }

    public String chiediOrario(){
        return chiediInput("Inserire orario apertura-chiusura: ");

    }
}
   