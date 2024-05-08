package com.esercizio.utente.nuovoService;

import com.esercizio.utente.dto.RuoliDto;
import com.esercizio.utente.errori.ConversionFailedException;
import com.esercizio.utente.filtri.JsonFormatter;
import com.esercizio.utente.model.Ruoli;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface NuovoServiceRuoli {

    RuoliDto create(JsonFormatter<RuoliDto>jsonFormatter) throws ConversionFailedException;

    List<RuoliDto> get(JsonFormatter<RuoliDto> jsonFormatter);

    RuoliDto edit(JsonFormatter<RuoliDto> jsonFormatter);
}
