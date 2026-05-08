package appolloni.migliano.dao.strutture;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import appolloni.migliano.dao.utente.DaoUtenteDB;
import appolloni.migliano.entity.Struttura;
import appolloni.migliano.exception.ErroreDiSistema;
import appolloni.migliano.interfacce.InterfacciaDaoStruttura;

public class DAOStruttureFILE  implements InterfacciaDaoStruttura{
    private static final String CSVFILE = "strutture.csv";
    private static final String FORMATOCSV = "%s;%s;%s;%s;%s;%s;%s;%b;%b;%s";
    private static final Logger logger = Logger.getLogger(DaoUtenteDB.class.getName());

    @Override
    public void salvaStruttura(Struttura s, String email) throws ErroreDiSistema {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSVFILE, true))) {
            String riga = String.format(FORMATOCSV,
                s.getName(), s.getTipo(), s.getCitta(), s.getIndirizzo(), s.getOrario(),
                (email != null ? email : "system_no_host"),
                s.getFoto(), s.hasWifi(), s.hasRistorazione(), s.getTipoAttivita()
            );
            bw.write(riga);
            bw.newLine();
        }catch(IOException e){
            logger.log(Level.SEVERE,"Errore salvataggioo struttura"+ s.getName(),e);
            throw new ErroreDiSistema("Errore salvataggio struttura", e);

        }
    }

    @Override
    public Struttura cercaStruttura(String nomeStruttura, String gestore) throws ErroreDiSistema{
        File file = new File(CSVFILE);
        
        if(!file.exists()) return null;
        Struttura struttura = null;
        try(BufferedReader br = new BufferedReader(new FileReader (file))) {
            String line;
            while((line = br.readLine()) != null){
                String[] dati = line.split(";");
                if(dati.length >= 10 && dati[0].equals(nomeStruttura)&& dati[5].equals(gestore)){
                    struttura = new Struttura(dati[1], dati[0],dati[2],dati[3], Boolean.parseBoolean(dati[7]), Boolean.parseBoolean(dati[8]));
                    struttura.setTipoAttivita(dati[9]);
                    struttura.setGestore(dati[5]);
                    struttura.setFoto(dati[6]);
                    struttura.setOrario(dati[4]);
                    return struttura;
                }

            }
        }catch(IOException e){
            logger.log(Level.SEVERE,"Errore ricerca struttura"+ nomeStruttura,e);
            throw new ErroreDiSistema("Errore ricerca struttura", e);

        }
        return struttura;
        
    }

   @Override
    public List<Struttura> ricercaStruttureConFiltri(String nome, String citta, String tipo) throws ErroreDiSistema {
     List<Struttura> lista = new ArrayList<>();
     File file = new File(CSVFILE);
     if (!file.exists()) {return lista;}

     try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] dati = line.split(";");
            if (dati.length < 10) {continue;} 
            if (soddisfaFiltri(dati, nome, citta, tipo)) {
                lista.add(creaStrutturaDaCsv(dati));
            }
        }
     }catch(IOException e){
            logger.log(Level.SEVERE,"Errore ricerca struttura con filtri"+ nome,e);
            throw new ErroreDiSistema("Errore ricerca struttura con filtri", e);

        }
     return lista;
    }


    private boolean soddisfaFiltri(String[] dati, String nome, String citta, String tipo) {

     if (nome != null && !nome.isEmpty() && !dati[0].toLowerCase().contains(nome.toLowerCase())) {
        return false;
     }

     if (citta != null && !citta.isEmpty() && !dati[2].toLowerCase().contains(citta.toLowerCase())) {
        return false;
     }

     return !(tipo != null && !tipo.isEmpty() && !dati[9].equalsIgnoreCase(tipo));
    }


    private Struttura creaStrutturaDaCsv(String[] dati) {
     String foto = dati[6];
     if (foto == null || foto.isEmpty() || "null".equalsIgnoreCase(foto)) {
        foto = "placeholder.png";
     }

     Struttura s = new Struttura(
        dati[1], 
        dati[0],
        dati[2], 
        dati[3],
        Boolean.parseBoolean(dati[7]), 
        Boolean.parseBoolean(dati[8])
     );
     s.setFoto(foto);
     s.setTipoAttivita(dati[9]);
     s.setGestore(dati[5]);
     s.setOrario(dati[4]);
     return s;
    }



    @Override
    public Struttura recuperaStrutturaPerHost(String emailHost) throws ErroreDiSistema {
        File file = new File(CSVFILE);
        if (!file.exists()) { return null;}

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] dati = line.split(";");
                if (dati.length >= 10 && dati[5].equals(emailHost)) {
                    Struttura s = new Struttura(
                        dati[1], 
                        dati[0], 
                        dati[2], 
                        dati[3], 
                        Boolean.parseBoolean(dati[7]), 
                        Boolean.parseBoolean(dati[8])
                    );
                    s.setFoto(dati[6]);
                    s.setTipoAttivita(dati[9]);
                    s.setGestore(dati[5]);
                    s.setOrario(dati[4]);
                    return s;
                }
            }
        }catch(IOException e){
            logger.log(Level.SEVERE,"Errore ricerca struttura host"+ emailHost,e);
            throw new ErroreDiSistema("Errore ricerca struttura host", e);

        }
        return null;
    }

   @Override
   public void updateStruttura(Struttura struttura, String vecchioNome) throws ErroreDiSistema {
    File file = new File(CSVFILE);
    if (!file.exists()) {return;}

    List<String> righeDaRiscrivere = new ArrayList<>();
    boolean trovato = false;

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] dati = line.split(";");
    
            if (dati.length > 0 && dati[0].equalsIgnoreCase(vecchioNome)) {
               
                String rigaAggiornata = String.format(FORMATOCSV,
                        struttura.getName(), struttura.getTipo(), struttura.getCitta(),
                        struttura.getIndirizzo(), struttura.getOrario(), struttura.getGestore(),
                        struttura.getFoto(), struttura.hasWifi(), struttura.hasRistorazione(),
                        struttura.getTipoAttivita());
                
                righeDaRiscrivere.add(rigaAggiornata); 
                trovato = true;
            } else {
                righeDaRiscrivere.add(line); 
            }
        }
    }catch(IOException e){
            logger.log(Level.SEVERE,"Errore aggiornamento struttura"+ struttura.getName(),e);
            throw new ErroreDiSistema("Errore aggiornamento struttura", e);

    }


      if (trovato) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) { 
            for (String riga : righeDaRiscrivere) {
                bw.write(riga);
                bw.newLine();
            }

        }catch(IOException e){
            logger.log(Level.SEVERE,"Errore aggiornamento struttura"+ struttura.getName(),e);
            throw new ErroreDiSistema("Errore aggiornamento struttura", e);

        }
    }
    }

    @Override
    public void aggiornaFotoStruttura(String emailHost, String fotoNuova) throws ErroreDiSistema {
        File file = new File(CSVFILE);
        if (!file.exists()) return;

        List<String> righeDaScrivere = new ArrayList<>();
        boolean modificato = false;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] dati = line.split(";");
                if (dati.length >= 10 && dati[5].equalsIgnoreCase(emailHost)) {
                    String nuovaRiga = String.format(FORMATOCSV, 
                        dati[0], dati[1], dati[2], dati[3], dati[4], 
                        dati[5], 
                        fotoNuova, 
                        Boolean.parseBoolean(dati[7]), Boolean.parseBoolean(dati[8]), dati[9]
                    );
                    righeDaScrivere.add(nuovaRiga);
                    modificato = true;
                } else {
                
                    righeDaScrivere.add(line);
                }
            }
        }catch(IOException e){
            logger.log(Level.SEVERE,"Errore aggiornamento foto struttura"+ emailHost ,e);
            throw new ErroreDiSistema("Errore aggiornamento foto struttura", e);

        }
        if (modificato) {
            riscriviFile(file, righeDaScrivere);
        }
    }
    
    
    @Override 
    public List<String> recuperaNomiStrutture(String citta) throws ErroreDiSistema {
        List<String> listaNomi = new ArrayList<>();
        
        File file = new File(CSVFILE);
        if (!file.exists()) {return listaNomi;}

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] dati = line.split(";");
                
                if (dati.length >= 3) {
                    String cittaNelFile = dati[2];
                    
                    if (cittaNelFile != null && cittaNelFile.trim().equalsIgnoreCase(citta.trim())) {
                        listaNomi.add(dati[0]);
                    }
                }
            }
        }catch(IOException e){
            logger.log(Level.SEVERE,"Errore recupero struttura citta"+ citta,e);
            throw new ErroreDiSistema("Errore recupero struttura citta", e);

        }

        return listaNomi;
    }

