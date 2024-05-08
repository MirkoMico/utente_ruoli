package com.esercizio.utente.repository;

import com.esercizio.utente.model.Ruoli;
import com.esercizio.utente.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {



    //Optional<User> findByRuoloIdId(Long ruoloId);

 //   @Query("SELECT u FROM User u WHERE u.ruoloId.ruoloId = :ruoloId")
  //  Optional<User> findByRuoloId(Long ruoloId);

  //  @Query("SELECT u FROM User u WHERE u.ruoloId.ruoloId = :ruoloId")
  //  Optional<List<User>> findByRuoloId( Long ruoloId);
}
