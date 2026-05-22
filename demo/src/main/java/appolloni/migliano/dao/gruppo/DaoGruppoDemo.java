package appolloni.migliano.dao.gruppo;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import appolloni.migliano.entity.Gruppo;
import appolloni.migliano.interfacce.InterfacciaDaoGruppo;

public class DaoGruppoDemo implements InterfacciaDaoGruppo {

    private static List<Gruppo> gruppiDB = new ArrayList<>();
    private static List<String[]> iscrizioniDB = new ArrayList<>();

    @Override
    public void creaGruppo(Gruppo gruppo) {
        gruppiDB.add(gruppo);

        if (gruppo.getAdmin() != null) {
            String[] rigaIscrizione = {gruppo.getNome(), gruppo.getAdmin().getEmail()};
            iscrizioniDB.add(rigaIscrizione);
        }
    }

    @Override
    public Gruppo cercaGruppo(String nome)  {
        for (Gruppo g : gruppiDB) {
            if (g.getNome().equals(nome)) {
                return g;
            }
        }
        return null;
    }

    @Override
    public List<Gruppo> recuperaGruppiUtente(String emailUtente) {
        List<Gruppo> lista = new ArrayList<>();

        for (String[] riga : iscrizioniDB) {
            String nomeGruppo = riga[0];
            String emailIscritto = riga[1];

            if (emailIscritto.equals(emailUtente)) {
                Gruppo g = cercaGruppo(nomeGruppo);
                if (g != null) {
                    lista.add(g);
                }
            }
        }
        return lista;
    }

    @Override
    public void iscriviUtente(String nomeGruppo, String emailUtente)  {
        String[] nuovaIscrizione = {nomeGruppo, emailUtente};
        iscrizioniDB.add(nuovaIscrizione);
    }

    @Override
    public boolean esisteGruppo(String nomeGruppo)  {
            return cercaGruppo(nomeGruppo) != null;
              
        }

    @Override
    public List<Gruppo> ricercaGruppiConFiltri(String nome, String citta, String materia)  {
        List<Gruppo> risultati = new ArrayList<>();

        for (Gruppo g : gruppiDB) {
            boolean match = true;

            if (nome != null && !g.getNome().contains(nome)) {
                match = false;
            }
   
            if (citta != null && !g.getCitta().equals(citta)) {
                match = false;
            }
            if (materia != null && !g.getMateria().contains(materia)) {
                match = false;
            }

            if (match) {
                risultati.add(g);
            }
        }
        return risultati;
    }

    @Override
    public void abbandonaGruppo(String nomeGruppo, String emailUtente) {
        Iterator<String[]> iter = iscrizioniDB.iterator();
        while (iter.hasNext()) {
            String[] riga = iter.next();
            if (riga[0].equals(nomeGruppo) && riga[1].equals(emailUtente)) {
                iter.remove();
            }
        }
    }

    @Override
    public void eliminaGruppo(String nomeGruppo) {
        gruppiDB.removeIf(g -> g.getNome().equals(nomeGruppo));
        iscrizioniDB.removeIf(riga -> riga[0].equals(nomeGruppo));
        
    }
}