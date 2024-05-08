package com.esercizio.utente.nuovoService.impl;

import com.esercizio.utente.dto.RuoliDto;
import com.esercizio.utente.errori.ConversionFailedException;
import com.esercizio.utente.filtri.JsonFormatter;
import com.esercizio.utente.model.Ruoli;
import com.esercizio.utente.nuovoService.NuovoServiceRuoli;
import com.esercizio.utente.repository.RuoliRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import lombok.SneakyThrows;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class NuovoServiceRuoliImpl implements NuovoServiceRuoli {

    @Autowired
    private RuoliRepository ruoliRepository;

    private String parametroId = "id";
    private String parametroDescrizione = "descrizione";

    @Override
    public RuoliDto create(JsonFormatter<RuoliDto> jsonFormatter) throws ConversionFailedException {
        List<Map<String, Object>> elenco = (List<Map<String, Object>>) jsonFormatter.getElenco();
        if (!elenco.isEmpty()) {
            Map<String, Object> map = elenco.get(0);
            Integer id = (Integer) map.get(parametroId);
            String descrizione = (String) map.get(parametroDescrizione);
          ;  if (descrizione != null) {
                    Specification<Ruoli> specification = ricercaPerCriteri(new RuoliDto(id, descrizione));
                try {
                    Ruoli ruoli = ruoliRepository.findOne(specification).orElse(null);
                    if (ruoli == null) {
                        ruoli = new Ruoli();
                        ruoli.setDescrizione(descrizione);
                        ruoliRepository.save(ruoli);
                        return ruoliToDto(ruoli);//return create(descrizione, specification);
                    } else throw new EntityExistsException("ruolo esistente");
                } catch (Exception e) {
                    throw new EntityExistsException(e.getMessage());
                }
            }
        }else{
            throw new ConversionFailedException("errore");
        }
        return null;
    }

    @Override
    public List<RuoliDto> get(JsonFormatter<RuoliDto> jsonFormatter) {
        try {
            Specification<Ruoli> specification = ricercaPerCriteri(jsonFormatter.getFiltri());
            List<Ruoli> applicativoList = ruoliRepository.findAll(specification);
            return applicativoList.stream().map(this::ruoliToDto).toList();
        } catch (Exception e) {
            throw new EntityNotFoundException(e.getMessage());
        }
    }

    @Override
    public RuoliDto edit(JsonFormatter<RuoliDto> jsonFormatter) {
        Specification<Ruoli> specification = ricercaPerCriteri(jsonFormatter.getFiltri());
        List<Map<String, Object>> elenco = (List<Map<String, Object>>) jsonFormatter.getElenco();
        String descrizioneElenco = null;
        if (!elenco.isEmpty()) {
            Map<String, Object> map = elenco.get(0);
            descrizioneElenco = (String) map.get(parametroDescrizione);
        } else throw new IllegalArgumentException(": Elenco non presente nel JSON");
        try {
            Ruoli ruoli = ruoliRepository.findOne(specification).orElse(null);
            System.out.println(specification + "specification");
            if (ruoli != null) {
                ruoli.setDescrizione(descrizioneElenco);
                ruoliRepository.save(ruoli);
                return ruoliToDto(ruoli);
            }else {
                throw new EntityNotFoundException(": Ruolo non trovato nel database");
                // return ruoli.edit(descrizioneElenco, specification);
            }
        } catch (Exception e) {
            throw new EntityNotFoundException(e.getMessage());
        }

    }




//----------------------------------------FINE METODI CONTROLLER----------------------------------------------------

    /**
     * Converte un'istanza di Ruoli in un'istanza di RuoliDTO.
     *
     * @param ruoli l'istanza di Applicativo da convertire
     * @return un'istanza di RuoliDTO corrispondente all'istanza di Ruoli fornita
     * @throws ConversionFailedException se ruoli è null
     */
    @SneakyThrows
    private RuoliDto ruoliToDto(Ruoli ruoli) {
        if (ruoli != null) {
            return new RuoliDto(ruoli);
        } else throw new ConversionFailedException("errore");
    }

    /**
     * Costruisce una specitication (SELECT) dinamica per la ricerca di Ruoli nel database basata sui criteri forniti.
     *
     * @param parametri l'oggetto contenente i criteri di ricerca per Ruoli
     * @return una Specitication per la ricerca di Ruoli basata sui criteri forniti
     */
    private Specification<Ruoli> ricercaPerCriteri(RuoliDto parametri) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Estrarre i valori dai parametri di ricerca e aggiungere i predicati corrispondenti
            Integer id = parametri.getRuoloId();
            if (id != null) {
                predicates.add(criteriaBuilder.equal(root.get(parametroId), id));
            }

            String descrizione = parametri.getDescRuolo();
            if (descrizione != null) {
                predicates.add(criteriaBuilder.equal(root.get(parametroDescrizione), descrizione));
            }

            // Combina i predicati con l'operatore AND
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
