package com.example.jabas.connectsteel;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ImageButton nextpage1;
    private ImageView iv_ifmalogo;
    private TextView tv_about;
    private ImageButton ib_info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nextpage1=(ImageButton) findViewById(R.id.nextpagebutton1);
        nextpage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMaterial();

            }
    });

        ImageButton btinfo = (ImageButton) findViewById(R.id.ib_info);
        btinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                 View mView = getLayoutInflater().inflate(R.layout.aboutifma, null);
                 iv_ifmalogo = mView.findViewById(R.id.iv_ifmalogo);
                 tv_about =  mView.findViewById(R.id.tv_about);
                 mBuilder.setView(mView);
                 final AlertDialog dialog = mBuilder.create();
                 dialog.show();

            }
        });
    }


    public  void openMaterial (){
        Intent intent = new Intent(this,material.class);
        startActivity(intent);
    }

}



