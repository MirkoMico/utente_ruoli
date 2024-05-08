package com.esercizio.utente.controller;

import com.esercizio.utente.dto.RuoliDto;
import com.esercizio.utente.errori.ErrorEnum;
import com.esercizio.utente.errori.ErroreDto;
import com.esercizio.utente.filtri.JsonFormatter;
import com.esercizio.utente.model.Ruoli;
import com.esercizio.utente.nuovoService.NuovoServiceRuoli;
import com.esercizio.utente.service.RuoliService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ruoli")
public class RuoliController {


    @Autowired
    private NuovoServiceRuoli nuovoService;

    @PostMapping("/new")
    public ResponseEntity<JsonFormatter<RuoliDto>> create(@RequestBody JsonFormatter<RuoliDto> jsonFormatter) {
        JsonFormatter<RuoliDto> formatter = new JsonFormatter<>();
        try {
            RuoliDto ruoliDto = nuovoService.create(jsonFormatter);
            List<RuoliDto> list = new ArrayList<>();
            list.add(ruoliDto);
            formatter.setFiltri(jsonFormatter.getFiltri());
            formatter.setElenco(list);
            return ResponseEntity.status(HttpStatus.CREATED).body(formatter);
        } catch (IllegalArgumentException e) {
            formatter.setErroreDTO(new ErroreDto(ErrorEnum.ELEMENTO_NON_VALIDO, e));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(formatter);
        } catch (EntityExistsException e) {
            formatter.setErroreDTO(new ErroreDto(ErrorEnum.ENTITA_DUPLICATA, e));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(formatter);


        } catch (Exception e) {
            formatter.setErroreDTO(new ErroreDto(ErrorEnum.ERRORE, e));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(formatter);
        }
    }

    @PostMapping
    public ResponseEntity<JsonFormatter<RuoliDto>> update(@RequestBody JsonFormatter<RuoliDto> jsonFormatter) {
        JsonFormatter<RuoliDto> formatter = new JsonFormatter<>();
        try {

            List<RuoliDto> list = nuovoService.get(jsonFormatter);

            formatter.setFiltri(jsonFormatter.getFiltri());
            formatter.setElenco(list);
            return ResponseEntity.status(HttpStatus.CREATED).body(formatter);
        } catch (IllegalArgumentException e) {
            formatter.setErroreDTO(new ErroreDto(ErrorEnum.ELEMENTO_NON_VALIDO, e));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(formatter);
        } catch (EntityExistsException e) {
            formatter.setErroreDTO(new ErroreDto(ErrorEnum.ENTITA_DUPLICATA, e));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(formatter);
        }
    }







    @PostMapping("/edit")
    public ResponseEntity<JsonFormatter<RuoliDto>> edit(@RequestBody JsonFormatter<RuoliDto> jsonRequest) {
        JsonFormatter<RuoliDto> formatter = new JsonFormatter<>();
        try {
            RuoliDto RuoliDTO = nuovoService.edit(jsonRequest);
            List<RuoliDto> list = new ArrayList<>();
            list.add(   RuoliDTO);
            formatter.setFiltri(jsonRequest.getFiltri());
            formatter.setElenco(list);
            return ResponseEntity.status(HttpStatus.OK).body(formatter);
        } catch (IllegalArgumentException e) {
            formatter.setErroreDTO(new ErroreDto(ErrorEnum.ELEMENTO_NON_VALIDO, e));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(formatter);
        } catch (EntityNotFoundException e) {
            formatter.setErroreDTO(new ErroreDto(ErrorEnum.ENTITA_NON_TROVATA, e));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(formatter);
        } catch (Exception e) {
            formatter.setErroreDTO(new ErroreDto(ErrorEnum.ERRORE, e));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(formatter);
        }
    }
















  /*  @Autowired
    private RuoliService ruoliService;

    @RequestMapping
    public List<Ruoli> getAllRuoli() {
        return null;
    }

    @PostMapping
    public ResponseEntity<Ruoli> saveRuoli(@RequestBody RuoliDto ruoliDto) {
        try {
            Ruoli ruoli = ruoliService.saveRuoli(ruoliDto);
            return ResponseEntity.ok(ruoli);
        }

        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{ruoloId}")
    public ResponseEntity<Optional<Ruoli>> getById(@PathVariable Long ruoloId) {
        try{
        Optional<Ruoli> ruoli = ruoliService.getById(ruoloId);
        return ResponseEntity.ok(ruoli);
    }catch (Exception e){
        return ResponseEntity.badRequest().build();
    }
    }
*/

}
