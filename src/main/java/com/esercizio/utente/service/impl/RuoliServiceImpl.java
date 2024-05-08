package com.esercizio.utente.service.impl;


import com.esercizio.utente.dto.RuoliDto;
import com.esercizio.utente.model.Ruoli;
import com.esercizio.utente.repository.RuoliRepository;
import com.esercizio.utente.service.RuoliService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/*
@Service
public class RuoliServiceImpl implements RuoliService {
    @Autowired
    private RuoliRepository ruoliRepository;

    @Override
    public Ruoli saveRuoli(RuoliDto ruoliDto) {
        Ruoli ruoli = new Ruoli();
        ruoli.setDescRuolo(ruoliDto.getDescRuolo());
        return ruoliRepository.save(ruoli);

    }

   @Override
    public Optional<Ruoli> getById(Long ruoloId) {
        Optional<Ruoli> list = ruoliRepository.findById(ruoloId);
        if (list != null) {
            return list;
        }
        return null;
    }



    @Override
    public Ruoli deleteRuoli(Long ruoloId) {
        return null;
    }
}*/
