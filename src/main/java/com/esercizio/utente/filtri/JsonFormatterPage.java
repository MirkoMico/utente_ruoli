package com.esercizio.utente.filtri;

import com.esercizio.utente.errori.ErrorEnum;
import com.esercizio.utente.errori.ErroreDto;
import lombok.*;
import org.springframework.data.domain.Page;

@Setter
@AllArgsConstructor
@Getter
@NoArgsConstructor
@ToString
public class JsonFormatterPage<T> {

    private ErroreDto erroreDTO = new ErroreDto(ErrorEnum.OK.ordinal(), ErrorEnum.OK.name(), ErrorEnum.OK.getDescrizione());
    private T filtri;
    private Page<?> elenco;
}
