package appolloni.migliano.boundary;

import java.util.Scanner;

public class BoundaryRecensione {

    private Scanner input = new Scanner(System.in);

    public String chiediInfo(String text){
        String temp;

        do{
            System.out.println(text);
            temp = input.nextLine().trim();

            if(temp.isEmpty()){
                System.out.println("Errore riprovare");

            }

        }while(temp.isEmpty());

        return temp;
    }

    public String chiediRecensione(){
        return chiediInfo("Inserire la recensione");
    }

    public String chiediVoto(){
        String temp_2;
        boolean done = false;

        do{ 
         temp_2 = chiediInfo("Inserire il voto per la struttura: ");
         try{
            Integer.parseInt(temp_2);
            done = true;

         }catch(NumberFormatException e){
            System.out.println("Riprova, inserire un numero!");

         }

        }while(!done);
    
        return temp_2;
    }    
}
