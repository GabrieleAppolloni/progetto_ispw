package appolloni.migliano.boundary;

import java.util.Scanner;



public class BoundaryCreazioneGruppo {

    private Scanner input = new Scanner(System.in);

    public String acquisciInput(String text) {
        String temp;
        
        do{
            System.out.println(text);
            temp = input.nextLine().trim();
            if(temp.isEmpty()){
                System.out.println("Errore, non sono stati inseriti caratteri.");              
            }


        }while(temp.isEmpty()); 
        return temp;
    }

    public String chiediNomeGruppo(){
        return acquisciInput("Inserire il nome del Gruppo");
    }

    public String chiediMateria(){
        return acquisciInput("Inserire la materia di studio: ");

    }



}
