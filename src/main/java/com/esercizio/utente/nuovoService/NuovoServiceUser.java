package com.esercizio.utente.nuovoService;

import com.esercizio.utente.dto.UserDto;
import com.esercizio.utente.errori.ConversionFailedException;
import com.esercizio.utente.filtri.JsonFormatter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NuovoServiceUser {

    UserDto create(JsonFormatter<UserDto> jsonFormatter) throws ConversionFailedException;

    Page<UserDto> get(JsonFormatter<UserDto> jsonFormatter, Pageable pageable);

    UserDto edit(JsonFormatter<UserDto> jsonFormatter);
}
