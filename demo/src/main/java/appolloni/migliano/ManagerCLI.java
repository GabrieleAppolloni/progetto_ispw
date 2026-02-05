package appolloni.migliano;

import appolloni.migliano.bean.BeanGruppo;
import appolloni.migliano.bean.BeanStruttura;
import appolloni.migliano.bean.BeanUtenti;
import appolloni.migliano.cli.LoginCLI;
import appolloni.migliano.cli.ChatCLI;
import appolloni.migliano.cli.CreazioneUtenteCLI;
import appolloni.migliano.cli.CreazioneGruppoCLI;
import appolloni.migliano.cli.CreazioneStruttureCLI;
import appolloni.migliano.cli.ModificaStrutturaCLI;
import appolloni.migliano.cli.RicercaCLI;
import appolloni.migliano.cli.ScriviRecensioneCLI;
import appolloni.migliano.cli.SegnalaStrutturaCLI;
import appolloni.migliano.cli.HostMenuCLI;
import appolloni.migliano.cli.MenuPrincipaleCLI;
import appolloni.migliano.cli.ProfiloUtenteCLI;
public class ManagerCLI {

    private static ManagerCLI instance = null;

    private ManagerCLI() {}

    public static ManagerCLI getInstance() {
        if (instance == null) {
            instance = new ManagerCLI();
        }
        return instance;
    }

    public void avviaMainMenu(BeanUtenti beanUtente) {
        new MenuPrincipaleCLI(beanUtente).start();
    }

    public void avviaMenuHost(BeanUtenti beanUtente) {
        new HostMenuCLI(beanUtente).start();
    }
    public void avviaRicerca(BeanUtenti bean) {
        new RicercaCLI(bean).start();
    }

    public void apriProfilo(BeanUtenti bean) {
        new ProfiloUtenteCLI(bean).start();
    }
    
    public void avviaCreazioneUtente(){
        new CreazioneUtenteCLI().start();
    }

    public void avviaCreazioneGruppo(BeanUtenti bean) {
        new CreazioneGruppoCLI(bean).start();
    }
    public void avviaCreazioneStruttura(BeanUtenti bean) {
         new CreazioneStruttureCLI(bean).start();
    }
    public void avviaSegnalaStruttura(BeanUtenti bean) {
        new SegnalaStrutturaCLI(bean).start();
    }

    public void avviaChat(BeanUtenti bean, BeanGruppo gruppo) {
        new ChatCLI(bean, gruppo).start();
    }
    public void scriviRec(BeanUtenti bean, BeanStruttura struttura){
        new ScriviRecensioneCLI(bean, struttura).start();
    }
    public void login(){
        new LoginCLI().start();
    }
    public void modificaStruttura(BeanUtenti bean, BeanStruttura struttura){
        new ModificaStrutturaCLI(bean, struttura).start();
    }
}