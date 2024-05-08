package com.esercizio.utente.service.impl;

import com.esercizio.utente.dto.UserDto;
import com.esercizio.utente.model.Ruoli;
import com.esercizio.utente.model.User;
import com.esercizio.utente.repository.RuoliRepository;
import com.esercizio.utente.repository.UserRepository;
import com.esercizio.utente.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/*
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RuoliRepository ruoliRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public User saveUser(UserDto userdto) {
        User user = new User();
        user.setName(userdto.getName());
        user.setSurname(userdto.getSurname());
        user.setEmail(userdto.getEmail());
        user.setPassword(userdto.getPassword());
        Ruoli ruoli = ruoliRepository.findById(userdto.getRuoloId()).orElse(null);
        user.setRuoloId(ruoli);

        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        if (userRepository.findAll().isEmpty()) {
            return null;
        }
        return userRepository.findAll();
    }


    @Override
    public User updateUser(UserDto userdto, Long userId) {
        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            user.setName(userdto.getName());
            user.setSurname(userdto.getSurname());
            user.setEmail(userdto.getEmail());
            user.setPassword(userdto.getPassword());
            user.setRuoloId(ruoliRepository.findById(userdto.getRuoloId()).orElse(null));
        }
            return userRepository.save(user);

    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);

    }

    @Override
    public Optional<List<User>> getUserByRuoliId(Long ruoloId) throws Exception {
        Optional<List<User>> userList = userRepository.findByRuoloId(ruoloId);

        if (userList.isEmpty()) {
            throw new Exception("User not found");
        }
        return userList;
        }
    }*/



