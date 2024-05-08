package com.esercizio.utente.dto;

import com.esercizio.utente.model.Ruoli;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
public class RuoliDto {

    private Integer ruoloId;
    private String descRuolo;


    public RuoliDto(Ruoli ruolo) {
        this.ruoloId = ruolo.getId();
        this.descRuolo = ruolo.getDescrizione();
    }


    public RuoliDto(Integer id) {
        this.ruoloId = id;
    }


    public Integer getRuoloId() {
        return ruoloId;
    }

    public void setRuoloId(Integer ruoloId) {
        this.ruoloId = ruoloId;
    }

    public String getDescRuolo() {
        return descRuolo;
    }

    public void setDescRuolo(String descRuolo) {
        this.descRuolo = descRuolo;
    }
}
