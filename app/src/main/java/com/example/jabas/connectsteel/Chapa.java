package com.example.jabas.connectsteel;

import java.util.ArrayList;

public class Chapa {

    private String name;
    private ArrayList<Coluna> colunas;
    private float b;
    private float t;
    String material;
    String furacao;
    boolean protecao;
    double h, fu, fy;
    int nFurosExt;
    int nFurosInt;
    double rd;
    private ArrayList<Double>distP;

    //FAZER CALCULO DOS SBX!!! Vetor de floats - nao sera mais considerado
    public Chapa(String name, float b, float t, double h, String material, String furacao, boolean protecao,double fu ,double fy) {
        this.name = name;
        this.colunas = new ArrayList<Coluna>();
        this.b = b;
        this.t=t;
        this.h = h;
        this.material = material;
        this.furacao = furacao;
        this.distP= new ArrayList<Double>();
        this.protecao = protecao;
        this.fu = fu;
        this.fy = fy;

    }
    /*
    private void calculaFu(){//espelha o calculo no parafuso
        switch (material){
            case 0://MR250
                fu = 40.0;
                break;
            case 1://AR350
                fu = 45.0;
                break;
            case 2://AR415
                fu = 52.0;
                break;
            case 3://CG26
                fu = 41.0;
                break;
            case 4://CG28
                fu = 44.0;
                break;
        }
    }

    private void calculaFy() {
        switch (material){
            case 0://MR250
                fy = 25.0;
                break;
            case 1://AR350
                fy = 35.0;
                break;
            case 2://AR415
                fy = 41.5;
                break;
            case 3://CG26
                fy = 25.5;
                break;
            case 4://CG28
                fy = 27.5;
                break;
        }
    }

    public boolean isFuracao() {
        return furacao;
    }*/

    public String getName() {
        return name;
    }

    public ArrayList<Coluna> getColunas() {
        return colunas;
    }

    public int getNumeroParafusos(){
        int n = 0;
        for(int i = 0; i < this.getColunas().size(); i++){
            n += this.getColunas().get(i).getParafusos().size();
        }
        return n;
    }

    public void setPontoBorda(){
        for(int i = 0; i < this.getColunas().size(); i++){
            this.getColunas().get(i).getParafusos().add(new ParafusoComum(this.getColunas().get(i).getX(), 0, this.getColunas().get(i).getName() + 0, 20,false,false,0,0,0,0));
            this.getColunas().get(i).getParafusos().add(new ParafusoComum(this.getColunas().get(i).getX(), this.b,this.getColunas().get(i).getName() + "FIM", 20, false, false,0,0,0,0));
        }
    }

    public void print(){
        for(int i = 0; i < this.getColunas().size(); i++){
            for(int j = 0; j < this.getColunas().get(i).getParafusos().size(); j++){
                System.out.println(this.getName() + " "
                        + this.getColunas().get(i).getParafusos().get(j).getX() + " "
                        + this.getColunas().get(i).getParafusos().get(j).getY() + " "
                        + this.getColunas().get(i).getParafusos().get(j).getLabel());
            }
            System.out.println();
        }
    }

    public int getPosInGraph(Parafuso p){
        int pos = 0;

        for(int i = 0; i < this.getColunas().size(); i++){
            for(int j = 0; j < this.getColunas().get(i).getParafusos().size(); j++){
                if(this.getColunas().get(i).getParafusos().get(j).getLabel().equals(p.getLabel())){
                    System.out.println(p.getLabel());
                    return pos;
                }
                pos++;
            }
        }
        return 0;
    }

    public double pressaodeApoioeRasgamento( double ya2) {//refazer SXB VETOR DE FLOATS

        double db = colunas.get(0).getParafusos().get(0).d;

        double rdtExt;
        double rdtInt;
       // sInt = sInt / 10;
        db = db / 10;
        t = t / 10;

        /* Rasgamento para parafusos externos */
        if (((1.2 * fu * (getColunas().get(0).getX()-(db/2))* t) / ya2) <= ((2.4 * fu * db * t) / ya2)) {
            rdtExt = ((1.2 * fu * (getColunas().get(0).getX()-(db/2))* t) / ya2);
            System.out.println("Rd parafuso Ext = " + String.format("%.2f", rdtExt) + "KN");
        } else {
            rdtExt = ((2.4 * fu * db * t) / ya2);
            System.out.println("Rd parafuso Ext = " + String.format("%.2f", rdtExt) + "KN");
        }
/* Pressao de apoio para parafusos internos - Desconsiderando a possibilidade de rasgamento para parafusos internos. Deve-se limitar entao a distancia entre as colunas ser sempre maior que a distancia da coluna 0 para borda */
        /*for (int i=0; i < getColunas().size()-2; i++){
            distP.add(1.2*fu *(getColunas().get(i+1).getX() - getColunas().get(i).getX())*t/ya2) ;
            if (((1.2 * fu * sInt * t) / ya2) <= ((2.4 * fu * db * t) / ya2)) {
            rdtInt = ((1.2 * fu * sInt * t) / ya2);
            System.out.println("Rd parafusos internos = " + String.format("%.2f", rdtInt) + "KN");*/
        rdtInt = ((2.4 * fu * db * t) / ya2);
        System.out.println("Rd parafusos internos = " + String.format("%.2f", rdtInt) + "KN");
        this.rd =  getColunas().get(0).getParafusos().size()* rdtExt + (getNumeroParafusos()- getColunas().get(0).getParafusos().size()) * rdtInt;
        return rd;

    }

    public void statusChapa() {
        System.out.println("Resistencia de pressao de apoio do fuste na chapa e rasgamento da chapa calculada = " + String.format("%.2f", rd) + "KN");
    }

    public ArrayList<String> ToString(){
        ArrayList<String> output = new ArrayList<String>();

        output.add(new String("Chapa: " + this.name));
        for(int i = 0; i < this.getColunas().size(); i++){
            output.add(new String("Coluna: " + this.getColunas().get(i).getName()));
            for(int j = 0; j < this.getColunas().get(i).getParafusos().size(); j++){
                output.add(new String( "Paafuso: " + this.getColunas().get(i).getParafusos().get(j).getX() + " " + this.getColunas().get(i).getParafusos().get(j).getY()));
            }
        }
        return output;
    }


    public float getB() {
        return b;
    }
    public float getT() {
        return t;
    }
}