@Override
    public void aggiornaHost(Struttura s, String nuovoGestore) throws ErroreDiSistema {
        File file = new File(CSVFILE);
        if (!file.exists()) return;

        List<String> righeDaScrivere = new ArrayList<>();
        boolean trovato = false;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] dati = line.split(";");
                
                
                if (dati.length >= 10 && 
                    dati[0].equalsIgnoreCase(s.getName()) && 
                    dati[5].equalsIgnoreCase("system_no_host")) {

                    
                    String rigaAggiornata = String.format(FORMATOCSV,
                        s.getName(), 
                        s.getTipo(), 
                        s.getCitta(), 
                        s.getIndirizzo(), 
                        s.getOrario(),
                        nuovoGestore,    
                        s.getFoto(), 
                        s.hasWifi(), 
                        s.hasRistorazione(), 
                        s.getTipoAttivita()
                    );
                    righeDaScrivere.add(rigaAggiornata);
                    trovato = true;
                } else {
                    righeDaScrivere.add(line); 
                }
            }
        }catch(IOException e){
            logger.log(Level.SEVERE,"Errore aggiornamento host struttura"+ s.getName(),e);
            throw new ErroreDiSistema("Errore aggiornamento host struttura", e);
        }

        if (trovato) {
            riscriviFile(file, righeDaScrivere);
        }
    }

    
    private void riscriviFile(File file, List<String> righe) throws ErroreDiSistema {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
            for (String riga : righe) {
                bw.write(riga);
                bw.newLine();
            }
        }catch(IOException e){
            logger.log(Level.SEVERE,"Errore aggiornamento struttura"+ file,e);
            throw new ErroreDiSistema("Errore aggiornamento struttura", e);

        }
    }

}
