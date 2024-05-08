package com.esercizio.utente.nuovoService.impl;

import com.esercizio.utente.dto.RuoliDto;
import com.esercizio.utente.dto.UserDto;
import com.esercizio.utente.errori.ConversionFailedException;
import com.esercizio.utente.filtri.JsonFormatter;
import com.esercizio.utente.filtri.JsonFormatterPage;
import com.esercizio.utente.model.Ruoli;
import com.esercizio.utente.model.User;
import com.esercizio.utente.nuovoService.NuovoServiceUser;
import com.esercizio.utente.repository.RuoliRepository;
import com.esercizio.utente.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.Date;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class NuovoServiceUserImpl implements NuovoServiceUser {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RuoliRepository ruoliRepository;


    private String parametroId = "id";
    private String parametroName = "name";
    private String parametroSurname = "surname";

    private String parametroEmail = "email";
    private String parametroPassword = "password";
    private String parametroRuoloId = "ruoloId";

    @Override
    public UserDto create(JsonFormatter<UserDto> jsonFormatter) throws ConversionFailedException {
        List<Map<String, Object>> elenco = (List<Map<String, Object>>) jsonFormatter.getElenco();
        UserDto userDto = estrapolaElenco(elenco);

        try {
            User user = new User();
            user.setName(userDto.getName());
            user.setSurname(userDto.getSurname());
            user.setEmail(userDto.getEmail());
            user.setPassword(userDto.getPassword());
            user.setRuoloId(ruoliRepository.findById(userDto.getRuolo().getRuoloId()).
                    orElseThrow(() -> new EntityNotFoundException("Ruolo non trovato tramite id: "
                            + userDto.getRuolo().getRuoloId())));
            userRepository.save(user);

        } catch (Exception e) {
            throw new EntityExistsException(e.getMessage());
        }

        return userDto;
    }




    @Override
    public Page<UserDto> get(JsonFormatter<UserDto> jsonFormatter, Pageable pageable) {
        Specification<User> specification = ricercaPerCriteri(jsonFormatter.getFiltri());
        String name = String.valueOf(jsonFormatter.getFiltri().getName());
        System.out.println("name: " + name);
        Page<User> richiestaList = userRepository.findAll(specification, pageable);
        System.out.println(richiestaList.getContent() + "content");

        try {
            List<UserDto> userDTOList = richiestaList.stream().map(this::userToDto).toList();
            return new PageImpl<>(userDTOList, pageable, richiestaList.getTotalElements());
        } catch (Exception e) {
            throw new EntityNotFoundException(e.getMessage());
        }
    }



    @Override
    public UserDto edit(JsonFormatter<UserDto> jsonFormatter) {
        //Specification<User> specification = ricercaPerCriteri(jsonFormatter.getFiltri());
        List<Map<String, Object>> elenco = (List<Map<String, Object>>) jsonFormatter.getElenco();
        UserDto userDto = estrapolaElenco(elenco);
        Integer userId = jsonFormatter.getFiltri().getUserId(); // Ottieni l'ID dell'utente da modificare

        System.out.println(elenco + "elenco");

        try {
            System.out.println(userDto.getUserId() + "userId");
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("User non trovato tramite id: " + userId));

            // Aggiorna solo i campi non nulli nel DTO
            if (userDto.getName() != null) {
                user.setName(userDto.getName());
            }
            if (userDto.getSurname() != null) {
                user.setSurname(userDto.getSurname());
            }
            if (userDto.getEmail() != null) {
                user.setEmail(userDto.getEmail());
            }
            if (userDto.getPassword() != null) {
                user.setPassword(userDto.getPassword());
            }
            if (userDto.getRuolo() != null && userDto.getRuolo().getRuoloId() != null) {
                user.setRuoloId(ruoliRepository.findById(userDto.getRuolo().getRuoloId())
                        .orElseThrow(() -> new EntityNotFoundException("Ruolo non trovato tramite id: "
                                + userDto.getRuolo().getRuoloId())));
            }
            System.out.println(user+ "user");




            userRepository.save(user);

        } catch (Exception e) {
            throw new EntityExistsException(e.getMessage());
        }

        return userDto;
    }



    //--------------------------FINE METODI CONTROLLER---------------------------------------------------------------------

    /**
     * Converte un'istanza di User in un'istanza di UserDTO.
     *
     * @param user l'istanza di User da convertire
     * @return un'istanza di UserDTO corrispondente all'istanza di User fornita
     * @throws ConversionFailedException se user è null
     */
    //@SneakyThrows
    //private UserDto userToDto(User user) throws ConversionFailedException {
    //  if (user != null) {
    //      return new UserDto(user);
    //  } else throw new ConversionFailedException("errore");
    //}

    /**
     * Costruisce una specitication (SELECT) dinamica per la ricerca di User nel database basata sui criteri forniti.
     *
     * @param parametri l'oggetto contenente i criteri di ricerca per User
     * @return una Specitication per la ricerca di User basata sui criteri forniti
     */
    private Specification<User> ricercaPerCriteri(UserDto parametri) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Estrarre i valori dai parametri di ricerca e aggiungere i predicati corrispondenti
            Integer id = parametri.getUserId();
            if (id != null) {
                predicates.add(criteriaBuilder.equal(root.get(parametroId), id));
            }

            String name = parametri.getName();
            if (name != null) {
                predicates.add(criteriaBuilder.equal(root.get(parametroName), name));
                System.out.println(name + "name");
            }

            String surname = parametri.getSurname();
            if (surname != null) {
                predicates.add(criteriaBuilder.equal(root.get(parametroSurname), surname));
            }


            String email = parametri.getEmail();
            if (email != null) {
                predicates.add(criteriaBuilder.equal(root.get(parametroEmail), email));
            }

            String password = parametri.getPassword();
            if (password != null) {
                predicates.add(criteriaBuilder.equal(root.get(parametroPassword), password));
                System.out.println(password + "password");
            }

            addNestedPredicate(parametri.getRuolo(), "ruoloId", root, criteriaBuilder, predicates);// Aggiungi i predicati per il ruolo essendo il campo nidificato




            // Combina i predicati con l'operatore AND
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }


    /**
     * Aggiunge un predicato alla lista dei predicati se il valore del campo nidificato non è nullo.
     * <p>
     * Questo metodo aggiunge un predicato alla lista dei predicati solo se il valore del campo nidificato non è nullo.
     * Utilizza la riflessione per accedere al campo nidificato, in base al nome del campo fornito, e verifica se il valore non è nullo.
     * Se il valore non è nullo, crea un predicato di uguaglianza utilizzando il campo nidificato e il valore estratto.
     * Il predicato viene quindi aggiunto alla lista dei predicati fornita.
     *
     * @param value           il valore del campo nidificato
     * @param nestedFieldName il nome del campo nidificato
     * @param root            il root del tipo di entità
     * @param criteriaBuilder il criteriaBuilder utilizzato per costruire il predicato
     * @param predicates      la lista dei predicati a cui aggiungere il nuovo predicato
     * @param <T>             il tipo del valore del campo nidificato
     */
    private <T> void addNestedPredicate(T value, String nestedFieldName, Root<User> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (value != null) {
            try {
                // Recupera il campo nidificato tramite reflection
                Object nestedFieldValue = value.getClass().getMethod("get" + Character.toUpperCase(nestedFieldName.charAt(0)) + nestedFieldName.substring(1)).invoke(value);
                if (nestedFieldValue != null) {
                    predicates.add(criteriaBuilder.equal(root.get(nestedFieldName), nestedFieldValue));
                }
            } catch (Exception e) {
                // Gestione dell'eccezione
                e.printStackTrace();
            }
        }
    }

    /**
     * Estrae una UserDTO dall'elenco di mappe fornito.
     *
     * @param elenco l'elenco di mappe da cui estrarre la UserDTO
     * @return la RichiestaDTO estratta dall'elenco
     * @throws IllegalArgumentException se l'elenco è vuoto
     */
    private UserDto estrapolaElenco(List<Map<String, Object>> elenco) {
        if (elenco.isEmpty()) {
            throw new IllegalArgumentException("Elenco non presente nel JSON");
        }
        Map<String, Object> map = elenco.get(0);
        return buildUserDTO(map);
    }

    /**
     * Costruisce un oggetto UserDTO utilizzando i valori dalla mappa fornita.
     *
     * @param map la mappa contenente i valori per costruire la RichiestaDTO
     * @return il RichiestaDTO costruito dalla mappa
     */
    private UserDto buildUserDTO(Map<String, Object> map) {
        Integer id = getIntValue(map, "id");

        String name = getStringValue(map, "name");
        String surname = getStringValue(map, "surname");
        String email = getStringValue(map, "email");
        String password = getStringValue(map, "password");

        RuoliDto ruoliDto = getDTOValue(map, "ruolo", "ruoloId", RuoliDto.class);

        return new UserDto(id, name, surname, email, password, ruoliDto);
    }

    /**
     * Estrae e restituisce un valore Integer dalla mappa utilizzando la chiave specificata.
     *
     * @param map la mappa dalla quale estrarre il valore
     * @param key la chiave associata al valore da estrarre
     * @return il valore Integer estratto dalla mappa, o null se la chiave non esiste nella mappa o il valore non è di tipo Integer
     */
    private Integer getIntValue(Map<String, Object> map, String key) {
        return (Integer) map.get(key);
    }

    /**
     * Restituisce il valore associato alla chiave specificata nella mappa come una stringa.
     * Questo metodo recupera il valore associato alla chiave specificata nella mappa fornita e lo converte in una stringa.
     * Se la chiave non è presente nella mappa o il valore associato è null, viene restituito null.
     *
     * @param map la mappa da cui recuperare il valore
     * @param key la chiave associata al valore da recuperare
     * @return il valore associato alla chiave come una stringa, o null se la chiave non è presente nella mappa o il valore è null
     */
    private String getStringValue(Map<String, Object> map, String key) {
        return (String) map.get(key);
    }

    /**
     * Restituisce la data associata alla chiave specificata nella mappa.
     * Questo metodo recupera il valore associato alla chiave specificata nella mappa fornita, lo converte in una data e lo restituisce.
     * Se la chiave non è presente nella mappa o il valore associato è null, restituisce null.
     *
     * @param map la mappa da cui recuperare il valore
     * @param key la chiave associata alla data da recuperare
     * @return la data associata alla chiave, o null se la chiave non è presente nella mappa o il valore è null
     * @throws IllegalArgumentException se il valore associato alla chiave non può essere convertito in una data
     */
    private Date getDateValue(Map<String, Object> map, String key) {
        String stringValue = getStringValue(map, key);
        return stringValue != null ? Date.valueOf(stringValue) : null;
    }

    /**
     * Verifica che la data di stima finale sia successiva o uguale alla data di creazione.
     * Questo metodo verifica se la data di stima finale è antecedente alla data di creazione.
     * Se entrambe le date sono non null e la data di stima finale è antecedente alla data di creazione,
     * viene lanciata un'eccezione DateTimeException con un messaggio appropriato.
     *
     * @param dataCreazione   la data di creazione da verificare
     * @param dataStimaFinale la data di stima finale da verificare
     * @throws DateTimeException se la data di stima finale è antecedente alla data di creazione
     */
    private void validateDateOrder(Date dataCreazione, Date dataStimaFinale) {
        if (dataCreazione != null && dataStimaFinale != null && dataStimaFinale.before(dataCreazione)) {
            throw new DateTimeException("Data di stima finale non può essere antecedente alla data di creazione");
        }
    }

    /**
     * Ottiene il valore Double associato alla chiave specificata nella mappa.
     * <p>
     * Questo metodo recupera il valore corrispondente alla chiave specificata dalla mappa e lo converte in un valore Double.
     * Se il valore ottenuto è nullo o non può essere convertito in un Double, viene restituito null.
     *
     * @param map la mappa da cui recuperare il valore
     * @param key la chiave associata al valore nella mappa
     * @return il valore Double associato alla chiave, o null se il valore non esiste o non è un Double valido
     */
    private Double getDoubleValue(Map<String, Object> map, String key) {
        String stringValue = getStringValue(map, key);
        return stringValue != null ? Double.parseDouble(stringValue) : null;
    }

    /**
     * Ottiene un oggetto DTO corrispondente alla chiave nidificata specificata nella mappa.
     * <p>
     * Questo metodo recupera un oggetto DTO dalla mappa fornita, utilizzando una chiave nidificata
     * per accedere ai valori pertinenti. Se la mappa non contiene la chiave nidificata o se il valore
     * associato alla chiave nidificata è nullo, il metodo restituirà null.
     *
     * @param map         la mappa da cui recuperare l'oggetto DTO nidificato
     * @param nestedKey   la chiave nidificata nella mappa che contiene l'oggetto DTO
     * @param nestedIdKey la chiave all'interno dell'oggetto nidificato che contiene l'identificatore necessario per costruire l'oggetto DTO
     * @param dtoClass    la classe dell'oggetto DTO
     * @param <T>         il tipo dell'oggetto DTO
     * @return l'oggetto DTO corrispondente alla chiave nidificata, o null se la chiave nidificata non esiste o l'oggetto DTO non può essere costruito
     */
    private <T> T getDTOValue(Map<String, Object> map, String nestedKey, String nestedIdKey, Class<T> dtoClass) {
        Map<String, Object> nestedMap = (Map<String, Object>) map.get(nestedKey);
        if (nestedMap == null) {
            return null;
        }
        Integer nestedId = (Integer) nestedMap.get(nestedIdKey);
        if (nestedId == null) {
            return null;
        }
        try {
            return dtoClass.getDeclaredConstructor(Integer.class).newInstance(nestedId);
        } catch (Exception e) {
            e.printStackTrace(); // gestione dell'eccezione
            return null;
        }
    }

    /**
     * Converte un oggetto di tipo User in un oggetto di tipo UserDTO.
     *
     * @param user l'oggetto user da convertire
     * @return un oggetto RichiestaDTO corrispondente all'oggetto user fornito
     * @throws IllegalArgumentException  se user è null
     * @throws ConversionFailedException se la conversione dell'entità user in userDTO non riesce
     */
    @SneakyThrows
    private UserDto userToDto(User user) {
        if (user != null) {

            return new UserDto(user.getId(),
                    user.getName(),
                    user.getSurname(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getRuoloId() != null ? new RuoliDto(user.getRuoloId()) : null



            );
        } else
            throw new ConversionFailedException("Conversione dell'entità RichiestaStorico in RichiestaDTO non riuscita");
    }
}
