package appolloni.migliano.boundary;

import java.util.Scanner;

public class BoundaryInvioMessaggio {

    private Scanner input = new Scanner(System.in);
    private String tempString;

    public String chiediInput(String domanda){
        do{
         System.out.println(domanda);
         tempString = input.nextLine().trim();
         if(tempString.isEmpty()){
            System.out.println("Messaggio non valido, riprova.");
         }
        }while(tempString.isEmpty());
        
        return tempString;

    }

    public String chiediTesto(){
        return chiediInput("Inserisci il testo del messaggio: ");
    }



}
