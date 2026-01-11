package appolloni.migliano;

import appolloni.migliano.factory.FactoryUI;
import appolloni.migliano.Configurazione;
import appolloni.migliano.DBConnection;
import appolloni.migliano.interfacce.InterfacciaGrafica;

public class Launcher {

    public static void main(String[] args) {
        

        String tipoPersistenza = Configurazione.JDBC;
        String tipoInterfaccia = "GUI";
        
        if (args.length > 0) {
   
            if (args[0].equalsIgnoreCase("FILE")) {
                tipoPersistenza = Configurazione.FILE;
            }else if(args[0].equalsIgnoreCase("DEMO")){
                tipoPersistenza = Configurazione.DEMO;
            }
            if (args.length > 1) {
                if (args[1].equalsIgnoreCase("CLI")) {
                    tipoInterfaccia = "CLI";
                }
            }
        }


        Configurazione.setTipoPersistenza(tipoPersistenza);
        Configurazione.setTipoInterfaccia(tipoInterfaccia);

        System.out.println(">>> MODALITÀ AVVIO: " + tipoPersistenza + " | INTERFACCIA: " + tipoInterfaccia + " <<<");

        try {
            DBConnection.getConnection();
            
            InterfacciaGrafica ui = FactoryUI.getInterfaccia();
            
            ui.avvia(args);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection();
        }
    }
}