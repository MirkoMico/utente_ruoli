package com.esercizio.utente.errori;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ErroreDto {
    private int idErrore;
    private String descrizione;
    private String messaggio;

    public ErroreDto(ErrorEnum errorEnum, Exception e) {
        this.idErrore = errorEnum.ordinal();
        this.descrizione = errorEnum.name();
        this.messaggio = errorEnum.getDescrizione() + ": " + e.getMessage();
    }



    //public ErroreDto(ErrorEnum errorEnum, String message) {}

}
