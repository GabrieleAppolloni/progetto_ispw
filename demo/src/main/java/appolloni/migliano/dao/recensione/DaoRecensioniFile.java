package appolloni.migliano.dao.recensione;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import appolloni.migliano.entity.Recensione;
import appolloni.migliano.entity.Struttura;
import appolloni.migliano.entity.Studente;
import appolloni.migliano.entity.Utente;
import appolloni.migliano.exception.ErroreDiSistema;
import appolloni.migliano.factory.AbstractFactoryDao;
import appolloni.migliano.interfacce.InterfacciaDaoRecensioni;
import appolloni.migliano.interfacce.InterfacciaDaoStruttura;
import appolloni.migliano.interfacce.InterfacciaDaoUtente;

public class DaoRecensioniFile implements InterfacciaDaoRecensioni {

    private static final Logger logger = Logger.getLogger(DaoRecensioniFile.class.getName());
    private static final String CSVFILE = "recensioni.csv";
    private static final String FORMATOCSV = "%s;%s;%s;%d;%s";

    private Studente casting(Utente u) {
     if (u instanceof Studente studente) {
        return studente;
     } else {
        throw new IllegalArgumentException("Errore recupero dati");
     }
    }

    @Override
    public void salvaRecensione(Recensione r) throws ErroreDiSistema {
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSVFILE, true))) {
            
            String testoPulito = r.getTesto().replace(";", ",").replace("\n", " ");
            
            String riga = String.format(FORMATOCSV,
                    r.getAutore().getEmail(),             
                    r.getStrutturaRecensita().getName(),
                    r.getStrutturaRecensita().getGestore(),
                    r.getVoto(),
                    testoPulito
            );

            bw.write(riga);
            bw.newLine();
        }catch(IOException e){
            logger.log(Level.SEVERE,"Errore salvataggio recensione su file",e);
            throw new ErroreDiSistema("Errore salvataggio recensione", e);
        }
    }

    @Override
    public List<Recensione> getRecensioniByStruttura(String nomeStr, String gestore) throws ErroreDiSistema {
        List<Recensione> lista = new ArrayList<>();
        File file = new File(CSVFILE);

        if (!file.exists()) {return lista;}
        InterfacciaDaoStruttura daoStruttura = AbstractFactoryDao.getDao().getDaoStruttura();
        InterfacciaDaoUtente daoUtente = AbstractFactoryDao.getDao().getDaoUtente();
        Struttura strutturaTarget = null;


        
        strutturaTarget = daoStruttura.cercaStruttura(nomeStr, gestore);
    
        if (strutturaTarget == null) { return lista;}

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] dati = line.split(";");
                if (dati.length < 5) continue; 

                String csvEmail = dati[0];
                String csvNomeStr = dati[1];
                String csvGestoreStr = dati[2];
                int csvVoto = Integer.parseInt(dati[3]);
                String csvTesto = dati[4];

                if (csvNomeStr.equals(nomeStr) && csvGestoreStr.equals(gestore)) {
                    
                    Studente autore = casting(daoUtente.cercaUtente(csvEmail));
                    Recensione r = new Recensione(csvTesto, csvVoto, autore, strutturaTarget);
                    lista.add(r);
                }
            }
        }catch(IOException e){
            logger.log(Level.SEVERE,"Errore lettura recensioni da file",e);
            throw new ErroreDiSistema("Errore recupero recensioni ", e);

        }
        return lista;
    }
}