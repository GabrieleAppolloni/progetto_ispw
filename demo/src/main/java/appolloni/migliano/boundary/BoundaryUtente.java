package appolloni.migliano.boundary;
import java.util.Scanner;


public class BoundaryUtente {


    private Scanner input = new Scanner(System.in);

    public String leggiInput(String Text){
        String temp;

        do{
         System.out.println(Text);
         temp = input.nextLine();
         temp = temp.trim();
         if(temp.isEmpty()){
            System.out.println("Stringa non valida, riprovare.");

         }
        }while(temp.isEmpty());
        return temp;
    } 
    
    public String chiediNome(){
        return leggiInput("Inserisci il tuo nome: ");
    }

    public String chiediCognome(){
        return leggiInput("Inserisci il tuo cognome: ");
    }   

    public String chiediEmail(){
        return leggiInput("Inserisci la tua email: ");
    }   
    public String chiediCitta(){
        return leggiInput("Inserisci la tua citta': ");
    }
    public String chiediTipo(){
        String tempTipo;
        do{
         System.out.println("Sei un utente Host o Studente? Inserire la tua risposta: ");
         tempTipo = input.nextLine().trim();
         if(!tempTipo.equalsIgnoreCase("Host") && !tempTipo.equalsIgnoreCase("Studente")){
            System.out.println("Tipo di utente non valido, riprovare.");
         }

        }while(!tempTipo.equalsIgnoreCase("Host") && !tempTipo.equalsIgnoreCase("Studente"));

        if (tempTipo.equalsIgnoreCase("Host")){
            tempTipo = "Host";
        } else {
            tempTipo = "Studente";
        }
        return tempTipo;
    }
    public String chiediAttivita(){
        return leggiInput("Inserisci il nome della tua attivita': ");
    }  

    public String chiediTipoAtt(){
        return leggiInput("Inserisci il tipo dell'attività: ");     
    }

    public String chiediGestore(){
        return leggiInput("Inserisci il nome del gestore: ");
    }

    public String chiediPass(){
        return leggiInput("Inserire la password: ");
    }
}
