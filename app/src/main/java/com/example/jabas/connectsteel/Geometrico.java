package com.example.jabas.connectsteel;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Color;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Geometrico extends AppCompatActivity {

    public double dPar,dfPar,fuPar,fyPar,lPar,fuChapa,fyChapa,bChapa,hChapa,tChapa,Ng,Nq,Nq2,Sd,yg,yq,wu0,wu1,wu2,ya1,ya2,yq2,wu0b,wu1b,wu2b,RanTra,RagTra,ResBloco,An1,Ag1, rnvPar, resPressaoApoio;
    public double cts = 1;
    public String testepaths = "";
    public String materialParafuso = "";
    public String materialchapaS = "";
    public double distMin;

    public boolean Protegido;
    public TextView testintent2,testresults;

    private static final String TAG = "Geometrico";

    private  Button nextpage4,bt_cleaar; /*declarei o botao */

    //add PointsGraphSeries of DataPoint type
    PointsGraphSeries<DataPoint> xySeries;
    LineGraphSeries<DataPoint> border1;
    LineGraphSeries<DataPoint> border2;
    LineGraphSeries<DataPoint> border3;
    LineGraphSeries<DataPoint> border4;

    BarGraphSeries<DataPoint> backgroundChapa;

    private Button btnAddPt;

    private EditText mX,mY;

    GraphView mScatterPlot;

    //make xyValueArray global
    private ArrayList<XYValue> xyValueArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geometrico);
        getmyItent2();

        //declare variables in oncreate
