package com.esercizio.utente.dto;

import com.esercizio.utente.model.Ruoli;
import com.esercizio.utente.model.User;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

public class UserDto {

    private Integer userId;
    private String name;
    private String surname;
    private String email;
    private String password;
     private RuoliDto ruolo;



 /*  public UserDto(User user) {
        this.userId = user.getId();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.ruolo = user.getRuoloId() != null ? new RuoliDto(user.getRuoloId()) : null;
    }*/


 //  public UserDto(Integer id) {
   //    this.userId = id;
    //}


    public UserDto(Integer id, String name, String email, String password, RuoliDto ruolo) {
        this.userId = id;
        this.name = name;
        this.email = email;
        this.password = password;
      this.ruolo =ruolo ;
    }

   // public UserDto(Integer id, String name, String email, String password) {
   // }
}
