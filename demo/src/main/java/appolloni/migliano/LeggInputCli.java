package appolloni.migliano;

import java.util.Scanner;

public class LeggInputCli {
    private static final Scanner scanner = new Scanner(System.in);

    private LeggInputCli() {
        throw new IllegalStateException("Utility class");
    }

    public static String leggiStringa(String messaggio){
       
        if(messaggio != null && !messaggio.isEmpty()){
           System.out.println(messaggio); //NOSONAR 
        }
        return scanner.nextLine().trim();
    }

    public static String acquisisciOrario() {
     String input;
     while (true) {
        input = leggiStringa("Inserisci orario di apertura (es. 08:00-19:00): ");

        if (input.isEmpty()) return "";

        if (validaFormatoEIntervallo(input)) {
            return input;
        }
        
        System.out.println("[ERRORE] Formato non valido o orario incoerente."); //NOSONAR
     }
    }

    public static boolean validaFormatoEIntervallo(String input) {
     String regex = "^([0-1]?\\d|2[0-3]):[0-5]\\d-([0-1]?\\d|2[0-3]):[0-5]\\d$";
    
  
     if (!input.matches(regex)) {
        return false;
     }

     String[] parti = input.split("-");
     if (parti.length < 2) return false;

     String[] inizio = parti[0].split(":");
     String[] fine = parti[1].split(":");

     if (inizio.length < 2 || fine.length < 2) return false;

   
     int minutiInizio = Integer.parseInt(inizio[0]) * 60 + Integer.parseInt(inizio[1]);
     int minutiFine = Integer.parseInt(fine[0]) * 60 + Integer.parseInt(fine[1]);

     if (minutiFine <= minutiInizio) {
        System.out.println("[ERRORE] L'orario di chiusura deve essere successivo a quello di apertura."); //NOSONAR
        return false;
     }

     return true;
    }

}
