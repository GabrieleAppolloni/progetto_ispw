package appolloni.migliano;

import java.util.Scanner;

public class LeggInputCli {
    private static final Scanner scanner = new Scanner(System.in);

    public static String leggiStringa(String messaggio){
        System.out.println(messaggio); //NOSONAR
        return scanner.nextLine().trim();
    }
}
