package com.esercizio.utente.controller;

import com.esercizio.utente.dto.RuoliDto;
import com.esercizio.utente.dto.UserDto;
import com.esercizio.utente.errori.ErrorEnum;
import com.esercizio.utente.errori.ErroreDto;
import com.esercizio.utente.filtri.JsonFormatter;
import com.esercizio.utente.filtri.JsonFormatterPage;
import com.esercizio.utente.model.User;
import com.esercizio.utente.nuovoService.NuovoServiceUser;
import com.esercizio.utente.service.UserService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private NuovoServiceUser nuovoServiceUser;


    @PostMapping("/new")
    public ResponseEntity<JsonFormatter<UserDto>> create(@RequestBody JsonFormatter<UserDto> jsonFormatter) {
        JsonFormatter<UserDto> formatter = new JsonFormatter<>();
        try {
            UserDto userDto = nuovoServiceUser.create(jsonFormatter);
            List<UserDto> list = new ArrayList<>();
            list.add(userDto);
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

    @PostMapping("/{pageNumber}-{pageSize}")
    public ResponseEntity<JsonFormatterPage<UserDto>> get(@RequestBody JsonFormatter<UserDto> jsonFormatter,
                                                          @PathVariable("pageNumber") Integer pageNumber,
                                                          @PathVariable("pageSize") Integer pageSize)
                                                         {
        Pageable pageable = creazionePageable(pageNumber, pageSize);
        return processRequest(jsonFormatter, pageable);
    }

    @PostMapping("/edit")
    public ResponseEntity<JsonFormatter<UserDto>> edit(@RequestBody JsonFormatter<UserDto> jsonFormatter) {
        JsonFormatter<UserDto> formatter = new JsonFormatter<>();
        try {
            UserDto userDto = nuovoServiceUser.edit(jsonFormatter);
            List<UserDto> list = new ArrayList<>();
            list.add(userDto);
            formatter.setFiltri(jsonFormatter.getFiltri());
            formatter.setElenco(list);
            return ResponseEntity.status(HttpStatus.CREATED).body(formatter);
        } catch (IllegalArgumentException e) {
            formatter.setErroreDTO(new ErroreDto(ErrorEnum.ELEMENTO_NON_VALIDO, e));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(formatter);
        } catch (EntityNotFoundException e) {
            formatter.setErroreDTO(new ErroreDto(ErrorEnum.ENTITA_NON_TROVATA, e));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(formatter);
        } catch (Exception e) {
            formatter.setErroreDTO(new ErroreDto(ErrorEnum.ERRORE, e));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(formatter);
        }
    }







  /*  @Autowired
    private UserService userService;

  @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody UserDto userDto) {
      try {
        User user = userService.saveUser(userDto);
        return ResponseEntity.ok(user);
    }catch (Exception e) {
        return ResponseEntity.badRequest().build();
    }
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@RequestBody UserDto userDto, @PathVariable Long userId) {
      try {
          User user = userService.updateUser(userDto, userId);
          return ResponseEntity.ok(user);
      }catch (Exception e) {
          return ResponseEntity.badRequest().build();
      }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<User>> getUserByRuoloId(@PathVariable Long userId) {
        try {
            Optional<List<User>> userOptional = userService.getUserByRuoliId(userId);
            return userOptional.map(user -> ResponseEntity.status(HttpStatus.OK).body(user))
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
           return ResponseEntity.badRequest().build();
        }
    }


*/

    //----------------------------------------FINE METODI CRUD----------------------------------------------------

    /**
     * Crea un oggetto Pageable per la paginazione dei risultati, eventualmente ordinati per un campo specificato.
     *
     * @param pageNumber il numero di pagina (inizia da 1)
     * @param pageSize la dimensione della pagina
     * //@param campo il campo su cui ordinare i risultati (opzionale)
     * //@param ordinamento la direzione dell'ordinamento ("asc" per ascendente, "desc" per discendente) (opzionale)
     * @return un oggetto Pageable per la paginazione dei risultati
     */
    private Pageable creazionePageable(Integer pageNumber, Integer pageSize/*, String campo, String ordinamento*/) {
        return PageRequest.of(pageNumber -1, pageSize);
    }
        /*if (campo != null && ordinamento != null) {
            Sort sort = Sort.by(verificaDirezione(ordinamento), campo);
            return PageRequest.of(pageNumber - 1, pageSize, sort);
        } else {
            return PageRequest.of(pageNumber - 1, pageSize);
        }
    }*/

    /**
     * Verifica la direzione dell'ordinamento specificato e restituisce l'enumerazione Sort.Direction corrispondente.
     *
     * @param ordinamento la stringa rappresentante la direzione dell'ordinamento ("asc" per ascendente, "desc" per discendente)
     * @return l'enumerazione Sort.Direction corrispondente alla direzione dell'ordinamento specificato
     * @throws IllegalArgumentException se l'ordinamento non è valido (deve essere "asc" per ascendente o "desc" per discendente)
     */
    private Sort.Direction verificaDirezione(String ordinamento){
        if (ordinamento.equalsIgnoreCase("desc")){
            return Sort.Direction.DESC;
        } else if (ordinamento.equalsIgnoreCase("asc")) {
            return Sort.Direction.ASC;
        } else{
            throw new IllegalArgumentException("L' ordinamento deve essere valorizzato con \"asc\" per ascendente, \"desc\" per discendente");
        }
    }

    /**
     * Processa una richiesta di dati paginati e formatta la risposta.
     *
     * Questo metodo riceve un formatter JSON, una specifica di paginazione e un indicatore booleano per determinare se la richiesta è per dati storici.
     * Utilizza il servizio richiestaService per ottenere i dati corrispondenti alla richiesta e formatta la risposta in un oggetto ResponseEntity contenente i dati paginati.
     *
     * @param jsonFormatter Il formatter JSON utilizzato per formattare i dati
     * @param pageable La specifica di paginazione per la query dei dati
   //  * @param isStorico Un indicatore booleano che specifica se la richiesta è per dati storici
     * @return Un oggetto ResponseEntity contenente i dati formattati e le informazioni sulla paginazione
     */
    private ResponseEntity<JsonFormatterPage<UserDto>> processRequest(JsonFormatter<UserDto> jsonFormatter,
                                                                               Pageable pageable
                                                                               ) {
        JsonFormatterPage<UserDto> formatter = new JsonFormatterPage<>();
        try {
            Page<UserDto> richiestaDTOS = nuovoServiceUser.get(jsonFormatter, pageable);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Pagina n: ", String.valueOf(pageable.getPageNumber()));
            httpHeaders.add("Elementi per pagina: ", String.valueOf(pageable.getPageSize()));
            formatter.setFiltri(jsonFormatter.getFiltri());
            formatter.setElenco(richiestaDTOS);
            return ResponseEntity.status(HttpStatus.OK)
                    .headers(httpHeaders)
                    .body(formatter);
        } catch (EntityNotFoundException e) {
            formatter.setErroreDTO(new ErroreDto(ErrorEnum.ENTITA_NON_TROVATA, e));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(formatter);
        } catch (Exception e) {
            formatter.setErroreDTO(new ErroreDto(ErrorEnum.ERRORE, e));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(formatter);
        }
    }



}

