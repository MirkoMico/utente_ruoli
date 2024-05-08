package com.esercizio.utente.errori;

public enum ErrorEnum {

    OK("Operazione completata con successo"),
    ERRORE(""),
    ENTITA_DUPLICATA("L'entità che si tenta di inserire nel database è già presente"),
    ERRORE_CREAZIONE_FILE("Errore di recupero dei dati dal database"),
    ELEMENTO_NON_VALIDO("Elemento inserito non è valido"),
    ENTITA_NON_TROVATA("Entità cercata nel database non è stata trovata");

    private final String DESCRIZIONE;


    ErrorEnum(String descrizione) {
        this.DESCRIZIONE = descrizione;
    }

    public String getDescrizione(){
        return DESCRIZIONE;
    }











}
