package com.esercizio.utente.service;

import com.esercizio.utente.dto.UserDto;
import com.esercizio.utente.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(UserDto userdto);
    List<User> getAllUsers();

    User updateUser(UserDto userdto, Long userId);
    void deleteUser(Long userId);


 Optional<List<User>> getUserByRuoliId(Long ruoloId) throws Exception;
}
