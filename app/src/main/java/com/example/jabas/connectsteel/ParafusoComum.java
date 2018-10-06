package com.example.jabas.connectsteel;

import android.util.Log;

public class ParafusoComum extends Parafuso {


    public ParafusoComum(float x, float y, String label, float d, boolean tipo, boolean material, double l, double furacao,double fu, double fy){
        this.x = x;
        this.y = y;
        this.label = label;
        this.d = d;
        this.tipo = tipo;
        this.fu = fu;
        this.fy = fy;
        this.material = material;
        this.l = l;
        this.furacao = furacao;
        this.dfuro = dfuro;
        /*calculaFu(tipo, material);
        calculaFy(tipo, material);*/
        areaDoParafuso();
        calculaDfuro();
    }
    public double calculoRnv(double ya2, int nplanosdeCorte, int nParafusos) {
        Log.d("oola","esse e o an " +Double.toString(ya2)+" "+Double.toString(area)+" "+Double.toString(nplanosdeCorte)+" "+Double.toString(nParafusos)+" "+Double.toString(fu));

        this.ya2 = ya2;
        Rnv = 0.4 * this.area * this.fu;
        Rd = (Rnv * nplanosdeCorte * nParafusos) / this.ya2;
        if (l > 5*d) {
            int taxadereducao = (int)Math.floor((l-5*d)/1.5);
            Rd = this.Rd * (1 - (l-5*d) / 100);
            return Rd;
        } else {
            return Rd;
        }
    }
}