/*add o botao nextpage */
        nextpage4=(Button) findViewById(R.id.bt_nextpagebutton4);
        nextpage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openResultado();

            }
        }); /*ate aqui*/
        bt_cleaar =(Button) findViewById(R.id.bt_ClearGraph) ;
        bt_cleaar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mScatterPlot.removeAllSeries();
                xyValueArray = new ArrayList<>();
                drawBorders();
            }
        });

        btnAddPt = (Button) findViewById(R.id.btnAddPt);
        mX = (EditText) findViewById(R.id.numX);
        mY = (EditText) findViewById(R.id.numY);
        mScatterPlot = (GraphView) findViewById(R.id.scatterPlot);
        xyValueArray = new ArrayList<>();
        distMin = minimoDistanciaParBorda();

        border1 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(distMin / 10, distMin / 10),
                new DataPoint(distMin / 10, bChapa / 10 - distMin / 10)
        });
        border2 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(distMin / 10, bChapa / 10 - distMin / 10),
                new DataPoint(hChapa / 10 - distMin / 10, bChapa / 10 - distMin / 10)
        });

        border3 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(distMin / 10, distMin / 10),
                new DataPoint(hChapa / 10 - distMin / 10, distMin / 10)
        });
        border4 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(hChapa / 10 - distMin / 10, distMin / 10),
                new DataPoint(hChapa / 10 - distMin / 10, bChapa / 10 - distMin / 10)
        });

        init();
    }

    private void drawBorders(){

        border1.setColor(Color.RED);
        border2.setColor(Color.RED);
        border3.setColor(Color.RED);
        border4.setColor(Color.RED);

        mScatterPlot.addSeries(border1);
        mScatterPlot.addSeries(border2);
        mScatterPlot.addSeries(border3);
        mScatterPlot.addSeries(border4);

        mScatterPlot.getViewport().setYAxisBoundsManual(true);
        mScatterPlot.getViewport().setMaxY(bChapa/10); /*get b */
        mScatterPlot.getViewport().setMinY(0);

        //set manual y bounds
        mScatterPlot.getViewport().setXAxisBoundsManual(true);
        mScatterPlot.getViewport().setMaxX(hChapa/10); /*get h*/
        mScatterPlot.getViewport().setMinX(0);
    }

    private void getmyItent2 () {

        dfPar= getIntent().getDoubleExtra("dfPar",0);
        lPar = getIntent().getDoubleExtra("lPar",0);
        dPar = getIntent().getDoubleExtra("dPar",0);
        fuPar = getIntent().getDoubleExtra("fuPar",0);
        fyPar = getIntent().getDoubleExtra("fyPar",0);
        fuChapa = getIntent().getDoubleExtra("fuChapa",0);
        fyChapa = getIntent().getDoubleExtra("fyChapa",0);
        bChapa = getIntent().getDoubleExtra("bChapa",0);
        tChapa = getIntent().getDoubleExtra("tChapa",0);
        hChapa = getIntent().getDoubleExtra("hChapa",0);

        //Ng,Nq,Nq2,Sd,yg,yq,wu0,wu1,wu2,ya1,ya2,yq2,wu0b,wu1b,wu2b

        ya1=getIntent().getDoubleExtra("Ya1",0);
        ya2=getIntent().getDoubleExtra("Ya2",0);
        Ng=getIntent().getDoubleExtra("Ng",0);
        Nq=getIntent().getDoubleExtra("Nq",0);
        yg=getIntent().getDoubleExtra("Yg",0);
        yq=getIntent().getDoubleExtra("Yq",0);
        wu0=getIntent().getDoubleExtra("wu0",0);
        wu1=getIntent().getDoubleExtra("wu1",0);
        wu2=getIntent().getDoubleExtra("wu2",0);
        wu0b=getIntent().getDoubleExtra("wu0b",0);
        wu1b=getIntent().getDoubleExtra("wu1b",0);
        wu2b=getIntent().getDoubleExtra("wu2b",0);
        Nq2=getIntent().getDoubleExtra("Nq2",0);
        yq2=getIntent().getDoubleExtra("Yq2",0);
        Sd=getIntent().getDoubleExtra("Sd",0);

        Protegido= getIntent().getBooleanExtra("Protegido",false);

        materialParafuso=getIntent().getExtras().getString("materialxyz","");
        materialchapaS=getIntent().getExtras().getString("materialchs","");

    }

    private void show() {
        testintent2 = findViewById(R.id.tv_testIntent2);
        testresults= findViewById(R.id.tv_testeResult2);
        testintent2.setText("teste testoso do teste  " + testepaths);
        testresults.setText("Ran " +String.format("%.2f", RanTra)+"KN "
                + "Rag " +String.format("%.2f",RagTra)
                + "\nResBloco " + String.format("%.2f",ResBloco)
                + " RNV " + String.format("%.2f", rnvPar)
        +"\nResPressaoApoio" + String.format("%.2f",resPressaoApoio));

        Log.d(TAG,"tste do teste do teste teste  " + testepaths);
    }

    private void init(){
        //declare the xySeries Object
        xySeries = new PointsGraphSeries<>();

        btnAddPt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mX.getText().toString().equals("") && !mY.getText().toString().equals("") ){
                    double x = Double.parseDouble(mX.getText().toString());
                    double y = Double.parseDouble(mY.getText().toString());

                    distMin = minimoDistanciaParBorda();
                    if(x > hChapa / 10 || y > bChapa / 10) { //MODIFICADO EM 5/10 PARA NAO ADD PTS FORA DO LIMITE
                        toastMessage("Posição do conector fora dos limites da chapa.");
                    } else if (containsParafuso(x, y)) {
                        toastMessage("Você não pode adicionar um conector sobre outro.");
                    } else if (x * 10 < distMin || y * 10 < distMin || x * 10 > hChapa - distMin || y * 10 > bChapa - distMin) {
                        toastMessage("A norma NBR 8800 estabelece a distância mínima entre parafuso e borda neste caso é de " + distMin + " mm");
                    } else if (minimoDistanciaEntreParafusos(x, y)) {
                        toastMessage("A norma NBR 8800 estabelece a distância mínima entre parafusos neste caso é de " + 3 * dPar + " mm");
                    } else {
                        Log.d(TAG, "onClick: Adding a new point. (x,y): (" + x + "," + y + ")" );
                        xyValueArray.add(new XYValue(x,y));

                        // nosso print
                        Log.d(TAG,"Aqui eh quando adiciona!");
                        for (int i = 0; i<xyValueArray.size();i++) {
                            Log.d(TAG,Double.toString(xyValueArray.get(i).getX()) + ", " + Double.toString(xyValueArray.get(i).getY()));
                        }
                        init();
                    }
                }else {
                    toastMessage("Preencha ambos os campos");
                }
            }
        });

        //little bit of exception handling for if there is no data.
        if(xyValueArray.size() != 0){
            createScatterPlot();
        }else{

            drawBorders();

            Log.d(TAG, "onCreate: No data to plot.");
        }
    }

    private boolean containsParafuso(double x, double y){

        for(int i = 0; i < xyValueArray.size(); i++){
            if (xyValueArray.get(i).getX() == x && xyValueArray.get(i).getY() == y) return true;
        }
        return false;
    }

    private boolean minimoDistanciaEntreParafusos(double x, double y){

        for(int i = 0; i < xyValueArray.size(); i++){
            double dist = Math.sqrt(Math.pow(xyValueArray.get(i).getX() * 10 - x * 10, 2) + Math.pow(xyValueArray.get(i).getY() * 10 - y * 10, 2));
         //   if (dist < 10 * dPar)
                return false;
        }
        return false;
    }

    private double minimoDistanciaParBorda(){

        double distMin;
        if(dPar <= 19){
            distMin = dPar + 6;
        }else if(dPar <= 26){
            distMin = dPar + 7;
        }else if(dPar <= 30){
            distMin = dPar + 9;
        }else if(dPar <= 36){
            distMin = dPar + 10;
        }else{
            distMin = 1.25 * dPar;
        }
        Log.d(TAG, "UAUAUUAUAUUAUAUAUUAU: " + distMin + "asdasdasd asd: " + dPar);
        return distMin;
    }

    private String espacamentoMaximo(Chapa c){

        int espMaximo;

        if(Protegido){
            espMaximo = 24;
        }else{
            espMaximo = 14;
        }

        for(int i = 0; i < c.getColunas().size(); i++){
            for(int j = 1; j < c.getColunas().get(i).getParafusos().size(); j++){
                if (j == 1){
                    if(12 * c.getT() > 150){
                        if (Math.abs(c.getColunas().get(i).getParafusos().get(0).getY() - c.getColunas().get(i).getParafusos().get(j).getY()) > 12 * c.getT()) {
                            // Da um aviso.
                            return "A norma NBR 8800 estabelece o espaçamento máximo do parafuso até a borda não pode ser maior que 12T ou 150 mm para estruturas com proteção anti-corrosão. Verifique o espaçamento máximo ou calcule assim mesmo.";
                        }
                    }else{
                        if (Math.abs(c.getColunas().get(i).getParafusos().get(j - 1).getY() - c.getColunas().get(i).getParafusos().get(j).getY()) > 150) {
                            // Da um aviso.
                            return "A norma NBR 8800 estabelece o espaçamento máximo do parafuso até a borda não pode ser maior que 12T ou 150 mm para estruturas com proteção anti-corrosão. Verifique o espaçamento máximo ou calcule assim mesmo.";
                        }
                    }
                    continue;
                }
                if (j == c.getColunas().get(i).getParafusos().size() - 1){
                    if(12 * c.getT() > 150){
                        if (Math.abs(c.getColunas().get(i).getParafusos().get(j - 1).getY() - c.getColunas().get(i).getParafusos().get(j).getY()) > 12 * c.getT()) {
                            // Da um aviso.
                            return "A norma NBR 8800 estabelece o espaçamento máximo do parafuso até a borda não pode ser maior que 12T ou 150 mm para estruturas com proteção anti-corrosão. Verifique o espaçamento máximo ou calcule assim mesmo.";
                        }
                    }else{
                        if (Math.abs(c.getColunas().get(i).getParafusos().get(j - 1).getY() - c.getColunas().get(i).getParafusos().get(j).getY()) > 150) {
                            // Da um aviso.
                            return "A norma NBR 8800 estabelece o espaçamento máximo do parafuso até a borda não pode ser maior que 12T ou 150 mm para estruturas com proteção anti-corrosão. Verifique o espaçamento máximo ou calcule assim mesmo.";
                        }
                    }
                    continue;
                }
                if (Math.abs(c.getColunas().get(i).getParafusos().get(j - 1).getY() - c.getColunas().get(i).getParafusos().get(j).getY()) > espMaximo * c.getT()) {
                    // Da um aviso.
                    return "A norma NBR 8800 estabelece o espaçamento máximo entre paraafusos de 24T para estruturas com proteção anti-corrosão. Verifique o espaçamento máximo ou calcule assim mesmo.";
                }
            }
        }

        for(int i = 1; i < c.getColunas().size(); i++){
            if (Math.abs(c.getColunas().get(i - 1).getParafusos().get(0).getY() - c.getColunas().get(i).getParafusos().get(0).getY()) > espMaximo * c.getT()) {
                // Da um aviso.
                return "A norma NBR 8800 estabelece o espaçamento máximo entre paraafusos de 24T para estruturas com proteção anti-corrosão. Verifique o espaçamento máximo ou calcule assim mesmo.";
            }
        }
        return "";
    }


    private void createScatterPlot() {
        Log.d(TAG, "createScatterPlot: Creating scatter plot.");

        //sort the array of xy values
        xyValueArray = sortArray(xyValueArray);
        Log.d(TAG,"Aqui eh quando plota!");
        for (int i = 0; i<xyValueArray.size();i++) {
            Log.d(TAG,Double.toString(xyValueArray.get(i).getX()) + ", " + Double.toString(xyValueArray.get(i).getY()));
        }


        //add the data to the series
        for(int i = 0;i <xyValueArray.size(); i++){
            try{
                double x = xyValueArray.get(i).getX();
                double y = xyValueArray.get(i).getY();
                xySeries.appendData(new DataPoint(x,y),true, 1000);
            }catch (IllegalArgumentException e){
                Log.e(TAG, "createScatterPlot: IllegalArgumentException: " + e.getMessage() );
            }
        }



        //set some properties
        //xySeries.setShape(PointsGraphSeries.Shape.RECTANGLE);
        xySeries.setColor(Color.rgb(100,100,100));
        xySeries.setCustomShape(new PointsGraphSeries.CustomShape() {
            @Override
            public void draw(Canvas canvas, Paint paint, float x, float y, DataPointInterface dataPoint) {
                paint.setStrokeWidth(10);
                paint.setColor(Color.rgb(120,120,120));
                canvas.drawCircle (x, y, 25f, paint);
                paint.setColor(Color.rgb(80, 80, 80));
                canvas.drawLine(x-10, y-10, x+10, y+10, paint);
                canvas.drawLine(x+10, y-10, x-10, y+10, paint);
            }
        });

        xySeries.setSize(20f);

        mScatterPlot.getViewport().setScrollable(true);
        mScatterPlot.getViewport().setScrollableY(true);

        //set manual x bounds
        mScatterPlot.getViewport().setYAxisBoundsManual(true);
        mScatterPlot.getViewport().setMaxY(bChapa/10); /*get b */
        mScatterPlot.getViewport().setMinY(0);

        //set manual y bounds
        mScatterPlot.getViewport().setXAxisBoundsManual(true);
        mScatterPlot.getViewport().setMaxX(hChapa/10); /*get h*/
        mScatterPlot.getViewport().setMinX(0);

        drawBorders();

        mScatterPlot.addSeries(xySeries);
    }

    /**
     * Sorts an ArrayList<XYValue> with respect to the x values.
     * @param array
     * @return
     */
    private ArrayList<XYValue> sortArray(ArrayList<XYValue> array){
        /*
        //Sorts the xyValues in Ascending order to prepare them for the PointsGraphSeries<DataSet>
         */
        int factor = Integer.parseInt(String.valueOf(Math.round(Math.pow(array.size(),2))));
        int m = array.size() - 1;
        int count = 0;
      /*  Log.d(TAG, "sortArray: Sorting the XYArray.");*/


        while (true) {
            m--;
            if (m <= 0) {
                m = array.size() - 1;
            }
            //Log.d(TAG, "sortArray: m = " + m);
            try {
                //print out the y entrys so we know what the order looks like
                //Log.d(TAG, "sortArray: Order:");
                //for(int n = 0;n < array.size();n++){
                //Log.d(TAG, "sortArray: " + array.get(n).getY());
                //}
                double tempY = array.get(m - 1).getY();
                double tempX = array.get(m - 1).getX();
                if (tempX > array.get(m).getX()) {
                    array.get(m - 1).setY(array.get(m).getY());
                    array.get(m).setY(tempY);
                    array.get(m - 1).setX(array.get(m).getX());
                    array.get(m).setX(tempX);
                } else if (tempX == array.get(m).getX()) {
                    count++;
                    //Log.d(TAG, "sortArray: count = " + count);
                } else if (array.get(m).getX() > array.get(m - 1).getX()) {
                    count++;
                 // Log.d(TAG, "sortArray: count = " + count);
                }
                //break when factorial is done
                if (count == factor) {
                    break;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                Log.e(TAG, "sortArray: ArrayIndexOutOfBoundsException. Need more than 1 data point to create Plot." +
                        e.getMessage());
                break;
            }
        }
        return array;
    }

    /**
     *
     * @return
     */
    private ArrayList<Float> getColunasPorParafusos(){
        ArrayList<Float> colunas = new ArrayList<Float>();

        for(int i = 0; i < this.xyValueArray.size(); i++){
            if(!colunas.contains(new Float(this.xyValueArray.get(i).getX() * 10))){
                colunas.add(new Float(this.xyValueArray.get(i).getX() * 10));
            }
        }
        return colunas;
    }

    /**
     *
     * @param coluna
     * @return
     */
    private ArrayList<Float> getParafusosPorColunas(Float coluna){
        ArrayList<Float> parafusos = new ArrayList<Float>();
        for(int i = 0; i < this.xyValueArray.size(); i++){
            if(coluna == this.xyValueArray.get(i).getX() * 10){
                parafusos.add(new Float(xyValueArray.get(i).getY() * 10));
            }
        }
        return parafusos;
    }

    /**
     *
     * @param nome
     * @param b
     * @param t
     * @param h
     * @param matChapa
     * @param furacao
     * @param isParafusoComun
     * @param d
     * @param matParafuso
     * @param l
     * @param furacaoParafuso
     * @return
     */
    private Chapa gerarChapa(String nome, float b, float t, float h, String matChapa, String furacao, boolean isParafusoComun, float d, boolean matParafuso, float l, double furacaoParafuso, boolean protecao,double fu, double fy,double fuPar, double fyPar, double dfPar1){

        //Verifica o tipo de parafuso

        if (materialParafuso.equals("A307")||  materialParafuso.equals("")) {
            isParafusoComun=true;


        } else {
            isParafusoComun=false;
        }

        Log.d("qual tipo de par e" , Boolean.toString(isParafusoComun));


        ArrayList<Float> colunas = getColunasPorParafusos();
        Collections.sort(colunas);

        Chapa chapa = new Chapa(nome, b, t, h, matChapa, furacao, protecao,fu,fy);

        for (int i = 0; i < colunas.size(); i++){
            chapa.getColunas().add(new Coluna(colunas.get(i), Integer.toString(i)));
        }

        for(int i = 0; i < colunas.size(); i++){

            ArrayList<Float> parafuso = getParafusosPorColunas(colunas.get(i));

            parafuso.add(new Float(0));
            parafuso.add(chapa.getB());
            Collections.sort(parafuso);
            for(int j = 0 ; j < parafuso.size(); j++){

                float x = chapa.getColunas().get(i).getX();
                float y =  parafuso.get(j);
                String label = chapa.getColunas().get(i).getName() + (chapa.getColunas().get(i).getParafusos().size() + 1);
                Parafuso p = null;
                if(isParafusoComun){
                    p = new ParafusoComum(x, y, label, d, isParafusoComun, matParafuso, l, furacaoParafuso,fuPar,fyPar,dfPar1);
                }else{
                    p = new ParafusoAltaResistencia(x, y, label, d, isParafusoComun, matParafuso, l, furacaoParafuso,fuPar,fyPar,dfPar1);
                }

                if(p != null) chapa.getColunas().get(i).getParafusos().add(p);
            }
            parafuso.clear();
        }
        return chapa;
    }

    private Graph gerarGrafo(Chapa c){
        ArrayList<Parafuso> parafusos = new ArrayList<Parafuso>();

        for(int i = 0; i < c.getColunas().size(); i++){
            for(int j = 0; j < c.getColunas().get(i).getParafusos().size(); j ++){
                parafusos.add(c.getColunas().get(i).getParafusos().get(j));
            }
        }

        // Gera o grafo.
        Graph G = new Graph(parafusos.size());

        // Popula o grafo segundo as restrições do livro.
        for(int i = 0; i < parafusos.size(); i++){
            for(int j = 0; j < parafusos.size(); j ++){
                // Se ta na mesma coluna so conecta com o de baixo.
                if(parafusos.get(i).getX() == parafusos.get(j).getX() && j == i + 1){
                    if(i != j) G.addEdge(i, j);
                    continue;
                }
                // Se for a ultima coluna so conecta com a penultima.
                if(Integer.parseInt(parafusos.get(i).getLabel()) > (c.getColunas().size() - 1 )*10){
                    if(Integer.parseInt(parafusos.get(j).getLabel()) < (c.getColunas().size() - 1 )*10 && Integer.parseInt(parafusos.get(j).getLabel()) > (c.getColunas().size() - 2 )*10){
                        if(parafusos.get(i).getY() < parafusos.get(j).getY()){ // So conecta com parafusos abaixo
                            if(i != j) G.addEdge(i, j);
                        }
                    }
                }

                // Se for a primeira coluna so conecta com a segunda.
                if(Integer.parseInt(parafusos.get(i).getLabel()) < 10){
                    if(Integer.parseInt(parafusos.get(j).getLabel()) < 20 && Integer.parseInt(parafusos.get(j).getLabel()) > 10){
                        if(parafusos.get(i).getY() < parafusos.get(j).getY()){ // So conecta com parafusos abaixo
                            if(i != j) G.addEdge(i, j);
                        }
                    }
                }
                // Se é uma das colunas do meio da chapa.
                if(Integer.parseInt(parafusos.get(i).getLabel()) > 10 && Integer.parseInt(parafusos.get(i).getLabel()) < (c.getColunas().size() - 1 )*10){
                    int currentCol = Integer.parseInt(parafusos.get(i).getLabel())/10;

                    if((Integer.parseInt(parafusos.get(j).getLabel()) > (currentCol - 1 ) * 10 && Integer.parseInt(parafusos.get(j).getLabel()) < currentCol * 10) ||
                            (Integer.parseInt(parafusos.get(j).getLabel()) > currentCol * 10 && Integer.parseInt(parafusos.get(j).getLabel()) < (currentCol + 2 ) * 10)){ // se for um dos do meio conecta com seus vizinhos.
                        if(parafusos.get(i).getY() < parafusos.get(j).getY()){ // So conecta com parafusos abaixo
                            if(parafusos.get(i).getX() != parafusos.get(j).getX()) { // Tira o bug do 11
                                if (i != j) G.addEdge(i, j);
                                continue;
                            }
                        }
                    }
                }
            }
        }

        return G;
    }

    private Double gerarAllPaths(Chapa c, Graph G){

        ArrayList<Parafuso> parafusos = new ArrayList<Parafuso>();

        for(int i = 0; i < c.getColunas().size(); i++){
            for(int j = 0; j < c.getColunas().get(i).getParafusos().size(); j ++){
                parafusos.add(c.getColunas().get(i).getParafusos().get(j));
            }
        }

        //Pra rodar o paths é so tirar esses comentarios abaixo.
        ArrayList<AllPaths> allPaths = new ArrayList<AllPaths>();

        ArrayList<Double> calcPaths = new ArrayList<Double>();

        for(int i = 0; i < parafusos.size(); i++) {
            for (int j = 0; j < parafusos.size(); j++) {
                if(parafusos.get(i).getY() == 0 && parafusos.get(j).getY() == c.getB() && i != j){
                    AllPaths currPath = new AllPaths(G, i, j);
                    allPaths.add(currPath);
                    ArrayList<ArrayList<Integer>> paths = currPath.getPaths();
                    //printPaths(paths);
                    calcPaths.add(getMenorCamiho(c, paths, parafusos));
                }
            }
        }

        Collections.sort(calcPaths);

        for (int i =0; i<calcPaths.size(); i++) {
            testepaths +=" " + calcPaths.get(i);
        }

        double shortest = calcPaths.get(0);
        return shortest;
    }

    public double getMenorCamiho(Chapa c, ArrayList<ArrayList<Integer>> paths, ArrayList<Parafuso> parafusos){
        ArrayList<Double> calcPaths = new ArrayList<Double>();
        for (int i = 0; i < paths.size(); i++) {
            ArrayList<Integer> aux = paths.get(i);
            double sum = calcCaminho(c, aux, parafusos);
            calcPaths.add(sum);
        }

        Collections.sort(calcPaths);

        return calcPaths.get(0);
    }

    public double calcCaminho(Chapa c, ArrayList<Integer> path, ArrayList<Parafuso> parafusos){
        double sum = c.getB() - (path.size() - 2) * (dfPar); // colocar valor df
        for (int j = 0; j < path.size() - 1; j++) {
            if(parafusos.get(path.get(j)).getX() != parafusos.get(path.get(j + 1)).getX()){
                double s = Math.pow(parafusos.get(path.get(j)).getX() - parafusos.get(path.get(j + 1)).getX(), 2);
                double g = Math.abs(parafusos.get(path.get(j)).getY() - parafusos.get(path.get(j + 1)).getY());
                double div = s/(4 * g);// era s
                sum += div; // adição da parcela diagonal
            }
        }
        return sum;
    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    public  static  double AreaLiquida (double t,double shortest){
        double An=(t*shortest)/100;
        return An;
    }

    public static  double AreaBruta (double t, double b) {
        double Ag= (t*b)/100;
        return Ag;
    }

   public static double Resistencia(double A,double y,double f) {
        double R = A*f/y;
        return R;
    }

    // CALCULO CISALHAMENTO EM BLOCO

   public   double ResCisBloco (Chapa c, double ya2, double fu, double fy, double cts) {

       Parafuso maior = c.getColunas().get(0).getParafusos().get(1); /* cisalhamento para primeira linha */
       Parafuso menor = c.getColunas().get(0).getParafusos().get(1);
       for (int i = 0; i < c.getColunas().size(); i++) {
           if (c.getColunas().get(i).getParafusos().get(1).getY() < menor.getY())
               menor = c.getColunas().get(i).getParafusos().get(1);
       }
       for (int i = 0; i < c.getColunas().size(); i++) {
           if (c.getColunas().get(i).getParafusos().get(1).getX() > maior.getX())
               if (c.getColunas().get(i).getParafusos().get(1).getY() == menor.getY())
                   maior = c.getColunas().get(i).getParafusos().get(1);
       }

       Parafuso pultimalinhaY = c.getColunas().get(0).getParafusos().get(c.getColunas().get(0).getParafusos().size() - 2); /* cisalhamento para ultima linha */
       Parafuso pultimalinhaX = c.getColunas().get(0).getParafusos().get(c.getColunas().get(0).getParafusos().size() - 2);
       for (int i = 0; i < c.getColunas().size(); i++) {
           if (c.getColunas().get(i).getParafusos().get(c.getColunas().get(i).getParafusos().size() - 2).getY() > pultimalinhaY.getY())
               pultimalinhaY = c.getColunas().get(i).getParafusos().get(c.getColunas().get(i).getParafusos().size() - 2);
       }
       for (int i = 0; i < c.getColunas().size(); i++) {
           if (c.getColunas().get(i).getParafusos().get(c.getColunas().get(i).getParafusos().size() - 2).getX() > pultimalinhaX.getX())
               if (c.getColunas().get(i).getParafusos().get(c.getColunas().get(i).getParafusos().size() - 2).getY() == pultimalinhaY.getY())
                   pultimalinhaX = c.getColunas().get(i).getParafusos().get(c.getColunas().get(i).getParafusos().size() - 2);
       }

       int naultimalinha = 0;
       for (int i = 0; i < c.getColunas().size(); i++) {
           if (c.getColunas().get(i).getParafusos().get(c.getColunas().get(i).getParafusos().size() - 2).getY() == pultimalinhaY.getY())
               naultimalinha++;
       }
       Log.d(TAG,"dfPar que chega resbloc " +dfPar);

       Log.d(TAG,"Menor x " + menor.getX()+" Menor y "+menor.getY()+" Maior x "+maior.getX()+" Maior y "+maior.getY()+" Pultima linhaX x "+pultimalinhaX.getX()+" Pultima linhaX y"+pultimalinhaX.getY()+" Pultima linhaY x "+pultimalinhaY.getX()+" Pultima linhaY y"+pultimalinhaY.getY() );
       Log.d(TAG,"parafusos na ultima " + naultimalinha);
       double Anv2 = ((pultimalinhaX.getX() - ((naultimalinha - 1) * (dfPar)) - (dfPar / 2)) * c.getT()/100) ;

       double Ant2 = ((c.getB() - (pultimalinhaY.getY()) - ((dfPar) / 2)) * c.getT()/100) ;

       double Agv2 = (pultimalinhaX.getX() * c.getT()/100) ;


        /*
        System.out.println(naultimalinha +" na ultima linha");
        System.out.println(pultimalinhaY.getY());
        System.out.println(pultimalinhaX.getX());
        System.out.println(Anv2);
        System.out.println(Ant2);
        System.out.println(Agv2);
        */

       /* a partir daqui e original */
       int naLinha = 0;
       for (int i = 0; i < c.getColunas().size(); i++) {
           if (c.getColunas().get(i).getParafusos().get(1).getY() == menor.getY()) naLinha++;
       }
       Log.d(TAG,"Na primeira linha" + naLinha);

       double Anv1 = ((maior.getX() - ((naLinha - 1) * (dfPar)) - ((dfPar) / 2)) * c.getT() /100) ;

       double Ant1 = ((menor.getY() - ((dfPar) / 2)) * c.getT()/100) ;

       double Agv1 = (maior.getX() * c.getT()/100);

       /* soma o de cima e o de baixo */
       double Anv = (Anv1 + Anv2)*10;
       double Agv = (Agv1 + Agv2)*10;
       double Ant = (Ant1 + Ant2)*10;

       Log.d(TAG,"anv1 agv1 ant1 bloc " +Double.toString(Anv1) + " " +Double.toString(Agv1) + " " + Double.toString(Ant1) );
       Log.d(TAG,"anv2 agv2 ant2 bloc " +Double.toString(Anv2) + " " +Double.toString(Agv2) + " " + Double.toString(Ant2) );

       Log.d(TAG,"anv agv ant bloc " +Double.toString(Anv) + " " +Double.toString(Agv) + " " + Double.toString(Ant) );
       double Rd1 = ((0.6 * fu * Anv) + (cts * fu * Ant)) / ya2;

       double Rd2 = ((0.6 * fy * Agv) + (cts * fu * Ant)) / ya2;

       System.out.println(naLinha + " na primeira linha");
       System.out.println(menor.getY());
       System.out.println(maior.getX());
       System.out.println(Anv1);
       System.out.println(Ant1);
       System.out.println(Agv1);

       if (Rd1 < Rd2) {
           System.out.printf("A resistência de cisalhamento em bloco é  %.2f KN \n", Rd1);
           return Rd1;
       } else {
           System.out.printf("A resistência de cisalhamento em bloco é  %.2f KN \n", Rd2);
           return Rd2;
       }
   }


    /* eu add aqui o botao*/
    public  void openResultado (){
        Log.d(TAG, "Acabou!");
        for (int i = 0; i<xyValueArray.size();i++) {
            Log.d(TAG,Double.toString(xyValueArray.get(i).getX()) + ", " + Double.toString(xyValueArray.get(i).getY()));
        }
        Intent intent = new Intent(this, Resultados.class);

        Chapa c = gerarChapa("Chaposa", new Float(bChapa), new Float(tChapa), new Float(hChapa), materialchapaS, "asdasd", true, new Float(dPar), true, new Float(lPar), 0, true,fuChapa,fyChapa,fuPar,fyPar,dfPar);

        ArrayList<String> output = c.ToString();

        Graph graph = gerarGrafo(c);
        Log.d(TAG,"AsasdasdasdsdsDDDDDDDDDDDDDDDDDDDDDD  espaçamento maximo:" + espacamentoMaximo(c));
        Double d = gerarAllPaths(c, graph);
        Log.d("esse e o d " + TAG, Double.toString(d));

        for(int i = 0; i < output.size(); i++){
            Log.d(TAG, output.get(i));
        }
        Log.d(TAG,"dfpar " +Double.toString(dfPar));

        An1 =AreaLiquida(c.getT(),d);
        Log.d(TAG,"esse e o an " +Double.toString(An1));
        Ag1=AreaBruta(c.getT(),c.getB());
        Log.d(TAG, "esse e o ag " +Double.toString(Ag1));
        RanTra = Resistencia(An1,ya2,fuChapa);
        RagTra = Resistencia(Ag1,ya1,fyChapa);
        Log.d(TAG,"Resistencia area bruta " +RagTra);
        Log.d(TAG,"Resistencia area liquida " +RanTra);

        /*System.out.printf("A resistência na área líquida é  %.2f KN \n",Ran);
        System.out.printf("A resistência na área bruta é %.2f KN \n",Rag);*/


        int nParafusos  = c.getNumeroParafusos() - (c.getColunas().size()*2);
        Log.d("oola","esse e o an " +Double.toString(ya2)+" "+Double.toString(c.getColunas().get(0).getParafusos().get(0).area)+" "+Double.toString(nParafusos)+" "+Double.toString(c.getColunas().get(0).getParafusos().get(0).fu)+" "+ nParafusos);
        Log.d("oola","material do par " +materialParafuso);
        rnvPar = c.getColunas().get(0).getParafusos().get(1).calculoRnv(ya2, 1, nParafusos);

        resPressaoApoio=c.pressaodeApoioeRasgamento(ya2);

        ResBloco = ResCisBloco(c, ya2, fuChapa, fyChapa, cts);

        Bitmap salvarChapa = mScatterPlot.takeSnapshot();

        intent.putExtra("snapshotGet", BitMapToString(salvarChapa));
        intent.putExtra("rnvPar", rnvPar);
        intent.putExtra("resPressaoApoio", resPressaoApoio);
        intent.putExtra("RagTra", RagTra);
        intent.putExtra("RanTra", RanTra);
        intent.putExtra("ResBloco", ResBloco);
        intent.putExtra("Sd", Sd);

        show();
        startActivity(intent);
    }

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
}
