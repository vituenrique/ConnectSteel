package com.example.jabas.connectsteel;

import java.util.ArrayList;

public class Coluna {
    private float x;
    private String name;
    private ArrayList<Parafuso> parafusos;

    public Coluna(float x, String name){
        this.x = x;
        this.name = name;
        this.parafusos = new ArrayList<Parafuso>();
    }

    public float getX() {
        return x;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Parafuso> getParafusos() {
        return parafusos;
    }
}
