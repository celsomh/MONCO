package com.iot.monco;

import java.util.List;

public class Card {
    String titulo;
    String strCuerpo;
    private String id;

    public Card(String id, String titulo, String strCuerpo) {
        this.titulo = titulo;
        this.strCuerpo = strCuerpo;
        this.id = id;
    }

    public void setCuerpo(String strCuerpo) {
        this.strCuerpo = strCuerpo;

    }

    public String getId() {
        return id;
    }


}
