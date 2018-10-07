package com.example.jabas.connectsteel;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Resultados extends AppCompatActivity {

    private ImageView screenshot;

    private double rnvPar, resPressaoApoio, RagTra, RanTra, ResBloco, Sd;

    public TextView rnvParTxt,resPressaoApoioTxt, rdTraTxt, ResBlocoTxt, SdTxt, verificacaoTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);
        screenshot = (ImageView)findViewById(R.id.iv_screenshot);
        Intent intent = getIntent();
        String snapshot = (String) intent.getExtras().getString("snapshotGet");

        rnvPar = intent.getDoubleExtra("rnvPar", 0);
        resPressaoApoio = intent.getDoubleExtra("resPressaoApoio", 0);
        RagTra = intent.getDoubleExtra("RagTra", 0);
        RanTra = intent.getDoubleExtra("RanTra", 0);
        ResBloco = intent.getDoubleExtra("ResBloco", 0);
        Sd = intent.getDoubleExtra("Sd", 0);

        Bitmap img = StringToBitMap(snapshot);
        if(img != null){
            screenshot.setImageBitmap(img);
        }
        ArrayList<Double> values = new ArrayList<Double>();
        values.add(rnvPar);
        values.add(resPressaoApoio);
        values.add(RagTra);
        values.add(RanTra);
        values.add(ResBloco);

        Collections.sort(values);
        values.get(0);

        SdTxt = findViewById(R.id.tv_SdResult);
        ResBlocoTxt = findViewById(R.id.tv_RCisBloco);
        rnvParTxt = findViewById(R.id.tv_RcisConect);
        rdTraTxt = findViewById(R.id.tv_RTracaoChapa);
        resPressaoApoioTxt = findViewById(R.id.tv_RApoioeCorteChapa);
        verificacaoTxt = findViewById(R.id.tv_verificacao);


        SdTxt.setText(String.format("%.2f", Sd) + "KN");
        if(values.get(0) == resPressaoApoio) resPressaoApoioTxt.setTextColor(Color.GREEN);
        resPressaoApoioTxt.setText(String.format("%.2f", resPressaoApoio) + "KN");
        if(values.get(0) == ResBloco) ResBlocoTxt.setTextColor(Color.GREEN);
        ResBlocoTxt.setText(String.format("%.2f", ResBloco) + "KN");
        if(values.get(0) == rnvPar) rnvParTxt.setTextColor(Color.GREEN);
        rnvParTxt.setText(String.format("%.2f", rnvPar) + "KN");

        if(values.get(0) == RagTra || values.get(0) == RanTra) rdTraTxt.setTextColor(Color.GREEN);
        if (RagTra < RanTra){
            rdTraTxt.setText(String.format("%.2f", RagTra) + "KN");
        }else{
            rdTraTxt.setText(String.format("%.2f", RanTra) + "KN");
        }

        if (Sd < values.get(0)){
            verificacaoTxt.setText("OK");
        }else{
            verificacaoTxt.setTextColor(Color.RED);
            verificacaoTxt.setText("Resistência da ligação menor do que a solicitação de calculo. Revise a ligação!");
        }
    }

    public Bitmap StringToBitMap(String image){
        try{
            byte [] encodeByte=Base64.decode(image,Base64.DEFAULT);

            InputStream inputStream  = new ByteArrayInputStream(encodeByte);
            Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }
}
