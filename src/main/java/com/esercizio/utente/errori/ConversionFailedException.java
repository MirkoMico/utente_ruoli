package com.esercizio.utente.errori;

public class ConversionFailedException extends Exception{
    public ConversionFailedException(String tipoErrore) {
        super(tipoErrore);
    }

}
