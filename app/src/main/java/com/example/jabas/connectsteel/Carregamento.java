package com.example.jabas.connectsteel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class Carregamento extends AppCompatActivity {
    public double dPar,dfPar,fuPar,fyPar,lPar,fuChapa,fyChapa,bChapa,hChapa,tChapa,Ng,Nq,Nq2,Sd,yg,yq,wu0,wu1,wu2,ya1,ya2,yq2,wu0b,wu1b,wu2b;
    public boolean Protegido;
    public String[] Combinacoes = new String[]{"Normais", "Especiais ou de Construção", "Excepcionais"};
    public String[] AcoesPermanentes = new String[]{"Peso próprio", "Peso próprio - (Pré-moldados)","Peso próprio - Moldado em loco", "Industrializados com adição em loco","Elementos construtivos em geral","Indireta"};
    public String[] AcoesVariaveis = new String[]{ "Ações variáveis de ocupação", "Ação do Vento", "Efeito Temperatura","Ações Truncadas", "Cargas móveis"};
    public String[] AcoesVariaveisNivelOcupação= new String[]{"Locais sem predominância de cargas fixas","Locais com predominância de cargas fixas","Bibiliotecas,arquivos depósitos e garagens..."};
    public String[] AcoesVariaveisNivelVento= new String[]{"Pressão dinâmica do vento"};
    public String[] AcoesVariaveisNivelTemp= new String[]{"Variação de temperatura em relação à média"};
    public String[] AcoesVariaveisNivelCargasMoveis= new String[]{"Passarelas de pedestres","Vigas de rolamento e pontes rolantes","Pilares e outras subestrturas em pontes rolantes"};
    public String[] ListVarNivel2 = AcoesVariaveisNivelOcupação;
    public String[] ListVarNivel2B = AcoesVariaveisNivelOcupação;
    public String [] Nada = new String []{"",""};
    public  TextView testintent,tv_ya1,tv_ya2,tv_yg,tv_yq1,tv_yq2,tv_Sd;
    public EditText et_Ng,et_Nq,et_Nq2;
    private Button nextpage3;
    private int positionCombinacao,positionAcoesPerm,positionAcoesVar1,positionCaracLocal,positionAcoesVar2,positionCaracLocal2;
    private Spinner sp_Combinacao,sp_AcaoPermanente,sp_AcaoVariavel,sp_CaracLocal,sp_AcaoVariavel2,sp_CaracLocal2;
    private static final String TAG = "Carregamento";
    public String materialxyz ="";
    public String materialchs = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carregamento);

    }

    @Override
    protected void onResume() {
        super.onResume();
        SetArrayListCombinacoes();
        SetArrayListAcoesPerm();
        SetArrayListAcoesVar();
        SetArrayListAcoesVar2();
        SetArrayListCaracLocal2();
        LeavingEditText();
        BuscadeDadosCarregamento();
        getMyIntent();


        nextpage3=(Button) findViewById(R.id.bt_nextpagebutton3);
        nextpage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BuscadeDadosCarregamento();
                if (Ng <=0) {
                    Toast.makeText(Carregamento.this, "Preencha todos os valores", Toast.LENGTH_SHORT).show();
                } else {
                    openGeometrico();
                }

                show();

            }
        });


    }

    private void SetArrayListCombinacoes () {
        // Adapter do Spinner Combinacoes
        //Na montagem e se nao for clicado no Spinner
        positionCombinacao=0;


        ArrayAdapter<String> adapter6 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Combinacoes);
        adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_Combinacao = findViewById(R.id.sp_comb);
        sp_Combinacao.setAdapter(adapter6);
        sp_Combinacao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                positionCombinacao = sp_Combinacao.getSelectedItemPosition();
                BuscadeDadosCarregamento();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                positionCombinacao = 0;
                BuscadeDadosCarregamento();

            }
        });
    }


    private void SetArrayListAcoesPerm () {
        // Adapter do Spinner Acoes Permanentes
        //Na montagem e se nao for clicado no Spinner
        positionAcoesPerm=0;


        ArrayAdapter<String> adapter7 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, AcoesPermanentes);
        adapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_AcaoPermanente = findViewById(R.id.sp_permAc);
        sp_AcaoPermanente.setAdapter(adapter7);
        sp_AcaoPermanente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                positionAcoesPerm = sp_AcaoPermanente.getSelectedItemPosition();
               BuscadeDadosCarregamento();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                positionAcoesPerm = 0;
                BuscadeDadosCarregamento();

            }
        });
    }

    private void SetArrayListAcoesVar () {
        // Adapter do Spinner Acoes Variavel
        //Na montagem e se nao for clicado no Spinner
        positionAcoesVar1=0;
        sp_CaracLocal=findViewById(R.id.sp_CaractLocal);

        ArrayAdapter<String> adapter8 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, AcoesVariaveis);
        adapter8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_AcaoVariavel = findViewById(R.id.sp_variaAc);
        sp_AcaoVariavel.setAdapter(adapter8);
        sp_AcaoVariavel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               positionAcoesVar1 = sp_AcaoVariavel.getSelectedItemPosition();

               if(positionAcoesVar1 == 0) {
                   ListVarNivel2 = AcoesVariaveisNivelOcupação;
                   sp_CaracLocal.setEnabled(true);
                   SetArrayListCaracLocal();
               } else if (positionAcoesVar1==1) {
                   ListVarNivel2 = AcoesVariaveisNivelVento;
                   sp_CaracLocal.setEnabled(true);
                   SetArrayListCaracLocal();
               }else if (positionAcoesVar1==2) {
                   ListVarNivel2 = AcoesVariaveisNivelTemp;
                   sp_CaracLocal.setEnabled(true);
                   SetArrayListCaracLocal();
               } else if (positionAcoesVar1==3) {
                   ListVarNivel2 = Nada;
                   SetArrayListCaracLocal();
                   sp_CaracLocal.setEnabled(false);
               } else if (positionAcoesVar1==4) {
                   ListVarNivel2=AcoesVariaveisNivelCargasMoveis;
                   sp_CaracLocal.setEnabled(true);
                   SetArrayListCaracLocal();
               }

               BuscadeDadosCarregamento();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                positionAcoesVar1 = 0;
                ListVarNivel2=AcoesVariaveisNivelOcupação;
                sp_CaracLocal.setEnabled(true);
                SetArrayListCaracLocal();
                BuscadeDadosCarregamento();
            }
        });
    }


    private void SetArrayListCaracLocal() {
        // Adapter do Spinner Caracteristicas do Local
        //Na montagem e se nao for clicado no Spinner
        positionCaracLocal=0;

        ArrayAdapter<String> adapter9 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, ListVarNivel2);
        adapter9.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_CaracLocal = findViewById(R.id.sp_CaractLocal);
        sp_CaracLocal.setAdapter(adapter9);
        sp_CaracLocal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               positionCaracLocal = sp_CaracLocal.getSelectedItemPosition();
               BuscadeDadosCarregamento();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                positionCaracLocal = 0;
                BuscadeDadosCarregamento();
            }
        });
    }

    // para a Ação variável 2

    private void SetArrayListAcoesVar2 () {
        // Adapter do Spinner Acoes Variavel
        //Na montagem e se nao for clicado no Spinner
        positionAcoesVar2=0;
        sp_CaracLocal2=findViewById(R.id.sp_CaractLocal2);

        ArrayAdapter<String> adapter10 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, AcoesVariaveis);
        adapter10.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_AcaoVariavel2 = findViewById(R.id.sp_variaAc2);
        sp_AcaoVariavel2.setAdapter(adapter10);
        sp_AcaoVariavel2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                positionAcoesVar2 = sp_AcaoVariavel2.getSelectedItemPosition();

                if(positionAcoesVar2 == 0) {
                    ListVarNivel2B = AcoesVariaveisNivelOcupação;
                    sp_CaracLocal2.setEnabled(true);
                    SetArrayListCaracLocal2();
                } else if (positionAcoesVar2==1) {
                    ListVarNivel2B = AcoesVariaveisNivelVento;
                    sp_CaracLocal2.setEnabled(true);
                    SetArrayListCaracLocal2();
                }else if (positionAcoesVar2==2) {
                    ListVarNivel2B = AcoesVariaveisNivelTemp;
                    sp_CaracLocal2.setEnabled(true);
                    SetArrayListCaracLocal2();
                } else if (positionAcoesVar2==3) {
                    ListVarNivel2B = Nada;
                    SetArrayListCaracLocal2();
                    sp_CaracLocal2.setEnabled(false);
                } else if (positionAcoesVar2==4) {
                    ListVarNivel2B=AcoesVariaveisNivelCargasMoveis;
                    sp_CaracLocal2.setEnabled(true);
                    SetArrayListCaracLocal2();
                }
                BuscadeDadosCarregamento();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                positionAcoesVar2 = 0;
                ListVarNivel2=AcoesVariaveisNivelOcupação;
                sp_CaracLocal2.setEnabled(true);
                SetArrayListCaracLocal2();
                BuscadeDadosCarregamento();
            }
        });
    }

    private void SetArrayListCaracLocal2() {
        // Adapter do Spinner Caracteristicas do Local
        //Na montagem e se nao for clicado no Spinner
        positionCaracLocal2=0;

        ArrayAdapter<String> adapter11 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, ListVarNivel2B);
        adapter11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_CaracLocal2 = findViewById(R.id.sp_CaractLocal2);
        sp_CaracLocal2.setAdapter(adapter11);
        sp_CaracLocal2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                positionCaracLocal2 = sp_CaracLocal2.getSelectedItemPosition();
                BuscadeDadosCarregamento();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                positionCaracLocal2 = 0;
                BuscadeDadosCarregamento();
            }
        });
    }


    public  void openGeometrico (){
        Intent Myintent2 = new Intent(this,Geometrico.class);
        Myintent2.putExtra("fuPar",fuPar);
        Myintent2.putExtra("fyPar",fyPar);
        Myintent2.putExtra("lPar",lPar);
        Myintent2.putExtra("dfPar",dfPar);
        Myintent2.putExtra("dPar",dPar);
        Myintent2.putExtra("fuChapa",fuChapa);
        Myintent2.putExtra("fyChapa",fyChapa);
        Myintent2.putExtra("bChapa",bChapa);
        Myintent2.putExtra("hChapa",hChapa);
        Myintent2.putExtra("tChapa",tChapa);
        Myintent2.putExtra("Protegido",Protegido);
        Myintent2.putExtra("Ya1",ya1);
        Myintent2.putExtra("Ya2",ya2);
        Myintent2.putExtra("Ng",Ng);
        Myintent2.putExtra("Nq",Nq);
        Myintent2.putExtra("Yg",yg);
        Myintent2.putExtra("Yq",yq);
        Myintent2.putExtra("wu0",wu0);
        Myintent2.putExtra("wu1",wu1);
        Myintent2.putExtra("wu2",wu2);
        Myintent2.putExtra("wu0b",wu0b);
        Myintent2.putExtra("wu1b",wu1b);
        Myintent2.putExtra("wu2b",wu2b);
        Myintent2.putExtra("Nq2",Nq2);
        Myintent2.putExtra("Yq2",yq2);
        Myintent2.putExtra("Sd",Sd);
        Myintent2.putExtra("materialxyz",materialxyz);
        Myintent2.putExtra("materialchs",materialchs);



        startActivity(Myintent2);
}
     private void getMyIntent() {
        dPar = getIntent().getDoubleExtra("dPar",0);
        dfPar= getIntent().getDoubleExtra("dfPar",0);
        lPar =getIntent().getDoubleExtra("lPar",0);
        dPar =getIntent().getDoubleExtra("dPar",0);
        fuPar= getIntent().getDoubleExtra("fuPar",0);
        fyPar=getIntent().getDoubleExtra("fyPar",0);
        fuChapa=getIntent().getDoubleExtra("fuChapa",0);
        fyChapa=getIntent().getDoubleExtra("fyChapa",0);
        bChapa=getIntent().getDoubleExtra("bChapa",0);
        tChapa=getIntent().getDoubleExtra("tChapa",0);
        hChapa=getIntent().getDoubleExtra("hChapa",0);
        Protegido= getIntent().getBooleanExtra("Protegido",false);
        materialxyz=getIntent().getExtras().getString("materialxyz","");
        materialchs=getIntent().getExtras().getString("materialchs","");


}

      private void show() {
        testintent = findViewById(R.id.tv_resultspag2);
        testintent.setText("ya1 - " +ya1+" "+"ya2 - "+ya2+" "+"Ng - "+Ng+" "+"Nq -" + Nq+" "+"YG - " + yg+" "+"YQ - " +yq+" "+"w0 - " +wu0+" "+ "w1 - " + wu1+" "+"w2 - "+wu2 +" "+"Nq2 -" + Nq2+" "+"Yq2 -" + yq2+" "+"wu0b -" + wu0b+" "+"wu1b -" + wu1b+" "+"wu2b -" + wu2b);
}

      public void BuscadeDadosCarregamento(){

          et_Ng = findViewById(R.id.et_Ng);
          String sNg = et_Ng.getText().toString();
          if (!sNg.isEmpty()) {
              Ng = Double.parseDouble(sNg);
              Log.d(TAG, "Solicitacao Permanente " + Ng);
          } else {
              Ng = 0;
          }

          et_Nq = findViewById(R.id.et_Nq);
          String sNq = et_Nq.getText().toString();
          if (!sNq.isEmpty()) {
              Nq = Double.parseDouble(sNq);
              Log.d(TAG, "Solicitacao Variavel " + Nq);
          } else {
              Nq = 0;
          }

          et_Nq2 = findViewById(R.id.et_Nq2);
          String sNq2 = et_Nq2.getText().toString();
          if (!sNq2.isEmpty()) {
              Nq2 = Double.parseDouble(sNq2);
              Log.d(TAG, "Solicitacao Variavel " + Nq2);
          } else {
              Nq2 = 0;
          }

          if (positionCombinacao == 0) {
              if (positionAcoesPerm == 0){
                  // Normais - peso proprio
                  yg = 1.25;
              } else if(positionAcoesPerm==1){
                  // Normais - peso proprio pre moldado
                  yg = 1.30;
              } else if (positionAcoesPerm ==2){
                  // Moldado em loco
                  yg = 1.35;
              } else if(positionAcoesPerm==3){
                  //Industrializado com add em loco
                  yg = 1.40;
              } else if (positionAcoesPerm==4) {
                  //Elementos construtivos
                  yg = 1.50;
              } else {
                  //Indireta
                  yg=1.2;
              }
          } else if (positionCombinacao ==1) {
              if (positionAcoesPerm == 0){
                  // Especiais - peso proprio
                  yg = 1.15;
              } else if(positionAcoesPerm==1){
                  // Especiais - peso proprio pre moldado
                  yg = 1.20;
              } else if (positionAcoesPerm ==2){
                  //Especiais Moldado em loco
                  yg = 1.25;
              } else if(positionAcoesPerm==3){
                  //Especiais Industrializado com add em loco
                  yg = 1.30;
              } else if (positionAcoesPerm==4){
                  //Especiais Elementos construtivos
                  yg = 1.50;
              } else {
                  //Especiais indireta
                  yg=1.2;
              }
          } else {
              if (positionAcoesPerm == 0){
                  // Excepicionais - peso proprio
                  yg = 1.1;
              } else if(positionAcoesPerm==1){
                  // Excepicionais - peso proprio pre moldado
                  yg = 1.15;
              } else if (positionAcoesPerm ==2){
                  //Excepicionais Moldado em loco
                  yg = 1.15;
              } else if(positionAcoesPerm==3){
                  // Excepicionais Industrializado com add em loco
                  yg = 1.20;
              } else if (positionAcoesPerm==4){
                  //Excepicionais Elementos construtivos
                  yg = 1.30;
              } else {
                  //Excepicionais indireta
                  yg=0;
              }
          }

// Carga variavel

          if (positionCombinacao == 0) {
              if (positionAcoesVar1 == 0){
                  // Normais - Ocupacao
                  yq = 1.5;
              } else if (positionAcoesVar1==1){
                  //Normais acao vento
                  yq=1.4;
              } else  if (positionAcoesVar1==2){
                  // normal temperatura
                  yq=1.2;
              } else if (positionAcoesVar1==3) {
                  //acoes truncadas
                  yq=1.2;
              } else {
                  // cargas moveis
                  yq = 1.5; //confirmar
              }

          } else if (positionCombinacao ==1) {

              if (positionAcoesVar1 == 0){
                  // Especiais - Ocupacao
                  yq = 1.3;
              } else if (positionAcoesVar1==1){
                  //Especiais acao vento
                  yq=1.2;
              } else  if (positionAcoesVar1==2){
                  // Especiais temperatura
                  yq=1.0;
              } else if (positionAcoesVar1==3) {
                  //Especiais truncadas
                  yq=1.1;
              } else {
                  // Especiais cargas moveis
                  yq = 1.3; //confirmar
              }

          } else {
              if (positionAcoesVar1 == 0){
                  // Excepicionais - Ocupacao
                  yq = 1.0;
              } else if (positionAcoesVar1==1){
                  //Excepicionais acao vento
                  yq=1.0;
              } else  if (positionAcoesVar1==2){
                  // Excepicionais temperatura
                  yq=1.0;
              } else if (positionAcoesVar1==3) {
                  //Excepicionais truncadas
                  yq=1.0;
              } else {
                  // Excepicionais cargas moveis
                  yq = 1.0; //confirmar
              }
          }

          // faz a relacao para achaar os valores de Wo,W1,W2
          if (positionAcoesVar1 ==0) {
              // ocupacao
              if (positionCaracLocal == 0) {
                  //sem predomincia de cargas fixas
                  wu0 = 0.5;
                  wu1 = 0.4;
                  wu2 = 0.3;
              } else if (positionCaracLocal==1) {
                  // com predominacia cargas fixas
                  wu0 = 0.7;
                  wu1 = 0.6;
                  wu2 = 0.4;
              } else if(positionCaracLocal==2) {
                  // depositos
                  wu0 = 0.8;
                  wu1 = 0.7;
                  wu2 = 0.6;
              }
          } else if (positionAcoesVar1==1) {
              // vento
                  wu0 = 0.6;
                  wu1 = 0.3;
                  wu2 = 0.0;
          } else if (positionAcoesVar1==2) {
              // temperatura
              wu0 = 0.6;
              wu1 = 0.5;
              wu2 = 0.3;
          } else if (positionAcoesVar1==3){
              //acoes truncadas
              wu0 = 0.0;
              wu1 = 0.0;
              wu2 = 0.0;
          } else if (positionAcoesVar1==4){
              //cargas moveis
              if (positionCaracLocal==0) {
                  wu0 = 0.6;
                  wu1 = 0.4;
                  wu2 = 0.3;
              } else if (positionCaracLocal==1){
                  wu0 = 1.0;
                  wu1 = 0.8;
                  wu2 = 0.5;
              } else {
                  wu0 = 0.7;
                  wu1 = 0.6;
                  wu2 = 0.4;
              }
          }


          //Para a variavel 2

          if (positionCombinacao == 0) {
              if (positionAcoesVar2 == 0){
                  // Normais - Ocupacao
                  yq2 = 1.5;
              } else if (positionAcoesVar2==1){
                  //Normais acao vento
                  yq2=1.4;
              } else  if (positionAcoesVar2==2){
                  // normal temperatura
                  yq2=1.2;
              } else if (positionAcoesVar2==3) {
                  //acoes truncadas
                  yq2=1.2;
              } else {
                  // cargas moveis
                  yq2 = 1.5; //confirmar
              }

          } else if (positionCombinacao ==1) {

              if (positionAcoesVar2 == 0){
                  // Especiais - Ocupacao
                  yq2 = 1.3;
              } else if (positionAcoesVar2==1){
                  //Especiais acao vento
                  yq2=1.2;
              } else  if (positionAcoesVar2==2){
                  // Especiais temperatura
                  yq2=1.0;
              } else if (positionAcoesVar2==3) {
                  //Especiais truncadas
                  yq2=1.1;
              } else {
                  // Especiais cargas moveis
                  yq2 = 1.3; //confirmar
              }

          } else {
              if (positionAcoesVar2 == 0){
                  // Excepicionais - Ocupacao
                  yq2 = 1.0;
              } else if (positionAcoesVar2==1){
                  //Excepicionais acao vento
                  yq2=1.0;
              } else  if (positionAcoesVar2==2){
                  // Excepicionais temperatura
                  yq2=1.0;
              } else if (positionAcoesVar2==3) {
                  //Excepicionais truncadas
                  yq2=1.0;
              } else {
                  // Excepicionais cargas moveis
                  yq2 = 1.0; //confirmar
              }
          }

          // faz a relacao para achaar os valores de Wo,W1,W2
          if (positionAcoesVar2 ==0) {
              // ocupacao
              if (positionCaracLocal2 == 0) {
                  //sem predomincia de cargas fixas
                  wu0b = 0.5;
                  wu1b = 0.4;
                  wu2b = 0.3;
              } else if (positionCaracLocal2==1) {
                  // com predominacia cargas fixas
                  wu0b = 0.7;
                  wu1b = 0.6;
                  wu2b = 0.4;
              } else if(positionCaracLocal2==2) {
                  // depositos
                  wu0b = 0.8;
                  wu1b = 0.7;
                  wu2b = 0.6;
              }
          } else if (positionAcoesVar2==1) {
              // vento
              wu0b = 0.6;
              wu1b = 0.3;
              wu2b = 0.0;
          } else if (positionAcoesVar2==2) {
              // temperatura
              wu0b = 0.6;
              wu1b = 0.5;
              wu2b = 0.3;
          } else if (positionAcoesVar2==3){
              //acoes truncadas
              wu0b = 0.0;
              wu1b = 0.0;
              wu2b = 0.0;
          } else if (positionAcoesVar2==4){
              //cargas moveis
              if (positionCaracLocal2==0) {
                  wu0b = 0.6;
                  wu1b = 0.4;
                  wu2b = 0.3;
              } else if (positionCaracLocal2==1){
                  wu0b = 1.0;
                  wu1b = 0.8;
                  wu2b = 0.5;
              } else {
                  wu0b = 0.7;
                  wu1b = 0.6;
                  wu2b = 0.4;
              }
          }

          if (positionCombinacao ==0) {
              ya1 = 1.10;
              ya2= 1.35;
          } else if(positionCombinacao==1) {
              ya1 = 1.10;
              ya2= 1.35;
          } else if(positionCombinacao==2) {
              ya1 = 1.00;
              ya2= 1.15;
          }

          CalculoSd();


          AtualizacaodeItensDinamicos();



}

        private void AtualizacaodeItensDinamicos (){
        tv_ya1=findViewById(R.id.tv_ya1);
        tv_ya1.setText(Double.toString(ya1));
        tv_ya2=findViewById(R.id.tv_ya2);
        tv_ya2.setText(Double.toString(ya2));
        tv_yg=findViewById(R.id.tv_yg);
        tv_yg.setText(Double.toString(yg));
        tv_yq1=findViewById(R.id.tv_yq);
        tv_yq1.setText(Double.toString(yq));
        tv_yq2 =findViewById(R.id.tv_yq2);
        tv_yq2.setText(Double.toString(yq2));
        tv_Sd =findViewById(R.id.tv_Sd);
        tv_Sd.setText(String.format("%.2f",Sd)+"KN");

}

        private double CalculoSd () {

        if(Nq<0) {
            yg=1;
            if(Nq2>0){
                yq2=1;
            }
        }
        if(Nq2<0) {
            yg=1;
            if(Nq>0){
                yq=1;
            }
        }

        if(positionCombinacao==2) {
            Sd=(yg*Ng)+(yq*Nq)+(yq2*wu2b*Nq2);
        } else {
            Sd=(yg*Ng)+(yq*Nq)+(yq2*wu0b*Nq2);
        }

        return Sd;
 }

 private void LeavingEditText () {
        et_Ng=findViewById(R.id.et_Ng);
        et_Nq=findViewById(R.id.et_Nq);
        et_Nq2=findViewById(R.id.et_Nq2);

     et_Ng.setOnFocusChangeListener(new View.OnFocusChangeListener() {

         public void onFocusChange(View v, boolean hasFocus) {
             if(!hasFocus)
                BuscadeDadosCarregamento();
         }
     });

     et_Nq.setOnFocusChangeListener(new View.OnFocusChangeListener() {

         public void onFocusChange(View v, boolean hasFocus2) {
             if(!hasFocus2)
                 BuscadeDadosCarregamento();
         }
     });

     et_Nq2.setOnFocusChangeListener(new View.OnFocusChangeListener() {

         public void onFocusChange(View v, boolean hasFocus3) {
             if(!hasFocus3)
                 BuscadeDadosCarregamento();
         }
     });
 }



    }
