package com.iot.monco;

public class Card {
    String titulo;
    int cuerpo;
    String strCuerpo;

    public Card(String titulo, int cuerpo) {
        this.titulo = titulo;
        this.cuerpo = cuerpo;
    }

    public Card(String titulo, String strCuerpo) {
        this.titulo = titulo;
        this.strCuerpo = strCuerpo;
    }
}