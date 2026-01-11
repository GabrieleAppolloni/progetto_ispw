package appolloni.migliano.factory;

import appolloni.migliano.Configurazione;
import appolloni.migliano.interfacce.InterfacciaGrafica;
import appolloni.migliano.view.GraficaCLI;
import appolloni.migliano.view.GraficaGUI;

public class FactoryUI {

    public static InterfacciaGrafica getInterfaccia() {
        String tipo = Configurazione.getTipoInterfaccia(); 

        if ("CLI".equals(tipo)) {
            return new GraficaCLI();
        } else {
            return new GraficaGUI();
        }
    }
}