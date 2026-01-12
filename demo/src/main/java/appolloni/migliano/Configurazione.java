package appolloni.migliano;

public class Configurazione {
    public static final String JDBC = "JDBC";
    public static final String FILE = "FILE";
    public static final String DEMO = "DEMO";
    

    private static String tipoPersistenza = JDBC; 
    private static String tipoInterfaccia = "GUI"; 

    private Configurazione() {
        throw new UnsupportedOperationException("Questa è una classe di utilità e non può essere istanziata");
    }

    public static String getTipoPersistenza() { return tipoPersistenza; }
    public static void setTipoPersistenza(String tipo) { tipoPersistenza = tipo; }


    public static String getTipoInterfaccia() { return tipoInterfaccia; }
    public static void setTipoInterfaccia(String tipo) { tipoInterfaccia = tipo; }
}