package com.esercizio.utente.repository;

import com.esercizio.utente.model.Ruoli;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RuoliRepository extends JpaRepository<Ruoli, Integer>, JpaSpecificationExecutor<Ruoli> {


}
