package appolloni.migliano;

import java.util.Scanner;

public class LeggInputCli {
    private static final Scanner scanner = new Scanner(System.in);

    private LeggInputCli() {
        throw new IllegalStateException("Utility class");
    }

    public static String leggiStringa(String messaggio){
       
        if(!messaggio.isEmpty() && messaggio != null){
           System.out.println(messaggio); //NOSONAR 
        }
        return scanner.nextLine().trim();
    }
}
