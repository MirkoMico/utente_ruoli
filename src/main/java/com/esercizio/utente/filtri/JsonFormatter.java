package com.esercizio.utente.filtri;

import com.esercizio.utente.errori.ErrorEnum;
import com.esercizio.utente.errori.ErroreDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JsonFormatter<T> {

    private ErroreDto erroreDTO = new ErroreDto(ErrorEnum.OK.ordinal(), ErrorEnum.OK.name(), ErrorEnum.OK.getDescrizione());
    private T filtri;
    private List<?> elenco;

}
