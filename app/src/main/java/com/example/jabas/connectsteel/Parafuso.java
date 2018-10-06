package com.example.jabas.connectsteel;

import android.content.Intent;

public abstract class Parafuso {
    protected float x;
    protected float y;
    protected String label;
    protected float d;
    protected boolean tipo; /* Parafuso comum ou alta resistencia */
    protected double fu; /* padrao de acordo com a esocolha de ASTM */
    protected double fy;  /* padrao de acordo com a esocolha de ASTM */
    protected boolean material; /* ASTM OU A - ESSA LISTA VAI SURGIR EM DROPDOWN DE ACORDO COM A ESCOLHA DO TIPO */
    protected double l; /*Disponivel de acordo com o tipo */
    protected double furacao; /* profundidade de furacao */
    protected double dfuro; /* diametro d + valor da furacao */
    protected double area;
    protected double Rnv,Rd,ya2;



   /* protected void calculaFu(boolean comum, boolean A325) {
        if(comum){//comum
            fu = 41.5;
        }else{//alta resistencia
            if(A325){
                if(d>25.4) {
                    fu = 72.5;
                }else{
                    fu = 82.5;
                }
            }else{//A490
                fu = 103.5;
            }
        }
    }

    protected void calculaFy(boolean comum, boolean A325) {
        if(comum){
            fy = 40;//ALTERAR PARA VALOR REAL
        }else{
            if(A325){
                if(d<25.4) {
                    fy = 63.5;
                }else{
                    fy = 56.0;
                }
            }else{
                fy = 89.5;
            }
        }
    }*/

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public String getLabel() {
        return label;
    }

    public float getD() {
        return d;
    }

    public double getDfuro(){
        return dfuro;
    }
    public double getArea(){
        return area;
    }


    protected void areaDoParafuso() {
        this.area = (Math.PI * Math.pow(d / 10, 2)) / 4;
    }

    protected void calculaDfuro(){
        double df = 0;
        df = furacao + d;
        dfuro = df;
    }

    public boolean verificarMomentoNoConector() {
        boolean longo;
        double taxaReducao;
        if (l >= 5 * d) {
            longo = true;
            taxaReducao = Math.floor((l - (5 * d)) / 1.5);
        } else {
            longo = false;
        }
        return (longo); /* verifica se o parafuso e longo (sofre flexao) */
    }

    public abstract double calculoRnv(double ya2, int nplanosdeCorte, int nParafusos);





}

