package appolloni.migliano.interfacce;

public interface InterfacciaFactoryDao {
  InterfacciaDaoUtente getDaoUtente();
  InterfacciaDaoGruppo getDaoGruppo();
  InterfacciaDaoMessaggi getDaoMessaggi();
  InterfacciaDaoStruttura getDaoStruttura();
  InterfacciaDaoRecensioni getDaoRecensioni();
}
