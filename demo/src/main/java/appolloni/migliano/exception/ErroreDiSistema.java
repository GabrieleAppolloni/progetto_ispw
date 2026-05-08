package appolloni.migliano.exception;

public class ErroreDiSistema extends Exception{
    public ErroreDiSistema(String mess, Throwable cause){
        super(mess, cause);
    }
}
