package appolloni.migliano.controller;
import appolloni.migliano.bean.BeanStruttura;
import appolloni.migliano.entity.Struttura;
import appolloni.migliano.exception.CampiVuotiException;
import appolloni.migliano.exception.ErroreDiSistema;
import appolloni.migliano.factory.AbstractFactoryDao;
import appolloni.migliano.interfacce.InterfacciaDaoStruttura;

public class ControllerModificaStrutturHost {

    private InterfacciaDaoStruttura daoStruttura;

    public ControllerModificaStrutturHost(){
        this.daoStruttura = AbstractFactoryDao.getDao().getDaoStruttura();
    }
    
    public void aggiornaStruttura(BeanStruttura beanStruttura, String vecchioNome) throws CampiVuotiException, ErroreDiSistema{
        if(beanStruttura.getName().isBlank() || beanStruttura.getCitta().isBlank() || beanStruttura.getOrario().isBlank() || beanStruttura.getIndirizzo().isBlank()) throw new CampiVuotiException("Nessuna modifica applicata");
        Struttura struttura = new Struttura(beanStruttura.getTipo(), beanStruttura.getName(), beanStruttura.getCitta(), beanStruttura.getIndirizzo(), beanStruttura.hasWifi(), beanStruttura.hasRistorazione());
        struttura.setOrario(beanStruttura.getOrario());
        struttura.setTipoAttivita(beanStruttura.getTipoAttivita());
        struttura.setFoto(beanStruttura.getFoto());
        struttura.setGestore(beanStruttura.getGestore());
        daoStruttura.updateStruttura(struttura, vecchioNome);
    }
}
