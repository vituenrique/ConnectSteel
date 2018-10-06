package com.example.jabas.connectsteel;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class material extends AppCompatActivity {
    private double fuPar, fyPar,lPar,fuChapa,fyChapa,bChapa,hChapa,dPar,dfPar,alargamento,tChapa ;
    private boolean isItAntiCorrosao;
    private Button nextpage2;
    private CheckBox cb_AntiCorrosao;
    private boolean Protegido;
    public String[] materialPar = new String[]{"A307", "A325", "A490"};
    public String[] materialChapa = new String[]{"MR250", "AR350", "AR415","CG-26","CG-28"};
    public String[] dParA307list = new String[]{"1/4 pol", "3/8 pol", "1/2 pol","5/8 pol", "3/4 pol", "7/8 pol","1 pol", "1 1/8 pol", "1 1/4 pol","1 3/8 pol", "1 1/2 pol", "1 3/4 pol","2 pol", "2 1/4 pol", "2 1/2 pol","2 3/4 pol", "3 pol"};
    public double[] dParA307= new double[]{6.35,9.53,12.70,15.88,19.05,22.23,25.40,28.58,31.75,34.93,38.10,44.45,50.80,57.15,63.50,69.85,76.20};
    public String[] dParA325eA490list = new String[]{"1/2 pol", "5/8 pol", "16 mm","3/4 pol", "20 mm", "22 mm","7/8 pol", "24 mm", "1 pol","27 mm", "1 1/8 pol", "30 mm","1 1/4 pol", "36 mm", "1 1/2 pol"};
    public double[] dParA325eA490= new double[]{12.70,15.88,16.0,19.05,20.00,22.00,22.23,24.00,25.40,27.00,28.58,30.00,31.75,36.00,38.10};
    public String [] furacaoChapa = new String[] {"Padrão por puncionamento","Alargado"};
    private Spinner sp_materialpar;
    private Spinner sp_diametro;
    private Spinner sp_materialch;
    private Spinner sp_furacao;
    private int positionmaterialPar,positionDiamPar,positionmaterialChapa,positionFuracao;
    private EditText et_fuPar, et_fyPar, et_lPar,et_fuChapa, et_fyChapa,et_bChapa, et_hChapa,et_diamCriado,et_alargamento, et_tChapa;
    private TextView resutados;
    public String[] ListDiam = dParA307list;
    private static final String TAG = "MainActivity";
    private Switch sw_createConect,sw_createChapa;
    private String MaterialXYZ,MaterialChs,FuracaoS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material);

    }


    protected void onResume() {
        super.onResume();
        Intent intent2 = getIntent();


// Inicia as variaveis do parafuso com zero
        positionFuracao=0;
        positionmaterialPar=0;
        positionDiamPar=0;
        positionmaterialChapa=0;
        dPar=0;
        lPar=0;
        fuPar=0;
        fyPar=0;
        Protegido=false;

        SetArrayListSpMaterial();
        SetArrayListFuracao();
        SwitchCreate();
        SetArrayListChapa();
        SwitchCreateChapa();

        nextpage2=(Button) findViewById(R.id.bt_nextpagebutton2);
        nextpage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                RemontaPg();
                if (fuChapa <=0) {
                    Toast.makeText(material.this, "Preencha todos os valores", Toast.LENGTH_SHORT).show();
                } else if(fyChapa<=0) {
                    Toast.makeText(material.this, "Preencha todos os valores", Toast.LENGTH_SHORT).show();
                }
                else if(fuPar <=0){
                    Toast.makeText(material.this, "Preencha todos os valores", Toast.LENGTH_SHORT).show();
                }
                else if(fyPar <=0){
                    Toast.makeText(material.this, "Preencha todos os valores", Toast.LENGTH_SHORT).show();
                }
                else if(bChapa<=0){
                    Toast.makeText(material.this, "Preencha todos os valores", Toast.LENGTH_SHORT).show();
                }
                else  if(hChapa<=0){
                    Toast.makeText(material.this, "Preencha todos os valores", Toast.LENGTH_SHORT).show();
                }
                else if(tChapa<=0){
                    Toast.makeText(material.this, "Preencha todos os valores", Toast.LENGTH_SHORT).show();
                }
                else if(lPar <=0){
                    Toast.makeText(material.this, "Preencha todos os valores", Toast.LENGTH_SHORT).show();
                }
                else if(dPar<=0){
                    Toast.makeText(material.this, "Preencha todos os valores", Toast.LENGTH_SHORT).show();
                }
                else  if(dPar==dfPar){
                    Toast.makeText(material.this, "Preencha todos os valores", Toast.LENGTH_SHORT).show();
                } else {
                    openCarregamento();
                }



            }
        });



    }

    private void RemontaPg (){
        BuscaDeDadosParafuso();
        BuscadadosChapa();
       show();
    }
    private void SwitchCreate (){

        sw_createConect = findViewById(R.id.sw_criarconector);
        sw_createConect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(sw_createConect.isChecked()){

                    et_fuPar.setText("");
                    et_fyPar.setText("");
                    BuscaDeDadosParafuso();

                } else  if(!sw_createConect.isChecked()){
                    sp_materialpar.setVisibility(View.VISIBLE);
                    sp_materialpar.setEnabled(true);
                    sp_diametro.setEnabled(true);
                    sp_diametro.setVisibility(View.VISIBLE);
                    et_diamCriado.setVisibility(View.GONE);
                    SetArrayListSpMaterial();
                }
            }
        });
    }



    private void SwitchCreateChapa (){

        sw_createChapa = findViewById(R.id.sw_criarchapa);
        sw_createChapa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(sw_createChapa.isChecked()){
                    et_fuChapa.setText("");
                    et_fyChapa.setText("");
                    BuscadadosChapa();

                } else if(!sw_createChapa.isChecked()) {
                    sp_materialch.setVisibility(View.VISIBLE);
                    SetArrayListChapa();
                }
            }
        });
    }

    private void BuscaDeDadosParafuso () {

        et_lPar = findViewById(R.id.et_lpar);
        String slPar = et_lPar.getText().toString();
        if (!slPar.isEmpty()) {
            lPar = Double.parseDouble(slPar);
            Log.d(TAG, "comprimento " + lPar);
        } else {
            lPar = 0;
        }

        sw_createConect = findViewById(R.id.sw_criarconector);
        if (sw_createConect.isChecked()) {

            // vai fazer verificar os Edit Text

            et_fuPar =findViewById(R.id.et_fu);
            et_fuPar.setEnabled(true);
            String sfuPar = et_fuPar.getText().toString();
            if(!sfuPar.isEmpty()) {
                fuPar = Double.parseDouble(sfuPar);
            } else {
                fuPar=0;
            }
            et_fyPar =findViewById(R.id.et_fy);
            et_fyPar.setEnabled(true);
            String sfyPar =et_fyPar.getText().toString();
            if(!sfyPar.isEmpty()) {
                fyPar=Double.parseDouble(sfyPar);
            } else {
                fyPar=0;
            }

            // faz desaparecer spinner e aparecer edit text box
            sp_diametro = findViewById(R.id.sp_diâmetro);
            sp_diametro.setVisibility(View.GONE);
            sp_materialpar.setEnabled(false);
            sp_materialpar.setVisibility(View.GONE);


            et_diamCriado = findViewById(R.id.et_diamCriado);
            et_diamCriado.setVisibility(View.VISIBLE);
            et_diamCriado.setEnabled(true);
            String sdPar = et_diamCriado.getText().toString();
            if (!sdPar.isEmpty()) {
                dPar=Double.parseDouble(sdPar);}
            else {
                dPar=0;
            }

        } else if (!sw_createConect.isChecked()){

            //Preenche os valores de fuPar, fyPar de acordo com as positions dos arrays.

            if (positionmaterialPar == 0) {
                dPar = dParA307[positionDiamPar];
                fuPar = 41.5;
                fyPar = 25;
                et_fyPar = findViewById(R.id.et_fy);
                et_fuPar = findViewById(R.id.et_fu);
                et_fuPar.setText(Double.toString(fuPar)+ " KN/cm2");
                et_fuPar.setEnabled(false);
                et_fyPar.setText(Double.toString(fyPar)+ " KN/cm2" );
                et_fyPar.setEnabled(false);
                Log.d(TAG, "diametro e " + dPar);
                Log.d(TAG, "fu e fy " + fuPar + " " + fyPar);


            } else if (positionmaterialPar == 1) {

                dPar = dParA325eA490[positionDiamPar];

                if (dPar < 25.4) {
                    fuPar = 82.5;
                    fyPar = 63.5;
                    et_fyPar = findViewById(R.id.et_fy);
                    et_fuPar = findViewById(R.id.et_fu);
                    et_fuPar.setText(Double.toString(fuPar) + " KN/cm2");
                    et_fuPar.setEnabled(false);
                    et_fyPar.setText(Double.toString(fyPar)+ " KN/cm2");
                    et_fyPar.setEnabled(false);
                } else {
                    fuPar = 72.5;
                    fyPar = 56;
                    et_fyPar = findViewById(R.id.et_fy);
                    et_fuPar = findViewById(R.id.et_fu);
                    et_fuPar.setText(Double.toString(fuPar) + " KN/cm2");
                    et_fuPar.setEnabled(false);
                    et_fyPar.setText(Double.toString(fyPar) + " KN/cm2");
                    et_fyPar.setEnabled(false);
                }
                Log.d(TAG, "diametro e " + dPar);
                Log.d(TAG, "fu e fy " + fuPar + " " + fyPar);
            } else {

                dPar = dParA325eA490[positionDiamPar];
                fuPar = 103.5;
                fyPar = 89.5;
                et_fyPar = findViewById(R.id.et_fy);
                et_fuPar = findViewById(R.id.et_fu);
                et_fuPar.setText(Double.toString(fuPar) + " KN/cm2");
                et_fuPar.setEnabled(false);
                et_fyPar.setText(Double.toString(fyPar) + " KN/cm2");
                et_fyPar.setEnabled(false);
                Log.d(TAG, "diametro e " + dPar);
                Log.d(TAG, "fu e fy " + fuPar + " " + fyPar);
            }

    }

        // analisa o tipo de furacao e faz acrecimo ao diametro
        if (positionFuracao == 0) {
            dfPar = dPar + 3.5;
            et_alargamento = findViewById(R.id.et_alargamento);
            et_alargamento.setVisibility(View.GONE);

        } else {
            et_alargamento = findViewById(R.id.et_alargamento);
            et_alargamento.setVisibility(View.VISIBLE);
            String sAlargamento = et_alargamento.getText().toString();
            if (!sAlargamento.isEmpty()) {
                alargamento = Double.parseDouble(sAlargamento);
            } else {
                alargamento = 0;
            }

            dfPar = dPar + alargamento;
        }


        }


  /*  }
*/
    private void SetArrayListSpMaterial () {
        // Na montagem e se nao for clicado no Spinner
        positionmaterialPar=0;
        ListDiam=dParA307list;
        SetArrayListSpdiametro();

        // Adapter do Spinner Material
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, materialPar);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_materialpar = findViewById(R.id.sp_materialpar);
        sp_materialpar.setAdapter(adapter);
        sp_materialpar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {

                if( sp_materialpar.getSelectedItemPosition() == 0) {
                    ListDiam = dParA307list;
                    positionmaterialPar=sp_materialpar.getSelectedItemPosition();
                    SetArrayListSpdiametro();


                    Log.d(TAG, "cheguei pelo 0  " + sp_materialpar.getSelectedItemPosition());
                }
                else if (  sp_materialpar.getSelectedItemPosition() == 1) {
                    ListDiam = dParA325eA490list;
                    positionmaterialPar=sp_materialpar.getSelectedItemPosition();
                    SetArrayListSpdiametro();

                    Log.d(TAG, "cheguei pelo 1  " + sp_materialpar.getSelectedItemPosition());
                }
                else {
                    ListDiam =dParA325eA490list;
                    positionmaterialPar=sp_materialpar.getSelectedItemPosition();
                    SetArrayListSpdiametro();

                    Log.d(TAG, "cheguei pelo 2  " + sp_materialpar.getSelectedItemPosition());
                }

                MaterialXYZ = materialPar [sp_materialpar.getSelectedItemPosition()];


                Log.d(TAG, "selecao de material " + sp_materialpar.getSelectedItem());
                Log.d(TAG, "positionmaterialpar saindo do metodo " + positionmaterialPar);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ListDiam = dParA307list;
                positionmaterialPar = sp_materialpar.getSelectedItemPosition();
                SetArrayListSpdiametro();

                Log.d(TAG, "cheguei pelo nada selecionado  " + positionmaterialPar);

            }
        });

   }

    private void SetArrayListSpdiametro () {
        // Adapter do Spinner diametro
        //Na montagem e se nao for clicado no Spinner
        positionDiamPar=0;
        BuscaDeDadosParafuso();


        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, ListDiam);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_diametro = findViewById(R.id.sp_diâmetro);
        sp_diametro.setAdapter(adapter1);
        sp_diametro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                positionDiamPar = sp_diametro.getSelectedItemPosition();
                BuscaDeDadosParafuso();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                positionDiamPar=0;
                BuscaDeDadosParafuso();

                Log.d(TAG, "aqui e a posicao do diametro sem selecao " + sp_diametro.getSelectedItemPosition());

            }
        });

    }

    private void CheckboxAntiCorrosao () {
        cb_AntiCorrosao=findViewById(R.id.checkBox);
        cb_AntiCorrosao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               BuscadadosChapa();
            }
        });

    }

    private void SetArrayListChapa () {
        // Adapter do Spinner material chapa
        BuscadadosChapa();

        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,materialChapa);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_materialch = findViewById(R.id.sp_materialch);
        sp_materialch.setAdapter(adapter4);
        sp_materialch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                positionmaterialChapa = sp_materialch.getSelectedItemPosition();

                BuscadadosChapa();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                positionmaterialChapa=0;
                BuscadadosChapa();
            }
        });

        MaterialChs = materialChapa[sp_materialch.getSelectedItemPosition()];

        Log.d(TAG, "selecao de material " + sp_materialch.getSelectedItem());
    }

    private void BuscadadosChapa (){
        // determina B da chapa
        et_bChapa = findViewById(R.id.et_bchapa);
        String sBchapa = et_bChapa.getText().toString();
        if (!sBchapa.isEmpty()) {
            bChapa=Double.parseDouble(sBchapa);
        } else {
            bChapa=0;  //voltar pra 0
        }
        // determina H da chapa
        et_hChapa = findViewById(R.id.et_hchapa);
        String sHchapa = et_hChapa.getText().toString();
        if (!sHchapa.isEmpty()) {
            hChapa=Double.parseDouble(sHchapa);
        } else {
            hChapa=0;  //voltar pra 0
        }

        // determina T da chapa
        et_tChapa = findViewById(R.id.et_tchapa);
        String sTchapa = et_tChapa.getText().toString();
        if (!sTchapa.isEmpty()) {
            tChapa=Double.parseDouble(sTchapa);
        } else {
            tChapa=0;  //voltar pra 0
        }
        // VERIFICAR SWITCH E FAZ COMO SEGUE
        sw_createChapa = findViewById(R.id.sw_criarchapa);
        if(sw_createChapa.isChecked()){
            sp_materialch.setVisibility(View.GONE);
            et_fuChapa =findViewById(R.id.et_fuchapa);
            et_fuChapa.setEnabled(true);
            String sfuChapa = et_fuChapa.getText().toString();
            if(!sfuChapa.isEmpty()) {
                fuChapa = Double.parseDouble(sfuChapa);
            } else {
                fuChapa=0;}

            et_fyChapa =findViewById(R.id.et_fychapa);
            et_fyChapa.setEnabled(true);
            String sfyChapa = et_fyChapa.getText().toString();
            if(!sfyChapa.isEmpty()) {
                fyChapa = Double.parseDouble(sfyChapa);
            } else {
                fyChapa=0;}
        } else if(!sw_createChapa.isChecked()) {

            if (positionmaterialChapa==0){
                fuChapa=40.0;
                fyChapa=25.0;
                et_fyChapa =findViewById(R.id.et_fychapa);
                et_fuChapa=findViewById(R.id.et_fuchapa);
                et_fuChapa.setText(Double.toString(fuChapa)+" KN/cm2");
                et_fuChapa.setEnabled(false);
                et_fyChapa.setText(Double.toString(fyChapa)+" KN/cm2");
                et_fyChapa.setEnabled(false);
            } else if (positionmaterialChapa ==1) {
                fuChapa=45.0;
                fyChapa=35.0;
                et_fuChapa=findViewById(R.id.et_fuchapa);
                et_fuChapa.setText(Double.toString(fuChapa)+" KN/cm2");
                et_fuChapa.setEnabled(false);
                et_fyChapa.setText(Double.toString(fyChapa)+" KN/cm2");
                et_fyChapa.setEnabled(false);
            }else if (positionmaterialChapa ==2) {
                fuChapa=52.0;
                fyChapa=41.5;
                et_fuChapa=findViewById(R.id.et_fuchapa);
                et_fuChapa.setText(Double.toString(fuChapa)+" KN/cm2");
                et_fuChapa.setEnabled(false);
                et_fyChapa.setText(Double.toString(fyChapa)+" KN/cm2");
                et_fyChapa.setEnabled(false);
            }else if (positionmaterialChapa ==3) {
                fuChapa = 41.0;
                fyChapa = 25.5;
                et_fuChapa=findViewById(R.id.et_fuchapa);
                et_fuChapa.setText(Double.toString(fuChapa)+" KN/cm2");
                et_fuChapa.setEnabled(false);
                et_fyChapa.setText(Double.toString(fyChapa)+" KN/cm2");
                et_fyChapa.setEnabled(false);
            } else {
                fuChapa=44;
                fyChapa=27.5;
                et_fuChapa=findViewById(R.id.et_fuchapa);
                et_fuChapa.setText(Double.toString(fuChapa)+" KN/cm2");
                et_fuChapa.setEnabled(false);
                et_fyChapa.setText(Double.toString(fyChapa)+" KN/cm2");
                et_fyChapa.setEnabled(false);
            }

        }

        //VERIFICA A CHECKBOX DE ANTI CORROSAO
        cb_AntiCorrosao=findViewById(R.id.checkBox);
        if (cb_AntiCorrosao.isChecked()){
            Protegido=true;
        } else if (!cb_AntiCorrosao.isChecked()) {
            Protegido=false;
        }

    }

    private void SetArrayListFuracao () {
        // Adapter do Spinner furacao chapa
        ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,furacaoChapa);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_furacao= findViewById(R.id.sp_furacao);
        sp_furacao.setAdapter(adapter5);
        sp_furacao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                positionFuracao = sp_furacao.getSelectedItemPosition();
                FuracaoS= furacaoChapa[positionFuracao];
                BuscaDeDadosParafuso();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                positionFuracao=0;
                FuracaoS= furacaoChapa[positionFuracao];
                BuscaDeDadosParafuso();

            }
        });
    }

    public  void show () {

        resutados = findViewById(R.id.tv_resultspag1);
        resutados.setText("dpar - "  + dPar+" "+ "dfpar - " + dfPar + " " + " fupar - " + fuPar+ " " + " fypar - " + fyPar + " " + " lpar - " + lPar+  " " + " fuchap - " + fuChapa + " " + " fychapa - " + fyChapa+ " " + " bchapa - " + bChapa+ " " + " h chapa - " + hChapa+ " "+ " t chapa - " + tChapa+ " "+"protegido" + Protegido  );

    }




    public  void openCarregamento (){
        Intent Myintent = new Intent(this,Carregamento.class);
        Myintent.putExtra("fuPar",fuPar);
        Myintent.putExtra("fyPar",fyPar);
        Myintent.putExtra("lPar",lPar);
        Myintent.putExtra("dfPar",dfPar);
        Myintent.putExtra("dPar",dPar);
        Myintent.putExtra("fuChapa",fuChapa);
        Myintent.putExtra("fyChapa",fyChapa);
        Myintent.putExtra("bChapa",bChapa);
        Myintent.putExtra("hChapa",hChapa);
        Myintent.putExtra("tChapa",tChapa);
        Myintent.putExtra("Protegido",Protegido);
        Myintent.putExtra("materialxyz",MaterialXYZ);
        Myintent.putExtra("materialchs",MaterialChs);
        Myintent.putExtra("materialchs",FuracaoS);






        startActivity(Myintent);
    }


}


