package com.esercizio.utente.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Table(name = "ruoli")
@Entity
@ToString
public class Ruoli {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, name = "RuoloId")
    private Integer id;
    @Column(nullable = false, name ="descRuolo" )
    private String descrizione;


}
